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
         * Calling this constructor does not count as inserting "" into the trie.
         */
        Node(){
            letters = "";
            branches = new Vector<Node>();
            wordcount = 0;
        }
        
        /** Constructor for additional nodes.
         * The supplied word is inserted into the trie.
         * @param another_node Any other node - used to set up iterators
         * @param word Sequence of letters this node represents
         * @param index Starting offset within 'word'
         */
        Node( Node another_node, CharSequence word, int index ){
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
            if (nonmatch < limit){
                // need to split the node
                assert( nonmatch > 0 );
                
                // Step 1: create new node for the portion removed
                Node newnode = new Node(this, letters, nonmatch);
                // Swap newnode's empty set of branches with our existing set
                Collection<Node> tmpbranches = newnode.branches;
                newnode.branches = branches;
                branches = tmpbranches;
                
                // Step 2: adjust our node's sequence
                letters = letters.substring(0, nonmatch);

                // Step 3: create our two branches: one for the portion removed, one for the newly inserted word
                assert( branches.isEmpty() );
                branches.add(newnode);
                branches.add(new Node(this, word, nonmatch));
                return true;
                
            } else if (word_length == letters_length){
                // exact match for our word
                // that said, wordcount of 0 means it was previously removed.
                return (wordcount++ == 0);
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
                branches.add(new Node(this, word, start_index));
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
        
        private void advance(){
            assert(!node_tracker.isEmpty());
            do {
                /* 
                 * Invariants:
                 * node_tracker contains all nodes associated with the string we most recently returned.
                 * index_tracker contains all iterators associated with the nodes, except perhaps the top one.
                 *
                 */
                
                /*
                Node current = node_tracker.peek();
                assert(node_tracker.size() == index_tracker.size());
                assert(node_tracker.size() <= index_tracker.size() + 1);
                
                if (node_tracker.size() > index_tracker.size()){
                    // we have yet to explore the top node's branches
                    Iterator<Node> current_iter = node_tracker.peek().branches.iterator();
                    if (current_iter.hasNext()){
                        // search depth first
                        node_tracker.push(current_iter.next());
                        index_tracker.push(current_iter);
                        continue;
                    } else {
                        // leaf - return to the previous level
                        node_tracker.pop();
                    }
                }
                */
                
                assert(node_tracker.size() == index_tracker.size());
                Iterator<Node> current_iter = index_tracker.peek();
                if (current_iter.hasNext()){
                    // advance to the next branch
                    Node current = current_iter.next();
                    node_tracker.push(current);
                    index_tracker.push(current.branches.iterator());
                } else {
                    // leaf - return to the previous level
                    node_tracker.pop();
                    index_tracker.pop();
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
