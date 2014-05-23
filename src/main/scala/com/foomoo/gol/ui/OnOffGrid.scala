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

/**
 * A Panel which will draw a grid of cells as either On or Off according to a given function.
 */
class OnOffGrid(val cellsX: Int, val cellsY: Int, cellWidth: Int = 20, cellHeight: Int = 20) extends Panel with Publisher {
  import OnOffGrid._

  preferredSize = new Dimension(cellsX * cellWidth, cellsY * cellHeight)

  listenTo(mouse.clicks)

  reactions += {
    case MouseClicked(_, point, _, _, _) => publish(CellClicked(this, point.x / cellWidth, point.y / cellHeight))
  }

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
 * Event signalling that a grid cell has been clicked.
 */
case class CellClicked(source: OnOffGrid, x: Int, y: Int) extends UIEvent
