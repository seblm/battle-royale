package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json.Json
import play.api.libs.json.Json._
import scala.concurrent.Future
import play.api.libs.ws.WS
import org.jsoup.Jsoup
import org.jsoup.nodes.{Element, Document}
import org.jsoup.select.Elements
import scala.concurrent.ExecutionContext.Implicits.global

object Application extends Controller {

  case class Category(label:String, identifier:String)
  implicit val categoryFormats =Json.format[Category]

  def regions=Action.async {
    import scala.collection.JavaConversions._

    def extractRegions(document:Document):List[String]={
      val select: Elements = document.select(".CountyList a")
      select.map {el : Element =>el.attr("href").split("/").last }.toList
    }
    val body: Future[String] = WS.url("http://www.leboncoin.fr").get().map(r => r.body)
    val json=body.map(Jsoup.parse).map(extractRegions).map(r=>toJson(r))
    json.map(body=>Ok(body))
  }
  def categories=Action.async {
    import scala.collection.JavaConversions._

    def extractCategories(document:Document):List[Category]={
      val select: Elements = document.select("#categories_container a")
      def identifier(el: Element) =el.attr("href").replaceAll("/offres/.*","").split("/").last
      def label(el:Element)=el.text()
      select.map(el=> Category(identifier(el),label(el))).toList
    }
    val body: Future[String] = WS.url("http://www.leboncoin.fr/annonces").get().map(r => r.body)
    val json=body.map(Jsoup.parse).map(extractCategories).map(r=>toJson(r))
    json.map(body=>Ok(body))
  }
}