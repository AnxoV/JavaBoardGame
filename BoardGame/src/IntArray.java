/**
 * Class that performs basic operations with int arrays
 * 
 * @author Anxo Vilas
 * @version 1.0
 */
public class IntArray {

    /**
     * Validates a list of arrays
     * @param arrays The arrays to be validated
     * @return True if the arrays length is greater than 0 and have equal length, false otherwise
     */
    private static boolean validateArray(int[][] arrays) {
        boolean correct = true;
        int size = arrays[0].length;
        for (int i = 0; i < arrays.length && correct; i++) {
            if (arrays[i].length != size) {
                correct = false;
            }
        }
        return correct;
    }


    /**
     * Inverts the values of the array (n * -1)
     * @param v The array to invert
     * @return The inverted array
     */
    public static int[] negate(int[] v) {
        int size = v.length;
        int[] result = new int[size];
        for (int i = 0; i < size; i++) {
            result[i] = -v[i];
        }
        return result;
    }

    /**
     * Removes the sign from all the values of an array
     * @param v The array to operate with
     * @return The unsigned value array
     */
    public static int[] unsign(int[] v) {
        int size = v.length;
        int[] result = new int[size];
        for (int i = 0; i < size; i++) {
            if (v[i] < 0) {
                result[i] = v[i]*-1;
            } else {
                result[i] = v[i];
            }
        }
        
        return result;
    }

    public int[] join(int[] v1)

    /**
     * Checks wether an array is equal to another value by value
     * @param v1 First array
     * @param v2 Second array
     * @return True if both are equal, false otherwise
     * @throws InvalidArrayError If:
     *  - The arrays size doesn't match
     */
    public boolean equals(int v1[], int v2[]) throws InvalidArrayError {
        if (!validateArray(new int[][] {v1, v2})) {
            throw new InvalidArrayError("The size of the arrays must match");
        }
        int size = v1.length;
        boolean equal = true;
        for (int i = 0; i < size && equal; i++) {
            if (v1[i] != v2[i]) {
                equal = false;
            }
        }
        return equal;
    }

    public static boolean contains(int[] v, int[][] vArray) throws InvalidArrayError {
        if (!validateArray(new int[][] {}))
    }

    /**
     * Adds an integer n to an array v
     * @param v The specified array
     * @param n The constant to add
     * @return The result of the operation
     */
    public static int[] add(int[] v, int n) {
        int size = v.length;
        int[] result = new int[size];
        for (int i = 0; i < size; i++) {
            result[i] = v[i] + n;
        }
        return result;
    }

    /**
     * Adds two arrays of the same length together
     * @param v1 The first array
     * @param v2 The second array
     * @return The result of the operation
     * @throws InvalidArrayError If:
     *  - The arrays size doesn't match
     *  - The arrays have a size of 0
     */
    public static int[] add(int[] v1, int[] v2) throws InvalidArrayError {
        if (!validateArray(new int[][] {v1, v2})) {
            throw new InvalidArrayError("The size of the arrays must match");
        }
        int size = v1.length;
        int[] result = new int[size];
        for (int i = 0; i < size; i++) {
            result[i] = v1[i] + v2[i];
        }
        return result;
    }

    /**
     * Adds n arrays of the same length together
     * @param vArray An array consisting of the different arrays to combine
     * @return The result of the operation
     * @throws InvalidArrayError If:
     *  - The arrays size doesn't match
     *  - The arrays have a size of 0
     */
    public static int[] add(int[][] vArray) throws InvalidArrayError {
        if (!validateArray(vArray)) {
            throw new InvalidArrayError("The size of the arrays must match");
        }
        int size = vArray[0].length;
        int[] result = new int[size];
        for (int i = 0; i < size; i++) {
            result[i] = 0;
            for (int[] array : vArray) {
                result[i] += array[i];
            }
        }
        return result;
    }

    /**
     * Substract an integer n to an array v
     * @param v The specified array
     * @param n The constant to substract
     * @return The result of the operation
     */
    public static int[] substract(int[] v, int n) {
        return add(v, -n);
    }

    /**
     * Substracts the second array from the first array
     * @param v1 The first array
     * @param v2 The second array
     * @return The result of the operation
     * @throws InvalidArrayError If:
     *  - The arrays size doesn't match
     *  - The arrays have a size of 0
     */
    public static int[] substract(int[] v1, int[] v2) throws InvalidArrayError {
        return add(v1, negate(v2));
    }

    /**
     * Multiplys an integer n to an array v
     * @param v The specified array
     * @param n The constant to multiply
     * @return The result of the operation
     */
    public static int[] multiply(int[] v, int n) {
        int size = v.length;
        int[] result = new int[size];
        for (int i = 0; i < size; i++) {
            result[i] = v[i] * n;
        }
        return result;
    }

}
