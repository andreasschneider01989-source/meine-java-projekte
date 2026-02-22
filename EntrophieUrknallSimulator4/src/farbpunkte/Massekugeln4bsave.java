package farbpunkte;

import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GOval;

import acm.program.GraphicsProgram;

public class Massekugeln4bsave extends GraphicsProgram {

	double[][] objekte = new double[1000][16]; // Atribute 0-16
	GOval[] kreise = new GOval[1000];

	public void initt() {
		for (int i = 0; i < objekte.length; i++) {
			// objekte[i][0] = 10.0; //ein PUnkt als Gravitationszentrum
			// if(i%8==0)objekte[i][0] = 100.0;
			objekte[i][0] = Math.random() * 255; // Gerade, Diagonale durch 0,0? als Gravitationszentrum
			for (int j = 1; j < 4; j++) {
				objekte[i][j] = Math.random() * 255;
			}
			for (int j = 4; j < 7; j++) {
				objekte[i][j] = -5.0 + Math.random() * 10;
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

	public void getKreisGravAutokl() {
		long time = System.currentTimeMillis();
		while (System.currentTimeMillis() - time < 1000) {
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

					// Entropie Massenaustausch

					/// Astausch von Masse: Je geringer der Geschwindigkeitsdifferenz sowie dies

					// double swapA = objekte[kreisNrA][0] * Math.random();
					// double swapB = objekte[kreisNrB][0] * Math.random();
					//
					// swapA = swapA / dist;
					// swapB = swapB / dist;
					//
					// objekte[kreisNrA][0] -= swapA;
					// objekte[kreisNrB][0] += swapA;
					//
					// objekte[kreisNrB][0] -= swapB;
					// objekte[kreisNrA][0] += swapB;

					for (int i = 4; i < 7; i++) {

						// double swapA = objekte[kreisNrA][i] * Math.random();
						// double swapB = objekte[kreisNrB][i] * Math.random();
						//
						// swapA = swapA;// mal chaoswert /masse
						// swapB = swapB;

						double swapA = 500.0 / dist;
						double swapB = swapA * MB / MA;

						if ((objekte[kreisNrA][i] >= objekte[kreisNrB][i] + swapA * MB / MA)||(objekte[kreisNrA][i] >255)) {
							objekte[kreisNrA][i] -= (swapA * MB / MA);
							objekte[kreisNrB][i] += (swapA * MA / MB);
						} else if (objekte[kreisNrA][i] >= objekte[kreisNrB][i]) {
							objekte[kreisNrA][i] -= (swapB * MB / MA);
							objekte[kreisNrB][i] += (swapB * MA / MB);
						} else if((objekte[kreisNrA][i]+(swapA * MB / MA) < objekte[kreisNrB][i])||(objekte[kreisNrA][i] <255)){
							objekte[kreisNrA][i] += (swapA * MB / MA);
							objekte[kreisNrB][i] -= (swapA * MA / MB);
						}
						else if(objekte[kreisNrA][i] < objekte[kreisNrB][i]){
							objekte[kreisNrA][i] += (swapB * MB / MA);
							objekte[kreisNrB][i] -= (swapB * MA / MB);
						}
						// objekte[kreisNrA][i] -= (swapA*MB/MA);
						// objekte[kreisNrB][i] += (swapA*MA/MB);
						//
						// objekte[kreisNrB][i] -= (swapB*MA/MB);
						// objekte[kreisNrA][i] += (swapB*MB/MA);
					}
				}
			}
		}
		drawKreise();
	}

	public void kreisGeschwindgkeit(int kreisNr) {

		objekte[kreisNr][1] += objekte[kreisNr][4];
		objekte[kreisNr][2] += objekte[kreisNr][5];
		objekte[kreisNr][3] += objekte[kreisNr][6];

	}

	public void getKreisGrav2() {
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
					if (dist < 0.00001)
						dist = 0.00001;

					// Entropie Massenaustausch

					/// Astausch von Masse: Je geringer der Geschwindigkeitsdifferenz sowie dies

					// double swapA = objekte[kreisNrA][0] * Math.random();
					// double swapB = objekte[kreisNrB][0] * Math.random();
					//
					// swapA = swapA / dist;
					// swapB = swapB / dist;
					//
					// objekte[kreisNrA][0] -= swapA;
					// objekte[kreisNrB][0] += swapA;
					//
					// objekte[kreisNrB][0] -= swapB;
					// objekte[kreisNrA][0] += swapB;

					for (int i = 4; i < 7; i++) {

						// double swapA = objekte[kreisNrA][i] * Math.random();
						// double swapB = objekte[kreisNrB][i] * Math.random();
						//
						// swapA = swapA;// mal chaoswert /masse
						// swapB = swapB;

						double swapA = 250.0 / dist;
						double swapB = swapA * MB / MA;

						if ((objekte[kreisNrA][i] >= objekte[kreisNrB][i] + swapA * MB / MA)||(objekte[kreisNrA][i] >255)) {
							objekte[kreisNrA][i] -= (swapA * MB / MA);
							objekte[kreisNrB][i] += (swapA * MA / MB);
						} else if (objekte[kreisNrA][i] >= objekte[kreisNrB][i]) {
							objekte[kreisNrA][i] -= (swapB * MB / MA);
							objekte[kreisNrB][i] += (swapB * MA / MB);
						} else if((objekte[kreisNrA][i]+(swapA * MB / MA) < objekte[kreisNrB][i])||(objekte[kreisNrA][i] <255)){
							objekte[kreisNrA][i] += (swapA * MB / MA);
							objekte[kreisNrB][i] -= (swapA * MA / MB);
						}
						else if(objekte[kreisNrA][i] < objekte[kreisNrB][i]){
							objekte[kreisNrA][i] += (swapB * MB / MA);
							objekte[kreisNrB][i] -= (swapB * MA / MB);
						}
						// objekte[kreisNrA][i] -= (swapA*MB/MA);
						// objekte[kreisNrB][i] += (swapA*MA/MB);
						//
						// objekte[kreisNrB][i] -= (swapB*MA/MB);
						// objekte[kreisNrA][i] += (swapB*MB/MA);
					}
				}
			}
		}
		drawKreise();
	}

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

		if (m.getX() > 1000)
			getKreisGravAutokl();
		else
			getKreisGrav();

		for (int i = 0; i < objekte.length; i++)
			kreisGeschwindgkeit(i);
	}

	public void run() {
		counter = 1;
		setBackground(new Color(10, 10, 10));
		setSize(3000, 3000);
		initt();
		addMouseListeners();
		// urknall();
	}

	public static void main(String[] args) {
		new Massekugeln4bsave().start(args);
	}

}
