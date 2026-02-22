package farbpunkte;

import java.awt.Color;

public class MaterieStruktur {

	int unterstufe;

	double xMin;
	double xMax;
	double yMin;
	double yMax;
	double zMin;
	double zMax;
	
	double xAvg;
	double yAvg;
	double zAvg;

	double masse;

	MaterieStruktur unterstruktur;
	MaterieStruktur unterstruktur2;
	
	KreisPunkt kreispunkt;

	public MaterieStruktur(int unterstufe,double xMin, double xMax, double yMin, double yMax, double zMin, double zMax, double masse) {
		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;
		this.zMin = zMin;
		this.zMax = zMax;
		this.masse = masse;
		this.unterstufe=unterstufe;
		
		if(unterstufe>0) {this.unterstruktur=makeRandUnterstrukts(); this.unterstruktur2=makeRandUnterstrukts();}
		else {xAvg=xMin+xMax/2; yAvg=yMin+yMax/2; zAvg=zMin+zMax/2;
		Color color = new Color((int)(Math.random()*255), (int)(Math.random()*255),(int)(Math.random()*255));
		kreispunkt= new KreisPunkt(5,color,masse,xAvg,yAvg,zAvg);}
		
	}

	public MaterieStruktur makeRandUnterstrukts() {
		
		int stufe =this.unterstufe-1;;
		
		double xMin;
		double xMax;
		double yMin;
		double yMax;
		double zMin;
		double zMax;
		
		double masse;
		
		
		
		if(Math.random()<0.5) {xMin=this.xMin;xMax=this.xMin+this.xMax/2;}else {xMin=this.xMin+this.xMax/2;xMax=this.xMax;}
		if(Math.random()<0.5) {yMin=this.yMin;yMax=this.yMin+this.yMax/2;}else {yMin=this.yMin+this.yMax/2;yMax=this.yMax;}
		if(Math.random()<0.5) {zMin=this.zMin;zMax=this.zMin+this.zMax/2;}else {zMin=this.zMin+this.zMax/2;zMax=this.zMax;}
		masse=this.masse*Math.random();
			
		MaterieStruktur unterstruktur = new MaterieStruktur(stufe,xMin,xMax,yMin,yMax,zMin,zMax,masse);
		return unterstruktur;
	}

}
