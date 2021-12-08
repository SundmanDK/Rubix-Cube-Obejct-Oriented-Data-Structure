package com.company;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;
import java.io.PrintWriter;

public class App {
    Cube cube;
    Solver solver;
    public App(){
        Scanner sc = new Scanner(System.in);
        String command;
        boolean running = true;
        boolean A_star = false;
        boolean brute = false;
        byte[] solution_byte;
        long start_time;
        long stop_time;
        long time_to_solve;
        cube = new Cube();
        solver = new Solver(cube);

        while (running) {
            System.out.println("-------------------------------------------------------------------------------------");
            cube.show();
            if (A_star) {
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
                A_star = false;
            } else /*if (brute) {
                start_time = System.currentTimeMillis();
                System.out.println("before solving");
                runGC();

                System.out.println("Solution moves:");
                solution_byte = solver.brute_force();
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
                brute = false;
            } else */{
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
                    System.out.println("which algorithm do you wish to use?: ");
                    String algoritm = sc.nextLine();
                    if (algoritm.trim().toLowerCase(Locale.ROOT).equals("a star")) {
                        A_star = true;
                    } else if (algoritm.trim().toLowerCase(Locale.ROOT).equals("brute")) {
                        brute = true;
                    }
                } else if (command.trim().toLowerCase(Locale.ROOT).equals("scramble")){

                    System.out.println("how many scrambles? ");
                    int scrable_amount = Integer.parseInt(sc.nextLine());
                    System.out.println("scramble moves:");
                    cube.scramble(scrable_amount);
                    System.out.println(Arrays.toString(cube.scrambled_path)); //cube.scramble(scrable_amount)));

                } else if (command.trim().toLowerCase(Locale.ROOT).equals("help")){

                    System.out.println("List of commands:");
                    System.out.println("stop, help, scramble, solve, R, Ri, R180, L, Li, L180, F, Fi, F180, B, Bi, B180, U, Ui, U180, D, Di, D180");

                } else if (command.trim().toLowerCase(Locale.ROOT).equals("new")) {
                    try {
                        PrintWriter writer = new PrintWriter("Data.txt", StandardCharsets.UTF_8);
                        writer.println("scramble length, total nodes, memory used (kb), solve time (ms), solution length");
                        System.out.println("scramble length, total nodes, memory used (kb), solve time (ms), solution length");

                        for (int nr_of_scrambles = 1; nr_of_scrambles <= 10; nr_of_scrambles++) {
                            for (int run = 0; run < 30; run++) {
                                //System.out.println("run: " + nr_of_scrambles);
                                cube.scramble(nr_of_scrambles);
                                int memory_before = runGC();
                                start_time = System.currentTimeMillis();
                                solution_byte = solver.algorithm(20);
                                stop_time = System.currentTimeMillis();
                                time_to_solve = stop_time - start_time;
                                int memory_after = runGC();
                                String[] solution_string = new String[solution_byte.length];
                                for (int i = 0; i < solution_byte.length; i++) {
                                    solution_string[i] = cube.byte_to_string(solution_byte[i]);
                                }
                                int total_memory = memory_after - memory_before;
                                System.out.println(nr_of_scrambles + "," + solver.all_nodes.size() + "," + total_memory + "," + (int) time_to_solve + "," + solution_byte.length);
                                //writer.println(nr_of_scrambles + "," + solver.all_nodes.size() + "," + total_memory + "," + (int) time_to_solve + "," + solution_byte.length);
                                cube.scrambled_path = new String[0];
                                solver.all_nodes.clear();
                                solver.all_open_nodes.clear();
                                System.gc();
                            }
                        }
                        writer.close();
                    } catch (IOException e) {
                        System.out.println("couldn't find file");
                        e.printStackTrace();
                    }
                } else if (command.trim().toLowerCase(Locale.ROOT).equals("brute")) {
                    try {
                        PrintWriter writer = new PrintWriter("Data.txt", StandardCharsets.UTF_8);
                        writer.println("scramble length, total nodes, memory used (kb), solve time (ms), solution length");
                        System.out.println("scramble length, total nodes, memory used (kb), solve time (ms), solution length");
                        for (int nr_of_scrambles = 1; nr_of_scrambles <= 10; nr_of_scrambles++) {
                            for (int run = 0; run < 30; run++) {
                                //System.out.println("run: " + nr_of_scrambles);
                                cube.scramble(nr_of_scrambles);
                                int memory_before = runGC();
                                start_time = System.currentTimeMillis();
                                solution_byte = solver.brute_force();
                                stop_time = System.currentTimeMillis();
                                int memory_after = runGC();
                                time_to_solve = stop_time - start_time;
                                String[] solution_string = new String[solution_byte.length];
                                for (int i = 0; i < solution_byte.length; i++) {
                                    solution_string[i] = cube.byte_to_string(solution_byte[i]);
                                }
                                int total_memory = memory_after - memory_before;
                                System.out.println(nr_of_scrambles + "," + solver.all_nodes.size() + "," + total_memory + "," + (int) time_to_solve + "," + solution_byte.length);
                                //writer.println(nr_of_scrambles + "," + solver.all_nodes.size() + "," + total_memory + "," + (int) time_to_solve + "," + solution_byte.length);
                                cube.scrambled_path = new String[0];
                                solver.all_nodes.clear();
                                solver.all_open_nodes.clear();
                                System.gc();
                            }
                        }
                        writer.close();
                    } catch (IOException e) {
                        System.out.println("couldn't find file");
                        e.printStackTrace();
                    }
                } else if (command.trim().toLowerCase(Locale.ROOT).equals("old")) {
                    try {
                        PrintWriter writer = new PrintWriter("Data.txt", StandardCharsets.UTF_8);
                        writer.println("scramble length, total nodes, memory used (kb), solve time (ms), solution length");
                        System.out.println("scramble length, total nodes, memory used (kb), solve time (ms), solution length");
                        for (int nr_of_scrambles = 1; nr_of_scrambles <= 10; nr_of_scrambles++) {
                            for (int run = 0; run < 30; run++) {
                                //System.out.println("run: " + nr_of_scrambles);
                                cube.scramble(nr_of_scrambles);
                                int memory_before = runGC();
                                start_time = System.currentTimeMillis();
                                solution_byte = solver.algorithm2(20);
                                stop_time = System.currentTimeMillis();
                                int memory_after = runGC();
                                time_to_solve = stop_time - start_time;
                                String[] solution_string = new String[solution_byte.length];
                                for (int i = 0; i < solution_byte.length; i++) {
                                    solution_string[i] = cube.byte_to_string(solution_byte[i]);
                                }
                                int total_memory = memory_after - memory_before;
                                System.out.println(nr_of_scrambles + "," + solver.all_nodes.size() + "," + total_memory + "," + (int) time_to_solve + "," + solution_byte.length);
                                //writer.println(nr_of_scrambles + "," + solver.all_nodes.size() + "," + total_memory + "," + (int) time_to_solve + "," + solution_byte.length);
                                cube.scrambled_path = new String[0];
                                solver.all_nodes.clear();
                                solver.all_open_nodes.clear();
                                System.gc();
                            }
                        }
                        writer.close();
                    } catch (IOException e) {
                        System.out.println("couldn't find file");
                        e.printStackTrace();
                    }
                } else {
                        System.out.println("Not a valid command!");
                }
            }
        }
    }


    public int runGC() {
        Runtime runtime = Runtime.getRuntime();
        long memoryMax = runtime.maxMemory();
        long memoryUsed = runtime.totalMemory() - runtime.freeMemory();

        return (int) memoryUsed/1000;
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
