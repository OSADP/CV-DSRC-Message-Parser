package com.leidos.dataparser.executor;

import com.leidos.dataparser.appcommon.MessageType;
import junit.framework.TestCase;

import java.io.File;

/**
 * Created by rushk1 on 10/16/2015.
 */
public class JobCreatorTest extends TestCase {

    public void testGetOutputFile() throws Exception {
        File input = new File("test_input_file1.pcap");
        JobCreator jobCreator = new JobCreator();

        File f1 = jobCreator.getOutputFile(input, new File("C:\\Output\\"), MessageType.BSM);
        File f2 = jobCreator.getOutputFile(input, new File("C:\\Output\\"), MessageType.FHWA_SPAT);
        File f3 = jobCreator.getOutputFile(input, new File("C:\\Output\\"), MessageType.MAP);

        assertEquals("C:\\Output\\test_input_file1_bsm.csv", f1.getAbsolutePath());
        assertEquals("C:\\Output\\test_input_file1_fhwaSpat.csv", f2.getAbsolutePath());
        assertEquals("C:\\Output\\test_input_file1_map.csv", f3.getAbsolutePath());
    }

    public void testIncrementNumericalSuffix() throws Exception {
        File input = new File("test_input_file1.csv");
        JobCreator jobCreator = new JobCreator();

        File f1 = jobCreator.incrementNumericalSuffix(input);

        assertEquals("test_input_file1 (1).csv", f1.getName());

        File f2 = jobCreator.incrementNumericalSuffix(f1);

        assertEquals("test_input_file1 (2).csv", f2.getName());

        File f3 = new File("C:/test dir (1)/test file_bsm (12).csv");

        File f4 = jobCreator.incrementNumericalSuffix(f3);

        assertEquals("C:\\test dir (1)\\test file_bsm (13).csv", f4.getAbsolutePath());
    }
}