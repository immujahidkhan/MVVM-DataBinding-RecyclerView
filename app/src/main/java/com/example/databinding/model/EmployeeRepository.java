package com.example.databinding.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.databinding.network.EmployeeDataService;
import com.example.databinding.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeRepository {
    private static final String TAG = "EmployeeRepository";
    private ArrayList<Employee> employees = new ArrayList<>();
    private MutableLiveData<List<Employee>> mutableLiveData = new MutableLiveData<>();

    public EmployeeRepository() {
    }

    public MutableLiveData<List<Employee>> getMutableLiveData() {

        final EmployeeDataService userDataService = RetrofitClient.getService();

        Call<EmployeeDBResponse> call = userDataService.getEmployees();
        call.enqueue(new Callback<EmployeeDBResponse>() {
            @Override
            public void onResponse(@NonNull Call<EmployeeDBResponse> call, @NonNull Response<EmployeeDBResponse> response) {
                EmployeeDBResponse employeeDBResponse = response.body();
                if (employeeDBResponse != null && employeeDBResponse.getEmployee() != null) {
                    employees = (ArrayList<Employee>) employeeDBResponse.getEmployee();
                    mutableLiveData.setValue(employees);
                }
            }

            @Override
            public void onFailure(@NonNull Call<EmployeeDBResponse> call, @NonNull Throwable t) {

            }
        });

        return mutableLiveData;
    }
}