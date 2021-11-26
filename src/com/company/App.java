package com.company;

import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class App {
    Cube cube;
    Solver solver;
    public App(){
        Scanner sc = new Scanner(System.in);
        String command;
        boolean running = true;
        boolean automatic = false;
        byte[] solution_byte;
        long start_time;
        long stop_time;
        long time_to_solve;
        cube = new Cube();
        solver = new Solver(cube);
        /*
        System.out.println();
        cube.show();
        System.out.println();
        cube.show_indexes();
        System.out.println();
        cube.show_id();
        System.out.println();
        */
        while (running) {
            System.out.println("-------------------------------------------------------------------------------------");
            cube.show();
            if (automatic) {
                start_time = System.currentTimeMillis();
                System.out.println("before solving");
                runGC();

                System.out.println("Solution moves:");
                solution_byte = solver.algorithm(20);
                stop_time = System.currentTimeMillis();
                time_to_solve = stop_time - start_time;
                System.out.println("time to compute: " + time_to_solve + "ms or " + time_to_solve/1000 + " seconds" );
                String[] solution_string = new String[solution_byte.length];
                for (int i = 0; i < solution_byte.length; i++) {
                    solution_string[i] = cube.byte_to_string(solution_byte[i]);
                }
                System.out.println(Arrays.toString(solution_string));
                System.out.println("after solver");
                runGC();
                System.out.println(solver.all_nodes.size());
                cube.scrambled_path = new String[0];
                automatic = false;
            } else {
                System.out.print("Write a command: ");
                command = sc.nextLine();
                if (string_in_array(Cube.possible_moves, command)) {

                    cube.move(command);
                    cube.add_to_scrambled_path(command);
                    System.out.println("is it solved: " + cube.is_solved());

                } else if (command.trim().toLowerCase(Locale.ROOT).equals("stop")) {

                    running = false;
                    System.out.println("Loop ended");

                } else if (command.trim().toLowerCase(Locale.ROOT).equals("solve")) {
                    automatic = true;
                } else if (command.trim().toLowerCase(Locale.ROOT).equals("scramble")){

                    System.out.println("how many scrambles? ");
                    int scrable_amount = Integer.parseInt(sc.nextLine());
                    System.out.println("scramble moves:");
                    cube.scramble(scrable_amount);
                    System.out.println(Arrays.toString(cube.scrambled_path)); //cube.scramble(scrable_amount)));

                } else if (command.trim().toLowerCase(Locale.ROOT).equals("help")){

                    System.out.println("List of commands:");
                    System.out.println("stop, help, scramble, solve, R, Ri, R180, L, Li, L180, F, Fi, F180, B, Bi, B180, U, Ui, U180, D, Di, D180");

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
