package info.androidhive.hmtvirtuallab;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 *
 * create an instance of this fragment.
 */

public class DetailsFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    ArrayList<TextView> views = new ArrayList<>();
    int EXPERIMENT_NUMBER = 0;

    String viewClicked;     //to store which view was clicked in details fragment.

    TextView theory_btn;
    TextView about_setup_btn;
    TextView procedure_btn;
    TextView simulation_btn;
    TextView observation_table_btn;
    TextView graphs_btn;
    TextView quiz_btn;


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

        EXPERIMENT_NUMBER = this.getArguments().getInt("tag");

        theory_btn = view.findViewById(R.id.theory);
        theory_btn.setOnClickListener(this);

        about_setup_btn = view.findViewById(R.id.aboutSetup);
        about_setup_btn.setOnClickListener(this);

        procedure_btn = view.findViewById(R.id.procedure);
        procedure_btn.setOnClickListener(this);

        simulation_btn = view.findViewById(R.id.simulation);
        simulation_btn.setOnClickListener(this);

        observation_table_btn = view.findViewById(R.id.observationTable);
        observation_table_btn.setOnClickListener(this);

        quiz_btn = view.findViewById(R.id.quiz);
        quiz_btn.setOnClickListener(this);

        graphs_btn = view.findViewById(R.id.graphs);
        graphs_btn.setOnClickListener(this);

        views.add(theory_btn);
        views.add(about_setup_btn);
        views.add(procedure_btn);
        views.add(simulation_btn);
        views.add(observation_table_btn);
        views.add(graphs_btn);
        views.add(quiz_btn);

        return view;
    }

    public void openExperiment(View view)
    {
        if(EXPERIMENT_NUMBER == 1)
        {
            Intent intent = new Intent(getActivity(), ThermalConductivityOfLiquids.class);
            intent.putExtra("clickedViewTag",viewClicked);
            startActivity(intent);
        }
        else if(EXPERIMENT_NUMBER == 2)
        {
            Intent intent = new Intent(getActivity(), Experiment2Activity.class);
            intent.putExtra("clickedViewTag",viewClicked);
            startActivity(intent);
        }
        else if(EXPERIMENT_NUMBER == 3)
        {
            Intent intent = new Intent(getActivity(), NaturalConvection.class);
            intent.putExtra("clickedViewTag",viewClicked);
            startActivity(intent);
        }
        else if(EXPERIMENT_NUMBER == 4)
        {
            Intent intent = new Intent(getActivity(), Experiment4Activity.class);
            intent.putExtra("clickedViewTag",viewClicked);
            startActivity(intent);
        }
        else if(EXPERIMENT_NUMBER == 5)
        {
            Intent intent = new Intent(getActivity(), Experiment5Activity.class);
            intent.putExtra("clickedViewTag",viewClicked);
            startActivity(intent);
        }


    }

    @Override
    public void onClick(View v) {
        TextView clickedView = theory_btn;
        for(TextView temp : views)
        {
            if(temp.equals(v)){
                clickedView = temp;
                break;
            }
        }

        if (theory_btn.equals(clickedView)) {
            //Log.v("LOGGED MESSAGE", "THEORY BUTTON WAS CLICKED!");
            viewClicked = "theory";
        } else if (about_setup_btn.equals(clickedView)) {
            //Log.v("LOGGED MESSAGE", "ABOUT SETUP BUTTON WAS CLICKED!");
            viewClicked = "aboutSetup";
        }
        else if (procedure_btn.equals(clickedView)) {
            //Log.v("LOGGED MESSAGE", "ABOUT SETUP BUTTON WAS CLICKED!");
            viewClicked = "procedure";
        }
        else if (simulation_btn.equals(clickedView)) {
            //Log.v("LOGGED MESSAGE", "ABOUT SETUP BUTTON WAS CLICKED!");
            viewClicked = "simulation";
        }
        else if (observation_table_btn.equals(clickedView)) {
            //Log.v("LOGGED MESSAGE", "ABOUT SETUP BUTTON WAS CLICKED!");
            viewClicked = "observationTable";
        }
        else if (graphs_btn.equals(clickedView)) {
            //Log.v("LOGGED MESSAGE", "ABOUT SETUP BUTTON WAS CLICKED!");
            viewClicked = "graphs";
        }
        else if (quiz_btn.equals(clickedView)) {
            //Log.v("LOGGED MESSAGE", "ABOUT SETUP BUTTON WAS CLICKED!");
            viewClicked = "quiz";
        }

        openExperiment(v);
    }
}