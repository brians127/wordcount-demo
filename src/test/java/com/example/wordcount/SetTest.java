/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.wordcount;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test suite for any Set implementation.
 * @author bshaw
 */
public class SetTest extends UnitTest {
    /* Set methods. Note that comments come from the Javadoc for java.util.Set.
    
     * add(E e); // Adds the specified element to this set if it is not already present (optional operation).
     * boolean addAll(Collection<? extends E> c); // Adds all of the elements in the specified collection to this set if they're not already present (optional operation).
     * void clear(); // Removes all of the elements from this set (optional operation).
     * boolean contains(Object o); // Returns true if this set contains the specified element.
     * boolean containsAll(Collection<?> c); // Returns true if this set contains all of the elements of the specified collection.
     * boolean equals(Object o); // Compares the specified object with this set for equality.
     * int hashCode(); // Returns the hash code value for this set.
     * boolean isEmpty(); // Returns true if this set contains no elements.
     * Iterator<E> iterator(); // Returns an iterator over the elements in this set.
     * boolean remove(Object o); // Removes the specified element from this set if it is present (optional operation).
     * boolean removeAll(Collection<?> c); // Removes from this set all of its elements that are contained in the specified collection (optional operation).
     * boolean retainAll(Collection<?> c); // Retains only the elements in this set that are contained in the specified collection (optional operation).
     * int size(); // Returns the number of elements in this set (its cardinality).
     * default Spliterator<E> 	spliterator(); // Creates a Spliterator over the elements in this set.
     * Object[] toArray(); // Returns an array containing all of the elements in this set.
     * <T> T[] toArray(T[] a); // Returns an array containing all of the elements in this set; the runtime type of the returned array is that of the specified array.
     */
    
    public static void verifySet( Set<? extends Object> set ){
        Object[] array = set.toArray();
        // No two objects are the same
        for (int i = 0; i < array.length; i++){
            for (int j = 0; j < array.length; j++){
                if (i != j){
                    Object o1 = array[i];
                    Object o2 = array[j];
                    Assert.assertNotSame(o1, o2);
                    Assert.assertFalse(o1.equals(o2));
                    Assert.assertFalse(o2.equals(o1));
                }
            }
        }
    }
    
    
    public static void verifySize( Collection<? extends Object> items, int expected_size ){
        
        if (expected_size == 0){
            Assert.assertTrue(items.isEmpty());
        } else {
            Assert.assertFalse(items.isEmpty());
        }
        
        Assert.assertTrue(items.size() == expected_size);
        Assert.assertTrue(items.toArray().length == expected_size);
        Assert.assertTrue(items.toArray(new Object[expected_size + 1]).length == expected_size);
        
        Iterator<? extends Object> iter = items.iterator();
        for (int i = 0; i < expected_size; i++){
            Assert.assertTrue(iter.hasNext());
            iter.next();
        }
        Assert.assertFalse(iter.hasNext());
    }
    
    // TODO: collection insert and remove tests
}
