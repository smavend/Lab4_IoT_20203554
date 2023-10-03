package com.example.lab4_appiot_20203554;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.lab4_appiot_20203554.databinding.ActivityPrincipalBinding;
import com.example.lab4_appiot_20203554.entity.Result;
import com.example.lab4_appiot_20203554.entity.ResultDto;
import com.example.lab4_appiot_20203554.fragment.AcelerometroFragmentDirections;
import com.example.lab4_appiot_20203554.fragment.MagnetometroFragmentDirections;
import com.example.lab4_appiot_20203554.service.CredentialsService;
import com.example.lab4_appiot_20203554.viewModel.ResultViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PrincipalActivity extends AppCompatActivity {
    ActivityPrincipalBinding binding;
    CredentialsService credentialsService;
    List<Result> listMagnetometro = new ArrayList<>();
    List<Result> listAcelerometro = new ArrayList<>();
    private ResultViewModel resultViewModel;
    NavController navController;
    boolean inMagnet = true;

    private static String TAG = "msg-test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPrincipalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        resultViewModel = new ViewModelProvider(this).get(ResultViewModel.class);

        FragmentManager fragmentManager = getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment) fragmentManager.findFragmentById(R.id.fragmentContainerView);
        navController = navHostFragment.getNavController();
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                inMagnet = navDestination.getId() == R.id.magnetometroFragment;
                if (inMagnet){
                    binding.btnMenu.setText("Ir a Acelerómetro");
                }else {
                    binding.btnMenu.setText("Ir a Magnetómetro");
                }
            }
        });

        binding.btnMenu.setOnClickListener(view -> {
            if (inMagnet){
                navController.navigate(MagnetometroFragmentDirections.fromMagnetToAcel());
            } else {
                navController.navigate(AcelerometroFragmentDirections.fromAcelToMagnet());
            }
        });

        binding.btnAdd.setOnClickListener(view -> {
            binding.btnMenu.setEnabled(false);
            binding.btnAdd.setEnabled(false);
            if (tengoInternet()){
                loadNewResult();
            }else {
                Toast.makeText(this, "Sin internet", Toast.LENGTH_SHORT).show();
            }
            binding.btnMenu.setEnabled(true);
            binding.btnAdd.setEnabled(true);
        });

        binding.btnDetails.setOnClickListener(view -> {
            if (inMagnet){
                new MaterialAlertDialogBuilder(this)
                        .setTitle("Detalles - Magnetómetro")
                        .setMessage("Haga CLICK en 'Añadir' para agregar contactos a su lista. Esta aplicación está usando el MAGNETÓMETRO de su dispositivo. \n\n " +
                                "De esta manera, la lista se mostrará al 100% cuando se apunte al NORTE. Caso contrario, se desvanecerá...")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
            }else {
                new MaterialAlertDialogBuilder(this)
                        .setTitle("Detalles - Acelerómetro")
                        .setMessage("Haga CLICK en 'Añadir' para agregar contactos a su lista. Esta aplicación está usando el ACELERÓMETRO de su dispositivo. \n\n " +
                                "De esta manera, la lista hará scroll hacia abajo, cuando agite su dispositivo.")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
            }
        });
    }

    public void loadNewResult() {
        credentialsService = new Retrofit.Builder()
                .baseUrl("https://randomuser.me")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CredentialsService.class);
        credentialsService.getResult().enqueue(new Callback<ResultDto>() {
            @Override
            public void onResponse(Call<ResultDto> call, Response<ResultDto> response) {
                if (response.isSuccessful()) {
                    ResultDto body = response.body();
                    Result result = body.getResults().get(0);

                    if (inMagnet){
                        resultViewModel.getResultMagnet().setValue(result);
                    }else {
                        resultViewModel.getResultAcel().setValue(result);
                    }

                } else {
                    Log.d(TAG, "response unsuccessful");
                }
            }

            @Override
            public void onFailure(Call<ResultDto> call, Throwable t) {
                Log.d(TAG, "algo pasó!!!");
                Log.d(TAG, t.getMessage());
                t.printStackTrace();
            }
        });
    }
    public boolean tengoInternet() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
        boolean tieneInternet = activeNetworkInfo != null && activeNetworkInfo.isConnected();

        Log.d("msg-test", "Internet: " + tieneInternet);

        return tieneInternet;
    }
}