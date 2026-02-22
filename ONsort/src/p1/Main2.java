package p1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main2 {

	public static void main(String[] args) {
		List<Integer> list1 = new ArrayList<Integer>();

		List<Integer> list2 = new ArrayList<Integer>();
		
		List<Double> list3 = new ArrayList<Double>();

		for (int i = 0; i < 100; i++) {
			Random random = new Random();
			int randomInt = random.nextInt(); // Gibt einen zufälligen int-Wert im gesamten Bereich zurück
			list1.add(randomInt);
		}

		for (int i = 0; i < 100; i++) {
			list2.add((int) (Math.random() * 100 - Math.random() * 100));
		}
		
		for (int i = 0; i < 100; i++) {
			list3.add(Math.random() * 1000000);
		}

//		printIntegerList(list1);
//
//		System.out.println("");
//		System.out.println("Sortierte Liste");
//		List<Integer> gekonnt = ONSort(list1);
//		printIntegerList(gekonnt);
		
		printDoubleList(list3);

		System.out.println("");
		System.out.println("Sortierte Liste");
		List<Double> gekonnt = ONSort2(list3);
		printDoubleList(gekonnt);
	}

	public static List<Integer> integerListe(int size) {
		List<Integer> integerListe = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			int integr = (int) (Math.random() * 100000000) - (int) (Math.random() * 100000000);
			integerListe.add(integr);
		}
		return integerListe;
	}

	public static void printIntegerList(List<Integer> integerListe) {
		for (int i = 0; i < integerListe.size(); i++) {
			System.out.println(integerListe.get(i));
		}
	}
	
	public static void printDoubleList(List<Double> DoubleListe) {
		for (int i = 0; i < DoubleListe.size(); i++) {
			System.out.println(DoubleListe.get(i));
		}
	}

	public static List<Integer> ONSort(List<Integer> integerListe) {
		double[] sortArray1 = new double[8000];
		double[] sortArray2 = new double[2000000];

		for (int s = 0; s < sortArray1.length; s++) {
			sortArray1[s] = 0.5;
		}
		for (int s2 = 0; s2 < sortArray2.length; s2++) {
			sortArray2[s2] = 0.5;
		}

		List<Integer> SortListe = new ArrayList<>();
		for (int i = 0; i < integerListe.size(); i++) {
			int index = integerListe.get(i) / 1000000;
			if (index < 0) {
				index = 4000 + index;
			} else
				index += 4000;
			if (sortArray1[index] == 0.5) {
				sortArray1[index] = integerListe.get(i);
			} else {
				int index2 = integerListe.get(i);
				int index3 = (int) sortArray1[index];
				index2 = index2 - 1000000 * (index - 4000);
				index3 = index3 - 1000000 * (index - 4000);
				if (index2 < 0) {
					index2 = 1000000 + index2;
				} else {
					index2 += 1000000;
				}
				if (index3 < 0) {
					index3 = 1000000 + index3;
				} else {
					index3 += 1000000;
				}

				sortArray2[index2] = integerListe.get(i);

				if (sortArray1[index] != 1.5)
					sortArray2[index3] = (int) sortArray1[index];
				sortArray1[index] = 1.5;
			}
		}

		for (int i2 = 0; i2 < sortArray1.length; i2++) {
			if (sortArray1[i2] != 0.5 && sortArray1[i2] != 1.5) {
				SortListe.add((int) sortArray1[i2]);
			} else if (sortArray1[i2] == 1.5) {
				for (int k = 0; k < sortArray2.length; k++) {
					if (sortArray2[k] != 0.5)
						SortListe.add((int) sortArray2[k]);
				}
			}
		}
		return SortListe;
	}

	public static List<Double> ONSort2(List<Double> doubleListe) {
		double[] sortArray = new double[1000];
		double min=Double.MAX_VALUE;
		double max=Double.MIN_VALUE;

		for(int t=0; t<doubleListe.size(); t++) {
			if(doubleListe.get(t)<min) {
				min=doubleListe.get(t);
			}
			if(doubleListe.get(t)>max) {
				max=doubleListe.get(t);
			}
		}
		
		for (int s = 0; s < sortArray.length; s++) {
			sortArray[s] = 0.5;
		}

		List<Double> sortList = new ArrayList<>();
		for (int i = 0; i < doubleListe.size(); i++) {
			int index = (int) (doubleListe.get(i) / sortArray.length);
			sortArray[index]=doubleListe.get(i);
		}
		for(int j=0; j<sortArray.length; j++) {
			sortList.add(sortArray[j]);
		}
		
		
		return sortList;
	}

}