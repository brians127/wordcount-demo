package com.example.wordcount;

import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test user-level WordCount functionality.
 */
public class WordCountTest {
    
    static final String TEXTFILE_WORD_PER_LINE_SHORT = "wordperline.txt";
    
    private InputStreamReader getText(String filename){
        return new InputStreamReader(getClass().getClassLoader().getResourceAsStream(filename), StandardCharsets.US_ASCII);
    }
    
    // TODO: test larger files with different content and no 'one word per line' quirk.
    // TODO: test degenerate cases - for example an empty file or one with big blocks of whitespace
    
    @Test
    public void sanityTestBuiltins() {
        try {
            new WordCount().runWithBuiltins(getText(TEXTFILE_WORD_PER_LINE_SHORT));
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    
    @Test
    public void sanityTestOptimized() {
        try {
            new WordCount().runOptimized(getText(TEXTFILE_WORD_PER_LINE_SHORT));
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}