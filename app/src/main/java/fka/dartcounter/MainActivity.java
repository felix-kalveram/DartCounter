package fka.dartcounter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;


/**
 * Created by FKA on 08.03.2016.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button buttonStart501 = (Button) findViewById(R.id.button_startGame501);
        buttonStart501.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int gamePoints = 501;

                EditText editTextPlayer1 = (EditText) findViewById(R.id.editText_playerName1);
                String player1Name = editTextPlayer1.getText().toString();
                EditText editTextPlayer2 = (EditText) findViewById(R.id.editText_playerName2);
                String player2Name = editTextPlayer2.getText().toString();

                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra("gameType", gamePoints);
                intent.putExtra("player1Name", player1Name);
                intent.putExtra("player2Name", player2Name);
                startActivity(intent);
            }
        });

        Button buttonStart301 = (Button) findViewById(R.id.button_startGame301);
        buttonStart301.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int gamePoints = 301;

                EditText editTextPlayer1 = (EditText) findViewById(R.id.editText_playerName1);
                String player1Name = editTextPlayer1.getText().toString();
                EditText editTextPlayer2 = (EditText) findViewById(R.id.editText_playerName2);
                String player2Name = editTextPlayer2.getText().toString();

                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra("gameType", gamePoints);
                intent.putExtra("player1Name", player1Name);
                intent.putExtra("player2Name", player2Name);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
