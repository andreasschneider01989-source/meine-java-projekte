package farbpunkte;

import java.awt.Color;

public class KreispunktAlt {

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

	// initMaxima
	double posXMax;
	double posYMax;
	double posZMax;

	// Bewegung
	double dx;
	double dy;
	double dz;

	// Nummer
	int nr;

	double[][] dimensionsarray;// [Raumdimensionen][Ableitungsdimensionen]

	// Blocknummern
	boolean[] bocknummern;

	// Sekundär Atribute

	// Beschleunigung

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
					dimensionsarray[dim][ab] = dimensionsarray[dim][ab]+ dimensionsarray[dim][ab - 1]*0.00000001;

			}
		}
		posX = dimensionsarray[0][0];
		posY = dimensionsarray[1][0];
		posZ = dimensionsarray[2][0];
		dx= dimensionsarray[0][1];
		dy= dimensionsarray[1][1];
		dz= dimensionsarray[2][1];
	}

	public void beschleunigen(double ax, double ay, double az) {
		dx = dx + ax;
		dy = dy + ay;
		dz = dz + az;
	}
	
	
	// Beschleunigt in richtung Punkt mit a
	public void beschleunigenP(double a, double x, double y, double z) {
		double ax=x-posX;
		double ay=y-posY;
		double az=z-posZ;
		double n=Math.abs(ax)+Math.abs(ay)+Math.abs(az);
		ax=ax/n*a;
		ay=ay/n*a;
		az=az/n*a;
		dx = dx + ax;
		dy = dy + ay;
		dz = dz + az;
	}
	
	public void grav(KreispunktAlt kp) {
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
	
	public void gravNoMove(KreispunktAlt kp) {

		
		this.kpbesch(kp);

		kp.kpbesch(this);	

	}
	
//	public void randbesch() {
//		dx+=Math.random();
//		dy+=Math.random();
//		dz+=Math.random();
//		dx-=Math.random();
//		dy-=Math.random();
//		dz-=Math.random();
//	}
	
	public void kpbesch(KreispunktAlt kp) {
		double distq=(posX-kp.posX)*(posX-kp.posX)+(posY-kp.posY)*(posY-kp.posY);
		distq*=10000;
		double ax=(kp.posX-posX);
		double ay=(kp.posY-posY);
		double az=(kp.posZ-posZ);
		
		if(distq>0 && distq<1)distq=1;
		else if(distq<0 && distq>-1)distq=-1;
		
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

	public KreispunktAlt(int size, Color color, double masse, double posX, double posY, double posZ) {
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
	}

	public void beschleunigung(double fx, double fy, double fz) {
		if (masse != 0)
			beschleunigen(fx / masse, fy / masse, fz / masse);
	}

	// Wechselwirkung mit anderem Kreis
	public double gravity(KreispunktAlt b) {
		if (posX == b.posX && posY == b.posY && posZ == b.posZ)
			return 0;
		// double tempoq= (dx-b.dx)*(dx-b.dx)+(dy-b.dy)*(dy-b.dy)+(dz-b.dz)*(dz-b.dz);
		// double tempo=Math.pow(tempoq, 0.5);

		double gf = masse * b.masse / ((posX - b.posX) * (posX - b.posX)
				+ (posY - b.posY) * (posY - b.posY) * (posZ - b.posZ) * (posZ - b.posZ));

		return gf;
	}

	public double beschleunigungNeu(KreispunktAlt b) {
		double a = 0;
		// a=b.masse/dist
		a = b.masse / ((this.posX - b.posX) * (this.posX - b.posX) + (this.posY - b.posY) * (this.posY - b.posY)
				+ (this.posZ - b.posZ) * (this.posZ - b.posZ));
		return a;
	}

	// Kraftrichtungsvektor von a richtung b und von b richtung a
	public double[] richtungsvektor(KreispunktAlt b) {
		double[] vektor = new double[3];
		vektor[0] = b.posX - posX;
		vektor[1] = b.posY - posY;
		vektor[2] = b.posZ - posZ;
		return vektor;
	}

	// Richtungsvektor mit gravity multiplizieren und fx fy fz in beschleunigung
	// This Kreis beschleunigt richtung Kreis b
	public void eigenbeschleunigung(KreispunktAlt b) {
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
		
		
		
		
		
		posX = posX + (dx*0.1 );
		posY = posY + (dy*0.1 );
		posZ = posZ + (dz*0.1 );
		if(posZ>posZMax/2) {nah=false;fern=true;}
		else if(posZ<-(posZMax/2)) {nah=true;fern=false;}
		else {nah=false;fern=false;}
	}
	
	public double getSpeed() {
		double speed=Math.pow(dx*dx*0.01+dy*dy*0.01+dz*dz*0.01,0.5);
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
	
	public void move3(double dx,double dy,double dz) {
		
		posX+=dx;
		posY+=dy;
		posZ+=dz;
	}

	// wechselwire eigenbesch jedes mit jedem und addiere danach jeweil den Speed
	// zur x,y und zpos

}
