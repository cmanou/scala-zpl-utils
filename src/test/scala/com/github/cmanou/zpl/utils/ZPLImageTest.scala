package com.github.cmanou.zpl.utils

import com.sksamuel.scrimage.Image
import org.scalatest._

class ZPLImageTest extends FunSuite with BeforeAndAfter with OneInstancePerTest {

  test("to uncompressed is valid") {
    val expected = Image.fromStream(getClass.getResourceAsStream("/premonition_rgb.png"))
    val zplImage = ZPLImage(expected,8)

    println(zplImage.toZPL(50,true))
//    println(zplImage.toZPL(50,false))

    assert( true)
  }
}