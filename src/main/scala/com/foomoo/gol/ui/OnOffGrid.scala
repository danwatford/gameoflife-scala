package com.foomoo.gol.ui

import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics2D
import java.awt.Rectangle
import scala.swing.MainFrame
import scala.swing.Panel
import scala.swing.Publisher
import scala.swing.SimpleSwingApplication
import scala.swing.event.MouseClicked
import scala.swing.event.UIEvent
import javafx.scene.input.MouseButton
import scala.swing.event.MouseButtonEvent
import scala.swing.event.MouseReleased
import java.awt.Point
import scala.swing.event.MousePressed

/**
 * A Panel which will draw a grid of cells as either On or Off according to a given function.
 */
class OnOffGrid(val cellsX: Int, val cellsY: Int, cellWidth: Int = 20, cellHeight: Int = 20) extends Panel with Publisher {
  import OnOffGrid._

  preferredSize = new Dimension(cellsX * cellWidth, cellsY * cellHeight)

  listenTo(mouse.clicks)

  var mouseDownCellX = -1;
  var mouseDownCellY = -1;
  
  reactions += {
    case MousePressed(_, point, _, _, _) =>
      // Capture the cell that the mouse button was pressed in.
      val (cellX, cellY) = pointToCell(point)
      mouseDownCellX = cellX
      mouseDownCellY = cellY
    case MouseReleased(_, point, _, _, _) =>
      // If the cell that the mouse button was released in is the same as where it was pressed, register a toggled cell.
      val (cellX, cellY) = pointToCell(point)
      if (cellX == mouseDownCellX && cellY == mouseDownCellY) publish(CellToggled(this, cellX, cellY))
  }

  def pointToCell(point: Point): (Int, Int) = (point.x / cellWidth, point.y / cellHeight)
  
  /** Function to determine whether a particular cell at position (x, y) should be on (true) or off (false). */
  var onOff: (Int, Int) => Boolean = { (_, _) => false }

  override def paintComponent(g: Graphics2D) {
    for (x <- 0 to cellsX; y <- 0 to cellsY; on = onOff(x, y)) {
      drawCell(g, x, y, on)
    }
  }

  def drawCell(g: Graphics2D, x: Int, y: Int, on: Boolean) {
    val r = new Rectangle(x * cellWidth, y * cellHeight, cellWidth, cellHeight)
    if (on) g.setColor(Color.BLACK)
    else g.setColor(Color.WHITE)

    g.draw(r)
    g.fill(r)
  }
}

/**
 * Event signalling that a grid cell has been toggled.
 */
case class CellToggled(source: OnOffGrid, x: Int, y: Int) extends UIEvent
