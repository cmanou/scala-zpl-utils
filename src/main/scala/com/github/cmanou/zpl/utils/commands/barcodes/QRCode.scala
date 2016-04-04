package com.github.cmanou.zpl.utils.commands.barcodes

import com.github.cmanou.zpl.utils.Implicits._
import com.github.cmanou.zpl.utils.commands.Commands._
import com.github.cmanou.zpl.utils.commands.{FieldData, PrintableCommand}
import com.github.cmanou.zpl.utils.constants.Orientation

case class QRCode(data: String, orientation: Orientation, model: Int, magnification: Int, HQML: Char, NABK: Int) extends PrintableCommand{
  def zpl =
    s"""^BQ${orientation.code},$model,$magnification,$HQML,$NABK
       |${FieldData(data).zpl}${FieldSeparator.zpl}""".stripMargin
}