package se.deltazulu.www.slidepuzzle;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by Dexter Zetterman on 2017-01-31.
 */

public class Tile extends ImageButton {

    private int tileWidth;
    private int tileHeight;
    private int row;
    private int col;
    private GameBoard board;
    private Context context;
    private int gamesize;
    private int imgId;
    private boolean empty;
    private int currentPos;

    //GET SCREEN-SIZE
    private DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
    private int screenWidth = metrics.widthPixels;
    private int screenHeight = metrics.heightPixels;
    private int screenDpi = metrics.densityDpi;


    public Tile(Context context, GameBoard board, int gamesize) {
        super(context);
        init(context, board, gamesize);
    }

    public void init(Context context, GameBoard board, int gamesize){
        this.board = board;
        this.context = context;
        this.gamesize = gamesize;
        this.empty = false;
        setSize();
    }

    //SETTERS
    void setSize(){
        int tileSize = (screenWidth / gamesize);
        this.tileWidth = tileSize;
        this.tileHeight = tileSize;
    }

    public void setImage(int imgId, String bg){
        this.imgId = imgId;
        this.setImageResource(imgId);
        this.setScaleType(Tile.ScaleType.FIT_CENTER);
        this.setBackgroundColor(Color.parseColor(bg));
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public void setCurrentPos(int currentPos) {
        this.currentPos = currentPos;
    }

    //GETTERS
    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public boolean getEmpty(){
        return empty;
    }

    public int getCurrentPos() {
        return currentPos;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        int action = MotionEventCompat.getActionMasked(event);
        switch (action){
            case (MotionEvent.ACTION_DOWN):
                board.tilePress(this);
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }

}
