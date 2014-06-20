package com.foomoo.exp.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class OnOffGridJ7 extends JPanel implements MouseListener {
	private int cellsX;
	private int cellsY;
	private int cellWidth;
	private int cellHeight;

	private OnOffGridModel model;

	private ArrayList<OnOffGridListener> listeners = new ArrayList<>();

	public OnOffGridJ7(int cellsX, int cellsY) {
		this(cellsX, cellsY, 20, 20);
	}

	public OnOffGridJ7(int cellsX, int cellsY, int cellWidth, int cellHeight) {
		this.cellsX = cellsX;
		this.cellsY = cellsY;
		this.cellWidth = cellWidth;
		this.cellHeight = cellHeight;
		setPreferredSize(new Dimension(cellsX * cellWidth, cellsY * cellHeight));

		// Set a default model
		setModel(new OnOffGridModel() {

			public boolean getCellState(int x, int y) {
				return false;
			}
		});

		addMouseListener(this);
	}

	public void paint(Graphics g2) {
		Graphics2D g = (Graphics2D) g2;
		for (int x = 0; x < cellsX; x++) {
			for (int y = 0; y < cellsY; y++) {
				boolean on = model.getCellState(x, y);
				Rectangle r = new Rectangle(x * cellWidth, y * cellHeight,
						cellWidth, cellHeight);
				g.setColor(on ? Color.BLACK : Color.WHITE);
				g.draw(r);
				g.fill(r);
			}
		}
	}

	public void setModel(OnOffGridModel model) {
		this.model = model;
	}

	public void addOnOffGridListener(OnOffGridListener l) {
		listeners.add(l);
	}

	public void mouseClicked(MouseEvent e) {
		notifyCellClicked(e.getX() / cellWidth, e.getY() / cellHeight);
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	private void notifyCellClicked(int x, int y) {
		for (OnOffGridListener listener : listeners) {
			listener.cellClicked(x, y);
		}
	}

	interface OnOffGridModel {
		boolean getCellState(int x, int y);
	}

	interface OnOffGridListener {
		void cellClicked(int x, int y);
	}
}
