package com.company;

public class Corner_Cubie extends Cubie{

    public Corner_Cubie(String top, String bottom, String front, String back, String left, String right, int id, int[] coord){
        this.top = top;
        this.bottom = bottom;
        this.front = front;
        this.back = back;
        this.left = left;
        this.right = right;
        this.correct_coordinate = coord;

        update_side_color_array();

        assign_guide_colors();
        this.id = id;
    } // Constructor
}
