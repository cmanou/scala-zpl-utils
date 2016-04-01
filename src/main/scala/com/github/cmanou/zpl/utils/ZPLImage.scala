package com.github.cmanou.zpl.utils

import com.sksamuel.scrimage.filter.DitherFilter
import com.sksamuel.scrimage.{Color, Image, PixelTools}

import scala.annotation.tailrec


case class ZPLImage(image: Image, dpmm: Int = 8) {
  def toZPL(preferredWidth: Int, compressed: Boolean = true) =  {
    val preferredHeight = (image.height.toFloat / image.width * preferredWidth).toInt

    val bwResizedImage = image.scaleTo(preferredWidth * dpmm, preferredHeight * dpmm)
                              .removeTransparency(Color.White)
                              .filter(DitherFilter)


    val data = getImageHex(bwResizedImage, compressed)

    val totalBytes = (Math.ceil(preferredWidth * dpmm /8.0)* preferredHeight * dpmm).toInt
    val bytesPerRow = Math.ceil(preferredWidth * dpmm /8.0).toInt


    println(preferredWidth)
    println(preferredHeight)
    println(totalBytes)
    println(bytesPerRow)
    println(bwResizedImage.width)
    println(bwResizedImage.height)
    println(data.length)

    "^GFA,%d,%d,%d,%s".format(totalBytes,totalBytes,bytesPerRow,data)
  }

  def getImageHex(image: Image, compressed: Boolean) = {
    val newImage = image.scaleTo(image.width/8, image.height)

    val arr = Array.ofDim[Byte](newImage.height,newImage.width)

    for (y <- 0 until newImage.height; x <- 0 until newImage.width) {
      val gray = PixelTools.gray(newImage.pixel(x,y).toInt)
      arr(y)(x) = (255 - gray).toByte
    }

    val lines: List[String] = arr.map(_.map("%02X".format(_)).mkString).toList

    compressed match {
      case false => lines.mkString
      case true => ZPLImage.compressDuplicateLines(lines.map(ZPLImage.compressDuplicateChars)).mkString
    }
  }

}

@tailrec
object ZPLImage {
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
      case ((i,x), index) if i < 3 => List.fill(i)(x)
      case ((i,x), index) => getMultiplier(i) ::: ( x :: Nil)
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
