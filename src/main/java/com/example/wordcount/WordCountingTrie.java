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

    @Override
    public Iterator<CharSequence> iterator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    int countOf(String three) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
            wordcount = 0;
        }
        
        void addWord( CharSequence word, int start_index ){
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
                
            } else if (word_length == letters_length){
                // exact match for our word
                wordcount++;
            } else {
                // string we're trying to match goes beyond our node's boundaries
                
                // Step 1: see if an existing branch has a longer subsequence of the node
                // if so, recurse into the appropriate branch
                assert( nonmatch == letters_length );
                boolean found = false;
                for (Node child : branches){
                    if (child.letters.charAt(0)== word.charAt(letters_length)){
                        // NOTE: recursion!
                        child.addWord(word, letters_length);
                        found = true;
                        break;
                    }
                }
                
                // Step 2: if no match found in existing branches, add a new one
                if (!found){
                    branches.add(new Node( word, start_index ));
                }
            }
        }   
    }
}
