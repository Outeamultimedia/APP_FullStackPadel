package com.example.clubdiversion.ui.main;

import com.example.clubdiversion.data.entities.SocioDB;
import com.example.clubdiversion.data.repository.UserRepository;

public class MainPresenter implements MainContract.Presenter {

    private final MainContract.View view;
    private final UserRepository userRepository;
    public MainPresenter(MainContract.View view, UserRepository userRepository) {
        this.view = view;
        this.userRepository = userRepository;
    }

    @Override
    public void checkUser() {
        SocioDB currentUser = userRepository.getCurrentUser();

        // Log para verificar el estado del usuario
        if (currentUser == null || currentUser.getId() == null) {
            //android.util.Log.d("MainPresenter", "Usuario no autenticado, navegando a Login.");
            view.navigateToLogin();
        } else {
            //android.util.Log.d("MainPresenter", "Usuario autenticado: " + currentUser.getNombre());
            //android.util.Log.d("MainPresenter", "¿Es administrador?: " + currentUser.getSocioAdmin());

            String welcomeMessage = currentUser.getSocioAdmin() ?
                    "Bienvenido Admin: " + currentUser.getNombre() :
                    "Bienvenido Usuario: " + currentUser.getNombre();
            view.showWelcomeMessage(welcomeMessage);

            // Log para verificar visibilidad del menú
            //android.util.Log.d("MainPresenter", "Configurando visibilidad del menú Admin.");
            view.setAdminMenuVisibility(currentUser.getSocioAdmin());
        }
    }


    @Override
    public void logout() {
        boolean isLoggedOut = userRepository.logoutUser();

        if (isLoggedOut) {
            view.navigateToLogin();
        } else {
            view.showWelcomeMessage("No se pudo cerrar sesión");
        }
    }
}
