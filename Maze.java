package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.Arrays;
import java.nio.*;
import java.util.ArrayList;
import java.lang.Integer;


public class Maze {
    private int rows;
    private int columns;
    private int[][] Walls;
    private Position start;
    private Position end;
    public Maze(){

    }

    public Maze(byte[] b) throws Exception {
        int a=0;
        int c=0;
        int d=0;
        int e=0;
        int f=0;
        int g=0;
        for(int i=0;i<4;i++){
            if(b[i]<0){
                int lm=256+b[i];
                a+=lm;
            }
            else{
                a+=b[i];
            }
        }
        this.setRows(a);
        for(int i=4;i<8;i++){
            if(b[i]<0){
                int lm=256+b[i];
                c+=lm;
            }
            else{
                c+=b[i];
            }
        }
        this.setColumns(c);
        for(int i=8;i<12;i++){
            if(b[i]<0){
                int lm=256+b[i];
                d+=lm;
            }
            else{
                d+=b[i];
            }        }
        for(int i=12;i<16;i++){
            if(b[i]<0){
                int lm=256+b[i];
                e+=lm;
            }
            else{
                e+=b[i];
            }        }
        int[][] walls = new int[a][c];
        setWalls(walls);
        Position st=new Position(d,e);
        this.setStart(st);
        for(int i=16;i<20;i++){
            if(b[i]<0){
                int lm=256+b[i];
                f+=lm;
            }
            else{
                f+=b[i];
            }        }
        for(int i=20;i<24;i++){
            if(b[i]<0){
                int lm=256+b[i];
                g+=lm;
            }
            else{
                g+=b[i];
            }        }
        Position en=new Position(f,g);
        this.setEnd(en);
        int k=24;
        for(int i=0;i<rows;i++){
            for(int j =0;j<columns;j++){
                this.setCell(i,j,b[k]);
                k++;
            }
        }
    }

    public void setWalls(int[][] walls) throws Exception {
        if (walls == null)
            throw new Exception("Walls Are Null");
        this.Walls = walls;
    }

    public int[][] getWalls() {
        return Walls;
    }

    public void setStart(Position start) throws Exception {
        if (start == null)
            throw new Exception("start is null");
        this.start = start;
    }

    public void setEnd(Position end) throws Exception {
        this.end = end;
    }

    public Position getStartPosition() {
        return start;
    }

    public Position getGoalPosition() {
        return end;
    }

    public void setRows(int rows) throws Exception {
        if (rows < 2)
            throw new Exception("Amount Of Rows Not Enough");
        this.rows = rows;
    }

    public void setColumns(int columns) throws Exception {
        if (columns < 2)
            throw new Exception("Amount Of Columns Not Enough");
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setCell(int row, int column, int val) throws Exception {
        if (row >= 0 & column >= 0 & row < rows & column < columns & (val == 0 | val == 1)) {
            Walls[row][column] = val;
        } else
            throw new Exception("invalid cell or value");

    }

    public void print() {
        boolean StartRedColor = false;
        boolean EndGreenColor = false;
        for (int currRow = 0; currRow < getRows(); currRow++) {
            if (currRow != 0)
                System.out.println(" }");
            System.out.print("{ ");
            for (int currCol = 0; currCol < getColumns(); currCol++) {
                StartRedColor = false;
                EndGreenColor = false;
                if (getStartPosition().getRowIndex() == currRow & getStartPosition().getColumnIndex() == currCol)
                    StartRedColor = true;
                if (getGoalPosition().getRowIndex() == currRow & getGoalPosition().getColumnIndex() == currCol)
                    EndGreenColor = true;
                if (StartRedColor)
                    System.out.print("\033[0;31m" + "S");
                if (EndGreenColor)
                    System.out.print("\033[0;32m" + "E");
                if (!StartRedColor && !EndGreenColor)
                    System.out.print(getWalls()[currRow][currCol]);
                if (StartRedColor || EndGreenColor)
                    System.out.print("\033[0m");
                if (currCol != columns - 1)
                    System.out.print(" ");
            }
        }
        System.out.println(" }");
    }

    public byte[] toByteArray() {
        byte by = (byte)(this.rows/255);
        byte res= (byte)(this.rows%255);
        byte co = (byte)(this.columns/255);
        byte cols= (byte)(this.columns%255);
        byte strro = (byte)(start.getRowIndex()/255);
        byte strresrow= (byte)(start.getRowIndex()%255);
        byte strco = (byte)(start.getColumnIndex()/255);
        byte strrescols= (byte)(start.getColumnIndex()%255);
        byte endrow = (byte)(end.getRowIndex()/255);
        byte endresrow= (byte)(end.getRowIndex()%255);
        byte endcol = (byte)(end.getColumnIndex()/255);
        byte endrescols= (byte)(end.getColumnIndex()%255);
        byte[] b= new byte[24+rows*columns];
        for(int i =0; i<b.length;i++) {
            b[i] = (byte) 0;
        }
        for(int y=0; y<by;y++){
            b[y]=(byte)255;
          //  b[y+4]=(byte)255;
        }
        b[by]=res;
        for(int y=0; y<co;y++){
            b[y+4]=(byte)255;
            //  b[y+4]=(byte)255;
        }
        b[co+4]=cols;
        for(int y=0; y<strro;y++){
            b[y+8]=(byte)255;
            //  b[y+4]=(byte)255;
        }
        b[strro+8]=strresrow;
        for(int y=0; y<strco;y++){
            b[y+12]=(byte)255;
            //  b[y+4]=(byte)255;
        }
        b[strco+12]=strrescols;
        for(int y=0; y<endrow;y++){
            b[y+16]=(byte)255;
            //  b[y+4]=(byte)255;
        }
        b[endrow+16]=endresrow;
        for(int y=0; y<endcol;y++){
            b[y+20]=(byte)255;
            //  b[y+4]=(byte)255;
        }
        b[endcol+20]=endrescols;
        int a= 0;
        for (int ro=0;ro<rows;ro++){
            for (int col=0;col<columns;col++){
                b[24+a]=(byte)Walls[ro][col];
                a++;
            }
        }
        return b;

        }
    }
