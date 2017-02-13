package com.example.wordcount;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;


/** Creates a map indicating how many instances of a word were found in some text.
 * @author bshaw
 */
public class WordCount {

    /** Prints the number of times each word appears to stdout.
     * @param lookup Mapping of each word to the number of appearances.
     */
    static void printWordCounts(Map<String, Integer> lookup){
        for (String keyword : lookup.keySet()){
            assert (keyword != null);
            Integer count = lookup.get(keyword);
            assert (count != null);
            System.out.println(count.toString() + " " + keyword);
        }
    }
    
    
    static void printTrie(WordCountingTrie trie){
        for (CharSequence keyword : trie){
            assert (keyword != null);
            int count = trie.countOf(keyword);
            System.out.println(Integer.toString(count) + " " + keyword);
        }
        System.out.println("----------------");
    }
    

    /** Produces a word count using Java's built-in data structures.Does stuff.
     * The input file must have each word separated by a newline.
     * @param input
     * @return Mapping of each word to the number of appearances.
     * @throws IOException 
     */
    Map<String, Integer> runWithBuiltins(InputStreamReader input) throws IOException{
        
        // HashMap is fast and known to work but provides no ordering guarantees
        final int HASH_INITIAL_CAPACITY = 1024;
        Map<String, Integer> lookup = new HashMap<String, Integer>(HASH_INITIAL_CAPACITY);
        
        BufferedReader input_reader = new BufferedReader(input);
        String line = input_reader.readLine();
        while (line != null){
            StringTokenizer tokenizer = new StringTokenizer(line, " \t\n\r\f,;:.?!");
            while (tokenizer.hasMoreTokens()){
                String word = tokenizer.nextToken();
                String key = word.toLowerCase();
                Integer prev = lookup.putIfAbsent(key, new Integer(1));
                if ((prev != null) && (prev.intValue() < Integer.MAX_VALUE)){
                    lookup.put(key, new Integer(prev.intValue() + 1));
                }
            }
            line = input_reader.readLine();
        }
        return lookup;
    }
    
    
    /** Produces a word count using Java's built-in data structures.Does stuff.
     * The input file must have each word separated by a newline.
     * @param input
     * @return Mapping of each word to the number of appearances.
     * @throws IOException 
     */
    Map<String, Integer> runOptimized(InputStreamReader input) throws IOException{
        
        // TODO: write a custom data structure more tailored to our purpose
        WordCountingTrie trie = new WordCountingTrie();
        
        BufferedReader input_reader = new BufferedReader(input);
        String line = input_reader.readLine();
        while (line != null){
            StringTokenizer tokenizer = new StringTokenizer(line);
            while (tokenizer.hasMoreTokens()){
                String word = tokenizer.nextToken();
                String key = word.toLowerCase();
                // NOTE: defined behavior is to increment the count whenever a duplicate key is added
                trie.add(key);
            }
            line = input_reader.readLine();
        }
                
        return new WordCountMap<>(trie);
    }


    /** Entry point into WordCount program.
     * Example command lines:
     * cat Blob.txt | java WordCount
     * java WordCount Blob.txt
     */
    public static void main(String[] arguments){
        try {
            InputStreamReader reader;
            switch (arguments.length) {
                case 0:
                    reader = new InputStreamReader(System.in, StandardCharsets.US_ASCII);
                    break;
                case 1:
                    reader = new FileReader(arguments[0]);
                    break;
                default:
                    throw new RuntimeException("Usage: WordCount (filename - omit for stdin)" + Integer.toString(arguments.length));
            }
            
            /* BEGIN DEBUG */
            
            WordCountingTrie trie = new WordCountingTrie();
            printTrie(trie);
            trie.add("Lorem");
            printTrie(trie);
            trie.add("Lipstick");
            printTrie(trie);
            trie.add("Pig");
            printTrie(trie);
            
            //new WordCount().runOptimized(reader);
            
            
            /* END DEBUG */
            
            Map<String, Integer> lookup = new WordCount().runWithBuiltins(reader);
            printWordCounts( lookup );
            
        } catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        } catch (Throwable t){
            t.printStackTrace();
            System.exit(-1);
        }
    }
}
