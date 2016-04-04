package com.github.cmanou.zpl.utils.commands


case class FieldData(data: String) extends PrintableCommand{
  def zpl = s"^FD$data"
}
