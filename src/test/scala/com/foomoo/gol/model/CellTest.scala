package com.foomoo.gol.model

import com.foomoo.gol.UnitSpec

class CellTest extends UnitSpec {
  "A Cell" should "have 8 neighbours" in {
    val cell = Cell(0, 0)
    cell.neighbouringCells.size should be(8)
  }

  it should "have neighbours within one unit on each axis" in {
    val cell = Cell(0, 0)
    val neighbouringCells = cell.neighbouringCells
    neighbouringCells.forall { testCell: Cell => math.abs(testCell.x - cell.x) < 2 } should be(true)
    neighbouringCells.forall { testCell: Cell => math.abs(testCell.y - cell.y) < 2 } should be(true)
  }

  it should "not refer to itself as a neighbour" in {
    val cell = Cell(0, 0)
    cell.neighbouringCells.exists { testCell: Cell => cell == testCell } should be(false)
  }
}