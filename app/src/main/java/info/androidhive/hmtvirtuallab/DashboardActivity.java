package info.androidhive.hmtvirtuallab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.sasank.roundedhorizontalprogress.RoundedHorizontalProgressBar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import info.androidhive.hmtvirtuallab.ModelClass;
import info.androidhive.hmtvirtuallab.R;

import static info.androidhive.hmtvirtuallab.MainActivity.exp1_list;
import static info.androidhive.hmtvirtuallab.MainActivity.exp2_list;
import static info.androidhive.hmtvirtuallab.MainActivity.exp3_list;
import static info.androidhive.hmtvirtuallab.MainActivity.exp4_list;
import static info.androidhive.hmtvirtuallab.MainActivity.exp5_list;


public class DashboardActivity extends AppCompatActivity {
    CountDownTimer countDownTimer;
    int timerValue = 10;
    int time = 10000;
    RoundedHorizontalProgressBar progressBar;
    ArrayList<ModelClass> listOfQ;
    ModelClass modelClass;
    int index = 0;

    TextView question, opA, opB, opC, opD;
    CardView qCard, oACard, oBCard, oCCard, oDCard;
    LinearLayout nextBtn;

    int correctCnt = 0;
    int wrongCnt = 0;

    TextView exitBtn;

    TextView ques_counter;
    CircularProgressBar ques_cpb;
    int questionsAttempted = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title.
        getSupportActionBar().hide(); //hide the title bar.
        setContentView(R.layout.activity_dashboard);

        progressBar = findViewById(R.id.quiz_timer);
        int expNo = getIntent().getIntExtra("exp",1);
        switch (expNo){
            case 1:
                listOfQ = exp1_list;
                break;
            case 2:
                listOfQ = exp2_list;
                break;
            case 3:
                listOfQ = exp3_list;
                break;
            case 4:
                listOfQ = exp4_list;
                break;
            default:
                listOfQ = exp5_list;
        }

        Collections.shuffle(listOfQ);
        modelClass = listOfQ.get(index);

        question = findViewById(R.id.card_question_tv);
        opA = findViewById(R.id.card_optionA);
        opB = findViewById(R.id.card_optionB);
        opC = findViewById(R.id.card_optionC);
        opD = findViewById(R.id.card_optionD);

        qCard = findViewById(R.id.card_question);
        oACard = findViewById(R.id.cardA);
        oBCard = findViewById(R.id.cardB);
        oCCard = findViewById(R.id.cardC);
        oDCard = findViewById(R.id.cardD);

        ques_cpb = findViewById(R.id.counter_cpb);
        ques_counter = findViewById(R.id.counter_tv);

        exitBtn = findViewById(R.id.ic_exit);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                countDownTimer.cancel();
            }
        });

        nextBtn = findViewById(R.id.nextBtn);
        nextBtn.setClickable(false);
        setAllData();

        countDownTimer = new CountDownTimer(time,1000) {
            @Override
            public void onTick(long l) {
                timerValue -= 1;
                progressBar.setProgress(timerValue);
            }

            @Override
            public void onFinish() {
                Dialog dialog = new Dialog(DashboardActivity.this, R.style.DialogueStyle);
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                dialog.setContentView(R.layout.time_out_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                dialog.findViewById(R.id.tryAgain).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
                disableButton();
                nextBtn.setClickable(false);
                dialog.show();
                Log.v("quiz","Timer Khatamm....");
            }
        };
        countDownTimer.start();
    }



    private void setAllData() {
        question.setText(modelClass.getQuestion());
        opA.setText(modelClass.getoA());
        opB.setText(modelClass.getoB());
        opC.setText(modelClass.getoC());
        opD.setText(modelClass.getoD());
        ques_cpb.setProgressWithAnimation(questionsAttempted,1000L);
        ques_counter.setText(""+questionsAttempted);
    }

    public void correct(CardView cardView){
        cardView.setBackgroundColor(getResources().getColor(R.color.green));
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correctCnt++;
                questionsAttempted++;
                if(index < listOfQ.size()-1){
                    index++;
                    modelClass = listOfQ.get(index);
                    setAllData();
                    resetColor();
                    enableButton();
                    time = 10000;
                    timerValue = 10;
                    countDownTimer.cancel();
                    countDownTimer.start();
                    nextBtn.setClickable(false);
                }
                else {
                    countDownTimer.cancel();
                    quizOver();
                    finish();
                }
            }
        });
    }
    public void wrong(CardView card){
        card.setBackgroundColor(getResources().getColor(R.color.red));
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wrongCnt++;
                questionsAttempted++;
                if(index < listOfQ.size()-1){
                    index++;
                    modelClass = listOfQ.get(index);
                    setAllData();
                    resetColor();
                    enableButton();
                    time = 10000;
                    timerValue = 10;
                    countDownTimer.cancel();
                    countDownTimer.start();
                    nextBtn.setClickable(false);
                }else {
                    countDownTimer.cancel();
                    quizOver();
                    finish();
                }
            }
        });
    }

    private void quizOver() {
        Intent intent = new Intent(DashboardActivity.this, QuizOverActivity.class);
        intent.putExtra("correct", correctCnt);
        intent.putExtra("wrong", wrongCnt);
        startActivity(intent);
    }
    public void enableButton(){
        oACard.setClickable(true);
        oBCard.setClickable(true);
        oCCard.setClickable(true);
        oDCard.setClickable(true);
    }
    public void disableButton(){
        oACard.setClickable(false);
        oBCard.setClickable(false);
        oCCard.setClickable(false);
        oDCard.setClickable(false);
    }
    public void resetColor(){
        oACard.setBackgroundColor(getResources().getColor(R.color.primary_light));
        oBCard.setBackgroundColor(getResources().getColor(R.color.primary_light));
        oCCard.setBackgroundColor(getResources().getColor(R.color.primary_light));
        oDCard.setBackgroundColor(getResources().getColor(R.color.primary_light));

    }

    public void onClickOpA(View view) {
        disableButton();
        markCorrectAns();
        nextBtn.setClickable(true);
        if(modelClass.getoA().equals(modelClass.getAns())){
            oACard.setBackgroundColor(getResources().getColor(R.color.green));
            correct(oACard);
        }
        else{
            wrong(oACard);
        }
    }

    public void onClickOpB(View view) {
        disableButton();
        markCorrectAns();
        nextBtn.setClickable(true);
        if(modelClass.getoB().equals(modelClass.getAns())){
            oBCard.setBackgroundColor(getResources().getColor(R.color.green));
            correct(oBCard);
        }
        else{
            wrong(oBCard);
        }
    }

    public void onClickOpC(View view) {
        disableButton();
        markCorrectAns();
        nextBtn.setClickable(true);
        if(modelClass.getoC().equals(modelClass.getAns())){
            oCCard.setBackgroundColor(getResources().getColor(R.color.green));
            correct(oCCard);
        }
        else{
            wrong(oCCard);
        }
    }

    public void onClickOpD(View view) {
        disableButton();
        markCorrectAns();
        nextBtn.setClickable(true);
        if(modelClass.getoD().equals(modelClass.getAns())){
            oDCard.setBackgroundColor(getResources().getColor(R.color.green));
            correct(oDCard);
        }
        else{
            wrong(oDCard);
        }
    }

    private void markCorrectAns() {
        if(modelClass.getoA().equals(modelClass.getAns())){
            oACard.setBackgroundColor(getResources().getColor(R.color.green));
        }else if(modelClass.getoB().equals(modelClass.getAns())){
            oBCard.setBackgroundColor(getResources().getColor(R.color.green));
        }else if(modelClass.getoC().equals(modelClass.getAns())){
            oCCard.setBackgroundColor(getResources().getColor(R.color.green));
        }else{
            oDCard.setBackgroundColor(getResources().getColor(R.color.green));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        countDownTimer.cancel();
    }

}