package com.github.cmanou.zpl.utils.commands.barcodes

import com.github.cmanou.zpl.utils.commands.{PrintableCommand, FieldData}
import com.github.cmanou.zpl.utils.commands.Commands._
import com.github.cmanou.zpl.utils.constants.Orientation
import com.github.cmanou.zpl.utils.Implicits._

case class Code11(data: String, orientation: Orientation, checkDigit: Boolean, height: Int, interpretationLine: Boolean, interpretationLineAbove: Boolean) extends PrintableCommand{
  def zpl =
    s"""^B1${orientation.code},${checkDigit.zpl},$height,${interpretationLine.zpl},${interpretationLineAbove.zpl}
        |${FieldData(data).zpl}${FieldSeparator.zpl}""".stripMargin
}