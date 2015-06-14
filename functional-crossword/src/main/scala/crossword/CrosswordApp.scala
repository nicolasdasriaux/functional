package crossword

import io.Source

import scala.collection.breakOut

object CrosswordApp extends App {
  val allWords = Source.fromInputStream(getClass.getResourceAsStream("words.txt"))
      .getLines()
      .toSeq

  val words = allWords.view
      .filter(_.size > 1)
      .filterNot(w => w.contains('-') || w.contains('(') || w.contains(')'))
      .force

  type Size = Int
  type Word = String
  type Letter = Char
  type Position = Int
  type -->:[K, V] = Map[K, V]
  
  /**
  Groupe un séquence de mots par taille

  Le résultat est une map avec :
  - pour entrée une '''taille''
  - et pour valeur un '''ensemble de mots''' ayant cette taille.
  */
  def groupedBySize(words: Seq[Word]): Size -->: Set[Word] = {
    /*
    TODO Implémenter la fonction

    INDICES

    - Méthode `groupBy` de `Seq`
    - Méthode `toSet` de `Seq`

    [[http://www.scala-lang.org/api/2.11.6/#scala.collection.immutable.Seq]]

    - Méthode `map` de `Map`

    [[http://www.scala-lang.org/api/2.11.6/#scala.collection.immutable.Map]]

    Fonction partielle
    */

    ???
  }

  /**
  Groupe un ensemble de mots par la lettre figurant à une position donnée

  Le résultat est une map avec :
  - pour entrée une '''lettre'''
  - et pour valeur un '''ensemble de mots'''
    ayant cette lettre à cette position.

  Pour une lettre donnée, si aucun mot ne correspond,
  la map doit retourner un ensemble vide.
  */
  def groupedByLetter(words: Set[Word], position: Position): Letter -->: Set[Word] = {
    /*
    TODO Implémenter la fonction

    INDICES

    - Méthode `groupBy` de `Seq`
    - Méthode `empty` de `Seq`
    
    [[http://www.scala-lang.org/api/2.11.6/#scala.collection.immutable.Set]]

    - Méthode `withDefaultValue` de `Map`

    [[http://www.scala-lang.org/api/2.11.6/#scala.collection.immutable.Map]]
    */

    ???
  }

  /**
  Etant de donné une ensemble de mots de même taille,
  groupe les mots par position et lettre à cette position

  Le résultat est une map ayant :

  - pour entrée une '''position'''

  - et pour valeur une map ayant
    - pour une entrée une '''lettre'''
    - et pour valeur un '''ensemble de mots'''
      ayant cette lettre à cette position
  */
  def groupedByPositionThenLetter(size: Size, wordsForSize: Set[Word]): Position -->: Letter -->: Set[Word] = {
    /*
    TODO Implémenter la fonction

    INDICES

    Parcourir (`for`) l'ensemble des positions possibles
    et générer (`yield`) des entrées de map (opérateur `->`) avec
    - pour entrée la position
    - et pour valeur les mots groupés par lettre à cette position.

    Convertir la séquence indexée d'entrée de map obtenue en une map

    Méthode `toMap` de `IndexedSeq`
    http://www.scala-lang.org/api/2.11.6/#scala.collection.immutable.IndexedSeq
    */

    ???
  }

  /**
  Etant de donné une ensemble de mots,
  groupe les mots par taille puis par position et lettre à cette position

  Le résulat est une map ayant :
  
  - pour entrée une '''taille'''

  - et pour valeur une map ayant
    - pour entrée une '''position'''

    - et pour valeur une map ayant
      - pour une entrée une '''lettre'''
      - et pour valeur un '''ensemble de mots'''
        ayant cette taille et cette lettre à cette position.
  */

  def groupedBySizeThenPositionThenLetter(words: Seq[Word]): Size -->: Position -->: Letter -->: Set[Word] = {
    /*
    TODO Implémenter la fonction

    INDICES

    Parcourir (`for`) les mots groupés par taille
    et générer (`yield`) des entrées de map (opérateur `->`) avec
    - pour entrée la taille
    - et pour valeur les mots groupés par position et lettre à cette position.
    */

    ???
  }

  val wordsBySizeThenPositionThenLetter = groupedBySizeThenPositionThenLetter(words)

  /**
  Etant donnés la taille
  et les positions pour lesquelles la lettre est connue,
  détermine l'ensemble des mots qui correspondent
  */
  def matchingWords(size: Size, knownPositions: Map[Position, Letter]): Set[Word] = {
    /*
    TODO Implémenter la fonction

    INDICES

    Parcourir (`for`) les positions connues
    et générer (`yield`) les ensembles de mots
    correspondants à la taille, la position, et la lettre

    Déterminer l'intersection de tous les ensembles de la séquence

    - Méthode `foldLeft` de `Set`
    - Méthode `empty`de `Set`
    - Méthode `intersect` de `Set`

    [[http://www.scala-lang.org/api/2.11.6/#scala.collection.immutable.Set]]
    */

    ???
  }

  val w1 = matchingWords(11, Map(1 -> 'a', 4 -> 'b'))
  val w2 = matchingWords(15, Map(1 -> 'a', 7 -> 'e'))
  val w3 = matchingWords(7, Map(0 -> 'a', 2 -> 'e', 6 -> 'e'))

  println(w1)
  println(w2)
  println(w3)
}
