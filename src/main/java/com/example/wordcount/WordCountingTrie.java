package com.example.wordcount;

import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;
import java.util.Vector;

/**
 *  
 * @author bshaw
 */
public class WordCountingTrie extends AbstractSet<CharSequence> {
    
    final Node root = new Node();
    int size = 0; // makes size computation O(1) instead of traversing entire tree) 

    @Override
    public Iterator<CharSequence> iterator() {
        return new WCTIterator(this);
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
    
    
    @Override
    public boolean remove(Object word){
        if (word instanceof CharSequence){
            Node node = root.getNode((CharSequence) word, 0);
            if (node == null){
                return false;
            } else {
                // 
                node.wordcount = 0;
                return true;
            }
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
        if (word instanceof CharSequence){
            Node node = getNode((CharSequence) word);
            return (node != null) && (node.wordcount > 0);
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
         * Calling this constructor does not count as inserting "" into the trie.
         */
        Node(){
            letters = "";
            branches = new Vector<Node>();
            wordcount = 0;
        }
        
        /** Constructor for additional nodes.
         * The supplied word is inserted into the trie.
         * @param word Sequence of letters this node represents
         * @param index Starting offset within 'word'
         */
        Node( CharSequence word, int index ){
            // toString() ensures that passing a mutable CharSequence as an argument doesn't result in us claiming ownership of the object.
            letters = word.subSequence(index, word.length()).toString();
            branches = new Vector<Node>();
            wordcount = 0;
        }
        
        /**
         * Recursive call to add a word to the trie, starting at this node.
         * If the word is an exact match, a counter tracking the number of times it has been seen will be incremented.
         * @param word Word being added
         * @param start_index 0-based index within the word to start from.
         * @return whether or not the set of words was modified (will be false if the count for a known word was incremented)
         */
        boolean addWord(CharSequence word, int start_index){
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
            if (nonmatch < letters_length){
                // need to split the node
                assert( nonmatch > 0 );
                
                // Step 1: create new node for the portion removed
                Node removed_portion = new Node(letters, nonmatch);
                removed_portion.wordcount = wordcount;
                wordcount = 0;
                // Swap newnode's empty set of branches with our existing set
                Collection<Node> tmpbranches = removed_portion.branches;
                removed_portion.branches = branches;
                branches = tmpbranches;
                
                // Step 2: adjust our node's sequence
                letters = letters.substring(0, nonmatch);
                
                // Step 3: create our two branches: one for the portion removed, one for the newly inserted word
                assert( branches.isEmpty() );
                branches.add(removed_portion);
                if (word_length == nonmatch){
                    // inserted word matches the subset of the existing node
                    System.out.println("Inserting matching subset " + letters + " for word " + word);
                    wordcount = 1;
                } else {
                    // inserted word = subset of existing + some other letters
                    Node new_branch = new Node(word, nonmatch + start_index);
                    new_branch.wordcount = 1;
                    System.out.println("Inserting new tail " + new_branch.letters + " with prefix " + letters + " for word " + word);
                    branches.add(new_branch);
                }
                return true;
                
            } else if (word_length == letters_length){
                // exact match for our word
                // that said, wordcount of 0 means it was previously removed.
                System.out.println("Found duplicate of existing word " + word);
                return (wordcount++ == 0);
            } else {
                assert( nonmatch == letters_length );
                // string we're trying to match goes beyond our node's boundaries
                
                // Step 1: see if an existing branch has a longer subsequence of the node
                // if so, recurse into the appropriate branch
                for (Node child : branches){
                    if (child.letters.charAt(0)== word.charAt(nonmatch + start_index)){
                        // NOTE: recursion!
                        System.out.println("Recursing for " + word + " because branch found with the letter " + child.letters.substring(0,1));
                        return child.addWord(word, nonmatch + start_index);
                    }
                }
                
                // Step 2: if no match found in existing branches, add a new one
                Node new_node = new Node(word, start_index);
                new_node.wordcount = 1;
                branches.add(new_node);
                System.out.println("Inserting new branch " + new_node.letters + " with prefix " + letters + " for word " + word);
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
            
            System.out.println("Searching for " + word + " in node " + letters);
            
            // check if this node represents a substring of 'word'
            if (nonmatch < letters_length){
                // node not found
                System.out.println("No match found for " + word + " because it's less than " + letters);
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
                    if (child.letters.charAt(0)== word.charAt(letters_length + start_index)){
                        // NOTE: recursion!
                        return child.getNode(word, letters_length + start_index);
                    }
                }
                
                // no branch found that could contain what we're looking for
                System.out.println("No match found for " + word + " because " + letters + " has no appropriate branch");
                return null;
            }
        }
    }
    
    /** Iterates through all words in the map in no particular order.
     * If a node is inserted during the iteration, behavior is not defined.
     */
    private class WCTIterator implements Iterator<CharSequence>{
        
        Stack<Node> node_tracker = new Stack<>();
        Stack<Iterator<Node>> index_tracker = new Stack<>();
        
        WCTIterator( WordCountingTrie trie ){
            node_tracker.push(trie.root);
            index_tracker.push(trie.root.branches.iterator());
            if (trie.root.wordcount == 0){
                advance();
            }
        }
        
        /** Advances the iterator to the next word in the map.
         */
        private void advance(){
            assert(!node_tracker.isEmpty());
            do {
                /* Invariants:
                 * node_tracker contains all nodes associated with the string we most recently returned.
                 * index_tracker contains all iterators associated with the nodes in node_tracker.
                 * 
                 * 
                 */
                assert(node_tracker.size() == index_tracker.size());
                Iterator<Node> current_iter = index_tracker.peek();
                if (current_iter.hasNext()){
                    // advance to the next branch
                    Node current = current_iter.next();
                    node_tracker.push(current);
                    index_tracker.push(current.branches.iterator());
                } else {
                    // leaf - return to the previous level, and continue until one is found that still has branches to iterate through.
                    while (!index_tracker.isEmpty() && !index_tracker.peek().hasNext()){
                        node_tracker.pop();
                        index_tracker.pop();
                    }
                }
                
                // Loop skips removed nodes (wordcount == 0) by advancing until the next valid node or the end is reached.
            } while (!node_tracker.isEmpty() && (node_tracker.peek().wordcount == 0));
        }
        
        @Override
        public boolean hasNext(){
            return !node_tracker.empty();
        }
        
        
        @Override
        public CharSequence next(){
            if (!hasNext()){
                throw new NoSuchElementException("All elements of the trie have been iterated through");
            }
            StringBuilder builder = new StringBuilder();
            for (Node node : node_tracker){
                builder.append(node.letters);
            }
            advance();
            return builder.toString();
        }
    }
}
