package org.example;

import java.util.Random;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final Random random = new Random();
    private static final StringBuilder sb = new StringBuilder();

    public static void main(String[] args) {
        int rows = 3;
        String field = generateField(rows);
        System.out.println("random field:\n" +field+ '\n');
        System.out.println("formatted field:\n" +formatFields(field, rows)+ '\n');
        int intToSave = convertToIntBits(field);
        field = convertIntBitsToField(intToSave);
        System.out.println("parsed field from " +intToSave+ ":\n" +formatFields(field, rows)+ '\n');
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

    private static String convertIntBitsToField(int intBits){
        String binaryString = Integer.toBinaryString(intBits);
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
        //System.out.println("N: parsed binary string " + binaryString + " that is equal to " + intBits+ " into ");
        return sb.toString();
    }
}