package com.nhpva.day7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener, Runnable {

    private static final long ROTATE_TIME = 200;
    private Button btnLeft, btnRight;
    private ImageView imgBall;
    private ImageView imgSquare;
    private ImageView imgStart;
    private TextView tvScore;
    private int degree = 0;
    private int squareColor = 0;
    private int ballColor = 0;
    private int score = 0;
    private static int highScore = 0;
    private Random random = new Random();
    private Handler handler = new Handler();
    private boolean isPlaying = true;
    private int fallDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initViews();
    }

    private void initViews() {
        btnLeft = findViewById(R.id.btn_left);
        btnRight = findViewById(R.id.btn_right);
        imgBall = findViewById(R.id.img_ball);
        imgSquare = findViewById(R.id.img_square);
        imgStart = findViewById(R.id.img_start);
        tvScore = findViewById(R.id.tv_score);
        btnLeft.setOnClickListener(this);
        btnRight.setOnClickListener(this);
        imgStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_left:
                turnLeft();
                break;
            case R.id.btn_right:
                turnRight();
                break;
            case R.id.img_start:
                startGame();
                break;
        }
    }

    private void startGame() {
        ballColor = 0;
        fallDuration = 2000;
        squareColor = 0;
        score = 0;
        degree = 0;
        Thread thread = new Thread(this);
        thread.start();
        imgStart.setEnabled(false);
        imgStart.setVisibility(View.INVISIBLE);
    }

    private void ballFallDown() {
        ballColor = random.nextInt(4);
        imgBall.setImageResource(R.drawable.ball_0 + ballColor);
        TranslateAnimation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_PARENT, 0,
                Animation.RELATIVE_TO_PARENT, 0.9f);
        if (score % 10 == 0 ){
            fallDuration -= 100;
        }
        animation.setDuration(fallDuration);
        imgBall.startAnimation(animation);
    }

    private void turnRight() {
        RotateAnimation animation = new RotateAnimation(degree, degree + 90, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(ROTATE_TIME);
        imgSquare.startAnimation(animation);
        animation.setFillAfter(true);
        degree += 90;
        squareColor--;
        if (squareColor < 0) {
            squareColor = 3;
        }
    }

    private void turnLeft() {
        RotateAnimation animation = new RotateAnimation(degree, degree - 90, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(ROTATE_TIME);
        imgSquare.startAnimation(animation);
        animation.setFillAfter(true);
        degree -= 90;
        squareColor++;
        if (squareColor > 3) {
            squareColor = 0;
        }
    }

    @Override
    public void run() {
        while (isPlaying){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    ballFallDown();
                }
            });
            ballFallDown();
            try {
                Thread.sleep(fallDuration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (ballColor == squareColor){
                score++;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        tvScore.setText(String.valueOf(score));
                    }
                });
            } else {
                isPlaying = false;
                if (highScore < score){
                    highScore = score;
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(GameActivity.this, GameOverActivity.class);
                        intent.putExtra("score", score);
                        intent.putExtra("high_score", highScore);
                        startActivity(intent);
                    }
                });
            }
        }
    }
}