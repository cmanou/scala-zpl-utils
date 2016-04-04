package com.github.cmanou.zpl.utils.constants

case class Orientation(code: Char)

object Orientation {
  def Normal = Orientation('N')
  def Rotated = Orientation('R')
  def Inverted = Orientation('I')
  def BottomUp = Orientation('B')
}
