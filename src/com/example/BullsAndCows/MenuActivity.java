package com.example.BullsAndCows;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.button_start:
                intent = new Intent(MenuActivity.this, NewGameActivity.class);
                startActivity(intent);
                break;
            case R.id.button_about:
                intent = new Intent(MenuActivity.this, AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.button_exit:
                System.exit(0);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menulayout);
    }
}
