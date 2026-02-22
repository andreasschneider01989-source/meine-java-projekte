package farbpunkte;

import java.awt.Color;

public class KreisPunkt {

	// Darstellung
	int size;
	Color color;
	boolean filled;
	boolean nah;
	boolean fern;

	// Masse
	double masse;

	// Orts Eigenschaften
	double posX;
	double posY;
	double posZ;
	
	double dist0;

	double[] zusatzdim = new double[10];

	double pendelpunktX;
	double pendelpunktY;
	double pendelpunktZ;

	// initMaxima
	double posXMax;
	double posYMax;
	double posZMax;

	// Bewegung
	double dx;
	double dy;
	double dz;
	
	double speed0;

	double[] zusatzspeed = new double[10];

	// Nummer
	int nr;

	double[][] dimensionsarray;// [Raumdimensionen][Ableitungsdimensionen]

	// Blocknummern
	boolean[] bocknummern;

	KreisPunkt[] unterpunkte = new KreisPunkt[8];

	// Sekundär Atribute

	// Beschleunigung

	public void getKreisUnterp() {
		if (unterpunkte[0] == null)
			konstUnterStrukt();
		for (int i = 0; i < unterpunkte.length; i++) {

			int kreis = (int) (Math.random() * unterpunkte.length);
			int kreisb = (int) (Math.random() * unterpunkte.length);
			KreisPunkt a = unterpunkte[kreis];
			KreisPunkt b = unterpunkte[kreisb];
			a.grav(b);
		}
		setMasse();
		setX();
		setY();
		setZ();
	}

	public void setMasse() {
		double m = 0;
		for (int i = 0; i < unterpunkte.length; i++) {
			m += unterpunkte[i].masse;
		}
		masse = m;
	}

	public void setX() {
		double x = 0;
		for (int i = 0; i < unterpunkte.length; i++) {
			x += unterpunkte[i].posX;
		}
		posX = x / unterpunkte.length * Math.random();
	}

	public void setY() {
		double y = 0;
		for (int i = 0; i < unterpunkte.length; i++) {
			y += unterpunkte[i].posY;
		}
		posY = y / unterpunkte.length * Math.random();
	}

	public void setZ() {
		double z = 0;
		for (int i = 0; i < unterpunkte.length; i++) {
			z += unterpunkte[i].posZ;
		}
		posZ = z / unterpunkte.length * Math.random();
	}

	public void konstUnterStrukt() {

		for (int i = 0; i < unterpunkte.length; i++) {

			double maxMass = masse / unterpunkte.length;

			int size = this.size - 1;

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

			double posX = 3 * posXMax * Math.random() - 3 * posXMax * Math.random();
			double posY = 3 * posYMax * Math.random() - 3 * posYMax * Math.random();
			double posZ = 3 * posZMax * Math.random() - 3 * posZMax * Math.random();
			;

			unterpunkte[i] = new KreisPunkt(size, color, masse, posX, posY, posZ);

//			strukt[i].dx =  maxx * Math.random() - maxx * Math.random();
//			strukt[i].dy = maxy * Math.random() - maxy * Math.random();
//			strukt[i].dz = maxz * Math.random() - maxz * Math.random();

			unterpunkte[i].dx = 0;
			unterpunkte[i].dy = 0;
			unterpunkte[i].dz = 0;
		}
	}

	public void dimmove() {
		// System.out.println(dx);
		// posX = posX + (dx * 0);
		// if(dx>150000)dx=150000;
		// if(dy>150000)dy=150000;
		// if(dz>150000)dz=150000;
		// if(dx<-150000)dx=-150000;
		// if(dy<-150000)dy=-150000;
		// if(dz<-150000)dz=-150000;

		for (int ab = 0; ab < dimensionsarray[0].length; ab++) {
			for (int dim = 0; dim < dimensionsarray.length; dim++) {
				if (ab > 0)
					dimensionsarray[dim][ab] = dimensionsarray[dim][ab] + dimensionsarray[dim][ab - 1] * 0.00000001;

			}
		}
		posX = dimensionsarray[0][0];
		posY = dimensionsarray[1][0];
		posZ = dimensionsarray[2][0];
		dx = dimensionsarray[0][1];
		dy = dimensionsarray[1][1];
		dz = dimensionsarray[2][1];
	}

	public void beschleunigen(double ax, double ay, double az) {
		dx = dx + ax;
		dy = dy + ay;
		dz = dz + az;
	}

	// Beschleunigt in richtung Punkt mit a
	public void beschleunigenP(double a, double x, double y, double z) {
		double ax = x - posX;
		double ay = y - posY;
		double az = z - posZ;
		double n = Math.abs(ax) + Math.abs(ay) + Math.abs(az);
		ax = ax / n * a;
		ay = ay / n * a;
		az = az / n * a;
		dx = dx + ax;
		dy = dy + ay;
		dz = dz + az;
	}

	public void grav(KreisPunkt kp) {
//		double distq=(posX-kp.posX)*(posX-kp.posX)+(posY-kp.posY)*(posY-kp.posY)+(posZ-kp.posZ)*(posZ-kp.posZ);
//		double athis=10000.0/distq/masse;
//		double a2 = 10000.0/distq/kp.masse;
//		this.beschleunigenP(athis, kp.posX, kp.posY, kp.posZ);
//		kp.beschleunigenP(a2, posX, posY, posZ);
//		randbesch();
//		kp.randbesch();
		
		this.kpbesch(kp);
		this.move();
		kp.kpbesch(this);	
		kp.move();
	}

	public void gravpendel(KreisPunkt kp) {
//		double distq=(posX-kp.posX)*(posX-kp.posX)+(posY-kp.posY)*(posY-kp.posY)+(posZ-kp.posZ)*(posZ-kp.posZ);
//		double athis=10000.0/distq/masse;
//		double a2 = 10000.0/distq/kp.masse;
//		this.beschleunigenP(athis, kp.posX, kp.posY, kp.posZ);
//		kp.beschleunigenP(a2, posX, posY, posZ);
//		randbesch();
//		kp.randbesch();

		kp.pendeln();
		kp.kpbeschR(this);
		kp.move();
	}

//	public void randbesch() {
//		dx+=Math.random();
//		dy+=Math.random();
//		dz+=Math.random();
//		dx-=Math.random();
//		dy-=Math.random();
//		dz-=Math.random();
//	}

	public void kpbesch(KreisPunkt kp) {
		double distq=(posX-kp.posX)*(posX-kp.posX)+(posY-kp.posY)*(posY-kp.posY);
		double dist=Math.pow(distq, 0.5);
		distq*=10000;
		double ax=(kp.posX-posX)/dist;
		double ay=(kp.posY-posY)/dist;
		double az=(kp.posZ-posZ)/dist;
		
//		double ax=(kp.posX-posX);
//		double ay=(kp.posY-posY);
//		double az=(kp.posZ-posZ);
		
		if(distq>0 && distq<1)distq=1;
//		else if(distq<0 && distq>-1)distq=-1;
		
		ax=ax*kp.masse/distq;
		ay=ay*kp.masse/distq;
		az=az*kp.masse/distq;
		
		// Das ax ay az darf nicht über den kp hinaus fliegen
		
		if(distq!=0) {
		
		
		this.dx+=ax;
		this.dy+=ay;
		this.dz+=az;
		
//		dx=dx+(kp.posX-posX);
//		dy=dy+(kp.posY-posY);
//		dz=dz+(kp.posZ-posZ);
	}}
	
	public void kpbeschNorm(KreisPunkt kp) {
		double distq = (posX - kp.posX) * (posX - kp.posX) + (posY - kp.posY) * (posY - kp.posY)
				+ (posZ - kp.posZ) * (posZ - kp.posZ);

//		for (int i = 0; i < zusatzdim.length; i++) {
//			double sqr = (zusatzdim[i] - kp.zusatzdim[i]) * (zusatzdim[i] - kp.zusatzdim[i]);
//			distq += sqr;
//		}
		
		distq=Math.pow(distq, 0.5);
//		System.out.println(distq);

//		distq *= 1000000;
//		if(distq<0)distq=-distq;
//		if(distq<1024)distq=1024.0;
//		while(distq< 0.1)distq*=10000;
		double ax = (kp.posX - posX);
		double ay = (kp.posY - posY);
		double az = (kp.posZ - posZ);
		
		double norm = 1/distq;

		double[] azusatz = new double[zusatzdim.length];

//		for (int i = 0; i < zusatzdim.length; i++) {
//			azusatz[i] = kp.zusatzdim[i] - zusatzdim[i];
//			azusatz[i] = azusatz[i] * kp.masse / (double)distq;
//		}

		ax = ax*norm * kp.masse / distq/distq;
		ay = ay*norm * kp.masse / distq/distq;
		az = az*norm * kp.masse / distq/distq;
		
//		System.out.println(ax);

		// Das ax ay az darf nicht über den kp hinaus fliegen

		if (distq != 0) {

			this.dx += ax;
			this.dy += ay;
			this.dz += az;

			for (int i = 0; i < zusatzdim.length; i++) {
				this.zusatzspeed[i] += azusatz[i];
			}

//		dx=dx+(kp.posX-posX);
//		dy=dy+(kp.posY-posY);
//		dz=dz+(kp.posZ-posZ);
		}
	}

	public void pendeln() {
		double distq = (posX - pendelpunktX) * (posX - pendelpunktX) + (posY - pendelpunktY) * (posY - pendelpunktY)
				+ (posZ - pendelpunktZ) * (posZ - pendelpunktZ);
		distq *= 1000000;
		if (distq < 0)
			distq = -distq;
		if (distq < 1024)
			distq = 1024.0;

		double ax = (posX - pendelpunktX);
		double ay = (posY - pendelpunktY);
		double az = (posZ - pendelpunktZ);

		ax = ax / distq;
		ay = ay / distq;
		az = az / distq;

		// Das ax ay az darf nicht über den kp hinaus fliegen

		if (distq > 1) {

			this.dx += ax;
			this.dy += ay;
			this.dz += az;

//		dx=dx+(kp.posX-posX);
//		dy=dy+(kp.posY-posY);
//		dz=dz+(kp.posZ-posZ);
		}
	}

	public void kpbeschR(KreisPunkt kp) {
		double distq = (posX - kp.posX) * (posX - kp.posX) + (posY - kp.posY) * (posY - kp.posY)
				+ (posZ - kp.posZ) * (posZ - kp.posZ);
		distq *= 1000000;
		if (distq < 0)
			distq = -distq;
		if (distq < 1024)
			distq = 1024.0;
//		while(distq< 0.1)distq*=10000;
		double ax = (kp.dx - dx);
		double ay = (kp.dy - dy);
		double az = (kp.dz - dz);

		ax = ax * kp.masse / distq;
		ay = ay * kp.masse / distq;
		az = az * kp.masse / distq;

		// Das ax ay az darf nicht über den kp hinaus fliegen

		if (distq != 0) {

			this.dx += ax;
			this.dy += ay;
			this.dz += az;

			kp.dx -= ax / kp.masse;
			kp.dy -= ay / kp.masse;
			kp.dz -= az / kp.masse;

//		dx=dx+(kp.posX-posX);
//		dy=dy+(kp.posY-posY);
//		dz=dz+(kp.posZ-posZ);
		}
	}

	public void kpbesch2(KreisPunkt kp) {
		double distq = (posX - kp.posX) * (posX - kp.posX) + (posY - kp.posY) * (posY - kp.posY);
		distq *= 10000;
		double ax = (kp.posX - posX);
		double ay = (kp.posY - posY);
		double az = (kp.posZ - posZ);

		ax = ax * kp.masse / distq;
		ay = ay * kp.masse / distq;
		az = az * kp.masse / distq;

		// Das ax ay az darf nicht über den kp hinaus fliegen

		if (distq != 0) {

//			if(this.posX<-this.posXMax&&this.dx<0)this.dx++;
//			else if(this.posX>this.posXMax&&this.dx>0)this.dx--;
//			if(this.posY<-this.posYMax&&this.dy<0)this.dy++;
//			else if(this.posY>this.posYMax&&this.dy>0)this.dy--;
//			if(this.posZ<-this.posZMax&&this.dz<0)this.dz++;
//			else if(this.posZ>this.posZMax&&this.dz>0)this.dz--;

			this.dx -= ax;
			this.dy -= ay;
			this.dz -= az;

			if (this.posX < -200) {
				this.posX = -200;
				dx += Math.random();
			}
			if (this.posX > 200) {
				this.posX = 200;
				dx -= Math.random();
			}
			if (this.posY < -200) {
				this.posY = -200;
				;
				dy += Math.random();
			}
			if (this.posY > 200) {
				this.posY = 200;
				;
				dy -= Math.random();
			}
			if (this.posZ < -200) {
				this.posZ = -200;
				dz += Math.random();
			}
			if (this.posZ > 200) {
				this.posZ = 200;
				dz -= Math.random();
			}

		}

//		dx=dx+(kp.posX-posX);
//		dy=dy+(kp.posY-posY);
//		dz=dz+(kp.posZ-posZ);
	}

	public KreisPunkt(int size, Color color, double masse, double posX, double posY, double posZ) {
		filled = true;
		dx = 0;
		dy = 0;
		dz = 0;
		this.size = size;
		this.color = color;
		this.masse = masse;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;

		for (int i = 0; i < zusatzdim.length; i++) {
			zusatzdim[i] = Math.random();
		}
	}

	public void beschleunigung(double fx, double fy, double fz) {
		if (masse != 0)
			beschleunigen(fx / masse, fy / masse, fz / masse);
	}

	// Wechselwirkung mit anderem Kreis
	public double gravity(KreisPunkt b) {
		if (posX == b.posX && posY == b.posY && posZ == b.posZ)
			return 0;
		// double tempoq= (dx-b.dx)*(dx-b.dx)+(dy-b.dy)*(dy-b.dy)+(dz-b.dz)*(dz-b.dz);
		// double tempo=Math.pow(tempoq, 0.5);

		double gf = masse * b.masse / ((posX - b.posX) * (posX - b.posX)
				+ (posY - b.posY) * (posY - b.posY) * (posZ - b.posZ) * (posZ - b.posZ));

		return gf;
	}

	public double beschleunigungNeu(KreisPunkt b) {
		double a = 0;
		// a=b.masse/dist
		a = b.masse / ((this.posX - b.posX) * (this.posX - b.posX) + (this.posY - b.posY) * (this.posY - b.posY)
				+ (this.posZ - b.posZ) * (this.posZ - b.posZ));
		return a;
	}

	// Kraftrichtungsvektor von a richtung b und von b richtung a
	public double[] richtungsvektor(KreisPunkt b) {
		double[] vektor = new double[3];
		vektor[0] = b.posX - posX;
		vektor[1] = b.posY - posY;
		vektor[2] = b.posZ - posZ;
		return vektor;
	}

	// Richtungsvektor mit gravity multiplizieren und fx fy fz in beschleunigung
	// This Kreis beschleunigt richtung Kreis b
	public void eigenbeschleunigung(KreisPunkt b) {
		double[] vektor = richtungsvektor(b);

		double fx = vektor[0] * gravity(b);
		double fy = vektor[1] * gravity(b);
		double fz = vektor[2] * gravity(b);
		// System.out.println(fx);
		beschleunigung(fx, fy, fz);
	}

	public void move() {
//		 System.out.println(dx);
		// posX = posX + (dx * 0);
		// if(dx>150000)dx=150000;
		// if(dy>150000)dy=150000;
		// if(dz>150000)dz=150000;
		// if(dx<-150000)dx=-150000;
		// if(dy<-150000)dy=-150000;
		// if(dz<-150000)dz=-150000;
		
		double r = Math.random();
		
		double rand = 10000000;
		
		
		posX = posX + (dx*r);
		if(posX>rand) posX=-rand;
		else if(posX<-rand) posX=rand;
		posY = posY + (dy*r);
		if(posY>rand) posY=-rand;
		else if(posY<-rand) posY=rand;
		posZ = posZ + (dz*r);
		if(posZ>rand) posZ=-rand;
		else if(posZ<-rand) posZ=rand;

		for (int i = 0; i < zusatzdim.length; i++) {
			zusatzdim[i] = zusatzdim[i] + zusatzspeed[i]*r;
		}

	}

	public double getSpeed() {
		double speed = Math.pow(dx * dx * 0.01 + dy * dy * 0.01 + dz * dz * 0.01, 0.5);
		return speed;
	}

	public void move2() {
		// System.out.println(dx);
		// posX = posX + (dx * 0);
		// if(dx>150000)dx=150000;
		// if(dy>150000)dy=150000;
		// if(dz>150000)dz=150000;
		// if(dx<-150000)dx=-150000;
		// if(dy<-150000)dy=-150000;
		// if(dz<-150000)dz=-150000;
//		posX = posX + Math.min(10, Math.max(-10, dx));
//		posY = posY + Math.min(10, Math.max(-10, dy));
//		posZ = posZ +  Math.min(10, Math.max(-10, dz));
		posX++;
	}

	public void move3(double dx, double dy, double dz) {

		posX += dx;
		posY += dy;
		posZ += dz;
	}

	// wechselwire eigenbesch jedes mit jedem und addiere danach jeweil den Speed
	// zur x,y und zpos

}
