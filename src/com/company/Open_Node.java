package com.company;

public class Open_Node implements Comparable<Open_Node>{
    double fitness;
    String[] path;

    public Open_Node(double fitness, String[] path){
        this.fitness = fitness;
        this.path = path;
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

    public void set_path(){

    }

    public String[] get_path(){
        return path;
    }


}
