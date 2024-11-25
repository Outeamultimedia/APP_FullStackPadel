package com.example.clubdiversion.ui.login;

import android.content.Context;

import com.example.clubdiversion.data.network.ApiService;
import com.example.clubdiversion.data.network.RetrofitClient;
import com.example.clubdiversion.data.entities.LoginRequest;
import com.example.clubdiversion.data.entities.LoginResponse;
import com.example.clubdiversion.data.repository.UserRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;

public class LoginPresenter implements LoginContract.Presenter {
    private final LoginContract.View view;
    private final UserRepository userRepository;
    private final ApiService apiService;


    // Constructor modificado para inyectar dependencias
    public LoginPresenter(LoginContract.View view, UserRepository userRepository, ApiService apiService) {
        this.view = view;
        this.userRepository = userRepository;
        this.apiService = apiService;
    }

    @Override
    public void doLogin(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            view.showLoginError("Completa todos los campos");
            return;
        }

        view.showProgress();

        LoginRequest loginRequest = new LoginRequest(username, password);

        apiService.login(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                view.hideProgress();

                if (response.isSuccessful() && response.body() != null) {
                    boolean isSaved = userRepository.saveUser(response.body());
                    if (isSaved) {
                        view.showLoginSuccess();
                        view.navigateToHome();
                    } else {
                        view.showLoginError("Error al guardar el usuario en la base de datos");
                    }
                } else {
                    view.showLoginError("Credenciales inválidas");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                view.hideProgress();
                view.showLoginError("Error: " + t.getMessage());
            }
        });
    }

}

