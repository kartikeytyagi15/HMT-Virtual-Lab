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
        exp2_list.add(new ModelClass("qwertyuiiuytreqwert iunbfj ijn auohfa a iuhab","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));
        exp2_list.add(new ModelClass("kjbkvjb aiubfi aoiahe oa aoaf","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));
        exp2_list.add(new ModelClass("qvjawbvkjabvt iunbfj ijn auohfa a iuhab","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));
        exp2_list.add(new ModelClass("aeff qwekjbekwjv t iunbfj ijn auohfa a iuhab","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));
        exp2_list.add(new ModelClass("f fefrgqwffaert uohfa a iuhab","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));
        exp2_list.add(new ModelClass("jiahfi aifbi efbqwertyuiiu a iuhab","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));
        exp2_list.add(new ModelClass("faiubfia  iab uytr qwert iunbfj ijn auohfa a iuhab","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));
        exp2_list.add(new ModelClass("aefkqwertyu uahrfi iauh iert iunbfj ijn auohfa a iuhab","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));
        exp2_list.add(new ModelClass("ai oaih aqwertyuiiuytreqwert iunbfj ijn auohfa a iuhab","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));
        exp2_list.add(new ModelClass("qwertyuiiuytreqwert iunbfj ijn auohfa a iuhab","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));


        exp3_list = new ArrayList<>();
        exp3_list.add(new ModelClass("qwertyuiiuytreqwert iunbfj ijn auohfa a iuhab","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));
        exp3_list.add(new ModelClass("kjbkvjb aiubfi aoiahe oa aoaf","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));
        exp3_list.add(new ModelClass("qvjawbvkjabvt iunbfj ijn auohfa a iuhab","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));
        exp3_list.add(new ModelClass("aeff qwekjbekwjv t iunbfj ijn auohfa a iuhab","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));
        exp3_list.add(new ModelClass("f fefrgqwffaert uohfa a iuhab","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));
        exp3_list.add(new ModelClass("jiahfi aifbi efbqwertyuiiu a iuhab","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));
        exp3_list.add(new ModelClass("faiubfia  iab uytr qwert iunbfj ijn auohfa a iuhab","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));
        exp3_list.add(new ModelClass("aefkqwertyu uahrfi iauh iert iunbfj ijn auohfa a iuhab","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));
        exp3_list.add(new ModelClass("ai oaih aqwertyuiiuytreqwert iunbfj ijn auohfa a iuhab","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));
        exp3_list.add(new ModelClass("qwertyuiiuytreqwert iunbfj ijn auohfa a iuhab","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));

        exp4_list = new ArrayList<>();
        exp4_list.add(new ModelClass("qwertyuiiuytreqwert iunbfj ijn auohfa a iuhab","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));
        exp4_list.add(new ModelClass("kjbkvjb aiubfi aoiahe oa aoaf","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));
        exp4_list.add(new ModelClass("qvjawbvkjabvt iunbfj ijn auohfa a iuhab","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));
        exp4_list.add(new ModelClass("aeff qwekjbekwjv t iunbfj ijn auohfa a iuhab","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));
        exp4_list.add(new ModelClass("f fefrgqwffaert uohfa a iuhab","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));
        exp4_list.add(new ModelClass("jiahfi aifbi efbqwertyuiiu a iuhab","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));
        exp4_list.add(new ModelClass("faiubfia  iab uytr qwert iunbfj ijn auohfa a iuhab","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));
        exp4_list.add(new ModelClass("aefkqwertyu uahrfi iauh iert iunbfj ijn auohfa a iuhab","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));
        exp4_list.add(new ModelClass("ai oaih aqwertyuiiuytreqwert iunbfj ijn auohfa a iuhab","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));
        exp4_list.add(new ModelClass("qwertyuiiuytreqwert iunbfj ijn auohfa a iuhab","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));

        exp5_list = new ArrayList<>();
        exp5_list.add(new ModelClass("qwertyuiiuytreqwert iunbfj ijn auohfa a iuhab","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));
        exp5_list.add(new ModelClass("kjbkvjb aiubfi aoiahe oa aoaf","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));
        exp5_list.add(new ModelClass("qvjawbvkjabvt iunbfj ijn auohfa a iuhab","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));
        exp5_list.add(new ModelClass("aeff qwekjbekwjv t iunbfj ijn auohfa a iuhab","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));
        exp5_list.add(new ModelClass("f fefrgqwffaert uohfa a iuhab","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));
        exp5_list.add(new ModelClass("jiahfi aifbi efbqwertyuiiu a iuhab","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));
        exp5_list.add(new ModelClass("faiubfia  iab uytr qwert iunbfj ijn auohfa a iuhab","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));
        exp5_list.add(new ModelClass("aefkqwertyu uahrfi iauh iert iunbfj ijn auohfa a iuhab","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));
        exp5_list.add(new ModelClass("ai oaih aqwertyuiiuytreqwert iunbfj ijn auohfa a iuhab","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));
        exp5_list.add(new ModelClass("qwertyuiiuytreqwert iunbfj ijn auohfa a iuhab","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));
    }
}