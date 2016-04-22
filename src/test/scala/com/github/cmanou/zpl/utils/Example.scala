package com.github.cmanou.zpl.utils

import java.io.File

import com.github.cmanou.zpl.utils.commands._
import com.github.cmanou.zpl.utils.Implicits._
import com.github.cmanou.zpl.utils.constants.Orientation
import com.sksamuel.scrimage.Image
import com.github.cmanou.zpl.utils.commands.Commands._
import com.sksamuel.scrimage.filter.DitherFilter

object Example {
  def main(args: Array[String]) {
    val image = Image.fromStream(getClass.getResourceAsStream("/premonition_rgb.png")).filter(DitherFilter)
    val gf = GraphicField(image, image.width*3, image.height*3)
    val padding = 10

    //For 4 inch label
    val zplDocument = List(
      StartFormat,
      FieldOrigin(812 - gf.width - padding,padding),
      gf,
      FieldSeparator,
      barcodes.FieldDefault(width = 3),
      FieldOrigin(100,100),
      barcodes.EAN8("1234567",Orientation.Normal,100,true,false),
      FieldOrigin(100,300),
      barcodes.UPCE("1230000045",Orientation.Normal,100,true,false,true),
      FieldOrigin(100,500),
      barcodes.Code93("12345ABCDE",Orientation.Normal,100,true,false,false),
      FieldOrigin(100,700),
      barcodes.Code128("123456",Orientation.Normal,100,true,false,false, 'N'),
      FieldOrigin(100,900),
      barcodes.UPSMaxiCode("123456",4,1,1),
      FieldOrigin(100,1200),
      barcodes.EAN13("12345678",Orientation.Normal,100,true,false),
      EndFormat
    )

    println(zplDocument.zpl("\n"))
  }
}
