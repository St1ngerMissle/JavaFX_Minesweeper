import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Random;
import java.util.Stack;

public class Minefield extends GridPane {
    int numColumns = 12;
    int numRows = 12;
    int size = 30;
    int bombCount = 20;
    boolean gaming = true;

    Tile[][] pos = new Tile[numColumns][numRows];

    int clearedTiles = 0;
    final int GOAL = (numColumns*numRows) - bombCount;
    public Minefield(){
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numColumns; col++) {
                Tile tile = new Tile(size,col,row,this);
                //save tile pos in matrix
                pos[col][row] = tile;
                // Add the square to the grid
                this.add(tile, col, row);
            }
        }

        //pick random locations for bombs
        Random r = new Random();
        HashMap<Integer,Boolean> bombSpots = new HashMap<>();
        for(int i = 0; i < bombCount; i++){
            int loc = -1;
            //if(loc)
            do {
                loc = r.nextInt(numColumns*numRows);
            } while(bombSpots.get(loc) != null);
            bombSpots.put(loc,true);
        }

        int i = 0;
        for (int row = 0; row < numRows; row++)
            for (int col = 0; col < numColumns; col++){
                pos[col][row].pretty();
                if(bombSpots.get(i++) != null){
                    pos[col][row].prime();
                    for(Tile tile : getAdj(pos[col][row]))
                        if(tile != null)
                            tile.adjBombs++;
                }
            }
    }
    public void winCheck(){
        if(gaming && clearedTiles >= GOAL){
            for(Tile[] ta : pos){
                for(Tile t : ta){
                    if(!t.isBomb)
                        t.disp.setFill(Color.GREEN);
                }
            }
        }
    }

    public void gameover(){
        gaming = false;
        for(int col = 0; col < numColumns; col++)
            for(int row = 0; row < numColumns; row++){
                pos[col][row].revealTileEvent.handle(null);
            }
    }
    public Stack<Tile> getAdj(Tile t){
        Stack<Tile> adj = new Stack<>();
        if(t.row > 0){
            if(t.col > 0)
                adj.push(pos[t.col-1][t.row-1]);
            adj.push(pos[t.col][t.row-1]);
            if(t.col < numColumns - 1)
                adj.push(pos[t.col+1][t.row-1]);
        } 
        if(t.row < numRows - 1){
            if(t.col > 0)
                adj.push(pos[t.col-1][t.row+1]);
            adj.push(pos[t.col][t.row+1]);
            if(t.col < numColumns - 1)
                adj.push(pos[t.col+1][t.row+1]);
        }
        if(t.col > 0)
            adj.push(pos[t.col-1][t.row]);
        if(t.col < numColumns - 1)
            adj.push(pos[t.col+1][t.row]);    

        return adj;
    }
/*
    Tile[] getAdj(Tile tile){
        Tile[] adj = new Tile[8];
            if(tile.row > 0){
                if(tile.col > 0){
                    adj[0] = pos[tile.col-1][tile.row-1];
                    adj[1] = pos[tile.col-1][tile.row];
                    if(tile.row < numRows - 1)
                        adj[2] = pos[tile.col-1][tile.row+1];
                } if (tile.col < numColumns - 1) {
                    adj[5] = pos[tile.col+1][tile.row-1]; 
                    adj[6] = pos[tile.col+1][tile.row]; 
                    if(tile.row < numRows - 1)
                        adj[7] = pos[tile.col+1][tile.row+1]; 
                }
            }
            if(tile.row > 0)
                adj[3] = pos[tile.col][tile.row-1];
            if(tile.row < numRows - 1)
                adj[4] = pos[tile.col][tile.row+1];
        return adj;
    }
 */
    /*public boolean centerTiles(){
        boolean validBombTile = true;
        

        return validBombTile;
    }*/
    public int getPixelWidth(){
        return numColumns*size + numColumns;
    }
    public int getPixelHeight(){
        return numRows*size + numRows;
    }
}
