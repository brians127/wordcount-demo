package com.example.wordcount;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

/**
 * Note that this class does not copy the inputted CharSequence. 
 * @author bshaw
 */
public class WordCountingTrie extends AbstractSet<CharSequence> {
    
    final Node root = new Node();
    int size = 0; // makes size computation O(1) instead of traversing entire tree) 

    @Override
    public Iterator<CharSequence> iterator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int size() {
        return size;
    }
    
    @Override
    public boolean add(CharSequence word){
        if (root.addWord(word, 0)){
            size++;
            return true;
        } else {
            return false;
        }
    }
    
    private Node getNode(CharSequence word){
        return root.getNode(word, 0);
    }
    
    
    /**
     * 
     * @param word
     * @return 
     */
    @Override
    public boolean contains(Object word){
        if (word.instanceOf(CharSequence)){
            return getNode((CharSequence) word) != null;
        } else {
            return false;
        }
    }
    

    /** 
     * Determines how many times the given word was inserted into the Trie.
     * @param word 
     * @return Number of times the word was added to the trie. If the word is not present, the value 0 is returned and the trie is not modified.
     */
    public int countOf(CharSequence word) {
        Node node = getNode(word);
        if (node == null){
            return 0;
        } else {
            return node.wordcount;
        }
    }
    
    
    @Override
    public void clear(){
        root.clear();
        size = 0;
    }
    
    
    private static class Node {
        // What this node represents
        String letters;
        // Children of the node
        Collection<Node> branches;
        // Payload - number of occurrences of the word
        int wordcount;
        
        /** Constructor for the root of the trie.
         */
        Node(){
            letters = "";
            branches = new Vector<Node>();
            wordcount = 0;
        }
        
        
        Node( CharSequence word, int index ){
            // toString() ensures that passing a mutable CharSequence as an argument doesn't result in us claiming ownership of the object.
            letters = word.subSequence(index, word.length()).toString();
            branches = new Vector<Node>();
            wordcount = 1;
        }
        
        /**
         * Recursive call to add a word to the trie, starting at this node.
         * If the word is an exact match, a counter tracking the number of times it has been seen will be incremented.
         * @param word Word being added
         * @param start_index 0-based index within the word to start from.
         * @return whether or not the set of words was modified (will be false if the count for a known word was incremented)
         */
        boolean addWord( CharSequence word, int start_index ){
            final int letters_length = letters.length(); // no API guarantee length() is constant time and fast
            final int word_length = word.length() - start_index;
            
            // find the first character in the word, starting at start_index, that doesn't match our contents
            final int limit = Math.min(letters_length, word_length);
            int nonmatch = limit;
            for (int i = 0; i < limit; i++){
                if (letters.charAt(i) != word.charAt(start_index + i)){
                    nonmatch = i;
                    break;
                }
            }
            
            // check if this node represents a substring of 'word'
            if (nonmatch < limit){
                // need to split the node
                assert( nonmatch > 0 );
                
                // Step 1: create new node for the portion removed
                Node newnode = new Node( letters, nonmatch );
                // Swap newnode's empty set of branches with our existing set
                Collection<Node> tmpbranches = newnode.branches;
                newnode.branches = branches;
                branches = tmpbranches;
                
                // Step 2: adjust our node's sequence
                letters = letters.substring(0, nonmatch);

                // Step 3: create our two branches: one for the portion removed, one for the newly inserted word
                assert( branches.isEmpty() );
                branches.add(newnode);
                branches.add(new Node( word, nonmatch ));
                return true;
                
            } else if (word_length == letters_length){
                // exact match for our word
                wordcount++;
                return false;
            } else {
                // string we're trying to match goes beyond our node's boundaries
                
                // Step 1: see if an existing branch has a longer subsequence of the node
                // if so, recurse into the appropriate branch
                assert( nonmatch <= letters_length );
                assert( nonmatch == Math.min(letters_length, word_length) );
                for (Node child : branches){
                    if (child.letters.charAt(0)== word.charAt(nonmatch)){
                        // NOTE: recursion!
                        return child.addWord(word, nonmatch);
                    }
                }
                
                // Step 2: if no match found in existing branches, add a new one
                branches.add(new Node( word, start_index ));
                return true;
            }
        }
            
        /* This should only be called on the root node. 
         * The rest of the tree can be garbage collected at this point.
         */
        void clear(){
            assert( letters.equals("") );
            wordcount = 0; // it's possible user added "" to the set.
            branches.clear();
        }
        
        /**
         * Recursive call to find a word in the trie, starting at this node.
         * If the word is an exact match, it will be returned. 
         * No change is made to the trie regardless.
         * @param word Word being added
         * @param start_index 0-based index within the word to start from.
         * @return matching node
         */
        private Node getNode(CharSequence word, int start_index) {
            final int letters_length = letters.length(); // no API guarantee length() is constant time and fast
            final int word_length = word.length() - start_index;
            
            // find the first character in the word, starting at start_index, that doesn't match our contents
            final int limit = Math.min(letters_length, word_length);
            int nonmatch = limit;
            for (int i = 0; i < limit; i++){
                if (letters.charAt(i) != word.charAt(start_index + i)){
                    nonmatch = i;
                    break;
                }
            }
            
            // check if this node represents a substring of 'word'
            if (nonmatch < limit){
                // node not found
                return null;
            } else if (word_length == letters_length){
                // exact match for our word
                return this;
            } else {
                // string we're trying to match goes beyond our node's boundaries
                
                // Step 1: see if an existing branch has what we're looking for
                // if so, recurse into the appropriate branch
                assert( nonmatch == letters_length );
                for (Node child : branches){
                    if (child.letters.charAt(0)== word.charAt(letters_length)){
                        // NOTE: recursion!
                        return child.getNode(word, letters_length);
                    }
                }
                
                // no branch found that could contain what we're looking for
                return null;
            }
        }
    }
}
