package info.androidhive.hmtvirtuallab;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    int EXP_1_TAG = 1;
    int EXP_2_TAG = 2;
    int EXP_3_TAG = 3;
    int EXP_4_TAG = 4;
    int EXP_5_TAG = 5;

    CardView liquids_th;
    CardView experiment_2;
    CardView experiment_3;
    CardView experiment_4;
    CardView experiment_5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        File filePath = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            filePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)+File.separator+"HMT Virtual Lab");
        }
        filePath.mkdirs();

        liquids_th = findViewById(R.id.thermalConductivityLiquids);
        experiment_2 = findViewById(R.id.exp2);
        experiment_3= findViewById(R.id.exp3);
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

        experiment_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("tag", EXP_2_TAG);
                DetailsFragment detailsFragment = new DetailsFragment();
                detailsFragment.setArguments(bundle);
                detailsFragment.show(getSupportFragmentManager(), detailsFragment.getTag());
            }
        });
        experiment_3.setOnClickListener(new View.OnClickListener() {
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
}