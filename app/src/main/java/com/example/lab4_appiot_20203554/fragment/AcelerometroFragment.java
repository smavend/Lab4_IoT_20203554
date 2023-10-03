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
import android.widget.Toast;

import com.example.lab4_appiot_20203554.R;
import com.example.lab4_appiot_20203554.databinding.FragmentAcelerometroBinding;
import com.example.lab4_appiot_20203554.entity.Result;
import com.example.lab4_appiot_20203554.recyclerView.AcelerometroAdapter;
import com.example.lab4_appiot_20203554.viewModel.ResultViewModel;

import java.util.ArrayList;
import java.util.List;

public class AcelerometroFragment extends Fragment implements SensorEventListener {

    FragmentAcelerometroBinding binding;
    private SensorManager sensorManager;
    private Sensor sensor;
    List<Result> listaResults = new ArrayList<>();
    private static String TAG = "msg-test";
    private RecyclerView recyclerView;
    private AcelerometroAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAcelerometroBinding.inflate(inflater, container, false);
        ResultViewModel resultViewModel = new ViewModelProvider(requireActivity()).get(ResultViewModel.class);
        resultViewModel.getResultAcel().observe(getViewLifecycleOwner(),result -> {
            listaResults.add(result);
            adapter.setLista(listaResults);
            adapter.notifyDataSetChanged();
        });

        recyclerView = binding.rvContactos2;
        adapter = new AcelerometroAdapter();
        adapter.setContext(getContext());
        adapter.setLista(listaResults);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return binding.getRoot();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float aceleracion = Math.abs(sensorEvent.values[0]) + Math.abs(sensorEvent.values[1]) + Math.abs(sensorEvent.values[2]);
            if (aceleracion > 15.0f) {
                if (recyclerView.getAdapter() != null) {
                    int lastItemPosition = recyclerView.getAdapter().getItemCount() - 1;
                    if (lastItemPosition >= 0) {
                        recyclerView.smoothScrollToPosition(lastItemPosition);
                    }
                }
                Toast.makeText(requireContext(), "Aceleración: " + aceleracion + " m/s^2", Toast.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager = (SensorManager) requireContext().getSystemService(requireContext().SENSOR_SERVICE);
        if (sensorManager != null) {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
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
}