package BasisEditorenMatrixgame;

public class Unit extends KreisObjekt {
	double leben;
	double damage;
	double distance;
	double speed;
	int team;

	public void moveTo(KreisObjekt oponent) {
		double distQ = (this.posX - oponent.posX) * (this.posX - oponent.posX)
				+ (this.posY - oponent.posY) * (this.posY - oponent.posY)
				+ (this.posZ - oponent.posZ) * (this.posZ - oponent.posZ);
		double dist = Math.pow(distQ, 0.5);
		
		double dx=posX-oponent.posX;
		double dy=posY-oponent.posY;
		double dz=posZ-oponent.posZ;
		
		vx=-dx/dist*speed;
		vy=-dy/dist*speed;
		vz=-dz/dist*speed;

	}
	
	public double getDistance(KreisObjekt oponent) {
		double distQ = (this.posX - oponent.posX) * (this.posX - oponent.posX)
				+ (this.posY - oponent.posY) * (this.posY - oponent.posY)
				+ (this.posZ - oponent.posZ) * (this.posZ - oponent.posZ);
		double dist = Math.pow(distQ, 0.5);
		return dist;
	}

	public Unit() {
		super();
		this.leben = 20*Math.random()/Math.random();
		this.damage = 20*Math.random()/Math.random();
		this.distance = 20*Math.random()/Math.random();
		this.speed = 20*Math.random()/Math.random();
		this.team = (int)(Math.random()*2);
	}
	
	
}