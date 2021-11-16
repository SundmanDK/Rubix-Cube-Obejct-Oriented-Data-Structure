package com.company;

public class Open_Node {
    double fitness;
    String[] path;

    public Open_Node(double fitness, String[] path){
        this.fitness = fitness;
        this.path = path;
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
