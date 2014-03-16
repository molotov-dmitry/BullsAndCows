package com.example.BullsAndCows;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

public class GameActivity extends GameNewGameActivity {
    public static final ArrayList<String> list = new ArrayList<String>();
    private ArrayAdapter<String> adapter;

    private RatingBar bulls_bar;
    private RatingBar cows_bar;

    private ListView guesseslist;

    private LinearLayout pickerslayout;
    private LinearLayout guesseslayout;
    private LinearLayout mainlayout;

    public void win() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(300);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.win_title);
        alert.setMessage(R.string.win_text);
        alert.setPositiveButton(R.string.dlg_newgame, MyDialogClickListener);
        alert.setNeutralButton(R.string.dlg_exit, MyDialogClickListener);
        alert.setCancelable(false);
        alert.show();
    }

    public void lose() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(300);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.lose_title);
        alert.setMessage(
                getString(R.string.lose_text) +
                        "\n" +
                        getString(R.string.lose_answer) +
                        " " +
                        GameNewGameActivity.game.getAnswer()
        );
        alert.setPositiveButton(R.string.dlg_newgame, MyDialogClickListener);
        alert.setNeutralButton(R.string.dlg_exit, MyDialogClickListener);
        alert.setCancelable(false);
        alert.show();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.game_check_button:
                if (GameNewGameActivity.game.addGuess(GameNewGameActivity.numpickersvalues)) {
                    bulls_bar.setRating(GameNewGameActivity.game.getBulls(GameNewGameActivity.numpickersvalues));
                    cows_bar.setRating(GameNewGameActivity.game.getCows(GameNewGameActivity.numpickersvalues));

                    list.add(
                            String.valueOf(GameNewGameActivity.game.getIterationsCount()) +
                                    ": " +
                                    String.valueOf(GameNewGameActivity.numpickersvalues[0]) +
                                    String.valueOf(GameNewGameActivity.numpickersvalues[1]) +
                                    String.valueOf(GameNewGameActivity.numpickersvalues[2]) +
                                    String.valueOf(GameNewGameActivity.numpickersvalues[3])
                    );
                    adapter.notifyDataSetChanged();

                    if (GameNewGameActivity.game.checkGuess(GameNewGameActivity.numpickersvalues))
                        win();
                    else if (GameNewGameActivity.game.getIterationsCount() >= 10)
                        lose();
                } else {
                    if (GameNewGameActivity.game.alreadyGuessed(GameNewGameActivity.numpickersvalues))
                        thinkof_label.setText(getString(R.string.alert_alreadyguessed));
                    if (!GameNewGameActivity.game.allDigitsAreDifferent(GameNewGameActivity.numpickersvalues))
                        thinkof_label.setText(getString(R.string.alert_digitsnotdifferent));
                    thinkof_label.setTextColor(ColorStateList.valueOf(0xffff0000));
                    numberserror = true;
                }
                break;
        }
    }

    final DialogInterface.OnClickListener MyDialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int id) {
            Intent intent;
            switch (id) {
                case DialogInterface.BUTTON_POSITIVE:
                    intent = new Intent(GameActivity.this, NewGameActivity.class);
                    startActivity(intent);
                    GameActivity.this.finish();
                    break;
                case DialogInterface.BUTTON_NEUTRAL:
                    GameActivity.this.finish();
                    break;
            }
        }
    };

    final AdapterView.OnItemClickListener MyItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            bulls_bar.setRating(GameNewGameActivity.game.getBulls(GameNewGameActivity.game.getGuess(position)));
            cows_bar.setRating(GameNewGameActivity.game.getCows(GameNewGameActivity.game.getGuess(position)));

            for (int i = 0; i < numpickers.length; i++) {
                numpickers[i].setValue(GameNewGameActivity.game.getGuess(position)[i]);
                GameNewGameActivity.numpickersvalues[i] = (GameNewGameActivity.game.getGuess(position)[i]);
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        int[] numpickerIds = {R.id.game_numberPicker0, R.id.game_numberPicker1, R.id.game_numberPicker2, R.id.game_numberPicker3};
        super.onCreate(savedInstanceState, R.layout.gamelayout, R.id.game_guess_label, numpickerIds);

        //orientation change

        pickerslayout = (LinearLayout) findViewById(R.id.game_pickers_layout);
        guesseslayout = (LinearLayout) findViewById(R.id.game_guesses_layout);
        mainlayout = (LinearLayout) findViewById(R.id.game_main_layout);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mainlayout.setOrientation(LinearLayout.HORIZONTAL);
            guesseslayout.setOrientation(LinearLayout.VERTICAL);
            pickerslayout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.FILL_PARENT, 1.f));
            guesseslayout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.FILL_PARENT, 1.f));
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mainlayout.setOrientation(LinearLayout.VERTICAL);
            guesseslayout.setOrientation(LinearLayout.HORIZONTAL);
            pickerslayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 0, 1.f));
            guesseslayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 0, 1.f));
        }

        bulls_bar = (RatingBar) findViewById(R.id.game_bulls_bar);
        cows_bar = (RatingBar) findViewById(R.id.game_cows_bar);

        if (GameNewGameActivity.game.getIterationsCount() <= 0)
            bulls_bar.setRating(0);
        else
            bulls_bar.setRating(GameNewGameActivity.game.getBulls(GameNewGameActivity.game.getGuess(GameNewGameActivity.game.getIterationsCount() - 1)));

        if (GameNewGameActivity.game.getIterationsCount() <= 0)
            cows_bar.setRating(0);
        else
            cows_bar.setRating(GameNewGameActivity.game.getCows(GameNewGameActivity.game.getGuess(GameNewGameActivity.game.getIterationsCount() - 1)));

        guesseslist = (ListView) findViewById(R.id.game_guesses);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        guesseslist.setAdapter(adapter);
        guesseslist.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

        guesseslist.setOnItemClickListener(MyItemClickListener);
    }
}