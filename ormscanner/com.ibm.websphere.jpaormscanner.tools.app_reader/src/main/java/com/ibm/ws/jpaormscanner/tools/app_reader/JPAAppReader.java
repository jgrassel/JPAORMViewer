package com.ibm.ws.jpaormscanner.tools.app_reader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.ibm.ws.jpaormscanner.tools.app_reader.appdata.AppData;

import javafx.application.Application;

public class JPAAppReader {
    public static void main(String[] args) throws Exception {
        if (args == null || args.length < 1) {
            usage();          
        }
        
        // Get the path to the report file from the argument.
        final Path reportFilePath = Paths.get(args[0]);
        if (!Files.exists(reportFilePath)) {
            System.err.println("The report file \"" + reportFilePath + "\" does not exist.");
            System.exit(1);
        } 
        
        AppData appData = AppData.loadAppData(reportFilePath);
        
        Application.launch(JPAAppReaderApplication.class, args);
    }
    
    private static void usage() {
        System.err.println("Usage: <path to generated report file>");
        System.exit(1);
    }
}
