package com.github.cmanou.zpl.utils.commands


case class FieldOrigin(x:Int, y: Int) extends PrintableCommand{
  def zpl = s"^FO$x,$y"
}
