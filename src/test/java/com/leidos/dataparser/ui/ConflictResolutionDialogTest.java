package com.leidos.dataparser.ui;

import junit.framework.TestCase;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Created by rushk1 on 10/16/2015.
 */
public class ConflictResolutionDialogTest extends TestCase {

    public void testDisplayUI() {
        JDialog test = null;
        JDialog frame = new JDialog(test, "Resolve File Conflict...", true);

        frame.setContentPane(new ConflictResolutionDialog(frame, new File("test"),
                new File("test_bsm"),
                new File("test_bsm (1)")).getContent());

        frame.pack();
        frame.setVisible(true);
    }

}