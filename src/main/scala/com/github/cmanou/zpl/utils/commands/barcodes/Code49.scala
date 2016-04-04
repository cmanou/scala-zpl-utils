package com.github.cmanou.zpl.utils.commands.barcodes

import com.github.cmanou.zpl.utils.commands.{FieldData, PrintableCommand}
import com.github.cmanou.zpl.utils.commands.Commands._
import com.github.cmanou.zpl.utils.Implicits._
import com.github.cmanou.zpl.utils.constants.Orientation

case class Code49(data: String, orientation: Orientation, height: Int, interpretationLine: Boolean, startingMode: Char) extends PrintableCommand{
  def zpl =
    s"""^B4${orientation.code},$height,${interpretationLine.zpl},$startingMode
       |${FieldData(data).zpl}${FieldSeparator.zpl}""".stripMargin
}