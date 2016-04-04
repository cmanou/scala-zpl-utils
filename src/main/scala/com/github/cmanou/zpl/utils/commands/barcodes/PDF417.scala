package com.github.cmanou.zpl.utils.commands.barcodes

import com.github.cmanou.zpl.utils.Implicits._
import com.github.cmanou.zpl.utils.commands.Commands._
import com.github.cmanou.zpl.utils.commands.{FieldData, PrintableCommand}
import com.github.cmanou.zpl.utils.constants.Orientation

case class PDF417(data: String, orientation: Orientation, height: Int, security:Int, columns: Int, rows: Int, truncate: Boolean) extends PrintableCommand{
  def zpl =
    s"""^B7${orientation.code},$height,$security,$columns,$rows,${truncate.zpl}
        |${FieldData(data).zpl}${FieldSeparator.zpl}""".stripMargin
}