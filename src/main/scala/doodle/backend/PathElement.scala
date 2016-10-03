package doodle.backend

/**
  * Created by liyuanhao on 9/27/16.
  */
sealed trait PathElement
final case class MoveTo(x: Double, y: Double) extends PathElement
final case class LineTo(x: Double, y: Double) extends PathElement
final case class CurveTo(cp1x: Double, cp1y: Double, cp2x: Double, cp2y: Double, endX: Double, endY: Double)
final case class Path(elements: Seq[PathElement])
final case class Image(path: Path)