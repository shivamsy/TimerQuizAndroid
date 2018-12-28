package com.example.shivam.timerquiz;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    TextView timer, question, score, msg;
    Button option1, option2, option3, option4, playAgain, start;
    ConstraintLayout gameStart;
    GridLayout gridLayout;
    int score1=0, answer, total=0;
    ArrayList<Integer> options = new ArrayList<Integer>();
    boolean isActive = false;

    public void start(View view) {

        start.setVisibility(View.INVISIBLE);
        gameStart.setVisibility(View.VISIBLE);
        playAgain(question);
    }

    public void onAnswer(View view) throws InterruptedException {

        if(isActive) {
            int option = Integer.parseInt(view.getTag().toString());
            msg.setVisibility(View.VISIBLE);
            if (option == answer) {
                score1++;
                total++;
                msg.setText("CORRECT!");
                msg.setTextColor(Color.GREEN);
            } else {
                total++;
                msg.setText("INCORRECT!");
                msg.setTextColor(Color.RED);
            }
            score.setText(Integer.toString(score1) + "/" + Integer.toString(total));
            newQuestion();
        }
        else
            Toast.makeText(this, "Game Over!!!", Toast.LENGTH_SHORT).show();
    }

    public void newQuestion() {

        options.clear();

        Random random = new Random();

        int a = random.nextInt(21);
        int b = random.nextInt(21);

        Log.i("numbers:", Integer.toString(a)+"  +   "+Integer.toString(b) );

        question.setText(Integer.toString(a)+"  +   "+Integer.toString(b));
        answer = random.nextInt(4);

        for (int i=0; i<4; i++) {

            if (i==answer)
                options.add(a+b);
            else {
                int wronganswer = random.nextInt(41);

                while (wronganswer == a+b)
                    wronganswer = random.nextInt(41);

                options.add(wronganswer);
            }
        }
        option1.setText(Integer.toString(options.get(0)));
        option2.setText(Integer.toString(options.get(1)));
        option3.setText(Integer.toString(options.get(2)));
        option4.setText(Integer.toString(options.get(3)));
    }

    public void disableButtons(GridLayout layout) {

        // Get all touchable views
        ArrayList<View> layoutButtons = layout.getTouchables();

        // loop through them, if they are an instance of Button, disable it.
        for(View v : layoutButtons){
            if( v instanceof Button ) {
                ((Button)v).setEnabled(false);
            }
        }
    }


    public void playAgain(View view) {

        total = 0;
        score1 = 0;
        score.setText("0/0");
        timer.setText("30s");
        msg.setVisibility(View.INVISIBLE);
        playAgain.setVisibility(View.INVISIBLE);
        gridLayout = findViewById(R.id.options);
        isActive = true;

        newQuestion();

        new CountDownTimer(30100, 1000) {

            public void onTick(long millisUntilDone) {

                if (millisUntilDone/1000 < 20 && millisUntilDone/1000 > 10) {
                    timer.setBackgroundColor(Color.YELLOW);
                    timer.setText(Long.toString(millisUntilDone / 1000) + "s");
                }
                else if ((millisUntilDone/1000) < 10) {
                    timer.setBackgroundColor(Color.RED);
                    timer.setText("0" + Long.toString(millisUntilDone / 1000) + "s");
                }
                else
                    timer.setText(Long.toString(millisUntilDone / 1000) + "s");
            }

            public void onFinish() {
                //disableButtons(gridLayout);
                isActive = false;
                playAgain.setVisibility(View.VISIBLE);
            }
        }.start();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = findViewById(R.id.button);
        gameStart = findViewById(R.id.gameLayout);
        question = findViewById(R.id.question);
        timer = findViewById(R.id.timer);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        msg = findViewById(R.id.msg);
        score = findViewById(R.id.score);
        msg.setVisibility(View.INVISIBLE);
        playAgain = findViewById(R.id.playAgain);
        playAgain.setVisibility(View.INVISIBLE);
        gameStart.setVisibility(View.INVISIBLE);

    }
}
