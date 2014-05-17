package com.foomoo.gol.model

import com.foomoo.gol.UnitSpec

class GofModelTest extends UnitSpec {

  "A GofModel" should "find all live neighbouring cells to a given cell" in {
    var liveNeighbours = GofModel(Cell(0, 0), Cell(2, 2)).liveNeighbours(Cell(0, 1))
    liveNeighbours should have size 1
    liveNeighbours should contain(Cell(0, 0))

    liveNeighbours = GofModel(Cell(0, 0), Cell(2, 2)).liveNeighbours(Cell(1, 1))
    liveNeighbours should have size 2
    liveNeighbours should (contain(Cell(0, 0)) and contain(Cell(2, 2)))
  }

  it should "find all dead cells neighbouring live cells" in {
    val expectedDead = (Cell(0, 0).neighbouringCells ++ Cell(2, 2).neighbouringCells).toSet

    val deadCells = GofModel(Cell(0, 0), Cell(2, 2)).deadCellsWithLiveNeighbours
    deadCells should have size 15
    deadCells.toSet should equal(expectedDead)
  }

  it should "not return any live cells when searching for dead cells with live neighbours" in {
    GofModel(Cell(0, 0), Cell(2, 2)).deadCellsWithLiveNeighbours should not(contain(Cell(0, 0)) or contain(Cell(2, 2)))
  }

  it should "filter cells" in {
    GofModel((_ => false), Cell(0, 0), Cell(1, 1), Cell(2, 2)).getCells should be('empty)

    GofModel((_.y > 1), Cell(0, 0), Cell(1, 1), Cell(2, 2)).getCells should not(contain(Cell(0, 0)) or contain(Cell(1, 1)))
  }

  it should "cause a single cell model to an empty model" in {
    GofModel(Cell(0, 0)).tick.getCells should be('empty)
  }

  it should "cause a cell with fewer than two neighbours to die" in {
    GofModel(Cell(0, 0), Cell(0, 1), Cell(0, 2)).tick.getCells should not(contain(Cell(0, 0)) or contain(Cell(0, 2)))
  }

  it should "keep a cell with two neighbours live" in {
    GofModel(Cell(0, 0), Cell(0, 1), Cell(0, 2)).tick.getCells should contain(Cell(0, 1))
  }

  it should "keep a cell with three neighbours live" in {
    GofModel(Cell(0, 0), Cell(0, 2), Cell(1, 1), Cell(2, 2)).tick.getCells should contain(Cell(1, 1))
  }

  it should "kill a cell with four neighbours" in {
    GofModel(Cell(1, 1), Cell(1, 0), Cell(1, 2), Cell(0, 1), Cell(2, 1)).tick.getCells should not contain (Cell(1, 1))
  }

  it should "kill a cell with eight neighbours" in {
    val cell = Cell(1, 1)
    val neighbours = cell.neighbouringCells;
    GofModel(cell +: neighbours: _*).tick.getCells should not contain (Cell(1, 1))
  }

  it should "bring a dead cell with three live neighbours to life" in {
    GofModel(Cell(0, 0), Cell(0, 2), Cell(2, 2)).tick.getCells should contain(Cell(1, 1))
  }

  "An Empty GofModel" should "return no cells" in {
    GofModel().getCells should be('empty)
  }

  it should "return no live neighbours" in {
    GofModel().liveNeighbours(Cell(0, 0)) should be('empty)
  }

  it should "return no dead cells with live neighbours" in {
    GofModel().deadCellsWithLiveNeighbours should be('empty)
  }
}