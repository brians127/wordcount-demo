package com.example.wordcount;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

/**
 * A Set used to store entries of a MaxValueOrderedMap in a max heap / binary tree.
 * 
 * @author bshaw
 */
class MaxTreeSet<E extends Comparable> extends AbstractSet<E>{
    
    private final Vector<E> contents;
    private int actual_size;

    public MaxTreeSet() {
        this.actual_size = 0;
        this.contents = new Vector<>();
    }
    
    /**
     * Adds the specified element to this set if it is not already present.
     * WARNING: if (new_element.compareTo(existing) == 0), then element WILL NOT be inserted!
     * This is because max heaps 
     * @Pre Set represents a max heap using the ordering of the elements
     * @param new_element
     * @return 
     */
    @Override
    public boolean add(E new_element){
        // NOTE: One based indexing, to simplify new index calculation
        int index = 1;
        while (index < contents.size() + 1){
            E compare_item = contents.elementAt(index - 1); // vector uses 0-based indices
            if (compare_item.equals(new_element)){
                // cannot insert duplicate into a set
                return false;
            }
            
            int comparison = compare_item.compareTo(new_element);
            if (comparison <= 0){
                // routine case - new item will be smaller
                assert(index < (1 << 31)); // check for overflow
                index <<= 1;
                continue;
            } else {
                // new item is larger - swap with existing, then find a new home for existing item
                E temp = new_element;
                new_element = compare_item;
                contents.add(index, temp);
                continue;
            }
        }
    }
    
    
    /**
     * Adds all of the elements in the specified collection to this set if they're not already present.
     * @param c
     * @return 
     */
    @Override
    public boolean addAll(Collection<? extends E> collection){
        boolean modified = false;
        for (E item : collection){
            modified |= add(item);
        }
        return modified;
    }
    
    
    /**
     * Removes all of the elements from this set.
     */
    @Override
    public void clear(){
        contents.clear();
    }
    
    
    /** 
     * Returns true if this set contains the specified element.
     * @param o
     * @return 
     */
    @Override
    public boolean contains(Object o){
        // NOTE: One based indexing, to simplify new index calculation
        if (!o.instanceOf(Comparable)){
            // it couldn't have been inserted
            return false;
        }
        if (isEmpty()){
            return false;
        }
        
        Comparable item = (Comparable) o;
        E compare_item = contents.elementAt(0);
        if (item.compareTo(compare_item) > 0){
            // o is larger than anything in the max heap, no point in searching it
            return false;
        }
        
        int index = 1;
        do {
            // Search children of the node we just inspected, making as few compareTo()s as possible. 
            assert ((index << 1) > 0); // storage overflow is not allowed when traversing a valid max heap
            int greater_child_index;
            int lesser_child_index;
            E greater_child;
            E lesser_child;
            final int left_child_index = index << 1;
            final E left_child = contents.elementAt(left_child_index - 1);
            final int right_child_index = (index << 1) + 1;
            final E right_child = contents.elementAt(right_child_index - 1);
            
            assert (left_child.compareTo(right_child) != 0);
            
            // don't assume compareTo() and equals() align - get this check out of the way
            if (item.equals(left_child) || item.equals(right_child)){
                return true;
            }
            
            if (left_child.compareTo(right_child) < 0){
                lesser_child = left_child;
                lesser_child_index = left_child_index;
                greater_child = right_child;
                greater_child_index = right_child_index;
            } else {
                lesser_child = right_child;
                lesser_child_index = right_child_index;
                greater_child = left_child;
                greater_child_index = left_child_index;
            }
            
            // compare to greater child - the one on the left
            int greater_comparison = item.compareTo(greater_child);
            if (greater_comparison <)
            if (item.compareTo(left_child) > 0){
                // it's larger than anything in the left or right trees
                return false;
            }
            // compare to lesser child - the one on the right
            
            if (item.equals(right_child)){
                return true;
            }
            if (item.compareTo(right_child) > 0){
                // it's larger than anything in the right tree, so search the left tree
                index <<= 1;
            } else {
                // search the right tree
                
            }
            
            
            
            E compare_item = contents.elementAt(index - 1); // vector uses 0-based indices
            if (compare_item.equals(o)){
                return true;
            }
            int comparison = compare_item.compareTo(o);
            if (comparison > 0){
                // we're a max heap, and everything from here onwards is less than 'o'
                return false;
            } else if ({
                // routine case - new item will be smaller
                assert(index < (1 << 31)); // check for overflow
                index <<= 1;
            } else {
                
                E temp = new_element;
                new_element = compare_item;
                contents.add(index, temp);
                continue;
            }
        } while (index < contents.size() + 1);
    }
    
    
    /**
     * Returns true if this set contains all of the elements of the specified collection.
     * @param collection Group of items that must be in the set
     * @return Whether or not all items specified are in the set.
     */
    @Override
    public boolean containsAll(Collection<?> collection){
        for (Object item : collection){
            if (!contains(item)){
                return false;
            }
        }
        return true;
    }


    /** 
     * Returns true if this set contains no elements.
     * @return 
     */
    @Override
    public boolean isEmpty(){
        return contents.isEmpty();
    }
    
    
    /** Returns an iterator over the elements in this set.
     * 
     * @return 
     */
    @Override
    public Iterator<E> iterator(){
        throw new UnsupportedOperationException("Not Implemented"); // TODO
    }
    
    
    /**
     * Removes the specified element from this set if it is present.
     * @param o
     * @return 
     */
    @Override
    public boolean remove(Object o){
        throw new UnsupportedOperationException("Not Implemented"); // TODO
    }
    

    /**
     * Retains only the elements in this set that are contained in the specified collection.
     * @param c
     * @return 
     */
    @Override
    public boolean retainAll(Collection<?> c){
        throw new UnsupportedOperationException("Not Implemented"); // TODO
    }
    
    /**
     * Returns the number of elements in this set.
     * @return 
     */
    @Override
    public int size(){
        return actual_size;
    }
    
    /**
     * Returns an array containing all of the elements in this set.
     * @return 
     */
    @Override
    public Object[] toArray(){
        return toArray((Object[]) null);
    }
    
    /** 
     * Returns an array containing all of the elements in this set; the runtime type of the returned array is that of the specified array.
     * @param <T>
     * @param a
     * @return 
     */
    @Override
    public <T> T[] toArray(T[] a){
        throw new UnsupportedOperationException("Not Implemented"); // TODO
    }
}
