package se.deltazulu.www.slidepuzzle;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Game extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Get GameSize from menu
        Intent intent = getIntent();
        int gamesize = intent.getIntExtra(Menu.GAMESIZE, 0);

        GameBoard board = new GameBoard(this, gamesize);

        setContentView(board);

    }
}
