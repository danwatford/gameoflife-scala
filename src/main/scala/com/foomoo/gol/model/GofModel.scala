package com.foomoo.gol.model

import scala.language.postfixOps

/**
 * Represents the live cells in a generation of Game Of Life.
 */
class GofModel(cells: Set[Cell], cellFilter: Cell => Boolean) {

  /**
   * Returns the set of cells currently live in this model and valid according to the cellFilter.
   */
  lazy val getCells: Set[Cell] = cells.filter(cellFilter)

  /**
   * Given a cell, return the neighbouring live cells.
   */
  def liveNeighbours(cell: Cell): Seq[Cell] = {
    cell.neighbouringCells filter (cells.contains(_))
  }

  /**
   * The set of dead cells which have a live cell as a neighbour.
   */
  lazy val deadCellsWithLiveNeighbours: Set[Cell] = (getCells flatMap (_.neighbouringCells) toSet) filter cellFilter

  /**
   * A new GofModel representing all cells that are live after the tick.
   */
  lazy val tick: GofModel = {
    val keepAliveCells = getCells.filter { cell =>
      val liveNeighbourCount = liveNeighbours(cell).size
      liveNeighbourCount == 2 || liveNeighbourCount == 3
    }

    val bringToLifeCells = deadCellsWithLiveNeighbours.filter(liveNeighbours(_).size == 3)

    new GofModel(keepAliveCells ++ bringToLifeCells, cellFilter)
  }

  def toggleCell(cell: Cell): GofModel = {
    if (getCells.contains(cell)) new GofModel(getCells - cell, cellFilter)
    else new GofModel(getCells + cell, cellFilter)
  }
}

object GofModel {
  def apply(cells: Cell*): GofModel = new GofModel(cells.toSet, (_ => true))
  def apply(cellFilter: Cell => Boolean, cells: Cell*): GofModel = new GofModel(cells.toSet, cellFilter)
}
