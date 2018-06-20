package com.summerland.android.twozerofoureightapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static private float MIN_DISTANCE = 200;
    private float x1 = 0, y1 = 0, x2 = 0, y2 = 0;
    private TwoZeroFourEight game;
    private int[] cells = new int[16];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cells = new int[16];
        cells[0] = R.id.index0;
        cells[1] = R.id.index1;
        cells[2] = R.id.index2;
        cells[3] = R.id.index3;
        cells[4] = R.id.index4;
        cells[5] = R.id.index5;
        cells[6] = R.id.index6;
        cells[7] = R.id.index7;
        cells[8] = R.id.index8;
        cells[9] = R.id.index9;
        cells[10] = R.id.index10;
        cells[11] = R.id.index11;
        cells[12] = R.id.index12;
        cells[13] = R.id.index13;
        cells[14] = R.id.index14;
        cells[15] = R.id.index15;

        Log.i("Logm", "Inside on create main activity method");

        if (savedInstanceState != null) {
            TwoZeroFourEight tmpGame = (TwoZeroFourEight) savedInstanceState.getSerializable("GAME_KEY");
            if (tmpGame != null) {
                game = tmpGame;
                game.rePlot();
            }
        } else {
            game = new TwoZeroFourEight();
        }
        paintTiles();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.i("Logm", "Inside onSaveInstanceState method");
        outState.putSerializable("GAME_KEY", game);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i("Logm", "Inside onRestoreInstanceState method");
        if (savedInstanceState != null) {
            TwoZeroFourEight tmpGame = (TwoZeroFourEight) savedInstanceState.getSerializable("GAME_KEY");
            if (tmpGame != null) {
                game = tmpGame;
                game.rePlot();
            }
        }
        paintTiles();
    }

    public void onClick(View view) {
        Log.i("Logm", "Inside on click main activity action method");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // onTouchEvent () method gets called when User performs any touch event on screen
    // Method to handle touch event like left to right swap and right to left swap
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            // when user first touches the screen we get x and y coordinate
            case MotionEvent.ACTION_DOWN: {
                x1 = event.getX();
                y1 = event.getY();
                break;
            }
            case MotionEvent.ACTION_UP: {
                x2 = event.getX();
                y2 = event.getY();

                //if left to right sweep event on screen
                if (x1 < x2 && x2 - x1 > MIN_DISTANCE) {
                    //Toast.makeText(this, "Left to Right Swap Performed", Toast.LENGTH_SHORT).show();
                    if (game.actionMoveRight()) game.addNewTile();
                    paintTiles();
                }

                // if right to left sweep event on screen
                if (x1 > x2 && x1 - x2 > MIN_DISTANCE) {
                    //Toast.makeText(this, "Right to Left Swap Performed", Toast.LENGTH_SHORT).show();
                    if (game.actionMoveLeft()) game.addNewTile();
                    paintTiles();
                }

                // if UP to Down sweep event on screen
                if (y1 < y2 && y2 - y1 > MIN_DISTANCE) {
                    //Toast.makeText(this, "UP to Down Swap Performed", Toast.LENGTH_SHORT).show();
                    if (game.actionMoveDown()) game.addNewTile();
                    paintTiles();
                }

                //if Down to UP sweep event on screen
                if (y1 > y2 && y1 - y2 > MIN_DISTANCE) {
                    //Toast.makeText(this, "Down to UP Swap Performed", Toast.LENGTH_SHORT).show();
                    if (game.actionMoveUp()) game.addNewTile();
                    paintTiles();
                }
                break;
            }
        }
        return super.onTouchEvent(event);
    }

    private void paintTiles() {

        if (game.getMaxTile() >= TwoZeroFourEight.TARGET) {
            Toast.makeText(this, "YOU HAVE WON: " + game.getMaxTile(), Toast.LENGTH_LONG).show();
        } else if (!game.hasMovesRemaining()) {
            Toast.makeText(this, "SORRY, NO MORE MOVES.  PLAY AGAIN?", Toast.LENGTH_LONG).show();
        }

        Log.i("Logm", game.toString());

        TextView tv;
        tv = (TextView) findViewById(R.id.score);
        tv.setText("Score: " + game.getScore());

        ArrayList<TwoZeroFourEight.Transition> transitions = game.getTransitions();
        if (transitions == null || transitions.size() == 0) return;

        for (TwoZeroFourEight.Transition trans : transitions) {
            paintTransition(trans);
        }

/*        for (int i = 0; i < TwoZeroFourEight.GRID_CNT; i++) {
            if (game.getValue(i) == 0)
                paintCell(findViewById(cells[i]), game.getValue(i));
        }*/

        game.createNewTransitions();

        /*paintCell(findViewById(R.id.index0), game.getValue(0));
        paintCell(findViewById(R.id.index1), game.getValue(1));
        paintCell(findViewById(R.id.index2), game.getValue(2));
        paintCell(findViewById(R.id.index3), game.getValue(3));
        paintCell(findViewById(R.id.index4), game.getValue(4));
        paintCell(findViewById(R.id.index5), game.getValue(5));
        paintCell(findViewById(R.id.index6), game.getValue(6));
        paintCell(findViewById(R.id.index7), game.getValue(7));
        paintCell(findViewById(R.id.index8), game.getValue(8));
        paintCell(findViewById(R.id.index9), game.getValue(9));
        paintCell(findViewById(R.id.index10), game.getValue(10));
        paintCell(findViewById(R.id.index11), game.getValue(11));
        paintCell(findViewById(R.id.index12), game.getValue(12));
        paintCell(findViewById(R.id.index13), game.getValue(13));
        paintCell(findViewById(R.id.index14), game.getValue(14));
        paintCell(findViewById(R.id.index15), game.getValue(15));*/
    }

    private void paintTransition(TwoZeroFourEight.Transition trans) {

        TextView tv = (TextView) findViewById(cells[trans.posFinal]);

        if (trans.type == TwoZeroFourEight.actions.COMPACT) {
            Log.i("Logm", "About to do paint compact");
            int w = tv.getWidth();
            int h = tv.getHeight();
            float t = tv.getTextSize();
            Log.i("Logm", "About to do paint compact w=" + w + " h=" + h + " t=" + t);
            try {
                //tv.setShadowLayer(3, 4, Color.GREEN);
                //tv.setPressed(true);
                //tv.setHeight(h+5);
                //tv.setTextSize(getResources().getDimension(R.dimen.tile_txt_compacted));
                //tv.setShadowLayer(3, 3, 3, getResources().getColor(R.color.t_Merge, null));
                paintCell(tv, trans.val);
                wait(500);
                //tv.setRotation(2);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                paintCell(tv, trans.val);
            }
            //tv.setWidth(w);
            //tv.setHeight(h);
            //tv.setTextSize(getResources().getDimension(R.dimen.tile_txt_normal));
            //tv.setPressed(false);
        } else {
            paintCell(tv, trans.val);
        }
    }

    private void paintCell(Object obj, int val) {

        TextView tv = (TextView) obj;

        if (val == 0) {
            tv.setText("");
        } else {
            tv.setText("" + val);
        }

        int tx_col = getResources().getColor(R.color.t_dark_text, null);
        int bg_col = getResources().getColor(R.color.t0_bg, null);

        switch (val) {
            case 2:
                bg_col = getResources().getColor(R.color.t2_bg, null);
                break;
            case 4:
                bg_col = getResources().getColor(R.color.t4_bg, null);
                break;
            case 8:
                tx_col = getResources().getColor(R.color.t_light_text, null);
                bg_col = getResources().getColor(R.color.t8_bg, null);
                break;
            case 16:
                tx_col = getResources().getColor(R.color.t_light_text, null);
                bg_col = getResources().getColor(R.color.t16_bg, null);
                break;
            case 32:
                tx_col = getResources().getColor(R.color.t_light_text, null);
                bg_col = getResources().getColor(R.color.t32_bg, null);
                break;
            case 64:
                tx_col = getResources().getColor(R.color.t_light_text, null);
                bg_col = getResources().getColor(R.color.t64_bg, null);
                break;
            case 128:
                tx_col = getResources().getColor(R.color.t_light_text, null);
                bg_col = getResources().getColor(R.color.t128_bg, null);
                break;
            case 256:
                bg_col = getResources().getColor(R.color.t256_bg, null);
                break;
            case 512:
                bg_col = getResources().getColor(R.color.t512_bg, null);
                break;
            case 1024:
                bg_col = getResources().getColor(R.color.t1024_bg, null);
                break;
            case 2048:
                bg_col = getResources().getColor(R.color.t2048_bg, null);
                break;
            default:
                break;
        }
        tv.setBackgroundColor(bg_col);
        tv.setTextColor(tx_col);
    }
}
