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

/**
 *
 * @author bshaw
 */
public class WordCountingTrieTest extends UnitTest {
    
    @Test
    public void testEmptySet(){
        Set<CharSequence> set = new WordCountingTrie();
        SetTest.verifySize(set, 0);
    }
    
    
    @Test
    public void testSimpleSet(){
        Set<CharSequence> set = new WordCountingTrie();
        set.add("two");
        set.add("negative");
        set.add("three");
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
    
    /** Verify correct behavior when using an object type where .equals() and .compareTo() behave the same way.
     */
    @Test
    public void testIntegerSetWithDuplicates(){
        WordCountingTrie set = new WordCountingTrie();
        set.add("two");
        set.add("hamburger");
        set.add("two");
        set.add("three");
        set.add("three");
        set.add("three");
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
