package com.example.wordcount;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author bshaw
 */
public class MaxTreeSetTest extends UnitTest {
    
    
    public static void verifyMaxHeap( Collection<? extends Comparable> items ){
        if (!items.isEmpty()){
            Iterator<? extends Comparable> iter = items.iterator();
            Comparable prev = iter.next();
            // NOTE: 'null' should not be in a max heap since it can't be compared to other items
            Assert.assertNotNull(prev);
            while (iter.hasNext()){
                Comparable curr = iter.next();
                Assert.assertNotNull(curr);
                Assert.assertTrue( Math.signum(prev.compareTo(curr)) == -Math.signum(curr.compareTo(prev)) );
                Assert.assertTrue( prev.compareTo(curr) >= 0 );
            }
        }
    }
    
    
    @Test
    public static void testEmptySet(){
        Set<Integer> set = new MaxTreeSet<>();
        SetTest.verifySize(set, 0);
    }
    
    
    @Test
    public static void testSimpleSet(){
        Set<Integer> set = new MaxTreeSet<>();
        set.add(new Integer(2));
        set.add(new Integer(-1));
        set.add(new Integer(3));
        set.add(new Integer(0));
        SetTest.verifySize(set, 4);
        SetTest.verifySet(set);
        verifyMaxHeap(set);
        Assert.assertTrue(set.contains(new Integer(2)));
        Assert.assertTrue(set.contains(new Integer(-1)));
        Assert.assertTrue(set.contains(new Integer(3)));
        Assert.assertTrue(set.contains(new Integer(0)));
        Assert.assertFalse(set.contains(new Integer(233)));
    }
    
    /** Verify correct behavior when using an object type where .equals() and .compareTo() behave the same way.
     */
    @Test
    public static void testIntegerSetWithDuplicates(){
        Set<Integer> set = new MaxTreeSet<Integer>();
        Integer special = new Integer(-1);
        set.add(new Integer(2));
        set.add(special);
        set.add(new Integer(3));
        set.add(new Integer(-1));
        set.add(special);
        SetTest.verifySet(set);
        SetTest.verifySize(set, 3); // 'special' gets inserted once, and the other -1 is considered to be the same item
        verifyMaxHeap(set);
    }
    
    /** A simple class that has a natural ordering that is inconsistent with equals.
     * 
     */
    static class Oddball implements Comparable {
        
        int payload;
        
        public int compareTo(Object o){
            Oddball other = (Oddball) o; // throws ClassCastException if type doesn't match
            return payload - other.payload;
        }
        
        public Oddball(int value){
            payload = value;
        }
        
        // Inherit .equals() behavior from Object that only returns true if (this == other)
    }    
    
    /** Verify correct behavior when using an object type where .equals() and .compareTo() are not consistent.
     */
    @Test
    public static void testOddballSetWithDuplicates(){
        Set<Oddball> set = new MaxTreeSet<Oddball>();
        Oddball neg_one = new Oddball(-1);
        Oddball neg_one_dupe = new Oddball(-1);
        Oddball two = new Oddball(2);
        Oddball three = new Oddball(3);
        set.add(two);
        set.add(neg_one);
        set.add(three);
        set.add(neg_one_dupe);
        set.add(neg_one);
        SetTest.verifySet(set);
        SetTest.verifySize(set, 4); // 'special' gets inserted once, but the other -1 isn't considered to be the same item
        verifyMaxHeap(set);
        Assert.assertTrue(set.contains(neg_one));
        Assert.assertTrue(set.contains(neg_one_dupe));
        Assert.assertTrue(set.contains(two));
        Assert.assertTrue(set.contains(three));
        Assert.assertFalse(set.contains(new Oddball(233)));
        Assert.assertFalse(set.contains(new Oddball(-1)));
    }
    
    
    @Test
    public static void testClear(){
        Set<Integer> set = new MaxTreeSet<Integer>();
        set.add(new Integer(20));
        set.add(new Integer(21));
        set.add(new Integer(30));
        set.add(new Integer(0));
        SetTest.verifySize(set, 4);
        SetTest.verifySet(set);
        verifyMaxHeap(set);
        set.clear();
        SetTest.verifySize(set, 0);
    }
    
    // TODO: collection insert and remove tests
}