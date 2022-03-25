package info.androidhive.hmtvirtuallab;

import android.annotation.SuppressLint;
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
import android.widget.EditText;
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

public class NaturalConvection extends AppCompatActivity {

    TextView obj_tv;
    TextView aim_tv;
    MathJaxView intro_tv;
    MathJaxView theory_tv;
    TextView theory_tv2 ;

    TextView start_procedure;
    TextView close_procedure;
    TextView simul_procedure;

    ImageView simulation_iv;
    TextView powerBtn;
    boolean POWER_ON = false;
    TextView temp_title_tv;
    TextView temp_tv;
    TextView voltage_tv;
    TextView current_tv;
    int thermocouple_number = 0;
    TextView timer_tv;
    FloatingActionButton startBtn, pauseBtn, resetBtn;
    Handler customHandler = new Handler();
    long startTime = 0L, timeInMillis = 0L, updateTime = 0L, millisPassed = 0L;
    boolean isRunning;
    boolean wasRunning;
    boolean voltageFlag=true;

    CountDownTimer countDown = null;
    double thermocouple_1 ;
    double thermocouple_2 ;
    double thermocouple_3 ;
    double thermocouple_4 ;
    double thermocouple_5 ;
    double thermocouple_6 ;
    double thermocouple_7 ;
    double thermocouple_8 ;


    double avg_temp = 30.0;
    double time_elapsed = 0;
    int pulses = 4;
    double curr_voltage=50.0;
    double curr_current=0.923;

    int LED_count = 0;
    boolean LED_ON = false;
    double LED_time = 0;
    double LED_interval = 0;
    boolean graph_voltage=false;

    SQLiteDatabase db;
    TableLayout table;
    int numReadings = 0;
    TextView numReadings_tv;

    SharedPreferences sharedPref;

    Menu menu;
    boolean menuVisible;

    String objective_text = "To study free convection heat transfer.";
    String aim_text = "To calculate the heat transfer coefficient in free convection for a vertical heated tube.";
    String intro_text = "<p align=\"justify\" style = \"font-family: Arial Rounded MT; font-size: 18px; font-style:bold; font-weight: 400;color:#707070\">\n"+
            "$\\bullet$ Convection heat transfer takes place between a solid surface and surrounded flowing fluid when the two are at different temperatures. \n"+"<br>"+
            "<br>$\\bullet$ When flow of fluid is caused by external means such as pump or fan, the convection is called forced convection. Whereas,  if there is no any external means to cause the fluid flow, convection is called free or natural convection. \n</br>" +
            "<br>$\\bullet$ In free convection, the fluid layer in contact with the heated surface gets heated, rises up due the decrease in its density and cold fluid rushes from the bottom side to fill up the void. \n<br>" +
            "<br>$\\bullet$ Thus the motion of the fluid is induced by the buoyancy force. The process is continuous and the heat transfer takes place due to the motion of the fluid.  \n<br>";

    String theory_text= "<p align=\"justify\" style = \"font-family: Arial Rounded MT; font-size: 18px; font-style:bold; font-weight: 400;color:#707070\">\n"+
            "$\\bullet$ In this experiment, heat transfer coefficient is calculated for a heated vertical pipe, where heat is transferred from the external surface of the pipe by free convection.\n<br>" +
            "<br>$\\bullet$ Regardless of the particular nature of the convection process, the heat transfer rate due to convection is calculated from $Newton’s$ $law$ $of$ $cooling$:\n</br>" +
            "$$Q = hA(T_s - T_a)$$\n" +
            "<br> Where $Q$ is the heat transfer rate ($W$)\n</br>"+
            "<br> $A$ is the surface area perpendicular to the heat transfer direction ($m^2$)\n</br>"+
            "<br> $T_s$ = Temperature of solid surface (K)\n</br>"+
            "<br> $T_α$ = Temperature of Ambient Fluid (K)\n</br>"+
            "<br> $h$ = Heat Transfer Coefficient (W/m^2 K)\n</br>";





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
        theory_tv2=findViewById(R.id.thoery2_title_tv);

        obj_tv.setText(objective_text);
        aim_tv.setText(aim_text);
        intro_tv.setText(intro_text);
        theory_tv.setText(theory_text);
        theory_tv2.setVisibility(View.GONE);

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
        if(voltageFlag) {
            return 0.00000067016317 * Math.pow(time, 4) - 0.00009459984460 * Math.pow(time, 3) -
                    0.00204836829838 * time * time + 0.76482128982337 * time +  32.91841491837450 + value;
        }
        else
        {
            return -0.00000323426573 * Math.pow(time, 4) +  0.00051327376327 * Math.pow(time, 3)
                    - 0.03172397047399 * time * time + 1.59047526547822 * time + 31.23387723382090 + value;
        }
    }
    public double calculateTemp2(double time)
    {
        Random rand = new Random();
        double value = rand.nextDouble() / 2.0;
        if(rand.nextInt(2) == 0) //negative
            value *= -1;
        if(voltageFlag) {
            return 0.00000081585082 * Math.pow(time, 4) - 0.00010865060865 * Math.pow(time, 3) -
                    0.00158896658898 * time * time + 0.74062419062626 * time +  33.00388500384490 + value;
        }
        else
        {
            return -0.00000361305361* Math.pow(time, 4) + 0.00056293706294* Math.pow(time, 3) - 0.03370629370631* Math.pow(time, 2) + 1.59055944056232* Math.pow(time, 1) + 31.04195804190320+ value;
        }
    }
    public double calculateTemp3(double time)
    {
        Random rand = new Random();
        double value = rand.nextDouble() / 2.0;
        if(rand.nextInt(2) == 0) //negative
            value *= -1;
        if(voltageFlag) {
            return 0.00000046620047* Math.pow(time, 4) - 0.00004597254597* Math.pow(time, 3) - 0.00521173271174* Math.pow(time, 2) + 0.79948847449052* Math.pow(time, 1) + 32.90986790982830+value;
        }
        else
        {
            return -0.00000492424242* Math.pow(time, 4) + 0.00078703703704* Math.pow(time, 3) - 0.04614898989900* Math.pow(time, 2) + 1.80740740741022* Math.pow(time, 1) + 30.08080808075440+value;
        }
    }
    public double calculateTemp4(double time)
    {
        Random rand = new Random();
        double value = rand.nextDouble() / 2.0;  //random value generated between 0 and 0.5 (about 1.5% of 35)
        if(rand.nextInt(2) == 0) //negative
            value *= -1;
        if(voltageFlag) {
            return 0.00000072843823* Math.pow(time, 4) - 0.00010560735561* Math.pow(time, 3) - 0.00118783993785* Math.pow(time, 2) + 0.74921652421867* Math.pow(time, 1) + 33.94250194246030+value;
        }
        else
        {
            return -0.00000463286713* Math.pow(time, 4) + 0.00072358197358* Math.pow(time, 3) - 0.04288170163172* Math.pow(time, 2) + 1.85421522921823* Math.pow(time, 1) + 31.10023310017580+value;
        }
    }
    public double calculateTemp5(double time)
    {
        Random rand = new Random();
        double value = rand.nextDouble() / 2.0;
        if(rand.nextInt(2) == 0) //negative
            value *= -1;
        if(voltageFlag) {
            return -0.00000040792541* Math.pow(time, 4) + 0.00007705257705* Math.pow(time, 3) - 0.00496309246309* Math.pow(time, 2) + 0.12438487438592* Math.pow(time, 1) + 30.04351204349100+value;
        }
        else
        {
            return -0.00000014568765* Math.pow(time, 4) + 0.00002246827247* Math.pow(time, 3) - 0.00146950271951* Math.pow(time, 2) + 0.07133514633623* Math.pow(time, 1) + 30.10644910642740+value;
        }
    }
    public double calculateTemp6(double time)
    {
        Random rand = new Random();
        double value = rand.nextDouble() / 2.0;
        if(rand.nextInt(2) == 0) //negative
            value *= -1;
        if(voltageFlag) {
            return 0.00000032051282* Math.pow(time, 4) - 0.00002855477856* Math.pow(time, 3) - 0.00615093240094* Math.pow(time, 2) + 0.87360139860358* Math.pow(time, 1) + 33.98601398597170+value;
        }
        else
        {
            return -0.00000469114219* Math.pow(time, 4) + 0.00072617197617* Math.pow(time, 3) - 0.04265637140639* Math.pow(time, 2) + 1.87495467495775* Math.pow(time, 1) + 31.06604506598650+value;
        }
    }
    public double calculateTemp7(double time)
    {
        Random rand = new Random();
        double value = rand.nextDouble() / 2.0;
        if(rand.nextInt(2) == 0) //negative
            value *= -1;
        if(voltageFlag) {
            return 0.00000072843823* Math.pow(time, 4) - 0.00010560735561* Math.pow(time, 3) - 0.00118783993785* Math.pow(time, 2) + 0.74921652421867* Math.pow(time, 1) + 33.94250194246030+value;
        }
        else
        {
            return -0.00000230186480* Math.pow(time, 4) + 0.00036914011914* Math.pow(time, 3) - 0.02505147630149* Math.pow(time, 2) + 1.53886298886603* Math.pow(time, 1) + 31.06371406365620+value;
        }
    }
    public double calculateTemp8(double time)
    {
        Random rand = new Random();
        double value = rand.nextDouble() / 2.0;  //random value generated between 0 and 0.5 (about 1.5% of 35)
        if(rand.nextInt(2) == 0) //negative
            value *= -1;
        if(voltageFlag) {
            return 0.00000046620047* Math.pow(time, 4) - 0.00005607355607* Math.pow(time, 3) - 0.00399961149962* Math.pow(time, 2) + 0.79292281792493* Math.pow(time, 1) + 32.87956487952400+value;
        }
        else
        {
            return -0.00000352564103* Math.pow(time, 4) + 0.00052790727791* Math.pow(time, 3) - 0.03019327894329* Math.pow(time, 2) + 1.48844858845147* Math.pow(time, 1) + 31.02253302247820+value;
        }
    }

    public double calculatePulses(double time){
        return 3.41253344420811E-05*Math.pow(time,4) - 3.92624735874136E-03*Math.pow(time,3) +
                1.64034123337430E-01*time*time - 3.04057480429492E+00*time + 6.08993773891020E+01;
    }


    public void setTemperature_tv() {
        if(thermocouple_number == 0) {
            temp_tv.setText(String.format(Locale.getDefault(),"%.2f", thermocouple_1));
        }
        else if(thermocouple_number ==1) {
            temp_tv.setText(String.format(Locale.getDefault(),"%.2f", thermocouple_2));
        }
        else if(thermocouple_number ==2) {
            temp_tv.setText(String.format(Locale.getDefault(),"%.2f", thermocouple_3));
        }
        else if(thermocouple_number ==3) {
            temp_tv.setText(String.format(Locale.getDefault(),"%.2f", thermocouple_4));
        }
        else if(thermocouple_number ==4) {
            temp_tv.setText(String.format(Locale.getDefault(),"%.2f", thermocouple_5));
        }
        else if(thermocouple_number ==5) {
            temp_tv.setText(String.format(Locale.getDefault(),"%.2f", thermocouple_6));
        }
        else if(thermocouple_number ==6) {
            temp_tv.setText(String.format(Locale.getDefault(),"%.2f", thermocouple_7));
        }

        else {
            temp_tv.setText(String.format(Locale.getDefault(),"%.2f", thermocouple_8));
            thermocouple_number = -1;
        }
    }

    public void setVoltage_tv() {
            voltage_tv.setText(String.format(Locale.getDefault(),"%.0f", curr_voltage));
    }
    public void setCurrent_tv() { current_tv.setText(String.format(Locale.getDefault(),"%.3f", curr_current)); }
    public void setInitialtemp(boolean flag)
    {
        if(flag)
        {
            //50V
            thermocouple_1=33;
            thermocouple_2=33;
            thermocouple_3=33;
            thermocouple_4=34;
            thermocouple_5=30;
            thermocouple_6=34;
            thermocouple_7=34;
            thermocouple_8=33;
        }
        else
        {
            //76V
            thermocouple_1=31;
            thermocouple_2=31;
            thermocouple_3=30;
            thermocouple_4=31;
            thermocouple_5=30;
            thermocouple_6=31;
            thermocouple_7=31;
            thermocouple_8=31;
        }
    }

    public void setVoltage(View v)
    {
        if(!POWER_ON)
        {
            setInitialtemp(voltageFlag);
            if(!voltageFlag)
            {
                curr_voltage=50;
                curr_current=0.923;
            }
            else
            {
                curr_voltage=76;
                curr_current=1.396;
            }
            setVoltage_tv();
            setCurrent_tv();
            voltageFlag=!voltageFlag;

        }
    }

    public void turnOnHeater(View v)
    {
//        Log.v("LOGGED MESSAGE", "POWER BUTTON CLICKED");
        if(!POWER_ON)
        {  //Power on
            POWER_ON = true;
            simulation_iv.setImageResource(R.drawable.nc_green);
            temp_title_tv.setVisibility(View.VISIBLE);
            temp_tv.setVisibility(View.VISIBLE);
            setTemperature_tv();
            time_elapsed = 0;
            countDown = new CountDownTimer(4800000, 500) {  //80 minutes count down, updates every half second
                @Override
                public void onTick(long millisUntilFinished) {
                    time_elapsed += 0.5;
                    setTemperature_tv();
                    thermocouple_1 = calculateTemp1(time_elapsed / 60.0);
                    thermocouple_2 = calculateTemp2(time_elapsed / 60.0);
                    thermocouple_3 = calculateTemp3(time_elapsed / 60.0);
                    thermocouple_4 = calculateTemp4(time_elapsed / 60.0);
                    thermocouple_5 = calculateTemp5(time_elapsed / 60.0);
                    thermocouple_6 = calculateTemp6(time_elapsed / 60.0);
                    thermocouple_7 = calculateTemp7(time_elapsed / 60.0);
                    thermocouple_8 = calculateTemp8(time_elapsed / 60.0);
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
        simulation_iv.setImageResource(R.drawable.nc_red);
        temp_title_tv.setVisibility(View.INVISIBLE);

        temp_tv.setVisibility(View.INVISIBLE);
        setInitialtemp(voltageFlag);
        avg_temp = 29.0;
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
                temp_tv.setText(String.format(Locale.getDefault(),"%.2f", thermocouple_1));
            }
            else if(thermocouple_number ==1) {
                temp_title_tv.setText("T2 in "+ (char) 0x00B0+"C");
                temp_tv.setText(String.format(Locale.getDefault(),"%.2f", thermocouple_2));
            }
            else if(thermocouple_number ==2) {
                temp_title_tv.setText("T3 in "+ (char) 0x00B0+"C");
                temp_tv.setText(String.format(Locale.getDefault(),"%.2f", thermocouple_3));
            }
            else if(thermocouple_number ==3) {
                temp_title_tv.setText("T4 in "+ (char) 0x00B0+"C");
                temp_tv.setText(String.format(Locale.getDefault(),"%.2f", thermocouple_4));
            }
            else if(thermocouple_number ==4) {
                temp_title_tv.setText("T5 in "+ (char) 0x00B0+"C");
                temp_tv.setText(String.format(Locale.getDefault(),"%.2f", thermocouple_5));
            }
            else if(thermocouple_number ==5) {
                temp_title_tv.setText("T6 in "+ (char) 0x00B0+"C");
                temp_tv.setText(String.format(Locale.getDefault(),"%.2f", thermocouple_6));
            }
            else if(thermocouple_number ==6) {
                temp_title_tv.setText("T7 in "+ (char) 0x00B0+"C");
                temp_tv.setText(String.format(Locale.getDefault(),"%.2f", thermocouple_7));
            }
            else {
                temp_title_tv.setText("T8 in "+ (char) 0x00B0+"C");
                temp_tv.setText(String.format(Locale.getDefault(),"%.2f", thermocouple_8));
                thermocouple_number = -1;
            }
            temp_title_tv.setVisibility(View.VISIBLE);
            temp_tv.setVisibility(View.VISIBLE);
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
        try {
            numReadings++;
//        String input_txt = num_pulses.getText().toString();
//        pulses = Integer.parseInt(input_txt);
            numReadings_tv.setText(String.valueOf(numReadings));
            Log.v("Temp",""+pulses);
            Log.v("Temp", ""+updateTime);

            db.execSQL("INSERT INTO "
                    + "NCTable"
                    + "(Sno, Volt, Time, Temp1, Temp2, Temp3, Temp4, Temp5, Temp6, Temp7, Temp8)"
                    + " VALUES (" +
                    String.format(Locale.US, "%d, %.2f, %.2f, %.2f, %.2f, %.2f, %.2f, %.2f, %.2f, %.2f, %.2f",
                            numReadings,curr_voltage, time_elapsed, thermocouple_1, thermocouple_2, thermocouple_3, thermocouple_4, thermocouple_5, thermocouple_6, thermocouple_7, thermocouple_8)
                    + ");");
//        Toast.makeText(getApplicationContext(),"Data Saved!",Toast.LENGTH_SHORT).show();
            sharedPref.edit().putInt("numReadings",numReadings).apply();

        }
        catch (Exception e)
        {
            Log.v("this is error","message:"+e);
            e.printStackTrace();
        }

    }

    public void onDeleteBtnClicked(View v)
    {
        numReadings = sharedPref.getInt("numReadings",0);
        Log.v("Temp","Num read: "+ numReadings);
        if(numReadings > 0)
        {
            db.execSQL("DELETE FROM NCTable WHERE Sno = " + numReadings);
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
        db.execSQL("DELETE FROM NCTable");
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
            filePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)+File.separator+"/HMT Virtual Lab/NC Table.xlsx");
        }

        if(numReadings == 0)
        {
            Toast.makeText(getApplicationContext(),"No readings to save!",Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(getApplicationContext(),"Excel file named 'NC Table' is saved to device internal storage.",Toast.LENGTH_LONG).show();

        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        HSSFSheet hssfSheet = hssfWorkbook.createSheet();

        HSSFRow hssfRow = hssfSheet.createRow(0);
        HSSFCell hssfCell = hssfRow.createCell(0);
        hssfCell.setCellValue("S.No.");
        HSSFCell hssfCell1 = hssfRow.createCell(1);
        hssfCell1.setCellValue("Voltage");
        HSSFCell hssfCell2 = hssfRow.createCell(2);
        hssfCell2.setCellValue("Time");
        HSSFCell hssfCell3 = hssfRow.createCell(3);
        hssfCell3.setCellValue("T1");
        HSSFCell hssfCell4 = hssfRow.createCell(4);
        hssfCell4.setCellValue("T2");
        HSSFCell hssfCell5 = hssfRow.createCell(5);
        hssfCell5.setCellValue("T3");
        HSSFCell hssfCell6 = hssfRow.createCell(6);
        hssfCell6.setCellValue("T4");
        HSSFCell hssfCell7 = hssfRow.createCell(7);
        hssfCell7.setCellValue("T5");
        HSSFCell hssfCell8 = hssfRow.createCell(8);
        hssfCell8.setCellValue("T6");
        HSSFCell hssfCell9 = hssfRow.createCell(9);
        hssfCell9.setCellValue("T7");
        HSSFCell hssfCell10 = hssfRow.createCell(10);
        hssfCell10.setCellValue("T8");

        try {
            Cursor c = db.rawQuery("SELECT * FROM " + "NCTable", null);
            ArrayList<Integer> indexArr = new ArrayList<>();
            indexArr.add(c.getColumnIndex("Sno"));
            indexArr.add(c.getColumnIndex("Volt"));
            indexArr.add(c.getColumnIndex("Time"));
            indexArr.add(c.getColumnIndex("Temp1"));
            indexArr.add(c.getColumnIndex("Temp2"));
            indexArr.add(c.getColumnIndex("Temp3"));
            indexArr.add(c.getColumnIndex("Temp4"));
            indexArr.add(c.getColumnIndex("Temp5"));
            indexArr.add(c.getColumnIndex("Temp6"));
            indexArr.add(c.getColumnIndex("Temp7"));
            indexArr.add(c.getColumnIndex("Temp8"));

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
            Cursor c = db.rawQuery("SELECT * FROM " + "NCTable", null);
            ArrayList<Integer> indexArr = new ArrayList<>();
            indexArr.add(c.getColumnIndex("Sno"));
            indexArr.add(c.getColumnIndex("Volt"));
            indexArr.add(c.getColumnIndex("Time"));
            indexArr.add(c.getColumnIndex("Temp1"));
            indexArr.add(c.getColumnIndex("Temp2"));
            indexArr.add(c.getColumnIndex("Temp3"));
            indexArr.add(c.getColumnIndex("Temp4"));
            indexArr.add(c.getColumnIndex("Temp5"));
            indexArr.add(c.getColumnIndex("Temp6"));
            indexArr.add(c.getColumnIndex("Temp7"));
            indexArr.add(c.getColumnIndex("Temp8"));

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
        catch(Exception e) { Log.e("Error", "Error", e);
        }
    }


    void openSimulation()
    {
//        MenuItem item = menu.findItem(R.id.pulses_menu_item_id);
//        item.setVisible(true);
      //  menuVisible =  ;
       // invalidateOptionsMenu();

        simulation_iv = findViewById(R.id.simul_setup);
        powerBtn = findViewById(R.id.power_button);
        temp_title_tv = findViewById(R.id.temp_title_tv);
        current_tv = findViewById(R.id.current_tv);
        voltage_tv=findViewById(R.id.voltage_tv);
        temp_tv=findViewById(R.id.temp_tv);

        timer_tv = findViewById(R.id.timer_tv);
        startBtn = findViewById(R.id.start_btn);
        pauseBtn = findViewById(R.id.pause_btn);
        resetBtn = findViewById(R.id.reset_btn);
        temp_tv = findViewById(R.id.temp_tv);
        numReadings_tv = findViewById(R.id.numReadings_id);

        numReadings_tv.setText(String.valueOf(numReadings));
//        simulation_iv.setImageResource(R.drawable.tcl_green_black);

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

        CustomDataEntry(String x, Number value, Number value2, Number value3, Number value4, Number value5, Number value6, Number value7,Number value8) {
            super(x, value);
            setValue("value2", value2);
            setValue("value3", value3);
            setValue("value4", value4);
            setValue("value5", value5);
            setValue("value6", value6);
            setValue("value7", value7);
            setValue("value8", value8);

        }

    }

    private void openGraphs() {



        if(graph_voltage)
        {
            menuVisible =true;
            invalidateOptionsMenu();
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
            cartesian.removeAllSeries();
//        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d)
            List<DataEntry> seriesData = new ArrayList<>();
            seriesData.clear();
            //seriesData.clear();
            Toast.makeText(this, "inside if condition", Toast.LENGTH_SHORT).show();
            seriesData.add(new CustomDataEntry("0", 33, 33, 33,34, 30, 34, 34,33));
            seriesData.add(new CustomDataEntry("10", 40, 40, 40, 41, 31, 42, 41, 40));
            seriesData.add(new CustomDataEntry("20", 47, 47, 47,48, 31, 49, 48,47));
            seriesData.add(new CustomDataEntry("30", 52, 51, 51,53, 31, 54, 53,52));
            seriesData.add(new CustomDataEntry("40", 56, 55, 55,57, 31, 58, 57,56));
            seriesData.add(new CustomDataEntry("50", 58, 58, 57,60, 31, 61, 60,58));
            seriesData.add(new CustomDataEntry("60", 60, 59, 58,61, 31, 62, 61,60));
            seriesData.add(new CustomDataEntry("70", 60, 59, 59,62, 31, 63, 62,61));
            seriesData.add(new CustomDataEntry("80", 60, 60, 59,62, 31, 63, 62,61));
            Set set = Set.instantiate();
            set.data(seriesData);
            Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
            Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");
            Mapping series3Mapping = set.mapAs("{ x: 'x', value: 'value3' }");
            Mapping series4Mapping = set.mapAs("{ x: 'x', value: 'value4' }");
            Mapping series5Mapping = set.mapAs("{ x: 'x', value: 'value5' }");
            Mapping series6Mapping = set.mapAs("{ x: 'x', value: 'value6' }");
            Mapping series7Mapping = set.mapAs("{ x: 'x', value: 'value7' }");
            Mapping series8Mapping = set.mapAs("{ x: 'x', value: 'value8' }");
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

            Line series5 = cartesian.line(series5Mapping);
            series5.name("Temperature 5");
            series5.hovered().markers().enabled(true);
            series5.hovered().markers()
                    .type(MarkerType.CIRCLE)
                    .size(4d);
            series5.tooltip()
                    .position("right")
                    .anchor(Anchor.LEFT_CENTER)
                    .offsetX(0d)
                    .offsetY(5d);

            Line series6 = cartesian.line(series6Mapping);
            series6.name("Temperature 6");
            series6.hovered().markers().enabled(true);
            series6.hovered().markers()
                    .type(MarkerType.CIRCLE)
                    .size(4d);
            series6.tooltip()
                    .position("right")
                    .anchor(Anchor.LEFT_CENTER)
                    .offsetX(0d)
                    .offsetY(5d);

            Line series7 = cartesian.line(series7Mapping);
            series7.name("Temperature 7");
            series7.hovered().markers().enabled(true);
            series7.hovered().markers()
                    .type(MarkerType.CIRCLE)
                    .size(4d);
            series7.tooltip()
                    .position("right")
                    .anchor(Anchor.LEFT_CENTER)
                    .offsetX(0d)
                    .offsetY(5d);

            Line series8 = cartesian.line(series8Mapping);
            series8.name("Temperature 8");
            series8.hovered().markers().enabled(true);
            series8.hovered().markers()
                    .type(MarkerType.CIRCLE)
                    .size(4d);
            series8.tooltip()
                    .position("right")
                    .anchor(Anchor.LEFT_CENTER)
                    .offsetX(0d)
                    .offsetY(5d);
            cartesian.legend().enabled(true);
            cartesian.legend().fontSize(15d);
            cartesian.legend().padding(0d, 0d, 10d, 0d);
            anyChartView.setChart(cartesian);
        }
        else
        {
            menuVisible =true;
            invalidateOptionsMenu();
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
            cartesian.removeAllSeries();
//        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d)
            Toast.makeText(this, "inside else condition", Toast.LENGTH_SHORT).show();
            List<DataEntry> seriesData = new ArrayList<>();
            seriesData.clear();
            seriesData.add(new CustomDataEntry("0", 31, 31, 30,31, 30, 31, 31,31));
            seriesData.add(new CustomDataEntry("10", 45, 44, 44, 46, 31, 46, 44,43));
            seriesData.add(new CustomDataEntry("20", 54, 54, 55,57, 31, 58, 56,54));
            seriesData.add(new CustomDataEntry("30", 61, 60, 58,63, 31, 63, 61,58));
            seriesData.add(new CustomDataEntry("40", 68, 67, 66,70, 32, 72, 70,67));
            seriesData.add(new CustomDataEntry("50", 77, 75, 74,80, 32, 81, 78,75));
            seriesData.add(new CustomDataEntry("60", 81, 80, 79,84, 32, 86, 84,80));
            seriesData.add(new CustomDataEntry("70", 85, 83, 81,87, 32, 89, 86,83));
            seriesData.add(new CustomDataEntry("80", 86, 83, 81,86, 32, 88, 89,83));
            Set set = Set.instantiate();
            set.data(seriesData);
            Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
            Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");
            Mapping series3Mapping = set.mapAs("{ x: 'x', value: 'value3' }");
            Mapping series4Mapping = set.mapAs("{ x: 'x', value: 'value4' }");
            Mapping series5Mapping = set.mapAs("{ x: 'x', value: 'value5' }");
            Mapping series6Mapping = set.mapAs("{ x: 'x', value: 'value6' }");
            Mapping series7Mapping = set.mapAs("{ x: 'x', value: 'value7' }");
            Mapping series8Mapping = set.mapAs("{ x: 'x', value: 'value8' }");
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

            Line series5 = cartesian.line(series5Mapping);
            series5.name("Temperature 5");
            series5.hovered().markers().enabled(true);
            series5.hovered().markers()
                    .type(MarkerType.CIRCLE)
                    .size(4d);
            series5.tooltip()
                    .position("right")
                    .anchor(Anchor.LEFT_CENTER)
                    .offsetX(0d)
                    .offsetY(5d);

            Line series6 = cartesian.line(series6Mapping);
            series6.name("Temperature 6");
            series6.hovered().markers().enabled(true);
            series6.hovered().markers()
                    .type(MarkerType.CIRCLE)
                    .size(4d);
            series6.tooltip()
                    .position("right")
                    .anchor(Anchor.LEFT_CENTER)
                    .offsetX(0d)
                    .offsetY(5d);

            Line series7 = cartesian.line(series7Mapping);
            series7.name("Temperature 7");
            series7.hovered().markers().enabled(true);
            series7.hovered().markers()
                    .type(MarkerType.CIRCLE)
                    .size(4d);
            series7.tooltip()
                    .position("right")
                    .anchor(Anchor.LEFT_CENTER)
                    .offsetX(0d)
                    .offsetY(5d);

            Line series8 = cartesian.line(series8Mapping);
            series8.name("Temperature 8");
            series8.hovered().markers().enabled(true);
            series8.hovered().markers()
                    .type(MarkerType.CIRCLE)
                    .size(4d);
            series8.tooltip()
                    .position("right")
                    .anchor(Anchor.LEFT_CENTER)
                    .offsetX(0d)
                    .offsetY(5d);
            cartesian.legend().enabled(true);
            cartesian.legend().fontSize(15d);
            cartesian.legend().padding(0d, 0d, 10d, 0d);
            anyChartView.setChart(cartesian);

        }

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu_) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.voltage_menu, menu_);
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
        if (itemId == R.id.fiftysix) {
            Toast.makeText(this, "Graph For Voltage Set To 56V", Toast.LENGTH_SHORT).show();
            graph_voltage = true;
            openGraphs();
            return true;
        } else if (itemId == R.id.seventyone) {
            Toast.makeText(this, "Graph For Voltage Set To 71V", Toast.LENGTH_SHORT).show();
            graph_voltage = false;
            openGraphs();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_natural_convection);

        try {
            db = this.openOrCreateDatabase("NCDB", MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS "
                    + "NCTable"
                    + " (Sno INT, Volt FLOAT,Time FLOAT, Temp1 FLOAT, Temp2 FLOAT,Temp3 FLOAT,Temp4 FLOAT,Temp5 FLOAT, Temp6 FLOAT,Temp7 FLOAT, Temp8 FLOAT );");
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
            setContentView(R.layout.simulation_layout_nc);
            openSimulation();
        } else if (viewClicked != null && viewClicked.equals("procedure")) {
            setTitle("Procedure");
            setContentView(R.layout.procedure_layout);
            openProcedure();
        } else if (viewClicked != null && viewClicked.equals("observationTable")) {
            setTitle("Observation Table");
            setContentView(R.layout.observation_layout_nc);
            openObservation();
        } else if (viewClicked != null && viewClicked.equals("graphs")) {
            setTitle("Graphs");
            setContentView(R.layout.graphs_layout);
            openGraphs();
        }
    }
}