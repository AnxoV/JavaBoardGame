package src.Logic;

import src.Exceptions.*;

/**
 * The {@code Operator} class provides static methods
 * to perform light logic operations with Java int[] arrays. 
 * 
 * @since JDK 1.11
 * @version 1.0
 * 
 */
public class Operator {

    /**
     * Checks wether all the arrays contained within the array are of the same length.
     * 
     * @param array - The array to check
     * @return A boolean representing if all the subarrays have equal lengths
     */
    public static boolean areSameSize(int[][] array) {
        if (array.length > 0) {
            boolean sameSize = true;
            int size = array[0].length;
            for (int i = 0; i < array.length && sameSize; i++) {
                if (array[i].length != size) {
                    sameSize = false;
                }
            }
            return sameSize;
        }
        return true;
    }



    /**
     * Counts the number of times an element is found in the array.
     * 
     * @param array - The array to count from
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
     * 
     * @param array - The array to reverse
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
     * Multiplies every value of the array by <b>-1</b>.
     * 
     * @param array - The array to invert
     * @return The inverted array
     */
    public static int[] invert(int[] array) {
        return multiply(array, -1);
    }



    /**
     * Asigns the specified sign to every value of the array.
     * @param array - The array to asign
     * @param positive - The sign
     * @return An array with all of the sign to the corresponding sing
     */
    public static int[] setSign(int[] array, boolean positive) {
        int size = array.length;
        int[] result = new int[size];
        for (int i = 0; i < size; i++) {
            if (array[i] < 0) {
                result[i] = array[i]*(positive ? -1 : 1);
            } else {
                result[i] = array[i]*(positive ? 1 : -1);
            }
        }
        return result;
    }

    /**
     * Removes the sign from all the values of the array.
     * 
     * @param array - The array to unsign
     * @return The unsigned array
     */
    public static int[] unsign(int[] array) {
        return setSign(array, true);
    }

    /**
     * Adds a negative sign to all of the values of the array.
     * 
     * @param array - The array to negate
     * @return The negated array
     */
    public static int[] negate(int[] array) {
        return setSign(array, false);
    }


    
    /**
     * Compares the equality of two arrays.
     * 
     * @param a1 - The first array to compare
     * @param a2 - The second array to compare
     * @return A boolean representing the equality of the arrays
     */
    public static boolean equals(int[] a1, int[] a2) {
        int size = a1.length;
        if (size != a2.length) {
            return false;
        }

        boolean equals = true;
        for (int i = 0; i < size && equals; i++) {
            if (a1[i] != a2[i]) {
                equals = false;
            }
        }
        return equals;
    }



    /**
     * Returns wether an integer is contained in the array.
     * @param n - The number to search for
     * @param array - The array to search in
     * @return Wether the number is or is not contained in the array
     */
    public static boolean contains(int n, int[] array) {
        return count(array, n) > 0;
    }

    /**
     * Returns wether an array is contained in the list of arrays.
     * @param array - The array to search for
     * @param arrays - The arrays to search in
     * @return Wether the array is or not contained in the list of arrays
     */
    public static boolean contains(int[] array, int[][] arrays) {
        for (int[] arr : arrays) {
            if (equals(arr, array)) {
                return true;
            }
        }
        return false;
    }



    /**
     * Returns the length of every subarray in the array.
     * 
     * @param array - The array to count from
     * @return An array containing the lengths of the subarrays
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
     * Returns the minimum value of the array.
     * 
     * @param array - The array to search in
     * @return The minimum value of the array
     * @throws InvalidArrayError If the array is empty
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
     * Returns the maximum value on the array.
     * 
     * @param array - The array to search in
     * @return The maximum value of the array
     * @throws InvalidArrayError If the array is empty
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
     * Adds an integer <b>n</b> to every value of the array.
     * 
     * @param array - The array to operate with
     * @param n - The value to add
     * @return The result of the addition
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
     * 
     * <p>If the arrays vary in size, the result will be the addition of the overlapped indexes.
     * 
     * @param a1 - The first array to add
     * @param a2 - The second array to add
     * @return The result of the addition
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
     * 
     * <p>If restrictive is set to true, all of the arrays must be of equal lengths.
     * 
     * @param a1 - The first array to add
     * @param a2 - The second array to add
     * @param restrictive - The restriction to calculate the addition
     * @return The result of the addition
     * @throws InvalidArrayError If <code>restrictive</code> is set to true and the arrays are incosistent on sizes
     */
    public static int[] sum(int[] a1, int[] a2, boolean restrictive) throws InvalidArrayError {
        if (restrictive && !areSameSize(new int[][]{a1, a2})) {
            throw new InvalidArrayError("The arrays lengths doesn't match");
        }
        return sum(a1, a2);
    }

    /**
     * Sums <b>n</b> arrays together.
     * 
     * <p>If the arrays vary in size, the result will be the addition of the overlapped indexes.
     * 
     * @param arrays - The array to operate with
     * @return The result of the addition
     */
    public static int[] sum(int[][] arrays) {
        int size;
        try {
            size = max(lengths(arrays));
        } catch (InvalidArrayError err) {
            size = 0;
        }

        int[] result = new int[size];
        for (int[] array : arrays) {
            result = sum(result, array);
        }
        return result;
    }

    /**
     * Sums <b>n</b> arrays together.
     * 
     * <p>If restrictive is set to true, all of the arrays must be of equal lengths.
     * 
     * @param arrays - The array to operate with
     * @param restrictive - The restriction to calculate the addition
     * @return The result of the addition
     * @throws InvalidArrayError If restrictive is set to true and the arrays are incosistent on sizes
     */
    public static int[] sum(int[][] arrays, boolean restrictive) throws InvalidArrayError {
        if (restrictive && !areSameSize(arrays)) {
                throw new InvalidArrayError("The arrays lengths doesn't match");
        }
        return sum(arrays);
    }



    /**
     * Substracts an integer <b>n</b> to every value of the specified array.
     * 
     * @param array - The array to operate with
     * @param n - The value to substract
     * @return The result of the substraction
     */
    public static int[] substract(int[] array, int n) {
        return sum(array, -n);
    }

    /**
     * Substracts the second array from the first array.
     * 
     * <p>If the arrays vary in size, the result will be the substraction of the overlapped indexes.
     * 
     * @param a1 - The array to be substracted
     * @param a2 - The array to substract
     * @return The result of the substraction
     */
    public static int[] substract(int[] a1, int[] a2) {
        return sum(a1, negate(a2));
    }



    /**
     * Multiplies an integer <b>n</b> to every value of the specified array.
     * 
     * @param array - The array to operate with
     * @param n - The value to multiply
     * @return The result of the multiplication
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
