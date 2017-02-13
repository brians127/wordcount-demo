/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.wordcount;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author bshaw
 */
public class WordCountingTrieTest extends UnitTest {
    
    /** Verify correct behavior with an empty set.
     */
    @Test
    public void testEmptySet(){
        Set<CharSequence> set = new WordCountingTrie();
        SetTest.verifySize(set, 0);
    }
    
    /** Verify correct behavior with a simple set without duplicates.
     */
    @Test
    public void testSimpleSet(){
        Set<CharSequence> set = new WordCountingTrie();
        set.add("two");
        SetTest.verifySize(set, 1);
        SetTest.verifySet(set);
        Assert.assertTrue(set.contains("two"));
        set.add("negative");
        SetTest.verifySize(set, 2);
        SetTest.verifySet(set);
        Assert.assertTrue(set.contains("negative"));
        set.add("three");
        SetTest.verifySize(set, 3);
        SetTest.verifySet(set);
        Assert.assertTrue(set.contains("three"));
        set.add("affirmative");
        SetTest.verifySize(set, 4);
        SetTest.verifySet(set);
        Assert.assertTrue(set.contains("two"));
        Assert.assertTrue(set.contains("negative"));
        Assert.assertTrue(set.contains("three"));
        Assert.assertTrue(set.contains("affirmative"));
        Assert.assertFalse(set.contains("affirm"));
        Assert.assertFalse(set.contains("affirmatively"));
        Assert.assertFalse(set.contains("cow"));
        Assert.assertFalse(set.contains(""));
        Assert.assertFalse(set.contains("t"));
    }
    
    
    /** Verify correct behavior when the same word is inserted multiple times.
     */
    @Test
    public void testSetWithDuplicates(){
        WordCountingTrie set = new WordCountingTrie();
        Assert.assertTrue(set.add("two"));
        Assert.assertTrue(set.add("hamburger"));
        Assert.assertFalse(set.add("two"));
        Assert.assertTrue(set.add("three"));
        Assert.assertFalse(set.add("three"));
        Assert.assertFalse(set.add("three"));
        SetTest.verifySet(set);
        SetTest.verifySize(set, 3); // 'special' gets inserted once, and the other -1 is considered to be the same item
        Assert.assertTrue(set.contains("two"));
        Assert.assertTrue(set.contains("hamburger"));
        Assert.assertTrue(set.contains("three"));
        Assert.assertFalse(set.contains(""));
        Assert.assertFalse(set.contains("t"));
        Assert.assertFalse(set.contains("affirmative"));
        Assert.assertTrue(set.countOf("two") == 2);
        Assert.assertTrue(set.countOf("three") == 3);
        Assert.assertTrue(set.countOf("cow") == 0);
    }
    
    
    @Test
    public void testRelatedStrings(){
        WordCountingTrie trie = new WordCountingTrie();
        trie.add("Lorem");
        trie.add("");
        trie.add("Lipstick");
        trie.add("Pig");
        trie.add("P");
        trie.add("Pi");
        trie.add("Piglet");
        trie.add("Lipstick");
        trie.add("");
        Assert.assertEquals(trie.countOf("Lorem"), 1);
        Assert.assertEquals(trie.countOf("Lipstick"), 2);
        Assert.assertEquals(trie.countOf("P"), 1);
        Assert.assertEquals(trie.countOf("Pi"), 1);
        Assert.assertEquals(trie.countOf("Pig"), 1);
        Assert.assertEquals(trie.countOf("Piglet"), 1);
        Assert.assertEquals(trie.countOf(""), 2);
        Assert.assertEquals(trie.countOf("Loser"), 0);
    }
    
    /** Verify that clearing a set results in a set of size 0.
     */
    @Test
    public void testClear(){
        WordCountingTrie set = new WordCountingTrie();
        set.add("my");
        set.add("hamburger");
        set.add("fries");
        set.add("three");
        set.add("three");
        set.add("three");
        SetTest.verifySize(set, 4);
        SetTest.verifySet(set);
        set.clear();
        SetTest.verifySize(set, 0);
    }
    
    // TODO: collection insert and remove tests
}
