package controllers

import javax.inject._
import models.{Location, Place}
import play.api._
import play.api.mvc._
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.functional.syntax._

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  implicit val locationWrites: Writes[Location] = {
    new Writes[Location] {
      def writes(location: Location) =
        Json.obj("lat" -> location.lat, "long" -> location.long)
    }
  }

  implicit val placeWrites: Writes[Place] = {
    new Writes[Place] {
      def writes(place: Place) =
        Json.obj("name" -> place.name, "location" -> place.location)
    }
  }

  /**
    * Create an Action to render an HTML page.
    *
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def listPlaces = Action {
    val json = Json.toJson(Place.list)
    Ok(json)
  }

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }
}
