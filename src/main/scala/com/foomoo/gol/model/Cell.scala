package com.foomoo.gol.model

case class Cell(x: Int, y: Int) {
  def neighbouringCells: Seq[Cell] = {
    for {
      x <- -1 to 1
      y <- -1 to 1
      if ((x != 0) || (y != 0))
    } yield Cell(x + this.x, y + this.y)
  }
}