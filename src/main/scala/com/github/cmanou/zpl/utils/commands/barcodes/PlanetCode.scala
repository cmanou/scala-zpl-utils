package com.github.cmanou.zpl.utils.commands.barcodes

import com.github.cmanou.zpl.utils.commands.{FieldData, PrintableCommand}
import com.github.cmanou.zpl.utils.commands.Commands._
import com.github.cmanou.zpl.utils.constants.Orientation
import com.github.cmanou.zpl.utils.Implicits._

case class PlanetCode(data: String, orientation: Orientation, height: Int, interpretationLine: Boolean, interpretationLineAbove: Boolean) extends PrintableCommand{
  def zpl =
    s"""^B5${orientation.code},$height,${interpretationLine.zpl},${interpretationLineAbove.zpl}
        |${FieldData(data).zpl}${FieldSeparator.zpl}""".stripMargin
}