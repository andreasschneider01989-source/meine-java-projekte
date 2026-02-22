package p1;

import java.util.ArrayList;
import java.util.List;

public class Main3 {

	public static void main(String[] args) {
		List<Integer> list1 = new ArrayList<Integer>();
//		printIntegerList(list1);

		List<Integer> list2 = new ArrayList<Integer>();
//		for(int i=0; i<5; i++) {
//			list2.add((int)(Math.random()*1000)-(int)(Math.random()*1000));
//		}
		for(int i=0; i<20; i++) {
			list1.add((int)(Math.random()*1000000000)-(int)(Math.random()*1000000000));
		}
		list1.add(5000005);
		list1.add(5000003);
		list1.add(5000001);
		list1.add(5000004);
		list1.add(5000010);
		list1.add(5000077);
		list1.add(5000032);
		list1.add(5000013);
		list1.add(5000051);
//		list2.add(4);
//		list2.add(5);
//		list2.add(2);
//		list2.add(3);
//		list2.add(4);
		
		
		printIntegerList(list1);

		System.out.println("Sortierte Liste");
		List<Integer> gekonnt = ONSort(list1);
		printIntegerList(gekonnt);
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

//	public static List<Integer> ONSort(List<Integer> integerListe) {
//		
//		double[] sortArray1 = new double[2000];
//		double[] sortArray2 = new double[2000];
//
//		for (int s = 0; s < sortArray1.length; s++) {
//			sortArray1[s] = 0.5;
//		}
//		for (int s2 = 0; s2 < sortArray2.length; s2++) {
//			sortArray2[s2] = 0.5;
//		}
//
//		List<Integer> SortListe = new ArrayList<>();
//		for (int i = 0; i < integerListe.size(); i++) {
//			int index = integerListe.get(i) / 1000000;
//			if (index < 0) {
//				index = 1000 + index;
//			} else
//				index += 1000;
//			if (sortArray1[index] == 0.5) {
//				sortArray1[index] = integerListe.get(i);
//			} else {unterArray(10000000000.0/1000,index*skala, (index+1)*skala); value}}
			
			public int[] unterArray(int skala, int start, int end, int value) {
				int[] array = new int[1000];
				int index = value-start;
				index/=1000;
				if(array[index]==0.5)array[index]=value;
				else if (array[index]==1.5) {int[] unterArray = new int[1000]; valueINArray(value,unterArray);}
				else {unterArray(skala/1000, index*skala, (index+1)*skala,  value); valueINArray(array[index],unterArray)}
				}
			
			
			public int[] valueINArray(int value, int[] array) {
				
			}
				
		
		
			else {
				int index2 = integerListe.get(i);
				int index3 =  (int) sortArray1[index];
				index3 = index3/1000000;
				System.out.println("jo"+index2);
				System.out.println("Index"+index);
				index2 = index2 - 1000000 * (index-1000);
//				index3 = index3 - 1000000 * (index-1000);
				System.out.println("jo"+index2);
				if (index2 < 0) {
					index2 = 1000 + index2;
				} else
					{index2 += 1000;}
				if (index3 < 0) {
					index3 = 1000 + index3;
				} else
					{index3 += 1000;}
//				int index3 = (int) sortArray1[index];

				
				if(2<8) {
					System.out.println("index2"+index2);
					System.out.println("index"+index3);
				}
				
				sortArray2[index2] = integerListe.get(i);
				
				sortArray2[index3]= (int) sortArray1[index];
				sortArray1[index] = 1.5;
				
			}}


			for (int i2 = 0; i2 < sortArray1.length; i2++) {
				if (sortArray1[i2] != 0.5 && sortArray1[i2] != 1.5) {
					SortListe.add((int) sortArray1[i2]);
				} else if (sortArray1[i2] == 1.5) {
					for (int k = 0; k < sortArray2.length; k++) {
						if (sortArray2[k] != 0.5)
							SortListe.add((int) sortArray2[k]);
//					}
//				}
//			}
			
		}}}
		return SortListe;
	}

}