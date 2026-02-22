package farbpunkte;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import acm.graphics.GLabel;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

public class MassekugelnReloaded7 extends GraphicsProgram {

	double[][] objekte = new double[1000][16]; // Atribute 0-16
	GOval[] kreise = new GOval[1000];

	// Idee1)
	// Gitterentropie
	// 1D entropie
	// 2D entropie
	// 3D entropie

	// kleinste gefunde Unterteilungsmöglichkeit
	double minElem = 0.1;

	public void initt() {
		for (int i = 0; i < objekte.length; i++) {
			objekte[i][0] = 10.0; // ein PUnkt als Gravitationszentrum
			if (i % 8 == 0)
				objekte[i][0] = 100.0;
			// objekte[i][0] = Math.random() * 255; //Gerade, Diagonale durch 0,0? als
			// Gravitationszentrum
			for (int j = 1; j < 16; j++) {
				objekte[i][j] = Math.random() * 255;
			}
		}
	}

	double counter = 1.0;

	public void raumzeit2() {
		long time = System.currentTimeMillis();

		while (System.currentTimeMillis() - time < 100) {
			int kreisNrA = (int) (Math.random() * kreise.length); // 100 Kreise
			int kreisNrB = (int) (Math.random() * kreise.length); // 100 Kreise
			for (int i = 0; i < objekte[0].length; i++) {

				double swapA = objekte[kreisNrA][i] * Math.random();
				double swapB = objekte[kreisNrB][i] * Math.random();

				objekte[kreisNrA][i] -= swapA;
				objekte[kreisNrB][i] += swapA;

				objekte[kreisNrB][i] -= swapB;
				objekte[kreisNrA][i] += swapB;
			}

		}
		drawKreise();
	}

	public void raumzeit1() {
		long time = System.currentTimeMillis();

		while (System.currentTimeMillis() - time < 100) {
			int kreisNrA = (int) (Math.random() * kreise.length); // 100 Kreise
			int kreisNrB = (int) (Math.random() * kreise.length); // 100 Kreise
			for (int i = 0; i < objekte[0].length; i++) {

				double swapA = objekte[kreisNrA][i] * Math.random();
				double swapB = objekte[kreisNrB][i] * Math.random();

				objekte[kreisNrA][i] -= swapA;
				objekte[kreisNrB][i] += swapA;

				objekte[kreisNrB][i] -= swapB;
				objekte[kreisNrA][i] += swapB;
			}

		}
		drawKreise();
	}

	public void raumzeit3() {
		long time = System.currentTimeMillis();

		while (System.currentTimeMillis() - time < 100) {
			int kreisNrA = (int) (Math.random() * kreise.length); // 100 Kreise
			int kreisNrB = (int) (Math.random() * kreise.length); // 100 Kreise
			for (int i = 0; i < 16; i++) {

				double swapA = objekte[kreisNrA][i] * Math.random();
				double swapB = objekte[kreisNrB][i] * Math.random();

				// double swapC = Math.pow(swapA*swapA+swapB*swapB, 0.5);

				objekte[kreisNrA][i] -= swapA;
				objekte[kreisNrB][i] += swapA;

				objekte[kreisNrB][i] -= swapB;
				objekte[kreisNrA][i] += swapB;

			}

			int i = (int) (Math.random() * 16);
			int i2 = (int) (Math.random() * 16);
			double swapA = objekte[kreisNrA][i] * Math.random();
			double swapB = objekte[kreisNrB][i2] * Math.random();

			// double swapC = Math.pow(swapA*swapA+swapB*swapB, 0.5);

			objekte[kreisNrA][i] -= swapA;
			objekte[kreisNrB][i2] += swapA;

			objekte[kreisNrB][i2] -= swapB;
			objekte[kreisNrA][i] += swapB;

		}
		drawKreise();
	}

	public double getMasse() {
		double masse = 0;
		for (int i = 0; i < kreise.length; i++) {
			masse = (int) (masse + objekte[i][0]);
		}
		return masse;
	}

	// Unteraufteilung der Massen
	public int getKreis(double teilnummer) {
		for (int i = 0; i < kreise.length; i++) {
			if (teilnummer <= 0)
				return i;
			teilnummer -= objekte[i][0];

		}
		return kreise.length - 1;
	}

	// Teilen durch Abstand*Abstand
	// Alle Kreise brauchen in den Attributen drei Ortskoordinaten x,y,z

	// Aktuelle durchführung bei jedem Mausklick

	// Unteraufteilung von Massen auf Objektarray
	public int getObj(double teilnummer) {
		for (int i = 0; i < kreise.length; i++) {
			if (teilnummer <= 0)
				return i;
			teilnummer -= objekte[i][0];

		}
		return kreise.length - 1;
	}

	public void getKreisGravAutokl(int konst) {
		long time = System.currentTimeMillis();
		while (System.currentTimeMillis() - time < 10000) {
			for (int kreisNrA = 0; kreisNrA < kreise.length; kreisNrA++) {
				for (int kreisNrB = kreisNrA + 1; kreisNrB < kreise.length; kreisNrB++) {

					int j = kreisNrB; // 100 Kreise

					double MA = objekte[kreisNrA][0];
					double MB = objekte[j][0];

					double Dist1 = (objekte[kreisNrA][1] - objekte[j][1]) * (objekte[kreisNrA][1] - objekte[j][1]);
					double Dist2 = (objekte[kreisNrA][2] - objekte[j][2]) * (objekte[kreisNrA][2] - objekte[j][2]);
					double Dist3 = (objekte[kreisNrA][3] - objekte[j][3]) * (objekte[kreisNrA][3] - objekte[j][3]);
					double dist = Dist1 + Dist2 + Dist3;
					if (dist < 0.00001)
						dist = 0.00001;

					for (int i = 1; i < objekte[0].length; i++) {

						double swapA = objekte[kreisNrA][i] * Math.random();
						double swapB = objekte[kreisNrB][i] * Math.random();

						swapA = swapA * konst;// mal chaoswert /masse
						swapB = swapB * konst;

						swapA = swapA / dist;
						swapB = swapB / dist;

						objekte[kreisNrA][i] -= (swapA * MB / MA);
						objekte[kreisNrB][i] += (swapA * MA / MB);

						objekte[kreisNrB][i] -= (swapB * MA / MB);
						objekte[kreisNrA][i] += (swapB * MB / MA);
					}
				}
			}
		}
		drawKreise();
	}

	// public void getKreisGrav2() {
	// for (int i = 0; i < 1000000000; i++) {
	// KreisPunkt a = strukt[(int) (Math.random() * strukt.length)];
	// KreisPunkt b = strukt[(int) (Math.random() * strukt.length)];
	// // if (a == null || b == null) {
	// // konstStrukt(1000, 1000, 100, 1000);
	// // }
	// // a.eigenbeschleunigung(b);
	// // a.move();
	// boolean notme = false;
	//
	// double dismul = 100;
	//
	// double distQ1 = (a.posX * dismul - b.posX * dismul) * (a.posX * dismul -
	// b.posX * dismul);
	// if (distQ1 != 0)
	// notme = true;
	// double distQ2 = (a.posX * dismul - b.posX * dismul) * (a.posX * dismul -
	// b.posX * dismul);
	// if (distQ2 != 0)
	// notme = true;
	// double distQ3 = (a.posX * dismul - b.posX * dismul) * (a.posX * dismul -
	// b.posX * dismul);
	// if (distQ3 != 0)
	// notme = true;
	//
	// if (a.bocknummern[b.nr]) {
	// if (Math.random() > 0.9)
	// a.bocknummern[b.nr] = false;
	// else
	// notme = false;
	// }
	//
	// double distQ12 = distQ1 + distQ2;
	// double distQ23 = distQ2 + distQ3;
	// double distQ13 = distQ1 + distQ3;
	// double distQ123 = distQ1 + distQ2 + distQ3;
	//
	// if (notme) {
	// double swapAX = a.posX / distQ1;
	// double swapBX = b.posX / distQ1;
	// double swapAY = a.posY / distQ2;
	// double swapBY = b.posY / distQ2;
	// double swapAZ = a.posZ / distQ3;
	// double swapBZ = b.posZ / distQ3;
	//
	// swapAX = swapAX + (a.posX / distQ12);
	// swapBX = swapBX + (b.posX / distQ12);
	// swapAY = swapAY + (a.posY / distQ12);
	// swapBY = swapBY + (b.posY / distQ12);
	// swapAY = swapAY + (a.posY / distQ23);
	// swapBY = swapBY + (b.posY / distQ23);
	// swapAX = swapAX + (a.posX / distQ12);
	// swapBX = swapAX + (b.posX / distQ12);
	// swapAZ = swapAZ + (a.posZ / distQ13);
	// swapBZ = swapBZ + (b.posZ / distQ13);
	// swapAX = swapAX + (a.posX / distQ13);
	// swapBX = swapBX + (b.posX / distQ13);
	//
	// swapAZ = swapAZ + (a.posZ / distQ123);
	// swapBZ = swapBZ + (b.posZ / distQ123);
	// swapAX = swapAX + (a.posX / distQ123);
	// swapBX = swapBX + (b.posX / distQ123);
	// swapAY = swapAY + (a.posY / distQ123);
	// swapBY = swapBY + (b.posY / distQ123);
	//
	// double sumA = swapAX*swapAX + swapAY*swapAY + swapAZ*swapAZ;
	// double sumB = swapBX*swapBX + swapBY*swapBY + swapBZ*swapBZ;
	//
	//
	// if (sumA < 0.01 && sumB < 0.01)
	// a.bocknummern[b.nr] = true;
	// else {
	// b.bocknummern[a.nr] = false;
	// a.bocknummern[b.nr] = false;
	// }
	//
	// while (sumA > distQ123 * 2) {
	//
	// swapAX*= Math.random();
	// swapAY*= Math.random();
	// swapAZ*= Math.random();
	// sumA = swapAX + swapAY + swapAZ;
	// }
	//
	// a.posX = a.posX - swapAX;
	// a.posY = a.posY - swapAY;
	// a.posZ = a.posZ - swapAZ;
	// a.posX = a.posX + swapBX;
	// a.posY = a.posY + swapBY;
	// a.posZ = a.posZ + swapBZ;
	//
	// if (a.posZMax * 0.75 < a.posZ) {
	// a.size = 11;
	// a.nah = true;
	// a.fern = false;
	// } else if (a.posZMax * 0.25 > a.posZ) {
	// a.size = 9;
	// a.fern = true;
	// a.nah = false;
	// }
	//
	// while (sumB > distQ123 * 2) {
	// swapBX*= Math.random();
	// swapBY*= Math.random();
	// swapBZ*= Math.random();
	// sumB = swapBX + swapBY + swapBZ;
	// }
	//
	// b.posX = b.posX - swapBX;
	// b.posY = b.posY - swapBY;
	// b.posZ = b.posZ - swapBZ;
	// b.posX = b.posX + swapAX;
	// b.posY = b.posY + swapAY;
	// b.posZ = b.posZ + swapAZ;
	//
	// // b.eigenbeschleunigung(a);
	// // b.move();
	//
	// if (b.posZMax * 0.75 < b.posZ) {
	// b.size = 11;
	// b.nah = true;
	// b.fern = false;
	// } else if (b.posZMax * 0.25 > b.posZ) {
	// b.size = 9;
	// ;
	// b.fern = true;
	// b.nah = false;
	// }
	// }
	// }
	// }

	public void getKreisGrav3(long it, int dismul) {

		for (int i = 0; i < it; i++) {
			KreisPunkt a = strukt[(int) (Math.random() * strukt.length)];
			KreisPunkt b = strukt[(int) (Math.random() * strukt.length)];
			// if (a == null || b == null) {
			// konstStrukt(1000, 1000, 100, 1000);
			// }
			// a.eigenbeschleunigung(b);
			// a.move();
			boolean notme = false;

			// double dismul = 5;

			double distQ1 = (a.posX * dismul - b.posX * dismul) * (a.posX * dismul - b.posX * dismul);
			if (distQ1 != 0)
				notme = true;
			double distQ2 = (a.posY * dismul - b.posY * dismul) * (a.posY * dismul - b.posY * dismul);
			if (distQ2 != 0)
				notme = true;
			double distQ3 = (a.posZ * dismul - b.posZ * dismul) * (a.posZ * dismul - b.posZ * dismul);
			if (distQ3 != 0)
				notme = true;

			double distS1 = (a.dx * dismul - b.dx * dismul) * (a.dx * dismul - b.dx * dismul);
			if (distS1 != 0)
				notme = true;
			double distS2 = (a.dy * dismul - b.dy * dismul) * (a.dy * dismul - b.dy * dismul);
			if (distS2 != 0)
				notme = true;
			double distS3 = (a.dz * dismul - b.dz * dismul) * (a.dz * dismul - b.dz * dismul);
			if (distQ3 != 0)
				notme = true;

			// if (a.bocknummern[b.nr]) {
			// if (Math.random() > 0.9)
			// a.bocknummern[b.nr] = false;
			// else
			// notme = false;
			// }

			// double distQ12 = distQ1 + distQ2;
			// double distQ23 = distQ2 + distQ3;
			// double distQ13 = distQ1 + distQ3;
			double distQ123 = distQ1 + distQ2 + distQ3 + distS1 + distS2 + distS3;

			if (notme) {
				double swapAX = 0;
				double swapBX = 0;
				double swapAY = 0;
				double swapBY = 0;
				double swapAZ = 0;
				double swapBZ = 0;

				// swapAX = swapAX + (a.dx / distQ12);
				// swapBX = swapBX + (b.dx / distQ12);
				// swapAY = swapAY + (a.dy / distQ12);
				// swapBY = swapBY + (b.dy / distQ12);
				// swapAY = swapAY + (a.dy / distQ23);
				// swapBY = swapBY + (b.dy / distQ23);
				// swapAX = swapAX + (a.dx / distQ12);
				// swapBX = swapAX + (b.dx / distQ12);
				// swapAZ = swapAZ + (a.dz / distQ13);
				// swapBZ = swapBZ + (b.dz / distQ13);
				// swapAX = swapAX + (a.dx / distQ13);
				// swapBX = swapBX + (b.dx / distQ13);

				swapAZ = swapAZ + (a.dz / distQ123);
				swapBZ = swapBZ + (b.dz / distQ123);
				swapAX = swapAX + (a.dx / distQ123);
				swapBX = swapBX + (b.dx / distQ123);
				swapAY = swapAY + (a.dy / distQ123);
				swapBY = swapBY + (b.dy / distQ123);

				double sumA = swapAX * swapAX + swapAY * swapAY + swapAZ * swapAZ;
				double sumB = swapBX * swapBX + swapBY * swapBY + swapBZ * swapBZ;

				// if (sumA < 0.01 && sumB < 0.01)
				// a.bocknummern[b.nr] = true;
				// else {
				// b.bocknummern[a.nr] = false;
				// a.bocknummern[b.nr] = false;
				// }

				while (sumA * a.masse * b.masse > distQ123 * 2) {

					swapAX *= Math.random();
					swapAY *= Math.random();
					swapAZ *= Math.random();
					sumA = swapAX + swapAY + swapAZ;
				}

				while (sumB * a.masse * b.masse > distQ123 * 2) {
					swapBX *= Math.random();
					swapBY *= Math.random();
					swapBZ *= Math.random();
					sumB = swapBX + swapBY + swapBZ;
				}

				a.dx = a.dx - swapAX * b.masse;
				a.dy = a.dy - swapAY * b.masse;
				a.dz = a.dz - swapAZ * b.masse;
				b.dx = b.dx + swapAX * a.masse;
				b.dy = b.dy + swapAY * a.masse;
				b.dz = b.dz + swapAZ * a.masse;

				b.dx = b.dx - swapBX * a.masse;
				b.dy = b.dy - swapBY * a.masse;
				b.dz = b.dz - swapBZ * a.masse;
				a.dx = a.dx + swapBX * b.masse;
				a.dy = a.dy + swapBY * b.masse;
				a.dz = a.dz + swapBZ * b.masse;

				// b.eigenbeschleunigung(a);
				a.move();
				b.move();
				// if (a.size < 15) {
				// if (a.dzMax * 0.75 < a.dz) {
				// a.size = 11;
				// a.nah = true;
				// a.fern = false;
				// } else if (a.dzMax * 0.25 > a.dz) {
				// a.size = 9;
				// a.fern = true;
				// a.nah = false;
				// }
				// }
				// if (b.size < 15) {
				// if (b.dzMax * 0.75 < b.dz) {
				// b.size = 11;
				// b.nah = true;
				// b.fern = false;
				// } else if (b.posZMax * 0.25 > b.posZ) {
				// b.size = 9;
				// ;
				// b.fern = true;
				// b.nah = false;
				// }
				// }
			}
		}

	}

	// Gravitation entlang Teilchenkette
	public void getKreisGrav5(long it) {
		for (int i = 0; i < it; i++) {
			for (int j = 0; j < strukt.length; j++) {
//			int kreis=(int) (Math.random() * strukt.length);
				int kreis = j;
				int kreisb = 0;
				if (kreis < strukt.length - 1)
					kreisb = kreis + 1;
				KreisPunkt a = strukt[kreis];
				KreisPunkt b = strukt[kreisb];
				a.grav(b);
			}
			drawKreisePunkte2(zoom);
		}
	}

	// Gravitation potentiell jedes mit jedem
	public void getKreisGrav4(long it) {
		for (int i = 0; i < it; i++) {

			int kreis = (int) (Math.random() * strukt.length);
			int kreisb = (int) (Math.random() * strukt.length);
			KreisPunkt a = strukt[kreis];
			KreisPunkt b = strukt[kreisb];
			a.grav(b);
		}
//		structcolor2();
		drawKreisePunkteZ(zoom, zoom2);
	}

	public void getKreisGrav6(long it) {
//		if(strukt.length<3) {moveKreis=3;konstStrukt(1,1,1,1);}
		for (int i = 0; i < it; i++) {

			int kreis = (int) (Math.random() * strukt.length / 8);
			int kreisb = (int) (Math.random() * strukt.length / 4);
			int kreisc = (int) (Math.random() * strukt.length / 4);
			int kreisd = (int) (Math.random() * strukt.length / 2);
			int kreise = (int) (Math.random() * strukt.length / 2);
			int kreisf = (int) (Math.random() * strukt.length / 2);
			int kreisg = (int) (Math.random() * strukt.length / 2);

			int kreish = (int) (Math.random() * strukt.length);
			int kreisi = (int) (Math.random() * strukt.length);
			int kreisj = (int) (Math.random() * strukt.length);
			int kreisk = (int) (Math.random() * strukt.length);

			int kreisl = (int) (Math.random() * strukt.length);
			int kreism = (int) (Math.random() * strukt.length);
			int kreisn = (int) (Math.random() * strukt.length);
			int kreiso = (int) (Math.random() * strukt.length);

			KreisPunkt a = strukt[kreis];
			KreisPunkt b = strukt[kreisb];
			KreisPunkt c = strukt[kreisc];
			KreisPunkt d = strukt[kreisd];
			KreisPunkt e = strukt[kreise];
			KreisPunkt f = strukt[kreisf];
			KreisPunkt g = strukt[kreisg];
			KreisPunkt h = strukt[kreish];
			KreisPunkt i2 = strukt[kreisi];
			KreisPunkt j = strukt[kreisj];
			KreisPunkt k = strukt[kreisk];
			KreisPunkt l = strukt[kreisl];
			KreisPunkt m = strukt[kreism];
			KreisPunkt n = strukt[kreisn];
			KreisPunkt o = strukt[kreiso];

			a.grav(b);
			a.grav(c);
			b.grav(d);
			b.grav(e);
			c.grav(f);
			c.grav(g);

			d.grav(h);
			d.grav(i2);
			e.grav(j);
			e.grav(k);
			f.grav(l);
			f.grav(m);
			g.grav(n);
			g.grav(o);

		}
//		structcolor2();
		drawKreisePunkteZ(zoom, zoom2);
	}

	public void getKreisGrav7(long it) {
//		if(strukt.length<3) {moveKreis=3;konstStrukt(1,1,1,1);}
		for (int i = 0; i < it; i++) {

			int kreis = (int) (Math.random() * strukt.length / 8);
			int kreisb = (int) (Math.random() * strukt.length / 4);
			int kreisc = (int) (Math.random() * strukt.length / 4);
			int kreisd = (int) (Math.random() * strukt.length / 2);
			int kreise = (int) (Math.random() * strukt.length / 2);
			int kreisf = (int) (Math.random() * strukt.length / 2);
			int kreisg = (int) (Math.random() * strukt.length / 2);

			int kreish = (int) (Math.random() * strukt.length);
			int kreisi = (int) (Math.random() * strukt.length);
			int kreisj = (int) (Math.random() * strukt.length);
			int kreisk = (int) (Math.random() * strukt.length);

			int kreisl = (int) (Math.random() * strukt.length);
			int kreism = (int) (Math.random() * strukt.length);
			int kreisn = (int) (Math.random() * strukt.length);
			int kreiso = (int) (Math.random() * strukt.length);

			KreisPunkt a = strukt[kreis];
			KreisPunkt b = strukt[kreisb];
			KreisPunkt c = strukt[kreisc];
			KreisPunkt d = strukt[kreisd];
			KreisPunkt e = strukt[kreise];
			KreisPunkt f = strukt[kreisf];
			KreisPunkt g = strukt[kreisg];
			KreisPunkt h = strukt[kreish];
			KreisPunkt i2 = strukt[kreisi];
			KreisPunkt j = strukt[kreisj];
			KreisPunkt k = strukt[kreisk];
			KreisPunkt l = strukt[kreisl];
			KreisPunkt m = strukt[kreism];
			KreisPunkt n = strukt[kreisn];
			KreisPunkt o = strukt[kreiso];

			a.getKreisUnterp();
			b.getKreisUnterp();
			c.getKreisUnterp();
			d.getKreisUnterp();
			e.getKreisUnterp();
			f.getKreisUnterp();
			g.getKreisUnterp();
			h.getKreisUnterp();
			i2.getKreisUnterp();
			j.getKreisUnterp();
			k.getKreisUnterp();
			l.getKreisUnterp();
			m.getKreisUnterp();
			n.getKreisUnterp();
			o.getKreisUnterp();

			a.grav(b);
			a.grav(c);
			b.grav(d);
			b.grav(e);
			c.grav(f);
			c.grav(g);

			d.grav(h);
			d.grav(i2);
			e.grav(j);
			e.grav(k);
			f.grav(l);
			f.grav(m);
			g.grav(n);
			g.grav(o);

		}
//		structcolor2();
		drawKreisePunkteZ(zoom, zoom2);
	}

	double zoom2 = 1;

	public void structcolor1() {
		double maxSpeed = 0;
		for (int i = 0; i < strukt.length; i++) {
			if (strukt[i].getSpeed() > maxSpeed)
				maxSpeed = strukt[i].getSpeed();
		}
		int r = 0;
		int g = 0;
		int b = 0;
		for (int i = 0; i < strukt.length; i++) {
			r = (int) (Math.pow(strukt[i].getSpeed() / maxSpeed, 0.25) * 255);
			g = (int) (Math.pow(strukt[i].getSpeed() / maxSpeed, 0.5) * 255);
			b = (int) (Math.pow(strukt[i].getSpeed() / maxSpeed, 1) * 255);
			Color c = new Color(r, g, b);
			strukt[i].color = c;
		}

	}

	public void structcolor2() {
//		double maxSpeed=0;
//		for(int i=0; i<strukt.length; i++) {
//			if(strukt[i].getSpeed()>maxSpeed)maxSpeed=strukt[i].getSpeed();
//		}
		int r = 0;
		int g = 0;
		int b = 0;
		for (int i = 0; i < strukt.length; i++) {
			r = (int) (Math.min(Math.pow(strukt[i].getSpeed(), 1), 255));
			g = (int) (Math.min(Math.pow(strukt[i].getSpeed(), 0.5), 255));
			b = (int) (Math.min(Math.pow(strukt[i].getSpeed(), 0.25), 255));
			Color c = new Color(r, g, b);
			strukt[i].color = c;
		}

	}

	public void getKreisGrav2(long it) {
		int dismul = 5;
		for (int i = 0; i < it; i++) {
			KreisPunkt a = strukt[(int) (Math.random() * Math.min(strukt.length, moveKreis))];
			KreisPunkt b = strukt[(int) (Math.random() * Math.min(strukt.length, moveKreis))];
			// if(Math.pow(a.dx-b.dx,2)==0||Math.pow(a.dy-b.dy,2)==0||Math.pow(a.dz-b.dz,2)==0)
			// {
			// if (a == null || b == null) {
			// konstStrukt(1000, 1000, 100, 1000);
			// }
			// a.eigenbeschleunigung(b);
			// a.move();
			boolean notme = false;

			// double dismul = 5;

			double distQ1 = (a.posX * dismul - b.posX * dismul) * (a.posX * dismul - b.posX * dismul);
			if (distQ1 != 0)
				notme = true;
			double distQ2 = (a.posY * dismul - b.posY * dismul) * (a.posY * dismul - b.posY * dismul);
			if (distQ2 != 0)
				notme = true;
			double distQ3 = (a.posZ * dismul - b.posZ * dismul) * (a.posZ * dismul - b.posZ * dismul);
			if (distQ3 != 0)
				notme = true;

			double distS1 = (a.dx * dismul - b.dx * dismul) * (a.dx * dismul - b.dx * dismul);
			if (distS1 != 0)
				notme = true;
			double distS2 = (a.dy * dismul - b.dy * dismul) * (a.dy * dismul - b.dy * dismul);
			if (distS2 != 0)
				notme = true;
			double distS3 = (a.dz * dismul - b.dz * dismul) * (a.dz * dismul - b.dz * dismul);
			if (distQ3 != 0)
				notme = true;

			// if (a.bocknummern[b.nr]) {
			// if (Math.random() > 0.9)
			// a.bocknummern[b.nr] = false;
			// else
			// notme = false;
			// }

			// double distQ12 = distQ1 + distQ2;
			// double distQ23 = distQ2 + distQ3;
			// double distQ13 = distQ1 + distQ3;
			double distQ123 = distQ1 + distQ2 + distQ3 + distS1 + distS2 + distS3;

			if (notme) {
				double swapAX = 0;
				double swapBX = 0;
				double swapAY = 0;
				double swapBY = 0;
				double swapAZ = 0;
				double swapBZ = 0;

				// swapAX = swapAX + (a.dx / distQ12);
				// swapBX = swapBX + (b.dx / distQ12);
				// swapAY = swapAY + (a.dy / distQ12);
				// swapBY = swapBY + (b.dy / distQ12);
				// swapAY = swapAY + (a.dy / distQ23);
				// swapBY = swapBY + (b.dy / distQ23);
				// swapAX = swapAX + (a.dx / distQ12);
				// swapBX = swapAX + (b.dx / distQ12);
				// swapAZ = swapAZ + (a.dz / distQ13);
				// swapBZ = swapBZ + (b.dz / distQ13);
				// swapAX = swapAX + (a.dx / distQ13);
				// swapBX = swapBX + (b.dx / distQ13);

				swapAZ = swapAZ + (a.posZ / distQ123);
				swapBZ = swapBZ + (b.posZ / distQ123);
				swapAX = swapAX + (a.posX / distQ123);
				swapBX = swapBX + (b.posX / distQ123);
				swapAY = swapAY + (a.posY / distQ123);
				swapBY = swapBY + (b.posY / distQ123);

				double sumA = swapAX * swapAX + swapAY * swapAY + swapAZ * swapAZ;
				double sumB = swapBX * swapBX + swapBY * swapBY + swapBZ * swapBZ;

				// if (sumA < 0.01 && sumB < 0.01)
				// a.bocknummern[b.nr] = true;
				// else {
				// b.bocknummern[a.nr] = false;
				// a.bocknummern[b.nr] = false;
				// }

				while (sumA * a.masse * b.masse > distQ123 * 2) {

					swapAX *= Math.random();
					swapAY *= Math.random();
					swapAZ *= Math.random();
					sumA = swapAX + swapAY + swapAZ;
				}

				while (sumB * a.masse * b.masse > distQ123 * 2) {
					swapBX *= Math.random();
					swapBY *= Math.random();
					swapBZ *= Math.random();
					sumB = swapBX + swapBY + swapBZ;
				}

				a.posX = a.posX - swapAX * b.masse;
				a.posY = a.posY - swapAY * b.masse;
				a.posZ = a.posZ - swapAZ * b.masse;
				b.posX = b.posX + swapAX * a.masse;
				b.posY = b.posY + swapAY * a.masse;
				b.posZ = b.posZ + swapAZ * a.masse;

				b.posX = b.posX - swapBX * a.masse;
				b.posY = b.posY - swapBY * a.masse;
				b.posZ = b.posZ - swapBZ * a.masse;
				a.posX = a.posX + swapBX * b.masse;
				a.posY = a.posY + swapBY * b.masse;
				a.posZ = a.posZ + swapBZ * b.masse;

				double faktor = 0.01;

				a.dx = a.dx - swapAX * b.masse * faktor;
				a.dy = a.dy - swapAY * b.masse * faktor;
				a.dz = a.dz - swapAZ * b.masse * faktor;
				b.dx = b.dx + swapAX * a.masse * faktor;
				b.dy = b.dy + swapAY * a.masse * faktor;
				b.dz = b.dz + swapAZ * a.masse * faktor;
				;

				b.dx = b.dx - swapBX * a.masse * faktor;
				b.dy = b.dy - swapBY * a.masse * faktor;
				b.dz = b.dz - swapBZ * a.masse * faktor;
				a.dx = a.dx + swapBX * b.masse * faktor;
				a.dy = a.dy + swapBY * b.masse * faktor;
				a.dz = a.dz + swapBZ * b.masse * faktor;

				a.move();
				b.move();

				// b.eigenbeschleunigung(a);
				// b.move();
				if (a.size < 15) {
					if (a.posZMax * 0.75 < a.posZ) {
						a.size = 11;
						a.nah = true;
						a.fern = false;
					} else if (a.posZMax * 0.25 > a.posZ) {
						a.size = 9;
						a.fern = true;
						a.nah = false;
					}
				}
				if (b.size < 15) {
					if (b.posZMax * 0.75 < b.posZ) {
						b.size = 11;
						b.nah = true;
						b.fern = false;
					} else if (b.posZMax * 0.25 > b.posZ) {
						b.size = 9;
						;
						b.fern = true;
						b.nah = false;
					}
				}
			}
		}
		getKreisGrav3(it, dismul);
	}

	public void getKreisGrav() {
		long time = System.currentTimeMillis();
		while (System.currentTimeMillis() - time < 100) {
			for (int kreisNrA = 0; kreisNrA < kreise.length; kreisNrA++) {
				for (int kreisNrB = kreisNrA + 1; kreisNrB < kreise.length; kreisNrB++) {

					int j = kreisNrB; // 100 Kreise

					double MA = objekte[kreisNrA][0];
					double MB = objekte[j][0];

					double Dist1 = (objekte[kreisNrA][1] - objekte[j][1]) * (objekte[kreisNrA][1] - objekte[j][1]);
					double Dist2 = (objekte[kreisNrA][2] - objekte[j][2]) * (objekte[kreisNrA][2] - objekte[j][2]);
					double Dist3 = (objekte[kreisNrA][3] - objekte[j][3]) * (objekte[kreisNrA][3] - objekte[j][3]);
					double dist = Dist1 + Dist2 + Dist3;

					double masse = 255000;

					for (int i = 1; i < objekte[0].length; i++) {

						double teilnummer = (int) (Math.random() * masse);

						kreisNrA = getKreis(teilnummer); // 100 Kreise
						teilnummer = (int) (Math.random() * masse);
						kreisNrB = getKreis(teilnummer); // 100 Kreise

						double swapA = objekte[kreisNrA][i] * Math.random();
						double swapB = objekte[kreisNrB][i] * Math.random();

						swapA = swapA * 500.0;
						swapB = swapB * 500.0;

						swapA = swapA / dist;
						swapB = swapB / dist;

						objekte[kreisNrA][i] -= swapA;
						objekte[kreisNrB][i] += swapA;

						objekte[kreisNrB][i] -= swapB;
						objekte[kreisNrA][i] += swapB;
					}
				}
			}
		}
		drawKreise();
	}

	public void getKreisGrav(int konst) {
		long time = System.currentTimeMillis();
		while (System.currentTimeMillis() - time < 100) {
			for (int kreisNrA = 0; kreisNrA < kreise.length; kreisNrA++) {
				for (int kreisNrB = kreisNrA + 1; kreisNrB < kreise.length; kreisNrB++) {

					int j = kreisNrB; // 100 Kreise

					double MA = objekte[kreisNrA][0];
					double MB = objekte[j][0];

					double Dist1 = (objekte[kreisNrA][1] - objekte[j][1]) * (objekte[kreisNrA][1] - objekte[j][1]);
					double Dist2 = (objekte[kreisNrA][2] - objekte[j][2]) * (objekte[kreisNrA][2] - objekte[j][2]);
					double Dist3 = (objekte[kreisNrA][3] - objekte[j][3]) * (objekte[kreisNrA][3] - objekte[j][3]);
					double dist = Dist1 + Dist2 + Dist3;
					if (dist < 0.00001)
						dist = 0.00001;

					for (int i = 1; i < objekte[0].length; i++) {

						double swapA = objekte[kreisNrA][i] * Math.random();
						double swapB = objekte[kreisNrB][i] * Math.random();

						swapA = swapA * konst;
						swapB = swapB * konst;

						// dist ist bereits der Betrag des Abstandes
						swapA = swapA / dist;
						swapB = swapB / dist;

						objekte[kreisNrA][i] -= (swapA * MB / MA);
						objekte[kreisNrB][i] += (swapA * MA / MB);

						objekte[kreisNrB][i] -= (swapB * MA / MB);
						objekte[kreisNrA][i] += (swapB * MB / MA);
					}
				}
			}
		}
		drawKreise();
	}

	// F=m*a
	// a=F/m
	// Karaf wirkt immer auf beide objekte, un d in richtung des anderen (erst mal
	// one entro)

	// a=v/t
	public void getKreisFMAV(int konst) {
		long time = System.currentTimeMillis();
		while (System.currentTimeMillis() - time < 100) {
			for (int kreisNrA = 0; kreisNrA < kreise.length; kreisNrA++) {
				for (int kreisNrB = kreisNrA + 1; kreisNrB < kreise.length; kreisNrB++) {

					int j = kreisNrB; // 100 Kreise

					double MA = objekte[kreisNrA][0];
					double MB = objekte[j][0];

					double Dist1 = (objekte[kreisNrA][1] - objekte[j][1]) * (objekte[kreisNrA][1] - objekte[j][1]);
					double Dist2 = (objekte[kreisNrA][2] - objekte[j][2]) * (objekte[kreisNrA][2] - objekte[j][2]);
					double Dist3 = (objekte[kreisNrA][3] - objekte[j][3]) * (objekte[kreisNrA][3] - objekte[j][3]);
					double dist = Dist1 + Dist2 + Dist3;
					if (dist < 0.00001)
						dist = 0.00001;

					// Alt da es alle atribute mit Enro verändert
					// for (int i = 1; i < objekte[0].length; i++) {
					//
					// double swapA = objekte[kreisNrA][i] * Math.random();
					// double swapB = objekte[kreisNrB][i] * Math.random();
					//
					// swapA = swapA * konst;
					// swapB = swapB * konst;}

					// Jeder kreis wechselwirkt mit jedem anderen Kreis durch Gravitationskraft
					// -> Masse 1 * Masse 2 / dist(Weil dist bereits quadrat von abstand

				}
			}
		}
	}

	// Array von hundert anpassbar konstrierten KreisPunkt
	KreisPunkt[] strukt = new KreisPunkt[1];

	public KreisPunkt[] struktsort(KreisPunkt[] strukt) {
		KreisPunkt[] sortedStrukt = new KreisPunkt[strukt.length];
		double max = -99999999;
		for (int i = 0; i < sortedStrukt.length; i++) {
			double min = 999999999;
			int struktnummer = 0;
			for (int j = 0; j < strukt.length; j++) {
				if (strukt[j] != null && strukt[j].posZ > max && strukt[j].posZ < min) {
					min = strukt[j].posZ;
					struktnummer = j;
				}
			}

			sortedStrukt[i] = strukt[struktnummer];
			max = strukt[struktnummer].posZ;
		}
		return sortedStrukt;

	}

	public void konstStrukt(double maxx, double maxy, double maxz, double maxMass) {

		if (moveKreis > strukt.length)
			strukt = new KreisPunkt[moveKreis];

		int moveK = moveKreis;
		if (moveK > strukt.length || moveK < 1)
			moveK = strukt.length;

		for (int i = 0; i < moveK; i++) {

			int size = 13;

//			if (maxMass < 1)
//				maxMass = 1;

			double masse = maxMass * Math.random() * Math.random() * Math.random() * Math.random();
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
	}

	public void konstStrukt4(double maxx, double maxy, double maxz, double maxMass) {
		if (moveKreis > strukt.length)
			strukt = new KreisPunkt[moveKreis];

		int moveK = moveKreis;
		if (moveK > strukt.length || moveK < 1)
			moveK = strukt.length;

		for (int i = 0; i < moveK; i++) {

			int size = 7;

//		if (maxMass < 1)
//			maxMass = 1;

			double masse = maxMass * Math.random();

			if (masse < 1)
				masse = 1;
//		else if(masse<0 && masse>1)masse=1;

			int r = 6;
			int g = 6;
			int b = 6;

//		if (masse == 1) {
//
//			r = 66;
//			g = 66;
//			b = 66;
//		}

//		else if (masse / maxMass * 255 < 66) {
			r = (int) (255 * Math.random() * masse / maxMass);
			g = (int) (255 * Math.random() * masse / maxMass);
			b = (int) (255 * Math.random() * masse / maxMass);
			if (r < 0)
				r = -r;
			if (g < 0)
				g = -g;
			if (b < 0)
				b = -b;
//		}

//		 (masse / maxMass * 255 >= 66) {
//
//			r = (int) (255 * Math.random());
//			g = (int) (255 * Math.random());
//			b = (int) (255 * Math.random());
//		}

			Color color = new Color(r, g, b);

//			double posX = maxx * Math.random() - maxx * Math.random();
//			double posY = maxy * Math.random() - maxy * Math.random();
//			double posZ = maxz * Math.random() - maxz * Math.random();

			strukt[i] = new KreisPunkt(size, color, masse, 0, 0, 0);

			strukt[i].dx = maxx * Math.random() - maxx * Math.random();
			strukt[i].dy = maxy * Math.random() - maxy * Math.random();
			strukt[i].dz = maxz * Math.random() - maxz * Math.random();
		}
	}

	public List<KreisPunkt> sortStrktD0() {

		List<KreisPunkt> StruktList = new ArrayList();
		List<KreisPunkt> StruktList2 = new ArrayList();
		for (int i = 1; i < strukt.length; i++) {
			StruktList.add(strukt[i]);
		}

		for (int i = 1; i < StruktList.size(); i++) {
			double min = 999999;
			int minpos = 0;
			for (int i2 = 1; i2 < StruktList.size(); i2++) {
				KreisPunkt Erde = StruktList.get(0);
				KreisPunkt Laterne = StruktList.get(i2);

				double distq = (Erde.posX - Laterne.posX) * (Erde.posX - Laterne.posX)
						+ (Erde.posY - Laterne.posY) * (Erde.posY - Laterne.posY)
						+ (Erde.posZ - Laterne.posZ) * (Erde.posZ - Laterne.posZ);

				distq = Math.pow(distq, 0.5);

				if (min > distq) {
					minpos = i;
					min = distq;
				}
			}

			StruktList2.add(StruktList.get(minpos));
			StruktList.remove(minpos);

		}

		for (int i = 1; i < strukt.length && i < StruktList2.size(); i++) {
			return StruktList2;
		}
		return StruktList2;
	}

	public void getExpansion() {

		for (int i = 1; i < strukt.length; i++) {
			KreisPunkt Erde = strukt[0];
			KreisPunkt Laterne = strukt[i];

			double distq = (Erde.posX - Laterne.posX) * (Erde.posX - Laterne.posX)
					+ (Erde.posY - Laterne.posY) * (Erde.posY - Laterne.posY)
					+ (Erde.posZ - Laterne.posZ) * (Erde.posZ - Laterne.posZ);
			double speeddif = (Erde.dx - Laterne.dx) * (Erde.dx - Laterne.dx)
					+ (Erde.dy - Laterne.dy) * (Erde.dy - Laterne.dy) + (Erde.dz - Laterne.dz) * (Erde.dz - Laterne.dz);

			distq = Math.pow(distq, 0.5);
			strukt[i].dist0 = distq;
			speeddif = Math.pow(speeddif, 0.5);
			strukt[i].speed0 = speeddif;

//			double line = speeddif / distq;
//
//			System.out.println("dist" + distq);
//			System.out.println("speeddif" + speeddif);
//			System.out.println("s/d" + line);

		}
	}

	public void konstStruct3(double maxx, double maxy, double maxz, double maxMass) {

		if (moveKreis > strukt.length)
			strukt = new KreisPunkt[moveKreis];

		int moveK = moveKreis;
		if (moveK > strukt.length || moveK < 1)
			moveK = strukt.length;

		double posX = 0;
		double posY = 0;
		double posZ = 0;

		int r = 0;
		int g = 0;
		int b = 0;

		for (int i = 0; i < moveK; i++) {

			int size = 7;

//			if (maxMass < 1)
//				maxMass = 1;

			double masse = maxMass / Math.random();

			if (masse < 1)
				masse = 1;
//			else if(masse<0 && masse>1)masse=1;

//			if (masse == 1) {
//
//				r = 66;
//				g = 66;
//				b = 66;
//			}

//			else if (masse / maxMass * 255 < 66) {
//			r = (int) (200 * masse / maxMass+Math.random()*55);
//			g = (int) (200  * masse / maxMass+Math.random()*55);
//			b = (int) (200  * masse / maxMass+Math.random()*55);
//			}

//			 (masse / maxMass * 255 >= 66) {
//
//				r = (int) (255 * Math.random());
//				g = (int) (255 * Math.random());
//				b = (int) (255 * Math.random());
//			}

			if (Math.random() > 0.995) {
				posX = maxx * Math.random() - maxx * Math.random();
				posY = maxy * Math.random() - maxy * Math.random();
				posZ = maxz * Math.random() - maxz * Math.random();
				r = (int) (Math.random() * 255);
				g = (int) (Math.random() * 255);
				b = (int) (Math.random() * 255);
			}

			Color color = new Color(r, g, b);

			strukt[i] = new KreisPunkt(size, color, masse, posX, posY, posZ);

			strukt[i].dx = 0.0;
			strukt[i].dy = 0.0;
			strukt[i].dz = 0.0;
		}

	}

	// strukt konstruieren
	public void konstStrukt2(double maxx, double maxy, double maxz, double maxMass) {

		if (moveKreis > strukt.length)
			strukt = new KreisPunkt[moveKreis];

		int moveK = moveKreis;
		if (moveK > strukt.length || moveK < 1)
			moveK = strukt.length;

		for (int i = 0; i < moveK; i++) {

			int size = 7;

//			if (maxMass < 1)
//				maxMass = 1;

			double masse = maxMass;

			if (masse < 1)
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
			r = (int) (255 * Math.random() * masse / maxMass);
			g = (int) (255 * Math.random() * masse / maxMass);
			b = (int) (255 * Math.random() * masse / maxMass);
//			}

//			 (masse / maxMass * 255 >= 66) {
//
//				r = (int) (255 * Math.random());
//				g = (int) (255 * Math.random());
//				b = (int) (255 * Math.random());
//			}

			Color color = new Color(r, g, b);

			double posX = maxx;
			double posY = maxy;
			double posZ = maxz;

			strukt[i] = new KreisPunkt(size, color, masse, posX, posY, posZ);

			strukt[i].dx = 0.0;
			strukt[i].dy = 0.0;
			strukt[i].dz = 0.0;
		}
	}

	// public void konstStrukt2(double maxx, double maxy, double maxz, double
	// maxMass) {
	//
	// for (int i = 0; i < strukt.length; i++) {
	// int size = 10;
	//
	// double masse = 0;
	// if (i < 0) {
	// masse = maxMass / Math.random();
	// size = 50;
	// }
	// // {
	// // masse = Math.random() * maxMass;
	// // // masse
	// // if (maxMass * 0.75 < masse)
	// // size++;
	// // else if (maxMass * 0.25 > masse)
	// // size--;
	// // }
	// else {
	// masse = maxMass * Math.random();
	// }
	// // if(masse>5*maxMass)size=20;
	// // if(masse>25*maxMass)size=25;
	// // masse = 100;
	//
	//// double posX = Math.random() * maxx;
	//// double posY = Math.random() * maxy;
	//// double posZ = Math.random() * maxz;
	//
	// double posX = Math.random() + 0;
	// double posY = Math.random() + 0;
	// double posZ = Math.random() + 0;
	//
	// int r = (int) (Math.random() * 55);
	// int g = (int) (Math.random() * 55);
	// int b = (int) (Math.random() * 55);
	//
	// if (masse % 2 == 0)
	// r += 100;
	// if (masse % 4 == 0)
	// r += 100;
	// if (masse % 3 == 0)
	// g += 100;
	// if (masse % 9 == 0)
	// g += 100;
	// if (masse % 5 == 0)
	// b += 100;
	// if (masse % 25 == 0)
	// b += 100;
	//
	// Color color = new Color(r, g, b);
	//
	// strukt[i] = new KreisPunkt(size, color, masse, posX, posY, posZ);
	// strukt[i].nr = i;
	// strukt[i].bocknummern = new boolean[strukt.length];
	// strukt[i].posXMax = maxx;
	// strukt[i].posYMax = maxy;
	// strukt[i].posZMax = maxz;
	//
	// // posZ
	// if (maxz * 0.75 < posZ) {
	// size++;
	// strukt[i].nah = true;
	// } else if (maxz * 0.25 > posZ) {
	// size--;
	// strukt[i].fern = true;
	// }
	// }
	// }

	// wechselwire eigenbesch jedes mit jedem und addiere danach jeweil den Speed
	// zur x,y und zpos

	public void raumzeitneu() {
		for (int i = 0; i < 10000; i++) {
			KreisPunkt a = strukt[(int) (Math.random() * strukt.length)];
			KreisPunkt b = strukt[(int) (Math.random() * strukt.length)];
			// if (a == null || b == null) {
			// konstStrukt(1000, 1000, 100, 1000);
			// }
			a.eigenbeschleunigung(b);
			a.move();

			if (a.posZMax * 0.75 < a.posZ) {
				a.size = 11;
				a.nah = true;
				a.fern = false;
			} else if (a.posZMax * 0.25 > a.posZ) {
				a.size = 9;
				a.fern = true;
				a.nah = false;
			}

			b.eigenbeschleunigung(a);
			b.move();

			if (b.posZMax * 0.75 < b.posZ) {
				b.size = 11;
				b.nah = true;
				b.fern = false;
			} else if (b.posZMax * 0.25 > b.posZ) {
				b.size = 9;
				;
				b.fern = true;
				b.nah = false;
			}
		}
	}

	public void raumzeitneu(long t2) {
		for (int i = 0; i < t2; i++)
			raumzeitneu();
	};

	public void drawKreisePunkte() {
		removeAll();
		for (int i = 0; i < strukt.length; i++) {
			KreisPunkt kp = strukt[i];
			GOval kreis = new GOval(kp.size, kp.size);
			kreis.setFilled(kp.filled);
			kreis.setFillColor(kp.color);
			// if (kp.nah)
			// kreis.sendForward();
			// if (kp.fern)
			// kreis.sendBackward();
			add(kreis, kp.posX + 100, kp.posY);

		}
	}

	public void drawKreisePunkte(double zoom) {
		KreisPunkt[] strukt = struktsort(this.strukt);
		removeAll();
		for (int i = 0; i < strukt.length; i++) {
			KreisPunkt kp = strukt[i];
			GOval kreis = new GOval(kp.size, kp.size);
			kreis.setFilled(kp.filled);
			kreis.setFillColor(kp.color);
			// if (kp.nah)
			// kreis.sendForward();
			// if (kp.fern)
			// kreis.sendBackward();
			add(kreis, (kp.posX) * zoom + 500, kp.posY * zoom + 350);

		}
	}

	public void drawKreisePunkteZ(double zoom, double zoom2) {
//		KreisPunkt[] strukt = struktsort(this.strukt);
		removeAll();

		getExpansion();
		
		
		double maxdist0 = 0;
		double maxspeed0 = 0;
		
		
		KreisPunkt erde = strukt[0];
		
		for (int i = 0; i < strukt.length; i++) {
			KreisPunkt kp = strukt[i];
			GOval kreis = new GOval(kp.size * zoom2, kp.size * zoom2);
			kreis.setFilled(kp.filled);
			
			
//			kreis.setFillColor(kp.color);
			if(i==0)kreis.setFillColor(new Color(0,0,255));
			else kreis.setFillColor(new Color(255,0,0));

			// if (kp.nah)
			// kreis.sendForward();
			// if (kp.fern)
			// kreis.sendBackward();

			if ((kp.posX-erde.posX) * zoom + 900 < 2000 && (kp.posX-erde.posX) * zoom + 900 > 0 && (kp.posY-erde.posY) * zoom + 450 < 2000
					&& (kp.posY-erde.posY) * zoom + 450 > 0 && kp.size >= 1)

				add(kreis, (kp.posX-erde.posX) * zoom + 900, (kp.posY-erde.posY) * zoom + 450);

			if (strukt[i].dist0 > maxdist0) {
				maxdist0 = strukt[i].dist0;
			}
			if (strukt[i].speed0 > maxspeed0) {
				maxspeed0 = strukt[i].speed0;
			}
		}

		for (int i = 0; i < strukt.length; i++) {
			GOval rp = new GOval(3, 3);
			rp.setFilled(true);
			rp.setFillColor(new Color(255, 0, 0));
			add(rp,strukt[i].dist0*100/maxdist0,strukt[i].speed0*100/maxspeed0);
		}

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

//	public void drawKreisePunkteArray(double zoom, KreisPunkt[] strukt) {
////		KreisPunkt[] strukt = struktsort(struktArray);
//		removeAll();
//		for (int i = 0; i < strukt.length; i++) {
//			if (strukt[i] != null) {
//				KreisPunkt kp = strukt[i];
//				GOval kreis = new GOval(kp.size, kp.size);
//				kreis.setFilled(kp.filled);
//				kreis.setFillColor(kp.color);
//				// if (kp.nah)
//				// kreis.sendForward();
//				// if (kp.fern)
//				// kreis.sendBackward();
//				add(kreis, (kp.posX) * zoom + 500, kp.posY * zoom + 350);
//			}
//
//		}
//	}

	public KreisPunkt[] MatStrukToStruk(KreisPunkt[] struktArray, MaterieStruktur matStruk, int count) {

//		KreisPunkt[] strukt = new KreisPunkt[size];
//
//		for (int i = 0; i < strukt.length; i++)
//			strukt[i] = struktArray[i];

		if (matStruk.unterstufe == 0) {
			struktArray[count] = matStruk.kreispunkt;

		} else {
			struktArray = MatStrukToStruk(struktArray, matStruk.unterstruktur, count);
			count += Math.pow(2, matStruk.unterstruktur.unterstufe);
//			System.out.println(matStruk.unterstruktur2.unterstufe);
			struktArray = MatStrukToStruk(struktArray, matStruk.unterstruktur2, count);
		}

		return struktArray;
	}

	public void MatStrukGrav(MaterieStruktur matStruk) {

//		KreisPunkt[] strukt = new KreisPunkt[size];
//
//		for (int i = 0; i < strukt.length; i++)
//			strukt[i] = struktArray[i];

		if (matStruk.unterstufe == 1) {
			KreisPunkt kp1 = matStruk.unterstruktur.kreispunkt;
			KreisPunkt kp2 = matStruk.unterstruktur2.kreispunkt;
			kp1.grav(kp2);

		} else {
			MatStrukGrav(matStruk.unterstruktur);
			MatStrukGrav(matStruk.unterstruktur2);
//			System.out.println(matStruk.unterstruktur2.unterstufe);
//			struktArray=MatStrukToStruk(struktArray, matStruk.unterstruktur2, count);
		}
//
//		return struktArray;
	}

	MaterieStruktur matStrukt;

//Generieren und einmal zeichnen
	public void generateMatStrukt(int tiefe) {
		matStrukt = new MaterieStruktur(tiefe, -Math.pow(2, tiefe), Math.pow(2, tiefe), -Math.pow(2, tiefe),
				Math.pow(2, tiefe), -Math.pow(2, tiefe), Math.pow(2, tiefe), Math.pow(2, tiefe));

		matStrukt.unterstufe = tiefe;
		int size = (int) Math.pow(2, matStrukt.unterstufe);

		KreisPunkt[] struktA = new KreisPunkt[size];

//		System.out.println(struktA.length);

		struktA = MatStrukToStruk(struktA, matStrukt, 0);
		drawKreisePunkteZArray(zoom, zoom2, struktA);
//		System.out.println(struktA[5].posX);

	}

	public void MatStrukGrav2(long it) {
		for (int i = 0; i < it; i++) {
			MatStrukGrav(matStrukt);
		}
		int size = (int) Math.pow(2, matStrukt.unterstufe);

		KreisPunkt[] struktA = new KreisPunkt[size];

//	System.out.println(struktA.length);

		struktA = MatStrukToStruk(struktA, matStrukt, 0);
		drawKreisePunkteZArray(zoom, zoom2, struktA);

	}

	public void drawKreisePunkte2(double zoom) {
//		KreisPunkt[] strukt = struktsort(this.strukt);
		removeAll();
		for (int i = 0; i < strukt.length; i++) {
			KreisPunkt kp = strukt[i];
			GOval kreis = new GOval(kp.size, kp.size);
			kreis.setFilled(kp.filled);
			kreis.setFillColor(kp.color);

			// if (kp.nah)
			// kreis.sendForward();
			// if (kp.fern)
			// kreis.sendBackward();

			add(kreis, (kp.posX) * zoom + 900, kp.posY * zoom + 450);

		}
	}

	// weiter
	double c2 = 1.0;

	public void raumzeit4() {
		long time = System.currentTimeMillis();

		double masse = getMasse();
		long counter = 0;
		c2 *= 1.2;
		while (System.currentTimeMillis() - time < 100 && counter < c2) {
			counter++;
			double teilnummer = (int) (Math.random() * masse);

			int kreisNrA = getKreis(teilnummer); // 100 Kreise
			teilnummer = (int) (Math.random() * masse);
			int kreisNrB = getKreis(teilnummer); // 100 Kreise
			for (int i = 0; i < 16; i++) {

				double swapA = objekte[kreisNrA][i] * Math.random();
				double swapB = objekte[kreisNrB][i] * Math.random();

				// double swapC = Math.pow(swapA*swapA+swapB*swapB, 0.5);
				if (i == 0) {
					objekte[kreisNrA][i] -= swapA;
					objekte[kreisNrB][i] += swapA;

					objekte[kreisNrB][i] -= swapB;
					objekte[kreisNrA][i] += swapB;
				} else {

					objekte[kreisNrA][i] = objekte[kreisNrA][i] - swapA;
					objekte[kreisNrB][i] += swapA;
					objekte[kreisNrB][i] -= swapB;
					objekte[kreisNrA][i] += swapB;

				}

			}

		}
		drawKreise();
	}

	public void drawKreise() {
		removeAll();
		for (int i = 0; i < objekte.length; i++) {
			double masse = objekte[i][0];
			// double r = objekte[i][7] / objekte[i][0];
			// double g = objekte[i][1] / objekte[i][0];
			// double b = objekte[i][2] / objekte[i][0];
			double r = Math.random() * 256;
			double g = Math.random() * 256;
			double b = Math.random() * 256;

			double x;
			double y;
			double z;

			x = +objekte[i][1];

			// To Get Kreis --- Mehrere Objekte als kleinste einzelne Masse sollten auf den
			// Random Ort(x,y) eines
			// einzelnen kreises wirken

			y = +objekte[i][2];
			z = +objekte[i][3];
			double z2 = objekte[i][6] / objekte[i][0];
			;
			Color c;
			if (kreise[i] == null || kreise[i].getColor() == null) {

				r = Math.min(255, r);
				g = Math.min(255, g);
				b = Math.min(255, b);
				r = Math.max(10, r);
				g = Math.max(0, g);
				b = Math.max(0, b);

				c = new Color((int) r, (int) g, (int) b);
			} else
				c = kreise[i].getColor();
			kreise[i] = new GOval(10, 10);
			kreise[i].setFilled(true);
			kreise[i].sendToFront();
			// while (z > 0.1) {
			// kreise[i].sendBackward();
			// z /= 1.5;
			// }
			kreise[i].setColor(c);
			kreise[i].setFillColor(c);

			add(kreise[i], (x * 4.0), (y * 4.0));
		}
		drawButtons(1500, 30, 1, 8);
	}

	void drawButtons(int x, int y, int sizeX, int sizeY) {

		if (ButtonCount == null) {
			ButtonCount = new double[sizeX][sizeY];
			ButtonTitle = new String[sizeX][sizeY];
			buttonL = x;
			buttonO = y;
		}

		test2();

		for (int breite = 0; breite < sizeX; breite++) {
			for (int hohe = 0; hohe < sizeY; hohe++) {
				GRect feld = new GRect(100, 100);
				feld.setFilled(true);
				feld.setFillColor(new Color(0, 0, 0));
				feld.setColor(new Color(0, 253, 0));
				add(feld, breite * 100 + x, hohe * 100 + y);
				double count = ButtonCount[breite][hohe];
				String s = Double.toString(count);
				s = s.substring(0, Math.min(s.length() - 1, 6));
				GLabel l = new GLabel(s);
				l.setFont(l.getFont().deriveFont(30.0f));
				l.setColor(new Color(0, 253, 0));
				add(l, breite * 100 + x, hohe * 100 + y + 100);
				if (ButtonTitle[breite][hohe] == null)
					ButtonTitle[breite][hohe] = "";
				GLabel tit = new GLabel(ButtonTitle[breite][hohe]);
				tit.setFont(l.getFont().deriveFont(30.0f));
				tit.setColor(new Color(0, 253, 0));
				add(tit, breite * 100 + x, hohe * 100 + y + 40);

				testO = new GOval(gx, gy);
				add(testO, 50, 50);
			}
		}
	}

	double[][] ButtonCount;
	String[][] ButtonTitle;
	int buttonL;
	int buttonO;

	// Testpunkt
	double gx = 10;
	double gy = 10;
	GOval testO = new GOval(gx, gy);

	int moveKreis = strukt.length;

	public void test2() {
		ButtonTitle[0][1] = "zoom";
		zoom = ButtonCount[0][1];

		if (ButtonCount[2][1] != 0)
			zoom2 = ButtonCount[2][1];

		ButtonTitle[0][2] = "iter";
		t = (long) ButtonCount[0][2];
		ButtonTitle[0][0] = "moveKreis";
		moveKreis = (int) ButtonCount[0][0];

		ButtonTitle[1][1] = "kreisNummer";

		ButtonTitle[1][2] = "x";

		ButtonTitle[1][3] = "y";

		ButtonTitle[1][4] = "z";

		ButtonTitle[1][5] = "dx";

		ButtonTitle[1][6] = "dy";

		ButtonTitle[1][7] = "dz";

//		testO = new GOval(gx, gy);
//		add(testO, 50, 50);
	}

//	public void test() {
//		ButtonTitle[0][1] = "gx";
//		gx = ButtonCount[0][1];
//		ButtonTitle[0][4] = "gy";
//		gy = ButtonCount[0][4];
//
//		testO = new GOval(gx, gy);
//		add(testO, 50, 50);
//	}

	public void mousePressed(final MouseEvent m) {
		// raumzeit1();
		// getKreisGrav(1);
		//
//		if (m.getX() > 1500) {
//			if (t <= 0)
//				t = 1;
//			t *= 2;
//		}
//		if (m.getX() < 100)
//			t /= 2;
//		if (m.getY() > 800)
//			zoom *= 1.5;
//		if (m.getY() < 100)
//			zoom /= 1.5;

		// getKreisGravAutokl(10);

		// raumzeitneu(t);

		getKreisGrav4(t);

//		drawKreisePunkte(zoom);

		int x = m.getX();
		x = x - buttonL;
		int x2 = x / 50;
		x2 = x2 % 2;
		int x3 = x / 33;
		x3 = x3 % 3;
		x = x / 100;
		int y = m.getY();
		y = y - buttonO;
		int y2 = y / 50;
		y2 = y2 % 2;
		y = y / 100;
		int y3 = y / 33;
		y3 = y3 % 3;

//		if(ButtonCount==null)ButtonCount=new double[1][15];

		if (x >= 0 && x < 3 && y >= 0 && y < 15) {
			if (x2 == 0 && y2 == 1) {
				ButtonCount[x][y]--;
			}
			if (x2 == 1 && y2 == 1) {
				ButtonCount[x][y]++;
			}
			if (x3 == 0 && y2 == 0) {
				ButtonCount[x][y] *= 0.5;
			}
			if (x3 == 1 && y2 == 0) {
				ButtonCount[x][y] = 0;
			}
			if (x3 == 2 && y2 == 0) {
				ButtonCount[x][y] *= 2;
			}
		}
		if (ButtonCount != null && ButtonCount[1][1] >= 0 && ButtonCount[1][1] < strukt.length) {
			KreisPunkt chosKreis = strukt[(int) ButtonCount[1][1]];

			if (ButtonCount[1][0] == 1) {
				ButtonCount[1][2] = chosKreis.posX;
				ButtonCount[1][3] = chosKreis.posY;
				ButtonCount[1][4] = chosKreis.posZ;

				ButtonCount[1][5] = chosKreis.dx;
				ButtonCount[1][6] = chosKreis.dy;
				ButtonCount[1][7] = chosKreis.dz;
			}

			if (ButtonCount[1][0] == 2) {
				chosKreis.posX = ButtonCount[1][2];
				chosKreis.posY = ButtonCount[1][3];
				chosKreis.posZ = ButtonCount[1][4];
				chosKreis.dx = ButtonCount[1][5];
				chosKreis.dy = ButtonCount[1][6];
				chosKreis.dz = ButtonCount[1][7];
			}

			if (ButtonCount[1][0] == 4)
				generateMatStrukt(17);

			if (ButtonCount[1][0] == 6)
				MatStrukGrav2(t);

//		chosKreis.posY=(int) ButtonCount[1][3];
//		chosKreis.posZ=(int) ButtonCount[1][4];
//		chosKreis.dx=(int) ButtonCount[2][2];
//		chosKreis.dy=(int) ButtonCount[2][3];
//		chosKreis.dz=(int) ButtonCount[2][4];
		}

		if (x == 0 && y == 7) {
			if (ButtonCount[0][7] == 1)
				konstStrukt(ButtonCount[0][3], ButtonCount[0][4], ButtonCount[0][5], ButtonCount[0][6]);
			if (ButtonCount[0][7] == 2)
				konstStrukt2(ButtonCount[0][3], ButtonCount[0][4], ButtonCount[0][5], ButtonCount[0][6]);
			if (ButtonCount[0][7] == 3)
				konstStruct3(ButtonCount[0][3], ButtonCount[0][4], ButtonCount[0][5], ButtonCount[0][6]);
			if (ButtonCount[0][7] == 4)
				konstStrukt4(ButtonCount[0][3], ButtonCount[0][4], ButtonCount[0][5], ButtonCount[0][6]);
			if (ButtonCount[0][7] == 5)
				konstStrukt5(ButtonCount[0][3], ButtonCount[0][4], ButtonCount[0][5], ButtonCount[0][6]);
			if (ButtonCount[0][7] == 6)
				konstStrukt6(ButtonCount[0][3], ButtonCount[0][4], ButtonCount[0][5], ButtonCount[0][6]);
			if (ButtonCount[0][7] == 7)
				konstAtom(ButtonCount[0][3], ButtonCount[0][4], ButtonCount[0][5], ButtonCount[0][6]);

			// Ausgabe dunkle Energie
			if (ButtonCount[0][7] == -1)
				getExpansion();
		}
		// forsch für butten zahlen
		// test();

		// button 0

		drawButtons(1500, 30, 3, 8);
	}

	private void konstStrukt5(double maxx, double maxy, double maxz, double maxMass) {
		if (moveKreis > strukt.length)
			strukt = new KreisPunkt[moveKreis];

		int moveK = moveKreis;
		if (moveK > strukt.length || moveK < 1)
			moveK = strukt.length;

		for (int i = 0; i < moveK; i++) {

			int size = 7;

//			if (maxMass < 1)
//				maxMass = 1;

			double masse = maxMass * Math.random();

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
			r = (int) (255 * Math.random() * masse / maxMass);
			g = (int) (255 * Math.random() * masse / maxMass);
			b = (int) (255 * Math.random() * masse / maxMass);
			if (r < 0)
				r = -r;
			if (g < 0)
				g = -g;
			if (b < 0)
				b = -b;
//			}

//			 (masse / maxMass * 255 >= 66) {
			//
//				r = (int) (255 * Math.random());
//				g = (int) (255 * Math.random());
//				b = (int) (255 * Math.random());
//			}

			Color color = new Color(r, g, b);

			double posX = 0;
			double posY = 0;
			double posZ = 0;

			strukt[i] = new KreisPunkt(size, color, masse, posX, posY, posZ);

			strukt[i].dx = maxx * Math.random() / strukt[i].masse - maxx * Math.random() / strukt[i].masse;
			strukt[i].dy = maxy * Math.random() / strukt[i].masse - maxy * Math.random() / strukt[i].masse;
			strukt[i].dz = maxz * Math.random() / strukt[i].masse - maxz * Math.random() / strukt[i].masse;
		}
	}

	private void konstStrukt6(double maxx, double maxy, double maxz, double maxMass) {
		if (moveKreis > strukt.length)
			strukt = new KreisPunkt[moveKreis];

		int moveK = moveKreis;
		if (moveK > strukt.length || moveK < 1)
			moveK = strukt.length;

		for (int i = 0; i < moveK; i++) {

			int size = 7;

//			if (maxMass < 1)
//				maxMass = 1;

			double masse = maxMass * Math.random() / Math.random() / Math.random();

			if (masse < 1)
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
			r = (int) (255 * Math.random() * masse / maxMass);
			g = (int) (255 * Math.random() * masse / maxMass);
			b = (int) (255 * Math.random() * masse / maxMass);
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

			double posX = 0;
			double posY = 0;
			double posZ = 0;

			strukt[i] = new KreisPunkt(size, color, masse, posX, posY, posZ);

			strukt[i].dx = maxx * Math.random() / masse - maxx * Math.random() / masse;
			strukt[i].dy = maxy * Math.random() / masse - maxy * Math.random() / masse;
			strukt[i].dz = maxz * Math.random() / masse - maxz * Math.random() / masse;
		}
	}

	private void konstAtom(double maxx, double maxy, double maxz, double maxMass) {
		if (moveKreis > strukt.length)
			strukt = new KreisPunkt[moveKreis];

		int moveK = moveKreis;
		if (moveK > strukt.length || moveK < 1)
			moveK = strukt.length;

		for (int i = 0; i < moveK; i++) {

			int size = 7;

//			if (maxMass < 1)
//				maxMass = 1;

			int r = 6;
			int g = 6;
			int b = 6;
			double masse = 0;
			if (i < moveKreis / 1000.0) {
				masse = maxMass * Math.random();
				size = 15;
			} else if (i < moveKreis / 100.0) {
				masse = maxMass / 10.0 * Math.random();
				size = 12;
			} else if (i < moveKreis / 10.0) {
				masse = maxMass / 100.0 * Math.random();
				size = 9;
			} else {
				masse = maxMass / 1000.0 * Math.random();
				size = 6;
			}

//			if (masse < 1)
//				masse = 1;
//			else if(masse<0 && masse>1)masse=1;

//			if (masse == 1) {
			//
//				r = 66;
//				g = 66;
//				b = 66;
//			}

//			else if (masse / maxMass * 255 < 66) {
			r = (int) (255 * Math.random() * masse / maxMass);
			g = (int) (255 * Math.random() * masse / maxMass);
			b = (int) (255 * Math.random() * masse / maxMass);
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

			double posX = 0;
			double posY = 0;
			double posZ = 0;

			strukt[i] = new KreisPunkt(size, color, masse, posX, posY, posZ);

			strukt[i].dx = maxx * Math.random() / masse - maxx * Math.random() / masse;
			strukt[i].dy = maxy * Math.random() / masse - maxy * Math.random() / masse;
			strukt[i].dz = maxz * Math.random() / masse - maxz * Math.random() / masse;
		}
	}

//ToDO: Atome modellieren

	double zoom = 1000.0;
	long t = 5;

	public void run() {
		counter = 1;
		setBackground(new Color(10, 10, 10));
		setSize(3000, 3000);
		// initt();
		addMouseListeners();
		konstStrukt2(0, 0, 0, 5);
//		drawKreisePunkte2(0.2);
		// urknall();
	}

	public static void main(String[] args) {
		new MassekugelnReloaded7().start(args);
	}

}
