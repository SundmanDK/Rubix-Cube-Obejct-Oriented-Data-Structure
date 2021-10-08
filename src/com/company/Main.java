package com.company;

import java.util.Arrays;
import java.util.Locale;

public class Main {

    /*
                            Yellow  Yellow  Yellow
                            Yellow  Yellow  Yellow
                            Yellow  Yellow  Yellow
    Orange  Orange  Orange  Blue    Blue    Blue    Red     Red     Red     Green   Green   Green
    Orange  Orange  Orange  Blue    Blue    Blue    Red     Red     Red     Green   Green   Green
    Orange  Orange  Orange  Blue    Blue    Blue    Red     Red     Red     Green   Green   Green
                            White   White   White
                            White   White   White
                            White   White   White
     */
    public static Cube[][][] rubixCube = new Cube[3][3][3];
    public static Cube tempCube;

    public static void main(String[] args){
        String[] colors = {"YELLOW","BLUE","RED","GREEN","ORANGE","WHITE","BLANK"};
        Cube cube = new Cube(colors[0],colors[5],colors[1],colors[3],colors[4],colors[2],0);

        RubixCube();
        //System.out.println(Arrays.deepToString(rubixCube[2]));
        //System.out.println(Arrays.deepToString(rubixCube[1]));
        //System.out.println(Arrays.deepToString(rubixCube[0]));


        System.out.println("top:    " + cube.top);
        System.out.println("bottom: " + cube.bottom);
        System.out.println("front:  " + cube.front);
        System.out.println("back:   " + cube.back);
        System.out.println("left:   " + cube.left);
        System.out.println("right:  " + cube.right);
        cube.UDi();
        System.out.println();
        System.out.println("top:    " + cube.top);
        System.out.println("bottom: " + cube.bottom);
        System.out.println("front:  " + cube.front);
        System.out.println("back:   " + cube.back);
        System.out.println("left:   " + cube.left);
        System.out.println("right:  " + cube.right);

        System.out.println();
        VisuallizeRubixCube();
        System.out.println();
        VisualIndexes();
        Bi();
        //VisuallizeRubixCube();
        System.out.println();


    }

    public static void R(){
        tempCube = rubixCube[2][0][2].RL();
        rubixCube[2][0][2] = rubixCube[2][2][2].RL();
        rubixCube[2][2][2] = rubixCube[0][2][2].RL();
        rubixCube[0][2][2] = rubixCube[0][0][2].RL();
        rubixCube[0][0][2] = tempCube;

        tempCube = rubixCube[2][1][2].RL();
        rubixCube[2][1][2] = rubixCube[1][2][2].RL();
        rubixCube[1][2][2] = rubixCube[0][1][2].RL();
        rubixCube[0][1][2] = rubixCube[1][0][2].RL();
        rubixCube[1][0][2] = tempCube;
    }

    public static void Ri(){
        tempCube = rubixCube[2][0][2].RLi();
        rubixCube[2][0][2] = rubixCube[0][0][2].RLi();
        rubixCube[0][0][2] = rubixCube[0][2][2].RLi();
        rubixCube[0][2][2] = rubixCube[2][2][2].RLi();
        rubixCube[2][2][2] = tempCube;

        tempCube = rubixCube[2][1][2].RLi();
        rubixCube[2][1][2] = rubixCube[1][0][2].RLi();
        rubixCube[1][0][2] = rubixCube[0][1][2].RLi();
        rubixCube[0][1][2] = rubixCube[1][2][2].RLi();
        rubixCube[1][2][2] = tempCube;
    }

    public static void L(){
        tempCube = rubixCube[2][0][0].RLi();
        rubixCube[2][0][0] = rubixCube[0][0][0].RLi();
        rubixCube[0][0][0] = rubixCube[0][2][0].RLi();
        rubixCube[0][2][0] = rubixCube[2][2][0].RLi();
        rubixCube[2][2][0] = tempCube;

        tempCube = rubixCube[2][1][0].RLi();
        rubixCube[2][1][0] = rubixCube[1][0][0].RLi();
        rubixCube[1][0][0] = rubixCube[0][1][0].RLi();
        rubixCube[0][1][0] = rubixCube[1][2][0].RLi();
        rubixCube[1][2][0] = tempCube;
    }

    public static void Li(){
        tempCube = rubixCube[2][0][0].RL();
        rubixCube[2][0][0] = rubixCube[2][2][0].RL();
        rubixCube[2][2][0] = rubixCube[0][2][0].RL();
        rubixCube[0][2][0] = rubixCube[0][0][0].RL();
        rubixCube[0][0][0] = tempCube;

        tempCube = rubixCube[2][1][2].RL();
        rubixCube[2][1][0] = rubixCube[1][2][0].RL();
        rubixCube[1][2][0] = rubixCube[0][1][0].RL();
        rubixCube[0][1][0] = rubixCube[1][0][0].RL();
        rubixCube[1][0][0] = tempCube;
    }

    public static void F(){
        tempCube = rubixCube[2][2][0].BF();
        rubixCube[2][2][0] = rubixCube[0][2][0].BF();
        rubixCube[0][2][0] = rubixCube[0][2][2].BF();
        rubixCube[0][2][2] = rubixCube[2][2][2].BF();
        rubixCube[2][2][2] = tempCube;

        tempCube = rubixCube[2][2][1].BF();
        rubixCube[2][2][1] = rubixCube[1][2][0].BF();
        rubixCube[1][2][0] = rubixCube[0][2][1].BF();
        rubixCube[0][2][1] = rubixCube[1][2][2].BF();
        rubixCube[1][2][2] = tempCube;
    }


    public static void Bi(){
        tempCube = rubixCube[2][0][0].BF();
        rubixCube[2][0][0] = rubixCube[0][0][0].BF();
        rubixCube[0][0][0] = rubixCube[0][0][2].BF();
        rubixCube[0][0][2] = rubixCube[2][0][2].BF();
        rubixCube[2][0][2] = tempCube;

        tempCube = rubixCube[2][2][1].BF();
        rubixCube[2][0][1] = rubixCube[1][0][0].BF();
        rubixCube[1][0][0] = rubixCube[0][0][1].BF();
        rubixCube[0][0][1] = rubixCube[1][0][2].BF();
        rubixCube[1][0][2] = tempCube;
    }



    public static void RubixCube (){
        String yellow = "YELLOW";
        String blue = "BLUE";
        String red = "RED\t";
        String green = "Green";
        String orange = "ORANGE";
        String white = "WHITE";
        String blank = "BLANK";

        //                  Top     Bottom  Front   Back    Left    Right
        Cube YGO = new Cube(yellow, blank,  blank,  green,  orange, blank, 1);
        Cube YG  = new Cube(yellow, blank,  blank,  green,  blank,  blank, 2);
        Cube YGR = new Cube(yellow, blank,  blank,  green,  blank,  red,   3);
        Cube YO  = new Cube(yellow, blank,  blank,  blank,  orange, blank, 4);
        Cube Y   = new Cube(yellow, blank,  blank,  blank,  blank,  blank, 5);
        Cube YR  = new Cube(yellow, blank,  blank,  blank,  blank,  red,   6);
        Cube YBO = new Cube(yellow, blank,  blue,   blank,  orange, blank, 7);
        Cube YB  = new Cube(yellow, blank,  blue,   blank,  blank,  blank, 8);
        Cube YBR = new Cube(yellow, blank,  blue,   blank,  blank,  red,   9);
        Cube GO  = new Cube(blank,  blank,  blank,  green,  orange, blank, 10);
        Cube G   = new Cube(blank,  blank,  blank,  green,  blank,  blank, 11);
        Cube GR  = new Cube(blank,  blank,  blank,  green,  blank,  red,   12);
        Cube O   = new Cube(blank,  blank,  blank,  blank,  orange, blank, 13);
        Cube BL  = new Cube(blank,  blank,  blank,  blank,  blank,  blank, 14);
        Cube R   = new Cube(blank,  blank,  blank,  green,  blank,  red,   15);
        Cube BO  = new Cube(blank,  blank,  blue,   green,  orange, blank, 16);
        Cube B   = new Cube(blank,  blank,  blue,   blank,  blank,  blank, 17);
        Cube BR  = new Cube(blank,  blank,  blue,   blank,  blank,  red,   18);
        Cube WGO = new Cube(blank,  white,  blank,  green,  orange, blank, 19);
        Cube WG  = new Cube(blank,  white,  blank,  green,  blank,  blank, 20);
        Cube WGR = new Cube(blank,  white,  blank,  green,  blank,  red,   21);
        Cube WO  = new Cube(blank,  white,  blank,  blank,  orange, blank, 22);
        Cube W   = new Cube(blank,  white,  blank,  blank,  blank,  blank, 23);
        Cube WR  = new Cube(blank,  white,  blank,  blank,  blank,  red,   24);
        Cube WBO = new Cube(blank,  white,  blue,   blank,  orange, blank, 25);
        Cube WB  = new Cube(blank,  white,  blue,   blank,  blank,  blank, 26);
        Cube WBR = new Cube(blank,  white,  blue,   blank,  blank,  red,   27);

        // top layer (yellow)
        rubixCube[2][0][0] = YGO;
        rubixCube[2][0][1] = YG;
        rubixCube[2][0][2] = YGR;
        rubixCube[2][1][0] = YO;
        rubixCube[2][1][1] = Y;
        rubixCube[2][1][2] = YR;
        rubixCube[2][2][0] = YBO;
        rubixCube[2][2][1] = YB;
        rubixCube[2][2][2] = YBR;

        // middle layer
        rubixCube[1][0][0] = GO;
        rubixCube[1][0][1] = G;
        rubixCube[1][0][2] = GR;
        rubixCube[1][1][0] = O;
        rubixCube[1][1][1] = BL;
        rubixCube[1][1][2] = R;
        rubixCube[1][2][0] = BO;
        rubixCube[1][2][1] = B;
        rubixCube[1][2][2] = BR;

        // bottom layer (white)
        rubixCube[0][0][0] = WGO;
        rubixCube[0][0][1] = WG;
        rubixCube[0][0][2] = WGR;
        rubixCube[0][1][0] = WO;
        rubixCube[0][1][1] = W;
        rubixCube[0][1][2] = WR;
        rubixCube[0][2][0] = WBO;
        rubixCube[0][2][1] = WB;
        rubixCube[0][2][2] = WBR;

    }

    public static void VisuallizeRubixCube(){

        for (int outer = 0; outer <= 2; outer++){
            for (int inner = 0; inner <= 2; inner++) {
                System.out.print(rubixCube[2][outer][inner].top + "\t");
            }
            System.out.print("\n");
        }

        for (int outer = 2; outer >= 0; outer--) {
            for (int inner = 0; inner <= 2; inner++) {
                System.out.print(rubixCube[outer][2][inner].front + "\t");
            }
            for (int inner = 2; inner >= 0; inner--) {
                System.out.print(rubixCube[outer][inner][2].right + "\t");
            }
            for (int inner = 2; inner >= 0; inner--) {
                System.out.print(rubixCube[outer][0][inner].back + "\t");
            }
            for (int inner = 0; inner <= 2; inner++) {
                System.out.print(rubixCube[outer][inner][0].left + "\t");
            }
            System.out.print("\n");
        }

        for (int outer = 2; outer >= 0; outer--){
            for (int inner = 0; inner <= 2; inner++) {
                System.out.print(rubixCube[0][outer][inner].bottom + "\t");
            }
            System.out.print("\n");
        }
    }

    public static void VisualIndexes(){
        for (int outer = 0; outer <= 2; outer++){
            for (int inner = 0; inner <= 2; inner++) {
                System.out.print(2+ " " + outer+ " " + inner +"\t");
            }
            System.out.print("\n");
        }

        for (int outer = 2; outer >= 0; outer--) {
            for (int inner = 0; inner <= 2; inner++) {
                System.out.print(outer +" "+ 2 +" "+ inner + "\t");
            }
            for (int inner = 2; inner >= 0; inner--) {
                System.out.print(outer +" "+ inner +" "+ 2 + "\t");
            }
            for (int inner = 2; inner >= 0; inner--) {
                System.out.print(outer +" "+ 0 +" "+ inner + "\t");
            }
            for (int inner = 0; inner <= 2; inner++) {
                System.out.print(outer +" "+ inner +" "+ 0 + "\t");
            }
            System.out.print("\n");
        }

        for (int outer = 2; outer >= 0; outer--){
            for (int inner = 0; inner <= 2; inner++) {
                System.out.print(0 +" "+ outer +" "+ inner + "\t");
            }
            System.out.print("\n");
        }
    }

}
