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

        side_color[0] = top;
        side_color[1] = bottom;
        side_color[2] = front;
        side_color[3] = back;
        side_color[4] = left;
        side_color[5] = right;

        assign_guide_colors();
        this.id = id;
    } // Constructor
}
