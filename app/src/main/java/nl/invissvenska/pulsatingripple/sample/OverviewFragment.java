package nl.invissvenska.pulsatingripple.sample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class OverviewFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_overview, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.sampleFill).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(OverviewFragment.this)
                        .navigate(R.id.action_OverviewFragment_to_FillFragment);
            }
        });

        view.findViewById(R.id.sampleStroke).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(OverviewFragment.this)
                        .navigate(R.id.action_OverviewFragment_to_StrokeFragment);
            }
        });

        view.findViewById(R.id.sampleCustom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(OverviewFragment.this)
                        .navigate(R.id.action_OverviewFragment_to_CustomFragment);
            }
        });

    }
}