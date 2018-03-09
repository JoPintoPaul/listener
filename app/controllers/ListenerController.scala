package controllers

import play.api.libs.json._
import play.api.mvc.{ Action, AnyContent, Controller }

class ListenerController extends Controller {

  def listen: Action[AnyContent] = Action { implicit request =>
    request.body.asJson match {
      case Some(json) => json.validate[CallbackBody] match {
        case JsSuccess(callback, _) =>
          println("********************************************************************")
          println(s"File upload notification received on callback URL. File reference: ${callback.reference}, " +
            s"file download URL: ${callback.downloadUrl}")
          println("********************************************************************")
          Ok("")
        case _: JsError =>
          println("********************************************************************")
          println(s"JSON of callback not in expected format, JSON body is: $json")
          println("********************************************************************")
          BadRequest("")
      }
      case None =>
        println("********************************************************************")
        println(s"Request body cannot be parsed as JSON, request body is: ${request.body.toString}")
        println("********************************************************************")
        BadRequest("")
    }
  }

  def home: Action[AnyContent] = Action { implicit request =>
    Ok(views.html.main())
  }
}

case class CallbackBody(reference: String, downloadUrl: String)

object CallbackBody {
  implicit val formats: Format[CallbackBody] = Json.format[CallbackBody]
}