import slick.driver.H2Driver.api._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.concurrent.duration.Duration

import model._
import model.Customers.customers
import model.Orders.orders

object PlaygroundQueries {
  /**
  [TODO]
  Ecrire une requête combinant `customers` et `orders`par une jointure externe gauche.
  http://slick.typesafe.com/doc/3.0.0/queries.html#joining-and-zipping
  */
  def findAllCustomersWithOrdersQuery: Query[(Customers, Rep[Option[Orders]]), (Customer, Option[Order]), Seq] = ???
}

object PlaygroundActions {
  import PlaygroundQueries._

  /**
  Combinaison de deux schémas en un seul
  */
  def schema = customers.schema ++ orders.schema

  def insertCustomers: DBIO[Option[Int]] = {
    customers ++= Seq(
      Customer(id = 1, firstName = "Pierre", lastName = "Renard", age = Some(34)),
      Customer(id = 2, firstName = "Jean", lastName = "Durand", age = Some(50)),
      Customer(id = 3, firstName = "Antoine", lastName = "Martin", age = Some(24)),
      Customer(id = 4, firstName = "Jean", lastName = "Citerne", age = None),
      Customer(id = 5, firstName = "Robert", lastName = "Pierrot", age = None)
    )
  }

  def insertOrders: DBIO[Option[Int]] = {
    orders ++= Seq(
      Order(id = 1, customerId = 1),
      Order(id = 2, customerId = 2),
      Order(id = 3, customerId = 2),
      Order(id = 4, customerId = 3),
      Order(id = 5, customerId = 3),
      Order(id = 6, customerId = 3)
    )
  }

  /**
  Combinaison de deux actions en une seule
  Ici le 'combinateur' perd les résultats de chacune des actions.
  */
  def insertSampleData: DBIO[Unit] = DBIO.seq(insertCustomers, insertOrders)

  /**
  [TODO] Ecrire l'action qui utilise la requête précédemment écrite
  */
  def findAllCustomersWithOrdersAction: DBIO[Seq[(Customer, Option[Order])]] = ???
}

object PlaygroundActionsCombined {
  import PlaygroundActions._

  /**
  [TODO] Créer une action qui combine :

  * la création du schéma des deux tables,
  * l'insertion des données de test dans les deux tables,
  * la requête qui joint les clients et les commandes avec une jointure externe gauche,
  * la destruction du schéma des deux tables.

  Et qui retourne une séquence de couples client et option de commande.
  */
  val combinedAction: DBIO[Seq[(Customer, Option[Order])]] = ???
}

object PlaygroundActionsExecuted extends App {
  import PlaygroundActionsCombined._

  val database = Database.forURL("jdbc:h2:mem:ecommerce", driver = "org.h2.Driver")

  val resultFuture: Future[Seq[(Customer, Option[Order])]] = database.run(combinedAction.transactionally)

  for (result <- resultFuture) {
    println(s"result=$result")
  }

  resultFuture.onFailure({
    case ex => println(ex)
  })
}
