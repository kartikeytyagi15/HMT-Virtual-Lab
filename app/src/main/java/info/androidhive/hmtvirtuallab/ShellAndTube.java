package info.androidhive.hmtvirtuallab;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;
import java.util.Random;

import io.github.sidvenu.mathjaxview.MathJaxView;

public class ShellAndTube extends AppCompatActivity {
    TextView obj_tv;
    TextView aim_tv;
    TextView aim_title_tv;
    TextView intro_title_tv;
    TextView theory2_title;
    MathJaxView theory2_tv;
    MathJaxView intro_text_tv;
    MathJaxView theory_tv;

    ImageView setup_img;
    View start_btn;
    boolean POWER_ON = false;

    int set_number = 1;

    Menu menu;
    boolean menuVisible;
    TextView hotFlowTv;
    TextView coldFlowTv;
    double time_elapsed;
    CountDownTimer countDown;

    double t1 = 1;
    double t2 = 2;
    double t3 = 3;
    double t4 = 4;
    int temp_number = 1;


    TextView temp_title;
    TextView temp;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shell_and_tube);

        Intent intent = getIntent();
        String viewClicked = intent.getStringExtra("clickedViewTag");

        if (viewClicked != null && viewClicked.equals("theory")) {
            setTitle("Theory");
            setContentView(R.layout.theory_layout);
            openTheory();
        } else if (viewClicked != null && viewClicked.equals("aboutSetup")) {
            setTitle("About Setup");
            setContentView(R.layout.about_setup_layout);
            openAboutSetup();
        } else if (viewClicked != null && viewClicked.equals("simulation")) {
            setTitle("Simulation");
            setContentView(R.layout.st_simulation);
            openSimulation();
        } else if (viewClicked != null && viewClicked.equals("procedure")) {
            setTitle("Procedure");
            setContentView(R.layout.procedure_layout);
            openProcedure();
        } else if (viewClicked != null && viewClicked.equals("observationTable")) {
            setTitle("Observation Table");
            setContentView(R.layout.observation_layout);
            openObservation();
        } else if (viewClicked != null && viewClicked.equals("graphs")) {
            setTitle("Graphs");
            setContentView(R.layout.graphs_layout);
            openGraphs();
        } else if (viewClicked != null && viewClicked.equals("quiz")) {
            setTitle("Quiz");
            Intent intent1 = new Intent(ShellAndTube.this, DashboardActivity.class);
            intent1.putExtra("exp", 5);
            startActivity(intent1);
            finish();
        }

    }

    private void openProcedure() {

    }

    private void openGraphs() {

    }

    private void openObservation() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu_) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.flow_rate_options_menu, menu_);
        if (!menuVisible)
        {
            for (int i = 0; i < menu_.size(); i++)
                menu_.getItem(i).setVisible(false);
        }
        menu = menu_;
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.op1) {
            set_number = 1;
            setFlowRates();
//            Toast.makeText(this, "Flow rates set to 220 and 200", Toast.LENGTH_SHORT).show();
            return true;
        } else if (itemId == R.id.op2) {
            set_number = 2;
            setFlowRates();
//            Toast.makeText(this, "Flow rates set to 100 and 150", Toast.LENGTH_SHORT).show();
            return true;
        } else if (itemId == R.id.op3) {
            set_number = 3;
            setFlowRates();
//            Toast.makeText(this, "Flow rates set to 80 and 240", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setTemperature_tv() {
        if(temp_number == 1) {
            temp.setText(String.format(Locale.getDefault(),"%.2f", t1));
        } else if(temp_number ==2) {
            temp.setText(String.format(Locale.getDefault(),"%.2f", t2));
        } else if(temp_number ==3) {
            temp.setText(String.format(Locale.getDefault(),"%.2f", t3));
        } else{
            temp.setText(String.format(Locale.getDefault(),"%.2f", t4));
            temp_number = 0;
        }
    }

    double calculateT1_set1(double time){
        Random rand = new Random();
        double value = rand.nextDouble() / 10.0;
        if(rand.nextInt(2) == 0) //negative
            value *= -1;
        return -0.02000*time*time + 0.60000*time + 26.00000 + value;
    }
    double calculateT2_set1(double time){
        Random rand = new Random();
        double value = rand.nextDouble() / 10.0;
        if(rand.nextInt(2) == 0) //negative
            value *= -1;
        return 0.01500*time*time - 0.45000*time + 35.00000 + value;
    }

    double calculateT1_set2(double time){
        Random rand = new Random();
        double value = rand.nextDouble() / 10.0;
        if(rand.nextInt(2) == 0) //negative
            value *= -1;
        return -0.01500*time*time + 0.45000*time + 30.00000 + value;
    }
    double calculateT2_set2(double time){
        Random rand = new Random();
        double value = rand.nextDouble() / 10.0;
        if(rand.nextInt(2) == 0) //negative
            value *= -1;
        return -0.01000*time*time + 0.30000*time + 32.00000 + value;
    }

    double calculateT1_set3(double time){
        Random rand = new Random();
        double value = rand.nextDouble() / 10.0;
        if(rand.nextInt(2) == 0) //negative
            value *= -1;
        return 0.01500*time*time - 0.45000*time + 33.00000 + value;
    }
    double calculateT2_set3(double time){
        Random rand = new Random();
        double value = rand.nextDouble() / 10.0;
        if(rand.nextInt(2) == 0) //negative
            value *= -1;
        return 0.01000*time*time - 0.30000*time + 34.00000 + value;
    }

    void tempInit(){
        if(set_number == 1){
            t1 = 26.0;
            t2 = 35.0;
            t3 = 25.0;
            t4 = 38.0;
        }
        else if(set_number == 2){
            t1 = 30.0;
            t2 = 32.0;
            t3 = 25.0;
            t4 = 38.0;
        }
        else{
            t1 = 33.0;
            t2 = 34.0;
            t3 = 25.0;
            t4 = 48.0;
        }
    }

    public void onStart(View v){
        if(!POWER_ON){
            POWER_ON = true;
            tempInit();
            temp.setVisibility(View.VISIBLE);
            temp_title.setVisibility(View.VISIBLE);
            menu.findItem(R.id.menu_item_id).setEnabled(false);
            if(set_number == 1) {
                setup_img.setImageResource(R.drawable.st_green_1);
            }
            else if(set_number == 2){
                setup_img.setImageResource(R.drawable.st_green_2);
            }
            else{
                setup_img.setImageResource(R.drawable.st_green_3);
            }

            time_elapsed = 0;
            countDown = new CountDownTimer(600000, 500) {  // 10 minutes count down, updates every half second
                @Override
                public void onTick(long millisUntilFinished) {
                    time_elapsed += 0.5;
                    setTemperature_tv();
                    if(set_number == 1){
                        t1 = calculateT1_set1(time_elapsed/60.0);
                        t2 = calculateT2_set1(time_elapsed/60.0);
                    }
                    else if(set_number == 2){
                        t1 = calculateT1_set2(time_elapsed/60.0);
                        t2 = calculateT2_set2(time_elapsed/60.0);
                    }
                    else {
                        t1 = calculateT1_set3(time_elapsed / 60.0);
                        t2 = calculateT2_set3(time_elapsed / 60.0);
                    }

//                    Log.v("Shell And Tube", "t1 = "+ t1);
//                    Log.v("Shell And Tube", "t2 = "+ t2);
                }
                @Override
                public void onFinish() {
                    Log.v("Temp","Time over");
                }
            }.start();
        }
        else{
            POWER_ON = false;
            countDown.cancel();
            temp.setVisibility(View.INVISIBLE);
            temp_title.setVisibility(View.INVISIBLE);
            menu.findItem(R.id.menu_item_id).setEnabled(true);
            setup_img.setImageResource(R.drawable.st_red);
        }
    }

    void setFlowRates(){
        if(set_number == 1){
            hotFlowTv.setText("m(hot) = 220lph");
            coldFlowTv.setText("m(cold) = 200lph");
        }else if(set_number == 2){
            hotFlowTv.setText("m(hot) = 100lph");
            coldFlowTv.setText("m(cold) = 150lph");
        }else{
            hotFlowTv.setText("m(hot) = 80lph");
            coldFlowTv.setText("m(hot) = 240lph");
        }
    }

    public void change_temp(View view) {
        if(POWER_ON) {
            temp_number++;
            if(temp_number == 1){
                temp_title.setText("T1("+(char) 0x00B0+"C)");
                temp.setText(String.format(Locale.getDefault(),"%.2f", t1));
            }
            else if(temp_number == 2){
                temp_title.setText("T2("+(char) 0x00B0+"C)");
                temp.setText(String.format(Locale.getDefault(),"%.2f", t2));
            }
            else if(temp_number == 3){
                temp_title.setText("T3("+(char) 0x00B0+"C)");
                temp.setText(String.format(Locale.getDefault(),"%.2f", t3));
            }
            else{
                temp_title.setText("T4("+(char) 0x00B0+"C)");
                temp.setText(String.format(Locale.getDefault(),"%.2f", t4));
                temp_number = 0;
            }
        }
    }

    private void openSimulation() {
        menuVisible =true;
        invalidateOptionsMenu();
        setup_img = findViewById(R.id.setup_img);
        start_btn = findViewById(R.id.start_btn);
        hotFlowTv = findViewById(R.id.hot_flow_rate);
        coldFlowTv = findViewById(R.id.cold_flow_rate);
        temp = findViewById(R.id.temp_id);
        temp_title = findViewById(R.id.temp_title_id);
        setFlowRates();
    }

    private void openAboutSetup() {

    }

    private void openTheory() {
        obj_tv = findViewById(R.id.objective_text_tv);
        aim_tv = findViewById(R.id.aim_text_tv);
        aim_title_tv = findViewById(R.id.aim_title_tv);
        intro_title_tv = findViewById(R.id.intro_title_tv);
        intro_text_tv = findViewById(R.id.intro_text_tv);
        theory_tv = findViewById(R.id.theory_text_tv);
        theory2_title = findViewById(R.id.thoery2_title_tv);
        theory2_tv = findViewById(R.id.theory2_tv_id);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        countDown.cancel();
    }
}
