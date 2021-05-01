package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView textViewTimer,textViewSum,textViewScore,textViewResult;
    Button button1,button2,button3,button4,playAgainButton;
    MediaPlayer mediaPlayer;
    ArrayList<Integer> answers = new ArrayList<Integer>();
    int locationOfCorrectAnswer;
    int score = 0;
    int numberOfQuestions = 0;

    public void playAgain(View view) {
        button1.setClickable(true);
        button2.setClickable(true);
        button3.setClickable(true);
        button4.setClickable(true);
        score = 0;
        numberOfQuestions = 0;
        textViewTimer.setText("30s");
        textViewScore.setText(Integer.toString(score)+"/"+Integer.toString(numberOfQuestions));
        newQuestion();
        playAgainButton.setVisibility(View.INVISIBLE);
        textViewResult.setText("");

        new CountDownTimer(30100,1000){

            @Override
            public void onTick(long millisUntilFinished) {

                Log.i("Timer",Long.toString(millisUntilFinished));
                textViewTimer.setText(Integer.toString((int) (millisUntilFinished / 1000))+"s");

            }

            @Override
            public void onFinish() {

                textViewResult.setText("Done");
                playAgainButton.setVisibility(View.VISIBLE);

                button1.setClickable(false);
                button2.setClickable(false);
                button3.setClickable(false);
                button4.setClickable(false);

            }
        }.start();
    }


    class RandomNumberGenerator {
        private int min = 5, max = 20;
        private List<Integer> randomList = new ArrayList<Integer>();

        private Random random = new Random();

        public int getNextRandomNumber() {

            int randomNum = random.nextInt((max - min) + 1) + min;
            if (randomList.contains(randomNum)) {
                randomNum = getNextRandomNumber();
            }
            randomList.add(randomNum);
            return randomNum;
        }

    }





    public void choseAnswer(View view) {
        if (Integer.toString(locationOfCorrectAnswer).equals(view.getTag().toString())) {
            textViewResult.setText("Correct!");
            mediaPlayer=MediaPlayer.create(getApplicationContext(),R.raw.correctsound);
            mediaPlayer.start();
            score++;
        } else {
            textViewResult.setText("Wrong :(");
            mediaPlayer=MediaPlayer.create(getApplicationContext(),R.raw.failsound);
            mediaPlayer.start();
        }
        numberOfQuestions++;
        textViewScore.setText(Integer.toString(score)+"/"+Integer.toString(numberOfQuestions));
        newQuestion();
    }
    public void newQuestion(){
        RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator();
        int  randomNum = randomNumberGenerator.getNextRandomNumber();
        int   randomNum1 = randomNumberGenerator.getNextRandomNumber();
        textViewSum.setText(randomNum + " + " + randomNum1);
        Random random=new Random();
        locationOfCorrectAnswer =random.nextInt(4) ;


answers.clear();

        for (int i=0; i<4; i++) {
            if (i == locationOfCorrectAnswer) {
                answers.add(randomNum+randomNum1);
            } else {
                int wrongAnswer = randomNumberGenerator.getNextRandomNumber();

                while (wrongAnswer == randomNum+randomNum1) {
                    wrongAnswer = randomNumberGenerator.getNextRandomNumber();
                }

                answers.add(wrongAnswer);
            }

        }

        button1.setText(Integer.toString(answers.get(0)));
        button2.setText(Integer.toString(answers.get(1)));
        button3.setText(Integer.toString(answers.get(2)));
        button4.setText(Integer.toString(answers.get(3)));

    }






        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


            textViewTimer = findViewById(R.id.textViewTimer);
            textViewSum = findViewById(R.id.textViewSum);
            textViewScore = findViewById(R.id.textViewScore);
            textViewResult = findViewById(R.id.textViewResult);

            button1= findViewById(R.id.button1);
            button2= findViewById(R.id.button2);
            button3= findViewById(R.id.button3);
            button4= findViewById(R.id.button4);
            playAgainButton = findViewById(R.id.playagainButton);



            playAgain(findViewById(R.id.textViewResult));



    }
}