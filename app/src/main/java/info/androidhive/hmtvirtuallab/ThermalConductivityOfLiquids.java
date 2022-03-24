package info.androidhive.hmtvirtuallab;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import io.github.sidvenu.mathjaxview.MathJaxView;

import static android.content.Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP;

public class ThermalConductivityOfLiquids extends AppCompatActivity {

    TextView obj_tv;
    TextView aim_tv;
    MathJaxView intro_tv;
    MathJaxView theory_tv;
    MathJaxView rayleigh_tv;
    TextView rayleigh_title;

    TextView start_procedure;
    TextView close_procedure;
    TextView simul_procedure;

    ImageView simulation_iv;
    View powerBtn;
    boolean POWER_ON = false;
    TextView temp_title_tv;
    TextView set_value_title_tv;
    TextView set_value_tv;

    int thermocouple_number = 0;

    //Timer
    TextView timer_tv;
    FloatingActionButton startBtn, pauseBtn, resetBtn;
    Handler customHandler = new Handler();
    long startTime = 0L, timeInMillis = 0L, updateTime = 0L, millisPassed = 0L;
    boolean isRunning;
    boolean wasRunning;

    TextView temperature_tv;
    CountDownTimer countDown = null;
    double thermocouple_1 = 29.30;
    double thermocouple_2 = 30.4;
    double thermocouple_3 = 30.6;
    double thermocouple_4 = 30.1;
    double surface_temp = 29.0;
    double time_elapsed = 0;

    TextView  surf_temp_tv;
    TextView surf_temp_title_tv;
    int pulses = 4;

    int LED_count = 0;
    boolean LED_ON = false;
    double LED_time = 0;
    double LED_interval = 0;

    SQLiteDatabase db;
    TableLayout table;
    int numReadings = 0;
    TextView numReadings_tv;

    SharedPreferences sharedPref;

    Menu menu;
    boolean menuVisible;

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
            "$\\bullet$ For thermal conductivity of liquids using Fourier's law, the heat flow through the liquid from hot fluid to cold fluid is the heat transfer through conductive fluid medium.\n<br>" +
            "<br>$\\bullet$ At steady state, the average face temperatures are recorded ($T_h$ and $Tc$) along with the amount of heat transfer ($q$) knowing, the heat transfer area ($A$) and the thickness of the sample ($\\Delta X$) across which the heat transfer " +
            "takes place, the thermal conductivity of the sample can be calculated using Fourier's law of heat conduction.\n</br>" +
            "$$q = kA\\frac{T_h - T_c}{\\Delta X}$$\n" +
            "$$k = \\frac{q\\times\\Delta X}{A\\times(T_h - T_c)}$$";

    String rayleigh = "<p align=\"justify\" style = \"font-family: Arial Rounded MT; font-size: 18px; font-style:bold; font-weight: 400;color:#707070\">\n"+
            "$\\bullet$ In our experimental setup, there may be a density gradient in the direction of gravity, leading to formation of convection cells. This is not desired in our experiment as we want to consider only conduction heat transfer.\n<br>" +
            "<br>$\\bullet$ The gravitational force acting downwards is countered by the viscous damping forces in the fluid. The balance of the two forces is expressed by the Rayleigh Number, a dimensionless parameter.\n</br>" +
            "Rayleigh Number is defined as: \n" +
            "$$Ra_L = \\frac{g\\beta(T_h - T_c)L^3}{\\upsilon \\alpha}$$\n" +
            "where,<br>"+
            "$T_c$ is the temperature of the top plate\n<br>"+
            "$T_h$ is the temperature of the bottom plate\n<br>"+
            "$\\beta$ is the thermal expansion coefficient\n<br>"+
            "$\\alpha$ is the thermal diffusivity\n<br>"+
            "$\\upsilon$ is the kinematic viscosity\n<br>"+
            "$L$ is the height of container\n<br>"+
            "<br>$\\bullet$ Gravitational force dominates for large Rayleigh Number and beyond a critical value (1708), convection cells start to generate.\n</br>" +
            "<br>$\\bullet$ Therefore, in our experiment, the dimensions and the fluid chosen (Glycerin) are such that the Rayleigh Number is less than the critical value, ensuring that the heat transferred is only due to conduction.\n</br>"+
            "<br>$\\bullet$ Now let's calculate the value of Rayleigh Number,<br>"+
            "<br>We will measure all the values at mean steady state temperature i.e. at $\\frac{T_h + T_c}{2} = 321.6K$</br>"+
            "<br>$T_c = 310.3 K $<br>"+
            "$T_h = 333.15 K$<br>"+
            "$\\beta = 0.51\\times 10^{-3} K^{-1}$<br>"+
            "$\\alpha = 0.897\\times 10^{-7} \\frac{m^2}{s}$<br>"+
            "$\\upsilon = 1.51\\times 10^{-4} \\frac{m^2}{s}$<br>"+
            "$L = 0.001m$<br>"+
            "<p align=\"justify\" style = \"font-family: Arial Rounded MT; font-size: 13px; font-style:bold; font-weight: 400;color:#707070\">\n"+
            "$$Ra_L = \\frac{9.81\\times 0.51\\times 10^{-3} (333.15 - 310.3)0.001^3}{0.51\\times 10^{-4}\\times 0.897\\times 10^{-7}}$$\n" +
            "</p>"+
            "<p align=\"justify\" style = \"font-family: Arial Rounded MT; font-size: 18px; font-style:bold; font-weight: 400;color:#707070\">\n"+
            "$$Ra_L = 7.97$$" +
            "Hence, we can say that there is no effect of convection in our experiment.\n";


    String equipment_reqd = "<p align=\"justify\" style = \"font-family: Arial Rounded MT; font-size: 18px; font-style:bold; font-weight: 400;color:#707070\">\n"+
            "$\\bullet$ Electricity Supply: Single Phase, 220 V AC, 50 Hz, 5-15 Amp combined socket with earth connection.\n<br>" +
            "<br>$\\bullet$ Water Supply: Continuous @ 2 LPM at 1 Bar.\n</br>" +
            "<br>$\\bullet$ Floor drain required.\n</br>" +
            "<br>$\\bullet$ Bench area required: 1 m x 1 m.\n</br>" +
            "<br>$\\bullet$ Glycerin: 250 ml.\n</br>" +
            "<br>$\\bullet$ Stop watch.\n</br>";

    String apparatus_desc = "<p align=\"justify\" style = \"font-family: Arial Rounded MT; font-size: 18px; font-style:bold; font-weight: 400;color:#707070\">\n"+
            "$\\bullet$ The apparatus consists of a heater; it heats a thin layer of liquid. A cooling plate removes heat through the liquid layer, ensuring unidirectional heat flow.\n<br>" +
            "<br>$\\bullet$ A PID controller is provided for varying the input to the heater and measurement of input power is carried out by a digital energy meter and stopwatch. \n</br>" +
            "<br>$\\bullet$ Funnel is provided with a valve to fill the liquid. Overflow pipe is given to maintain the liquid level. Plate is for circulation of water. A valve is provided to control the flow of water. \n</br>" +
            "<br>$\\bullet$ Four temperature sensors are provided to measure the temperature across the liquid layer.\n</br>";

    String start_procedure_text="1. Close the valves V1-V2.\n\n" +
            "2. Connect continuous water supply to the inlet of water chamber.\n\n" +
            "3. Connect outlet of chamber to drain.\n\n" +
            "4. Open the valve V2.\n\n" +
            "5. Fill the liquid (whose thermal conductivity have to be measure) through funnel till the liquid retain in funnel.\n\n" +
            "6. Extra liquid will overflow from another given pipe to keep the liquid at axis level.\n\n" +
            "7. Ensure that mains ON/OFF switch given on the panel are at OFF position.\n\n" +
            "8. Connect electric supply to the set up.\n\n" +
            "9. Switch ON the mains ON / OFF switch.\n\n" +
            "10. Start the water supply and adjust the flow of water by valve V1. \n\n" +
            "11. Note down the ambient temperature readings.\n\n" +
            "12. Set the input for heater by PID, Set Value (SV) is in the range 40"+ (char) 0x00B0+"C "+ "to 80"+ (char) 0x00B0+"C " +"\n\n" +
            "13. After 1.5 hrs. Note down the reading of pulses and temperature sensors in the observation table after every 10 minutes interval till observing change in consecutive readings of temperatures (± 0.2" + (char) 0x00B0+"C)."+"\n\n" +
            "14. Repeat the experiment for different liquids. \n\n" +
            "15. Perform the experiment at different Set Value (SV)\n\n" +
            "16. Repeat the experiment with different flow rate. ";
    String close_procedure_text="1. When experiment is over switch OFF the heater switch.\n\n" +
            "2. Switch OFF the mains ON/OFF switch.\n\n" +
            "3. Switch OFF electric supply to the set up.\n\n" +
            "4. Stop flow of water by closing the valve V1";

    String simulation_procedure = "In this simulation, it is assumed that Glycerin is already filled in, and the water supply turns on automatically by switching on the ‘Heater ON/OFF Switch’. Also, the set value of the heater is set to 60" + (char) 0x00B0+"C "+ "and cannot be changed.\n\n" +
            "1. First of all, click on the three dots on the top right to open Pulses Menu. Select the number of pulses you want to count while performing the experiment. \n" +
            "(In case you skip this step, by default, number of pulses reflected in the Observation Table will be 4)\n\n" +
            "2. Turn on the heater by clicking on the ‘Heater ON/OFF Switch’.\n" +
            "(Notice that the Mains Indicator turns green indicating that the experiment has begun, and the water supply is started)\n\n" +
            "3. Note down the ambient temperature readings.\n\n" +
            "4. To switch between thermocouple temperatures, click on the ‘Multi Switch Channel’ button.\n\n" +
            "5. Now, to note down the readings, keep an eye on the ‘Energy Meter’ and wait for a red blink.\n\n" +
            "6. As soon as a blink is seen, start the timer by pressing on the play button. \n\n" +
            "7. Count the number of blinks you want to (preferably 3 or 4) and stop the timer when you see the last blink.\n\n" +
            "8. Once you stop the timer, click on the ‘RECORD’ button to record the reading in the Observation Table.\n" +
            "(Note that the number of readings taken count increases by one)\n\n" +
            "9. In about 10 minutes, repeat steps 5-8 for recording more readings.\n\n" +
            "10. When the observed change in consecutive readings is ±0.1, you can assume that the steady state is reached. \n\n" +
            "11. Now, you can go back from the Simulation and open Observation Table to view the readings that were recorded. Optionally, you can download the data in form of an excel file(.xlsx) to analyze by yourself.\n\n" +
            "The downloaded file goes in your Android Device’s internal storage with the following path:\n" +
            "Internal Storage->Documents->HMT Virtual Lab->TCL Table\n";


    void openTheory() {
        obj_tv = findViewById(R.id.objective_text_tv);
        aim_tv = findViewById(R.id.aim_text_tv);
        intro_tv = findViewById(R.id.intro_text_tv);
        theory_tv = findViewById(R.id.theory_text_tv);
        rayleigh_tv = findViewById(R.id.theory2_tv_id);
        rayleigh_title = findViewById(R.id.thoery2_title_tv);

        obj_tv.setText(objective_text);
        aim_tv.setText(aim_text);
        intro_tv.setText(intro_text);
        theory_tv.setText(theory_text);
        rayleigh_tv.setText(rayleigh);
        rayleigh_title.setText("RAYLEIGH BENARD INSTABILITY:");
    }
    void openProcedure() {
        start_procedure = findViewById(R.id.start);
        close_procedure = findViewById(R.id.close);
        simul_procedure = findViewById(R.id.simulation_procedure_id);
        start_procedure.setText(start_procedure_text);
        close_procedure.setText(close_procedure_text);
        simul_procedure.setText(simulation_procedure);
    }

    public double calculateTemp1(double time)
    {
        Random rand = new Random();
        double value = rand.nextDouble() / 2.0;
        if(rand.nextInt(2) == 0) //negative
            value *= -1;
        return -2.08648972851097E-05*Math.pow(time,4) + 2.23147779876687E-03*Math.pow(time,3) -
                                        8.28851482252020E-02*time*time + 1.27507832940478E+00*time + 2.94202396330543E+01 + value;
    }
    public double calculateTemp2(double time)
    {
        Random rand = new Random();
        double value = rand.nextDouble() / 2.0;
        if(rand.nextInt(2) == 0) //negative
            value *= -1;
        return -1.29260809317161E-05*Math.pow(time,4) + 1.39416210149346E-03*Math.pow(time,3) -
                        5.26402023596546E-02*time*time + 8.39101752575061E-01*time + 3.05444908761908E+01 + value;
    }
    public double calculateTemp3(double time)
    {
        Random rand = new Random();
        double value = rand.nextDouble() / 2.0;
        if(rand.nextInt(2) == 0) //negative
            value *= -1;
        return -1.46274811305622E-05*Math.pow(time,4) + 1.68042637439569E-03*Math.pow(time,3) -
            6.86649136093251E-02*time*time + 1.19281005038874E+00*time + 3.07688267375543E+01 + value;
    }
    public double calculateTemp4(double time)
    {
        Random rand = new Random();
        double value = rand.nextDouble() / 2.0;  //random value generated between 0 and 0.5 (about 1.5% of 35)
        if(rand.nextInt(2) == 0) //negative
            value *= -1;
        return -1.55656292610759E-05*Math.pow(time,4) + 1.72549996510618E-03*Math.pow(time,3) -
                    6.71408402782134E-02*time*time + 1.09657322125850E+00*time + 3.02488660476099E+01 + value;
    }

    public double calculatePulses(double time){
        return 3.41253344420811E-05*Math.pow(time,4) - 3.92624735874136E-03*Math.pow(time,3) +
                1.64034123337430E-01*time*time - 3.04057480429492E+00*time + 6.08993773891020E+01;
    }

    public double calculateSurfaceTemp(double time){
        return -0.000009164195484*Math.pow(time,4) +0.00177864349*Math.pow(time,3) -
                0.116357812741*time*time + 3.161911615294*time +28.98745141703;
    }

    public void setTemperature_tv() {
        if(thermocouple_number == 0) {
            temperature_tv.setText(String.format(Locale.getDefault(),"%.2f", thermocouple_1));
        }
        else if(thermocouple_number ==1) {
            temperature_tv.setText(String.format(Locale.getDefault(),"%.2f", thermocouple_2));
        }
        else if(thermocouple_number ==2) {
            temperature_tv.setText(String.format(Locale.getDefault(),"%.2f", thermocouple_3));
        }
        else {
            temperature_tv.setText(String.format(Locale.getDefault(),"%.2f", thermocouple_4));
            thermocouple_number = -1;
        }
    }

    public void turnOnHeater(View v)
    {
//        Log.v("LOGGED MESSAGE", "POWER BUTTON CLICKED");
        if(!POWER_ON)
        {  //Power on
            POWER_ON = true;
            simulation_iv.setImageResource(R.drawable.tcl_green_black);
            temp_title_tv.setVisibility(View.VISIBLE);
            set_value_title_tv.setVisibility(View.VISIBLE);
            set_value_tv.setVisibility(View.VISIBLE);
            temperature_tv.setVisibility(View.VISIBLE);
            surf_temp_title_tv.setVisibility(View.VISIBLE);
            surf_temp_tv.setVisibility(View.VISIBLE);

            setTemperature_tv();

            time_elapsed = 0;
            countDown = new CountDownTimer(2400000, 500) {  //40 minutes count down, updates every half second
                @Override
                public void onTick(long millisUntilFinished) {
                    time_elapsed += 0.5;
                    setTemperature_tv();
                    surf_temp_tv.setText(String.format(Locale.getDefault(),"%.2f", surface_temp));
                    thermocouple_1 = calculateTemp1(time_elapsed / 60.0);
                    thermocouple_2 = calculateTemp2(time_elapsed / 60.0);
                    thermocouple_3 = calculateTemp3(time_elapsed / 60.0);
                    thermocouple_4 = calculateTemp4(time_elapsed / 60.0);
                    surface_temp = calculateSurfaceTemp(time_elapsed/60.0);

                    LED_time += 0.5;
                    if (LED_ON) {
                        LED_ON = false;
                        simulation_iv.setImageResource(R.drawable.tcl_green_black);
                        LED_time = 0.5;
                    } else {
                        if (LED_count == 0) {
                            LED_interval = calculatePulses(time_elapsed / 60.0) / 3;
                            LED_count++;
                        } else if (LED_count == 1) {
                            if (LED_time >= LED_interval) {
                                simulation_iv.setImageResource(R.drawable.tcl_green_red);
                                LED_ON = true;
                                LED_count++;
                            }
                        } else if (LED_count == 2) {
                            if (LED_time >= LED_interval) {
                                simulation_iv.setImageResource(R.drawable.tcl_green_red);
                                LED_ON = true;
                                LED_count++;
                            }
                        } else {
                            if (LED_time >= LED_interval) {
                                simulation_iv.setImageResource(R.drawable.tcl_green_red);
                                LED_ON = true;
                                LED_count = 0;
                            }
                        }
                    }
                }

                @Override
                public void onFinish() {
                    Log.v("Temp","time khatam :"+thermocouple_1);
                }
            }.start();
        }
        else
        {
            //Power off
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Warning!")
                    .setMessage("Are you sure you want to turn off the setup?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            powerOff();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    }
    public void powerOff()
    {
        POWER_ON = false;
        time_elapsed = 0;
        countDown.cancel();
        simulation_iv.setImageResource(R.drawable.tcl_red);
        temp_title_tv.setVisibility(View.INVISIBLE);
        set_value_title_tv.setVisibility(View.INVISIBLE);
        set_value_tv.setVisibility(View.INVISIBLE);
        temperature_tv.setVisibility(View.INVISIBLE);
        surf_temp_title_tv.setVisibility(View.INVISIBLE);
        surf_temp_tv.setVisibility(View.INVISIBLE);
        thermocouple_1 = 29.30;
        thermocouple_2 = 30.4;
        thermocouple_3 = 30.6;
        thermocouple_4 = 30.1;
        surface_temp = 29.0;
        LED_count = 0;
        LED_ON = false;
        LED_time = 0;
        LED_interval = 0;
    }
    public void change_temp(View v)
    {
        if(POWER_ON) {
            thermocouple_number++;
            if(thermocouple_number == 0) {
                temp_title_tv.setText("T1 in "+(char) 0x00B0+"C");
                temperature_tv.setText(String.format(Locale.getDefault(),"%.2f", thermocouple_1));
            }
            else if(thermocouple_number ==1) {
                temp_title_tv.setText("T2 in "+ (char) 0x00B0+"C");
                temperature_tv.setText(String.format(Locale.getDefault(),"%.2f", thermocouple_2));
            }
            else if(thermocouple_number ==2) {
                temp_title_tv.setText("T3 in "+ (char) 0x00B0+"C");
                temperature_tv.setText(String.format(Locale.getDefault(),"%.2f", thermocouple_3));
            }
            else {
                temp_title_tv.setText("T4 in "+ (char) 0x00B0+"C");
                temperature_tv.setText(String.format(Locale.getDefault(),"%.2f", thermocouple_4));
                thermocouple_number = -1;
            }
            temp_title_tv.setVisibility(View.VISIBLE);
            temperature_tv.setVisibility(View.VISIBLE);
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

    public void onRecordBtnClicked(View v)
    {
//        db.execSQL("INSERT INTO "
//                + "TCLTable"
//                + "(Temp1, Temp2, Temp3, Temp4)"
//                + " VALUES (55.5, 22.3, 34.4, 223.23);");
        numReadings++;
//        String input_txt = num_pulses.getText().toString();
//        pulses = Integer.parseInt(input_txt);
        numReadings_tv.setText(String.valueOf(numReadings));
        Log.v("Temp",""+pulses);
        Log.v("Temp", ""+updateTime);

        db.execSQL("INSERT INTO "
                + "TCLTable"
                + "(Sno, Pulses, PulseTime, TempSurf, Temp1, Temp2, Temp3, Temp4)"
                + " VALUES (" +
                    String.format(Locale.US, "%d, %d, %.2f, %.2f, %.2f, %.2f, %.2f, %.2f",
                            numReadings, pulses, updateTime/1000.0, surface_temp, thermocouple_1, thermocouple_2, thermocouple_3, thermocouple_4)
                + ");");
//        Toast.makeText(getApplicationContext(),"Data Saved!",Toast.LENGTH_SHORT).show();
        sharedPref.edit().putInt("numReadings",numReadings).apply();
    }

    public void onDeleteBtnClicked(View v)
    {
        numReadings = sharedPref.getInt("numReadings",0);
        Log.v("Temp","Num read: "+ numReadings);
        if(numReadings > 0)
        {
            db.execSQL("DELETE FROM TCLTable WHERE Sno = " + numReadings);
            numReadings--;
            sharedPref.edit().putInt("numReadings",numReadings).apply();
            numReadings_tv.setText(String.valueOf(numReadings));
        }
    }

    public void onResetBtnClicked(View v)
    {
        if(numReadings > 0) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Warning!")
                    .setMessage("Are you sure you want to reset the table?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            resetTable();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    }
    public void resetTable()
    {
        numReadings = 0;
        db.execSQL("DELETE FROM TCLTable");
        sharedPref.edit().putInt("SerialNo", 0).apply();
        sharedPref.edit().putInt("numReadings", 0).apply();
        int count = table.getChildCount();
        for (int i = 1; i < count; i++) {
            View child = table.getChildAt(i);
            if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
        }
    }

    public void downloadExcel(View v)
    {
//        String destPath = getApplicationContext().getExternalFilesDir(null).getAbsolutePath();
//        File filePath = new File(destPath+ "/TestingApp/TCL Table.xlsx");
        File filePath = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            filePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)+File.separator+"/HMT Virtual Lab/TCL Table.xlsx");
        }

        if(numReadings == 0)
        {
            Toast.makeText(getApplicationContext(),"No readings to save!",Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(getApplicationContext(),"Excel file named 'TCL Table' is saved to device internal storage.",Toast.LENGTH_LONG).show();

        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        HSSFSheet hssfSheet = hssfWorkbook.createSheet();

        HSSFRow hssfRow = hssfSheet.createRow(0);
        HSSFCell hssfCell = hssfRow.createCell(0);
        hssfCell.setCellValue("S.No.");
        HSSFCell hssfCell1 = hssfRow.createCell(1);
        hssfCell1.setCellValue("P");
        HSSFCell hssfCell2 = hssfRow.createCell(2);
        hssfCell2.setCellValue("t(p)");
        HSSFCell hssfCell3 = hssfRow.createCell(3);
        hssfCell3.setCellValue("T(S)");
        HSSFCell hssfCell4 = hssfRow.createCell(4);
        hssfCell4.setCellValue("T1");
        HSSFCell hssfCell5 = hssfRow.createCell(5);
        hssfCell5.setCellValue("T2");
        HSSFCell hssfCell6 = hssfRow.createCell(6);
        hssfCell6.setCellValue("T3");
        HSSFCell hssfCell7 = hssfRow.createCell(7);
        hssfCell7.setCellValue("T4");

        try {
            Cursor c = db.rawQuery("SELECT * FROM " + "TCLTable", null);
            ArrayList<Integer> indexArr = new ArrayList<>();
            indexArr.add(c.getColumnIndex("Sno"));
            indexArr.add(c.getColumnIndex("Pulses"));
            indexArr.add(c.getColumnIndex("PulseTime"));
            indexArr.add(c.getColumnIndex("TempSurf"));
            indexArr.add(c.getColumnIndex("Temp1"));
            indexArr.add(c.getColumnIndex("Temp2"));
            indexArr.add(c.getColumnIndex("Temp3"));
            indexArr.add(c.getColumnIndex("Temp4"));

            c.moveToFirst();
            int rowNum = 1;
            while(!c.isAfterLast()){
                HSSFRow row = hssfSheet.createRow(rowNum);
                for(int itr = 0; itr<indexArr.size(); itr++)
                {
                    String value;
                    if(itr <= 1)
                        value =  String.valueOf(c.getInt(indexArr.get(itr)));
                    else
                        value = String.valueOf(c.getFloat(indexArr.get(itr)));
                    HSSFCell cell = row.createCell(itr);
                    cell.setCellValue(value);
                }
                c.moveToNext();
                rowNum++;
            }
            c.close();
        }
        catch(Exception e) {
            Log.e("Error", "Error", e);
        }

        try {
            if (!filePath.exists()){
                filePath.createNewFile();
            }

            FileOutputStream fileOutputStream= new FileOutputStream(filePath);
            hssfWorkbook.write(fileOutputStream);

            if (fileOutputStream!=null){
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openObservation()
    {
        table = findViewById(R.id.observationTable);
        try {
            Cursor c = db.rawQuery("SELECT * FROM " + "TCLTable", null);
            ArrayList<Integer> indexArr = new ArrayList<>();
            indexArr.add(c.getColumnIndex("Sno"));
            indexArr.add(c.getColumnIndex("Pulses"));
            indexArr.add(c.getColumnIndex("PulseTime"));
            indexArr.add(c.getColumnIndex("TempSurf"));
            indexArr.add(c.getColumnIndex("Temp1"));
            indexArr.add(c.getColumnIndex("Temp2"));
            indexArr.add(c.getColumnIndex("Temp3"));
            indexArr.add(c.getColumnIndex("Temp4"));

            c.moveToFirst();
            while(!c.isAfterLast()){
                TableRow tableRow = new TableRow(this);
                for(int itr = 0; itr<indexArr.size(); itr++)
                {
                    String value;
                    if(itr <= 1)
                        value =  String.valueOf(c.getInt(indexArr.get(itr)));
                    else
                        value = String.valueOf(c.getFloat(indexArr.get(itr)));
                    TextView tv = new TextView(this);
                    tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT, 1f));
                    tv.setText(value);
                    tv.setTextSize(18);
                    tv.setGravity(Gravity.CENTER);
                    tableRow.addView(tv);
                }
                table.addView(tableRow);
                c.moveToNext();
            }
            c.close();
        }
        catch(Exception e) {
            Log.e("Error", "Error", e);
        }
    }

    void openSimulation()
    {
//        MenuItem item = menu.findItem(R.id.pulses_menu_item_id);
//        item.setVisible(true);
        menuVisible = true;
        invalidateOptionsMenu();

        simulation_iv = findViewById(R.id.simul_setup);
        powerBtn = findViewById(R.id.power_button);
        temp_title_tv = findViewById(R.id.temperature_title_tv);
        set_value_title_tv = findViewById(R.id.set_value_title_tv);
        set_value_tv = findViewById(R.id.set_value_tv);

        //Timer
        timer_tv = findViewById(R.id.timer_tv);
        startBtn = findViewById(R.id.start_btn);
        pauseBtn = findViewById(R.id.pause_btn);
        resetBtn = findViewById(R.id.reset_btn);

        temperature_tv = findViewById(R.id.temperature_tv);
        surf_temp_tv = findViewById(R.id.surface_temp_tv);
        surf_temp_title_tv = findViewById(R.id.surface_temp_title_tv);
        numReadings_tv = findViewById(R.id.numReadings_id);

        numReadings_tv.setText(String.valueOf(numReadings));

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
                updateTime = 0;
                timeInMillis = 0;
                String def = "0:00:00";
                timer_tv.setText(def);
                customHandler.removeCallbacks(updateTimerThread);
            }
        });
    }

    private void openAboutSetup()
    {
        MathJaxView equipment_tv = findViewById(R.id.equipment_tv);
        MathJaxView apparatus_tv = findViewById(R.id.apparatus_tv);
        ImageView apparatus_iv =findViewById(R.id.apparatus_image_iv);

        equipment_tv.setText(equipment_reqd);
        apparatus_tv.setText(apparatus_desc);
    }

    private class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number value, Number value2, Number value3, Number value4) {
            super(x, value);
            setValue("value2", value2);
            setValue("value3", value3);
            setValue("value4", value4);
        }
    }

    private void openGraphs() {
        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));

        Cartesian cartesian = AnyChart.line();

        cartesian.animation(true);

        cartesian.padding(10d, 20d, 10d, 20d);

        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

        cartesian.yAxis(0).title("Temperature("+(char)0x00B0+"C)");
        cartesian.xAxis(0).title("time in minutes");
//        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

        List<DataEntry> seriesData = new ArrayList<>();
        seriesData.add(new CustomDataEntry("0", 29.3, 30.4, 30.6,30.1));
        seriesData.add(new CustomDataEntry("5", 34.3, 34, 35.7, 34.67));
        seriesData.add(new CustomDataEntry("10", 35.8, 34.7, 37.1,35.87));
        seriesData.add(new CustomDataEntry("15", 36.2, 35.2, 37.9,36.43));
        seriesData.add(new CustomDataEntry("20", 36.2, 35.3, 38.3,36.6));
        seriesData.add(new CustomDataEntry("25", 36.3, 35.5, 38.4,36.73));
        seriesData.add(new CustomDataEntry("30", 36.7, 35.7, 38.4,36.93));
        seriesData.add(new CustomDataEntry("37", 36.8, 35.7, 38.4,36.96));
        seriesData.add(new CustomDataEntry("45", 36.8, 35.8, 38.6,37.07));

        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");
        Mapping series3Mapping = set.mapAs("{ x: 'x', value: 'value3' }");
        Mapping series4Mapping = set.mapAs("{ x: 'x', value: 'value4' }");

        Line series1 = cartesian.line(series1Mapping);
        series1.name("Temperature 1");
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(0d)
                .offsetY(5d);

        Line series2 = cartesian.line(series2Mapping);
        series2.name("Temperature 2");
        series2.hovered().markers().enabled(true);
        series2.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series2.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(0d)
                .offsetY(5d);

        Line series3 = cartesian.line(series3Mapping);
        series3.name("Temperature 3");
        series3.hovered().markers().enabled(true);
        series3.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series3.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(0d)
                .offsetY(5d);

        Line series4 = cartesian.line(series4Mapping);
        series4.name("Temperature 4");
        series4.hovered().markers().enabled(true);
        series4.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series4.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(0d)
                .offsetY(5d);

        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(15d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);
        anyChartView.setChart(cartesian);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu_) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu_);
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
        if (itemId == R.id.three_id) {
            Toast.makeText(this, "Number of Pulses set to Three", Toast.LENGTH_SHORT).show();
            pulses = 3;
            return true;
        } else if (itemId == R.id.four_id) {
            Toast.makeText(this, "Number of Pulses set to Four", Toast.LENGTH_SHORT).show();
            pulses = 4;
            return true;
        } else if (itemId == R.id.five_id) {
            Toast.makeText(this, "Number of Pulses set to Five", Toast.LENGTH_SHORT).show();
            pulses = 5;
            return true;
        } else if (itemId == R.id.six_id) {
            Toast.makeText(this, "Number of Pulses set to Six", Toast.LENGTH_SHORT).show();
            pulses = 6;
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thermal_conductivity_of_liquids);

        menuVisible = false;
        pulses = 4;

        try {
            db = this.openOrCreateDatabase("TCLDB", MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS "
                    + "TCLTable"
                    + " (Sno INT, Pulses INT, PulseTime FLOAT, TempSurf FLOAT,Temp1 FLOAT,Temp2 FLOAT,Temp3 FLOAT, Temp4 FLOAT);");
        } catch (Exception e) {
            Log.e("Error", "Error", e);
        }

        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        numReadings = sharedPref.getInt("numReadings", 0);

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
            setContentView(R.layout.simulation_layout);
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
            Intent intent1 = new Intent(ThermalConductivityOfLiquids.this, DashboardActivity.class);
            intent1.putExtra("exp", 1);
            startActivity(intent1);
            finish();
        }

    }
}