package com.github.cmanou.zpl.utils.commands

import com.github.cmanou.zpl.utils.constants.Colour


case class GraphicCircle(diameter: Int, thickness: Int = 1, colour: Colour = Colour.Black) extends PrintableCommand{
  def zpl = s"^GC$diameter,$thickness,${colour.code}"
}