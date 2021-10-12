package com.company;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;

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

    public static Cubie[][][] rubixCube;
    public static Cubie tempCubie;
    public static String[] moves = {"R","Ri","L","Li","F","Fi","B","Bi","U","Ui","D","Di"};
    public static final int size = 3;
    public static final int indexSize = size -1;

    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        String command;
        boolean running = true;

        //Actual Rubix Cube
        RubixCube2();

        System.out.println();
        VisuallizeRubixCube();
        System.out.println();
        VisualIndexes();
        System.out.println();

        Scramble(0);
        while (running) {
            //System.out.println("2,2,2 id:" + rubixCube[2][2][2].top);
            System.out.println("List of commands:");
            System.out.println("Stop, R, Ri, L, Li, F, Fi, B, Bi, U, Ui, D, Di");
            System.out.println();
            VisuallizeRubixCube();
            System.out.println();
            System.out.print("Write a command: ");
            command = in.nextLine();
            if (check(moves, command)){
                moves(command);
            } else if (command.equals("Stop")) {
                running = false;
                System.out.println("Loop ended");
            } else {
                System.out.println("Not a valid command!");
            }
        }
    }

    //Move functions
    public static void R(){
        tempCubie = rubixCube[2][0][2].RLi();
        rubixCube[2][0][2] = rubixCube[2][2][2].RLi();
        rubixCube[2][2][2] = rubixCube[0][2][2].RLi();
        rubixCube[0][2][2] = rubixCube[0][0][2].RLi();
        rubixCube[0][0][2] = tempCubie;

        tempCubie = rubixCube[2][1][2].RLi();
        rubixCube[2][1][2] = rubixCube[1][2][2].RLi();
        rubixCube[1][2][2] = rubixCube[0][1][2].RLi();
        rubixCube[0][1][2] = rubixCube[1][0][2].RLi();
        rubixCube[1][0][2] = tempCubie;
    }

    public static void RLi(int side){
        Cubie[][] cubieRows = new Cubie[size][size];
        Cubie[][] cubieTransCols = new Cubie[size][size];
        for (int index1 = indexSize; index1 >= 0; index1--){
            for (int index2 = indexSize; index2 >= 0; index2--){
                cubieRows[indexSize-index1][indexSize-index2] = rubixCube[index1][index2][side];
            }
        }
        for (int index1 = indexSize; index1 >= 0; index1--){
            for (int index2 = indexSize; index2 >= 0; index2--){
                cubieTransCols[indexSize-index1][indexSize-index2] = cubieRows[indexSize-index2][index1];
            }
        }

        for (int index1 = indexSize; index1 >= 0; index1--){
            for (int index2 = indexSize; index2 >= 0; index2--){
                rubixCube[index1][index2][side] = cubieTransCols[index1][index2].RLi();
            }
        }

        System.out.println();
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                System.out.print(cubieRows[i][j].id + "\t");
            }
            System.out.print("\n");
        }
        System.out.println();
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                System.out.print(cubieTransCols[i][j].id + "\t");
            }
            System.out.print("\n");
        }
    }

    public static void Ri(){
        tempCubie = rubixCube[2][0][2].RiL();
        rubixCube[2][0][2] = rubixCube[0][0][2].RiL();
        rubixCube[0][0][2] = rubixCube[0][2][2].RiL();
        rubixCube[0][2][2] = rubixCube[2][2][2].RiL();
        rubixCube[2][2][2] = tempCubie;

        tempCubie = rubixCube[2][1][2].RiL();
        rubixCube[2][1][2] = rubixCube[1][0][2].RiL();
        rubixCube[1][0][2] = rubixCube[0][1][2].RiL();
        rubixCube[0][1][2] = rubixCube[1][2][2].RiL();
        rubixCube[1][2][2] = tempCubie;
    }

    public static void Ri2(){
        Cubie[][] cubieRows = new Cubie[size][size];
        Cubie[][] cubieTransCols = new Cubie[size][size];

        for (int index1 = indexSize; index1 >= 0; index1--){
            for (int index2 = indexSize; index2 >= 0; index2--){
                cubieRows[indexSize-index1][indexSize-index2] = rubixCube[index1][index2][indexSize];
            }
        }
        for (int index1 = indexSize; index1 >= 0; index1--){
            for (int index2 = indexSize; index2 >= 0; index2--){
                cubieTransCols[indexSize-index1][indexSize-index2] = cubieRows[index2][indexSize-index1];
            }
        }
        for (int index1 = indexSize; index1 >= 0; index1--){
            for (int index2 = indexSize; index2 >= 0; index2--){
                rubixCube[index1][index2][indexSize] = cubieTransCols[index1][index2].RiL();
            }
        }
        /*
        System.out.println();
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                System.out.print(cubieRows[i][j].id + "\t");
            }
            System.out.print("\n");
        }
        System.out.println();
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                System.out.print(cubieTransCols[i][j].id + "\t");
            }
            System.out.print("\n");
        }
        */
    }

    public static void L(){
        tempCubie = rubixCube[2][0][0].RiL();
        rubixCube[2][0][0] = rubixCube[0][0][0].RiL();
        rubixCube[0][0][0] = rubixCube[0][2][0].RiL();
        rubixCube[0][2][0] = rubixCube[2][2][0].RiL();
        rubixCube[2][2][0] = tempCubie;

        tempCubie = rubixCube[2][1][0].RiL();
        rubixCube[2][1][0] = rubixCube[1][0][0].RiL();
        rubixCube[1][0][0] = rubixCube[0][1][0].RiL();
        rubixCube[0][1][0] = rubixCube[1][2][0].RiL();
        rubixCube[1][2][0] = tempCubie;

    }

    public static void RiL(int side){
        Cubie[][] cubieRows = new Cubie[size][size];
        Cubie[][] cubieTransCols = new Cubie[size][size];

        for (int index1 = indexSize; index1 >= 0; index1--){
            for (int index2 = indexSize; index2 >= 0; index2--){
                cubieRows[indexSize-index1][indexSize-index2] = rubixCube[index1][index2][side];
            }
        }
        for (int index1 = indexSize; index1 >= 0; index1--){
            for (int index2 = indexSize; index2 >= 0; index2--){
                cubieTransCols[indexSize-index1][indexSize-index2] = cubieRows[index2][indexSize-index1];
            }
        }
        for (int index1 = indexSize; index1 >= 0; index1--){
            for (int index2 = indexSize; index2 >= 0; index2--){
                rubixCube[index1][index2][side] = cubieTransCols[index1][index2].RiL();
            }
        }
        System.out.println();
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                System.out.print(cubieRows[i][j].id + "\t");
            }
            System.out.print("\n");
        }
        System.out.println();
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                System.out.print(cubieTransCols[i][j].id + "\t");
            }
            System.out.print("\n");
        }
    }

    public static void Li(){
        tempCubie = rubixCube[2][0][0].RLi();
        rubixCube[2][0][0] = rubixCube[2][2][0].RLi();
        rubixCube[2][2][0] = rubixCube[0][2][0].RLi();
        rubixCube[0][2][0] = rubixCube[0][0][0].RLi();
        rubixCube[0][0][0] = tempCubie;

        tempCubie = rubixCube[2][1][0].RLi();
        rubixCube[2][1][0] = rubixCube[1][2][0].RLi();
        rubixCube[1][2][0] = rubixCube[0][1][0].RLi();
        rubixCube[0][1][0] = rubixCube[1][0][0].RLi();
        rubixCube[1][0][0] = tempCubie;
    }

    public static void F(){
        tempCubie = rubixCube[2][2][0].BiF();
        rubixCube[2][2][0] = rubixCube[0][2][0].BiF();
        rubixCube[0][2][0] = rubixCube[0][2][2].BiF();
        rubixCube[0][2][2] = rubixCube[2][2][2].BiF();
        rubixCube[2][2][2] = tempCubie;

        tempCubie = rubixCube[2][2][1].BiF();
        rubixCube[2][2][1] = rubixCube[1][2][0].BiF();
        rubixCube[1][2][0] = rubixCube[0][2][1].BiF();
        rubixCube[0][2][1] = rubixCube[1][2][2].BiF();
        rubixCube[1][2][2] = tempCubie;
    }

    public static void Fi(){
        tempCubie = rubixCube[2][2][0].BFi();
        rubixCube[2][2][0] = rubixCube[2][2][2].BFi();
        rubixCube[2][2][2] = rubixCube[0][2][2].BFi();
        rubixCube[0][2][2] = rubixCube[0][2][0].BFi();
        rubixCube[0][2][0] = tempCubie;

        tempCubie = rubixCube[2][2][1].BFi();
        rubixCube[2][2][1] = rubixCube[1][2][2].BFi();
        rubixCube[1][2][2] = rubixCube[0][2][1].BFi();
        rubixCube[0][2][1] = rubixCube[1][2][0].BFi();
        rubixCube[1][2][0] = tempCubie;
    }

    public static void B(){
        tempCubie = rubixCube[2][0][0].BFi();
        rubixCube[2][0][0] = rubixCube[2][0][2].BFi();
        rubixCube[2][0][2] = rubixCube[0][0][2].BFi();
        rubixCube[0][0][2] = rubixCube[0][0][0].BFi();
        rubixCube[0][0][0] = tempCubie;

        tempCubie = rubixCube[2][0][1].BFi();
        rubixCube[2][0][1] = rubixCube[1][0][2].BFi();
        rubixCube[1][0][2] = rubixCube[0][0][1].BFi();
        rubixCube[0][0][1] = rubixCube[1][0][0].BFi();
        rubixCube[1][0][0] = tempCubie;
    }

    public static void Bi(){
        tempCubie = rubixCube[2][0][0].BiF();
        rubixCube[2][0][0] = rubixCube[0][0][0].BiF();
        rubixCube[0][0][0] = rubixCube[0][0][2].BiF();
        rubixCube[0][0][2] = rubixCube[2][0][2].BiF();
        rubixCube[2][0][2] = tempCubie;

        tempCubie = rubixCube[2][0][1].BiF();
        rubixCube[2][0][1] = rubixCube[1][0][0].BiF();
        rubixCube[1][0][0] = rubixCube[0][0][1].BiF();
        rubixCube[0][0][1] = rubixCube[1][0][2].BiF();
        rubixCube[1][0][2] = tempCubie;
    }

    public static void U(){
        tempCubie = rubixCube[2][0][0].UDi();
        rubixCube[2][0][0] = rubixCube[2][2][0].UDi();
        rubixCube[2][2][0] = rubixCube[2][2][2].UDi();
        rubixCube[2][2][2] = rubixCube[2][0][2].UDi();
        rubixCube[2][0][2] = tempCubie;

        tempCubie = rubixCube[2][0][1].UDi();
        rubixCube[2][0][1] = rubixCube[2][1][0].UDi();
        rubixCube[2][1][0] = rubixCube[2][2][1].UDi();
        rubixCube[2][2][1] = rubixCube[2][1][2].UDi();
        rubixCube[2][1][2] = tempCubie;
    }

    public static void Ui(){
        tempCubie = rubixCube[2][0][0].UiD();
        rubixCube[2][0][0] = rubixCube[2][0][2].UiD();
        rubixCube[2][0][2] = rubixCube[2][2][2].UiD();
        rubixCube[2][2][2] = rubixCube[2][2][0].UiD();
        rubixCube[2][2][0] = tempCubie;

        tempCubie = rubixCube[2][0][1].UiD();
        rubixCube[2][0][1] = rubixCube[2][1][2].UiD();
        rubixCube[2][1][2] = rubixCube[2][2][1].UiD();
        rubixCube[2][2][1] = rubixCube[2][1][0].UiD();
        rubixCube[2][1][0] = tempCubie;
    }

    public static void D(){
        tempCubie = rubixCube[0][0][0].UiD();
        rubixCube[0][0][0] = rubixCube[0][0][2].UiD();
        rubixCube[0][0][2] = rubixCube[0][2][2].UiD();
        rubixCube[0][2][2] = rubixCube[0][2][0].UiD();
        rubixCube[0][2][0] = tempCubie;

        tempCubie = rubixCube[0][0][1].UiD();
        rubixCube[0][0][1] = rubixCube[0][1][2].UiD();
        rubixCube[0][1][2] = rubixCube[0][2][1].UiD();
        rubixCube[0][2][1] = rubixCube[0][1][0].UiD();
        rubixCube[0][1][0] = tempCubie;
    }

    public static void Di(){
        tempCubie = rubixCube[0][0][0].UDi();
        rubixCube[0][0][0] = rubixCube[0][2][0].UDi();
        rubixCube[0][2][0] = rubixCube[0][2][2].UDi();
        rubixCube[0][2][2] = rubixCube[0][0][2].UDi();
        rubixCube[0][0][2] = tempCubie;

        tempCubie = rubixCube[0][0][1].UDi();
        rubixCube[0][0][1] = rubixCube[0][1][0].UDi();
        rubixCube[0][1][0] = rubixCube[0][2][1].UDi();
        rubixCube[0][2][1] = rubixCube[0][1][2].UDi();
        rubixCube[0][1][2] = tempCubie;
    }

    public static void Scramble(int scrambleMoves){
        Random random = new Random();
        int nrOfMoves = 12; //the 12 moves R, Ri, L, Li, and so on
        int whichMove;

        for (int move = 0; move < scrambleMoves; move++){
            whichMove = random.nextInt(nrOfMoves);

            moves(moves[whichMove]);
        }
    }

    private static boolean check(String[] arr, String toCheckValue) {
        for (String element : arr) {
            if (element.equals(toCheckValue)) {
                return true;
            }
        }
        return false;
    }

    public static void moves(String moveName){

        if (moveName.equals("R")) {
            RLi(2);
        } else if (moveName.equals("Ri")){
            RiL(2);
        } else if (moveName.equals("L")){
            RiL(0);
        } else if (moveName.equals("Li")){
            RLi(0);
        } else if (moveName.equals("F")){
            F();
        } else if (moveName.equals("Fi")){
            Fi();
        } else if (moveName.equals("B")){
            B();
        } else if (moveName.equals("Bi")){
            Bi();
        } else if (moveName.equals("U")){
            U();
        } else if (moveName.equals("Ui")){
            Ui();
        } else if (moveName.equals("D")){
            D();
        } else if (moveName.equals("Di")){
            Di();
        }
    }

    //Assign cubes and build rubix cube
    public static void RubixCube (){
        String yellow = "YELLOW";
        String blue = "BLUE";
        String red = "RED\t";
        String green = "Green";
        String orange = "ORANGE";
        String white = "WHITE";
        String blank = "BLANK";
        /*
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
        */
    }
    //Automated Assigning cubes and building rubix cube

    public static void RubixCube2(){
        String yellow = "YELLOW";
        String blue = "BLUE";
        String red = "RED\t";
        String green = "Green";
        String orange = "ORANGE";
        String white = "WHITE";
        String blank = "BLANK";
        String top;
        String bottom;
        String front;
        String back;
        String left;
        String right;
        int id = 0;
        rubixCube = new Cubie[size][size][size];

        for (int index1 = 0; index1 <= indexSize; index1++){
            if (index1 == 0){
                top = blank;
                bottom = white;
            } else if (index1 == indexSize){
                top = yellow;
                bottom =blank;
            } else {
                top = blank;
                bottom = blank;
            }
            for (int index2 = 0; index2 <= indexSize; index2++){
                if (index2 == 0){
                    front = blank;
                    back = green;
                } else if (index2 == indexSize){
                    front = blue;
                    back = blank;
                } else {
                    front = blank;
                    back = blank;
                }
                for (int index3 = 0; index3 <= indexSize; index3++){
                    if (index3 == 0){
                        left = orange;
                        right = blank;
                    } else if (index3 == indexSize){
                        left = blank;
                        right = red;
                    } else {
                        left = blank;
                        right = blank;
                    }

                    //System.out.println(id);
                    rubixCube[index1][index2][index3] = new Cubie(top,bottom,front,back,left,right,id);
                    id++;
                }
            }
        }
    }

    //Print cube
    public static void VisuallizeRubixCube(){

        for (int outer = 0; outer <= indexSize; outer++){
            for (int inner = 0; inner <= indexSize; inner++) {
                System.out.print(rubixCube[indexSize][outer][inner].top + "\t");
            }
            System.out.print("\n");
        }

        for (int outer = indexSize; outer >= 0; outer--) {
            for (int inner = 0; inner <= indexSize; inner++) {
                System.out.print(rubixCube[outer][indexSize][inner].front + "\t");
            }
            for (int inner = indexSize; inner >= 0; inner--) {
                System.out.print(rubixCube[outer][inner][indexSize].right + "\t");
            }
            for (int inner = indexSize; inner >= 0; inner--) {
                System.out.print(rubixCube[outer][0][inner].back + "\t");
            }
            for (int inner = 0; inner <= indexSize; inner++) {
                System.out.print(rubixCube[outer][inner][0].left + "\t");
            }
            System.out.print("\n");
        }

        for (int outer = indexSize; outer >= 0; outer--){
            for (int inner = 0; inner <= indexSize; inner++) {
                System.out.print(rubixCube[0][outer][inner].bottom + "\t");
            }
            System.out.print("\n");
        }
    }

    //Print indexes
    public static void VisualIndexes(){
        for (int outer = 0; outer <= indexSize; outer++){
            for (int inner = 0; inner <= indexSize; inner++) {
                System.out.print(indexSize+ " " + outer+ " " + inner +"\t");
            }
            System.out.print("\n");
        }

        for (int outer = indexSize; outer >= 0; outer--) {
            for (int inner = 0; inner <= indexSize; inner++) {
                System.out.print(outer +" "+ indexSize +" "+ inner + "\t");
            }
            for (int inner = indexSize; inner >= 0; inner--) {
                System.out.print(outer +" "+ inner +" "+ indexSize + "\t");
            }
            for (int inner = indexSize; inner >= 0; inner--) {
                System.out.print(outer +" "+ 0 +" "+ inner + "\t");
            }
            for (int inner = 0; inner <= indexSize; inner++) {
                System.out.print(outer +" "+ inner +" "+ 0 + "\t");
            }
            System.out.print("\n");
        }

        for (int outer = indexSize; outer >= 0; outer--){
            for (int inner = 0; inner <= indexSize; inner++) {
                System.out.print(0 +" "+ outer +" "+ inner + "\t");
            }
            System.out.print("\n");
        }
    }

}
