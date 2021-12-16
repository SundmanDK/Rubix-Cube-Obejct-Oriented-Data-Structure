package com.company;


public class Cubie{
    String top;
    String bottom;
    String front;
    String back;
    String left;
    String right;

    String guide_color_1;
    String guide_color_2;
    String guide_color_3;

    public String[] side_color = new String[6];

    public int[] correct_coordinate;

    int id;

    String temp;

    public Cubie(){
    } // Constructor

    public void assign_guide_colors(){
        //Assigns the guide colors. Used for orientation on corner cubies.
        for (int color = 0; color < side_color.length; color++){
            if (side_color[color] != null && color < 2){
                guide_color_1 = side_color[color];
            } else if (side_color[color] != null && color < 4){
                guide_color_2 = side_color[color];
            } else if (side_color[color] != null && color < 6){
                guide_color_3 = side_color[color];
            }
        }
    }

    //Following 6 methods checks if a tile matches its guide color, i.e. does the corner cubie have correct orientation.
    public boolean correct_top(){           return side_color[0].equals(guide_color_1); }
    public boolean correct_bottom(){        return side_color[1].equals(guide_color_1); }
    public boolean left_turned_top(){       return side_color[0].equals(guide_color_2); }
    public boolean left_turned_bottom(){    return side_color[1].equals(guide_color_3); }
    public boolean right_turned_top(){      return side_color[0].equals(guide_color_3); }
    public boolean right_turned_bottom(){   return side_color[1].equals(guide_color_2); }

    public void update_side_color_array(){
        //updates the array every move
        side_color[0] = top;
        side_color[1] = bottom;
        side_color[2] = front;
        side_color[3] = back;
        side_color[4] = left;
        side_color[5] = right;
    }


    //Following 9 methods deal with turning cubies so their tiles are turn alongside themselves.
    public Cubie LiR(){
        temp = back;
        back = top;
        top = front;
        front = bottom;
        bottom = temp;
        update_side_color_array();
        return this;
    }
    public Cubie LRi(){
        temp = front;
        front = top;
        top = back;
        back = bottom;
        bottom = temp;
        update_side_color_array();
        return this;
    }
    public Cubie LR180(){
        temp = top;
        top = bottom;
        bottom = temp;
        temp = front;
        front = back;
        back = temp;
        update_side_color_array();
        return this;
    }

    public Cubie BiF(){
        temp = right;
        right = top;
        top = left;
        left = bottom;
        bottom = temp;
        update_side_color_array();
        return this;
    }
    public Cubie BFi(){
        temp = top;
        top = right;
        right = bottom;
        bottom = left;
        left = temp;
        update_side_color_array();
        return this;
    }
    public Cubie BF180(){
        temp = top;
        top = bottom;
        bottom = temp;
        temp = left;
        left = right;
        right = temp;
        update_side_color_array();
        return this;
    }

    public Cubie DiU(){
        temp = front;
        front = right;
        right = back;
        back = left;
        left = temp;
        update_side_color_array();
        return this;
    }
    public Cubie DUi(){
        temp = back;
        back = right;
        right = front;
        front = left;
        left = temp;
        update_side_color_array();
        return this;
    }
    public Cubie DU180(){
        temp = front;
        front = back;
        back = temp;
        temp = left;
        left = right;
        right = temp;
        update_side_color_array();
        return this;
    }
}
