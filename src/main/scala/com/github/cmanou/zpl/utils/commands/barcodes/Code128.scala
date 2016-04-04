package com.github.cmanou.zpl.utils.commands.barcodes

import com.github.cmanou.zpl.utils.Implicits._
import com.github.cmanou.zpl.utils.commands.Commands._
import com.github.cmanou.zpl.utils.commands.{FieldData, PrintableCommand}
import com.github.cmanou.zpl.utils.constants.Orientation

case class Code128(data: String, orientation: Orientation, height: Int, interpretationLine: Boolean, interpretationLineAbove: Boolean, checkDigit: Boolean, mode: Char) extends PrintableCommand{
  def zpl =
    s"""^BC${orientation.code},$height,${interpretationLine.zpl},${interpretationLineAbove.zpl},${checkDigit.zpl},$mode
        |${FieldData(data).zpl}${FieldSeparator.zpl}""".stripMargin
}