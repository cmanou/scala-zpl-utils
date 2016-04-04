package com.github.cmanou.zpl.utils

import com.github.cmanou.zpl.utils.commands.PrintableCommand

object Implicits {
  implicit class ZPLDocument(document: List[PrintableCommand]) {
    def zpl = document.map(_.zpl).mkString
    def zpl(sep: String) = document.map(_.zpl).mkString(sep)
  }

  implicit class ZPLBoolean(boolean: Boolean) {
    def zpl = boolean match {
      case true => "Y"
      case false => "N"
    }
  }
}
