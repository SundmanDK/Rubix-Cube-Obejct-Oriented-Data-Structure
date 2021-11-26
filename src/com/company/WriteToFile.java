package com.company;
/*
Taken from: https://www.w3schools.com/java/java_files_create.asp
 */
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
import java.util.ArrayList;
import java.util.Arrays;

public class WriteToFile {
    public static FileWriter myWriter;

    public static void write_to_file(ArrayList<Open_Node> priority_Queue) {
        try {
            FileWriter myWriter = new FileWriter("Data.txt");
            for (Open_Node node: priority_Queue) {
                myWriter.write("depth: " + node.get_path().length + ", path: " + Arrays.toString(node.get_path()) + ", fitness: " + node.get_fitnes() + "\n ");
            }
            myWriter.close();
            //System.out.println("Successfully wrote to the file.");

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
