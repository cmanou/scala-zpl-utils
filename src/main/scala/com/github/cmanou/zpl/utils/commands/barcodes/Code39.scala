package com.github.cmanou.zpl.utils.commands.barcodes

import com.github.cmanou.zpl.utils.commands.{FieldData, PrintableCommand}
import com.github.cmanou.zpl.utils.commands.Commands._
import com.github.cmanou.zpl.utils.Implicits._
import com.github.cmanou.zpl.utils.constants.Orientation

case class Code39(data: String, orientation: Orientation, checkDigit: Boolean, height: Int, interpretationLine: Boolean, interpretationLineAbove: Boolean) extends PrintableCommand{
  def zpl =
    s"""^B3${orientation.code},${checkDigit.zpl},$height,${interpretationLine.zpl},${interpretationLineAbove.zpl}
        |${FieldData(data).zpl}${FieldSeparator.zpl}""".stripMargin
}