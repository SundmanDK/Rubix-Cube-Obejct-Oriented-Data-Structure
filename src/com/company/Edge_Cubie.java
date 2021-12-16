package com.company;

public class Edge_Cubie extends Cubie {

    public Edge_Cubie(String top, String bottom, String front, String back, String left, String right, int id, int[] coord) {
        this.top = top;
        this.bottom = bottom;
        this.front = front;
        this.back = back;
        this.left = left;
        this.right = right;
        this.correct_coordinate = coord;    //used when evaluating if position is correct

        update_side_color_array();          //used when dealing with orientation
        this.id = id;
    }
}