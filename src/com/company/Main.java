package com.company;
import java.lang.*;
import java.util.*;

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
    public static String[] possible_moves = {"R","Ri","R180","L","Li","L180","F","Fi","F180","B","Bi","B180","U","Ui","U180","D","Di","D180"};
    public static final int size = 3;
    public static final int index_Size = size -1;
    public static Cubie[][] cube_face = new Cubie[size][size];
    public static Cubie[][] transformed_cube_face = new Cubie[size][size];
    public static boolean cube_solved = false;
    public static String[] move_list;
    public static Random random = new Random();
    public static String[] scramble_moves;
    public static int nr_of_moves = possible_moves.length; //the 18 moves R, Ri, R180, L, Li, L180 and so on
    public static int which_move;
    public static String previous_move = "";
    public static PriorityQueue<Open_Node> all_open_nodes = new PriorityQueue<Open_Node>();
    public static ArrayList<Open_Node> all_nodes = new ArrayList<Open_Node>();

    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        String command;
        boolean running = true;
        boolean automatic = true;
        //Actual Rubix Cube
        rubixCube();

        System.out.println();
        visuallize_Rubix_Cube();
        System.out.println();
        visual_Indexes();
        System.out.println();
        visual_ID();
        System.out.println();

        scramble(6);
        System.out.println("scramble moves:");
        System.out.println(Arrays.toString(scramble_moves));
        while (running) {
            System.out.println("-------------------------------------------------------------------------------------");
            System.out.println();
            visuallize_Rubix_Cube();
            System.out.println();
            //visual_ID();
            //System.out.println();

            if (automatic) {
                //IDA_Star();
                IDA_step(new Open_Node(20, new String[]{}) ,20);
                System.out.println();
                // System.out.println("Solved");
                System.out.println("scramble moves:");
                System.out.println(Arrays.toString(scramble_moves));
                System.out.println("solution moves:");
                System.out.println(Arrays.toString(move_list));
                visuallize_Rubix_Cube();
                runGC();
                automatic = false;
            }else {
                System.out.println();
                System.out.println("List of commands:");
                System.out.println("Stop, R, Ri, R180, L, Li, L180, F, Fi, F180, B, Bi, B180, U, Ui, U180, D, Di, D180");
                System.out.println();
                System.out.print("Write a command: ");
                command = in.nextLine();
                if (string_in_array(possible_moves, command)) {
                    cube_move(command);
                    System.out.println("is it solved: " + solved());
                } else if (command.equals("Stop")) {
                    running = false;
                    System.out.println("Loop ended");
                } else {
                    System.out.println("Not a valid command!");
                }

                for (int index1 = 0; index1 < 2; index1++){
                    for (int index2 = 0; index2 < 2; index2++){
                        for (int index3 = 0; index3 < 2; index3++){
                            if (rubix_Cube[index1][index2][index3].id == 25){
                                System.out.println("orientation: " + rubix_Cube[index1][index2][index3]);
                            }
                        }
                    }
                }

                System.out.println();
                System.out.println("how many are solved " + (amount_solved()-1));

            }
        }
    }

    public static void IDA_Star(){
        for (int depth = 1; depth <= 20; depth++){
            System.out.println("Depth: " +depth);
            if (solved()) {
                return;
            } else {
                //move_list = new String[depth];
                IDA_step(new Open_Node(20, new String[]{}), depth);
            }
        }
        System.out.println("Couldn't find a solution");
    }

    public static void IDA_step(Open_Node node, int max_depth){
        int counter = 0;
        if (node.get_path().length < max_depth){
            /*
            if (counter % 3 == 0){
                for (int i = 0; i < 3; i++) {
                    extend_node(node, i);
                }
            }
            */

            Open_Node best_node = all_open_nodes.poll();
            move_list = best_node.get_path();
            // cube_move(best_node.get_path()[best_node.get_path().length - 1]);
            if (all_nodes.size() > Math.pow(18, 2)) {
                WriteToFile.write_to_file(all_nodes);
                return;
            }
            if (!solved()){
                rubixCube();
                go_to_path(scramble_moves);
                go_to_path(move_list);
                IDA_step(best_node, max_depth);
            }
        }
        counter++;
    }
    public static void extend_node(Open_Node node, int num) {
        Open_Node[] generation;
        generation = make_new_nodes(node);
        for (Open_Node new_node :generation) {
            make_new_nodes(new_node);
            if(num < 3) {
                extend_node(new_node, num);
            }

        }

    }

    public static void go_to_path(String[] path){
        for (String move: path) {
            cube_move(move);
        }
    }

    public static Open_Node[] make_new_nodes(Open_Node node){
        Open_Node[] new_open_nodes = new Open_Node[18];
        int counter = 0;
        for (String move : possible_moves){
            String[] new_path = new String[node.get_path().length + 1];
            array_to_new_array(node.get_path(), new_path);
            System.out.println("Move: " + move);
            cube_move(move);
            new_path[new_path.length - 1] = move;
            Open_Node new_node = new Open_Node(fitness(new_path), new_path);
            all_nodes.add(new_node);
            all_open_nodes.offer(new_node);
            iterate_back(move);
            new_open_nodes[counter] = new_node;
            counter++;
        }
        return new_open_nodes;
    }


    public static void array_to_new_array(String[] old_array, String[] new_array){
        for (int index = 0; index < old_array.length; index++){
            new_array[index] = old_array[index];
        }
    }

    public static void iterate_back(String move){
        cube_move(reverse_move(move));
    }

    public static String reverse_move(String move){
        switch (move) {
            case "R" -> {
                return "Ri";
            }
            case "Ri" -> {
                return "R";
            }
            case "R180" -> {
                return "R180";
            }
            case "L" -> {
                return "Li";
            }
            case "Li" -> {
                return "L";
            }
            case "L180" -> {
                return "L180";
            }
            case "F" -> {
                return "Fi";
            }
            case "Fi" -> {
                return "F";
            }
            case "F180" -> {
                return "F180";
            }
            case "B" -> {
                return "Bi";
            }
            case "B180" -> {
                return "B180";
            }
            case "Bi" -> {
                return "B";
            }
            case "U" -> {
                return "Ui";
            }
            case "U180" -> {
                return "U180";
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
            case "D180" -> {
                return "D180";
            }
        }
        return move;
    }

    //Move functions 90 degree
    public static void RLi(int side){
        // uses cubieRows
        // uses cubieTransCols
        for (int index1 = index_Size; index1 >= 0; index1--){
            for (int index2 = index_Size; index2 >= 0; index2--){
                cube_face[index_Size -index1][index_Size -index2] = rubix_Cube[index1][index2][side];
            }
        }
        for (int index1 = index_Size; index1 >= 0; index1--){
            for (int index2 = index_Size; index2 >= 0; index2--){
                transformed_cube_face[index_Size -index1][index_Size -index2] = cube_face[index_Size -index2][index1];
            }
        }

        for (int index1 = index_Size; index1 >= 0; index1--){
            for (int index2 = index_Size; index2 >= 0; index2--){
                rubix_Cube[index1][index2][side] = transformed_cube_face[index1][index2].LiR();
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
                cube_face[index_Size -index1][index_Size -index2] = rubix_Cube[index1][index2][side];
            }
        }
        for (int index1 = index_Size; index1 >= 0; index1--){
            for (int index2 = index_Size; index2 >= 0; index2--){
                transformed_cube_face[index_Size -index1][index_Size -index2] = cube_face[index2][index_Size -index1];
            }
        }
        for (int index1 = index_Size; index1 >= 0; index1--){
            for (int index2 = index_Size; index2 >= 0; index2--){
                rubix_Cube[index1][index2][side] = transformed_cube_face[index1][index2].LRi();
            }
        }
    }
    public static void FBi(int side){
        for (int index1 = index_Size; index1 >= 0; index1--){
            for (int index2 = index_Size; index2 >= 0; index2--){
                cube_face[index_Size -index1][index_Size -index2] = rubix_Cube[index1][side][index2];
            }
        }
        for (int index1 = index_Size; index1 >= 0; index1--){
            for (int index2 = index_Size; index2 >= 0; index2--){
                transformed_cube_face[index_Size -index1][index_Size -index2] = cube_face[index2][index_Size -index1];
            }
        }
        for (int index1 = index_Size; index1 >= 0; index1--){
            for (int index2 = index_Size; index2 >= 0; index2--){
                rubix_Cube[index1][side][index2] = transformed_cube_face[index1][index2].BiF();
            }
        }
    }
    public static void FiB(int side){
        for (int index1 = index_Size; index1 >= 0; index1--){
            for (int index2 = index_Size; index2 >= 0; index2--){
                cube_face[index_Size -index1][index_Size -index2] = rubix_Cube[index1][side][index2];
            }
        }
        for (int index1 = index_Size; index1 >= 0; index1--){
            for (int index2 = index_Size; index2 >= 0; index2--){
                transformed_cube_face[index_Size -index1][index_Size -index2] = cube_face[index_Size -index2][index1];
            }
        }

        for (int index1 = index_Size; index1 >= 0; index1--){
            for (int index2 = index_Size; index2 >= 0; index2--){
                rubix_Cube[index1][side][index2] = transformed_cube_face[index1][index2].BFi();
            }
        }
    }
    public static void UiD(int side){
        for (int index1 = index_Size; index1 >= 0; index1--){
            for (int index2 = index_Size; index2 >= 0; index2--){
                cube_face[index_Size -index1][index_Size -index2] = rubix_Cube[side][index1][index2];
            }
        }
        for (int index1 = index_Size; index1 >= 0; index1--){
            for (int index2 = index_Size; index2 >= 0; index2--){
                transformed_cube_face[index_Size -index1][index_Size -index2] = cube_face[index2][index_Size -index1];
            }
        }
        for (int index1 = index_Size; index1 >= 0; index1--){
            for (int index2 = index_Size; index2 >= 0; index2--){
                rubix_Cube[side][index1][index2] = transformed_cube_face[index1][index2].DUi();
            }
        }
    }
    public static void UDi(int side){
        for (int index1 = index_Size; index1 >= 0; index1--){
            for (int index2 = index_Size; index2 >= 0; index2--){
                cube_face[index_Size -index1][index_Size -index2] = rubix_Cube[side][index1][index2];
            }
        }
        for (int index1 = index_Size; index1 >= 0; index1--){
            for (int index2 = index_Size; index2 >= 0; index2--){
                transformed_cube_face[index_Size -index1][index_Size -index2] = cube_face[index_Size -index2][index1];
            }
        }

        for (int index1 = index_Size; index1 >= 0; index1--){
            for (int index2 = index_Size; index2 >= 0; index2--){
                rubix_Cube[side][index1][index2] = transformed_cube_face[index1][index2].DiU();
            }
        }
    }
    //180 degree
    public static void RL180(int side){
        for (int index1 = index_Size; index1 >= 0; index1--){
            for (int index2 = index_Size; index2 >= 0; index2--){
                cube_face[index_Size -index1][index_Size -index2] = rubix_Cube[index1][index2][side];
            }
        }
        for (int index1 = index_Size; index1 >= 0; index1--){
            for (int index2 = index_Size; index2 >= 0; index2--){
                rubix_Cube[index1][index2][side] = cube_face[index1][index2].LR180();
            }
        }
    }
    public static void FB180(int side){
        for (int index1 = index_Size; index1 >= 0; index1--){
            for (int index2 = index_Size; index2 >= 0; index2--){
                cube_face[index_Size -index1][index_Size -index2] = rubix_Cube[index1][side][index2];
            }
        }
        for (int index1 = index_Size; index1 >= 0; index1--){
            for (int index2 = index_Size; index2 >= 0; index2--){
                rubix_Cube[index1][side][index2] = cube_face[index1][index2].BF180();
            }
        }
    }
    public static void UD180(int side){
        for (int index1 = index_Size; index1 >= 0; index1--){
            for (int index2 = index_Size; index2 >= 0; index2--){
                cube_face[index_Size -index1][index_Size -index2] = rubix_Cube[side][index1][index2];
            }
        }
        for (int index1 = index_Size; index1 >= 0; index1--){
            for (int index2 = index_Size; index2 >= 0; index2--){
                rubix_Cube[side][index1][index2] = cube_face[index1][index2].DU180();
            }
        }
    }

    public static void scramble(int scrambleMoves){
        scramble_moves = new String[scrambleMoves];
        for (int move = 0; move < scrambleMoves; move++){
            scramble_step(move);
        }
    }

    public static void scramble_step(int move){
        which_move = random.nextInt(nr_of_moves);
        String the_move = possible_moves[which_move];
        if (!Objects.equals(the_move, previous_move) && !Objects.equals(the_move, reverse_move(previous_move))) {
            scramble_moves[move] = possible_moves[which_move];
            cube_move(possible_moves[which_move]);
            previous_move = possible_moves[which_move];
        } else scramble_step(move);
    }

    private static boolean string_in_array(String[] array, String word) {
        for (String element : array) {
            if (element.equals(word)) {
                return true;
            }
        }
        return false;
    }

    //Switch? looks good
    public static void cube_move(String moveName){
        switch (moveName) {
            case "R"    ->  RLi(index_Size);
            case "Ri"   ->  RiL(index_Size);
            case "R180" ->  RL180(index_Size);
            case "L"    ->  RiL(0);
            case "Li"   ->  RLi(0);
            case "L180" ->  RL180(0);
            case "F"    ->  FBi(index_Size);
            case "Fi"   ->  FiB(index_Size);
            case "F180" ->  FB180(index_Size);
            case "B"    ->  FiB(0);
            case "Bi"   ->  FBi(0);
            case "B180" ->  FB180(0);
            case "U"    ->  UDi(index_Size);
            case "Ui"   ->  UiD(index_Size);
            case "U180" ->  UD180(index_Size);
            case "D"    ->  UiD(0);
            case "Di"   ->  UDi(0);
            case "D180" ->  UD180(0);
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
        int[] coord;
        int id = 0;
        rubix_Cube = new Cubie[size][size][size];

        for (int index1 = 0; index1 <= index_Size; index1++){
            if (index1 == 0){
                top = null;
                bottom = white;
            } else if (index1 == index_Size){
                top = yellow;
                bottom =null;
            } else {
                top = null;
                bottom = null;
            }
            for (int index2 = 0; index2 <= index_Size; index2++){
                if (index2 == 0){
                    front = null;
                    back = green;
                } else if (index2 == index_Size){
                    front = blue;
                    back = null;
                } else {
                    front = null;
                    back = null;
                }
                for (int index3 = 0; index3 <= index_Size; index3++){
                    if (index3 == 0){
                        left = orange;
                        right = null;
                    } else if (index3 == index_Size){
                        left = null;
                        right = red;
                    } else {
                        left = null;
                        right = null;
                    }
                    //rubix_Cube[index1][index2][index3] = new Cubie(top, bottom, front, back, left, right, id);
                    coord = new int[]{index1, index2, index3};
                    if ((index1 == 1 || index2 == 1 || index3 == 1)){ // && (!(index1 == 1 && index2 == 1) || !(index1 == 1 && index3 == 1) || !(index2 == 1 && index3 == 1))) {
                        rubix_Cube[index1][index2][index3] = new Edge_Cubie(top, bottom, front, back, left, right, id, coord);
                    } else {
                        rubix_Cube[index1][index2][index3] = new Corner_Cubie(top, bottom, front, back, left, right, id, coord);
                    }

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

    public static void visual_ID(){
        for (int outer = 0; outer <= index_Size; outer++){
            for (int inner = 0; inner <= index_Size; inner++) {
                System.out.print(rubix_Cube[index_Size][outer][inner].id + "\t");
            }
            System.out.print("\n");
        }

        for (int outer = index_Size; outer >= 0; outer--) {
            for (int inner = 0; inner <= index_Size; inner++) {
                System.out.print(rubix_Cube[outer][index_Size][inner].id + "\t");
            }
            for (int inner = index_Size; inner >= 0; inner--) {
                System.out.print(rubix_Cube[outer][inner][index_Size].id + "\t");
            }
            for (int inner = index_Size; inner >= 0; inner--) {
                System.out.print(rubix_Cube[outer][0][inner].id + "\t");
            }
            for (int inner = 0; inner <= index_Size; inner++) {
                System.out.print(rubix_Cube[outer][inner][0].id + "\t");
            }
            System.out.print("\n");
        }

        for (int outer = index_Size; outer >= 0; outer--){
            for (int inner = 0; inner <= index_Size; inner++) {
                System.out.print(rubix_Cube[0][outer][inner].id + "\t");
            }
            System.out.print("\n");
        }
    }

    public static double fitness(String[] path){
        double fitness_position = 10 - ((double) amount_on_correct_place() / 27) * 10;
        double fitness_orientation = 10 - ((double) amount_correct_orientation() / 27) * 10;
        int g = path.length;
        double h = fitness_orientation + fitness_position;
        double f = g + h;
        return f;
    }

    public static int amount_solved(){
        int counter = 0;
        for (int z = 0; z <= index_Size; z++) {
            for (int y = 0; y <= index_Size; y++) {
                for (int x = 0; x <= index_Size; x++) {
                    if (is_this_cubie_correct(rubix_Cube[z][y][x], z, y, x)) {
                        counter++;
                    }
                }
            }
        }
        return counter;
    }

    public static int amount_on_correct_place(){
        int counter = 0;
        for (int z = 0; z <= index_Size; z++) {
            for (int y = 0; y <= index_Size; y++) {
                for (int x = 0; x <= index_Size; x++) {
                    if (Arrays.equals(rubix_Cube[z][y][x].correct_coordinate, new int[]{z, y, x})) {
                        counter++;
                    }
                }
            }
        }
        return counter;
    }

    public static int amount_correct_orientation(){
        int orientation = 1;
        int counter = 0;
        for (int z = 0; z <= index_Size; z++) {
            for (int y = 0; y <= index_Size; y++) {
                for (int x = 0; x <= index_Size; x++) {
                    if (rubix_Cube[z][y][x].getClass() == Edge_Cubie.class) {
                        orientation = is_orientation_correct_edge(rubix_Cube[z][y][x], z,y,x);
                    } else if (rubix_Cube[z][y][x].getClass() == Corner_Cubie.class){
                        orientation = is_orientation_correct_corner(z, rubix_Cube[z][y][x]);
                    }
                    if (orientation == 0){
                        counter++;
                    }
                }
            }
        }
        return counter;
    }

    public static boolean is_this_cubie_correct(Cubie cubie, int z, int y, int x) {
        int orientation = 1;
        boolean yes_no = false;
        boolean position = Arrays.equals(cubie.correct_coordinate, new int[]{z, y, x});

        if (cubie.getClass() == Edge_Cubie.class) {
            orientation = is_orientation_correct_edge(cubie, z,y,x);
        } else if (cubie.getClass() == Corner_Cubie.class){
            orientation = is_orientation_correct_corner(z, cubie);
        }
        //System.out.println(""+ z+y+x+ " " +orientation);

        if (position && orientation == 0) yes_no = true;
        return yes_no;
    }

    public static int is_orientation_correct_edge(Cubie cubie, int z, int y, int x){
        if (Arrays.equals(new int[]{y, x}, new int[]{1, 1})){
            return 0; // Correct orientation for center cubies
        }

        int face = cubie.correct_coordinate[1];
        if (face == 2){ // front
            if (rubix_Cube[1][face][1].side_color[2].equals(cubie.side_color[2])){
                return 0;
            }
            return 1;
        } else if (face == 0){ //back
            if (rubix_Cube[1][face][1].side_color[3].equals(cubie.side_color[3])){
                return 0;
            }
            return 1;
        }

        // followed by left and right
        face = cubie.correct_coordinate[2];
        if (face == 0){ // left
            if (rubix_Cube[1][1][face].side_color[4].equals(cubie.side_color[4])){
                return 0;
            }
            return 1;
        } else if (face == 2){ // right
            if (rubix_Cube[1][1][face].side_color[5].equals(cubie.side_color[5])){
                return 0;
            }
            return 1;
        }
        return 1;
    }

    public static int is_orientation_correct_corner(int layer, Cubie cubie){
        if (layer == 2) { //top
            if (cubie.correct_top()){
                return 0;
            } else if (cubie.left_turned_top()){
                return -1;
            } else if (cubie.right_turned_top()){
                return -1;
            }
        }
        if (cubie.correct_bottom()){
            return 0;
        } else if (cubie.left_turned_bottom()){
            return -1;
        } else if (cubie.right_turned_bottom()){
            return -1;
        }
        return Integer.parseInt(null);
    }

    public static boolean solved() {

        for (int z = 0; z <= index_Size; z++) {
            for (int y = 0; y <= index_Size; y++) {
                for (int x = 0; x <= index_Size; x++) {
                    if (!is_this_cubie_correct(rubix_Cube[z][y][x], z,y,x)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static void runGC() {
        Runtime runtime = Runtime.getRuntime();
        long memoryMax = runtime.maxMemory();
        long memoryUsed = runtime.totalMemory() - runtime.freeMemory();
        double memoryUsedPercent = (memoryUsed * 100.0) / memoryMax;
        System.out.println("memory Used: " + memoryUsed/1000000 + "mb");
        if (memoryUsedPercent > 90.0)
            System.gc();
    }
}

