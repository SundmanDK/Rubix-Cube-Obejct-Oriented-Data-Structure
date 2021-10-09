package com.company;

public class Cube {
    String top;
    String bottom;
    String front;
    String back;
    String left;
    String right;

    int id;

    String temp;

    public Cube(String newtop, String newbottom, String newfront, String newback, String newleft, String newright, int newID){
        top = newtop;
        bottom = newbottom;
        front = newfront;
        back = newback;
        left = newleft;
        right = newright;
        id = newID;
    } // Constructor

    public Cube RLi(){
        temp = back;
        back = top;
        top = front;
        front = bottom;
        bottom = temp;
        return this;
    }

    public Cube RiL(){
        temp = front;
        front = top;
        top = back;
        back = bottom;
        bottom = temp;
        return this;
    }

    public Cube BiF(){
        temp = right;
        right = top;
        top = left;
        left = bottom;
        bottom = temp;
        return this;
    }

    public Cube BFi(){
        temp = top;
        top = right;
        right = bottom;
        bottom = left;
        left = temp;
        return this;
    }

    public Cube UDi(){
        temp = front;
        front = right;
        right = back;
        back = left;
        left = temp;
        return this;
    }

    public Cube UiD(){
        temp = back;
        back = right;
        right = front;
        front = left;
        left = temp;
        return this;
    }

}
