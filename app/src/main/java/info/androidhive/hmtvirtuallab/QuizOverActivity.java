package info.androidhive.hmtvirtuallab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class QuizOverActivity extends AppCompatActivity {

    CircularProgressBar circularProgressBar;
    TextView resultTv;
    int correct, wrong;
    TextView exitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title.
        getSupportActionBar().hide(); //hide the title bar.
        setContentView(R.layout.activity_quiz_over);

        exitBtn = findViewById(R.id.ic_exit);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        correct = getIntent().getIntExtra("correct",0);
        wrong = getIntent().getIntExtra("wrong",0);

        circularProgressBar = findViewById(R.id.circularProgressBar);
        resultTv = findViewById(R.id.resultText);

        circularProgressBar.setProgressWithAnimation(correct,2000L);
        String resultStr = correct+"/10";
        resultTv.setText(resultStr);
    }

    public void restartClicked(View view) {
        finish();
    }
}