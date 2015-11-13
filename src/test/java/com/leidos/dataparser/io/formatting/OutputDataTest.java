package com.leidos.dataparser.io.formatting;

import junit.framework.TestCase;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by rushk1 on 6/2/2015.
 */
public class OutputDataTest extends TestCase {

    class TestData implements OutputData {
        @Output
        protected String val1 = "Hello ";
        private String val2 = "Goodbyte ";
        @Output
        private String val3 = "world!";
    }

    public void testGetParsedValues() throws Exception {
        OutputData t = new TestData();
        List<Field> data = t.getParsedValues();
    }
}