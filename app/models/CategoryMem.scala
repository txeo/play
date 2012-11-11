package models

import play.api._
import play.api.mvc._
import scala.collection.mutable.HashMap

// Clase
case class CategoryMem (id: Long, name: String)

// Objeto
object CategoryMem {
  
    // Atributos
    val categories = new HashMap [Long, CategoryMem]
    var id = 0L
    
    // Métodos
    def nexttId():Long = {id +=1; id}
    def create(name: String) = {val id=nexttId; categories+=id -> CategoryMem(id, name)}
    def delete(id: Long) {categories -=id}
    def list(): List[CategoryMem] = {categories.values.toList} 
}