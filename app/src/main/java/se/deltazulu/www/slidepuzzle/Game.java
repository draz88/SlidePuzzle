package se.deltazulu.www.slidepuzzle;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Game extends AppCompatActivity {

    int moves;
    int top;
    TextView numberOfMoves;
    TextView topScore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Get GameSize from menu
        Intent intent = getIntent();
        int gamesize = intent.getIntExtra(Menu.GAMESIZE, 0);

        LinearLayout linear = (LinearLayout) findViewById(R.id.activity_game);

        SharedPreferences pref = this.getSharedPreferences("se.deltazulu.www.slidepuzzle", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();


        moves = 0;
        top = pref.getInt("top"+gamesize,0);

        numberOfMoves = (TextView) findViewById(R.id.countMoves);
        numberOfMoves.setText("Moves\n"+moves);

        topScore = (TextView) findViewById(R.id.topScore);
        topScore.setText("Top score\n"+top);

        GameBoard board = new GameBoard(this, gamesize, this);



        linear.addView(board);

        //setContentView(board);

    }

    public void countMove(){
        this.moves++;
        numberOfMoves.setText("Moves\n"+moves);
    }

    public int getMoves() {
        return moves;
    }
}
