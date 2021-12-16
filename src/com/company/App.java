package com.company;


import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class App {
    Cube cube;
    Solver solver;
    public App(){
        //User interface.
        Scanner sc = new Scanner(System.in);
        String command;
        boolean running = true;
        boolean A_star = false;
        byte[] solution_byte;
        long start_time;
        long stop_time;
        long time_to_solve;
        cube = new Cube();
        solver = new Solver(cube);

        while (running) {   //Main loop.
            System.out.println("-------------------------------------------------------------------------------------");
            cube.show();
            if (A_star) {   //Manual call to solve the cube.
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
            } else {
                System.out.print("Write a command: ");
                command = sc.nextLine();

                if (string_in_array(Cube.possible_moves, command)) {    //Manually apply moves to cube.

                    cube.move(command);
                    cube.add_to_scrambled_path(command);
                    System.out.println("is it solved: " + cube.is_solved());

                } else if (command.trim().toLowerCase(Locale.ROOT).equals("stop")) {    //End loop.

                    running = false;
                    System.out.println("Loop ended");

                } else if (command.trim().toLowerCase(Locale.ROOT).equals("solve")) {   //Call to solve.

                    A_star = true;

                } else if (command.trim().toLowerCase(Locale.ROOT).equals("scramble")){ //Scramble the cube.

                    System.out.println("how many scrambles? ");
                    int scrable_amount = Integer.parseInt(sc.nextLine());
                    System.out.println("scramble moves:");
                    cube.scramble(scrable_amount);
                    System.out.println(Arrays.toString(cube.scrambled_path));

                } else if (command.trim().toLowerCase(Locale.ROOT).equals("help")){     //List of commands.

                    System.out.println("List of commands:");
                    System.out.println("stop, help, scramble, solve, R, Ri, R180, L, Li, L180, F, Fi, F180, B, Bi, B180, U, Ui, U180, D, Di, D180");

                } else if (command.trim().toLowerCase(Locale.ROOT).equals("new")) {     //Data collection for A*2.
                    System.out.println("scramble length, total nodes, memory used (kb), solve time (ms), solution length");
                    for (int nr_of_scrambles = 1; nr_of_scrambles <= 10; nr_of_scrambles++) {   //Which scramble complexities to try.
                        for (int run = 0; run < 50; run++) {                    //How many runs.
                            cube.scramble(nr_of_scrambles);
                            int memory_before = runGC();                        //Memory before algorithm operations.
                            start_time = System.currentTimeMillis();            //Time before algorithm operations.
                            solution_byte = solver.algorithm(20);      //Algorithm.
                            stop_time = System.currentTimeMillis();             //Time after algorithm operations.
                            int memory_after = runGC();                         //Memory before algorithm operations.
                            time_to_solve = stop_time - start_time;             //Time used.
                            int total_memory = memory_after - memory_before;    //memory used.

                            //We print the values gathered because all methods of writing to a csv did not work for us.
                            //Since we sometimes get memory errors which would then lose all the gathered data.
                            //Therefore, we print it, so we never lose data, it is inelegant but it works.
                            System.out.println(nr_of_scrambles + "," + solver.all_nodes.size() + "," + total_memory + "," + (int) time_to_solve + "," + solution_byte.length);
                            //clean up
                            cube.scrambled_path = new String[0];
                            solver.all_nodes.clear();
                            solver.all_open_nodes.clear();
                            System.gc();
                        }
                    }
                }  else if (command.trim().toLowerCase(Locale.ROOT).equals("old")) {        //Data collection for A*1.
                    //Works the same as A*2 data collection just with A*1.
                    System.out.println("scramble length, total nodes, memory used (kb), solve time (ms), solution length");
                    for (int nr_of_scrambles = 1; nr_of_scrambles <= 10; nr_of_scrambles++) {
                        for (int run = 0; run < 100; run++) {
                            cube.scramble(nr_of_scrambles);
                            int memory_before = runGC();
                            start_time = System.currentTimeMillis();
                            solution_byte = solver.algorithm2(20);
                            stop_time = System.currentTimeMillis();
                            time_to_solve = stop_time - start_time;
                            int memory_after = runGC();
                            int total_memory = memory_after - memory_before;
                            System.out.println(nr_of_scrambles + "," + solver.all_nodes.size() + "," + total_memory + "," + (int) time_to_solve + "," + solution_byte.length);
                            cube.scrambled_path = new String[0];
                            solver.all_nodes.clear();
                            solver.all_open_nodes.clear();
                            System.gc();
                        }
                    }
                }  else if (command.trim().toLowerCase(Locale.ROOT).equals("brute")) {      //Data collection for IDDFS.
                    //Works the same as A*2 data collection just with IDDFS.
                    System.out.println("scramble length, total nodes, memory used (kb), solve time (ms), solution length");
                    for (int nr_of_scrambles = 1; nr_of_scrambles <= 10; nr_of_scrambles++) {
                        for (int run = 0; run < 100; run++) {
                            cube.scramble(nr_of_scrambles);
                            int memory_before = runGC();
                            start_time = System.currentTimeMillis();
                            solution_byte = solver.brute_force();
                            stop_time = System.currentTimeMillis();
                            time_to_solve = stop_time - start_time;
                            int memory_after = runGC();
                            int total_memory = memory_after - memory_before;
                            System.out.println(nr_of_scrambles + "," + solver.amount_of_reqursions + "," + total_memory + "," + (int) time_to_solve + "," + solution_byte.length);
                            cube.scrambled_path = new String[0];
                            solver.all_nodes.clear();
                            solver.all_open_nodes.clear();
                            System.gc();
                        }
                    }
                }  else {
                        System.out.println("Not a valid command!");
                }
            }
        }
    }


    public int runGC() {
        //Checks memory use.
        //Taken from https://www.tutorialspoint.com/how-to-check-the-memory-used-by-a-program-in-java
        Runtime runtime = Runtime.getRuntime();
        long memoryUsed = runtime.totalMemory() - runtime.freeMemory();

        return (int) memoryUsed/1000;
    }

    private boolean string_in_array(String[] array, String word) {
        //checks if string is in array.
        for (String element : array) {
            if (element.equals(word)) {
                return true;
            }
        }
        return false;
    }



}
