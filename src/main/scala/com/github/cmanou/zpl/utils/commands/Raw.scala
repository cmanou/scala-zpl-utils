package com.github.cmanou.zpl.utils.commands

case class Raw(raw: String) extends PrintableCommand{
  def zpl = raw
}
