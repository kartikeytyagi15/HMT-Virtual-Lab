package info.androidhive.hmtvirtuallab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

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



    private void openGraphs() {
    }

    private void openObservation() {
    }

    private void openProcedure() {
    }

    private void openSimulation() {
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
        apparatus_iv.setImageResource(R.drawable.forced_red);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forced);

        Intent intent = getIntent();
        String viewClicked = intent.getStringExtra("clickedViewTag");

        Toast.makeText(getApplicationContext(),viewClicked,Toast.LENGTH_SHORT).show();

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
        }

    }
}