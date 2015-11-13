package com.leidos.dataparser.data.csv;

import com.leidos.dataparser.io.formatting.OutputData;
import com.leidos.dataparser.io.formatting.Output;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rushk1 on 6/22/2015.
 */
public class CSVBuilderTest {

    static class A implements OutputData {
        @Output("TEST")
        private String a1 = "a";
        @Output
        public int a2 = 1;

        public float a3 = 3.14f;

        @Output("MAP")
        public Map<String, String> hash = new HashMap<>();

        public A() {
            hash.put("TESTING IT", "IT WORKED");
        }

        //@ParserOutput
        public B nested = new B();
    }

    static class B implements OutputData {
        @Output
        public String b1 = "b";
        @Output
        public double b2 = 3.14;

        @Output
        public String a1 ="b";

        @Output
        public int[] b3 = {1, 2, 3, 4, 5, 37};

        @Output
        public ArrayList<String> test = new ArrayList<>();

        public B() {
            test.add("Hello ");
            test.add("World!");
        }
    }

    public static void main(String[] args) {
        A a = new CSVBuilderTest.A();
        B b = new CSVBuilderTest.B();

        CSVBuilder builder = new CSVBuilder();
        builder.formatOutputData("A", a);
        builder.formatOutputData("B", b);

        byte[] dest = {(byte) 254, (byte) 254, (byte) 254,  (byte) 254};
        byte[] src = {(byte) 192, (byte) 168, (byte) 10, (byte) 1};

        System.out.println(builder.toString());

        CSVBuilder builder1 = new CSVBuilder();
        //builder1.formatOutputData("", eth);

        System.out.println(builder1);
    }
}