package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Solver {
    public Cube cube;
    public  PriorityQueue<Open_Node> all_open_nodes = new PriorityQueue<Open_Node>();
    public  ArrayList<Open_Node> all_nodes = new ArrayList<Open_Node>();

    public Solver(Cube cube_to_solve) {
        cube = cube_to_solve;
    }

    public byte[] algorithm(int max_depth){
        byte[] move_list = {};
        Open_Node best_node = new Open_Node(20, new byte[]{});
        make_new_nodes(best_node);

        byte[] byte_scramble_path = new byte[cube.scrambled_path.length];
        for (int i = 0; i < cube.scrambled_path.length; i++) {
            byte_scramble_path[i] = cube.string_to_byte(cube.scrambled_path[i]);
        }

        while (best_node.get_path().length < max_depth && !cube.is_solved()){
            best_node = all_open_nodes.poll();
            assert best_node != null;
            move_list = best_node.get_path();

            if (all_nodes.size()%10000 == 0){
                System.out.println("Amount of nodes opend: " + all_nodes.size());
            }

            cube.create_cube();
            cube.go_to_path(byte_scramble_path);
            cube.go_to_path(move_list);
            make_new_nodes(best_node);
            WriteToFile.write_to_file(all_nodes);
        }
        return move_list;
    }

    public  void make_new_nodes(Open_Node node){
        for (byte move : cube.byte_moves){
            byte[] new_path = new byte[node.get_path().length + 1];
            duplicate_array(node.get_path(), new_path);
            cube.move(cube.byte_to_string(move));
            new_path[new_path.length - 1] = move;

            Open_Node new_node = new Open_Node(fitness(new_path), new_path);
            all_nodes.add(new_node);
            all_open_nodes.offer(new_node);
            cube.iterate_back(cube.byte_to_string(move));
        }
    }

    public void duplicate_array(byte[] old_array, byte[] new_array){
        for (int index = 0; index < old_array.length; index++){
            new_array[index] = old_array[index];
        }
    }

    public  double fitness(byte[] path){
        int g = path.length;
        double h = moves_until_all_cubies_are_correct() /4;

        double f = g + h;
        return f;
    }

    public double moves_until_cubie_is_correct(int z, int y, int x){
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


}
