package model

import scala.language.higherKinds

import slick.driver.H2Driver.api._

import Orders.orders

case class Customer(id: Int, firstName: String, lastName: String, age: Option[Int])

class Customers(tag: Tag) extends Table[Customer](tag, "CUSTOMERS") {
  def id = column[Int]("ID", O.PrimaryKey)
  def firstName = column[String]("FIRST_NAME")
  def lastName = column[String]("LAST_NAME")
  def age = column[Option[Int]]("AGE")
  def * = (id, firstName, lastName, age) <> (Customer.tupled, Customer.unapply)
}

object Customers {
  val customers = TableQuery[Customers]

  implicit class Extensions[C[_]](customers: Query[Customers, Customer, C]) {
    def byId(id: Int) = customers.filter(_.id === id)
    def withOrders = customers.joinLeft(orders).on(_.id === _.customerId)
  }
}
