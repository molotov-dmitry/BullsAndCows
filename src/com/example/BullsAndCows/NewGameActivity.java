package com.example.BullsAndCows;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;

public class NewGameActivity extends GameNewGameActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        int[] numpickerIds = {R.id.newgame_numberPicker0, R.id.newgame_numberPicker1, R.id.newgame_numberPicker2, R.id.newgame_numberPicker3};
        GameNewGameActivity.numpickersvalues[0] = 0;
        GameNewGameActivity.numpickersvalues[1] = 1;
        GameNewGameActivity.numpickersvalues[2] = 2;
        GameNewGameActivity.numpickersvalues[3] = 3;
        super.onCreate(savedInstanceState, R.layout.newgamelayout, R.id.newgame_thinkof_label, numpickerIds);
    }

    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.newgame_thinkof_button:
                if (GameNewGameActivity.game.thinkOfNumber(numpickersvalues)) {
                    GameActivity.list.clear();
                    GameNewGameActivity.numpickersvalues[0] = 0;
                    GameNewGameActivity.numpickersvalues[1] = 1;
                    GameNewGameActivity.numpickersvalues[2] = 2;
                    GameNewGameActivity.numpickersvalues[3] = 3;
                    intent = new Intent(NewGameActivity.this, GameActivity.class);
                    startActivity(intent);
                    NewGameActivity.this.finish();
                } else {
                    thinkof_label.setText(getString(R.string.alert_digitsnotdifferent));
                    thinkof_label.setTextColor(ColorStateList.valueOf(0xffff0000));
                    numberserror = true;
                }
                break;
            case R.id.newgame_thinkof_random_button:
                if (GameNewGameActivity.game.thinkOfRandomNumber()) {
                    GameActivity.list.clear();
                    GameNewGameActivity.numpickersvalues[0] = 0;
                    GameNewGameActivity.numpickersvalues[1] = 1;
                    GameNewGameActivity.numpickersvalues[2] = 2;
                    GameNewGameActivity.numpickersvalues[3] = 3;
                    intent = new Intent(NewGameActivity.this, GameActivity.class);
                    startActivity(intent);
                    NewGameActivity.this.finish();
                }
                break;
        }
    }
}
