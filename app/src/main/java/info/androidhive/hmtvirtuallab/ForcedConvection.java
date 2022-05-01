package info.androidhive.hmtvirtuallab;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import io.github.sidvenu.mathjaxview.MathJaxView;

public class ForcedConvection extends AppCompatActivity {
    TextView obj_tv;
    TextView aim_tv;
    TextView aim_title_tv;
    TextView intro_title_tv;
    TextView theory2_title;
    MathJaxView theory2_tv;
    MathJaxView intro_text_tv;
    MathJaxView theory_tv;

    MathJaxView eqp_tv;
    MathJaxView apparatus_tv;
    ImageView apparatus_iv;
    TextView internal_title;
    ImageView internal_iv;

    //Timer
    TextView timer_tv;
    FloatingActionButton startBtn, pauseBtn, resetBtn;
    Handler customHandler = new Handler();
    long startTime = 0L, timeInMillis = 0L, updateTime = 0L, millisPassed = 0L;
    boolean isRunning;
    boolean wasRunning;

    boolean POWER_ON;
    ImageView simul_iv;
    TextView temp_tv;
    TextView temp_title;
    TextView temp_text;
    TextView voltage_title;
    TextView voltage;
    TextView current_title;
    TextView current;

    TableLayout layout;

    CountDownTimer countDown;
    double thermocouple_number = 0;
    double thermocouple1 = 58.5;
    double thermocouple2 = 55.2;
    double thermocouple3 = 63.7;
    double thermocouple4 = 58.5;
    double thermocouple5 = 67.5;
    double thermocouple6 = 62.2;
    double thermocouple7 = 27.0;
    double thermocouple8 = 35.4;
    double time_elapsed = 0;

    SQLiteDatabase db;
    int numReadings = 0;
    SharedPreferences sharedPref;
    TextView numReadings_tv;

    String obj_text = "To determine the effectiveness and heat transfer coefficient in forced convection for internal pipe flow.";
    String theory_text = "<p align=\"justify\" style = \"font-family: Arial Rounded MT; font-size: 18px; font-style:bold; font-weight: 400;color:#707070\">\n"+
            "Convection is the mechanism of heat transfer through a fluid in the presence of bulk fluid motion. " +
            "Convection is classified as natural (or free) and forced convection depending on how the fluid motion is initiated. In natural convection, any fluid motion is caused by natural means such as the buoyancy effect, " +
            "i.e. the rise of warmer fluid and fall the cooler fluid. Whereas in forced convection, the fluid is forced to flow over a surface or in a tube by external means such as a pump or fan. Convection heat transfer is complicated since it involves fluid motion as well as heat conduction. " +
            "The fluid motion enhances heat transfer (the higher the velocity the higher the heat transfer rate). The rate of convection heat transfer is expressed by Newton’s law of cooling:\n" +
            "$$\uD835\uDC10̇ = ℎ \uD835\uDC34 (\uD835\uDC7B_{\uD835\uDC94} − \uD835\uDC7B_{∞})$$\n" +
            "The convective heat transfer coefficient h strongly depends on the fluid properties and roughness of the solid surface, and the type of the fluid flow (laminar or turbulent).\n" +
            "\n" +
            "The forced convection heat transfer from a solid surface may take place either for external flow or internal flow. In this experiment, heat transfer coefficient in forced convection is calculated for internal pipe flow. " +
            "This type of configuration is used for heating and cooling of fluids in several chemical process and energy conversion technique.\n" +
            "For theoretical calculation, Nusselt no. for Laminar developing region,\n" +
            "<p align=\"justify\" style = \"font-family: Arial Rounded MT; font-size: 15px; font-style:bold; font-weight: 400;color:#707070\">\n"+
            "$$Nu_{d} = 3.66 + \\frac{0.65(d/L)Re_{d}Pr}{1+0.04[(d/L)Re_{d}Pr]^{2/3}}$$\n" +
            "\n" +
            "<p align=\"justify\" style = \"font-family: Arial Rounded MT; font-size: 18px; font-style:bold; font-weight: 400;color:#707070\">\n"+
            "For fully Developed laminar region $$\uD835\uDC41\uD835\uDC62_{d} = 3.66$$ \n" +
            "<p align=\"justify\" style = \"font-family: Arial Rounded MT; font-size: 18px; font-style:bold; font-weight: 400;color:#707070\">\n"+
            "For Turbulent Region $$Nu_{d} = 0.023Re_{d}^{4/5}Pr^{0.4}$$\n" +
            "All properties are calculated at mean temperature.\n" +
            "For Experimental calculations we find $\uD835\uDC10$ by using fluid dynamics equation of flow in pipe,\n" +
            "$$\\dot Q = C_{d}a_{1}a_{2}\\frac{\\sqrt{2gH(\\frac{\\rho_{w}}{\\rho_{d}})}}{\\sqrt{a_{1}^{2}-a_{2}^{2}}}\\frac{m^{3}}{s}$$\n" +
            " \n" +
            "Mass flow Rate is $q = \uD835\uDC40\uD835\uDC36\uD835\uDC51∆\uD835\uDC47$ and by using this we can find heat transfer coefficient.\n";

    String exp_reqd_text = "<p align=\"justify\" style = \"font-family: Arial Rounded MT; font-size: 18px; font-style:bold; font-weight: 400;color:#707070\">\n"+
            "$\\bullet$ External pump/blower\n<br>" +
            "<br>$\\bullet$ Nichrome band heater\n</br>" +
            "<br>$\\bullet$ Orifice meter\n</br>" +
            "<br>$\\bullet$ U-tube Manometer\n</br>" +
            "<br>$\\bullet$ Thermocouples\n</br>" +
            "<br>$\\bullet$ Dimmerstat\n</br>" +
            "<br>$\\bullet$ Voltmeter and Ammeter\n</br>";
    String apparatus_text = "<p align=\"justify\" style = \"font-family: Arial Rounded MT; font-size: 18px; font-style:bold; font-weight: 400;color:#707070\">\n"+
            "There is a blower which blows the air. A valve is attached to the blower. By the help of the valve we control the flow of air through the pipe which is attached to the heater . " +
            "Heater heats the air. There are a total 8 thermocouples, 2 of which give the temperature of the connected inlet and outlet of the air(placed at location of inlet and outlet of the air)and 6 " +
            "other thermocouples are attached to different locations to give the temperature of the heater.";


    private class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number value, Number value2, Number value3, Number value4,
                        Number value5, Number value6, Number value7, Number value8) {
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
        seriesData.add(new ForcedConvection.CustomDataEntry("0",57,58,61,56,65,60,27,35));
        seriesData.add(new ForcedConvection.CustomDataEntry("7",82,81,119,111,121,109,30,48));
        seriesData.add(new ForcedConvection.CustomDataEntry("17",84,125,131,124,135,122,33,54));
        seriesData.add(new ForcedConvection.CustomDataEntry("27",87,129,135,129,142,127,35,57));
        seriesData.add(new ForcedConvection.CustomDataEntry("37",89,132,138,133,145,131,36,58));
        seriesData.add(new ForcedConvection.CustomDataEntry("47",91,135,141,135,147,133,37,59));
        seriesData.add(new ForcedConvection.CustomDataEntry("57",92,136,142,136,148,134,37,59));
        seriesData.add(new ForcedConvection.CustomDataEntry("67",92,136,142,136,149,135,37,60));

        Set set = Set.instantiate();
        set.data(seriesData);

        for(int i = 1; i<=8; i++){
            String mapping;
            if(i == 1){
                mapping = "{ x: 'x', value: 'value' }";
            }
            else{
                mapping = "{ x: 'x', value: 'value"+i+"' }";
            }
            Mapping seriesMapping = set.mapAs(mapping);

            Line series = cartesian.line(seriesMapping);
            series.name("Temperature "+i);
            series.hovered().markers().enabled(true);
            series.hovered().markers()
                    .type(MarkerType.CIRCLE)
                    .size(4d);
            series.tooltip()
                    .position("right")
                    .anchor(Anchor.LEFT_CENTER)
                    .offsetX(0d)
                    .offsetY(5d);
        }

        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(15d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);
        anyChartView.setChart(cartesian);

    }
    private void openObservation() {
        layout = findViewById(R.id.observationTable);
        layout.removeAllViews();
        TableRow headings = new TableRow(this);
        TextView time = new TextView(this);
        time.setLayoutParams(new TableRow.LayoutParams(60*3,
                45*3, 1f));
        time.setText("Time\n(minutes)");
        time.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f);
        time.setTypeface(null, Typeface.BOLD);
        headings.addView(time);
        for(int i = 1; i<=8; i++){
            TextView c = new TextView(this);
            if(i == 8)
                c.setLayoutParams(new TableRow.LayoutParams(72*3,
                        45*3, 1f));
            else
                c.setLayoutParams(new TableRow.LayoutParams(55*3,
                    45*3, 1f));

            String text = "T" + i;
            c.setText(text);
            c.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f);
            c.setGravity(Gravity.CENTER);
            c.setTypeface(null, Typeface.BOLD);
            c.setPadding(0,0,10,0);
            headings.addView(c);
        }
        layout.addView(headings);

        //Adding all values from database table 'FCTable' to the observation layout
        try {
            Cursor c = db.rawQuery("SELECT * FROM " + "FCTable", null);
            ArrayList<Integer> indexArr = new ArrayList<>();
            indexArr.add(c.getColumnIndex("Time"));
            indexArr.add(c.getColumnIndex("T1"));
            indexArr.add(c.getColumnIndex("T2"));
            indexArr.add(c.getColumnIndex("T3"));
            indexArr.add(c.getColumnIndex("T4"));
            indexArr.add(c.getColumnIndex("T5"));
            indexArr.add(c.getColumnIndex("T6"));
            indexArr.add(c.getColumnIndex("T7"));
            indexArr.add(c.getColumnIndex("T8"));

            c.moveToFirst();
            while(!c.isAfterLast()){
                TableRow tableRow = new TableRow(this);
                for(int itr = 0; itr<indexArr.size(); itr++)
                {
                    String value;
                    if(itr == 0)
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
                layout.addView(tableRow);
                c.moveToNext();
            }
            c.close();
        }
        catch(Exception e) {
            Log.e("Error", "Error", e);
        }
    }

    private void openProcedure() {
    }

    public double calculateTemp1(double time) {
        return -0.00002229317366*Math.pow(time, 4) + 0.00342315161894*Math.pow(time, 3) -
                0.17970196744270*Math.pow(time, 2) + 3.86854170149218*time + 58.56476282786710;
    }
    public double calculateTemp2(double time) {
        return  -0.00000511842561*Math.pow(time, 4) + 0.00149929790628*Math.pow(time, 3) -
            0.14429779140306*Math.pow(time, 2) + 5.69133786010434*time + 55.26184776794430;
    }
    public double calculateTemp3(double time) {
        return  -0.00004998637341*Math.pow(time, 4) + 0.00788278585019*Math.pow(time, 3) -
            0.42868808484869*Math.pow(time, 2) + 9.51990711828739*time + 63.77612242670150;
    }
    public double calculateTemp4(double time) {
        return  -0.00004458344824*Math.pow(time, 4) + 0.00708952680492*Math.pow(time,3) -
            0.39163853773064*Math.pow(time, 2) + 8.96309967070692*time + 58.58934740948460;
    }
    public double calculateTemp5(double time) {
        return  -0.00004251050225*Math.pow(time, 4) + 0.00686467159450*Math.pow(time, 3) -
            0.38662925646980*Math.pow(time, 2) + 9.07408462634703*time + 67.57719583602830;
    }
    public double calculateTemp6(double time) {
        return  -0.00003808638000*Math.pow(time, 4) + 0.00611403623717*Math.pow(time, 3) -
            0.34192959981468*Math.pow(time, 2) + 7.99024673343911*time + 62.22544465073220;
    }
    public double calculateTemp7(double time) {
        return  -0.00000045482336*Math.pow(time, 4) + 0.00009568313324*Math.pow(time, 3) -
            0.00943172250346*Math.pow(time, 2) + 0.48805350514294*time + 27.00516154532580;
    }
    public double calculateTemp8(double time) {
        return  -0.00000713684461*Math.pow(time, 4) + 0.00124182111536*Math.pow(time, 3) -
            0.07762091471409*Math.pow(time, 2) + 2.13671223531969*time + 35.40733294233310;
    }

    public void change_temp(View v)
    {
        if(POWER_ON) {
            thermocouple_number++;
            if(thermocouple_number == 0) {
                temp_title.setText("T1("+(char) 0x00B0+"C)");
                temp_text.setText(String.format(Locale.getDefault(),"%.1f", thermocouple1));
            }
            else if(thermocouple_number == 1) {
                temp_title.setText("T2("+(char) 0x00B0+"C)");
                temp_text.setText(String.format(Locale.getDefault(),"%.1f", thermocouple2));
            }
            else if(thermocouple_number == 2) {
                temp_title.setText("T3("+(char) 0x00B0+"C)");
                temp_text.setText(String.format(Locale.getDefault(),"%.1f", thermocouple3));
            }
            else if(thermocouple_number == 3) {
                temp_title.setText("T4("+(char) 0x00B0+"C)");
                temp_text.setText(String.format(Locale.getDefault(),"%.1f", thermocouple4));
            }
            else if(thermocouple_number == 4) {
                temp_title.setText("T5("+(char) 0x00B0+"C)");
                temp_text.setText(String.format(Locale.getDefault(),"%.1f", thermocouple5));
            }
            else if(thermocouple_number == 5) {
                temp_title.setText("T6("+(char) 0x00B0+"C)");
                temp_text.setText(String.format(Locale.getDefault(),"%.1f", thermocouple6));
            }
            else if(thermocouple_number == 6) {
                temp_title.setText("T7("+(char) 0x00B0+"C)");
                temp_text.setText(String.format(Locale.getDefault(),"%.1f", thermocouple7));
            }
            else {
                temp_title.setText("T8("+(char) 0x00B0+"C)");
                temp_text.setText(String.format(Locale.getDefault(),"%.1f", thermocouple8));
                thermocouple_number = -1;
            }
        }
    }

    public void setTemperature_tv() {
        if(thermocouple_number == 0) {
            temp_text.setText(String.format(Locale.getDefault(),"%.1f", thermocouple1));
        } else if(thermocouple_number ==1) {
            temp_text.setText(String.format(Locale.getDefault(),"%.1f", thermocouple2));
        } else if(thermocouple_number ==2) {
            temp_text.setText(String.format(Locale.getDefault(),"%.1f", thermocouple3));
        } else if(thermocouple_number ==3) {
            temp_text.setText(String.format(Locale.getDefault(),"%.1f", thermocouple4));
        } else if(thermocouple_number == 4) {
            temp_text.setText(String.format(Locale.getDefault(),"%.1f", thermocouple5));
        }else if(thermocouple_number == 5) {
            temp_text.setText(String.format(Locale.getDefault(),"%.1f", thermocouple6));
        }else if(thermocouple_number == 6) {
            temp_text.setText(String.format(Locale.getDefault(),"%.1f", thermocouple7));
        } else{
            temp_text.setText(String.format(Locale.getDefault(),"%.1f", thermocouple8));
            thermocouple_number = -1;
        }
    }

    public void onStart(View v){
        if(!POWER_ON){
            // power turned on
            POWER_ON = true;
            simul_iv.setImageResource(R.drawable.forced_green);
            temp_tv.setVisibility(View.VISIBLE);
            temp_title.setVisibility(View.VISIBLE);
            temp_text.setVisibility(View.VISIBLE);
            voltage_title.setVisibility(View.VISIBLE);
            voltage.setVisibility(View.VISIBLE);
            current_title.setVisibility(View.VISIBLE);
            current.setVisibility(View.VISIBLE);

            countDown = new CountDownTimer(3600000, 500) {  //60 minutes count down, updates every half second
                @Override
                public void onTick(long millisUntilFinished) {
                    time_elapsed += 0.5;
                    setTemperature_tv();
//                    temp_text.setText(String.format(Locale.getDefault(),"%.1f", thermocouple1));
                    thermocouple1 = calculateTemp1(time_elapsed/60.0);
                    thermocouple2 = calculateTemp2(time_elapsed/60.0);
                    thermocouple3 = calculateTemp3(time_elapsed/60.0);
                    thermocouple4 = calculateTemp4(time_elapsed/60.0);
                    thermocouple5 = calculateTemp5(time_elapsed/60.0);
                    thermocouple6 = calculateTemp6(time_elapsed/60.0);
                    thermocouple7 = calculateTemp7(time_elapsed/60.0);
                    thermocouple8 = calculateTemp8(time_elapsed/60.0);
                }
                @Override
                public void onFinish() {
                    Log.v("Temp","time khatam :"+thermocouple1);
                }
            }.start();
        }
        else{
            // power turned off
            POWER_ON = false;
            simul_iv.setImageResource(R.drawable.forced_red);
            temp_tv.setVisibility(View.INVISIBLE);
            temp_title.setVisibility(View.INVISIBLE);
            temp_text.setVisibility(View.INVISIBLE);

            voltage_title.setVisibility(View.INVISIBLE);
            voltage.setVisibility(View.INVISIBLE);
            current_title.setVisibility(View.INVISIBLE);
            current.setVisibility(View.INVISIBLE);

            thermocouple1 = 58.5;
            thermocouple2 = 55.2;
            thermocouple3 = 63.7;
            thermocouple4 = 58.5;
            thermocouple5 = 67.5;
            thermocouple6 = 62.2;
            thermocouple7 = 27.0;
            thermocouple8 = 35.4;
            time_elapsed = 0;
            countDown.cancel();
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


    public void onRecordBtnClicked(View v){
        numReadings++;
        int t = (int)time_elapsed/60;
        db.execSQL("INSERT INTO "
                + "FCTable"
                + "(Sno, Time, T1, T2, T3, T4, T5, T6, T7, T8)"
                + " VALUES (" +
                String.format(Locale.US, "%d, %d, %.2f, %.2f, %.2f, %.2f, %.2f, %.2f, %.2f, %.2f",
                        numReadings, t, thermocouple1,thermocouple2,thermocouple3,thermocouple4,thermocouple5,thermocouple6,thermocouple7,thermocouple8)
                + ");");

        numReadings_tv.setText(String.valueOf(numReadings));
        sharedPref.edit().putInt("numReadings",numReadings).apply();
    }

    public void onDeleteBtnClicked(View v){
        if(numReadings > 0)
        {
            db.execSQL("DELETE FROM FCTable WHERE Sno = " + numReadings);
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
        db.execSQL("DELETE FROM FCTable");
        sharedPref.edit().putInt("numReadings", 0).apply();
        int count = layout.getChildCount();
        for (int i = 1; i < count; i++) {
            View child = layout.getChildAt(i);
            if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
        }
    }

    private void openSimulation() {
        simul_iv = findViewById(R.id.simul_setup);
        temp_tv = findViewById(R.id.temperature_tv);
        temp_title = findViewById(R.id.temp_title_id);
        temp_text = findViewById(R.id.temp_text_id);
        voltage_title = findViewById(R.id.voltage_title_id);
        voltage = findViewById(R.id.voltage_id);
        current_title = findViewById(R.id.current_title_id);
        current = findViewById(R.id.current_id);

        //Timer
        timer_tv = findViewById(R.id.timer_tv);
        startBtn = findViewById(R.id.start_btn);
        pauseBtn = findViewById(R.id.pause_btn);
        resetBtn = findViewById(R.id.reset_btn);

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

    private void openAboutSetup() {
        eqp_tv =  findViewById(R.id.equipment_tv);
        apparatus_tv = findViewById(R.id.apparatus_tv);
        apparatus_iv = findViewById(R.id.apparatus_image_iv);
        internal_iv = findViewById(R.id.internal_iv_id);
        internal_title = findViewById(R.id.internal_title_id);

        internal_title.setVisibility(View.GONE);
        internal_iv.setVisibility(View.GONE);

        eqp_tv.setText(exp_reqd_text);
        apparatus_tv.setText(apparatus_text);
        apparatus_iv.setImageResource(R.drawable.apparatus_forced);
        apparatus_iv.setScaleType(ImageView.ScaleType.FIT_CENTER);

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


        aim_tv.setVisibility(View.GONE);
        aim_title_tv.setVisibility(View.GONE);
        intro_text_tv.setVisibility(View.GONE);
        intro_title_tv.setVisibility(View.GONE);
        theory2_title.setVisibility(View.GONE);
        theory2_tv.setVisibility(View.GONE);

        obj_tv.setText(obj_text);
        theory_tv.setText(theory_text);
    }

    public void downloadExcel(View v) {
        File filePath = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            filePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator + "/HMT Virtual Lab/FC Table.xlsx");
        }

        if (numReadings == 0) {
            Toast.makeText(getApplicationContext(), "No readings to save!", Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(getApplicationContext(), "Excel file named 'FC Table' is saved to device internal storage.", Toast.LENGTH_LONG).show();

        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        HSSFSheet hssfSheet = hssfWorkbook.createSheet();

        HSSFRow hssfRow = hssfSheet.createRow(0);
        HSSFCell hssfCell = hssfRow.createCell(0);
        hssfCell.setCellValue("S.No.");
        HSSFCell hssfCell1 = hssfRow.createCell(1);
        hssfCell1.setCellValue("time(minutes)");
        HSSFCell hssfCell2 = hssfRow.createCell(2);
        hssfCell2.setCellValue("T1");
        HSSFCell hssfCell3 = hssfRow.createCell(3);
        hssfCell3.setCellValue("T2");
        HSSFCell hssfCell4 = hssfRow.createCell(4);
        hssfCell4.setCellValue("T3");
        HSSFCell hssfCell5 = hssfRow.createCell(5);
        hssfCell5.setCellValue("T4");
        HSSFCell hssfCell6 = hssfRow.createCell(6);
        hssfCell6.setCellValue("T5");
        HSSFCell hssfCell7 = hssfRow.createCell(7);
        hssfCell7.setCellValue("T6");
        HSSFCell hssfCell8 = hssfRow.createCell(8);
        hssfCell8.setCellValue("T7");
        HSSFCell hssfCell9 = hssfRow.createCell(9);
        hssfCell9.setCellValue("T8");

        try {
            Cursor c = db.rawQuery("SELECT * FROM " + "FCTable", null);
            ArrayList<Integer> indexArr = new ArrayList<>();
            indexArr.add(c.getColumnIndex("Sno"));
            indexArr.add(c.getColumnIndex("Time"));
            indexArr.add(c.getColumnIndex("T1"));
            indexArr.add(c.getColumnIndex("T2"));
            indexArr.add(c.getColumnIndex("T3"));
            indexArr.add(c.getColumnIndex("T4"));
            indexArr.add(c.getColumnIndex("T5"));
            indexArr.add(c.getColumnIndex("T6"));
            indexArr.add(c.getColumnIndex("T7"));
            indexArr.add(c.getColumnIndex("T8"));

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forced);

        Intent intent = getIntent();
        String viewClicked = intent.getStringExtra("clickedViewTag");

        POWER_ON = false;

        try {
            db = this.openOrCreateDatabase("FCDB", MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS "
                    + "FCTable"
                    + " (Sno INT, Time INT, T1 FLOAT, T2 FLOAT, T3 FLOAT, T4 FLOAT, T5 FLOAT, T6 FLOAT, T7 FLOAT, T8 FLOAT);");
        } catch (Exception e) {
            Log.e("Error", "Error", e);
        }

        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        numReadings = sharedPref.getInt("numReadings", 0);

//        Toast.makeText(getApplicationContext(),viewClicked,Toast.LENGTH_SHORT).show();

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
            setContentView(R.layout.fc_simulation_layout);
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
            Intent intent1 = new Intent(ForcedConvection.this, DashboardActivity.class);
            intent1.putExtra("exp", 2);
            startActivity(intent1);
            finish();
        }
    }
}