package com.company;

import java.util.Arrays;
import java.util.Scanner;

public class App {
    Cube cube;
    Solver solver;
    public App(){
        Scanner in = new Scanner(System.in);
        String command;
        boolean running = true;
        boolean automatic = true;
        //Actual Rubix Cube
        cube = new Cube();
        /*
        System.out.println();
        cube.show();
        System.out.println();
        cube.show_indexes();
        System.out.println();
        cube.show_id();
        System.out.println();
        */
        System.out.println("scramble moves:");
        System.out.println(Arrays.toString(cube.scramble(5)));
        solver = new Solver(cube);
        System.out.println("-------------------------------------------------------------------------------------");
        while (running) {
            System.out.println();
            cube.show();
            System.out.println();

            if (automatic) {
                System.out.println();
                System.out.println("solution moves:");
                System.out.println(Arrays.toString(solver.IDA_step(new Open_Node(20, new String[]{}) ,20)));
                runGC();
                automatic = false;
            }else {
                System.out.println();
                System.out.println("List of commands:");
                System.out.println("Stop, R, Ri, R180, L, Li, L180, F, Fi, F180, B, Bi, B180, U, Ui, U180, D, Di, D180");
                System.out.println();
                System.out.print("Write a command: ");
                command = in.nextLine();
                if (string_in_array(Cube.possible_moves, command)) {
                    cube.move(command);
                    System.out.println("is it solved: " + cube.is_solved());
                } else if (command.equals("Stop")) {
                    running = false;
                    System.out.println("Loop ended");
                } else {
                    System.out.println("Not a valid command!");
                }


            }
        }

    }
    public void runGC() {
        Runtime runtime = Runtime.getRuntime();
        long memoryMax = runtime.maxMemory();
        long memoryUsed = runtime.totalMemory() - runtime.freeMemory();
        double memoryUsedPercent = (memoryUsed * 100.0) / memoryMax;
        System.out.println("memory Used: " + memoryUsed/1000000 + "mb");
        if (memoryUsedPercent > 90.0)
            System.gc();
    }
    private boolean string_in_array(String[] array, String word) {
        for (String element : array) {
            if (element.equals(word)) {
                return true;
            }
        }
        return false;
    }



}
