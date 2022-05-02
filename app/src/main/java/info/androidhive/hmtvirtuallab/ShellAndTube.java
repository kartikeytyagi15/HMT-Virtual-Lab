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
    TextView starting_procedure;
    TextView closing_procedure;
    MathJaxView apparatus;
    MathJaxView equipment;
    ImageView appartus_setup;
    ImageView internal;
    TextView simulation_procedure;
    TextView simulation_title;

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
    String objective_text = "To determine the heat exchanger effectiveness for shell and tube heat exchanger";
    String intro_text= "<p align=\"justify\" style = \"font-family: Arial Rounded MT; font-size: 18px; font-style:bold; font-weight: 400;color:#707070\">\n"+
            "$\\bullet$ Shell and Tube Heat Exchanger as the name proposed, are built around tubes mounted in a cylindrical\n" +
            "shell, the tubes are parallel to the shell. One fluid flows inside the tubes is called the (Tube Side Fluid),\n" +
            "while the other fluid flows across and along the axis of the exchanger in the shell side (Shell Side\n" +
            "Fluid). Shell and Tube Heat Exchanger type exchangers are used in many process industries, as well as\n" +
            "nuclear power stations as condensers, steam generators in pressurised water reactor power plants,\n" +
            "and feed water heaters.";
    String aim_text = "To determine the overall heat transfer coefficient for shell and tube heat exchangers using LMTD and \uD835\uDF16-NTU method. \n" ;
    String theory_text= "<p align=\"justify\" style = \"font-family: Arial Rounded MT; font-size: 18px; font-style:bold; font-weight: 400;color:#707070\">\n"+

            "$\\bullet$ Baffles are usually installed to increase the convection coefficient of the shell-side fluid by inducing\n" +
            "turbulence and a cross-flow velocity component. In addition, the baffles physically support the tubes,\n" +
            "reducing flow-induced tube vibration \n" +"<br>"+
            " <br>$\\bullet$In this experiment double-pass shell-and-tube heat exchanger. The known quantity of hot water from\n" +
            "the geyser enters the bottom tubes, flows through the length of the heat exchanger (pass 1) and then\n" +
            "returns from the upper tubes all through the heat exchanger length (pass 2) and finally goes out of the\n" +
            "heat exchanger. As fluid goes through two passes such a heat exchanger is referred to as a double-pass\n" +
            "shell and tube heat exchanger. Obviously, a double-pass heat exchanger is more effective then the\n" +
            "single pass heat exchanger, the reason being that heat transfer area increases as the length gets\n" +
            "doubled. The cold water (known quantity) from the bottom of the shell flows through a number of\n" +
            "baffle plates and finally leaves the shell through its outlet.\n" +"<br>"+
            " <br>$\\bullet$There are two methods to analyze heat exchanger i) LMTD Method ii) ε-NTU Method\n<br>"
            +
            "<br>"+
            "$\\bullet$ LMTD METHOD:-\n" +
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
            "ε-NTU METHOD:-\n" +
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
    String starting_procedure_text="● This experiment is done for three sets of mass flow rates of cold water and hot water" +
            "flow rate is constant.\n\n" +
            "● Flow of water through two pipes (in inner and outer pipe) is set by opening the valves" +
            "V1 and V2.\n\n" +
            "● Geyser is switched ON.\n\n" +
            "● Using thermocouples, the temperature of entering and leaving cold as well as cold" +
            "water is taken. Digital temperature indicator placed on the control panel is also used" +
            "but only after steady state is reached.\n\n" +
            "● Repeat the experiment for other flow rates of cold water.";
    String close_procedure_text="1. When experiment is over switch OFF the heater switch.\n\n" +
            "2. Switch OFF the mains ON/OFF switch.\n\n" +
            "3. Switch OFF electric supply to the set up.";
    String equipment_text="<p align=\"justify\" style = \"font-family: Arial Rounded MT; font-size: 18px; font-style:bold; font-weight: 400;color:#707070\">\n"+"●Shell and tube set up\n<br>" +
            "● Instant water heater (Geyser)<br>" +
            "● Rotameters/Flowmeters<br>" +
            "● Temperature sensor board to measure the corresponding temperatures.<br>" +
            "● Thermocouples<br>";
    String apparatus_text="<p align=\"justify\" style = \"font-family: Arial Rounded MT; font-size: 18px; font-style:bold; font-weight: 400;color:#707070\">\n"+"1. " +
            "Shell (Coldwater) - Mild steel Di= 210mm Do = 220 mm" +"<br>"+
            "2. Tubes (Hot water) " +
            "Copper " +
            "(k=385W/m-k) " +
            "di = 12.7mm " +
            "do =13.95mm " +"<br>"+
            "3. " +
            "Length of the heat exchanger (L) "+
            "0.5m\n" +"<br>"+
            " 4. " +
            "Number of passes (n)=" +
            " 2" +"<br>"+
            "    5.\n" +
            "Number of tubes=\n" +
            " \n" +
            "32\n" +
            "\n";

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
        starting_procedure=findViewById(R.id.start);
        starting_procedure.setText(starting_procedure_text);
        closing_procedure=findViewById(R.id.close);
        simulation_title=findViewById(R.id.textView6);
        closing_procedure.setText(close_procedure_text);
        simulation_procedure=findViewById(R.id.simulation_procedure_id);
        simulation_procedure.setVisibility(View.GONE);
        simulation_title.setVisibility(View.GONE);

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
        apparatus=findViewById(R.id.apparatus_tv);
        apparatus.setText(apparatus_text);
        equipment=findViewById(R.id.equipment_tv);
        equipment.setText(equipment_text);
        appartus_setup=findViewById(R.id.apparatus_image_iv);
        appartus_setup.setImageResource(R.drawable.st_green_1);
        internal=findViewById(R.id.internal_iv_id);
        internal.setImageResource(R.drawable.shelltubeinternal);
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
        obj_tv.setText(objective_text);
        aim_tv.setText(aim_text);
        theory_tv.setText(theory_text);
        theory2_tv.setVisibility(View.GONE);
        theory2_title.setVisibility(View.GONE);
        intro_text_tv.setText(intro_text);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(countDown != null)
            countDown.cancel();
    }
}
