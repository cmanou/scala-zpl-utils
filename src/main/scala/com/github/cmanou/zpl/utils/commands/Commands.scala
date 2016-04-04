package com.github.cmanou.zpl.utils.commands

object Commands {
  def StartFormat = Raw("^XA")
  def EndFormat = Raw("^XZ")
  def FieldSeparator = Raw("^FS")
}
