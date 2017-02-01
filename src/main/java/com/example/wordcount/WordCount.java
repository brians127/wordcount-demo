
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


public class WordCount {

    static final int HASH_INITIAL_CAPACITY = 1024; // TODO have an intelligent way of tuning this?

    InputStream input = System.in;
    // TODO use a custom data structure to demonstrate prowess - starting with a built-in to ensure correctness.
    Map<String, Integer> lookup = new HashMap<String, Integer>(HASH_INITIAL_CAPACITY);


    public void run(String[] args) throws IOException{
        /**** Argument parsing ****/
        // TODO parse -f
        // TODO argument for specifying charset? Read from input text?

        // TODO initialize(args);

        // TODO use WordReader instead of BufferedReader - this removes need to assume each word has its own line
        BufferedReader input_reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.US_ASCII));

        String word = input_reader.readLine();
        while (word != null){
            // TODO can I optimize away the Integer allocations? Maybe make a mutable int wrapper...?
            Integer prev = lookup.putIfAbsent(word, new Integer(1));
            if ((prev != null) && (prev.intValue() < Integer.MAX_VALUE)){
                lookup.put(word, new Integer(prev.intValue() + 1));
            }
            word = input_reader.readLine();
        }

        for (String keyword : lookup.keySet()){
            assert (keyword != null);
            Integer count = lookup.get(keyword);
            assert (count != null);
            System.out.println(keyword + ": " + count.toString());
        }
    }


    /** Entry point into WordCount program.
     * Example command lines:
     * cat Blob.txt | java WordCount
     * java WordCount -f Blob.txt
     */
    public static void main(String[] arguments){
        try {
            new WordCount().run(arguments);
        } catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        } catch (Throwable t){
            t.printStackTrace();
            System.exit(-1);
        }
    }
}
