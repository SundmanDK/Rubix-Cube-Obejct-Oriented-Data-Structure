package com.company;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class Cube {
    public int index_Size;
    public Cubie[][][] rubix_Cube;
    public Cubie[][] cube_face;
    public int cube_dimension;
    public Cubie[][] transformed_cube_face;
    public String[] scrambled_path = new String[0];
    public static String[] possible_moves = {"R","Ri","R180","L","Li","L180","F","Fi","F180","B","Bi","B180","U","Ui","U180","D","Di","D180"};
    public  byte[] byte_moves = new byte[18];

    public Cube(){
        //initial values and making the cube.
        cube_dimension = 3;
        index_Size = cube_dimension -1;
        cube_face= new Cubie[cube_dimension][cube_dimension];
        transformed_cube_face = new Cubie[cube_dimension][cube_dimension];
        for (int i = 0; i < possible_moves.length; i++) {
            byte_moves[i] = string_to_byte(possible_moves[i]);
        }
        create_cube();
    }

    public void move(String moveName){
        //Receives a command and calls the appropriate method to apply the move.
        switch (moveName) {
            case "R"    ->  RLi(index_Size); // byte = 1
            case "Ri"   ->  RiL(index_Size); // byte = 2
            case "R180" ->  RL180(index_Size); // byte = 3
            case "L"    ->  RiL(0); // byte = 4
            case "Li"   ->  RLi(0); // byte = 5
            case "L180" ->  RL180(0); // byte = 6
            case "F"    ->  FBi(index_Size); // byte = 7
            case "Fi"   ->  FiB(index_Size); // byte = 8
            case "F180" ->  FB180(index_Size); // byte = 9
            case "B"    ->  FiB(0); // byte = 10
            case "Bi"   ->  FBi(0); // byte = 11
            case "B180" ->  FB180(0); // byte = 12
            case "U"    ->  UDi(index_Size); // byte = 13
            case "Ui"   ->  UiD(index_Size); // byte = 14
            case "U180" ->  UD180(index_Size); // byte = 15
            case "D"    ->  UiD(0); // byte = 16
            case "Di"   ->  UDi(0); // byte = 17
            case "D180" ->  UD180(0); // byte = 18
        }
    }

    public boolean is_solved() {
        //Checks if the cube is solved
        for (int z = 0; z <= index_Size; z++) {
            for (int y = 0; y <= index_Size; y++) {
                for (int x = 0; x <= index_Size; x++) {
                    if (!is_cubie_solved(rubix_Cube[z][y][x], z,y,x)) {
                        return false;       //If there is a cubie which is not "solved" then the cube is not solved.
                    }
                }
            }
        }
        return true;
    }

    public void show(){
        //Prints the cube in a folded out manner allowing us to verify that everything works as intended.
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

    public String byte_to_string(byte byte_move){
        //converts the moves from byte to string, used for calling move() and printing scramble and solution paths.
        String string_move = "";

        switch (byte_move) {
            case 1    ->  string_move = "R";
            case 2   ->  string_move = "Ri";
            case 3 ->  string_move = "R180";
            case 4    ->  string_move = "L";
            case 5   ->  string_move = "Li";
            case 6  ->  string_move = "L180";
            case 7    ->  string_move = "F";
            case 8   ->  string_move = "Fi";
            case 9  ->  string_move = "F180";
            case 10    ->  string_move = "B";
            case 11   ->  string_move = "Bi";
            case 12 ->  string_move = "B180";
            case 13    ->  string_move = "U";
            case 14   ->  string_move = "Ui";
            case 15 ->  string_move = "U180";
            case 16    ->  string_move = "D";
            case 17   ->  string_move = "Di";
            case 18 ->  string_move = "D180";
            default -> throw new IllegalStateException("Unexpected value: " + byte_move);
        }
        return string_move;
    }

    public byte string_to_byte(String string_move){
        //converts from string to byte, used when storing the path of the solution to save memory.
        byte byte_move;
        byte R = 1;
        byte Ri = 2;
        byte R180 = 3;
        byte L = 4;
        byte Li = 5;
        byte L180 = 6;
        byte F = 7;
        byte Fi = 8;
        byte F180 = 9;
        byte B = 10;
        byte Bi = 11;
        byte B180 = 12;
        byte U = 13;
        byte Ui = 14;
        byte U180 = 15;
        byte D = 16;
        byte Di = 17;
        byte D180 = 18;

        switch (string_move) {
            case "R"    ->  byte_move =  R;
            case "Ri"   ->  byte_move = Ri;
            case "R180" ->  byte_move = R180;
            case "L"    ->  byte_move = L;
            case "Li"   ->  byte_move = Li;
            case "L180" ->  byte_move = L180;
            case "F"    ->  byte_move = F;
            case "Fi"   ->  byte_move = Fi;
            case "F180" ->  byte_move = F180;
            case "B"    ->  byte_move = B;
            case "Bi"   ->  byte_move = Bi;
            case "B180" ->  byte_move = B180;
            case "U"    ->  byte_move = U;
            case "Ui"   ->  byte_move = Ui;
            case "U180" ->  byte_move = U180;
            case "D"    ->  byte_move = D;
            case "Di"   ->  byte_move = Di;
            case "D180" ->  byte_move = D180;
            default -> throw new IllegalStateException("Unexpected value: " + string_move);
        }
        return byte_move;
    }

    public void create_cube(){
        //Creates the cube, initializes the cubies and assigns type and colors.
        String top, bottom, front, back, left, right;
        int[] coord;
        int id = 0;
        rubix_Cube = new Cubie[cube_dimension][cube_dimension][cube_dimension];

        for (int index1 = 0; index1 <= index_Size; index1++){
            if (index1 == 0){
                top = null;         //null is used on "tiles" facing the inside of the cube.
                bottom = "WHITE";
            } else if (index1 == index_Size){
                top = "YELLOW";
                bottom =null;
            } else {
                top = null;
                bottom = null;
            }
            for (int index2 = 0; index2 <= index_Size; index2++){
                if (index2 == 0){
                    front = null;
                    back = "GREEN";
                } else if (index2 == index_Size){
                    front = "BLUE";
                    back = null;
                } else {
                    front = null;
                    back = null;
                }
                for (int index3 = 0; index3 <= index_Size; index3++) {
                    if (index3 == 0) {
                        left = "ORANGE";
                        right = null;
                    } else if (index3 == index_Size) {
                        left = null;
                        right = "RED\t";
                    } else {
                        left = null;
                        right = null;
                    }

                    coord = new int[]{index1, index2, index3};
                    if (amount_of_1s(coord) >= 1){
                        rubix_Cube[index1][index2][index3] = new Edge_Cubie(top, bottom, front, back, left, right, id, coord);
                    } else {
                        rubix_Cube[index1][index2][index3] = new Corner_Cubie(top, bottom, front, back, left, right, id, coord);
                    }

                    id++;
                }
            }
        }
    }

    public int amount_of_1s(int[] list){
        //Used to determine if the cubie is to be a corner or an edge.
        //If one or more of its 3 coordinates are 1 then the cubie is an edge or a center.
        //We do not distinguish between edge and center for sake of simplicity.
        int counter = 0;
        for (int number : list){
            if (number == 1){
                counter++;
            }
        }
        return counter;
    }

    public String[] scramble(int amount_scrambles){
        //Part of a 2 method process of scrambling the cube.
        //It performs an amount of scramble moves based on what it is told.
        String previous_move;           //Used to avoid repeating moves or moves followed by their inverse.
        if (scrambled_path.length > 0){
            previous_move = scrambled_path[scrambled_path.length - 1];
        } else {
            previous_move = "";
        }
        for (int move = 0; move < amount_scrambles; move++){
            String current_move = scramble_step(previous_move);     //receives an applicable move
            move(current_move);                                     //applies it
            add_to_scrambled_path(current_move);
            previous_move = current_move;
        }
        return scrambled_path;
    }

    public String scramble_step(String previous_move){
        //Picks the scramble move, making sure it is neither a repeat nor inverse of the previous move.
        //We could not find a good way of including 180 degree turns in this.
        //But the scramble is still better than it was without excluding repeats and inverses.
        Random r = new Random();
        int nr_of_moves = possible_moves.length;
        int which_move = r.nextInt(nr_of_moves);
        String the_move = possible_moves[which_move];
        if (!Objects.equals(the_move, previous_move) && !Objects.equals(the_move, reverse_move(previous_move))) {
            return possible_moves[which_move];
        }
        else {
            return scramble_step(previous_move);
        }

    }

    public void add_to_scrambled_path(String move){
        //Adds element to the scramble path, allows multiple calls of scramble to store their moves.
        //Also means user inputted moves are added to the scramble path.
        //This is important because of the way our algorithm goes back to the scrambled state.
        String[] old_scramble = scrambled_path;
        scrambled_path = new String[scrambled_path.length + 1];

        inherit_from_array(old_scramble, scrambled_path);
        scrambled_path[scrambled_path.length - 1] = move;
    }

    public void inherit_from_array(String[] old_array, String[] new_array){
        //Takes the contents of one array and stores them in a new array.
        //Mainly used when an array is replaced with an array which is 1 element larger.
        for (int index = 0; index < old_array.length; index++){
            new_array[index] = old_array[index];
        }
    }
    //Move functions 90 degree
    public  void iterate_back(String move){
        move(reverse_move(move));       //applies the inverse move of the move given.
    }

    public  int amount_on_correct_place(){
        //Finds how many cubies are on the correct place.
        //Used in the old heuristic.
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

    public  int amount_correct_orientation(){
        //Finds how many cubies have correct orientation.
        //Used in the old heuristic.
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

    //Following 9 methods works the same, but on different faces of the cube.
    //We found that a move and the inverse of the move on the opposite side of the cube turn the cubies the same direction.
    //First the cubies on a face are saved in an array, then the array is transformed and the new placement
    // of the cubes is saved in the rubix_cube array.
    public  void RLi(int side){
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
    }
    public  void RiL(int side){
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
    public  void FBi(int side){
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
    public  void FiB(int side){
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
    public  void UiD(int side){
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
    public  void UDi(int side){
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
    public  void RL180(int side){
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
    public  void FB180(int side){
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
    public  void UD180(int side){
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

    public  String reverse_move(String move){
        //Defines the reverse move of any given move.
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

    public  int is_orientation_correct_edge(Cubie cubie, int z, int y, int x){
        //Checks and returns if am edge cubie has correct orientation.
        if (Arrays.equals(new int[]{y, x}, new int[]{1, 1})){
            return 0;       //Always correct orientation for center cubies.
        }
        //First checks if the cube is on the front or back face, this covers 8 of 12 edge cubies.
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

        //If the cubie is not on the front or back face it checks on the left and right faces catching the last 4 edge cubies.
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

    public  int is_orientation_correct_corner(int layer, Cubie cubie){
        //checks orientation of corner cubies.
        //First checks if the cubie it on the top face, where 4 out of 8 corner cubies reside.
        if (layer == 2) { //top
            if (cubie.correct_top()){
                return 0;
            } else if (cubie.left_turned_top()){
                return -1;
            } else if (cubie.right_turned_top()){
                return 1;
            }
        }
        //If the cubie is not on the top face it is checked for on the bottom face.
        if (cubie.correct_bottom()){
            return 0;
        } else if (cubie.left_turned_bottom()){
            return -1;
        } else if (cubie.right_turned_bottom()){
            return 1;
        }
        return Integer.parseInt(null);      //The method never reaches this. If it does then we know something is wrong.
    }

    public  boolean is_cubie_solved(Cubie cubie, int z, int y, int x) {
        //Checks if the cubie has correct placement and correct orientation.
        //Calls orientation methods for corner and edge cubies.
        int orientation = 1;
        boolean position = Arrays.equals(cubie.correct_coordinate, new int[]{z, y, x});

        //Subclasses are used to distinguish how we deal with orientation.
        if (cubie.getClass() == Edge_Cubie.class) {
            orientation = is_orientation_correct_edge(cubie, z,y,x);
        } else if (cubie.getClass() == Corner_Cubie.class){
            orientation = is_orientation_correct_corner(z, cubie);
        }
        return position && orientation == 0;
    }

    public  void go_to_path(byte[] path){
        //Takes the cube from one state to another, following scramble path or solution path.
        for (byte move: path) {
            move(byte_to_string(move));

        }
    }

}

