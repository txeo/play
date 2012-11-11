package models

import play.api._
import play.api.mvc._
import scala.collection.mutable.HashMap

import anorm._
import anorm.SqlParser._

import play.api.db._
import play.api.Play.current

// Clase
case class Category (id: Long, name: String)

// Objeto
object Category {
  
    // Atributos
    val categories = new HashMap [Long, Category]
    var id = 0L
    
    // Métodos
    def nexttId():Long = {id +=1; id}
    def create(name: String) = {val id=nexttId; categories+=id -> Category(id, name)}
    def delete(id: Long) {categories -=id}
    def list(): List[Category] = {categories.values.toList} 
}