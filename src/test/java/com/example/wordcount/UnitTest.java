package com.example.wordcount;

/** Base class for all unit tests.
 * This is used to ensure assertions are enabled during all tests.
 * @author bshaw
 */
public class UnitTest {
    static {
        ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);
    }
}
