package com.company;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Solver {
    public Cube cube;
    public  PriorityQueue<Open_Node> all_open_nodes = new PriorityQueue<Open_Node>();
    public  ArrayList<Open_Node> all_nodes = new ArrayList<Open_Node>();

    public Solver(Cube cube_to_solve){
        cube = cube_to_solve;
    }

    public void IDA_Star(){
        for (int depth = 1; depth <= 20; depth++){
            System.out.println("Depth: " + depth);
            if (cube.is_solved()) {
                return;
            } else {
                //move_list = new String[depth];
                IDA_step(new Open_Node(20, new String[]{}), depth);
            }
        }
        System.out.println("Couldn't find a solution");
    }

    public String[] IDA_step(Open_Node node, int max_depth){
        String[] move_list = {};
        if (node.get_path().length < max_depth){
            make_new_nodes(node);
            Open_Node best_node = all_open_nodes.poll();
            assert best_node != null;
            move_list = best_node.get_path();
            // cube_move(best_node.get_path()[best_node.get_path().length - 1]);
            if (all_nodes.size() > Math.pow(18, 6)) {
                WriteToFile.write_to_file(all_nodes);
                return best_node.get_path();
            }
            if (!cube.is_solved()){
                cube.create_cube();
                cube.go_to_path(cube.scrambled_path);
                cube.go_to_path(move_list);
                return IDA_step(best_node, max_depth);
            }
        }
        return move_list;
    }

    public  Open_Node[] make_new_nodes(Open_Node node){
        Open_Node[] new_open_nodes = new Open_Node[18];
        int counter = 0;
        for (String move : cube.possible_moves){
            String[] new_path = new String[node.get_path().length + 1];
            duplicate_array(node.get_path(), new_path);
            // System.out.println("Move: " + move);
            cube.move(move);
            new_path[new_path.length - 1] = move;
            Open_Node new_node = new Open_Node(fitness(new_path), new_path);
            all_nodes.add(new_node);
            all_open_nodes.offer(new_node);
            cube.iterate_back(move);
            new_open_nodes[counter] = new_node;
            counter++;
        }
        return new_open_nodes;
    }

    public void duplicate_array(String[] old_array, String[] new_array){
        for (int index = 0; index < old_array.length; index++){
            new_array[index] = old_array[index];
        }
    }

    public  void extend_node(Open_Node node, int num) {
        Open_Node[] generation;
        generation = make_new_nodes(node);
        for (Open_Node new_node :generation) {
            make_new_nodes(new_node);
            if(num < 3) {
                extend_node(new_node, num);
            }

        }

    }

    public  double fitness(String[] path){
        double fitness_position = 10 - ((double) cube.amount_on_correct_place() / 27) * 10;
        double fitness_orientation = 10 - ((double) cube.amount_correct_orientation() / 27) * 10;
        int g = path.length;
        double h = fitness_orientation + fitness_position;
        double f = g + h;
        return f;
    }


}
