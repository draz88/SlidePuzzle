package se.deltazulu.www.slidepuzzle;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    GameBoard board;
    ImageView border;

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

        board = new GameBoard(this, gamesize, this);

        border = new ImageView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.height = 20;
        params.width = LinearLayout.LayoutParams.MATCH_PARENT;

        border.setLayoutParams(params);
        border.setBackgroundColor(Color.parseColor("#009999"));
        linear.addView(border);

        linear.addView(board);

        border = new ImageView(this);
        border.setLayoutParams(params);
        border.setBackgroundColor(Color.parseColor("#009999"));
        linear.addView(border);

        //GameTimer gameTimer = new GameTimer(this);
        //linear.addView(gameTimer);


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
        if(this.top == 0 || this.top > this.moves) {
            editor = pref.edit();
            editor.putInt("top"+this.gamesize, this.moves);
            editor.apply();
        }
        dialog.show();
    }
}
