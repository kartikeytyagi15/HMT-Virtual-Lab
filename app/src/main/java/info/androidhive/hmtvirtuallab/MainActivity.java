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
        exp1_list.add(new ModelClass("qwertyuiiuytreqwert iunbfj ijn auohfa a iuhab","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));
        exp1_list.add(new ModelClass("kjbkvjb aiubfi aoiahe oa aoaf","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));
        exp1_list.add(new ModelClass("qvjawbvkjabvt iunbfj ijn auohfa a iuhab","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));
        exp1_list.add(new ModelClass("aeff qwekjbekwjv t iunbfj ijn auohfa a iuhab","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));
        exp1_list.add(new ModelClass("f fefrgqwffaert uohfa a iuhab","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));
        exp1_list.add(new ModelClass("jiahfi aifbi efbqwertyuiiu a iuhab","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));
        exp1_list.add(new ModelClass("faiubfia  iab uytr qwert iunbfj ijn auohfa a iuhab","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));
        exp1_list.add(new ModelClass("aefkqwertyu uahrfi iauh iert iunbfj ijn auohfa a iuhab","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));
        exp1_list.add(new ModelClass("ai oaih aqwertyuiiuytreqwert iunbfj ijn auohfa a iuhab","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));
        exp1_list.add(new ModelClass("qwertyuiiuytreqwert iunbfj ijn auohfa a iuhab","hbebab","fkajebfa","fkhabef","fjaef","fkajebfa"));


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