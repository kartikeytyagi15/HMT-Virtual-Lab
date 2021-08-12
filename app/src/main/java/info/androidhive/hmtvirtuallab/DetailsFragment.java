package info.androidhive.hmtvirtuallab;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 *
 * create an instance of this fragment.
 */

public class DetailsFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    int EXPERIMENT_NUMBER = 0;
    TextView theory_btn;
    TextView about_setup_btn;


    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        theory_btn = view.findViewById(R.id.theory);
        theory_btn.setOnClickListener(this);

        about_setup_btn = view.findViewById(R.id.aboutSetup);
        about_setup_btn.setOnClickListener(this);

        EXPERIMENT_NUMBER = this.getArguments().getInt("tag");
        return view;
    }

    public void openExperiment(View view)
    {
        if(EXPERIMENT_NUMBER == 1)
        {
            Intent intent = new Intent(getActivity(), ThermalConductivityOfLiquids.class);
            startActivity(intent);
        }
        else if(EXPERIMENT_NUMBER == 2)
        {
            Intent intent = new Intent(getActivity(), Experiment2Activity.class);
            startActivity(intent);
        }

    }

    @Override
    public void onClick(View v) {
        openExperiment(v);
    }
}