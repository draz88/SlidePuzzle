package se.deltazulu.www.slidepuzzle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class Game extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        int gamesize = intent.getIntExtra(Menu.GAMESIZE, 0);

        Log.d("Gamesize", ""+gamesize);
    }
}
