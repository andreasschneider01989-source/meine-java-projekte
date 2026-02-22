package farbpunkte;

import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GOval;

import acm.program.GraphicsProgram;

public class MassekugelnNextSave2 extends GraphicsProgram {

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

	public void getKreisGrav2(long it) {
		for (int i = 0; i < it; i++) {
			KreisPunkt a = strukt[(int) (Math.random() * strukt.length)];
			KreisPunkt b = strukt[(int) (Math.random() * strukt.length)];
			// if (a == null || b == null) {
			// konstStrukt(1000, 1000, 100, 1000);
			// }
			// a.eigenbeschleunigung(b);
			// a.move();
			boolean notme = false;

			double dismul = 5;

			double distQ1 = (a.posX * dismul - b.posX * dismul) * (a.posX * dismul - b.posX * dismul);
			if (distQ1 != 0)
				notme = true;
			double distQ2 = (a.posX * dismul - b.posX * dismul) * (a.posX * dismul - b.posX * dismul);
			if (distQ2 != 0)
				notme = true;
			double distQ3 = (a.posX * dismul - b.posX * dismul) * (a.posX * dismul - b.posX * dismul);
			if (distQ3 != 0)
				notme = true;

			// if (a.bocknummern[b.nr]) {
			// if (Math.random() > 0.9)
			// a.bocknummern[b.nr] = false;
			// else
			// notme = false;
			// }

			double distQ12 = distQ1 + distQ2;
			double distQ23 = distQ2 + distQ3;
			double distQ13 = distQ1 + distQ3;
			double distQ123 = distQ1 + distQ2 + distQ3;

			if (notme) {
				double swapAX = a.posX / distQ1;
				double swapBX = b.posX / distQ1;
				double swapAY = a.posY / distQ2;
				double swapBY = b.posY / distQ2;
				double swapAZ = a.posZ / distQ3;
				double swapBZ = b.posZ / distQ3;

				swapAX = swapAX + (a.posX / distQ12);
				swapBX = swapBX + (b.posX / distQ12);
				swapAY = swapAY + (a.posY / distQ12);
				swapBY = swapBY + (b.posY / distQ12);
				swapAY = swapAY + (a.posY / distQ23);
				swapBY = swapBY + (b.posY / distQ23);
				swapAX = swapAX + (a.posX / distQ12);
				swapBX = swapAX + (b.posX / distQ12);
				swapAZ = swapAZ + (a.posZ / distQ13);
				swapBZ = swapBZ + (b.posZ / distQ13);
				swapAX = swapAX + (a.posX / distQ13);
				swapBX = swapBX + (b.posX / distQ13);

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
	KreisPunkt[] strukt = new KreisPunkt[200];

	// strukt konstruieren
	public void konstStrukt(double maxx, double maxy, double maxz, double maxMass) {

		for (int i = 0; i < strukt.length; i++) {
			int size = 10;
			Color color = new Color((int) (Math.random() * 255), (int) (Math.random() * 255),
					(int) (Math.random() * 255));
			double masse = 0;
			if (i < 2) {
				masse = 100;
				size = 15;
			}
			// {
			// masse = Math.random() * maxMass;
			// // masse
			// if (maxMass * 0.75 < masse)
			// size++;
			// else if (maxMass * 0.25 > masse)
			// size--;
			// }
			else {
				masse = maxMass / Math.random();
			}
			// if(masse>5*maxMass)size=20;
			// if(masse>25*maxMass)size=25;
			// masse = 100;

			double posX = Math.random() * maxx;
			double posY = Math.random() * maxy;
			double posZ = Math.random() * maxz;

			strukt[i] = new KreisPunkt(size, color, masse, posX, posY, posZ);
			strukt[i].nr = i;
			strukt[i].bocknummern = new boolean[strukt.length];
			strukt[i].posXMax = maxx;
			strukt[i].posYMax = maxy;
			strukt[i].posZMax = maxz;

			// posZ
			if (maxz * 0.75 < posZ) {
				size++;
				strukt[i].nah = true;
			} else if (maxz * 0.25 > posZ) {
				size--;
				strukt[i].fern = true;
			}
		}
	}

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

	public void raumzeitneu(int iter) {
		for (int i = 0; i < iter; i++)
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
	}

	public void mousePressed(final MouseEvent m) {
		// raumzeit1();
		// getKreisGrav(1);
		//
		if (m.getX() > 1000)
			t *= 2;
		if (m.getX() < 100)
			t /= 2;

		// getKreisGravAutokl(10);

		// raumzeitneu(200);
		getKreisGrav2(t);
		drawKreisePunkte();
	}

	long t = 100;

	public void run() {
		counter = 1;
		setBackground(new Color(10, 10, 10));
		setSize(3000, 3000);
		// initt();
		addMouseListeners();
		konstStrukt(1000, 1000, 2, 2);
		// urknall();
	}

	public static void main(String[] args) {
		new MassekugelnNextSave2().start(args);
	}

}
