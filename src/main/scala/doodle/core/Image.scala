package doodle.core

import doodle.backend.Canvas

/**
  * Created by liyuanhao on 9/27/16.
  */
sealed trait Image {
  def on(that: Image): Unit = {
    On(this, that)
  }

  def above(that: Image) = {
    Above(this, that)
  }

  def beside(that: Image) = {
    Beside(this, that)
  }

  def draw(canvas: Canvas, originX: Double, originY: Double): Unit = {
    this match {
      case Circle(r) => {
        canvas.circle(0.0, 0.0, r)
        canvas.setStroke(Stroke(1.0, Color.red, Line.Cap.Round, Line.Join.Round))
        canvas.stroke()
      }
      case Rectangle(w, h) => {
        canvas.rectangle(0.0, 0.0, w, h)
        canvas.setStroke(Stroke(1.0, Color.darkBlue, Line.Cap.Round, Line.Join.Round))
        canvas.stroke()
      }
      case Beside(l, r) => {
        val box = this.boundingBox
        val lBox = l.boundingBox
        val rBox = r.boundingBox
        val leftOriginX = originX + box.left + (lBox.width / 2)
        val rightOriginX = originX + box.right - (rBox.width / 2)
        l.draw(canvas, leftOriginX, originY)
        r.draw(canvas, rightOriginX, originY)
      }
      case Above(a, b) => {
        val box = this.boundingBox
        val aBox = a.boundingBox
        val bBox = b.boundingBox
        val aboveOriginY = originY + box.top - (aBox.height / 2)
        val belowOriginY = originY + box.bottom + (bBox.height / 2)
        a.draw(canvas, originX, aboveOriginY)
        b.draw(canvas, originX, belowOriginY)
      }
      case On(o, u) => {
        o.draw(canvas, originX, originY)
        u.draw(canvas, originX, originY)
      }
    }
  }

  val boundingBox: BoundingBox =
    this match {
      case Circle(r) => BoundingBox(-r, r, r, -r)
      case Rectangle(w, h) => BoundingBox(-w / 2, h / 2, w / 2, -h / 2)
      case Beside(l, h) => l.boundingBox beside h.boundingBox
      case Above(a, b) => a.boundingBox above b.boundingBox
      case On(o, u) => o.boundingBox on u.boundingBox
    }
}

final case class Circle(radius: Double) extends Image {
}
final case class Rectangle(width: Double, height: Double) extends Image
final case class Above(above: Image, below: Image) extends Image
final case class Beside(left: Image, right: Image) extends Image
final case class On(on: Image, under: Image) extends Image

//package doodle
//package core
//import doodle.backend.Canvas
//sealed trait Image {
//  def on(that: Image): Image =
//    ???
//  def beside(that: Image): Image = ???
//  def above(that: Image): Image = ???
//  def draw(canvas: Canvas): Unit = this match {
//    case Circle(r) => canvas.circle(0.0, 0.0, r)
//    case Rectangle(w,h) => canvas.rectangle(-w/2, h/2, w/2, -h/2) }
//}
//final case class Circle(radius: Double) extends Image
//final case class Rectangle(width: Double, height: Double) extends Image