package com.github.cmanou.zpl.utils.commands.barcodes

import com.github.cmanou.zpl.utils.Implicits._
import com.github.cmanou.zpl.utils.commands.Commands._
import com.github.cmanou.zpl.utils.commands.{FieldData, PrintableCommand}
import com.github.cmanou.zpl.utils.constants.Orientation

case class UPCE(data: String, orientation: Orientation, height: Int, interpretationLine: Boolean, interpretationLineAbove: Boolean, checkDigit: Boolean) extends PrintableCommand{
  def zpl =
    s"""^B9${orientation.code},$height,${interpretationLine.zpl},${interpretationLineAbove.zpl},${checkDigit.zpl}
        |${FieldData(data).zpl}${FieldSeparator.zpl}""".stripMargin
}