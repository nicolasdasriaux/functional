import scala.language.higherKinds

import slick.driver.H2Driver.api._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.concurrent.duration.Duration

import model._
import model.Customers.customers
import model.Orders.orders

/**
ENTREES / SORTIE EN FONCTIONNEL

Nous allons utiliser Slick, un framework de mapping fonctionnel / relationel
pour illustrer les principes de séparation des E/S en fonctionnel :

* Dans la partie PURE, des fonctions fabriquent (et combinent)
  des objets immutables correspondant aux actions d'E/S à effectuer.

* Dans la partie IMPURE, les actions sont exécutées.
*/

/**
REQUETES (1/5)

Une requête est représentée par une instance de la classe `Query`
comportant 3 paramètres de généricité :

* la réprésentation de la table requêtée (ici décrite par la classe `Customers`),
* le type de la ligne de résulat (ici la case class `Customer`),
* le type de la collection de lignes du résultat
  lorque la requête sera exécutée (ici une séquence `Seq`).

Une requête est un objet immutable,
qui est simplement une représentation de la requête.

Une requête n'est pas exécutée à ce stade,
il n'y a aucun effet (lecture ou ecriture) sur la base de données.
*/
object CustomerQueries {
  def findAllCustomersQuery: Query[Customers, Customer, Seq] = customers
  def findCustomerByIdQuery(id: Int): Query[Customers, Customer, Seq] = customers.filter(_.id === 1)
}

/*
ACTIONS SUR LA BASE DE DONNEES (2/5)

Une action sur la base de donnée est représentée par une instance de la classe `DBIO`
comportant 1 paramètre de généricité :

* Type du résultat quand l'action sera executée.

Une action de base de données est un objet immutable,
qui est simplement une représention d'une action d'E/S
à effectuer sur la base de données.

Une action de base de données n'est pas exécutée à ce stade,
il n'y a aucun effet (lecture ou écriture) sur la base de données.
*/
object CustomerActions {
  import CustomerQueries._

  /**
  Action de création de la table `CUSTOMERS`
  Ne retourne rien en particulier (`Unit`) à l'exécution
  */
  def createCustomerSchemaAction: DBIO[Unit] =
    customers.schema.create

  /**
  Action de suppression de la table `CUSTOMERS`
  Ne retourne rien en particulier (`Unit`) à l'exécution
  */
  def dropCustomerSchemaAction: DBIO[Unit] =
    customers.schema.drop

  /**
  Action d'insertion d'une ligne dans la table `CUSTOMERS`
  Retourne le nombre de lignes insérées (`Int`) à l'exécution
  */
  def insertCustomerAction: DBIO[Int] = {
    customers += Customer(0, "Paul", "Simon", Some(30))
  }

  /**
  Action d'insertion de plusieurs lignes dans la table `CUSTOMERS`
  Retourne le nombre de lignes insérées (`Option[Int]`) à l'exécution
  */
  def insertCustomersAction: DBIO[Option[Int]] = {
    customers ++= Seq(
      Customer(1, "Paul", "Simon", Some(22)),
      Customer(2, "John", "Shepard", None),
      Customer(3, "William", "Richmond", None)
    )
  }

  /**
  Action de mise à jour d'une ligne dans la table `CUSTOMERS`
  Retourne le nombre de lignes mises à jour (`Int`) à l'exécution
  */
  def updateCustomerAction(id: Int): DBIO[Int] =
    findCustomerByIdQuery(id)
      .map(c => (c.firstName, c.lastName))
      .update("Peter", "Steward")

  /**
  Action de suppression d'une ligne dans la table `CUSTOMERS`
  Retourne le nombre de lignes supprimées (`Int`) à l'exécution
  */
  def deleteCustomerAction(id: Int): DBIO[Int] =
    findCustomerByIdQuery(id).delete

  /**
  Action de sélection de plusieurs lignes dans la table `CUSTOMERS`
  Retourne une séquence (`Seq`) d'object `Customer`à l'exécution

  La méthode `result` permet de transformer une requête en une action. 
  */
  def findAllCustomersAction: DBIO[Seq[Customer]] =
    findAllCustomersQuery.result


  /**
  Action de sélection d'une ligne dans la table `CUSTOMERS`
  Retourne une option (`Option`) d'objet `Customer` à l'exécution

  La méthode `result` permet de transformer une requête en une action.

  La méthode `headOption` permet de transformer
  une action retournant une séquence
  en une action retournant la tête de la séquence.

  Mais pourquoi une option ?
  */
  def findCustomerByIdAction(id: Int): DBIO[Option[Customer]] =
    findCustomerByIdQuery(id).result.headOption
}

/**
COMBINAISON D'ACTIONS (3/5)

Les actions peuvent être combinées ensemble pour former une nouvelle action.
L'action résultante reste immutable et sans effet immédiat sur la base de données.
*/
object CustomerActionsCombined {
  import CustomerActions._

  /**
  Combinaison d'actions formant une nouvelle action
  consistant en l'exécution séquentielle de plusieurs actions
  */
  val combinedAction: DBIO[(Option[Int], Seq[Customer], Option[Customer])] =
    for {
      _ <- createCustomerSchemaAction // Ignore le résultat, c'est juste `()`.
      customerCount <- insertCustomersAction

      allCustomers <- findAllCustomersAction
      customer <- findCustomerByIdAction(1)

      _ <- dropCustomerSchemaAction // Ignore le résultat, c'est juste  `()`.
    } yield (customerCount, allCustomers, customer)
}

/**
EXECUTION D'ACTIONS SUR LA BASE DE DONNEES (4/5)

C'est seulement quand une action est exécutée qu'il y a des effets sur la base de données.
L'action est exécutée de façon asynchrone et on récupère donc un `Future` qui contiendra le résultat.
*/
object CustomerActionsExecuted extends App {
  import CustomerActionsCombined._

  /**
  Description de la connexion à la base de données
  Aucune connexions n'est ouverte.
  */

  val database = Database.forURL("jdbc:h2:mem:ecommerce", driver = "org.h2.Driver")
  // val database = Database.forURL("jdbc:mysql:///ecommerce", driver = "com.mysql.jdbc.Driver", user = "root")

  /**
  L'action combinée est ici transformée
  en une action transactionnelle par la méthode `transactionally`.

  L'action transactionnelle est ensuite exécuté
  sur la base de donnée par la méthode `run`,
  qui alloue automatiquement la connexion et délimite la transaction.

  C'est ici le seul endroit IMPUR du code où il y a des effets sur la base de données.
  */
  val resultFuture: Future[(Option[Int], Seq[Customer], Option[Customer])] =
    database.run(combinedAction.transactionally)

  /*
  Lorsque le résultat du future est disponible, on l'affiche.
  Remarquer l'usage du pattern matching dans l'assignation.
  */
  for ((customerCount, allCustomers, customer) <- resultFuture) {
    println(s"customerCount=$customerCount")
    println(s"allCustomers=$allCustomers")
    println(s"customer=$customer")
  }

  /**
  En cas d'erreur sur le future, on affiche l'exception.
  */
  resultFuture.onFailure({
    case ex => println(ex)
  })
}

/**
TRANSPARENCE REFERENTIELLE (5/5)

Toutes les fonctions qui fabriquent des requêtes ou des actions respectent deux principes :

* Pour la même valeur des paramètres,
  la fonction retourne le même objet requête ou action
  ('même' au sens de l'égalité par valeur et non par référence).

* Il n'y a aucun effet sur la base de données,
  ni lecture, ni écriture, ni manipulation du schéma.

En jargon académique, on parle de transparence référentielle.
La notion d'effet fait également partie de ce jargon.

À ce stade, vous commencerez à comprendre le charabia des fondamentalistes fonctionnels.
*/
