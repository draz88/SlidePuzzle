package se.deltazulu.www.slidepuzzle;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
    int gamesize;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    LinearLayout linear;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Get GameSize from menu
        intent = getIntent();
        gamesize = intent.getIntExtra(Menu.GAMESIZE, 0);

        linear = (LinearLayout) findViewById(R.id.activity_game);

        pref = this.getSharedPreferences("se.deltazulu.www.slidepuzzle", Context.MODE_PRIVATE);
        editor = pref.edit();


        moves = 0;
        top = pref.getInt("top"+gamesize,0);

        numberOfMoves = (TextView) findViewById(R.id.countMoves);
        numberOfMoves.setText("Moves\n"+moves);

        topScore = (TextView) findViewById(R.id.topScore);
        topScore.setText("Top score\n"+top);

        GameBoard board = new GameBoard(this, gamesize, this);

        linear.addView(board);

    }

    public void countMove(){
        this.moves++;
        numberOfMoves.setText("Moves\n"+moves);
    }

    public int getMoves() {
        return moves;
    }

    public void winner(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("You completed the game in "+this.moves+" moves.");
        builder.setTitle("You win!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        if(this.top > this.moves) {
            editor = pref.edit();
            editor.putInt("top" + this.gamesize, this.moves);
            editor.commit();
        }
        dialog.show();
    }
}
