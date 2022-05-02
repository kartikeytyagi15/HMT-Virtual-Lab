package info.androidhive.hmtvirtuallab;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.Display;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    int EXP_1_TAG = 1;
    int EXP_2_TAG = 2;
    int EXP_3_TAG = 3;
    int EXP_4_TAG = 4;
    int EXP_5_TAG = 5;

    CardView liquids_th;
    CardView forced_convection;
    CardView natural_convection;
    CardView experiment_4;
    CardView experiment_5;

    public static ArrayList<ModelClass> exp1_list;
    public static ArrayList<ModelClass> exp2_list;
    public static ArrayList<ModelClass> exp3_list;
    public static ArrayList<ModelClass> exp4_list;
    public static ArrayList<ModelClass> exp5_list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        initializeQuestionsList();

        File filePath = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            filePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)+File.separator+"HMT Virtual Lab");
        }
        filePath.mkdirs();

        liquids_th = findViewById(R.id.thermalConductivityLiquids);
        forced_convection = findViewById(R.id.exp2);
        natural_convection= findViewById(R.id.naturalConvection);
        experiment_4 = findViewById(R.id.exp4);
        experiment_5 = findViewById(R.id.exp5);

        liquids_th.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("tag", EXP_1_TAG);
                DetailsFragment detailsFragment = new DetailsFragment();
                detailsFragment.setArguments(bundle);
                detailsFragment.show(getSupportFragmentManager(), detailsFragment.getTag());
            }
        });

        forced_convection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("tag", EXP_2_TAG);
                DetailsFragment detailsFragment = new DetailsFragment();
                detailsFragment.setArguments(bundle);
                detailsFragment.show(getSupportFragmentManager(), detailsFragment.getTag());
            }
        });
        natural_convection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("tag", EXP_3_TAG);
                DetailsFragment detailsFragment = new DetailsFragment();
                detailsFragment.setArguments(bundle);
                detailsFragment.show(getSupportFragmentManager(), detailsFragment.getTag());
            }
        });
        experiment_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("tag", EXP_4_TAG);
                DetailsFragment detailsFragment = new DetailsFragment();
                detailsFragment.setArguments(bundle);
                detailsFragment.show(getSupportFragmentManager(), detailsFragment.getTag());
            }
        });
        experiment_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("tag", EXP_5_TAG);
                DetailsFragment detailsFragment = new DetailsFragment();
                detailsFragment.setArguments(bundle);
                detailsFragment.show(getSupportFragmentManager(), detailsFragment.getTag());
            }
        });

    }

    private void initializeQuestionsList() {
        exp1_list = new ArrayList<>();
        exp1_list.add(new ModelClass("What is the liquid, that is used in the experiment, whose thermal conductivity we have to measure -", "Water", "Glycerine", "Rubbing Alcohol", "Distilled water", "Glycerine"));
        exp1_list.add(new ModelClass("When there exists a temperature gradient, heat transfer occurs  -", "From high temperature region to low temperature region","From low temperature region to high temperature region", "Heat transfer does not occur", "None of the above", "From high temperature region to low temperature region"));
        exp1_list.add(new ModelClass("According to the Fourier’s law of heat conduction the rate of heat transfer through a material is directly proportional to the -", "Area of the surface", "Temperature difference", "Thickness of the sample", "Both a and b", "Both a and b"));
        exp1_list.add(new ModelClass("In the experiment energy transfer occurs through -", "Convection", "Radiation", "Conduction", "Both Conduction and Convection", "Conduction"));
        exp1_list.add(new ModelClass("The number of pulses measured for the experiment -", "1", "2", "3", "4", "4"));
        exp1_list.add(new ModelClass("Flow of energy from the heater is -", "Unidirectional", "Bidirectional", "Both of the above", "None of the above", "Unidirectional"));
        exp1_list.add(new ModelClass("The purpose of cooling plate of water is-", "To ensure unidirectional heat flow through liquid layer", "To keep the apparatus clean", "Both a and b", "It serves no purpose", "To ensure unidirectional heat flow through liquid layer"));
        exp1_list.add(new ModelClass("What is the unit of thermal conductivity (k) -", "W.m/"+(char) 0x00B0+"C", "W/"+ (char) 0x00B0+"C", "W/m"+ (char) 0x00B0+"C", "J", "W/m"+ (char) 0x00B0+"C"));
        exp1_list.add(new ModelClass("In the experiment thermocouple is used to measure the temperature of", "Heater", "Glycerine film", "Water", "Both a and b", "Both a and b"));
        exp1_list.add(new ModelClass("Which of the following assumptions are required to be made for the experiment -", "Heat transfer is 1D along the thickness of the cavity (radially)", "Temperature Gradient across the heater is the same", "Heat transfer happens only through conduction", "All of the above", "All of the above"));


        exp2_list = new ArrayList<>();
        exp2_list.add(new ModelClass("The typical range of Prandtl number for water is","0.004-0.300","1.7-13.7","50-500","2000-1000","1.7-13.7"));
        exp2_list.add(new ModelClass("Which of the following fluid flow conditions has a high heat transfer coefficient?","Free convection in air","Forced convection in air","Free convection in water","Condensation of steam","Condensation of steam"));
        exp2_list.add(new ModelClass("The free convection coefficient is given by h = C 1 d t m/l 1 – 3m The value of exponent for turbulent flow is","0.43","0.33","0.23","-0.33","0.33"));
        exp2_list.add(new ModelClass("For inclined plates we multiply Grashoff number with","Cos 2 α","Sin 2 α","Sin α","Cos α","Cos α"));
        exp2_list.add(new ModelClass("The free convection coefficient is given by h = C 1 d t m/l 1 – 3m The value of exponent for laminar flow is","0.5","0.6","0.7","0.8","0.5"));
        exp2_list.add(new ModelClass("Free correction modulus is given by","p 2 β g c P/µ","p 2 β g c P/k","p 2 β g c P/µ k","p 2 β g c P","p 2 β g c P/µ k"));
        exp2_list.add(new ModelClass("The convective heat transfer coefficient in laminar flow over a flat plate","Increases with distance","Increases if a higher viscosity fluid is used","Increases if a denser fluid is used","Decreases with increase in free stream velocity","Increases if a denser fluid is used"));
        exp2_list.add(new ModelClass("In the experiment thermocouple is used to measure the temperature of", "Heater", "Glycerine film", "Water", "Both a and b", "Both a and b"));
        exp2_list.add(new ModelClass("For inclined plates we multiply Grashoff number with","Cos 2 α","Sin 2 α","Sin α","Cos α","Cos α"));
        exp2_list.add(new ModelClass("The typical range of Prandtl number for water is","0.004-0.300","1.7-13.7","50-500","2000-1000","1.7-13.7"));


        exp3_list = new ArrayList<>();
        exp3_list.add(new ModelClass("The motion of fluid in Natural Convection is induced by -","Buoyancy Force ","Velocity of fluid ","Both A and B ","Magnetic Force","Buoyancy Force "));
        exp3_list.add(new ModelClass("Nusselt number in case of free convection is the function of - ","Reynolds number and Prandtl number","Reynolds number only","Grashoff number only","Grashoff number and Prandtl number","Grashoff number and Prandtl number"));
        exp3_list.add(new ModelClass(" Convections heat transfer takes place between -","A solid surface and a surrounded flowing fluid ","Two solid surface held together ","Two flowing fluids ","A black body","A solid surface and a surrounded flowing fluid "));
        exp3_list.add(new ModelClass("Natural Convection heat transfer coefficients over the surface of a vertical pipe and vertical flat plate for the same height and fluid are equal. What is/are possible reasons for this?","Same height","Both Vertical ","Same Fluid","Same fluid flow pattern ","Same height"));
        exp3_list.add(new ModelClass("The characteristic length for computing Grashof number in the case of horizontal cylinder is - ","The length of cylinder","The diameter of the cylinder","The perimeter of the cylinder ","The radius of the cylinder ","The diameter of the cylinder"));
        exp3_list.add(new ModelClass("For which of these configurations is a minimum temperature difference required for natural convection to set in - ","Fluid near a heated vertical plate ","Fluid near a heated plate inclined at 45 to the vertical ","Fluid over a heated horizontal plate ","Fluid near a heated cylinder","Fluid over a heated horizontal plate "));
        exp3_list.add(new ModelClass("The free convection heat transfer is significantly affected by -","Reynolds number ","Grashof number ","Prandtl number","Stanton number ","Grashof number "));
        exp3_list.add(new ModelClass("In the case of turbulent flow through a horizontal isothermal cylinder of diameter ‘D’, the free convection heat transfer coefficient for the cylinder will - ","Be independent of diameter ","Vary as D3/4","Vary as D1/4","Vary as D1/2","Be independent of diameter "));
        exp3_list.add(new ModelClass("In the experiment we measure the temperature using - ","Thermocouple ","Barometer","Rotameter","Ammeter","Thermocouple "));
        exp3_list.add(new ModelClass("Why the top and bottom of the enclosure containing the vertical pipe are kept open - ","To facilitate undisturbed natural convection ","To keep pressure same as outside ","For observing the experiment better as compared to when kept closed ","To allow heat to escape to ambience ","To facilitate undisturbed natural convection "));

        exp4_list = new ArrayList<>();
        exp4_list.add(new ModelClass("The arithmetic mean temperature difference for a parallel flow heat exchanger is given as","ΔTam = (ΔTi– ΔTe)","ΔTam = (ΔTi+ ΔTe)","ΔTam = (ΔTi– ΔTe) / 2","ΔTam = (ΔTi+ ΔTe) / 2","ΔTam = (ΔTi+ ΔTe) / 2"));
        exp4_list.add(new ModelClass("In parallel-flow heat exchangers,","the exit temperature of hot fluid is always equal to the exit temperature of cold fluid","the exit temperature of hot fluid is always less than the exit temperature of cold fluid","the exit temperature of hot fluid is always more than the exit temperature of cold fluid","we cannot predict the comparison between exit temperatures of hot fluid and cold fluid","the exit temperature of hot fluid is always more than the exit temperature of cold fluid"));
        exp4_list.add(new ModelClass("If in a double pipe heat exchanger, we require to have a constant wall temperature, we use it in a counter-flow direction.","True","False","Can't Say","Sometimes","False"));
        exp4_list.add(new ModelClass("Which is the major mean of heat transfer in a Double Pipe heat exchanger?","Convection","Conduction","Radiation","Combined Convection and Conduction","Combined Convection and Conduction"));
        exp4_list.add(new ModelClass("How many times do we have to calculate for Nusselt number in a Double Pipe Heat Exchanger?","1","2","3","4","2"));
        exp4_list.add(new ModelClass("In an operation when we want to heat a stream of liquid by Steam, we usually keep steam on the annular side and fluid on the inner side.","True","False","Sometimes","Can't Say","True"));
        exp4_list.add(new ModelClass("In an operation where we want to heat a stream of liquid by Steam, we have the option to use extended fins. Then which of the following is best suited?","Steam on the annular side with the fins on the cold liquid side","Steam on the annular side with the fins on the steam side","Steam on the inner side with the fins on the cold liquid side","Steam on the inner side with the fins on the steam side","Steam on the annular side with the fins on the steam side"));
        exp4_list.add(new ModelClass("When gas is used as a fluid in a double pipe heat exchanger, which one of the following is not true?","The gas side has a low heat transfer coefficient","Extended fins are used on the gas side to increase the Heat Transfer coefficient","The gas side heat transfer coefficient is the highest","Fins increase the necessary heat transfer area","The gas side heat transfer coefficient is the highest"));
        exp4_list.add(new ModelClass("Double pipe heat exchangers are used________?","When the heat transfer area required is very low, i.e. (100-200 ft2).","Because it occupies less floor area","Because it is less costly","When the heat transfer area required is very high","When the heat transfer area required is very low, i.e. (100-200 ft2)."));
        exp4_list.add(new ModelClass("Which of the following has the maximum log mean temperature difference for a double pipe heat exchanger?","Counter-Flow","Parallel-Flow","Cross-Flow","Split-Flow","Counter-Flow"));

        exp5_list = new ArrayList<>();
        exp5_list.add(new ModelClass("In a shell and tube heat exchanger, baffles are provided on the shell side to","Improve heat transfer","Provide support for tubes","Prevent stagnation of shell-side fluid","All of these","All of these"));
        exp5_list.add(new ModelClass("Which of the following statements are incorrect about Baffles in a Shell and Tube HE?","Baffles provide mechanical support to the tubes and help them to be in position","Baffles streamline the motion of the fluid on Shell side and hence decrease the turbulence","The most common type of baffle is segmental baffle","Baffles increase the overall heat transfer coefficient on the shell side","Baffles streamline the motion of the fluid on Shell side and hence decrease the turbulence"));
        exp5_list.add(new ModelClass("When a fluid is used in a Shell and Tube heat exchanger, which one of the following is not true?","If the fluid is gas then the gas side heat transfer coefficient is the lowest","Extended fins are used on the shell side to increase the Heat Transfer coefficient","Baffles are provided only to work as fins","Fins increase necessary heat transfer area","Baffles are provided only to work as fins"));
        exp5_list.add(new ModelClass("What can we infer from the name 2-4 Shell and Tube Heat Exchanger?","Partition of the Shell into two and four passes of the tube","Partition of the Shell into two three passes of the tube","One Shell and Four pass of the tube","Partition of the Shell into three four passes of the tube","Partition of the Shell into two and four passes of the tube"));
        exp5_list.add(new ModelClass("How many times do we have to calculate for Pressure drop in a Shell and Tube Heat Exchanger?","1","2","3","4","2"));
        exp5_list.add(new ModelClass("In an operation where we want to heat a stream of liquid by Steam, we have the option to use extended fins. Then which of the following is best suited?","Steam on the shell side with the fins on outer surface of the tube","Steam on the tube side with the fins on outer surface of the tube","Steam on the shell side with the fins on inner surface of the tube","Steam on the tube side with the fins on inner surface of the tube","Steam on the shell side with the fins on outer surface of the tube"));
        exp5_list.add(new ModelClass("When a fluid is used in a Shell and Tube heat exchanger, which one of the following is not true?","If the fluid is gas then the gas side heat transfer coefficient is the lowest","Extended fins are used on the shell side to increase the Heat Transfer coefficient","Baffles are provided only to work as fins","Fins increase necessary heat transfer area","Baffles are provided only to work as fins"));
        exp5_list.add(new ModelClass("Shell side pressure drop in a shell and tube heat exchanger does not depend upon the","baffle spacing & shell diameter.","tube diameter & pitch.","viscosity, density & mass velocity of shell side fluid.","none of these","none of these"));
        exp5_list.add(new ModelClass("For shell and tube heat exchanger, with increasing heat transfer area, the purchased cost per unit heat transfer area","increase","decreases","remains constant","passes through a maxima","passes through a maxima"));
        exp5_list.add(new ModelClass("Air is to be heated by condensing steam. Two heat exchangers are available (i) a shell and tube heat exchanger and (ii) a finned tube heat exchanger. Tube side heat transfer area are equal in both the cases. The recommended arrangement is","finned tube heat exchanger with air inside and steam outside.","finned tube heat exchanger with air outside and steam inside.","shell and tube heat exchanger with air inside tubes and steam on shell side.","shell and tube heat exchanger with air on shell side and steam inside tubes.","finned tube heat exchanger with air outside and steam inside"));
    }
}