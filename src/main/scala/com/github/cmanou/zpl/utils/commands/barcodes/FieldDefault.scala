package com.github.cmanou.zpl.utils.commands.barcodes

import com.github.cmanou.zpl.utils.commands.PrintableCommand

case class FieldDefault(width: Int = 2, ratio: Double = 3.0, height: Int = 10) extends PrintableCommand{
  def zpl = s"^BY$width,$ratio,$height"
}