package com.github.cmanou.zpl.utils.commands.barcodes

import com.github.cmanou.zpl.utils.Implicits._
import com.github.cmanou.zpl.utils.commands.Commands._
import com.github.cmanou.zpl.utils.commands.{FieldData, PrintableCommand}
import com.github.cmanou.zpl.utils.constants.Orientation

case class UPSMaxiCode(data: String, mode: Int, symbol: Int, totalSymbols: Int) extends PrintableCommand{
  def zpl =
    s"""^BD$mode,$symbol,$totalSymbols
        |${FieldData(data).zpl}${FieldSeparator.zpl}""".stripMargin
}