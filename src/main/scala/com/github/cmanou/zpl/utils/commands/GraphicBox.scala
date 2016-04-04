package com.github.cmanou.zpl.utils.commands

import com.github.cmanou.zpl.utils.constants.Colour

case class GraphicBox(width: Int, height: Int, thickness: Int, colour: Colour = Colour.Black, radius: Int = 0) extends PrintableCommand{
  def zpl = s"^GB$width,$height,$thickness,${colour.code},$radius"
}