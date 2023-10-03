package com.example.lab4_appiot_20203554.service;

import com.example.lab4_appiot_20203554.entity.ResultDto;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CredentialsService {
    @GET("/api")
    Call<ResultDto> getResult();
}
