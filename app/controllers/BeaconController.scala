package controllers

import javax.inject.Inject
import models.Beacon
import play.api.libs.functional.syntax._
import play.api.libs.json.{JsPath, Json, Writes}
import play.api.mvc._

@Singleton
class BeaconController @Inject()(components: ControllerComponents)
  extends AbstractController(components) {

  case class BeaconResult(existsBeaconFindBySerial: Boolean,
                          existsBeaconFindByBLEAddress: Boolean,
                          existsVisualInspectionDefectiveAt: Boolean = false)

  def readFinishedProductInspection(serial: String, bleAddress: String): List[BeaconResult] = {
    val beaconFindBySerialAndBLEAddress = Beacon.findBySerialAndBLEAddress(serial, bleAddress).getOrElse(None)
    val beaconFindBySerial = Beacon.findBySerial(serial).getOrElse(None)
    val beaconFindByBLEAddress = Beacon.findByBLEAddress(bleAddress).getOrElse(None)

    if (beaconFindBySerialAndBLEAddress.isDefined) {
      List(BeaconResult(true, true))
    } else if (beaconFindBySerial.isDefined) {
      List(BeaconResult(true, false))
    } else if (beaconFindByBLEAddress.isDefined) {
      beaconFindByBLEAddress.get.visualInspectionDefectiveAt match {
        case Some(_) => List(BeaconResult(true, false, true))
        case None => List(BeaconResult(true, false, false))
      }
    } else {
      List(BeaconResult(false, false))
    }
  }

  implicit val beaconResultWrites: Writes[BeaconResult] = (
    (JsPath \ "existsBeaconFindBySerial").write[Boolean] and
      (JsPath \ "existsBeaconFindByBLEAddress").write[Boolean] and
      (JsPath \ "existsVisualInspectionDefectiveAt").write[Boolean]
    ) (unlift(BeaconResult.unapply))

  def index(serial: String, bleAddress: String) = Action { implicit request: Request[AnyContent] =>
    val json = Json.toJson(readFinishedProductInspection(serial, bleAddress))
    Ok(json)
  }


}