package com.company;
/*
Taken from: https://www.w3schools.com/java/java_files_create.asp
 */
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
import java.util.ArrayList;
import java.util.Arrays;

public class WriteToFile {
    public static FileWriter myWriter;// = new FileWriter("Data.txt");

    public static void CSV(int[] data) {

        try {
            FileWriter myWriter = new FileWriter("Data.txt");
            myWriter.write("total nodes, memory used (mb), solve time (ms), scramble length, solution length\n");
            for (int element: data) {
                // (total nodes, memory used, solve time, scramble length, solution length)
                myWriter.write(element + ", ");
            }
            myWriter.write("\n");
            myWriter.close();

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
