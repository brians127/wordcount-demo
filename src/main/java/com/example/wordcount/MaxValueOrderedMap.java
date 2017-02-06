package com.example.wordcount;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/** A map that stores its keys by comparing their associated values in descending order.
 * @author bshaw
 * @param <K>
 * @param <V>
 */
public class MaxValueOrderedMap<K, V extends Comparable> extends AbstractMap<K, V>{
    
    private Set<Entry> keyvalues;

    public MaxValueOrderedMap() {
        this.keyvalues = new MaxTreeSet<>();
    }
    
    /**
     * Note: this class has a natural ordering that is inconsistent with equals.
     * @param <K>
     * @param <V> 
     */
    private static class Entry<K, V extends Comparable> extends AbstractMap.SimpleEntry<K, V> implements Comparable {
        // Invariant: Parent >= this >= bigchild >= smallchild
        
        /**
         * Creates an entry representing a mapping from the specified key to the specified value.
         */
        Entry(K key, V value){
            super(key, value);
        }
        
        /**
         * Creates an entry representing the same mapping as the specified entry.
         */
        Entry(Map.Entry<? extends K,? extends V> entry){
            super(entry);
        }
        
        
        /** As per {@link java.lang.Comparable}, compares the natural order of this entry to the target.
         * This class is ordered using the values, which is inconsistent with equals().
         * @param target Entry to be compared to
         * @return a negative integer, zero, or a positive integer when this entry is less than, equal to, or greater than the specified entry (respectively).
         */
        @Override
        public int compareTo(Object o){
            // NOTE: it's not valid to compare entries to other object types
            Entry<? extends K, ? extends V> target = (Entry<? extends K, ? extends V>) o;
            return ((Comparable) getValue()).compareTo(target.getValue());    
        }
    }
    
    
    /** Returns a Set view of the mappings contained in this map.
     * @return Set view of all mappings that may not be used to modify the mappings.
     */
    @Override
    public Set<Map.Entry<K, V>> entrySet(){
        throw new UnsupportedOperationException("Not Implemented"); // TODO
    }

    

    /** Returns true if this map contains a mapping for the specified key.
     */
    public boolean containsKey(Object key){
        throw new UnsupportedOperationException("Not Implemented"); // TODO
    }
    
    
    /** Returns true if this map maps one or more keys to the specified value.
     */
    @Override
    public boolean containsValue(Object value){
        throw new UnsupportedOperationException("Not Implemented"); // TODO
    }
    
    
    /** Returns true if this map contains no key-value mappings.
     */
    @Override
    public boolean isEmpty(){
        throw new UnsupportedOperationException("Not Implemented"); // TODO
    }
    
    
    /** Returns a Set view of the keys contained in this map.
     * @return 
     */
    @Override
    public Set<K> keySet(){
        throw new UnsupportedOperationException("Not Implemented"); // TODO
    }

    
    /**
     * Associates the specified value with the specified key in this map.
     * @param key
     * @param value
     * @return 
     */
    @Override
    public V put(K key, V value){
        throw new UnsupportedOperationException("Not Implemented"); // TODO
    }

    
    /**
     * Copies all of the mappings from the specified map to this map.
     * @param source
     */
    @Override
    public void putAll(Map<? extends K,? extends V> source){
        source.forEach((key, value)->{
            this.put(key, value);
        });
    }
    
    
    /**
     * Removes the mapping for a key from this map if it is present.
     * @param key
     * @return 
     */
    @Override
    public V remove(Object key){
        throw new UnsupportedOperationException("Not Implemented"); // TODO
    }
    
    
    /**
     * Returns the number of key-value mappings in this map.
     * @return 
     */
    @Override
    public int size(){
        throw new UnsupportedOperationException("Not Implemented"); // TODO
    }

    
    /**
     * Returns a Collection view of the values contained in this map.
     * @return 
     */
    @Override
    public Collection<V> values(){
        throw new UnsupportedOperationException("Not Implemented"); // TODO
    }
}

