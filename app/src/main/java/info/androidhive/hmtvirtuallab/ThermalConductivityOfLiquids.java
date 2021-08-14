package info.androidhive.hmtvirtuallab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class ThermalConductivityOfLiquids extends AppCompatActivity {

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
        }
        else if(viewClicked != null && viewClicked.equals("aboutSetup"))
        {
            setTitle("About Setup");
            setContentView(R.layout.about_setup_layout);
        }
    }
}