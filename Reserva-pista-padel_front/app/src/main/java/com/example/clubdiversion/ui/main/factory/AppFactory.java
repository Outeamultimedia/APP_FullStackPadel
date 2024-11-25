package com.example.clubdiversion.ui.main.factory;

import android.content.Context;

import com.example.clubdiversion.data.network.ApiService;
import com.example.clubdiversion.data.network.RetrofitClient;
import com.example.clubdiversion.data.repository.UserRepository;
import com.example.clubdiversion.ui.main.MainContract;
import com.example.clubdiversion.ui.main.MainPresenter;

public class AppFactory {

    // Crear un presenter de MainActivity
    public static MainContract.Presenter createMainPresenter(MainContract.View view) {
        Context context = (Context) view;
        UserRepository userRepository = new UserRepository(context);
        return new MainPresenter(view, userRepository);
    }
}
