package com.nhpva.day7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    private TextView tvNumberScore;
    private TextView tvNumberHighScore;
    private ImageView imgReplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        tvNumberScore = findViewById(R.id.tv_number_score);
        tvNumberHighScore = findViewById(R.id.tv_number_high_score);
        imgReplay = findViewById(R.id.img_replay);
        Intent intent = getIntent();
        int score = intent.getIntExtra("score", 0);
        int highScore = intent.getIntExtra("high_score", 0);
        tvNumberScore.setText(String.valueOf(score));
        tvNumberHighScore.setText(String.valueOf(highScore));

        imgReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(GameOverActivity.this, GameActivity.class);
                startActivity(intent1);
            }
        });
    }
}