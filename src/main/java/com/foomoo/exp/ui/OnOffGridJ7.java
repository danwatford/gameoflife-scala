package com.foomoo.exp.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class OnOffGridJ7 extends JPanel {
	private int cellsX;
	private int cellsY;
	private int cellWidth;
	private int cellHeight;
	
	private Random random = new Random();

	public OnOffGridJ7(int cellsX, int cellsY) {
		this(cellsX, cellsY, 20, 20);
	}

	public OnOffGridJ7(int cellsX, int cellsY, int cellWidth, int cellHeight) {
		this.cellsX = cellsX;
		this.cellsY = cellsY;
		this.cellWidth = cellWidth;
		this.cellHeight = cellHeight;
		setPreferredSize(new Dimension(cellsX * cellWidth, cellsY * cellHeight));
	}

	
	public void paint(Graphics g2) {
		Graphics2D g = (Graphics2D)g2;
		for (int x = 0; x < cellsX; x++) {
			for (int y = 0; y < cellsY; y++) {
				boolean on = random.nextBoolean();
				Rectangle r = new Rectangle(x * cellWidth, y * cellHeight,
						cellWidth, cellHeight);
				g.setColor(on ? Color.BLACK : Color.WHITE);
				g.draw(r);
				g.fill(r);
			}
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new OnOffGridJ7(15, 20));
		frame.pack();
		frame.setVisible(true);
		
	}
}
