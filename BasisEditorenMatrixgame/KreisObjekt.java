package BasisEditorenMatrixgame;

public class KreisObjekt {
  double size;
  double red;
  double green;
  double blue;
  
  double posX;
  double posY;
  double posZ;
  
  double vx;
  double vy;
  double vz;
  
  public KreisObjekt(double size, double red, double green, double blue, double posX, double posY, double posZ,double vx, double vy, double vz) {
		this.size = size;
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.vx = vx;
		this.vy = vy;
		this.vz = vz;
	}
  
public KreisObjekt(double size, double red, double green, double blue, double posX, double posY, double posZ) {
	this.size = size;
	this.red = red;
	this.green = green;
	this.blue = blue;
	this.posX = posX;
	this.posY = posY;
	this.posZ = posZ;
}


public KreisObjekt(double posX, double posY) {
	this.posX = posX;
	this.posY = posY;
	this.size = 10.0;
	this.red = 255;
	this.green = 0;
	this.blue = 0;
	this.posZ = 0;
}

//Default mit interessantem Random
public KreisObjekt () {
	this.size = 50*Math.random();
	this.red = 20*Math.random()/Math.random()-20*Math.random()/Math.random();
	this.green = 20*Math.random()/Math.random()-20*Math.random()/Math.random();
	this.blue = 20*Math.random()/Math.random()-20*Math.random()/Math.random();
	this.posX = 20*Math.random()/Math.random()-20*Math.random()/Math.random();
	this.posY =20*Math.random()/Math.random()-20*Math.random()/Math.random();
	this.posZ = 20*Math.random()/Math.random()-20*Math.random()/Math.random();
	this.vx = 20*Math.random()/Math.random()-20*Math.random()/Math.random();
	this.vy = 20*Math.random()/Math.random()-20*Math.random()/Math.random();
	this.vz = 20*Math.random()/Math.random()-20*Math.random()/Math.random();
}

public void move (double time) {
	posX=posX+vx*time;
	posY=posY+vy*time;
	posZ=posZ+vz*time;
}




  
  
}
