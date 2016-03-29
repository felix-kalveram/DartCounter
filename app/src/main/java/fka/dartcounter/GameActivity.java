package fka.dartcounter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by FKA on 08.03.2016.
 */
public class GameActivity extends MainActivity {

    int clickedPoint;

    int clickedPointOne = 0;
    int clickedPointTwo = 0;
    int clickedPointThree = 0;
    int clickCounter = 0;

    String winnerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Dartboard dartboard = new Dartboard(this);
        Intent getDataMainActivity = getIntent();
        final Game game = new Game();

        game.setGameType(getDataMainActivity.getIntExtra("gameType", 0));

        final Player player1 = new Player();
        player1.setPlayerName(getDataMainActivity.getStringExtra("player1Name"));
        player1.setRemainingPoints(game.getGameType());
        player1.setPlaying(true);

        final Player player2 = new Player();
        player2.setPlayerName(getDataMainActivity.getStringExtra("player2Name"));
        player2.setRemainingPoints(game.getGameType());
        player2.setPlaying(false);

        final TextView textViewRemainingPointsPlayer1 = (TextView) findViewById(R.id.textView_remainingPointsPlayer1);
        final TextView textViewRemainingPointsPlayer2 = (TextView) findViewById(R.id.textView_remainingPointsPlayer2);

        ////// counter
        final TextView textViewPlayerIsPlaying = (TextView) findViewById(R.id.textView_playerisplaying);
        textViewPlayerIsPlaying.setText(player1.getPlayerName() + " is playing.");
        setTextViewClickedPoints(clickedPointOne, clickedPointTwo, clickedPointThree);

        Button buttonCountPoints = (Button) findViewById(R.id.button_countPoints);
        buttonCountPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (player1.isPlaying()
                        && player1.getRemainingPoints() - player1.calculateRoundPoints(clickedPointOne, clickedPointTwo, clickedPointThree) == 0) {
                    player1.setWinning(true);
                    startWinning(player1, player2);
                } else if (player2.isPlaying()
                        && player2.getRemainingPoints() - player2.calculateRoundPoints(clickedPointOne, clickedPointTwo, clickedPointThree) == 0) {
                    player2.setWinning(true);
                    startWinning(player1, player2);
                } else if (player1.isPlaying()
                        && player1.getRemainingPoints() - player1.calculateRoundPoints(clickedPointOne, clickedPointTwo, clickedPointThree) < 0) {
                    MakeToastTooManyPoints();
                } else if (player2.isPlaying()
                        && player2.getRemainingPoints() - player2.calculateRoundPoints(clickedPointOne, clickedPointTwo, clickedPointThree) < 0) {
                    MakeToastTooManyPoints();
                } else if (player1.isPlaying() == true && player2.isPlaying() == false) {
                    player1.calculateRemainingPoints(player1, clickedPointOne, clickedPointTwo, clickedPointThree);
                    game.addGameAction(calculateGameAction(clickedPointOne, clickedPointTwo, clickedPointThree));
                } else if (player2.isPlaying() == true && player1.isPlaying() == false) {
                    player2.calculateRemainingPoints(player2, clickedPointOne, clickedPointTwo, clickedPointThree);
                    game.addGameAction(calculateGameAction(clickedPointOne, clickedPointTwo, clickedPointThree));
                }
                switchPlayerIsPlaying(player1, player2);
                setClickCounterToZero();
                setClickedPointsToZero();

                setTextViewRemainingPoints(textViewRemainingPointsPlayer1, textViewRemainingPointsPlayer2, player1, player2);
                setTextViewPlayerIsPlaying(textViewPlayerIsPlaying, player1, player2);
                setTextViewClickedPoints(clickedPointOne, clickedPointTwo, clickedPointThree);

                System.out.println(game.getGameActions());
            }
        });

        ////// dartboard
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout_dartboard);
        linearLayout.addView(dartboard);

        dartboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickCounter < 3) {
                    clickCounter += 1;
                    clickedPoint = dartboard.getPoints();
                    switch (clickCounter) {
                        case 1:
                            clickedPointOne = clickedPoint;
                            setTextViewClickedPoints(clickedPointOne, clickedPointTwo, clickedPointThree);
                            break;
                        case 2:
                            clickedPointTwo = clickedPoint;
                            setTextViewClickedPoints(clickedPointOne, clickedPointTwo, clickedPointThree);
                            break;
                        case 3:
                            clickedPointThree = clickedPoint;
                            setTextViewClickedPoints(clickedPointOne, clickedPointTwo, clickedPointThree);
                            break;
                    }
                }
            }
        });

        Button buttonDeletePoints = (Button) findViewById(R.id.button_deletepoint);
        buttonDeletePoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickCounter > 0) {
                    switch (clickCounter) {
                        case 1:
                            clickedPointOne = 0;
                            setTextViewClickedPoints(clickedPointOne, clickedPointTwo, clickedPointThree);
                            break;
                        case 2:
                            clickedPointTwo = 0;
                            setTextViewClickedPoints(clickedPointOne, clickedPointTwo, clickedPointThree);
                            break;
                        case 3:
                            clickedPointThree = 0;
                            setTextViewClickedPoints(clickedPointOne, clickedPointTwo, clickedPointThree);
                            break;
                    }
                    clickCounter -= 1;
                }
            }
        });

        Button buttonDeleteLastAction = (Button) findViewById(R.id.button_deletelastaction);
        buttonDeleteLastAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (game.getGameActions().size() > 0) {
                    if (player1.isPlaying() == true) {
                        player2.setRemainingPoints(player2.getRemainingPoints() + game.getLastGameAction());
                        game.deleteLastGameAction();
                        switchPlayerIsPlaying(player1, player2);
                        setClickCounterToZero();
                        setClickedPointsToZero();

                        setTextViewRemainingPoints(textViewRemainingPointsPlayer1, textViewRemainingPointsPlayer2, player1, player2);
                        setTextViewPlayerIsPlaying(textViewPlayerIsPlaying, player1, player2);
                        setTextViewClickedPoints(clickedPointOne, clickedPointTwo, clickedPointThree);
                    } else if (player2.isPlaying() == true) {
                        player1.setRemainingPoints(player1.getRemainingPoints() + game.getLastGameAction());
                        game.deleteLastGameAction();
                        switchPlayerIsPlaying(player1, player2);
                        setClickCounterToZero();
                        setClickedPointsToZero();

                        setTextViewRemainingPoints(textViewRemainingPointsPlayer1, textViewRemainingPointsPlayer2, player1, player2);
                        setTextViewPlayerIsPlaying(textViewPlayerIsPlaying, player1, player2);
                        setTextViewClickedPoints(clickedPointOne, clickedPointTwo, clickedPointThree);
                    }
                }
            }
        });

        ////// players
        //// player1
        // name
        TextView textViewPlayer1 = (TextView) findViewById(R.id.textView_namePlayer1);
        textViewPlayer1.setText(player1.getPlayerName() + ": ");
        // remaining points
        //TextView textViewRemainingPointsPlayer1 = (TextView) findViewById(R.id.textView_remainingPointsPlayer1);
        textViewRemainingPointsPlayer1.setText(Integer.toString(player1.getRemainingPoints()));

        //// player2
        // name
        TextView textViewPlayer2 = (TextView) findViewById(R.id.textView_namePlayer2);
        textViewPlayer2.setText(player2.getPlayerName() + ": ");
        // remaining points
        //TextView textViewRemainingPointsPlayer2 = (TextView) findViewById(R.id.textView_remainingPointsPlayer2);
        textViewRemainingPointsPlayer2.setText(Integer.toString(player2.getRemainingPoints()));

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


    //// methods
    public void switchPlayerIsPlaying(Player player1, Player player2) {
        if (player1.isPlaying() == true && player2.isPlaying() == false) {
            player1.setPlaying(false);
            player2.setPlaying(true);
        } else if (player2.isPlaying() == true && player1.isPlaying() == false) {
            player2.setPlaying(false);
            player1.setPlaying(true);
        }
    }

    public void setTextViewClickedPoints(int clickedPointOne, int clickedPointTwo, int clickedPointThree) {
        TextView textViewClickedPoints = (TextView) findViewById(R.id.textView_clickedPoints);
        textViewClickedPoints.setText(Integer.toString(clickedPointOne) + " - "
                        + Integer.toString(clickedPointTwo) + " - "
                        + Integer.toString(clickedPointThree)
        );
    }

    public void setTextViewPlayerIsPlaying(TextView textView, Player player1, Player player2) {
        if (player1.isPlaying() == true) {
            textView.setText(player1.getPlayerName() + " is playing.");
        } else if (player2.isPlaying()) {
            textView.setText(player2.getPlayerName() + " is playing.");
        }
    }

    public void setTextViewRemainingPoints(TextView textViewPlayer1, TextView textViewPlayer2, Player player1, Player player2) {
        textViewPlayer1.setText(Integer.toString(player1.getRemainingPoints()));
        textViewPlayer2.setText(Integer.toString(player2.getRemainingPoints()));
    }

    public void setClickedPointsToZero() {
        clickedPointOne = 0;
        clickedPointTwo = 0;
        clickedPointThree = 0;
    }

    public void setClickCounterToZero() {
        clickCounter = 0;
    }

    public int calculateGameAction(int clickedPointOne, int clickedPointTwo, int clickedPointThree) {
        int gameAction = clickedPointOne + clickedPointTwo + clickedPointThree;
        return gameAction;
    }

    public void MakeToastTooManyPoints() {
        Toast.makeText(GameActivity.this, "Too many points!", Toast.LENGTH_SHORT).show();
    }

    public void startWinning(Player player1, Player player2) {
        if (player1.isWinning() == true) {
            winnerName = player1.getPlayerName();
        } else if (player2.isWinning() == true) {
            winnerName = player2.getPlayerName();
        }
        Intent intent = new Intent(GameActivity.this, GameOverActivity.class);
        intent.putExtra("winnerName", winnerName);
        startActivity(intent);
    }

}
