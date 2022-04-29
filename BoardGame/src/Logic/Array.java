package src.Logic;

import src.Exceptions.InvalidArrayError;
import src.Exceptions.InvalidParameterError;

/**
 * Class that performs basic operations with arrays
 * 
 * @version 1.0
 * @author Anxo Vilas
 */
public class Array {

    /**
     * Checks wether all the arrays contained within the array are of the same length.
     * <p>
     * Example:
     * <p>
     * {{1, 2}, {3, 4}} -> true
     * <p>
     * {{1, 2, 3}, {4, 5}} -> false
     * 
     * @param array - The array to check
     * @return A boolean representing if all the subarrays have equal lengths
     */
    public static boolean areSameSize(int[][] array) {
        boolean sameSize = true;
        int size = array[0].length;
        for (int i = 0; i < array.length && sameSize; i++) {
            if (array[i].length != size) {
                sameSize = false;
            }
        }
        return sameSize;
    }

    /**
     * Counts the number of times an element is found in the array.
     * <p>
     * Example:
     * <p>
     * count({1, 2, 1}, 1) -> 2
     * 
     * @param array - The <code>int[]</code> array to count from
     * @param element - The element to search for
     * @return The number of occurences of the element in the array
     */
    public static int count(int[] array, int element) {
        int occurences = 0;
        for (int i : array) {
            if (i == element) {
                occurences++;
            }
        }
        return occurences;
    }

    /**
     * Reverses the order of the array.
     * <p>
     * Example:
     * <p>
     * reverse({1, 2, 3}) -> {3, 2, 1}
     * 
     * @param array - The <code>int[]</code> array to be reversed
     * @return The reversed array
     */
    public static int[] reverse(int[] array) {
        int size = array.length;
        int[] result = new int[size];
        for (int i = 0; i < size; i++) {
            result[i] = array[size-i-1];
        }
        return result;
    }



    /**
     * Multiplies the values of any <code>int[]</code> array by -1.
     * <p>
     * Example:
     * <p>
     * {1, -2, 3} -> {-1, 2, -3}
     * 
     * @param array - The <code>int[]</code> array to invert
     * @return An <code>int[]</code> array with all of its subsequent values multiplied by -1
     */
    public static int[] invert(int[] array) {
        int size = array.length;
        int[] result = new int[size];
        for (int i = 0; i < size; i++) {
            result[i] = -array[i];
        }
        return result;
    }

    /**
     * Removes the sign from all the values within an <code>int[]</code> array.
     * <p>
     * Example:
     * <p>
     * {1, -2, 3} -> {1, 2, 3}
     * 
     * @param array - The <code>int[]</code> array to unsign
     * @return The unsigned <code>int[]</code> value array
     */
    public static int[] unsign(int[] array) {
        int size = array.length;
        int[] result = new int[size];
        for (int i = 0; i < size; i++) {
            if (array[i] < 0) {
                result[i] = array[i]*-1;
            } else {
                result[i] = array[i];
            }
        }
        
        return result;
    }

    /**
     * Adds a negative sign to all the values within an <code>int[]</code> array.
     * <p>
     * Example:
     * <p>
     * {1, -2, 3} -> {-1, -2, -3}
     * 
     * @param array - The <code>int[]</code> array to negate
     * @return The unsigned <code>int[]</code> value array
     */
    public static int[] negate(int[] array) {
        int size = array.length;
        int[] result = new int[size];
        for (int i = 0; i < size; i++) {
            if (array[i] > 0) {
                result[i] = array[i]*-1;
            } else {
                result[i] = array[i];
            }
        }
        
        return result;
    }



    /**
     * Adds an index to an array.
     * <p>
     * Example:
     * <p>
     * add({1, 2}, 3) -> {1, 2, 3}
     * 
     * @param array - The <code>int[]</code> array to operate with
     * @param n - The <code>int</code> element to be added
     * @return An <code>int[]</code> array, one index larger, with the element <code>n</code> added to it's last position
     */
    public static int[] add(int[] array, int n) {
        int size = array.length;
        int[] result = new int[size+1];
        for (int i = 0; i < size; i++) {
            result[i] = array[i];
        }
        result[size] = n;
        return result;
    }

    /**
     * Inserts an element to an array in a certain position.
     * <p>
     * [ ! ] The index must be within the array boundaries.
     * <p>
     * Example:
     * <p>
     * add({1, 3}, 1, 2) -> {1, 2, 3}
     * 
     * @param array - The <code>int[]</code> array to operate with
     * @param n - The <code>int</code> element to be added
     * @param index - The position the element will take on the array
     * @return An <code>int[]</code> array with <code>n</code> inserted into the selected position 
     */
    public static int[] add(int[] array, int index, int n) {
        int size = array.length;
        int[] result = new int[size+1];
        for (int i = 0; i < index; i++) {
            result[i] = array[i];
        }
        result[index] = n;
        for (int i = index; i < size; i++) {
            result[i+1] = array[i];
        }
        return result;
    }



    /**
     * Removes the last index from an array.
     * <p>
     * [ ! ] The array must not be empty.
     * <p>
     * Example:
     * <p>
     * remove({1, 2, 3}) -> {1, 2}
     * 
     * @param array - The <code>int[]</code> array to operate with
     * @return An <code>int[]</code> array with the last element removed
     */
    public static int[] remove(int[] array) {
        int size = array.length;
        int[] result = new int[size-1];
        for (int i = 0; i < size-1; i++) {
            result[i] = array[i];
        }
        return result;
    }

    /**
     * Removes a specified index from an array.
     * <p>
     * [ ! ] The index must be within the array boundaries.
     * <p>
     * Example:
     * <p>
     * remove({1, 2, 3}, 1) -> {1, 3}
     * 
     * @param array - The <code>int[]</code> array to operate with
     * @param index - The position to remove from the array
     * @return An <code>int[]</code> array with the specified index removed
     */
    public static int[] remove(int[] array, int index) {
        int size = array.length;
        int[] result = new int[size-1];
        for (int i = 0; i < index; i++) {
            result[i] = array[i];
        }
        for (int i = index+1; i < size; i++) {
            result[i-1] = array[i];
        }
        return result;
    }

    /**
     * Removes the first element from an array.
     * <p>
     * Example:
     * <p>
     * removeFirst({1, 2, 3, 2}, 2) -> {1, 3, 2}
     * 
     * @param array - The <code>int[]</code> array to operate with
     * @param element - The element to remove from the array
     * @return An <code>int[]</code> array with the first specified element removed from the array
     */
    public static int[] removeFirst(int[] array, int element) {
        int size = array.length;
        int numOccurences = count(array, element);
        int maxOccurences = 1;
        int[] result;
        if (numOccurences == 0) {
            result = array;
        } else {
            result = new int[size-1];
            int nOccurence = 0;
            for (int i = 0; i < size; i++) {
                if (array[i] == element && nOccurence < maxOccurences) {
                    nOccurence++;
                } else {
                    if (nOccurence < 1) {
                        result[i] = array[i];
                    } else {
                        result[i-1] = array[i];
                    }
                }
            }
        }
        return result;
    }

    /**
     * Removes the first <b>n</b> occurences of an element from an array.
     * <p>
     * Example:
     * <p>
     * removeFirst({1, 2, 2, 3, 2}, 2, 2) -> {1, 3, 2}
     * 
     * @param array
     * @param element
     * @param maxOccurences
     * @return
     */
    public static int[] removeFirst(int[] array, int element, int maxOccurences) {
        int size = array.length;
        int numOccurences = count(array, element);
        int[] result;
        if (numOccurences == 0) {
            result = array;
        } else {
            if (maxOccurences < numOccurences) {
                result = new int[size-maxOccurences];
            } else {
                result = new int[size-numOccurences];
            }
            int nOccurence = 0;
            for (int i = 0; i < size; i++) {
                if (array[i] == element && nOccurence < maxOccurences) {
                    nOccurence++;
                } else {
                    if (nOccurence < 1) {
                        result[i] = array[i];
                    } else {
                        result[i-nOccurence] = array[i];
                    }
                }
            }
        }
        return result;
    }
    
    /**
     * Removes all occurences of an element from an array.
     * <p>
     * Example:
     * <p>
     * removeAll({1, 2, 3, 2}, 2) -> {1, 3}
     * 
     * @param array - The <code>int[]</code> array to operate with
     * @param element - The element to remove from the array
     * @return An <code>int[]</code> array with the specified element removed
     */
    public static int[] removeAll(int[] array, int element) {
        int size = array.length;
        int numOccurences = count(array, element);
        int[] result;
        if (numOccurences == 0) {
            result = array;
        } else {
            result = new int[size-numOccurences];
            int j = 0;
            for (int i = 0; i < size; i++) {
                if (array[i] != element) {
                    result[j] = array[i];
                    j++;
                }
            }
        }

        return result;
    }



    /**
     * Joins two different arrays together.
     * <p>
     * Example:
     * <p>
     * join({1, 2}, {3, 4}) -> {1, 2, 3, 4}
     * 
     * @param a1 - The first <code>int[]</code> array
     * @param a2 - The second <code>int[]</code> array
     * @return An <code>int[]</code> array with the concatenation of the arrays
     */
    public static int[] join(int[] a1, int[] a2) {
        int size = a1.length;
        int[] result = new int[size+a2.length];
        for (int i = 0; i < size; i++) {
            result[i] = a1[i];
        }
        for (int i = 0; i < a2.length; i++) {
            result[i+size] = a2[i];
        }
        return result;
    }

    /**
     * Joins <b>n</b> different arrays together.
     * <p>
     * Example:
     * <p>
     * join({1}, {2, 3}, {4, 5, 6}) -> {1, 2, 3, 4, 5, 6}
     * 
     * @param arrays - The <code>int[]</code> arrays to join
     * @return An <code>int[]</code> array with the concatenation of the arrays
     */
    public static int[] join(int[][] arrays) {
        int size = 0;
        for (int[] array : arrays) {
            size += array.length;
        }
        int[] result = new int[size];
        int offset = 0;
        for (int[] array : arrays) {
            for (int i = 0; i < array.length; i++) {
                result[i+offset] = array[i];
            }
            offset += array.length;
        }
        return result;
    }



    /**
     * Creates a subarray of the array from the specified restrictions.
     * <p>
     * Example:
     * <p>
     * subArray({1, 2, 3}, 1, 2) -> {2, 3}
     * 
     * @param array - The <code>int[]</code> array to operate with
     * @param start - The position to start at
     * @param end - The position to end at
     * @return An <code>int[]</code> array from start to end
     * @throws InvalidParameterError If the start parameter is greater than the end parameter
     * @throws IndexOutOfBoundsException If the start and/or end parameters are out of the array bounds
     */
    public static int[] subArray(int[] array, int start, int end) throws InvalidParameterError, IndexOutOfBoundsException {
        if (start > end) {
            throw new InvalidParameterError("The end parameter must be greater than the start parameter");
        }
        int size = array.length;
        if (start < 0 || end >= size) {
            throw new IndexOutOfBoundsException("The start and end parameters must be within the array bounds");
        }

        int[] result = new int[end-start+1];
        for (int i = 0; i < end-start+1; i++) {
            result[i] = array[i+start];
        }
        return result;
    }

    /**
     * Creates a subarray of the array from the specified restrictions.
     * <p>
     * Example:
     * <p>
     * subArray({1, 2, 3, 4, 5}, 2) -> {3, 4, 5}
     * 
     * @param array - The <code>int[]</code> array to operate with
     * @param start - The position to start at
     * @return An <code>int[]</code> array from start to the end of the array
     * @throws InvalidParameterError If the start parameter is out of the array bounds
     */
    public static int[] subArray(int[] array, int start) throws InvalidParameterError {
        int size = array.length;
        if (start < 0 || start >= size) {
            throw new InvalidParameterError("The start parameter must be within the array bounds");
        }
        int[] result = new int[size-start];
        for (int i = 0; i < size-start; i++) {
            result[i] = array[i+start];
        }
        return result;
    }









    
    /**
     * Compares two arrays value by value.
     * <p>
     * Example:
     * <p>
     * equals({1, 2}, {1, 2}) -> true
     * <p>
     * equals({1}, {2, 3}) -> false
     * 
     * @param a1 - The first <code>int[]</code> array
     * @param a2 - The second <code>int[]</code> array
     * @return A boolean representing the equality of the arrays
     */
    public static boolean equals(int[] a1, int[] a2) {
        if (!areSameSize(new int[][] {a1, a2})) {
            return false;
        }
        int size = a1.length;
        boolean equal = true;
        for (int i = 0; i < size && equal; i++) {
            if (a1[i] != a2[i]) {
                equal = false;
            }
        }
        return equal;
    }

    /**
     * Evaluates if an <code>int[][]</code> array contains a specific <code>int[]</code> array.
     * <p>
     * Example:
     * <p>
     * contains({1, 2}, {{1, 2}, {1, 3}, {1, 4}}) -> true
     * <p>
     * contains({1}, {{}, {2}, {3, 4}}) -> false
     * 
     * @param array - The <code>int[]</code> array to check
     * @param arrays - The <code>int[][]</code> to check in
     * @return A boolean representingh wether the array is or not on the arrays
     */
    public static boolean contains(int[] array, int[][] arrays) {
        boolean found = false;
        for (int i = 0; i < arrays.length && !found; i++) {
            if (equals(array, arrays[i])) {
                found = true;
            }
        }
        return found;
    }

    /**
     * Counts the length of every item in an array and stores it in an <code>int[]</code> array.
     * <p>
     * Example:
     * <p>
     * lengths({{}, {1}, {1, 2}}) -> {0, 1, 2}
     * 
     * @param array - The <code>int[][]</code> array to count from
     * @return An <code>int[]</code> array containing the array lengths as its indexes
     */
    public static int[] lengths(int[][] array) {
        int size = array.length;
        int[] result = new int[size];
        for (int i = 0; i < size; i++) {
            result[i] = array[i].length;
        }
        return result;
    }

    /**
     * Searches for the minimum value on an array.
     * <p>
     * Example:
     * <p>
     * min({3, 1, 2}) -> 1
     * <p>
     * min({1, 1}) -> 1
     * 
     * @param array - The <code>int[]</code> array to search in
     * @return An <code>int</code> containing the minimum element of the array
     * @throws InvalidArrayError If the <code>int[]</code> array is empty
     */
    public static int min(int[] array) throws InvalidArrayError {
        if (array.length < 1) {
            throw new InvalidArrayError("The array must not be empty");
        }
        int min = Integer.MAX_VALUE;
        for (int i : array) {
            if (i <= min) {
                min = i;
            }
        }
        return min;
    }

    /**
     * Searches for the maximum value on an array.
     * <p>
     * Example:
     * <p>
     * max({2, 3, 1}) -> 3
     * <p>
     * max({1, 1}) -> 1
     * 
     * @param array - The <code>int[]</code> array to search in
     * @return An <code>int</code> containing the maximum element of the array
     * @throws InvalidArrayError If the <code>int[]</code> array is empty
     */
    public static int max(int[] array) throws InvalidArrayError {
        if (array.length < 1) {
            throw new InvalidArrayError("The array must not be empty");
        }
        int max = Integer.MIN_VALUE;
        for (int i : array) {
            if (i >= max) {
                max = i;
            }
        }
        return max;
    }



    
    /**
     * Adds an integer <b>n</b> to every value of the specified array.
     * <p>
     * Example:
     * <p>
     * sum({1, 2, 3}, 1) -> {2, 3, 4}
     * 
     * @param array - The <code>int[]</code> array to operate with
     * @param n - The value to add
     * @return An <code>int[]</code> array with the result of the addition
     */
    public static int[] sum(int[] array, int n) {
        int size = array.length;
        int[] result = new int[size];
        for (int i = 0; i < size; i++) {
            result[i] = array[i] + n;
        }
        return result;
    }

    /**
     * Sums every value of two different arrays.
     * <p>
     * If the arrays vary in size, the result will be the addition of the overlapped indexes.
     * <p>
     * Example:
     * <p>
     * sum({1, 2}, {3, 4}) -> {4, 6}
     * sum({1}, {2, 3}) -> {3, 3}
     * 
     * @param a1 - The first <code>int[]</code> array
     * @param a2 - The second <code>int[]</code> array
     * @return An <code>int[]</code> array with the result of the addition
     */
    public static int[] sum(int[] a1, int[] a2) {
        int size = a1.length >= a2.length ? a1.length : a2.length;
        int[] result = new int[size];
        for (int i = 0; i < size; i++) {
            try {
                result[i] = a1[i] + a2[i];
            } catch(IndexOutOfBoundsException err) {
                result[i] = a1.length >= a2.length ? a1[i] : a2[i];
            }
        }
        return result;
    }

    /**
     * Sums every value of two different arrays.
     * If restrictive is set to true, all of the arrays must be of equal lengths.
     * <p>
     * Example:
     * <p>
     * sum({1}, {1, 2}, false) -> {2, 2}
     * <p>
     * sum({1, 1}, {2, 2}, true) -> {3, 3}
     * <p>
     * sum({1}, {2, 3}, true) -> InvalidArrayError
     * 
     * @param a1 - The first <code>int[]</code> array
     * @param a2 - The second <code>int[]</code> aray
     * @param restrictive - The restriction to calculate the addition
     * @return An <code>int[]</code> array with the result of the addition
     * @throws InvalidArrayError If the <code>restrictive</code> parameter is set to true and the arrays are incosistent on sizes
     */
    public static int[] sum(int[] a1, int[] a2, boolean restrictive) throws InvalidArrayError {
        if (restrictive) {
            if (!areSameSize(new int[][]{a1, a2})) {
              throw new InvalidArrayError("The arrays lengths doesn't match");
            }
        }
        return sum(a1, a2);
    }

    /**
     * Sums <b>n</b> arrays together.
     * <p>
     * If the arrays vary in size, the result will be the addition of the overlapped indexes.
     * <p>
     * Example:
     * <p>
     * sum({{}, {0}, {1, 2}}) -> {1, 2}
     * 
     * @param arrays - The <code>int[][]</code> array to operate with
     * @return An <code>int[]</code> array with the result of the addition
     */
    public static int[] sum(int[][] arrays) {
        int size = 0;
        try {
            size = max(lengths(arrays));
        } catch (InvalidArrayError err) {}

        int[] result = new int[size];
        for (int[] array : arrays) {
            result = sum(result, array);
        }
        return result;
    }

    /**
     * Sums <b>n</b> arrays together.
     * <p>
     * If restrictive is set to true, all of the arrays must be of equal lengths.
     * <p>
     * Example:
     * <p>
     * sum({{1}, {1, 2}, {1, 2, 3}}, false) -> {3, 4, 3}
     * <p>
     * sum({{1, 1}, {2, 2}, {3, 3}}, true) -> {6, 6}
     * <p>
     * sum({{1}, {1, 2}, {1, 2, 3}}, true) -> InvalidArrayError
     * 
     * @param arrays - The <code>int[][]</code> array to operate with
     * @param restrictive - The restriction to calculate the addition
     * @return An <code>int[]</code> array with the result of the addition
     * @throws InvalidArrayError If the <code>restrictive</code> parameter is set to true and the arrays are incosistent on sizes
     */
    public static int[] sum(int[][] arrays, boolean restrictive) throws InvalidArrayError {
        if (restrictive) {
            if (!areSameSize(arrays)) {
                throw new InvalidArrayError("The arrays lengths doesn't match");
            }
        }
        return sum(arrays);
    }



    /**
     * Substracts an integer <b>n</b> to every value of the specified array.
     * <p>
     * Example:
     * <p>
     * substract({1, 2, 3}, 1) -> {0, 1, 2}
     * 
     * @param array - The <code>int[]</code> array to operate with
     * @param n - The value to substract
     * @return An <code>int[]</code> array with the result of the substraction
     */
    public static int[] substract(int[] array, int n) {
        return add(array, -n);
    }

    /**
     * Substracts the second array from the first array.
     * <p>
     * If the arrays vary in size, the result will be the substraction of the overlapped indexes.
     * <p>
     * Example:
     * <p>
     * substract({1, 2}, {0, 1}) -> {1, 1}
     * <p>
     * substract({1, 2}, {1}) -> {0, 2}
     * 
     * @param a1 - The first <code>int[]</code> array
     * @param a2 - The second <code>int[]</code> array
     * @return An <code>int[]</code> array with the result of the substraction
     */
    public static int[] substract(int[] a1, int[] a2) {
        return sum(a1, negate(a2));
    }


    /**
     * Multiplies an integer <b>n</b> to every value of the specified array.
     * <p>
     * Example:
     * <p>
     * multiply({1, 2, 3}, 2) -> {2, 4, 6}
     * 
     * @param array - The <code>int[]</code> array to operate with
     * @param n - The value to multiply
     * @return An <code>int[]</code> array with the result of the multiplication
     */
    public static int[] multiply(int[] array, int n) {
        int size = array.length;
        int[] result = new int[size];
        for (int i = 0; i < size; i++) {
            result[i] = array[i] * n;
        }
        return result;
    }

}
