package info.androidhive.hmtvirtuallab;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

import io.github.sidvenu.mathjaxview.MathJaxView;

public class ThermalConductivityOfLiquids extends AppCompatActivity {

    TextView obj_tv;
    TextView aim_tv;
    MathJaxView intro_tv;
    MathJaxView theory_tv;

    ImageView simulation_iv;
    View powerBtn;
    boolean POWER_ON = false;
    TextView temp_title_tv;
    TextView set_value_title_tv;
    TextView set_value_tv;
    int i=0;

    TextView timer_tv;
    FloatingActionButton startBtn, pauseBtn, resetBtn;
    Handler customHandler = new Handler();
    long startTime = 0L, timeInMillis = 0L, updateTime = 0L, millisPassed = 0L;
    boolean isRunning;
    boolean wasRunning;

    String tex = "<p align=\"justify\" style = \"font-family: Arial Rounded MT; font-size: 18px; font-style:bold; font-weight: 400;color:#707070\">\n"+
            "Inline formula:" +
            " $ax^2 + bx + c = 0$ " +
            "or displayed formula: $$\\sum_{i=0}^n i^2 = \\frac{(n^2+n)(2n+1)}{6}$$"+
            "</p>\n";
    String objective_text = "To study the heat transfer through liquids.";
    String aim_text = "To calculate the thermal conductivity of a liquid.";
    String intro_text = "<p align=\"justify\" style = \"font-family: Arial Rounded MT; font-size: 18px; font-style:bold; font-weight: 400;color:#707070\">\n"+
            "$\\bullet$ Energy is transferred from a region of high temperature to a region of low temperature.\n"+"<br>"+
            "<br>$\\bullet$ The rate of heat conduction $q$ through a medium depends on the geometry of the medium, thickness of the medium, the material of the medium, as well as the temperature difference across the medium.\n</br>" +
            "<br>$\\bullet$ The rate of heat conduction through a plane layer is proportional to the temperature difference across the layer "+"($\\Delta T$)"+
            "and the heat transfer area($A$), and is inversely proportional to the thickness of the layer ($\\Delta X$).\n" +
            "$$q \\propto A \\frac{\\Delta T}{\\Delta X}$$" +
            "After inserting the proportionality constant,\n" +
            "$$q = -kA\\frac{\\Delta T}{\\Delta X}$$\n" +
            "Where $q$ is the amount of heat transfer and $\\frac{\\Delta T}{\\Delta X}$ is the temperature gradient in the direction of heat flow. The constant $k$ is called thermal conductivity of the material.\n";
    String theory_text= "<p align=\"justify\" style = \"font-family: Arial Rounded MT; font-size: 18px; font-style:bold; font-weight: 400;color:#707070\">\n"+
            "$\\bullet$For thermal conductivity of liquids using Fourier's law, the heat flow through the liquid from hot fluid to cold fluid is the heat transfer through conductive fluid medium.\n<br>" +
            "<br>$\\bullet$At steady state, the average face temperatures are recorded ($T_h$ and $Tc$) along with the amount of heat transfer ($q$) knowing, the heat transfer area ($A$) and the thickness of the sample ($\\Delta X$) across which the heat transfer " +
            "takes place, the thermal conductivity of the sample can be calculated using Fourier's law of heat conduction.\n</br>" +
            "$$q = kA\\frac{T_h - T_c}{\\Delta X}$$\n" +
            "$$k = \\frac{q\\times\\Delta X}{A\\times(T_h - T_c)}$$";

    void openTheory() {
        obj_tv = findViewById(R.id.objective_text_tv);
        aim_tv = findViewById(R.id.aim_text_tv);
        intro_tv = findViewById(R.id.intro_text_tv);
        theory_tv = findViewById(R.id.theory_text_tv);

        obj_tv.setText(objective_text);
        aim_tv.setText(aim_text);
        intro_tv.setText(intro_text);
        theory_tv.setText(theory_text);
    }

    public void turnOnHeater(View v)
    {
//        Log.v("LOGGED MESSAGE", "POWER BUTTON CLICKED");
        if(!POWER_ON)
        {
            POWER_ON = true;
            simulation_iv.setImageResource(R.drawable.tcl_green_black);
            temp_title_tv.setVisibility(View.VISIBLE);
            set_value_title_tv.setVisibility(View.VISIBLE);
            set_value_tv.setVisibility(View.VISIBLE);
        }
        else
        {
            POWER_ON = false;
            simulation_iv.setImageResource(R.drawable.tcl_red);
            temp_title_tv.setVisibility(View.INVISIBLE);
            set_value_title_tv.setVisibility(View.INVISIBLE);
            set_value_tv.setVisibility(View.INVISIBLE);
        }
    }
    public void change_temp(View v)
    {
        if(POWER_ON) {
            i++;
            if(i==0) {
                temp_title_tv.setText("Temperature T1:");
                temp_title_tv.setVisibility(View.VISIBLE);
            }
            else if(i==1) {
                temp_title_tv.setText("Temperature T2:");
                temp_title_tv.setVisibility(View.VISIBLE);
            }
            else if(i==2) {
                temp_title_tv.setText("Temperature T3:");
                temp_title_tv.setVisibility(View.VISIBLE);
            }
            else {
                temp_title_tv.setText("Temperature T4:");
                temp_title_tv.setVisibility(View.VISIBLE);
                i = -1;
            }
       }
    }

    Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            timeInMillis = SystemClock.uptimeMillis() - startTime;
            updateTime = timeInMillis + millisPassed;
            int secs = (int)(updateTime/1000);
            int mins = secs/60;
            secs %= 60;
            int millis = (int)(updateTime%1000);
            millis /= 10;
            String dispTime = ""+mins+":"+String.format(Locale.getDefault(), "%02d",secs) + ":" +
                                                            String.format(Locale.getDefault(), "%02d", millis);
            timer_tv.setText(dispTime);
            customHandler.postDelayed(this, 0);
        }
    };

    void openSimulation()
    {
        simulation_iv = findViewById(R.id.simul_setup);
        powerBtn = findViewById(R.id.power_button);
        temp_title_tv = findViewById(R.id.temperature_title_tv);
        set_value_title_tv = findViewById(R.id.set_value_title_tv);
        set_value_tv = findViewById(R.id.set_value_tv);
        timer_tv = findViewById(R.id.timer_tv);
        startBtn = findViewById(R.id.start_btn);
        pauseBtn = findViewById(R.id.pause_btn);
        resetBtn = findViewById(R.id.reset_btn);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isRunning) {
                    isRunning = true;
                    startTime = SystemClock.uptimeMillis();
                    customHandler.postDelayed(updateTimerThread, 0);
                }
            }
        });
        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wasRunning = isRunning;
                isRunning = false;
                Log.v("LOGGED MESSAGE", ""+millisPassed);
                if(wasRunning)
                    millisPassed = updateTime;

                customHandler.removeCallbacks(updateTimerThread);
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRunning = false;
                wasRunning = false;
                millisPassed = 0L;
                String def = "0:00:00";
                timer_tv.setText(def);
                customHandler.removeCallbacks(updateTimerThread);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thermal_conductivity_of_liquids);

        Intent intent = getIntent();
        String viewClicked = intent.getStringExtra("clickedViewTag");
        if(viewClicked!= null && viewClicked.equals("theory"))
        {
            setTitle("Theory");
            setContentView(R.layout.theory_layout);
            openTheory();
        }
        else if(viewClicked != null && viewClicked.equals("aboutSetup"))
        {
            setTitle("About Setup");
            setContentView(R.layout.about_setup_layout);
        }
        else if(viewClicked != null && viewClicked.equals("simulation"))
        {
            setTitle("Simulation");
            setContentView(R.layout.simulation_layout);
            openSimulation();
        }
    }
}