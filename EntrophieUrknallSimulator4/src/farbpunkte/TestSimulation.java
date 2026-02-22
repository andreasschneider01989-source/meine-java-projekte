package farbpunkte;

import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GOval;

import acm.program.GraphicsProgram;

public class TestSimulation extends GraphicsProgram {

	/**
	 * Instructions: Start the program and click with the mouse until the simulation
	 * has developed! Anleitung: Starten sie das Programm und klicken sie mit der
	 * Maus so oft bis sich die Simulation entwickelt hat!
	 * 
	 * @author Andreas Schneider (andreas-schneider89@gmx.de)
	 */

	double[][][] objekte = new double[64][64][16];
	GOval[][] kreise = new GOval[64][64];

	double counter = 1.0;

	public void raumzeit2() {
		long time = System.currentTimeMillis();
		int count = 0;

		while (System.currentTimeMillis() - time < 100 && count < counter) {
			count++;

			int i = (int) (Math.random() * 36);
			int j = (int) (Math.random() * 36);
			int k = (int) (Math.random() * 8);
			int i2 = (int) (i * Math.random() + i * Math.random());
			int j2 = (int) (j * Math.random() + j * Math.random());
			int k2 = (int) (k * Math.random() + k * Math.random());

			double anstieg = (objekte[i2 % 64][j2 % 64][k2 % 16] - objekte[i % 64][j % 64][k % 16]) * Math.random();
			if (k == 6)
				anstieg = anstieg * Math.random();
			if (k == 7)
				anstieg *= 2.0;
			if (k2 == 7)
				anstieg *= 2.0;
			if (k2 == 6)
				anstieg = anstieg * Math.random();

			objekte[i % 64][j % 64][k % 16] += anstieg;
			objekte[i2 % 64][j2 % 64][k2 % 16] -= anstieg;
		}

		if (System.currentTimeMillis() - time < 100)
			counter *= 1.1;

		removeAll();
		double r = 0;
		double g = 0;
		double b = 0;
		double x = 0;
		double y = 0;
		double z = 0;
		double z2 = 0;
		for (int i = 0; i < 36; i++) {
			for (int j = 0; j < 36; j++) {
				for (int k = 0; k < 8; k++) {

					objekte[i][j][k] += Math.random();
					objekte[i][j][k] -= Math.random();
					if (k == 0)
						x = objekte[i][j][k];
					else if (k == 2)
						y = objekte[i][j][k];
					else if (k == 4) {
						z = objekte[i][j][k];
						z2 = 5.0 / (objekte[i][j][k] + 0.1);
						x = x - z2 / 2;
						y = y - z2 / 2;
					}

					else if (k == 1) {

						r = (int) (Math.round(objekte[i][j][k]));
					} else if (k == 3)
						g = (int) (Math.round(objekte[i][j][k]));
					else if (k == 5)
						b = (int) (Math.round(objekte[i][j][k]));

					if (r > 255 || g > 255 || b > 255 || x > 2000 || y > 2000 || r < 0 || g < 0 || b < 0 || x < -255
							|| y < -255) {
						count++;

						int i2 = (int) (i * Math.random() + i * Math.random());
						int j2 = (int) (j * Math.random() + j * Math.random());
						int k2 = (int) (k * Math.random() + k * Math.random());

						double anstieg = (objekte[i2 % 64][j2 % 64][k2 % 16] - objekte[i % 64][j % 64][k % 16])
								* Math.random();
						if (k == 6)
							anstieg = anstieg * Math.random();
						if (k == 7)
							anstieg *= 2.0;
						if (k2 == 7)
							anstieg *= 2.0;
						if (k2 == 6)
							anstieg = anstieg * Math.random();

						objekte[i % 64][j % 64][k % 16] += anstieg;
						objekte[i2 % 64][j2 % 64][k2 % 16] -= anstieg;
					}
				}

				r = Math.min(255, r);
				g = Math.min(255, g);
				b = Math.min(255, b);
				r = Math.max(0, r);
				g = Math.max(0, g);
				b = Math.max(0, b);

				Color c = new Color((int) r, (int) g, (int) b);
				kreise[i][j] = new GOval(5, 5);
				kreise[i][j].setFilled(true);
				while (z > 0.1) {
					kreise[i][j].sendBackward();
					z /= 1.5;
				}
				kreise[i][j].setColor(c);
				kreise[i][j].setFillColor(c);

				add(kreise[i][j], (x % 1000) + 300, (y % 1000) + 300);
			}
		}

	}

	public void raumzeit() {
		long time = System.currentTimeMillis();
		int count = 0;

		while (System.currentTimeMillis() - time < 100 && count < counter) {
			count++;

			int i = (int) (Math.random() * 36);
			int j = (int) (Math.random() * 36);
			int k = (int) (Math.random() * 8);
			int i2 = (int) (i * Math.random() + i * Math.random());
			int j2 = (int) (j * Math.random() + j * Math.random());
			int k2 = (int) (k * Math.random() + k * Math.random());

			double anstieg = (objekte[i2 % 64][j2 % 64][k2 % 16] - objekte[i % 64][j % 64][k % 16]) * Math.random();
			if (k == 6)
				anstieg = anstieg * Math.random();
			if (k == 7)
				anstieg *= 2.0;
			if (k2 == 7)
				anstieg *= 2.0;
			if (k2 == 6)
				anstieg = anstieg * Math.random();

			objekte[i % 64][j % 64][k % 16] += anstieg;
			objekte[i2 % 64][j2 % 64][k2 % 16] -= anstieg;
		}

		if (System.currentTimeMillis() - time < 100)
			counter *= 1.1;

		removeAll();
		double r = 0;
		double g = 0;
		double b = 0;
		double x = 0;
		double y = 0;
		double z = 0;
		double z2 = 0;
		for (int i = 0; i < 36; i++) {
			for (int j = 0; j < 36; j++) {
				for (int k = 0; k < 8; k++) {

					objekte[i][j][k] += Math.random();
					objekte[i][j][k] -= Math.random();
					if (k == 0)
						x = objekte[i][j][k];
					else if (k == 2)
						y = objekte[i][j][k];
					else if (k == 4) {
						z = objekte[i][j][k];
						z2 = 5.0 / (objekte[i][j][k] + 0.1);
						x = x - z2 / 2;
						y = y - z2 / 2;
					}

					else if (k == 1) {

						r = (int) (Math.round(objekte[i][j][k]));
					} else if (k == 3)
						g = (int) (Math.round(objekte[i][j][k]));
					else if (k == 5)
						b = (int) (Math.round(objekte[i][j][k]));

					if (r > 255 || g > 255 || b > 255 || x > 2000 || y > 2000 || r < 0 || g < 0 || b < 0 || x < -255
							|| y < -255) {
						count++;

						int i2 = (int) (i * Math.random() + i * Math.random());
						int j2 = (int) (j * Math.random() + j * Math.random());
						int k2 = (int) (k * Math.random() + k * Math.random());

						double anstieg = (objekte[i2 % 64][j2 % 64][k2 % 16] - objekte[i % 64][j % 64][k % 16])
								* Math.random();
						if (k == 6)
							anstieg = anstieg * Math.random();
						if (k == 7)
							anstieg *= 2.0;
						if (k2 == 7)
							anstieg *= 2.0;
						if (k2 == 6)
							anstieg = anstieg * Math.random();

						objekte[i % 64][j % 64][k % 16] += anstieg;
						objekte[i2 % 64][j2 % 64][k2 % 16] -= anstieg;
					}
				}

				r = Math.min(255, r);
				g = Math.min(255, g);
				b = Math.min(255, b);
				r = Math.max(0, r);
				g = Math.max(0, g);
				b = Math.max(0, b);

				Color c = new Color((int) r, (int) g, (int) b);
				kreise[i][j] = new GOval(5, 5);
				kreise[i][j].setFilled(true);
				while (z > 0.1) {
					kreise[i][j].sendBackward();
					z /= 1.5;
				}
				kreise[i][j].setColor(c);
				kreise[i][j].setFillColor(c);

				add(kreise[i][j], (x % 1000) + 300, (y % 1000) + 300);
			}
		}
	}

	double[] doubles = new double[43];

	boolean[] konekts = new boolean[43];

	public void mousePressed(final MouseEvent m) {
		raumzeit();
	}

	public void run() {
		counter = 1;
		setBackground(new Color(10, 10, 10));
		setSize(3000, 3000);
		addMouseListeners();
	}

	public static void main(String[] args) {
		new TestSimulation().start(args);
	}

}
