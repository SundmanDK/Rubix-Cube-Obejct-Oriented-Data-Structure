package com.company;

public class Cubie {
    String top;
    String bottom;
    String front;
    String back;
    String left;
    String right;

    int id;

    String temp;

    public Cubie(String newtop, String newbottom, String newfront, String newback, String newleft, String newright, int newID){
        top = newtop;
        bottom = newbottom;
        front = newfront;
        back = newback;
        left = newleft;
        right = newright;
        id = newID;
    } // Constructor

    public Cubie LiR(){
        temp = back;
        back = top;
        top = front;
        front = bottom;
        bottom = temp;
        return this;
    }

    public Cubie LRi(){
        temp = front;
        front = top;
        top = back;
        back = bottom;
        bottom = temp;
        return this;
    }

    public Cubie LR180(){
        temp = top;
        top = bottom;
        bottom = temp;
        temp = front;
        front = back;
        back = temp;
        return this;
    }

    public Cubie BiF(){
        temp = right;
        right = top;
        top = left;
        left = bottom;
        bottom = temp;
        return this;
    }

    public Cubie BFi(){
        temp = top;
        top = right;
        right = bottom;
        bottom = left;
        left = temp;
        return this;
    }

    public Cubie BF180(){
        temp = top;
        top = bottom;
        bottom = temp;
        temp = left;
        left = right;
        right = temp;
        return this;
    }

    public Cubie DiU(){
        temp = front;
        front = right;
        right = back;
        back = left;
        left = temp;
        return this;
    }

    public Cubie DUi(){
        temp = back;
        back = right;
        right = front;
        front = left;
        left = temp;
        return this;
    }

    public Cubie DU180(){
        temp = front;
        front = back;
        back = temp;
        temp = left;
        left = right;
        right = temp;
        return this;
    }
}
