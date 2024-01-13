package com.example.week1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int number;
    EditText guessText;
    Button tryButton;
    TextView hintText;
    TextView scoreText;
    LinearLayout history;
    ProgressBar progressBar;
    boolean isInLandscape = false;
    private void addToHistory(int guess){
        TextView _text = new TextView(this);
        _text.setText(String.valueOf(guess));
        if(guess == number)
            _text.setBackground(ContextCompat.getDrawable(this, R.drawable.textview_back_green));
        else if (guess < number)
            _text.setBackground(ContextCompat.getDrawable(this, R.drawable.textview_back));
        else  _text.setBackground(ContextCompat.getDrawable(this, R.drawable.textview_back_red));

        _text.setTextAppearance(R.style.GuessText);
        if(isInLandscape)
        {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0,0,0,10);
            _text.setLayoutParams(params);

        }
        else{
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.setMargins(0,0,10,0);
            _text.setLayoutParams(params);
        }
        _text.setGravity(Gravity.CENTER);
        _text.setPadding(20, 0, 20, 0);
        history.addView(_text);

    }

    private void endGame(boolean winner){
        guessText.setVisibility(View.GONE);
        tryButton.setVisibility(View.GONE);
        hintText.setVisibility(View.GONE);
        scoreText.setText(String.format("Score = %d", progressBar.getProgress() * 10));
        if(winner){
            scoreText.setTextAppearance(R.style.ResultTextSuccess);
        }else{
            scoreText.setTextAppearance( R.style.ResultTextFail);
        }
        scoreText.setVisibility(View.VISIBLE);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Random random = new Random();
        number = random.nextInt(100) + 1;
        guessText = findViewById(R.id.guessText);
        tryButton = findViewById(R.id.tryButton);
        hintText = findViewById(R.id.hintText);
        progressBar = findViewById(R.id.progressBar);
        scoreText = findViewById(R.id.scoreText);
        history = findViewById(R.id.historyView);
        progressBar.setProgress(10);
        guessText.setText("");
        guessText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hintText.setVisibility(View.GONE);
            }
        });
        tryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConstraintLayout r = findViewById(R.id.right_side);
                if(r!=null)
                    isInLandscape = true;
                int guess = Integer.parseInt(guessText.getText().toString());
                addToHistory(guess);
                if (number == guess){
                    hintText.setText("Success");
                    endGame(true);
                    return;
                    //Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                } else if (guess > number) {
                    hintText.setText("Go Down");
                    hintText.setTextColor(ContextCompat.getColor(view.getContext(), R.color.red));
                    //Toast.makeText(MainActivity.this, "Try again", Toast.LENGTH_SHORT).show();
                } else {
                    hintText.setText("Go Up");
                    hintText.setTextColor(ContextCompat.getColor(view.getContext(), R.color.teal_700));
                }
                hintText.setVisibility(View.VISIBLE);
                if(progressBar.getProgress() > 0)
                    progressBar.setProgress(progressBar.getProgress() -1);
                else
                    endGame(false);

            }
        });
    }
}