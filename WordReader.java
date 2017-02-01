import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;


/** Reads in text similar to BufferedReader, but one word at a time instead of one line.
 */
public class WordReader extends Reader {

    static final int BUFFER_SIZE = 4 * 1024 * 1024; // 4KB

    Reader source;

    /** Closes the stream and releases any system resources associated with it.
     */
     @Override
    public void close() throws IOException{
        // TODO remove buffer
        source.close();
    }

    /** Marks the present position in the stream.
     */
    @Override
    public void mark(int readAheadLimit){
        throw new UnsupportedOperationException("Not Implemented"); // TODO
    }

    /** Tells whether this stream supports the mark() operation.
     */
    @Override
    public boolean markSupported(){
        return true;
    }

    /** Reads a single character.
     */
    @Override
    public int read(){
        throw new UnsupportedOperationException("Not Implemented"); // TODO
    }

    /** Reads characters into an array.
     * @param cbuf
     * @return 
     */
    @Override
    public int read(char[] cbuf){
        throw new UnsupportedOperationException("Not Implemented"); // TODO
    }

    /** Reads characters into a portion of an array.
     * @param cbuf
     * @param off
     * @param len
     * @return 
     */
    @Override
    public int read(char[] cbuf, int off, int len){
        throw new UnsupportedOperationException("Not Implemented"); // TODO
    }
    
    /** Attempts to read characters into the specified character buffer.
     * 
     * @param target
     * @return 
     */
    @Override
    public int read(CharBuffer target){
        throw new UnsupportedOperationException("Not Implemented"); // TODO
    }

    /** Tells whether this stream is ready to be read.
     * @return 
     */
    @Override
    public boolean ready(){
        throw new UnsupportedOperationException("Not Implemented"); // TODO
    }

    /** Resets the stream.
     */
    @Override
    public void reset(){
        throw new UnsupportedOperationException("Not Implemented"); // TODO
    }

    /** Skips characters.
     * @param n
     * @return 
     */
    @Override
    public long skip(long n){
        throw new UnsupportedOperationException("Not Implemented"); // TODO
    }

}
