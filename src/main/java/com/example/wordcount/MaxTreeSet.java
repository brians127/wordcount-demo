package com.example.wordcount;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

/**
 *
 * @author bshaw
 */
public class MaxTreeSet<E extends Comparable> extends AbstractSet<E>{
    
    private final Vector<E> contents;
    private int actual_size;

    public MaxTreeSet() {
        this.actual_size = 0;
        this.contents = new Vector<>();
    }
    
    /**
     * Adds the specified element to this set if it is not already present.
     * @Pre Set represents a max heap using the ordering of the elements
     * @param new_element
     * @return 
     */
    @Override
    public boolean add(E new_element){
        throw new UnsupportedOperationException("Not Implemented"); // TODO
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
        throw new UnsupportedOperationException("Not Implemented"); // TODO
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
