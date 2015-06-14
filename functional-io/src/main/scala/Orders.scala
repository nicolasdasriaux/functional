package model

import scala.language.higherKinds

import slick.driver.H2Driver.api._

import Customers.customers

case class Order(id: Int, customerId: Int)

class Orders(tag: Tag) extends Table[Order](tag, "ORDERS") {
  def id = column[Int]("ID", O.PrimaryKey)
  def customerId = column[Int]("CUSTOMER_ID")
  def * = (id, customerId) <> (Order.tupled, Order.unapply)

  def customer = foreignKey("CUSTOMER_FK", customerId, customers)(_.id)
}

object Orders {
  val orders = TableQuery[Orders]

  implicit class Extensions[C[_]](query: Query[Orders, Order, C]) {
    def byId(id: Int) = query.filter(_.id === id)
    def withCustomer = query.join(customers).on(_.customerId === _.id)
  }
}
