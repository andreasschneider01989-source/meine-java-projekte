package p1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ONSortAlgorithm {

    // -------------------------------
    // Integer-Version
    // -------------------------------
    public static List<Integer> ONSort(List<Integer> integerListe) {
        double[] mainArray = new double[8000];
        double[] subArray  = new double[2000000];

        initializeArray(mainArray, 0.5);
        initializeArray(subArray, 0.5);

        for (int number : integerListe) {
            int mainIndex = getMainIndex(number);
            if (mainArray[mainIndex] == 0.5) {
                mainArray[mainIndex] = number;
            } else {
                handleCollision(mainArray, subArray, mainIndex, number);
            }
        }
        return mergeArrays(mainArray, subArray);
    }

    public static void initializeArray(double[] array, double defaultValue) {
        for (int i = 0; i < array.length; i++) {
            array[i] = defaultValue;
        }
    }

    public static int getMainIndex(int number) {
        int index = number / 1000000;
        if (index < 0) {
            index = 4000 + index;
        } else {
            index += 4000;
        }
        return index;
    }

    public static int getSubIndex(int number, int mainIndex) {
        int subIndex = number - 1000000 * (mainIndex - 4000);
        if (subIndex < 0) {
            subIndex = 1000000 + subIndex;
        } else {
            subIndex += 1000000;
        }
        return subIndex;
    }

    public static void handleCollision(double[] mainArray, double[] subArray, int mainIndex, int number) {
        int subIndexNew    = getSubIndex(number, mainIndex);
        int subIndexStored = getSubIndex((int) mainArray[mainIndex], mainIndex);

        subArray[subIndexNew] = number;
        if (mainArray[mainIndex] != 1.5) {
            subArray[subIndexStored] = (int) mainArray[mainIndex];
        }
        mainArray[mainIndex] = 1.5;
    }

    public static List<Integer> mergeArrays(double[] mainArray, double[] subArray) {
        List<Integer> sortedList = new ArrayList<>();
        for (int i = 0; i < mainArray.length; i++) {
            if (mainArray[i] != 0.5 && mainArray[i] != 1.5) {
                sortedList.add((int) mainArray[i]);
            } else if (mainArray[i] == 1.5) {
                for (int j = 0; j < subArray.length; j++) {
                    if (subArray[j] != 0.5) {
                        sortedList.add((int) subArray[j]);
                    }
                }
            }
        }
        return sortedList;
    }

    // -------------------------------
    // Double-Version
    // -------------------------------
    public static List<Double> ONSortDouble(List<Double> doubleListe) {
        double[] mainArray = new double[8000];
        double[] subArray  = new double[2000000];

        // Initialisiere beide Arrays mit dem Standardwert 0.5
        initializeArray(mainArray, 0.5);
        initializeArray(subArray, 0.5);

        for (double number : doubleListe) {
            int mainIndex = getMainIndexDouble(number);
            if (mainArray[mainIndex] == 0.5) {
                mainArray[mainIndex] = number;
            } else {
                handleCollisionDouble(mainArray, subArray, mainIndex, number);
            }
        }
        return mergeArraysDouble(mainArray, subArray);
    }

    /**
     * Berechnet den Index im Hauptarray für einen Double-Wert.
     * Hier wird der Wert durch 1.000.000 geteilt und ein Offset von 4000
     * (bzw. subtrahiert, wenn negativ) hinzugefügt.
     */
    public static int getMainIndexDouble(double number) {
        int index = (int) (number / 1000000);
        if (index < 0) {
            index = 4000 + index;
        } else {
            index += 4000;
        }
        return index;
    }

    /**
     * Berechnet den Index im Subarray für einen Double-Wert.
     */
    public static int getSubIndexDouble(double number, int mainIndex) {
        int subIndex = (int) (number - 1000000 * (mainIndex - 4000));
        if (subIndex < 0) {
            subIndex = 1000000 + subIndex;
        } else {
            subIndex += 1000000;
        }
        return subIndex;
    }

    /**
     * Behandelt Kollisionen in der Double-Version.
     * Wird ein Wert bereits am berechneten Hauptindex gefunden, so werden
     * der existierende Wert und der neue Wert in das Subarray an den berechneten
     * Positionen geschrieben und im Hauptarray ein Marker (1.5) gesetzt.
     */
    public static void handleCollisionDouble(double[] mainArray, double[] subArray, int mainIndex, double number) {
        int subIndexNew    = getSubIndexDouble(number, mainIndex);
        int subIndexStored = getSubIndexDouble(mainArray[mainIndex], mainIndex);

        subArray[subIndexNew] = number;
        if (mainArray[mainIndex] != 1.5) {
            subArray[subIndexStored] = mainArray[mainIndex];
        }
        mainArray[mainIndex] = 1.5;
    }

    /**
     * Führt die beiden Arrays (Hauptarray und Subarray) zusammen und erstellt
     * daraus die sortierte Liste von Double-Werten.
     */
    public static List<Double> mergeArraysDouble(double[] mainArray, double[] subArray) {
        List<Double> sortedList = new ArrayList<>();
        for (int i = 0; i < mainArray.length; i++) {
            if (mainArray[i] != 0.5 && mainArray[i] != 1.5) {
                sortedList.add(mainArray[i]);
            } else if (mainArray[i] == 1.5) {
                for (int j = 0; j < subArray.length; j++) {
                    if (subArray[j] != 0.5) {
                        sortedList.add(subArray[j]);
                    }
                }
            }
        }
        return sortedList;
    }

    // -------------------------------
    // Main-Methode zum Testen beider Implementierungen
    // -------------------------------
    public static void main(String[] args) {
        // Test mit Integern
        List<Integer> intInput = new ArrayList<>();
        intInput.add(1);
        intInput.add(3);
        intInput.add(2);
        intInput.add(440321);
        List<Integer> sortedIntegers = ONSort(intInput);
//        System.out.println("Sortierte Integer-Liste: " + sortedIntegers);

        // Test mit Doubles
        List<Double> doubleInput = new ArrayList<>();
        doubleInput.add(1.0);
        doubleInput.add(3.0);
        doubleInput.add(2.0);
        doubleInput.add(440321.0);
        List<Double> sortedDoubles = ONSortDouble(doubleInput);
//        System.out.println("Sortierte Double-Liste: " + sortedDoubles);
        
        
        // Weitere Tests
        List<Integer> list1 = new ArrayList<Integer>();

		List<Integer> list2 = new ArrayList<Integer>();
		
		List<Double> list3 = new ArrayList<Double>();
		
		List<Double> list4 = new ArrayList<Double>();
		
		List<Double> list5 = new ArrayList<Double>();

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
		
		for (int i = 0; i < 100; i++) {
			list4.add(Math.random());
		}
		
		for (int i = 0; i < 100; i++) {
			list5.add(Math.random()/Math.random());
		}
		
		
		List<Integer> list1Sorted = ONSort(list1);
		List<Integer> list2Sorted = ONSort(list2);
		List<Double> list3Sorted = ONSortDouble(list3);
		List<Double> list4Sorted = ONSortDouble(list4);
		List<Double> list5Sorted = ONSortDouble(list5);
		
		System.out.println(list4);
		System.out.println("Sortierte Liste: "+ONSortDouble(list4));
		
    }
}
