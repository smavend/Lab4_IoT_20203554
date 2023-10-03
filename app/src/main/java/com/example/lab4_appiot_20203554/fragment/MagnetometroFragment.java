package com.example.lab4_appiot_20203554.fragment;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab4_appiot_20203554.R;
import com.example.lab4_appiot_20203554.databinding.FragmentAcelerometroBinding;
import com.example.lab4_appiot_20203554.databinding.FragmentMagnetometroBinding;
import com.example.lab4_appiot_20203554.entity.Result;
import com.example.lab4_appiot_20203554.recyclerView.MagnetometroAdapter;
import com.example.lab4_appiot_20203554.viewModel.ResultViewModel;

import java.util.ArrayList;
import java.util.List;

public class MagnetometroFragment extends Fragment implements SensorEventListener {

    FragmentMagnetometroBinding binding;
    private SensorManager sensorManager;
    private Sensor sensor;
    private List<Result> listResults = new ArrayList<>();
    private RecyclerView recyclerView;
    private MagnetometroAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMagnetometroBinding.inflate(inflater, container, false);

        ResultViewModel resultViewModel = new ViewModelProvider(requireActivity()).get(ResultViewModel.class);
        resultViewModel.getResultMagnet().observe(getViewLifecycleOwner(),result -> {
            listResults.add(result);
            adapter.setLista(listResults);
            adapter.notifyDataSetChanged();
        });

        recyclerView = binding.rvContactos;
        adapter = new MagnetometroAdapter();
        adapter.setContext(getContext());
        adapter.setLista(listResults);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager = (SensorManager) requireContext().getSystemService(requireContext().SENSOR_SERVICE);
        if (sensorManager != null) {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            if (sensor != null) {
                sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (sensorManager != null && sensor != null) {
            sensorManager.unregisterListener(this);
        }

    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            float[] magneticFieldValues = sensorEvent.values;
            float x = magneticFieldValues[0];
            float y = magneticFieldValues[1];

            double angle = Math.atan2(y, x);
            angle = Math.toDegrees(angle);
            if (angle < 0) {
                angle += 360;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}