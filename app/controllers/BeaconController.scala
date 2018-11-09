package controllers

import javax.inject._
import play.api.mvc._

@Singleton
class BeaconController @Inject()(components: ControllerComponents)
  extends AbstractController(components) {

  def echo: Action[AnyContent] = Action { request =>
    val crud = request.queryString("crud").head
    val process = request.queryString("process").head
    val serial = request.queryString("serial").head
    val bleAddress = request.queryString("bleAddress").head
    val ng = request.queryString("ng").head

    Ok(s"crud = $crud",s"process = $process",s"serial = $serial",s"bleAddress = $bleAddress",s"ng = $ng")
  }
}