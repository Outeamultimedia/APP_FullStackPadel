package com.example.clubdiversion.ui.login.factory;

import android.content.Context;

import com.example.clubdiversion.data.network.ApiService;
import com.example.clubdiversion.data.network.RetrofitClient;
import com.example.clubdiversion.data.repository.UserRepository;
import com.example.clubdiversion.ui.login.LoginContract;
import com.example.clubdiversion.ui.login.LoginPresenter;

public class LoginPresenterFactory {

    public static LoginPresenter create(Context context, LoginContract.View view) {
        UserRepository userRepository = new UserRepository(context);
        ApiService apiService = RetrofitClient.getApiService();
        return new LoginPresenter(view, userRepository, apiService);
    }
}
