package com.foomoo.gol.model

import com.foomoo.gol.UnitSpec

class GofModelTest extends UnitSpec {

  def fixture = new {

    val model = {

      GofModel(Cell(0, 0), Cell(2, 2))
    }
  }

  "A GofModel" should "find all live neighbouring cells to a given cell" in {
    val f = fixture

    var liveNeighbours = f.model.liveNeighbours(Cell(0, 1))
    liveNeighbours should have size 1
    liveNeighbours.contains(Cell(0, 0)) should be(true)

    liveNeighbours = f.model.liveNeighbours(Cell(1, 1))
    liveNeighbours should have size 2
    liveNeighbours.contains(Cell(0, 0)) should be(true)
    liveNeighbours.contains(Cell(2, 2)) should be(true)
  }

  it should "find all dead cells neighbouring live cells" in {
    val expectedDead = (Cell(0, 0).neighbouringCells ++ Cell(2, 2).neighbouringCells).toSet
    val f = fixture

    val deadCells = f.model.deadCellsWithLiveNeighbours
    deadCells should have size 15
    deadCells.toSet should equal(expectedDead)
  }
  
  it should "not return any live cells when searching for dead cells with live neighbours" in {
    
  }
}