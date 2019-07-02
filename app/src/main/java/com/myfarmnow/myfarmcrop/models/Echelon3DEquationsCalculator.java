package com.myfarmnow.myfarmcrop.models;

import java.util.HashMap;

public class Echelon3DEquationsCalculator {
    double [][] initialMatrix;

    int rows,columns;
    public Echelon3DEquationsCalculator(double [][] initialMatrix, int rows, int columns){
        this.rows =rows;
        this. columns =columns;
        this.initialMatrix = initialMatrix;
    }


    public static boolean isCompletelyReduced(double [][] reducedMatrix, int rows, int columns){
        int posOfLeading =-1;
        double zero =0.0;
        for(int row=0; row<rows; row++){
            int leadingValue =0;
            boolean hasLeading = false;
            int curPosOfLeading =-1;
            for(int col=0; col<columns; col++){

                if( (int)Math.ceil(reducedMatrix[row][col]) !=0){
                    //System.out.println(" Ceil :"+Math.ceil(reducedMatrix[row][col])+" = "+reducedMatrix[row][col]);
                    hasLeading = true;
                    leadingValue = (int)reducedMatrix[row][col];
                    curPosOfLeading = col;
                    break;
                }
            }

            if(hasLeading && leadingValue!=1 && row != rows-1){
                // System.out.println(" "+leadingValue);
                return false;
            }
            if(hasLeading && curPosOfLeading<row){
                //System.out.println(" "+row+" "+curPosOfLeading);
                return false;
            }
            posOfLeading = curPosOfLeading;

        }

        return true;
    }

    public static double[][] reduceMatrix(double [][] matrix, int rows, int columns){
        //check the first row and set the first element to be 1;
        double divisor =0;
        boolean row1HasDivisor = false;
        boolean row2HasDivisor = false;
        for(int col=0; col<columns; col++){
            if(matrix[0][col] !=0 ){
                divisor =matrix[0][col];
                row1HasDivisor = true;
                break;
            }
        }


        if(row1HasDivisor ){
            for(int col=0; col<columns; col++){
                matrix[0][col] = matrix[0][col]/divisor;
            }
        }


        double multiplier =0;

        if(matrix[1][0]!=0){
            multiplier = matrix[1][0];
            for(int col=0; col<columns; col++){
                matrix[1][col] = (multiplier*matrix[0][col]-matrix[1][col]);
                if(matrix[1][col] !=0 ){
                    divisor =matrix[1][col];
                    row2HasDivisor = true;

                }
            }
        }

        if(matrix[2][0]!=0){
            multiplier = matrix[2][0];
            for(int col=0; col<columns; col++){
                matrix[2][col] = (multiplier*matrix[0][col]-matrix[2][col]);
            }

        }

        if(matrix[2][1]!=0){
            multiplier = matrix[2][1]/matrix[1][1];
            for(int col=1; col<columns; col++){
                matrix[2][col] = (multiplier*matrix[1][col]-matrix[2][col]);
            }
        }
        for(int col=0; col<columns; col++){
            if(matrix[1][col] !=0 ){
                divisor =matrix[1][col];
                row2HasDivisor = true;
                break;
            }
        }

        if(row2HasDivisor ){
            for(int col=0; col<columns; col++){
                matrix[1][col] = matrix[1][col]/divisor;
            }
        }




        return matrix;
    }

    public HashMap solveEquation(){
        double reducedMatrix[][] =reduceMatrix(this.initialMatrix,rows,columns);
        return solveReducedEchelonMatrix(reducedMatrix,rows,columns);
    }

    public static HashMap  solveReducedEchelonMatrix(double [][] reducedMatrix, int rows, int columns){
        HashMap<String, Double> solution = new HashMap<>();
        //isCompletelyReduced()
        if(isCompletelyReduced(reducedMatrix,rows,columns)){
            //calculate z
            double z,x,y;
            if(reducedMatrix[2][2] !=0){
                z = reducedMatrix[2][3]/reducedMatrix[2][2];
            }
            else{
                z=0;
            }
            if(reducedMatrix[1][1] !=0){
                y = (reducedMatrix[1][3]-reducedMatrix[1][2]*z)/reducedMatrix[1][1];
            }
            else{
                y=0;
            }
            if(reducedMatrix[0][0] !=0){
                x = (reducedMatrix[0][3]-reducedMatrix[0][1]*y-reducedMatrix[0][2]*z)/reducedMatrix[0][0];
            }
            else{
                x=0;
            }

            solution.put("x", x);
            solution.put("y", y);
            solution.put("z", z);

            return solution;

        }
        return null;
    }


    }

