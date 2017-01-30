package se.deltazulu.www.slidepuzzle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Menu extends AppCompatActivity {

    public final static String GAMESIZE = "se.deltazulu.www.slidepuzzle.GAMESIZE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void startGame(View view){
        Intent intent = new Intent(this, Game.class);

        String tag = (String) view.getTag();
        switch (tag){
            case "3": intent.putExtra(GAMESIZE, 3);
                break;
            case "4": intent.putExtra(GAMESIZE, 4);
                break;
            case "5": intent.putExtra(GAMESIZE, 5);
                break;
            default:
                break;
        }
        startActivity(intent);
    }
}
