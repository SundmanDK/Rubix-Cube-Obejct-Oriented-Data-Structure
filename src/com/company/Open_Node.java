package com.company;

public class Open_Node implements Comparable<Open_Node>{
    double fitness;
    byte[] byte_path;
    //String[] path;

    public Open_Node(double fitness, byte[] path){
        this.fitness = fitness;
        this.byte_path = path;
    }

    @Override
    public int compareTo(Open_Node o) {
        if (this.fitness<o.get_fitnes())
            return -1;
        if (this.fitness>o.get_fitnes())
            return 1;
        return 0;
    }

    public double get_fitnes(){
        return fitness;
    }

    public byte[] get_path(){
        return byte_path;
    }
}
