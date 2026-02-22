package farbpunkte;

import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GLabel;
import acm.program.GraphicsProgram;

public class BasicEntro extends GraphicsProgram {

	int[] gitter = new int[100];

	public void entro3() {
		for (int i = 0; i < gitter.length; i++) {
			for (int j = 0; j < gitter[i]; j++) {
				double rand = Math.random();
				if (rand < 0.25 && i > 0) {
					gitter[i]--;
					gitter[i - 1]++;
				} else if (rand > 0.75 && i < gitter.length - 1) {
					gitter[i]--;
					gitter[i + 1]++;
				}
			}
		}
	}
	
	public void entro4(int iter) {
		
		for (int i = 0; i < gitter.length; i++) {
			for (int j = 0; j < gitter[i]; j++) {
				double rand = Math.random();
//				if (rand < 0.2 && i > 10) {
//					gitter[i]--;
//					gitter[i - 10]++;
//				}
//				else if (rand < 0.4 && i > 0) {
//					gitter[i]--;
//					gitter[i - 1]++;
//				} 
//				else 
					if (rand < 0.6 && i < gitter.length - 1) {
					gitter[i]--;
					gitter[i + 1]++;
				}
				else if (rand > 0.8 && i < gitter.length - 10) {
					gitter[i]--;
					gitter[i + 10]++;
				}
			}
		}
	}
	
	//Fluss 1
	public void entro1() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				int zahl=gitter[(i * 10 + j)];
				for (int k = 0; k < zahl; k++) {
					double rand = Math.random();
//					if (rand < 0.25 && (i * 10 + j) > 0) {
//						gitter[i * 10 + j]--;
//						gitter[i * 10 + j - 1]++;
//					} else 
						if (rand > 0.75 && (i * 10 + j) < gitter.length - 1) {
						gitter[(i * 10 + j)]--;
						gitter[i * 10 + j + 1]++;
					}
				}
			}
		}
	}

	public void entro2() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				int zahl=gitter[(i * 10 + j)];
				for (int k = 0; k < zahl; k++) {
					double rand = Math.random();
					if (rand < 0.25 && (i * 10 + j) > 0) {
						gitter[i * 10 + j]--;
						gitter[i * 10 + j - 1]++;
					} else if (rand > 0.75 && (i * 10 + j) < gitter.length - 1) {
						gitter[(i * 10 + j)]--;
						gitter[i * 10 + j + 1]++;
					}
				}
			}
		}
	}

	public void draw1() {
		removeAll();
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				GLabel num = new GLabel(String.valueOf(gitter[i * 10 + j]));
				num.setFont(num.getFont().deriveFont(20.0f));
				num.setColor(new Color(0, 253, 0));
				add(num, j * 100 + 30, i * 100 + 30);
			}
		}
	}

	public void mousePressed(final MouseEvent m) {
		entro4(100);
		draw1();
	}

	public void run() {

		gitter[0] = 1024*1042;
//		gitter[25] = 20000000;
//		gitter[45] = 20000000;
//		gitter[45] = 22000000;
		
		
		
		setSize(1200, 1200);
		draw1();
		setBackground(new Color(10, 10, 10));
		System.out.println("Hello");
		addMouseListeners();
		// urknall();
	}

	public static void main(String[] args) {
		new BasicEntro().start(args);
	}
}
