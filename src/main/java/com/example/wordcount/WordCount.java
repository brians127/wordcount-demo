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
    
    
    /** Subroutine to handle incrementing the word count via the map API.
     * @param word Word that has been found
     * @param lookup Map storing the number of occurrences of each word found.
     */
    private static void wordFound(String word, Map<String, Integer> lookup){
        // TODO can I optimize away the Integer allocations? Maybe make a mutable int wrapper...?
        Integer prev = lookup.putIfAbsent(word, new Integer(1));
        if ((prev != null) && (prev.intValue() < Integer.MAX_VALUE)){
            lookup.put(word, new Integer(prev.intValue() + 1));
        }
    }
    

    /** Prints the number of times each word appears to stdout.
     * @param lookup Mapping of each word to the number of appearances.
     */
    static void printWordCounts(Map<String, Integer> lookup){
        for (String keyword : lookup.keySet()){
            assert (keyword != null);
            Integer count = lookup.get(keyword);
            assert (count != null);
            System.out.println(keyword + ": " + count.toString());
        }
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
            StringTokenizer tokenizer = new StringTokenizer(line);
            while (tokenizer.hasMoreTokens()){
                String word = tokenizer.nextToken();
                wordFound(word, lookup);
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
        final int HASH_INITIAL_CAPACITY = 1024;
        Map<String, Integer> lookup = new HashMap<String, Integer>(HASH_INITIAL_CAPACITY);
        
        
        // FIXME: Using BufferedReader requires each word to be on its own line
        BufferedReader input_reader = new BufferedReader(input);

        String word = input_reader.readLine();
        while (word != null){
            wordFound(word, lookup);
            word = input_reader.readLine();
        }
        return lookup;
    }


    /** Entry point into WordCount program.
     * Example command lines:
     * cat Blob.txt | java WordCount
     * java WordCount Blob.txt
     */
    public static void main(String[] arguments){
        try {
            InputStreamReader reader;
            if (arguments.length == 0){
                reader = new InputStreamReader(System.in, StandardCharsets.US_ASCII);
            } else if (arguments.length == 1){
                reader = new FileReader(arguments[0]);
            } else {
                throw new RuntimeException("Usage: WordCount (filename - omit for stdin)" + Integer.toString(arguments.length));
            }
            
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
