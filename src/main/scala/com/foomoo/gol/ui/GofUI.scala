package com.foomoo.gol.ui

import scala.swing.BorderPanel
import scala.swing.Button
import scala.swing.MainFrame
import scala.swing.SimpleSwingApplication
import scala.swing.event.ButtonClicked

import com.foomoo.gol.model.Cell
import com.foomoo.gol.model.GofModel

object GofUI extends SimpleSwingApplication {
  val grid = new OnOffGrid(20, 20) {
    onOff = gridCellIsLive
  }

  val tickButton = new Button {
    text = "Tick"
  }
  
  val randomModelButton = new Button {
    text = "Random"
  }

  def top = new MainFrame {
    title = "Game of Life"

    contents = new BorderPanel {
      layout(grid) = BorderPanel.Position.Center
      layout(tickButton) = BorderPanel.Position.West
      layout(randomModelButton) = BorderPanel.Position.North
    }

    listenTo(tickButton)
    listenTo(randomModelButton)
    listenTo(grid)

    reactions += {
      case ButtonClicked(`tickButton`) =>
        model = model.tick
        grid.repaint
        
      case ButtonClicked(`randomModelButton`) =>
        model = GofModel.random(20, 20)
        grid.repaint

      case CellToggled(`grid`, x, y) =>
        model = model.toggleCell(Cell(x, y))
        grid.repaint
    }
  }

  /**
   * Holds the GoF model for the current generation. The model is changed in response to a 'tick' 
   * or to cells being toggled by the user.
   */
  var model = GofModel(Cell(0, 0), Cell(0, 2), Cell(2, 2))

  /**
   * Function passed to the OnOffGrid to determine whether any particular cell is dead of alive.
   */
  def gridCellIsLive: (Int, Int) => Boolean = { (x, y) => model.getCells contains (Cell(x, y)) }
}