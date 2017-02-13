/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.wordcount;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Allows {@link WordCountingTrie} to be represented as an immutable Map.
 * @author bshaw
 * @param K key type - some form of String
 */
public class WordCountMap extends AbstractMap<CharSequence, Integer> {

    WordCountingTrie trie;
    
    WordCountMap(WordCountingTrie trie) {
        this.trie = trie;
    }
    
    /** Set wrapper for WordCountTrie. */
    private static class WordCountTrieWrapper extends AbstractSet<Map.Entry<CharSequence, Integer>>{
        
        WordCountingTrie trie;

        WordCountTrieWrapper(WordCountingTrie trie) {
            this.trie = trie;
        }

        @Override
        public Iterator<Map.Entry<CharSequence, Integer>> iterator() {
            return new WrapperIterator(trie);
        }

        @Override
        public int size() {
            return trie.size();
        }

        /**
         * 
         * @param word
         * @return 
         */
        @Override
        public boolean contains(Object word){
            return trie.contains(word);
        }
    
        /** Iterates through all words in the map in no particular order.
         * If a node is inserted during the iteration, behavior is not defined.
         */
        private class WrapperIterator implements Iterator<Map.Entry<CharSequence, Integer>>{

            WordCountingTrie trie;
            Iterator<CharSequence> iterator;
            
            WrapperIterator( WordCountingTrie trie ){
                this.trie = trie;
                iterator = trie.iterator();
            }

            @Override
            public boolean hasNext(){
                return iterator.hasNext();
            }


            @Override
            public Map.Entry next(){
                CharSequence word = iterator.next();
                Integer count = trie.countOf(word);
                return new AbstractMap.SimpleImmutableEntry<>(word, count);
            }
        }
    }
    
    
    /** Returns a set of entries that are represented in this map.
     * @return 
     */
    @Override
    public Set<Entry<CharSequence, Integer>> entrySet() {
        return new WordCountTrieWrapper(trie);
    }
    
    /**
     * Gets the count associated with a given string.
     * @param o
     * @return 
     */
    @Override
    public Integer get(Object o){
        return trie.countOf((CharSequence) o);
    }
    
    /**
     * Returns the number of keys which are represented in this map.
     * @return number of keys
     */
    @Override
    public int size(){
        return trie.size();
    }
}
