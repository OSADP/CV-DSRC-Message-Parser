package com.leidos.dataparser.main;

import com.leidos.dataparser.appcommon.Constants;
import com.leidos.dataparser.ui.MainUiForm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.swing.*;

/**
 * Execution entry point for Data Processing Application.
 */
public class DataProcessingApplication {
    private ApplicationContext context;

    private static final String APPLICATION_CONTEXT_FILE = "DataProcessorContext.xml";
    private static Logger log = LogManager.getLogger();

    public DataProcessingApplication(ApplicationContext context) {
        this.context = context;
    }

    /**
     * Statically setup the environment for the DPA.
     */
    public static void initialize() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    /**
     * Actual entry point for application execution, sets up values and initializes the Spring IOC container
     * @param args N/A
     */
    public static void main(String[] args) {
        // Load the com.leidos.dataparser.main UI window, need to do it before bean instantiation
        log.trace(Constants.APPLICATION_NAME + " Start Up...");

        initialize();
        ApplicationContext context = new ClassPathXmlApplicationContext(APPLICATION_CONTEXT_FILE); // Lexically shadows
        DataProcessingApplication dpa = new DataProcessingApplication(context);

        // Run the DPA application
        log.debug("Application execution beginning.");
        dpa.run();
    }

    /**
     * Logical entry point for application behavior
     */
    public void run() {
        log.debug("Opening UI...");
        JFrame frame = new JFrame(Constants.APPLICATION_NAME);
        MainUiForm ui = (MainUiForm) context.getBean("mainWindowForm");
        frame.setJMenuBar(ui.getMenuBar());
        frame.setContentPane(ui.getContent());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }
}
