package org.example;

import java.util.Random;
import java.io.FileOutputStream;
import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final Random random = new Random();
    private static final StringBuilder sb = new StringBuilder();

    public static void main(String[] args) {
        int rows = 3;
        int totalCells = rows*rows;
        String field = generateField(rows);
        System.out.println("random field:\n" +field+ '\n');
        System.out.println("formatted field:\n" +formatFields(field, rows)+ '\n');
        int intToSave = convertToIntBits(field);
        field = convertBinaryStringToField(convertIntToBinaryString(intToSave, totalCells));
        System.out.println("parsed field from " +intToSave+ ":\n" +formatFields(field, rows)+ '\n');
        writeBinaryString(convertIntToBinaryString(intToSave, totalCells));
    }

    private static String convertIntToBinaryString(int input, int length){
        String binaryString = Integer.toBinaryString(input);
        sb.setLength(0);
        int padding = length * 2 - binaryString.length();
        for (int i = 0; i < padding; i++) {
            sb.append('0');
        }
        sb.append(binaryString);
        System.out.println("N: " +input+ ">>>>" +binaryString+ " , padding: " +padding);
        return sb.toString();
    }

    private static String generateField(int rows){
        int totalCells = rows*rows;
        for (int i = 0; i < totalCells; i++) {
            int cell_occ = random.nextInt(0, 4);
            switch (cell_occ) {
                case 0:
                    sb.append('_');
                    break;
                case 1:
                    sb.append('R');
                    break;
                case 2:
                    sb.append('X');
                    break;
                case 3:
                    sb.append('O');
                    break;
            }
        }
        return sb.toString();
    }

    private static String formatFields(String field, int rowSize){
        sb.setLength(0);
        for (int i = 0; i < field.length(); i++) {
            sb.append(field.charAt(i));
            if (i > 0 && (i + 1) % rowSize == 0){
                sb.append('\n');
            }
            else {
                sb.append('\t');
            }
        }
        return sb.toString();
    }

    private static int convertToIntBits(String fields){
        sb.setLength(0);
        for (int i = 0; i < fields.length(); i++) {
            char ch = fields.charAt(i);
            if (ch == '_'){
                sb.append('0');
                sb.append('0');
            }
            else if (ch == 'R'){
                sb.append('0');
                sb.append('1');
            }
            else if (ch == 'X'){
                sb.append('1');
                sb.append('0');
            }
            else if (ch == 'O'){
                sb.append('1');
                sb.append('1');
            }
        }
        String binaryString = sb.toString();
        int ret = Integer.parseInt(sb.toString(), 2);
        System.out.println("N: parsed to INT " + binaryString + " that is equal to " + ret);
        return ret;
    }

    private static String convertBinaryStringToField(String binaryString){
        sb.setLength(0);
        for (int i = 0; i < binaryString.length(); i = i + 2) {
            char ch = binaryString.charAt(i);
            if (ch == '0'){
                if (binaryString.charAt(i+1) == '0'){
                    sb.append('_');
                }
                else {
                    sb.append('R');
                }
            }
            else {
                if (binaryString.charAt(i+1) == '0'){
                    sb.append('X');
                }
                else {
                    sb.append('O');
                }
            }
        }
        return sb.toString();
    }

    private static void writeBinaryString(String binaryString){
        String[] strings = new String[3];
        sb.setLength(0);
        for (int i = 0; i < 24; i++) {
            char ch = '0';
            if (i < binaryString.length()){
                ch = binaryString.charAt(i);
            }
            sb.append(ch);
            if ((i + 1) % 8 == 0){
                strings[((i + 1) / 8) - 1] = sb.toString();
                sb.setLength(0);
            }
        }
        sb.setLength(0);
        for (int i = 0; i < strings.length; i++) {
            System.out.println('\t' +strings[i]);
        }

        byte[] bytes = new byte[strings.length];
        for (int i = 0; i < strings.length; i++) {
            bytes[i] = (byte) Integer.parseInt(strings[i], 2);  // Convert binary string to byte
        }

        String path = "file3bytes";
        try (FileOutputStream fos = new FileOutputStream(path)) {
            // Write the byte array to the file
            fos.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}