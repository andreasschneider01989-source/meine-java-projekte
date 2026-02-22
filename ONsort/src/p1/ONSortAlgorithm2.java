package p1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ONSortAlgorithm2 {

    private static final int BUCKET_COUNT = 1000;
    private static final int MAX_RECURSION_LEVEL = 10;

    // -------------------------------
    // Integer-Version (wie zuvor)
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
    // Double-Version mit tieferen Subarrays (rekursiv)
    // -------------------------------
    
    /**
     * Einstiegspunkt für das Sortieren von Double-Listen, die bei Kollisionen
     * rekursiv tiefere Subarrays erzeugen.
     */
    public static List<Double> ONSortDouble(List<Double> doubleListe) {
        return recursiveONSortDouble(doubleListe, 0);
    }
    
    /**
     * Rekursive Methode zur Sortierung von Double-Werten.
     *
     * Bei Erreichen einer zu hohen Rekursionstiefe oder wenn alle Werte in einem Bucket
     * identisch sind, wird auf Collections.sort zurückgegriffen, um die Endsortierung
     * sicherzustellen.
     *
     * @param list  Die zu sortierende Liste von Double-Werten.
     * @param level Die aktuelle Rekursionsebene.
     * @return Eine sortierte Liste von Double-Werten.
     */
    public static List<Double> recursiveONSortDouble(List<Double> list, int level) {
        // Wenn keine weiteren Unterteilungen sinnvoll sind, direkt sortieren.
        if (level >= MAX_RECURSION_LEVEL || list.size() <= 1) {
            List<Double> copy = new ArrayList<>(list);
            Collections.sort(copy);
            return copy;
        }
        
        // Prüfe, ob alle Werte identisch sind.
        boolean allEqual = true;
        Double first = list.get(0);
        for (Double d : list) {
            if (!d.equals(first)) {
                allEqual = false;
                break;
            }
        }
        if (allEqual) {
            return new ArrayList<>(list);
        }
        
        Object[] buckets = new Object[BUCKET_COUNT];
        
        // Verteile die Werte in die Buckets
        for (Double number : list) {
            int index = getBucketIndex(number, level, BUCKET_COUNT);
            if (buckets[index] == null) {
                buckets[index] = number;
            } else if (buckets[index] instanceof Double) {
                List<Double> bucketList = new ArrayList<>();
                bucketList.add((Double) buckets[index]);
                bucketList.add(number);
                buckets[index] = bucketList;
            } else if (buckets[index] instanceof List) {
                @SuppressWarnings("unchecked")
                List<Double> bucketList = (List<Double>) buckets[index];
                bucketList.add(number);
            }
        }
        
        // Zusammenführen der Buckets in eine sortierte Liste
        List<Double> sorted = new ArrayList<>();
        for (int i = 0; i < BUCKET_COUNT; i++) {
            if (buckets[i] != null) {
                if (buckets[i] instanceof Double) {
                    sorted.add((Double) buckets[i]);
                } else if (buckets[i] instanceof List) {
                    @SuppressWarnings("unchecked")
                    List<Double> bucketList = (List<Double>) buckets[i];
                    // Falls im Bucket mehr als ein Wert vorhanden ist, 
                    // wird rekursiv die nächste Ebene sortiert.
                    if (bucketList.size() > 1) {
                        sorted.addAll(recursiveONSortDouble(bucketList, level + 1));
                    } else if (bucketList.size() == 1) {
                        sorted.add(bucketList.get(0));
                    }
                }
            }
        }
        return sorted;
    }
    
    /**
     * Berechnet den Bucket-Index für einen gegebenen Double-Wert und die Rekursionsebene.
     *
     * Die Berechnung erfolgt schrittweise:
     * - Auf Ebene 0: index = (int)(number * BUCKET_COUNT)
     * - Auf späteren Ebenen wird der bereits entfernte ganzzahlige Anteil ignoriert.
     *
     * Sollte der berechnete Index >= BUCKET_COUNT liegen, so wird er auf BUCKET_COUNT - 1
     * gesetzt.
     *
     * @param number Der zu sortierende Double-Wert.
     * @param level  Die aktuelle Rekursionsebene.
     * @param bucketCount Die Anzahl der Buckets.
     * @return Den Bucket-Index.
     */
    public static int getBucketIndex(double number, int level, int bucketCount) {
        double scaled = number;
        int index = 0;
        for (int i = 0; i <= level; i++) {
            index = (int) (scaled * bucketCount);
            if (index >= bucketCount) {
                index = bucketCount - 1;
            }
            scaled = scaled * bucketCount - index;
        }
        return index;
    }
    
    // -------------------------------
    // Main-Methode zum Testen
    // -------------------------------
    public static void main(String[] args) {
        // Test mit Integern
        List<Integer> intInput = new ArrayList<>();
        intInput.add(1);
        intInput.add(3);
        intInput.add(2);
        intInput.add(440321);
        List<Integer> sortedIntegers = ONSort(intInput);
        // System.out.println("Sortierte Integer-Liste: " + sortedIntegers);

        // Test mit Doubles
        List<Double> doubleInput = new ArrayList<>();
        doubleInput.add(1.0);
        doubleInput.add(3.0);
        doubleInput.add(2.0);
        doubleInput.add(440321.0);
        List<Double> sortedDoubles = ONSortDouble(doubleInput);
        // System.out.println("Sortierte Double-Liste: " + sortedDoubles);
        
        // Weitere Tests
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        List<Double> list3 = new ArrayList<>();
        List<Double> list4 = new ArrayList<>();
        List<Double> list5 = new ArrayList<>();
        List<Double> list6 = new ArrayList<>();
        List<Double> list7 = new ArrayList<>();

        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            int randomInt = random.nextInt();
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
            list5.add(Math.random() / Math.random());
        }
        for (int i = 0; i < 100; i++) {
            list6.add(0.000000000001*Math.random() / Math.random());
        }
        
        list7.add(3.0);
        list7.add(1.0);
        list7.add(3.0);
        list7.add(1.0);
        list7.add(3.0);
        
        List<Integer> list1Sorted = ONSort(list1);
        List<Integer> list2Sorted = ONSort(list2);
        List<Double> list3Sorted = ONSortDouble(list3);
        List<Double> list4Sorted = ONSortDouble(list4);
        List<Double> list5Sorted = ONSortDouble(list5);
        List<Double> list6Sorted = ONSortDouble(list6);
        List<Double> list7Sorted = ONSortDouble(list7);
        
        System.out.println("Original list4: " + list7);
        System.out.println("Sortierte Liste von list4: " + list7Sorted);
    }
}
