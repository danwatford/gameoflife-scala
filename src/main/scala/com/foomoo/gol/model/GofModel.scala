package com.foomoo.gol.model

/**
 * Represents the alive cells in a generation of Game Of Life.
 */
class GofModel (cells: Set[Cell]) {

  /**
   * Given a cell, return the neighbouring live cells.
   */
  def liveNeighbours(cell: Cell): Seq[Cell] = {
    cell.neighbouringCells filter(cells.contains(_))
  }
  
  /**
   * Return a set of dead cells which have a live cell as a neighbour.
   */
  def deadCellsWithLiveNeighbours(): Set[Cell] = cells flatMap(_.neighbouringCells) toSet 
  
//  def tick(): GofModel = {
//    // Build a set of all dead cells which are neighbours to live cells.
//    // These will be candidate cells to become live if they have exactly 3 live neighbours.
//    val candidateCellsForLife = 
//    
//    val mutMap: scala.collection.mutable.HashMap = 
//  }
}

object GofModel {
  def apply(cells: Cell*): GofModel = {
    new GofModel(cells.toSet)
  }
}

case class Cell(x: Int, y: Int) {
  def neighbouringCells: Seq[Cell] = {
    for {
      x <- -1 to 1
      y <- -1 to 1
      if ((x != 0) || (y != 0))
    } yield Cell(x + this.x, y + this.y)
  }
}