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

public class DoublePipeHex extends AppCompatActivity {
    TextView obj_tv;
    TextView aim_tv;
    MathJaxView intro_tv;
    MathJaxView theory_tv;
    TextView theory_tv2 ;

    TextView start_procedure;
    TextView close_procedure;
    TextView simul_procedure;

    ImageView simulation_iv;
    TextView temp_title_tv;
    TextView temp_tv;
    TextView voltage_tv;
    TextView current_tv;
    int thermocouple_number = 0;



    boolean voltageFlag=true;

    double thermocouple_1 ;
    double thermocouple_2 ;
    double thermocouple_3 ;
    double thermocouple_4 ;
    double thermocouple_5 ;
    double thermocouple_6 ;
    double thermocouple_7 ;
    double thermocouple_8 ;


    //Double pipe HeX
    boolean POWER_ON = false;
    View powerButton;
    TextView mHotTv, mColdTv;
    TextView t_hi_tv, t_ho_tv, t_ci_tv, t_co_tv;
    boolean PARALLEL = true;
    int set_number = 1;
    CountDownTimer countDown;
    double thi,tho,tci,tco;
    double time_elapsed = 0;


    double avg_temp = 30.0;
    int pulses = 4;
    double curr_voltage=50.0;
    double curr_current=0.923;

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

    String objective_text = "To determine the heat exchanger effectiveness for parallel and counter flow arrangements.";
    String aim_text = "To determine the overall heat transfer coefficient for parallel and counter flow heat exchangers using LMTD and \uD835\uDF16-NTU method. \n" ;
    String intro_text = "<p align=\"justify\" style = \"font-family: Arial Rounded MT; font-size: 18px; font-style:bold; font-weight: 400;color:#707070\">\n"+
            "$\\bullet$ The experimental setup consists of two concentric tubes in which fluids pass.\n"+"<br>"+
            "<br>$\\bullet$ The hot fluid is hot water, which is obtained from an electric geyser. \n</br>" +
            "<br>$\\bullet$ Hot water flows through the inner tube, in one direction. The cold fluid is cold water, which flows through the annulus.\n<br>" +
            "<br>$\\bullet$ Control valves are provided so that the direction of cold water can be kept parallel or opposite to that of hot water.  \n<br>"+
            "<br>$\\bullet$ Thus, the heat exchanger can be operated either as a parallel or counterflow heat exchanger. \n<br>"+
            "<br>$\\bullet$ The temperatures are measured with a thermometer  \n<br>"+
            "<br>$\\bullet$ Thus, the heat transfer rate, heat transfer coefficient, LMTD and effectiveness of heat exchanger can be calculated for both parallel and counter flow.\n\n<br>";

    String theory_text= "<p align=\"justify\" style = \"font-family: Arial Rounded MT; font-size: 18px; font-style:bold; font-weight: 400;color:#707070\">\n"+
            "$\\bullet$ Heat exchangers are devices that transfer, or “exchange” heat between two flows (liquid\n" +
            "or gas) via a conductive barrier without physically mixing them. The two fluids are at\n" +
            "different temperatures and separated by a solid wall. Heat exchangers are classified\n" +
            "according to flow arrangement and type of construction.\n" +"<br>"+
            "<br>$\\bullet$ The simplest heat exchanger is\n" +
            "one for which the hot and cold fluids move in the same or opposite directions in a\n" +
            "concentric tube (or double-pipe) construction. In the parallel-flow arrangement the hot\n" +
            "and cold fluids enter at the same end, flow in the same direction, and leave at the same\n" +
            "end. In the counterflow arrangement, the fluids enter at opposite ends, flow in opposite\n" +
            "directions, and leave at opposite ends. Another type is cross flow Heat Exchanger in which\n" +
            "we have a perpendicular direction of flows. They can be finned and unfinned tubular heat\n" +
            "exchangers. \n" +"<br>"+
            " <br>$\\bullet$In order to increase the rate of heat transfer between working fluids, we can introduce  longitudinal fins in the inner tube. They are majorly used in Boilers and compressors,Refrigeration, etc.\n" +"<br>"+
            " <br>$\\bullet$In our experiment we are using instant water heater to heat water, thermometers to measure temperatures, rotameter to measure flow rates. The hot water flows through\n" +
            "inner concentric pipe and cool water flows in the annular region of concentric pipe.\n<br>"
            +
            " <br>$\\bullet$By operating 4 valves in a particular order, we are generating parallel and counter flows.\n" +
            "Like if we open valve_1 ,valve_2 and close valve_3,valve_4 then parallel flow is generated and vice versa. \n<br>" +"<br>"+"<br>"+
            "$\\bullet$LMTD METHOD:-\n" +
            "This method is used when the inlet and outlet temperatures of both hot and cold streams\n" +
            "are known\n" +
            "$$Q = UA\\Delta T{m}$$\n" +
            "Where Q is the Heat Transferred between the hot and cold fluids U is the overall Heat\n" +
            "Transfer Coefficient. A is the total area available for heat exchange ∆\uD835\uDC47m is the log mean\n" +
            "temperature difference\n" +
            "Now for parallel flow ,∆\uD835\uDC47m is given by\n" +
            "$$\\Delta T{m} = \\frac{\\Delta T{1}- \\Delta T{2}}{ln\\frac{\\Delta T{1}}{\\Delta T{2}}}$$\n" +
            "$$\\Delta T{1} = \\Delta T{h,i}- \\Delta T{c,i}\n$$" +
            "$$\\Delta T{2} = \\Delta T{h,o}- \\Delta T{c,o}\n$$" +
            "And for counter flow\n" +
            "$$\\Delta T{m} = \\frac{\\Delta T{1}- \\Delta T{2}}{ln\\frac{\\Delta T{1}}{\\Delta T{2}}}\n$$" +
            "$$\\Delta T{1} = \\Delta T{h,i}- \\Delta T{c,o}\n$$" +
            "$$\\Delta T{2} = \\Delta T{h,o}- \\Delta T{c,i}\n$$" +
            "Where \n, ∆Tℎ,\uD835\uDC56=Inlet Temperature of hot stream\n" +
            "∆Tℎ,\uD835\uDC5C=Outlet Temperature of hot stream\n" +
            "∆T\uD835\uDC50,\uD835\uDC56=Inlet Temperature of hot stream\n" +
            "∆T\uD835\uDC50,\uD835\uDC5C=Outlet Temperature of hot stream\n" +
            "And Overall Heat Transfer Coefficient (U) for Double Pipe Heat Exchanger is given by\n" +
            "$$U = \\frac{1}{A{o}(\\frac{1}{h{i}A{i}} + \\frac{ln\\frac{r{o}}{r{i}}}{2{\\pi}kL} +\n" +
            "\\frac{1}{h{o}A{o}})}$$\n" +
            "With ro and ri being the outer and inner radii of the inner tube of the double pipe heat\n" +
            "exchanger Ao is the outer surface area of the inner tube And L is the length of the inner\n" +
            "tube.\n"+"<br>"+"<br>"+
            "$\\bullet$ ε-NTU METHOD:-\n" +
            "This method is used when only the inlet temperatures of the two streams are given Heat\n" +
            "Exchanger Effectiveness is given by\n" +
            "$$\\epsilon= \\frac{Q{actual}}{Q{maximum}}$$\n" +
            "$$Q{actual}= C{h}(T{h,i} - T{h,o})$$\n" +
            "$$Q{actual}= C{c}(T{c,o} - T{c,i})\n$$" +
            "$$Q{maximum}= C{min}(T{h,i} - T{c,i})$$\n" +
            "And after simplification Effectiveness can be written as\n" +
            " For  parallel  flow  arrangement :\n" +
            "$$\\epsilon= \\frac{1-exp[-NTU(1+C{r})]}{1+C{r}}$$\n" +
            " For counter flow arrangement:\n" +
            "$$\\epsilon= \\frac{1-exp[-NTU(1-C{r})]}{1-C{r}.exp[-NTU(1-C{r})]}$$\n" +
            "Where C{r} = $$\\frac{C{min}}{C{max}}$$\n" +
            "And NTU = $$\\frac{UA}{C{min}}$$"
           ;





    String equipment_reqd = "<p align=\"justify\" style = \"font-family: Arial Rounded MT; font-size: 18px; font-style:bold; font-weight: 400;color:#707070\">\n"+
            "$\\bullet$ Double Pipe Heat Exchanger- Concentric Copper tubes.\n<br>" +
            "<br>$\\bullet$ Instant Water Heater(Geyser).\n</br>" +
            "<br>$\\bullet$ Thermometers - For measuring inlet and outlet temperature for hot and cold fluid.\n</br>" +
            "<br>$\\bullet$ Rotameters - For controlling mass flow rate of hot and cold fluid.\n</br>" +
            "<br>$\\bullet$ Pipe network with valves - For controlling type of flow arrangement (Parallel or Counter flow)..\n</br>" ;

    String apparatus_desc = "<p align=\"justify\" style = \"font-family: Arial Rounded MT; font-size: 18px; font-style:bold; font-weight: 400;color:#707070\">\n"+
            "$\\bullet$ Inner(Copper) tube internal and external diameter(di & do)\n" +
            "10.3mm and 12.7mm\n" +
            "\n\n<br>" +
            "<br>$\\bullet$Outer (GI) tube internal and external diameter(Di & Do)\n" +
            "27.5mm and 33.8mm\n" +
            "\n\n</br>" +
            "<br>$\\bullet$Length of heat exchanger (L)\n" +
            "1m\n" +
            "\n \n</br>" +
            "<br>$\\bullet$ $ Thermometers .\n</br>"+
            "<br>$\\bullet$ $ Digital Stopwatch .\n</br>"+
            "<br>$\\bullet$ $ Instant water heater(Geyser) .\n</br>"+
            "<br>$\\bullet$ $ 2 Rotameters .\n</br>";

    String start_procedure_text="1.Following experiment is performed for parallel and counter flow heat exchanger\n" +
            "arrangements. Three readings are taken for each type of heat exchanger over three sets of\n" +
            "mass flow rates (this mass flow rate is measured for cold water)\n" +
            "2.. Flow rate of hot and cold water is set using rotameters R1 and R2. Before this valves V1\n" +
            "and V2 are opened to set flow in pipes in case of parallel flow arrangement.\n\n" +
            "3. geyser is switched to ON position..\n\n" +
            "4. We now calculate temperatures of entering and leaving hot and cold streams at regular\n" +
            "intervals. Three readings are taken for each case. Here we assume that at time of last entry\n" +
            "steady state has been achieved.\n\n" +
            "5. .Now we change flow rate of hot and cold steams two times. After that all above steps are\n" +
            "repeated.\n\n\n" +
            "6. .This same experimental procedure is repeated for counter flow arrangement. However,\n" +
            "in this case instead of V1 and V2; V3 and V4 are opening while V1 and v2 are closed.\n\n";
    String close_procedure_text="1. When experiment is over switch OFF the heater switch.\n\n" +
            "2. Switch OFF the mains ON/OFF switch.\n\n" +
            "3. Switch OFF electric supply to the set up.";

    String simulation_procedure = "In this simulation, it is assumed that no external device is running in surrounding which can alter the temperature\n\n" +
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

//    public void turnOnHeater(View v)
//    {
////        Log.v("LOGGED MESSAGE", "POWER BUTTON CLICKED");
//        if(!POWER_ON)
//        {  //Power on
//            POWER_ON = true;
//            simulation_iv.setImageResource(R.drawable.wateronhex);
//            temp_title_tv.setVisibility(View.VISIBLE);
//            temp_tv.setVisibility(View.VISIBLE);
//            setTemperature_tv();
//            time_elapsed = 0;
//            countDown = new CountDownTimer(4800000, 500) {  //80 minutes count down, updates every half second
//                @Override
//                public void onTick(long millisUntilFinished) {
//                    time_elapsed += 0.5;
//                    setTemperature_tv();
////                    thermocouple_1 = calculateTemp1(time_elapsed / 60.0);
////                    thermocouple_2 = calculateTemp2(time_elapsed / 60.0);
////                    thermocouple_3 = calculateTemp3(time_elapsed / 60.0);
////                    thermocouple_4 = calculateTemp4(time_elapsed / 60.0);
////                    thermocouple_5 = calculateTemp5(time_elapsed / 60.0);
////                    thermocouple_6 = calculateTemp6(time_elapsed / 60.0);
////                    thermocouple_7 = calculateTemp7(time_elapsed / 60.0);
////                    thermocouple_8 = calculateTemp8(time_elapsed / 60.0);
//                }
//
//                @Override
//                public void onFinish() {
//                    Log.v("Temp","time khatam :"+thermocouple_1);
//                }
//            }.start();
//        }
//        else
//        {
//            //Power off
//            new AlertDialog.Builder(this)
//                    .setIcon(android.R.drawable.ic_dialog_alert)
//                    .setTitle("Warning!")
//                    .setMessage("Are you sure you want to turn off the setup?")
//                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            powerOff();
//                        }
//                    })
//                    .setNegativeButton("No", null)
//                    .show();
//        }
//    }
//    public void powerOff()
//    {
//        POWER_ON = false;
//        time_elapsed = 0;
//        countDown.cancel();
//        simulation_iv.setImageResource(R.drawable.wateroffhex);
//        temp_title_tv.setVisibility(View.INVISIBLE);
//
//        temp_tv.setVisibility(View.INVISIBLE);
//        setInitialtemp(voltageFlag);
//        avg_temp = 29.0;
//        LED_count = 0;
//        LED_ON = false;
//        LED_time = 0;
//        LED_interval = 0;
//    }
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
//            Log.v("Temp",""+pulses);
//            Log.v("Temp", ""+updateTime);

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

//    public void downloadExcel(View v)
//    {
////        String destPath = getApplicationContext().getExternalFilesDir(null).getAbsolutePath();
////        File filePath = new File(destPath+ "/TestingApp/TCL Table.xlsx");
//        File filePath = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
//            filePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)+File.separator+"/HMT Virtual Lab/NC Table.xlsx");
//        }
//
//        if(numReadings == 0)
//        {
//            Toast.makeText(getApplicationContext(),"No readings to save!",Toast.LENGTH_LONG).show();
//            return;
//        }
//        Toast.makeText(getApplicationContext(),"Excel file named 'NC Table' is saved to device internal storage.",Toast.LENGTH_LONG).show();
//
//        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
//        HSSFSheet hssfSheet = hssfWorkbook.createSheet();
//
//        HSSFRow hssfRow = hssfSheet.createRow(0);
//        HSSFCell hssfCell = hssfRow.createCell(0);
//        hssfCell.setCellValue("S.No.");
//        HSSFCell hssfCell1 = hssfRow.createCell(1);
//        hssfCell1.setCellValue("Voltage");
//        HSSFCell hssfCell2 = hssfRow.createCell(2);
//        hssfCell2.setCellValue("Time");
//        HSSFCell hssfCell3 = hssfRow.createCell(3);
//        hssfCell3.setCellValue("T1");
//        HSSFCell hssfCell4 = hssfRow.createCell(4);
//        hssfCell4.setCellValue("T2");
//        HSSFCell hssfCell5 = hssfRow.createCell(5);
//        hssfCell5.setCellValue("T3");
//        HSSFCell hssfCell6 = hssfRow.createCell(6);
//        hssfCell6.setCellValue("T4");
//        HSSFCell hssfCell7 = hssfRow.createCell(7);
//        hssfCell7.setCellValue("T5");
//        HSSFCell hssfCell8 = hssfRow.createCell(8);
//        hssfCell8.setCellValue("T6");
//        HSSFCell hssfCell9 = hssfRow.createCell(9);
//        hssfCell9.setCellValue("T7");
//        HSSFCell hssfCell10 = hssfRow.createCell(10);
//        hssfCell10.setCellValue("T8");
//
//        try {
//            Cursor c = db.rawQuery("SELECT * FROM " + "NCTable", null);
//            ArrayList<Integer> indexArr = new ArrayList<>();
//            indexArr.add(c.getColumnIndex("Sno"));
//            indexArr.add(c.getColumnIndex("Volt"));
//            indexArr.add(c.getColumnIndex("Time"));
//            indexArr.add(c.getColumnIndex("Temp1"));
//            indexArr.add(c.getColumnIndex("Temp2"));
//            indexArr.add(c.getColumnIndex("Temp3"));
//            indexArr.add(c.getColumnIndex("Temp4"));
//            indexArr.add(c.getColumnIndex("Temp5"));
//            indexArr.add(c.getColumnIndex("Temp6"));
//            indexArr.add(c.getColumnIndex("Temp7"));
//            indexArr.add(c.getColumnIndex("Temp8"));
//
//            c.moveToFirst();
//            int rowNum = 1;
//            while(!c.isAfterLast()){
//                HSSFRow row = hssfSheet.createRow(rowNum);
//                for(int itr = 0; itr<indexArr.size(); itr++)
//                {
//                    String value;
//                    if(itr <= 1)
//                        value =  String.valueOf(c.getInt(indexArr.get(itr)));
//                    else
//                        value = String.valueOf(c.getFloat(indexArr.get(itr)));
//                    HSSFCell cell = row.createCell(itr);
//                    cell.setCellValue(value);
//                }
//                c.moveToNext();
//                rowNum++;
//            }
//            c.close();
//        }
//        catch(Exception e) {
//            Log.e("Error", "Error", e);
//        }
//
//        try {
//            if (!filePath.exists()){
//                filePath.createNewFile();
//            }
//
//            FileOutputStream fileOutputStream= new FileOutputStream(filePath);
//            hssfWorkbook.write(fileOutputStream);
//
//            if (fileOutputStream!=null){
//                fileOutputStream.flush();
//                fileOutputStream.close();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

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


    void setFlowRates(){
        if(PARALLEL){
            if(set_number == 1){
                mHotTv.setText("m(hot) = 60lph");
                mColdTv.setText("m(cold) = 62lph");
            }else if(set_number == 2){
                mHotTv.setText("m(hot) = 74lph");
                mColdTv.setText("m(cold) = 36lph");
            }else{
                mHotTv.setText("m(hot) = 72lph");
                mColdTv.setText("m(hot) = 90lph");
            }
        }
        else{
            if(set_number == 1){
                mHotTv.setText("m(hot) = 52lph");
                mColdTv.setText("m(cold) = 56lph");
            }else if(set_number == 2){
                mHotTv.setText("m(hot) = 46lph");
                mColdTv.setText("m(cold) = 42lph");
            }else{
                mHotTv.setText("m(hot) = 74lph");
                mColdTv.setText("m(hot) = 88lph");
            }
        }
    }

    void setTemperatureTv(){
        String text = "T(h,i) = "+String.format(Locale.getDefault(),"%.2f", thi);
        t_hi_tv.setText(text);
        text = "T(h,o) = "+String.format(Locale.getDefault(),"%.2f", tho);
        t_ho_tv.setText(text);

        text = "T(c,i) = "+String.format(Locale.getDefault(),"%.2f", tci);
        t_ci_tv.setText(text);
        text = "T(c,o) = "+String.format(Locale.getDefault(),"%.2f", tco);
        t_co_tv.setText(text);
    }

    double calculate_parallel_set1_tho(double time){
        Random rand = new Random();
        double value = rand.nextDouble() / 10.0;
        if(rand.nextInt(2) == 0) //negative
            value *= -1;
        return -0.04500*time*time + 1.35000*time + 47.00000 + value;
    }
    double calculate_parallel_set1_tco(double time){
        Random rand = new Random();
        double value = rand.nextDouble() / 10.0;
        if(rand.nextInt(2) == 0) //negative
            value *= -1;
        return -0.02000*time*time + 0.60000*time + 32.00000 + value;
    }

    double calculate_parallel_set2_tho(double time){
        Random rand = new Random();
        double value = rand.nextDouble() / 10.0;
        if(rand.nextInt(2) == 0) //negative
            value *= -1;
        return 0.05000*time*time - 1.05000*time + 56.75000 + value;
    }
    double calculate_parallel_set2_tco(double time){
        Random rand = new Random();
        double value = rand.nextDouble() / 10.0;
        if(rand.nextInt(2) == 0) //negative
            value *= -1;
        return 0.00267*Math.pow(time,3) - 0.06000*time*time + 0.23333*time + 37.00000 + value;
    }

    double calculate_parallel_set3_tho(double time){
        Random rand = new Random();
        double value = rand.nextDouble() / 10.0;
        if(rand.nextInt(2) == 0) //negative
            value *= -1;
        return value + 50.0;
    }

    double calculate_parallel_set3_tco(double time){
        Random rand = new Random();
        double value = rand.nextDouble() / 10.0;
        if(rand.nextInt(2) == 0) //negative
            value *= -1;
        return value + 32.0;
    }



    double calculate_counter_set1_tho(double time){
        Random rand = new Random();
        double value = rand.nextDouble() / 10.0;
        if(rand.nextInt(2) == 0) //negative
            value *= -1;
        return -0.00500*time*time + 0.15000*time + 55.00000 + value;
    }
    double calculate_counter_set1_tco(double time){
        Random rand = new Random();
        double value = rand.nextDouble() / 10.0;
        if(rand.nextInt(2) == 0) //negative
            value *= -1;
        return -0.00500*time*time + 0.15000*time + 36.00000 + value;
    }

    double calculate_counter_set2_tho(double time){
        Random rand = new Random();
        double value = rand.nextDouble() / 10.0;
        if(rand.nextInt(2) == 0) //negative
            value *= -1;
        return 0.00500*time*time - 0.15000*time + 61.00000 + value;
    }
    double calculate_counter_set2_tco(double time){
        Random rand = new Random();
        double value = rand.nextDouble() / 10.0;
        if(rand.nextInt(2) == 0) //negative
            value *= -1;
        return 41.0 + value;
    }

    double calculate_counter_set3_tho(double time){
        Random rand = new Random();
        double value = rand.nextDouble() / 10.0;
        if(rand.nextInt(2) == 0) //negative
            value *= -1;
        return 0.01000*time*time - 0.30000*time + 53.00000 + value;

    }
    double calculate_counter_set3_tco(double time){
        Random rand = new Random();
        double value = rand.nextDouble() / 10.0;
        if(rand.nextInt(2) == 0) //negative
            value *= -1;
        return 0.00500*time*time - 0.15000*time + 33.00000 + value;
    }




    void openSimulation()
    {
        menuVisible =true;
        invalidateOptionsMenu();
        powerButton = findViewById(R.id.powerBtn);
        simulation_iv = findViewById(R.id.simul_setup);
        mHotTv = findViewById(R.id.m_hot_id);
        mColdTv = findViewById(R.id.m_cold_id);
        t_hi_tv = findViewById(R.id.t_hi_id);
        t_ho_tv = findViewById(R.id.t_ho_id);
        t_ci_tv = findViewById(R.id.t_ci_id);
        t_co_tv = findViewById(R.id.t_co_id);
    }

    void tempInit(){
        if(PARALLEL){
            if(set_number == 1){
                thi = 66.0;
                tho = 47.0;
                tco = 29.0;
                tci = 32.0;
            }
            else if(set_number == 2){
                thi = 57.0;
                tho = 57.0;
                tci = 29.0;
                tco = 37.0;
            }
            else{
                thi = 57.0;
                tho = 50.0;
                tci = 29.0;
                tco = 32.0;
            }
        }
        else{
            if(set_number == 1){
                thi = 67.0;
                tho = 55.0;
                tci = 29.0;
                tco = 36.0;
            }
            else if(set_number == 2){
                thi = 76.0;
                tho = 61.0;
                tci = 29.0;
                tco = 41.0;
            }
            else{
                thi = 56.0;
                tho = 53.0;
                tci = 29.0;
                tco = 33.0;
            }
        }
    }
    private void openAboutSetup()
    {
        MathJaxView equipment_tv = findViewById(R.id.equipment_tv);
        MathJaxView apparatus_tv = findViewById(R.id.apparatus_tv);
        ImageView apparatus_iv =findViewById(R.id.apparatus_image_iv);
        apparatus_iv.setImageResource(R.drawable.doublehexsetup);
        equipment_tv.setText(apparatus_desc);
        apparatus_tv.setText(equipment_reqd);
        ImageView internal=findViewById(R.id.internal_iv_id);
        internal.setImageResource(R.drawable.counter_flow);
        TextView intertitle=findViewById(R.id.internal_title_id);
        intertitle.setText("Flow Arrangements");
    }

    public void onStart(View v){
        if(!POWER_ON){
            POWER_ON = true;
            tempInit();
            simulation_iv.setImageResource(R.drawable.wateronhex);

            setFlowRates();
            tempInit();

            menu.findItem(R.id.parallel).setEnabled(false);
            menu.findItem(R.id.counter).setEnabled(false);

            time_elapsed = 0;
            countDown = new CountDownTimer(600000, 500) {  // 10 minutes count down, updates every half second
                @Override
                public void onTick(long millisUntilFinished) {
                    time_elapsed += 0.5;
                    setTemperatureTv();
                    if(PARALLEL){
                        if(set_number == 1){
                            tho = calculate_parallel_set1_tho(time_elapsed/60.0);
                            tco = calculate_parallel_set1_tco(time_elapsed/60.0);
                        }
                        else if(set_number == 2){
                            tho = calculate_parallel_set2_tho(time_elapsed/60.0);
                            tco = calculate_parallel_set2_tco(time_elapsed/60.0);
                        }
                        else {
                            tho = calculate_parallel_set3_tho(time_elapsed/60.0);
                            tco = calculate_parallel_set3_tco(time_elapsed/60.0);
                        }
                    }
                    else{
                        if(set_number == 1){
                            tho = calculate_counter_set1_tho(time_elapsed/60.0);
                            tco = calculate_counter_set1_tco(time_elapsed/60.0);
                        }
                        else if(set_number == 2){
                            tho = calculate_counter_set2_tho(time_elapsed/60.0);
                            tco = calculate_counter_set2_tco(time_elapsed/60.0);
                        }
                        else {
                            tho = calculate_counter_set3_tho(time_elapsed / 60.0);
                            tco = calculate_counter_set3_tco(time_elapsed / 60.0);
                        }
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
            simulation_iv.setImageResource(R.drawable.wateroffhex);
            menu.findItem(R.id.parallel).setEnabled(true);
            menu.findItem(R.id.counter).setEnabled(true);
            countDown.cancel();
        }
    }



//    private void openAboutSetup()
//    {
//        MathJaxView equipment_tv = findViewById(R.id.equipment_tv);
//        MathJaxView apparatus_tv = findViewById(R.id.apparatus_tv);
//        ImageView apparatus_iv =findViewById(R.id.apparatus_image_iv);
//
//        equipment_tv.setText(equipment_reqd);
//        apparatus_tv.setText(apparatus_desc);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu_) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dp_hex_menu, menu_);
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
        if (itemId == R.id.parallel_op1) {
            PARALLEL = true;
            set_number = 1;
            setFlowRates();
            Toast.makeText(this, "Parallel op1", Toast.LENGTH_SHORT).show();
            return true;
        }else if (itemId == R.id.parallel_op2) {
            set_number = 2;
            PARALLEL = true;
            setFlowRates();
            Toast.makeText(this, "Parallel op2", Toast.LENGTH_SHORT).show();
            return true;
        }else if (itemId == R.id.parallel_op3) {
            set_number = 3;
            PARALLEL = true;
            setFlowRates();
            Toast.makeText(this, "Parallel op3", Toast.LENGTH_SHORT).show();
            return true;
        } else if (itemId == R.id.counter_op1) {
            set_number = 1;
            PARALLEL = false;
            setFlowRates();
            Toast.makeText(this, "Counter Flow Arrangement", Toast.LENGTH_SHORT).show();
            return true;
        }else if (itemId == R.id.counter_op2) {
            set_number = 2;
            PARALLEL = false;
            setFlowRates();
            Toast.makeText(this, "Counter Flow Arrangement", Toast.LENGTH_SHORT).show();
            return true;
        }else if (itemId == R.id.counter_op3) {
            set_number = 3;
            PARALLEL = false;
            setFlowRates();
            Toast.makeText(this, "Counter Flow Arrangement", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_double_pipe_hex);

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
            setContentView(R.layout.doublepipehexsimulation);
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
            //openGraphs();
        }
        else if (viewClicked != null && viewClicked.equals("quiz")) {
            setTitle("Quiz");
            Intent intent1 = new Intent(DoublePipeHex.this, DashboardActivity.class);
            intent1.putExtra("exp", 4);
            startActivity(intent1);
            finish();
        }
    }
}
