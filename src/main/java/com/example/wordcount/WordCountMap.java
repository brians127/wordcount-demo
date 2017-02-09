/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.wordcount;

import java.util.AbstractMap;
import java.util.Set;

/**
 * Allows {@link WordCountingTrie} to be represented as a Map.
 * @author bshaw
 * @param K key type - some form of String
 */
public class WordCountMap<K extends CharSequence, Integer> extends AbstractMap<K, Integer> {

    WordCountMap(WordCountingTrie trie) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<Entry<K, Integer>> entrySet() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
