package com.foomoo.exp.ui;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;

import com.foomoo.exp.ui.OnOffGridJ7.OnOffGridListener;
import com.foomoo.exp.ui.OnOffGridJ7.OnOffGridModel;

public class J7GridTestHarness {
	static class Cell {
		final int x;
		final int y;

		public Cell(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public int hashCode() {
			return x + y * 10000;
		}

		public boolean equals(Object obj) {
			if (obj instanceof Cell) {
				Cell that = (Cell) obj;
				return (this.x == that.x) && (this.y == that.y);
			}
			return false;
		}

		public String toString() {
			return "Cell(" + x + "," + y + ")";
		}
	}

	public static void main(String[] args) {
		final Set<Cell> cells = new HashSet<>();
		for (int i = 0; i < 20; i++) {
			cells.add(new Cell(i, i));
		}

		OnOffGridJ7 onOffGridJ7 = new OnOffGridJ7(15, 20);
		onOffGridJ7.setModel(new OnOffGridModel() {

			public boolean getCellState(int x, int y) {
				return cells.contains(new Cell(x, y));
			}
		});

		final JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(onOffGridJ7);
		frame.pack();
		frame.setVisible(true);

		onOffGridJ7.addOnOffGridListener(new OnOffGridListener() {

			@Override
			public void cellClicked(int x, int y) {
				Cell clickedCell = new Cell(x, y);

				if (cells.contains(clickedCell)) {
					cells.remove(clickedCell);
				} else {
					cells.add(clickedCell);
				}
				frame.repaint();
			}
		});
	}
}
