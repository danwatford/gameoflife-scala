package com.foomoo.gol.model

import scala.language.postfixOps

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
   * The set of dead cells which have a live cell as a neighbour.
   */
  lazy val deadCellsWithLiveNeighbours: Set[Cell] = cells flatMap(_.neighbouringCells) toSet 
  
  /**
   * Perform a tick, returning a new GofModel representing all cells that are live after the tick.
   */
  def tick(): Unit = {
    // Build a set of all dead cells which are neighbours to live cells.
    // These will be candidate cells to become live if they have exactly 3 live neighbours.
    //val candidateCellsForLife = deadCellsWithLiveNeighbours
    
    //val mutMap: scala.collection.mutable.HashMap = 
  }
}

object GofModel {
  def apply(cells: Cell*): GofModel = {
    new GofModel(cells.toSet)
  }
}
