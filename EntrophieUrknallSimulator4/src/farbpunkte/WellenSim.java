package farbpunkte;

import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.*;
import acm.program.*;

public class WellenSim extends GraphicsProgram {

	public void run() {


		setBackground(new Color(10, 10, 10));
		setSize(2000, 2000);
		// initt();
		addMouseListeners();

//		drawKreisePunkte2(0.2);
		// urknall();
	
		
		 strukt=konstStrukt(2000,2000,2000,2000,2000);
		drawKreisePunkteZArray(1,1,strukt);
		
	}
	
	public void drawKreisePunkteZArray(double zoom, double zoom2, KreisPunkt[] strukt) {
//		KreisPunkt[] strukt = struktsort(this.strukt);
		removeAll();
		for (int i = 0; i < strukt.length; i++) {
			KreisPunkt kp = strukt[i];
			GOval kreis = new GOval(kp.size * zoom2, kp.size * zoom2);
			kreis.setFilled(kp.filled);
			kreis.setFillColor(kp.color);

			// if (kp.nah)
			// kreis.sendForward();
			// if (kp.fern)
			// kreis.sendBackward();

			add(kreis, (kp.posX) * zoom + 900, kp.posY * zoom + 450);

		}
	}
	
	public void pendelnZeit(int iter, KreisPunkt[] strukt) {
		for (int i = 0; i < iter; i++) {

			int kreis = (int) (Math.random() * strukt.length);
			int kreisb = (int) (Math.random() * strukt.length);
			KreisPunkt a = strukt[kreis];
			KreisPunkt b = strukt[kreisb];
			
			
			
			
			a.grav(b);
			a.pendeln();
		}
	}
	

		
//		structcolor2();
		
	
	
	public void mousePressed(final MouseEvent m) {
		pendelnZeit(10000000,strukt);
		
		drawKreisePunkteZArray(1,1,strukt);
		
		
		
		
		
	}
	
	KreisPunkt[] strukt;
	
	public KreisPunkt[] konstStrukt(int anzahl, double maxx, double maxy, double maxz, double maxMass) {

		
		KreisPunkt[] strukt = new KreisPunkt[anzahl];

	

		for (int i = 0; i < anzahl; i++) {

			int size = 10;

//			if (maxMass < 1)
//				maxMass = 1;

			double masse = maxMass * Math.random() * Math.random() * Math.random();
			if (i < 3)
				masse = maxMass;
			double size2 = 10 * Math.pow(masse / maxMass, 0.33333);
			size = (int) Math.round(size2);

			if (masse <= 0)
				masse = 1;
//			else if(masse<0 && masse>1)masse=1;

			int r = 6;
			int g = 6;
			int b = 6;

//			if (masse == 1) {
//
//				r = 66;
//				g = 66;
//				b = 66;
//			}

//			else if (masse / maxMass * 255 < 66) {
			r = (int) (255 * Math.random());
			g = (int) (255 * Math.random());
			b = (int) (255 * Math.random());
			if (r < 0)
				r = -r;
			if (g < 0)
				g = -g;
			if (b < 0)
				b = -b;
			if (r > 255)
				r = 255;
			if (g > 255)
				g = 255;
			if (b > 255)
				b = 255;
//			}

//			 (masse / maxMass * 255 >= 66) {
//
//				r = (int) (255 * Math.random());
//				g = (int) (255 * Math.random());
//				b = (int) (255 * Math.random());
//			}

			Color color = new Color(r, g, b);

//			double posX = 0;
//			double posY = 0;
//			double posZ = 0;

			double posX = maxx * Math.random() - maxx * Math.random();
			double posY = maxy * Math.random() - maxy * Math.random();
			double posZ = maxz * Math.random() - maxz * Math.random();
			
			double pendelpunktX=posX;
			double pendelpunktY=posY;
			double pendelpunktZ=posZ;
			;

			strukt[i] = new KreisPunkt(size, color, masse, posX, posY, posZ);

//			strukt[i].dx =  maxx * Math.random() - maxx * Math.random();
//			strukt[i].dy = maxy * Math.random() - maxy * Math.random();
//			strukt[i].dz = maxz * Math.random() - maxz * Math.random();

			strukt[i].dx = 0;
			strukt[i].dy = 0;
			strukt[i].dz = 0;

			strukt[i].konstUnterStrukt();
		}
	
		return strukt;
	}

/* Standard Java entry point */
/* This method can be eliminated in most Java environments */
	public static void main(String[] args) {
		new WellenSim().start(args);
	}
}
