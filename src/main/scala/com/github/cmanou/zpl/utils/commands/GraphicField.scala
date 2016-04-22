package com.github.cmanou.zpl.utils.commands

import java.io.File

import com.sksamuel.scrimage.{Color, Image, PixelTools}

case class GraphicField(image: Image, maxWidth: Int, maxHeight: Int, compressed: Boolean = true) extends PrintableCommand {
  def width = math.round(image.width * imageRatio).toInt
  def height = math.round(image.height * imageRatio).toInt

  private def imageRatio = {
    val ratioX = maxWidth.toDouble / image.width
    val ratioY = maxHeight.toDouble / image.height
    math.min(ratioX, ratioY)
  }

  def zpl =  {
    val resizedImage = image.removeTransparency(Color.White)
                            .scaleTo(width, height)

    val imageBytes = GraphicField.getImageAsByteArray(resizedImage)
    val hexData = GraphicField.byteArrayToHexArray(imageBytes)

    val finalData = compressed match {
      case false => hexData.mkString
      case true => GraphicField.compressHexStrings(hexData).mkString
    }

    val bytesPerRow = imageBytes(0).length
    val totalBytes = bytesPerRow * imageBytes.length

    "^GFA,%d,%d,%d,%s".format(totalBytes,totalBytes,bytesPerRow,finalData)
  }
}

object GraphicField {

  def byteArrayToHexArray(bytes: Array[Array[Byte]]) = {
    bytes.map(_.map("%02X".format(_)).mkString).toList
  }

  def getImageAsByteArray(image: Image) = {
    val arr = Array.ofDim[Byte](image.height,math.ceil(image.width.toDouble / 8).toInt)

    for (y <- 0 until image.height; x <- 0 until image.width by 8) {
        var pixelRaw = 0
        for (i <- 0 until 8) {
          if ((x + i) < image.width) {
            val pixel = image.pixel(x + i, y)
            val grey = PixelTools.gray(pixel.toInt)
            val shift = grey match {
              case p if p < 128 => 1
              case _ => 0
            }
            pixelRaw = pixelRaw + (shift * Math.pow(2,7-i).toInt)
          }
        }
        arr(y)(x / 8) = pixelRaw.toByte
    }
    arr
  }

  def compressHexStrings(lines: List[String]) = {
    compressDuplicateLines(lines.map(compressDuplicateChars))
  }

  def getMultiplier(n: Int): List[Char] = {
    n match {
      case i if i >= 400 => 'z' :: getMultiplier(i - 400)
      case i if i >= 380 => 'y' :: getMultiplier(i - 380)
      case i if i >= 360 => 'x' :: getMultiplier(i - 360)
      case i if i >= 340 => 'w' :: getMultiplier(i - 340)
      case i if i >= 320 => 'v' :: getMultiplier(i - 320)
      case i if i >= 300 => 'u' :: getMultiplier(i - 300)
      case i if i >= 280 => 't' :: getMultiplier(i - 280)
      case i if i >= 260 => 's' :: getMultiplier(i - 260)
      case i if i >= 240 => 'r' :: getMultiplier(i - 240)
      case i if i >= 220 => 'q' :: getMultiplier(i - 220)
      case i if i >= 200 => 'p' :: getMultiplier(i - 200)
      case i if i >= 180 => 'o' :: getMultiplier(i - 180)
      case i if i >= 160 => 'n' :: getMultiplier(i - 160)
      case i if i >= 140 => 'm' :: getMultiplier(i - 140)
      case i if i >= 120 => 'l' :: getMultiplier(i - 120)
      case i if i >= 100 => 'k' :: getMultiplier(i - 100)
      case i if i >= 80 => 'j' :: getMultiplier(i - 80)
      case i if i >= 60 => 'i' :: getMultiplier(i - 60)
      case i if i >= 40 => 'h' :: getMultiplier(i - 40)
      case i if i >= 20 => 'g' :: getMultiplier(i - 20)
      case i if i >= 19 => 'Y' :: getMultiplier(i - 19)
      case i if i >= 18 => 'X' :: getMultiplier(i - 18)
      case i if i >= 17 => 'W' :: getMultiplier(i - 17)
      case i if i >= 16 => 'V' :: getMultiplier(i - 16)
      case i if i >= 15 => 'U' :: getMultiplier(i - 15)
      case i if i >= 14 => 'T' :: getMultiplier(i - 14)
      case i if i >= 13 => 'S' :: getMultiplier(i - 13)
      case i if i >= 12 => 'R' :: getMultiplier(i - 12)
      case i if i >= 11 => 'Q' :: getMultiplier(i - 11)
      case i if i >= 10 => 'P' :: getMultiplier(i - 10)
      case i if i >= 9 => 'O' :: getMultiplier(i - 9)
      case i if i >= 8 => 'N' :: getMultiplier(i - 8)
      case i if i >= 7 => 'M' :: getMultiplier(i - 7)
      case i if i >= 6 => 'L' :: getMultiplier(i - 6)
      case i if i >= 5 => 'K' :: getMultiplier(i - 5)
      case i if i >= 4 => 'J' :: getMultiplier(i - 4)
      case i if i >= 3 => 'I' :: getMultiplier(i - 3)
      case i if i >= 2 => 'H' :: getMultiplier(i - 2)
      case i if i >= 1 => 'G' :: getMultiplier(i - 1)
      case _ => Nil
    }
  }

  def compressDuplicateChars(line: String): String = {
    val counted = runLength(line.toCharArray.toList).zipWithIndex
    counted.flatMap(x => x match {
      case ((i,'0'), index) if index == (counted.length - 1) => ',' :: Nil
      case ((i,'1'), index) if index == (counted.length - 1) => '!' :: Nil
      case ((i,v), index) if i < 3 => List.fill(i)(v)
      case ((i,v), index) => getMultiplier(i) ::: ( v :: Nil)
    }).mkString
  }

  def compressDuplicateLines(lines: List[String]): List[String] = {
    runLength(lines).flatMap(x => x._2 :: List.fill(x._1 - 1)(":"))
  }

  def runLength[T](xs: List[T]): List[(Int, T)] = xs match {
    case Nil => List()
    case x :: l => {
      val (front, back) = l.span(_ == x)
      (front.length + 1, x) :: runLength(back)
    }
  }

}
