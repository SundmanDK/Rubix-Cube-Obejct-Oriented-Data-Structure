package com.company;
import java.lang.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;

public class Main {
    /*
    Yellow  Yellow  Yellow
    Yellow  Yellow  Yellow
    Yellow  Yellow  Yellow
    Blue    Blue    Blue    Red     Red     Red     Green   Green   Green   Orange  Orange  Orange
    Blue    Blue    Blue    Red     Red     Red     Green   Green   Green   Orange  Orange  Orange
    Blue    Blue    Blue    Red     Red     Red     Green   Green   Green   Orange  Orange  Orange
    White   White   White
    White   White   White
    White   White   White
     */

    public static Cubie[][][] rubix_Cube;
    //public static Cubie[][][] scrambledCube;
    public static Cubie temp_Cubie;
    public static String[] possible_moves = {"R","Ri","L","Li","F","Fi","B","Bi","U","Ui","D","Di"};
    public static final int size = 3;
    public static final int index_Size = size -1;
    public static Cubie[][] cubie_Rows = new Cubie[size][size];
    public static Cubie[][] cubie_Trans_Cols = new Cubie[size][size];

    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        String command;
        boolean running = true;

        //Actual Rubix Cube
        rubixCube();

        System.out.println();
        visuallize_Rubix_Cube();
        System.out.println();
        visual_Indexes();
        System.out.println();

        Scramble(5);
        while (running) {

            //System.out.println("2,2,2 id:" + rubixCube[2][2][2].top);
            System.out.println("List of commands:");
            System.out.println("Stop, R, Ri, L, Li, F, Fi, B, Bi, U, Ui, D, Di");
            System.out.println();
            visuallize_Rubix_Cube();
            System.out.println();
            System.out.print("Write a command: ");
            IDA_Star();
            command = in.nextLine();
            if (check(possible_moves, command)){
                cube_move(command);
            } else if (command.equals("Stop")) {
                running = false;
                System.out.println("Loop ended");
            } else {
                System.out.println("Not a valid command!");
            }
            System.out.println();
            System.out.println("Is the cube solved: " + solved());


        }
    }

    public static void IDA(){
        //scrambledCube = Rubix_Cube.clone();
        String[] moveList = new String[1];
        int tried_moves = 0;
        for (int i = 0; i < 12; i++){
            cube_move(possible_moves[i]);
            System.out.println(possible_moves[i]);
            visuallize_Rubix_Cube();
            System.out.println();
            if (solved()){
                break;
            } else {
                System.out.println("nr. of tried moves: "+tried_moves++);
                cube_move(reverse_move(possible_moves[i]));
                visuallize_Rubix_Cube();
            }
        }
    }

    public static void IDA2(int depth){
        for (String move : possible_moves){
            if (solved()){
                // gives final move list
                break;
            } else if (depth != 2){
                cube_move(move);
                System.out.println(move);
                visuallize_Rubix_Cube();
                System.out.println();
                IDA2(depth++);
            }
            cube_move(reverse_move(move));
            depth--;
        }
    }


    // new attempt still doesn't work
    public static String[] move_list;
    public static void IDA_Star(){
        for (int depth = 1; depth <= 20; depth++){
            if (!solved()) {
                move_list = new String[depth];
                IDA_Step(depth, 0);
            } else {
                break;
            }
        }
        System.out.println("scramble moves:");
        System.out.println(Arrays.toString(scramble_moves));
        System.out.println("solution moves:");
        System.out.println(Arrays.toString(move_list));
        visuallize_Rubix_Cube();
    }

    public static void IDA_Step(int max_Depth, int current_Depth){
        if (current_Depth < max_Depth){
            for (String move : possible_moves){
                cube_move(move);
                //System.out.println();
                //System.out.println("IDA move");
                //visuallize_Rubix_Cube();
                move_list[current_Depth] = move;
                //System.out.println(Arrays.toString(move_list));
                if (solved()){
                    System.out.println("solved");

                    break;
                } else {
                    //current_Depth = current_Depth +1;
                    IDA_Step(max_Depth, current_Depth+1);
                    cube_move(reverse_move(move));
                    //System.out.println("IDA reverse");
                    //visuallize_Rubix_Cube();
                }
            }
        }
    }


    public static String reverse_move(String move){
        switch (move) {
            case "R" -> {
                return "Ri";
            }
            case "Ri" -> {
                return "R";
            }
            case "L" -> {
                return "Li";
            }
            case "Li" -> {
                return "L";
            }
            case "F" -> {
                return "Fi";
            }
            case "Fi" -> {
                return "F";
            }
            case "B" -> {
                return "Bi";
            }
            case "Bi" -> {
                return "B";
            }
            case "U" -> {
                return "Ui";
            }
            case "Ui" -> {
                return "U";
            }
            case "D" -> {
                return "Di";
            }
            case "Di" -> {
                return "D";
            }
        }
        return move;
    }

    //Move functions
    public static void RLi(int side){
        // uses cubieRows
        // uses cubieTransCols
        for (int index1 = index_Size; index1 >= 0; index1--){
            for (int index2 = index_Size; index2 >= 0; index2--){
                cubie_Rows[index_Size -index1][index_Size -index2] = rubix_Cube[index1][index2][side];
            }
        }
        for (int index1 = index_Size; index1 >= 0; index1--){
            for (int index2 = index_Size; index2 >= 0; index2--){
                cubie_Trans_Cols[index_Size -index1][index_Size -index2] = cubie_Rows[index_Size -index2][index1];
            }
        }

        for (int index1 = index_Size; index1 >= 0; index1--){
            for (int index2 = index_Size; index2 >= 0; index2--){
                rubix_Cube[index1][index2][side] = cubie_Trans_Cols[index1][index2].RLi();
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
    public static void RiL(int side){
        // uses cubieRows
        // uses cubieTransCols
        for (int index1 = index_Size; index1 >= 0; index1--){
            for (int index2 = index_Size; index2 >= 0; index2--){
                cubie_Rows[index_Size -index1][index_Size -index2] = rubix_Cube[index1][index2][side];
            }
        }
        for (int index1 = index_Size; index1 >= 0; index1--){
            for (int index2 = index_Size; index2 >= 0; index2--){
                cubie_Trans_Cols[index_Size -index1][index_Size -index2] = cubie_Rows[index2][index_Size -index1];
            }
        }
        for (int index1 = index_Size; index1 >= 0; index1--){
            for (int index2 = index_Size; index2 >= 0; index2--){
                rubix_Cube[index1][index2][side] = cubie_Trans_Cols[index1][index2].RiL();
            }
        }
    }
    public static void FBi(int side){
        for (int index1 = index_Size; index1 >= 0; index1--){
            for (int index2 = index_Size; index2 >= 0; index2--){
                cubie_Rows[index_Size -index1][index_Size -index2] = rubix_Cube[index1][side][index2];
            }
        }
        for (int index1 = index_Size; index1 >= 0; index1--){
            for (int index2 = index_Size; index2 >= 0; index2--){
                cubie_Trans_Cols[index_Size -index1][index_Size -index2] = cubie_Rows[index2][index_Size -index1];
            }
        }
        for (int index1 = index_Size; index1 >= 0; index1--){
            for (int index2 = index_Size; index2 >= 0; index2--){
                rubix_Cube[index1][side][index2] = cubie_Trans_Cols[index1][index2].BiF();
            }
        }
    }
    public static void FiB(int side){
        for (int index1 = index_Size; index1 >= 0; index1--){
            for (int index2 = index_Size; index2 >= 0; index2--){
                cubie_Rows[index_Size -index1][index_Size -index2] = rubix_Cube[index1][side][index2];
            }
        }
        for (int index1 = index_Size; index1 >= 0; index1--){
            for (int index2 = index_Size; index2 >= 0; index2--){
                cubie_Trans_Cols[index_Size -index1][index_Size -index2] = cubie_Rows[index_Size -index2][index1];
            }
        }

        for (int index1 = index_Size; index1 >= 0; index1--){
            for (int index2 = index_Size; index2 >= 0; index2--){
                rubix_Cube[index1][side][index2] = cubie_Trans_Cols[index1][index2].BFi();
            }
        }
    }
    public static void UiD(int side){
        for (int index1 = index_Size; index1 >= 0; index1--){
            for (int index2 = index_Size; index2 >= 0; index2--){
                cubie_Rows[index_Size -index1][index_Size -index2] = rubix_Cube[side][index1][index2];
            }
        }
        for (int index1 = index_Size; index1 >= 0; index1--){
            for (int index2 = index_Size; index2 >= 0; index2--){
                cubie_Trans_Cols[index_Size -index1][index_Size -index2] = cubie_Rows[index2][index_Size -index1];
            }
        }
        for (int index1 = index_Size; index1 >= 0; index1--){
            for (int index2 = index_Size; index2 >= 0; index2--){
                rubix_Cube[side][index1][index2] = cubie_Trans_Cols[index1][index2].UiD();
            }
        }
    }
    public static void UDi(int side){
        for (int index1 = index_Size; index1 >= 0; index1--){
            for (int index2 = index_Size; index2 >= 0; index2--){
                cubie_Rows[index_Size -index1][index_Size -index2] = rubix_Cube[side][index1][index2];
            }
        }
        for (int index1 = index_Size; index1 >= 0; index1--){
            for (int index2 = index_Size; index2 >= 0; index2--){
                cubie_Trans_Cols[index_Size -index1][index_Size -index2] = cubie_Rows[index_Size -index2][index1];
            }
        }

        for (int index1 = index_Size; index1 >= 0; index1--){
            for (int index2 = index_Size; index2 >= 0; index2--){
                rubix_Cube[side][index1][index2] = cubie_Trans_Cols[index1][index2].UDi();
            }
        }
    }

    public static String[] scramble_moves;
    public static void Scramble(int scrambleMoves){
        scramble_moves = new String[scrambleMoves];
        Random random = new Random();
        int nrOfMoves = 12; //the 12 moves R, Ri, L, Li, and so on
        int whichMove;

        for (int move = 0; move < scrambleMoves; move++){
            whichMove = random.nextInt(nrOfMoves);
            scramble_moves[move] = possible_moves[whichMove];
            cube_move(possible_moves[whichMove]);
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

    //Switch? looks good
    public static void cube_move(String moveName){
        switch (moveName) {
            case "R" -> RLi(index_Size);
            case "Ri" -> RiL(index_Size);
            case "L" -> RiL(0);
            case "Li" -> RLi(0);
            case "F" -> FBi(index_Size);
            case "Fi" -> FiB(index_Size);
            case "B" -> FiB(0);
            case "Bi" -> FBi(0);
            case "U" -> UDi(index_Size);
            case "Ui" -> UiD(index_Size);
            case "D" -> UiD(0);
            case "Di" -> UDi(0);
        }
    }

    //Automated Assigning cubes and building rubix cube
    public static void rubixCube(){
        String yellow = "YELLOW";
        String blue = "BLUE";
        String red = "RED\t";
        String green = "GREEN";
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
        rubix_Cube = new Cubie[size][size][size];

        for (int index1 = 0; index1 <= index_Size; index1++){
            if (index1 == 0){
                top = blank;
                bottom = white;
            } else if (index1 == index_Size){
                top = yellow;
                bottom =blank;
            } else {
                top = blank;
                bottom = blank;
            }
            for (int index2 = 0; index2 <= index_Size; index2++){
                if (index2 == 0){
                    front = blank;
                    back = green;
                } else if (index2 == index_Size){
                    front = blue;
                    back = blank;
                } else {
                    front = blank;
                    back = blank;
                }
                for (int index3 = 0; index3 <= index_Size; index3++){
                    if (index3 == 0){
                        left = orange;
                        right = blank;
                    } else if (index3 == index_Size){
                        left = blank;
                        right = red;
                    } else {
                        left = blank;
                        right = blank;
                    }

                    //System.out.println(id);
                    rubix_Cube[index1][index2][index3] = new Cubie(top,bottom,front,back,left,right,id);
                    id++;
                }
            }
        }
    }

    //Print cube
    public static void visuallize_Rubix_Cube(){

        for (int outer = 0; outer <= index_Size; outer++){
            for (int inner = 0; inner <= index_Size; inner++) {
                System.out.print(rubix_Cube[index_Size][outer][inner].top + "\t");
            }
            System.out.print("\n");
        }

        for (int outer = index_Size; outer >= 0; outer--) {
            for (int inner = 0; inner <= index_Size; inner++) {
                System.out.print(rubix_Cube[outer][index_Size][inner].front + "\t");
            }
            for (int inner = index_Size; inner >= 0; inner--) {
                System.out.print(rubix_Cube[outer][inner][index_Size].right + "\t");
            }
            for (int inner = index_Size; inner >= 0; inner--) {
                System.out.print(rubix_Cube[outer][0][inner].back + "\t");
            }
            for (int inner = 0; inner <= index_Size; inner++) {
                System.out.print(rubix_Cube[outer][inner][0].left + "\t");
            }
            System.out.print("\n");
        }

        for (int outer = index_Size; outer >= 0; outer--){
            for (int inner = 0; inner <= index_Size; inner++) {
                System.out.print(rubix_Cube[0][outer][inner].bottom + "\t");
            }
            System.out.print("\n");
        }
    }

    //Print indexes
    public static void visual_Indexes(){
        for (int outer = 0; outer <= index_Size; outer++){
            for (int inner = 0; inner <= index_Size; inner++) {
                System.out.print(index_Size + " " + outer+ " " + inner +"\t");
            }
            System.out.print("\n");
        }

        for (int outer = index_Size; outer >= 0; outer--) {
            for (int inner = 0; inner <= index_Size; inner++) {
                System.out.print(outer +" "+ index_Size +" "+ inner + "\t");
            }
            for (int inner = index_Size; inner >= 0; inner--) {
                System.out.print(outer +" "+ inner +" "+ index_Size + "\t");
            }
            for (int inner = index_Size; inner >= 0; inner--) {
                System.out.print(outer +" "+ 0 +" "+ inner + "\t");
            }
            for (int inner = 0; inner <= index_Size; inner++) {
                System.out.print(outer +" "+ inner +" "+ 0 + "\t");
            }
            System.out.print("\n");
        }

        for (int outer = index_Size; outer >= 0; outer--){
            for (int inner = 0; inner <= index_Size; inner++) {
                System.out.print(0 +" "+ outer +" "+ inner + "\t");
            }
            System.out.print("\n");
        }
    }

    public static boolean solved(){
        //Top
        for(int index1 = 0; index1 <= index_Size; index1++){
            for(int index2 = 0; index2 <= index_Size; index2++){
                if (rubix_Cube[index_Size][index1][index2].top != "YELLOW"){
                    return false;
                }
            }
        }
        //Front
        for(int index1 = 0; index1 <= index_Size; index1++){
            for(int index2 = 0; index2 <= index_Size; index2++){
                if (rubix_Cube[index1][index_Size][index2].front != "BLUE"){
                    return false;
                }
            }
        }
        //Right
        for(int index1 = 0; index1 <= index_Size; index1++){
            for(int index2 = 0; index2 <= index_Size; index2++){
                if (rubix_Cube[index1][index2][index_Size].right != "RED\t"){
                    return false;
                }
            }
        }
        //Back
        for(int index1 = 0; index1 <= index_Size; index1++){
            for(int index2 = 0; index2 <= index_Size; index2++){
                if (rubix_Cube[index1][0][index2].back != "GREEN"){
                    return false;
                }
            }
        }
        //Left
        for(int index1 = 0; index1 <= index_Size; index1++){
            for(int index2 = 0; index2 <= index_Size; index2++){
                if (rubix_Cube[index1][index2][0].left != "ORANGE"){
                    return false;
                }
            }
        }
        //Bottom
        for(int index1 = 0; index1 <= index_Size; index1++){
            for(int index2 = 0; index2 <= index_Size; index2++){
                if (rubix_Cube[0][index1][index2].bottom != "WHITE"){
                    return false;
                }
            }
        }
        return true;
    }
}
