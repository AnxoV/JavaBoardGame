package src.Logic;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * To-Do:
 *  - Chekc if the static method contains can be recycled
 */

/**
 * Dynamic implemention of the Java Array.
 * 
 * <p>Comes with a wide variety of functions for array manipulation.
 * 
 * @param <E> the type of elements in the array
 * 
 * @since JDK 1.11
 * @version 1.0
 * 
 */
public class DynamicArray<E> implements Iterable<E> {
    /**
     * The array buffer to store the elements of the DynamicArray.
     */
    private E[] array;

    /**
     * Constructs an empty array with an initial capacity of 0.
     */
    @SuppressWarnings("unchecked")
    public DynamicArray() {
        array = (E[]) new Object[0];
    }

    /**
     * Constructs an empty array with an initial capacity of size.
     * @param size the new size of the array
     * @throws IllegalArgumentException if the specified initial capacity is negative
     */
    @SuppressWarnings("unchecked")
    public DynamicArray(int initialCapacity) {
        if (initialCapacity >= 0) {
            array = (E[]) new Object[initialCapacity];
        } else {
            throw new IllegalArgumentException("Illegal capacity: " + initialCapacity);
        }
    }

    /**
     * Returns the array of the object.
     * @return The array of the object 
     */
    public E[]  toArray() {
        int size = size();
        E[] result = (E[]) new Object[size];
        for (int i = 0; i < size; i++) {
            result[i] = (E) array[i]; 
        }
        return result;
    }

    /**
     * Constructs a {@code DynamicArray} object from the specified array.
     * @param array - The specified array to convert
     * @return A {@code DynamicArray} object from the specified array
     */
    public static <E> DynamicArray<E> of(E[] array) {
        DynamicArray<E> result = new DynamicArray<>(array.length);
        for (int i = 0; i < result.size(); i++) {
            result.set(i, array[i]);
        }
        return result;
    }

    /**
     * Returns the size of the array.
     * @return The size of the array
     */
    public int size() {
        return array.length;
    }

    /**
     * Asserts if the array is equal in size to a specified array.
     * @param array - The array to check
     * @return {@code true} if both arrays have the same size, {@code false} otherwise
     */
    public boolean isSameSize(DynamicArray<E> array) {
        return size() == array.size();
    }

    /**
     * Returns {@code true} if the array contains no elements.
     * @return {@code true} if the array contains no elements
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns the element at the specified position in the array.
     * @param index - The position of the element to return
     * @return The element at the specified position in the array
     * @throws IndexOutOfBoundsException If the index is outside the array boundaries
     */
    public E get(int index) throws IndexOutOfBoundsException {
        return (E) array[index];
    }

    /**
     * Modifies the element at the specified position in the array to element.
     * @param index - Index of the element to modify
     * @param element - The element to set the value of
     * @throws IndexOutOfBoundsException If the index is outside the array boundaries
     */
    public void set(int index, E element) throws IndexOutOfBoundsException {
        array[index] = element;
    }


    /**
     * Inserts an element at the specified position in the array.
     * Shifts the element currently at that position and subsequent
     * elements to the right.
     * @param index - The position in the array
     * @param element - The element to add
     */
    public void push(int index, E element) {
        DynamicArray<E> result = new DynamicArray<>(size()+1);
        for (int i = 0; i < index; i++) {
            result.set(i, get(i));
        }
        result.set(index, element);
        for (int i = index; i < size(); i++) {
            result.set(i+1, get(i));
        }
        array = result.array;
    }

    /**
     * Adds an element after the last position in the array.
     * @param element - The element to add
     */
    public void push(E element) {
        push(size(), element);
    }


    /**
     * Removes the specified index from the array.
     * @param index - The index to remove
     */
    public void pop(int index) {
        DynamicArray<E> result = new DynamicArray<>(size()-1);
        for (int i = 0; i < index; i++) {
            result.set(i, get(i));
        }
        for (int i = index+1; i < size(); i++) {
            result.set(i-1, get(i));
        }
        array = result.array;
    }

    /**
     * Removes the last index from the array.
     */
    public void pop() {
        pop(size()-1);
    }

    /**
     * Removes the first <b>n</b> elements found in the array.
     * @param element - The element to remove
     * @param max - The maximum number of elements to be removed
     * @return {@code true} if the array contained the specified element;
     */
    public boolean pop(E element, int max) {
        int occurences = 0;
        for (int i = 0; i < size() && occurences < max; i++) {
            if (get(i).equals(element)) {
                pop(i);
                i--;
                occurences++;
            }
        }
        if (occurences > 0) {
            return true;
        }
        return false;
    }

    /**
     * Removes the first occurence of an element from the array.
     * @param element - The element to remove
     * @return {@code true} if the array contained the specified element;
     */
    public boolean pop(E element) {
        return pop(element, 1);
    }

    /**
     * Removes all the occurences of a specified element in the array.
     * @param element - The element to remove
     * @return {@code true} if the array contained the specified element;
     */
    public boolean popAll(E element) {
        return pop(element, Integer.MAX_VALUE);
    }

    /**
     * Removes from the list all of the elements whose index is between
     * {@code fromIndex}, inclusive, and {@code toIndex}, exclusive.
     * Shifts any elements to the left if needed. This method shortens
     * the array by {@code (toIndex - fromIndex)} elements.
     * (If {@code toIndex == fromIndex}, this operation has no effect)
     * @param fromIndex - The start of the range
     * @param toIndex - The end of the range
     */
    public void popRange(int fromIndex, int toIndex) {
        if (fromIndex > toIndex) {
            throw new IndexOutOfBoundsException("fromIndex must be lower than toIndex");
        }

        DynamicArray<E> result = new DynamicArray<>(size()-(toIndex-fromIndex));
        for (int i = 0; i < fromIndex; i++) {
            result.set(i, get(i));
        }
        for (int i = toIndex; i < size(); i++) {
            result.set(i-(toIndex-fromIndex), get(i));
        }
        array = result.array;
    }

    /**
     * Returns a subarray of the array between the range, {@code fromIndex},
     * and {@code toIndex}, both inclusive.
     * (If {@code fromIndex} and {@code toIndex} are equal, the returned
     * array is constructed from the index at that position).
     * @param fromIndex - The start of the range
     * @param toIndex - The end of the range
     * @return The subarray constructed from the specified range
     */
    public DynamicArray<E> sublist(int fromIndex, int toIndex) {
        if (fromIndex > toIndex) {
            throw new IndexOutOfBoundsException("fromIndex must be lower than toIndex");
        }

        DynamicArray<E> result = new DynamicArray<>(toIndex-fromIndex+1);
        for (int i = 0; i < toIndex-fromIndex+1; i++) {
            result.set(i, get(i+fromIndex));
        }
        return result;
    }


    /**
     * Sets all of the elements from the list to {@code null}.
     */
    public void clear() {
        for (int i = 0; i < size(); i++) {
            set(i, null);
        }
    }


    /**
     * Reteurns the number of occurences of a specified element in the array.
     * @param element - The element to search for
     * @return The number of occurences found in the array
     */
    public int count(E element) {
        int count = 0;
        for (int i = 0; i < size(); i++) {
            if (array[i].equals(element)) {
                count++;
            }
        }
        return count;
    }


    /**
     * Joins another {@code DynamicArray} to the array
     * @param array - The {@code DynamicArray} to join
     */
    public void join(DynamicArray<E> array) {
        for (int i = 0; i < array.size(); i++) {
            push(array.get(i));
        }
    }

    /**
     * Returns the index of the first occurence of the specified element
     * on the array between the range, {@code start}, and {@code end},
     * both inclusive, or -1 if there is no such index.
     * @param element - The element to search
     * @param start - The start of the range
     * @param end - The end of the range
     * @return The index of the first occurence of the specified element on the array
     * @throws ArrayIndexOutOfBoundsException if {@code start < 0}
     */
    public int indexOfRange(E element, int start, int end) {
        for (int i = start; i <= end; i++) {
            if (element == null || element.equals(get(i))) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns the index of the first occurence of the specified element on the array
     * or -1 if there is no such index.
     * @param element - The element to search
     * @return The index of the first occurente of the specified element on the array
     */
    public int indexOf(E element) {
        return indexOfRange(element, 0, size()-1);
    }

    /**
     * Returns the index of the last occurence of the specified element
     * on the array between the range, {@code start}, and {@code end},
     * both inclusive, or -1 if there is no such index.
     * @param element - The element to search
     * @param start - The start of the range
     * @param end - The end of the range
     * @return The index of the last occurence of the specified element on the array
     * @throws ArrayIndexOutOfBoundsException if {@code end >= array.size()}
     */
    public int lastIndexOfRange(E element, int start, int end) {
        for (int i = end; i >= start; i--) {
            if (element == null || element.equals(get(i))) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns the index of the last occurence of the specified element on the array
     * or -1 if there is no such index. 
     * @param element - The element to search
     * @return The index of the last occurence of the specified element on the array
     */
    public int lastIndexOf(E element) {
        return lastIndexOfRange(element, 0, size()-1);
    }


    /**
     * Returns {@code true} if the array contains the specified element.
     * @param element - The element to search
     * @return {@code true} if the array contains the specified element
     */
    public boolean contains(E element) {
        return indexOf(element) >= 0;
    }
    
    /**
     * Asserts if the array is equal to a specified array.
     * @param array - The array to check
     * @return {@code true} if both arrays are equal, {@code false} otherwise
     */
    public boolean equals(DynamicArray<E> array) {
        if (array == this) {
            return true;
        }

        if (!(isSameSize(array))) {
            return false;
        }

        boolean equal = true;
        for (int i = 0; i < size() && equal; i++) {
            if (!get(i).equals(array.get(i))) {
                return false;
            }
        }
        return equal;
    }


    /**
     * Returns an iterator over the elements in the array.
     * @return an iterator over the elements in the array
     * @see java.util.ArrayList
     */
    public Iterator<E> iterator() {
        return new Itr();
    }

    /**
     * Implementation of the Iterable class.
     */
    private class Itr implements Iterator<E> {
        /**
         * Index of the next element to return.
         */
        int cursor;

        Itr(){}

        public boolean hasNext() {
            return cursor != size();
        }
        
        public E next() {
            int i = cursor;
            if (i >= size()) {
                throw new NoSuchElementException();
            }
            cursor++;
            return array[cursor-1];
        }
    }


    /**
     * Maps the array to an int array.
     * @return The mapped int array
     */
    public int[] mapToInt() {
        int size = size();
        int[] result = new int[size];
        for (int i = 0; i < size; i++) {
            result[i] = (int) get(i);
        }
        return result;
    }

    /**
     * Maps the specified array to an int array.
     * @param array - The specified array to map
     * @return The mapped int array
     */
    public static <E> int[] mapToInt(E[] array) {
        int size = array.length;
        int[] result = new int[size];
        for (int i = 0; i < size; i++){
            result[i] = (int) array[i];
        }
        return result;
    }

    /**
     * Maps the specified array to an int array.
     * @param array - The specified array to map
     * @return The mapped int array
     */
    public static <E> int[] mapToInt(DynamicArray<E> array) {
        return mapToInt(array.toArray());
    }
}
