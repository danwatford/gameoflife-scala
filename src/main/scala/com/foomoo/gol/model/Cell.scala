package com.foomoo.gol.model

/**
 * Represents a cell position on an x/y grid.
 */
case class Cell(x: Int, y: Int) {
  /**
   * Returns a sequence of neighbouring cell positions to this cell.
   */
  def neighbouringCells: Seq[Cell] = {
    for {
      x <- -1 to 1
      y <- -1 to 1
      if ((x != 0) || (y != 0))
    } yield Cell(x + this.x, y + this.y)
  }
}