package com.company;

public class Open_Node implements Comparable<Open_Node>{
    //Implements Comparable is necessary to order the PriorityQueue in the Solver class.
    double fitness;
    byte[] byte_path;
    //String[] path;

    public Open_Node(double fitness, byte[] path){
        //Each node stores a fitness and the path from the scrambled state to the state of the that node.
        this.fitness = fitness;
        this.byte_path = path;
    }

    @Override
    public int compareTo(Open_Node o) {
        //orders lowest first
        if (this.fitness<o.get_fitnes())
            return -1;
        if (this.fitness>o.get_fitnes())
            return 1;
        return 0;
    }

    public double get_fitnes(){
        return fitness;
    }       //outputs fitness.

    public byte[] get_path(){
        return byte_path;
    }       //outputs path.
}
