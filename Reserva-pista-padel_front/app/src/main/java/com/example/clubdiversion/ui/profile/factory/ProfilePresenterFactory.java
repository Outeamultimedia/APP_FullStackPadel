package com.example.clubdiversion.ui.profile.factory;

import android.content.Context;

import com.example.clubdiversion.data.network.ApiService;
import com.example.clubdiversion.data.network.RetrofitClient;
import com.example.clubdiversion.data.repository.UserRepository;
import com.example.clubdiversion.ui.profile.ProfileContract;
import com.example.clubdiversion.ui.profile.ProfilePresenter;

public class ProfilePresenterFactory {
    public static ProfilePresenter create(ProfileContract.View view, Context context) {
        ApiService apiService = RetrofitClient.getApiService();
        UserRepository userRepository = new UserRepository(context); // Crear UserRepository
        return new ProfilePresenter(view, userRepository, apiService); // Pasar UserRepository al constructor
    }
}
