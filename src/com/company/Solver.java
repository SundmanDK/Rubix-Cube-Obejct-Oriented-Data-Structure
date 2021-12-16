package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Solver {
    //Algorithm class.
    //Handles all 3 algorithms.
    public Cube cube;
    public  PriorityQueue<Open_Node> all_open_nodes = new PriorityQueue<Open_Node>();
    public  ArrayList<Open_Node> all_nodes = new ArrayList<Open_Node>();

    public Solver(Cube cube_to_solve) {
        cube = cube_to_solve;
    }

    public byte[] algorithm(int max_depth){ //A* 2
        //Handles main operations of the algorithm and calls to helper methods.
        byte[] move_list = {};
        Open_Node best_node = new Open_Node(20, new byte[]{});      //Arbitrary start node.
        make_new_nodes(best_node);                                        //opens first depth

        //Recives scramble path as string array and converts to byte array, necessary for correct data type.
        byte[] byte_scramble_path = new byte[cube.scrambled_path.length];
        for (int i = 0; i < cube.scrambled_path.length; i++) {
            byte_scramble_path[i] = cube.string_to_byte(cube.scrambled_path[i]);
        }

        //Main loop of algorithm
        while (best_node.get_path().length < max_depth && !cube.is_solved()){
            best_node = all_open_nodes.poll();  //Gets the top node, i.e. the one with the lowest fitness because of the way the queue is ordered.
            assert best_node != null;
            move_list = best_node.get_path();

            //Resets the cube and takes it to the state of the best node.
            cube.create_cube();
            cube.go_to_path(byte_scramble_path);
            cube.go_to_path(move_list);
            if (!cube.is_solved()) {    //If the cube is not solved, open child nodes
                make_new_nodes(best_node);
            }
        }
        return move_list;
    }

    public  void make_new_nodes(Open_Node node){
        //Opens all child nodes and assigns fitness and path for A*2
        for (byte move : cube.byte_moves){
            byte[] new_path = new byte[node.get_path().length + 1];
            expand_array(node.get_path(), new_path);
            cube.move(cube.byte_to_string(move));
            new_path[new_path.length - 1] = move;

            Open_Node new_node = new Open_Node(fitness(new_path), new_path);
            all_nodes.add(new_node);
            all_open_nodes.offer(new_node);
            cube.iterate_back(cube.byte_to_string(move));
        }
    }

    public void expand_array(byte[] old_array, byte[] new_array){
        //Takes the contents of one array and stores them in a new array.
        //Mainly used when an array is replaced with an array which is 1 element larger.
        for (int index = 0; index < old_array.length; index++){
            new_array[index] = old_array[index];
        }
    }

    public  double fitness(byte[] path){
        //Calculates the fitness of each node for A*2
        int g = path.length;
        double h = moves_until_all_cubies_are_correct() /5;

        double f = g + h;
        return f;
    }

    public double moves_until_cubie_is_correct(int z, int y, int x){
        //calculates how many moves a cubie is from being in correct placement and orientation.
        Cubie this_cubie = cube.rubix_Cube[z][y][x];

        if (!cube.is_cubie_solved(this_cubie, z, y, x)){
            if (this_cubie.getClass() == Edge_Cubie.class){
                if (cube.is_orientation_correct_edge(this_cubie, z, y, x) == 0){
                    return 1;
                } else if (Arrays.equals(this_cubie.correct_coordinate, new int[]{z, y, x}) && !(cube.is_orientation_correct_edge(this_cubie, z, y, x) == 0)){
                    return 3;
                } else {
                    return 2;
                }
            } else if (this_cubie.getClass() == Corner_Cubie.class){

                if (cube.is_orientation_correct_corner(z, this_cubie) == 0){
                    return 1;
                } else {
                    return 2;
                }
            }
        }
        return 0;
    }

    public double moves_until_all_cubies_are_correct(){
        //Sums up how many moves are needed for all cubies to be correct placement and orientation.
        double how_far_are_the_cubies_from_being_correct = 0;
        for (int z = 0; z <= cube.index_Size; z++){
            for (int y = 0; y <= cube.index_Size; y++){
                for (int x = 0; x <= cube.index_Size; x++){
                    how_far_are_the_cubies_from_being_correct += moves_until_cubie_is_correct(z, y, x);
                }
            }
        }
        return how_far_are_the_cubies_from_being_correct;
    }

    //Brute force algorithm (IDDFS)
    public int amount_of_reqursions = 0;
    public byte[] brute_force(){
        //handles iterations of depth
        byte[] move_list = new byte[0];
        for (int depth = 1; depth <= 20; depth++){
            if (cube.is_solved()) {
                return move_list;
            } else {
                move_list = new byte[depth];
                brute_step(depth, 0, move_list);
            }
        }
        return null;
    }
    public void brute_step(int max_Depth, int current_Depth, byte[] move_list){
        //searches the tree until the designated depth
        if (current_Depth < max_Depth){
            for (byte move : cube.byte_moves){
                if (cube.is_solved()){
                    return;
                } else {
                    cube.move(cube.byte_to_string(move));
                    move_list[current_Depth] = move;
                    brute_step(max_Depth, current_Depth+1, move_list);
                    if (!cube.is_solved()) {
                        cube.iterate_back(cube.byte_to_string(move));
                    }
                }
            }
        }
    }

    //A*1
    public byte[] algorithm2(int max_depth){ //A* 1
        //Works the same as A*2 but calls on a different fitness, thus separate methods are needed to call the different fitness.
        byte[] move_list = {};
        Open_Node best_node = new Open_Node(20, new byte[]{});
        make_new_nodes2(best_node);

        byte[] byte_scramble_path = new byte[cube.scrambled_path.length];
        for (int i = 0; i < cube.scrambled_path.length; i++) {
            byte_scramble_path[i] = cube.string_to_byte(cube.scrambled_path[i]);
        }

        while (best_node.get_path().length < max_depth && !cube.is_solved()){
            best_node = all_open_nodes.poll();
            assert best_node != null;
            move_list = best_node.get_path();
            cube.create_cube();
            cube.go_to_path(byte_scramble_path);
            cube.go_to_path(move_list);
            if (!cube.is_solved()) {
                make_new_nodes2(best_node);
            }
        }
        return move_list;
    }
    public  void make_new_nodes2(Open_Node node){
        //Opens nodes for A*1.
        for (byte move : cube.byte_moves){
            byte[] new_path = new byte[node.get_path().length + 1];
            expand_array(node.get_path(), new_path);
            cube.move(cube.byte_to_string(move));
            new_path[new_path.length - 1] = move;

            Open_Node new_node = new Open_Node(fitness2(new_path), new_path);
            all_nodes.add(new_node);
            all_open_nodes.offer(new_node);
            cube.iterate_back(cube.byte_to_string(move));
        }
    }
    public  double fitness2(byte[] path){
        //Fitness for A*1.
        //Fitness is calculated as how many cubies are correctly placed and oriented.
        double fitness_position = 10 - ((double) cube.amount_on_correct_place() / 20) * 10;
        double fitness_orientation = 10 - ((double) cube.amount_correct_orientation() / 20) * 10;
        int g = path.length;
        double h = fitness_orientation + fitness_position;
        double f = g + h;
        return f;
    }

}
