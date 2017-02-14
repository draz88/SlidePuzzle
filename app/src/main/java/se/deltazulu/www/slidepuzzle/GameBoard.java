package se.deltazulu.www.slidepuzzle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Dexter Zetterman on 2017-01-31.
 */

public class GameBoard extends GridLayout {

    private int gamezize;
    private Context context;
    private GridLayout.LayoutParams params;
    private ArrayList<Tile> tiles = new ArrayList<Tile>();
    private ArrayList<Tile> correctOrder = new ArrayList<Tile>();
    private Tile[][] grid;
    private int counterSize;
    /*private int moves;
    private TextView numberOfMoves;
    private int top;
    private TextView topScore;*/
    private Game game;

    int inversions;

    public GameBoard(Context context, int gamezize, Game game) {
        super(context);
        init(context, gamezize, game);
    }

    public void init(Context context, int gamezize, Game game){
        this.gamezize = gamezize;
        this.context = context;
        this.game = game;
        this.setColumnCount(gamezize);
        this.setRowCount(gamezize+1);
        this.setBackgroundColor(Color.TRANSPARENT);
        grid = new Tile[gamezize][gamezize];
        createTiles();
        shuffleTiles();
        placeTiles();
    }

    private void createTiles(){
        for(int i = 1; i <= gamezize*gamezize; i++){
            Tile tile = new Tile(this.context, this, gamezize);
            tile.setId(+i);
            tile.setImage(getResources().getIdentifier("btn"+i,"drawable",context.getPackageName()));
            if(i == gamezize*gamezize){
                tile.setEmpty(true);
            }
            tiles.add(tile);
            correctOrder.add(tile);
        }
    }

    private void shuffleTiles(){
        Collections.shuffle(tiles);
        while(!isWinnable()) {
            Collections.shuffle(tiles);
        }
    }

    private boolean isWinnable(){
        inversions = 0;
        for(int i = 0; i < tiles.size(); i++){
            for(int j = i+1; j < tiles.size(); j++) {
                if (tiles.get(i).getId() > tiles.get(j).getId() && !tiles.get(i).getEmpty() && !tiles.get(j).getEmpty()) {
                    inversions++;
                }
            }
        }
        if(inversions%2 == 1){
            return false;
        }else{
            return true;
        }
    }

    private void placeTiles(){
        int tileNumber = 0;
        for(int i = 0; i < this.gamezize; i++){
            for(int j = 0; j < this.gamezize; j++){
                Tile tile = tiles.get(tileNumber);
                tile.setRow(i);
                tile.setCol(j);
                params = new GridLayout.LayoutParams();
                params.rowSpec = GridLayout.spec(i);
                params.columnSpec = GridLayout.spec(j);
                params.height = tile.getTileHeight();
                params.width = tile.getTileWidth();
                tile.setLayoutParams(params);
                if(tile.getEmpty() == true){
                    tile.setVisibility(Tile.INVISIBLE);
                }
                grid[i][j] = tile;
                tile.setCurrentPos(tileNumber);
                this.addView(tile);
                tileNumber++;
                this.counterSize = tile.getTileHeight();
            }
        }
        /*addCounter(counterSize);
        addTopScore(counterSize);*/
    }

    /*private void addCounter(int size){
        numberOfMoves = new TextView(this.context);
        numberOfMoves.setText("Moves\n"+moves);
        params = new GridLayout.LayoutParams();
        params.rowSpec = GridLayout.spec(gamezize);
        params.columnSpec = GridLayout.spec(0);
        params.height = size;
        params.width = size;
        numberOfMoves.setLayoutParams(params);
        //numberOfMoves.setBackgroundColor(Color.LTGRAY);
        numberOfMoves.setGravity(Gravity.CENTER);
        this.addView(numberOfMoves);
    }

    private void addTopScore(int size){
        topScore = new TextView(this.context);
        topScore.setText("Top score\n"+inversions);
        params = new GridLayout.LayoutParams();
        params.rowSpec = GridLayout.spec(gamezize);
        params.columnSpec = GridLayout.spec(gamezize-1);
        params.height = size;
        params.width = size;
        topScore.setLayoutParams(params);
        //topScore.setBackgroundColor(Color.LTGRAY);
        topScore.setGravity(Gravity.CENTER);
        this.addView(topScore);
    }*/

    public void tilePress(Tile pressedTile){
        if(checkUp(pressedTile) || checkDown(pressedTile) || checkLeft(pressedTile) || checkRight(pressedTile)){
            /*moves++;
            numberOfMoves.setText("Moves\n"+moves);*/
            this.game.countMove();
            if(checkWinner()){
                AlertDialog.Builder builder = new AlertDialog.Builder(game);
                builder.setMessage("You completed the game in "+game.getMoves()+" moves");
                builder.setTitle("You win!");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        game.finish();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                Log.d("win","1");
            }else{
                Log.d("win","0");
            }
        }else{

        }
    }

    private boolean checkWinner() {
        if (tiles.equals(correctOrder)) {
            return true;
        }else{
            return false;
        }
    }

    private boolean checkUp(Tile pressedTile){
        int row = pressedTile.getRow();
        int col = pressedTile.getCol();
        if(row > 0) {
            Tile up = grid[row-1][col];
            if(up.getEmpty() == true){
                moveTile(pressedTile,up);
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
    private boolean checkDown(Tile pressedTile){
        int row = pressedTile.getRow();
        int col = pressedTile.getCol();
        if(row < gamezize-1) {
            Tile down = grid[row+1][col];
            if(down.getEmpty() == true){
                moveTile(pressedTile,down);
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
    private boolean checkLeft(Tile pressedTile){
        int row = pressedTile.getRow();
        int col = pressedTile.getCol();
        if(col > 0) {
            Tile left = grid[row][col-1];
            if(left.getEmpty() == true){
                moveTile(pressedTile,left);
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
    private boolean checkRight(Tile pressedTile){
        int row = pressedTile.getRow();
        int col = pressedTile.getCol();
        if(col < gamezize-1) {
            Tile right = grid[row][col+1];
            if(right.getEmpty() == true){
                moveTile(pressedTile,right);
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    private void moveTile(Tile fromTile, Tile toTile){

        int fromRow = fromTile.getRow();
        int fromCol = fromTile.getCol();
        int fromPos = fromTile.getCurrentPos();
        int toRow = toTile.getRow();
        int toCol = toTile.getCol();
        int toPos = toTile.getCurrentPos();

        GridLayout.LayoutParams paramsFrom;
        paramsFrom = new GridLayout.LayoutParams();
        paramsFrom.rowSpec = GridLayout.spec(fromRow);
        paramsFrom.columnSpec = GridLayout.spec(fromCol);
        paramsFrom.height = fromTile.getTileHeight();
        paramsFrom.width = fromTile.getTileWidth();

        GridLayout.LayoutParams paramsTo;
        paramsTo = new GridLayout.LayoutParams();
        paramsTo.rowSpec = GridLayout.spec(toRow);
        paramsTo.columnSpec = GridLayout.spec(toCol);
        paramsTo.height = toTile.getTileHeight();
        paramsTo.width = toTile.getTileWidth();

        fromTile.setRow(toRow);
        fromTile.setCol(toCol);

        toTile.setRow(fromRow);
        toTile.setCol(fromCol);

        fromTile.setLayoutParams(paramsTo);
        toTile.setLayoutParams(paramsFrom);

        grid[fromRow][fromCol] = toTile;
        grid[toRow][toCol] = fromTile;

        fromTile.setCurrentPos(toPos);
        toTile.setCurrentPos(fromPos);

        Collections.swap(tiles, fromPos, toPos);

    }

}
