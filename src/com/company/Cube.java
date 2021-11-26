package com.company;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class Cube {
    public int index_Size;
    public  Cubie[][][] rubix_Cube;
    public Cubie[][] cube_face;
    public int cube_dimension;
    public Cubie[][] transformed_cube_face;
    public String[] scrambled_path = new String[0];
    public static String[] possible_moves = {"R","Ri","R180","L","Li","L180","F","Fi","F180","B","Bi","B180","U","Ui","U180","D","Di","D180"};
    public  byte[] byte_moves = new byte[18];

    public Cube(){
        cube_dimension = 3;
        index_Size = cube_dimension -1;
        cube_face= new Cubie[cube_dimension][cube_dimension];
        transformed_cube_face = new Cubie[cube_dimension][cube_dimension];
        for (int i = 0; i < possible_moves.length; i++) {
            byte_moves[i] = string_to_byte(possible_moves[i]);
        }
        create_cube();
    }
    public void show_orientation(){
        for (int index1 = 0; index1 < 2; index1++) {
            for (int index2 = 0; index2 < 2; index2++) {
                for (int index3 = 0; index3 < 2; index3++) {
                    if (rubix_Cube[index1][index2][index3].id == 25) {
                        System.out.println("orientation: " + rubix_Cube[index1][index2][index3]);
                    }
                }
            }
        }
    }

    public int amount_solved(){
        int counter = 0;
        for (int z = 0; z <= index_Size; z++) {
            for (int y = 0; y <= index_Size; y++) {
                for (int x = 0; x <= index_Size; x++) {
                    if (is_cubie_solved(rubix_Cube[z][y][x], z, y, x)) {
                        counter++;
                    }
                }
            }
        }
        return counter;
    }

    public void move(String moveName){
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

        for (int z = 0; z <= index_Size; z++) {
            for (int y = 0; y <= index_Size; y++) {
                for (int x = 0; x <= index_Size; x++) {
                    if (!is_cubie_solved(rubix_Cube[z][y][x], z,y,x)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    public void show(){

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
        String top, bottom, front, back, left, right;
        int[] coord;
        int id = 0;
        rubix_Cube = new Cubie[cube_dimension][cube_dimension][cube_dimension];

        for (int index1 = 0; index1 <= index_Size; index1++){
            if (index1 == 0){
                top = null;
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
                    //rubix_Cube[index1][index2][index3] = new Cubie(top, bottom, front, back, left, right, id);
                    coord = new int[]{index1, index2, index3};
                    if ((amount_of_1s(coord) >= 1)) { // && (!(index1 == 1 && index2 == 1) || !(index1 == 1 && index3 == 1) || !(index2 == 1 && index3 == 1))) {
                        rubix_Cube[index1][index2][index3] = new Edge_Cubie(top, bottom, front, back, left, right, id, coord);
                    //} else if (amount_of_1s(coord) > 1){
                    //    rubix_Cube[index1][index2][index3] = new Center_Cubie(top, bottom, front, back, left, right, id, coord);
                    } else {
                        rubix_Cube[index1][index2][index3] = new Corner_Cubie(top, bottom, front, back, left, right, id, coord);
                    }

                    id++;
                }
            }
        }
    }

    public int amount_of_1s(int[] list){
        int counter = 0;
        for (int number : list){
            if (number == 1){
                counter++;
            }
        }
        return counter;
    }

    public void show_indexes(){
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

    public void show_id(){
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

    public String[] scramble(int amount_scrambles){
        String previous_move;
        if (scrambled_path.length > 0){
            previous_move = scrambled_path[scrambled_path.length - 1];
        } else {
            previous_move = "";
        }
        for (int move = 0; move < amount_scrambles; move++){
            String current_move = scramble_step(previous_move);
            move(current_move);
            add_to_scrambled_path(current_move);
            previous_move = current_move;
        }
        return scrambled_path;
    }

    public String scramble_step(String previous_move){
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
        String[] old_scramble = scrambled_path;
        scrambled_path = new String[scrambled_path.length + 1];

        inherit_from_array(old_scramble, scrambled_path);
        scrambled_path[scrambled_path.length - 1] = move;
    }

    public void inherit_from_array(String[] old_array, String[] new_array){
        for (int index = 0; index < old_array.length; index++){
            new_array[index] = old_array[index];
        }
    }
    //Move functions 90 degree
    public  void iterate_back(String move){
        move(reverse_move(move));
    }

    public  int amount_on_correct_place(){
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
    //180 degree
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

    public  int is_orientation_correct_corner(int layer, Cubie cubie){
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

    public  boolean is_cubie_solved(Cubie cubie, int z, int y, int x) {
        int orientation = 1;
        boolean position = Arrays.equals(cubie.correct_coordinate, new int[]{z, y, x});

        if (cubie.getClass() == Edge_Cubie.class) {
            orientation = is_orientation_correct_edge(cubie, z,y,x);
        } else if (cubie.getClass() == Corner_Cubie.class){
            orientation = is_orientation_correct_corner(z, cubie);
        }
        return position && orientation == 0;
    }

    public  void go_to_path(byte[] path){
        for (byte move: path) {
            move(byte_to_string(move));

        }
    }

}

