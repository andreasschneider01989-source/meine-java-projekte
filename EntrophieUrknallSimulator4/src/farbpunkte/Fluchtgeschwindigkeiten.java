package farbpunkte;

public class Fluchtgeschwindigkeiten {

	public static void main(String[] args) {
		
//		Erdmasse 5,9724 ∑ 10^24 kg

		
		
		//Erde
		double masse=5.9724*Math.pow(10, 24);
		double G=6.67430*Math.pow(10, -11);
		double dist=6371000;
		
		//Fluchtgeschwindigkeit Erde Prognose 11200 m/s
		
		
		
		double ve=Math.pow(masse*G*2/dist, 0.5);

		
		
		// Masse von Proton
		double masseA = 1.6726*Math.pow(10, -27);
		double distA = 5.3*Math.pow(10, -11);
		
	
		
		
		
		double vA = Math.pow(masseA*G*2/distA, 0.5);
		System.out.println(vA);
		
		// minimaler abstand von Teilchen auﬂerhalb des Protons
		 double distMin = masseA*G*2/300000000 / 300000000;
		 System.out.println(distMin);

		 // Vergleich anderen Masseverteilungen um Singularit‰ten im Zentrum von Galaxien
		 
		 
		 
		 double protonAbs = 1.67252 * Math.pow(10, -27);
		 double neutronAbs = 1.67482 * Math.pow(10, -27);
		 double elektronAbs = 9.10908* Math.pow(10, -31);
		 
		 double wasserstoffAbs = protonAbs+elektronAbs;
		 
		 double wasserstoff2 = 1.6735575* Math.pow(10, -27);
		 
		 
		 
		 System.out.println((neutronAbs-protonAbs)/elektronAbs);
		 System.out.println((wasserstoff2-protonAbs)/elektronAbs);
		 System.out.println((wasserstoffAbs-protonAbs)/elektronAbs);
		 System.out.println(neutronAbs/elektronAbs);
		 System.out.println(protonAbs/elektronAbs);

		 
	}

}
