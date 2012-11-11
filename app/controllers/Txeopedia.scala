package controllers

import play.api._
import play.api.mvc._

import play.api.data._
import play.api.data.Forms._

import models._
import views._

object Txeopedia extends Controller {
  
  /******************************/
  // Forms
  /******************************/
  val categoryCreateForm = Form ("name" -> nonEmptyText)
  
  /******************************/
  // Actions
  /******************************/
  def index = listCategories ("Empezamos")
  
  /******************************/


/**
 * Poz eso
 */
def listCategories (text: String) = Action {

   // Le pasamos la lista de categorías y el formulario de inserción  
   Ok(views.html.index(Category.list(), categoryCreateForm, text))

}

/**
 * Poz eso
 */
def createCategory = Action { implicit request =>
    categoryCreateForm.bindFromRequest.fold(
      errors => BadRequest(views.html.index(Category.list (), errors, "ERROR")),
      category => {
        Category.create(category)
        Redirect (routes.Txeopedia.listCategories("Insertada categoría "+category))
      }
    )
  }

/**
 * Poz eso
 */
def detailCategory (id: Long) = getWeather

  /**
 * Poz eso
 */
def deleteCategory (id: Long) = Action {
    Category.delete (id)
    Redirect (routes.Txeopedia.listCategories ("Borrado elemento con id"+id))
  }

def getWeather = Action {
    import play.api.libs.concurrent.Akka
    import play.api.libs.ws.{WS, Response}
    import play.api.Play.current
    val promiseOfString = Akka.future {
      WS.url("http://weather.yahooapis.com/forecastrss?p=80020&u=f").get().map { response =>
        response.body
      }
    }
    Async {
      promiseOfString.orTimeout("Oops", 5000).map { eitherStringOrTimeout =>
        eitherStringOrTimeout.fold(
          content => Ok(content.value.get),
          timeout => InternalServerError("Timeout Exceeded!")
        )    
      }  
    }
  }
}
