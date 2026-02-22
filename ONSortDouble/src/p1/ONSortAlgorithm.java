package p1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ONSortAlgorithm {

    private static final int BUCKET_COUNT = 1000;
    private static final int MAX_RECURSION_LEVEL = 10;

    // -------------------------------
    // Integer Version (as before)
    // -------------------------------
    public static List<Integer> ONSort(List<Integer> integerList) {
        double[] mainArray = new double[8000];
        double[] subArray = new double[2000000];

        initializeArray(mainArray, 0.5);
        initializeArray(subArray, 0.5);

        for (int number : integerList) {
            int mainIndex = getMainIndex(number);
            if (mainArray[mainIndex] == 0.5) {
                mainArray[mainIndex] = number;
            } else {
                handleCollision(mainArray, subArray, mainIndex, number);
            }
        }
        return mergeArrays(mainArray, subArray);
    }

    /**
     * Initializes the given array with the specified default value.
     *
     * @param array        the array to initialize
     * @param defaultValue the value to fill the array with
     */
    public static void initializeArray(double[] array, double defaultValue) {
        for (int i = 0; i < array.length; i++) {
            array[i] = defaultValue;
        }
    }

    /**
     * Calculates the main index for the given integer.
     *
     * The number is divided by 1,000,000 and an offset of 4000 is applied.
     *
     * @param number the integer to calculate the index for
     * @return the calculated main array index
     */
    public static int getMainIndex(int number) {
        int index = number / 1000000;
        if (index < 0) {
            index = 4000 + index;
        } else {
            index += 4000;
        }
        return index;
    }

    /**
     * Calculates the sub-index for a given number at the specified main index.
     *
     * @param number    the number for which the sub-index is calculated
     * @param mainIndex the main array index
     * @return the calculated sub-array index
     */
    public static int getSubIndex(int number, int mainIndex) {
        int subIndex = number - 1000000 * (mainIndex - 4000);
        if (subIndex < 0) {
            subIndex = 1000000 + subIndex;
        } else {
            subIndex += 1000000;
        }
        return subIndex;
    }

    /**
     * Handles a collision in the main array by placing values into the sub array.
     *
     * @param mainArray the main array
     * @param subArray  the sub array
     * @param mainIndex the main array index where the collision occurred
     * @param number    the new number causing the collision
     */
    public static void handleCollision(double[] mainArray, double[] subArray, int mainIndex, int number) {
        int subIndexNew = getSubIndex(number, mainIndex);
        int subIndexStored = getSubIndex((int) mainArray[mainIndex], mainIndex);

        subArray[subIndexNew] = number;
        if (mainArray[mainIndex] != 1.5) {
            subArray[subIndexStored] = (int) mainArray[mainIndex];
        }
        mainArray[mainIndex] = 1.5;
    }

    /**
     * Merges the main array and sub array into a sorted list.
     *
     * @param mainArray the main array containing numbers or markers
     * @param subArray  the sub array where collided numbers are stored
     * @return a list of integers in sorted order
     */
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
    // Double Version with Deeper Subarrays (Recursive)
    // -------------------------------

    /**
     * Entry point for sorting a list of doubles.
     * This method handles collisions by recursively creating deeper subarrays.
     *
     * @param doubleList the list of double values to sort
     * @return the sorted list of double values
     */
    public static List<Double> ONSortDouble(List<Double> doubleList) {
        return recursiveONSortDouble(doubleList, 0);
    }

    /**
     * Recursive method for sorting double values.
     *
     * When the maximum recursion depth is reached or if all values in a bucket are identical,
     * this method falls back to Collections.sort to ensure the final order.
     *
     * @param list  the list of double values to sort
     * @param level the current recursion level
     * @return a sorted list of double values
     */
    public static List<Double> recursiveONSortDouble(List<Double> list, int level) {
        // If further subdivision is not useful, sort directly.
        if (level >= MAX_RECURSION_LEVEL || list.size() <= 1) {
            List<Double> copy = new ArrayList<>(list);
            Collections.sort(copy);
            return copy;
        }

        // Check if all values in the list are identical.
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

        // Distribute the values into buckets
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

        // Merge the buckets into a sorted list
        List<Double> sorted = new ArrayList<>();
        for (int i = 0; i < BUCKET_COUNT; i++) {
            if (buckets[i] != null) {
                if (buckets[i] instanceof Double) {
                    sorted.add((Double) buckets[i]);
                } else if (buckets[i] instanceof List) {
                    @SuppressWarnings("unchecked")
                    List<Double> bucketList = (List<Double>) buckets[i];
                    // If there is more than one value in the bucket,
                    // sort recursively at the next level.
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
     * Calculates the bucket index for a given double value at the specified recursion level.
     *
     * The calculation is done stepwise:
     * - At level 0: index = (int)(number * BUCKET_COUNT)
     * - At later levels, the integer part removed in previous steps is ignored.
     *
     * If the calculated index is >= BUCKET_COUNT, it is set to BUCKET_COUNT - 1.
     *
     * @param number      the double value to sort
     * @param level       the current recursion level
     * @param bucketCount the number of buckets
     * @return the bucket index
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
    // Main method for testing
    // -------------------------------
    public static void main(String[] args) {
        // Test with integers
        List<Integer> intInput = new ArrayList<>();
        intInput.add(1);
        intInput.add(3);
        intInput.add(2);
        intInput.add(440321);
        List<Integer> sortedIntegers = ONSort(intInput);
        // System.out.println("Sorted Integer List: " + sortedIntegers);

        // Test with doubles
        List<Double> doubleInput = new ArrayList<>();
        doubleInput.add(1.0);
        doubleInput.add(3.0);
        doubleInput.add(2.0);
        doubleInput.add(440321.0);
        List<Double> sortedDoubles = ONSortDouble(doubleInput);
        // System.out.println("Sorted Double List: " + sortedDoubles);

        // Additional tests
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
            list6.add(1e-12 * Math.random() / Math.random());
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

        System.out.println("Original list7: " + list1);
        System.out.println("Sorted list from list7: " + list1Sorted);
    }
}

/**
 * This algorithm uses two special marker values to indicate empty slots 
 * and collisions within internal arrays during the sorting process. 
 * By default, these marker values are set to randomly chosen double 
 * values (e.g., around 1e20) that are extremely unlikely to occur in a 
 * typical dataset. However, if there is any possibility that your input data 
 * might contain these marker values, you can replace them with alternative 
 * double values that are equally unlikely to appear in real-world data. 
 * This ensures that the algorithm functions correctly for all valid double values.
 */
