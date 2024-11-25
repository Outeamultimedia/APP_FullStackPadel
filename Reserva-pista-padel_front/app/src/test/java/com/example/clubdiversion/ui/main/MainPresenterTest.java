package com.example.clubdiversion.ui.main;

import static org.mockito.Mockito.*;

import com.example.clubdiversion.data.entities.SocioDB;
import com.example.clubdiversion.data.repository.UserRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MainPresenterTest {

    @Mock
    private MainContract.View mockView;

    @Mock
    private UserRepository mockUserRepository;

    private MainPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        presenter = new MainPresenter(mockView, mockUserRepository);
    }

    @Test
    public void checkUser_noUserNavigatesToLogin() {
        // Arrange: Configurar que no hay usuario autenticado
        when(mockUserRepository.getCurrentUser()).thenReturn(null);

        // Act: Llamar a checkUser
        presenter.checkUser();

        // Assert: Verificar que se navega al Login
        verify(mockView).navigateToLogin();
        verifyNoMoreInteractions(mockView);
    }

    @Test
    public void checkUser_userExistsShowsWelcomeMessage() {
        // Arrange: Usuario regular no admin
        SocioDB mockUser = new SocioDB(1, "Test User", "Test Address", "1234567890", "test@example.com", false);
        when(mockUserRepository.getCurrentUser()).thenReturn(mockUser);

        // Act: Llamar a checkUser
        presenter.checkUser();

        // Assert: Verificar mensaje de bienvenida y visibilidad del menú admin
        verify(mockView).showWelcomeMessage("Bienvenido Usuario: Test User");
        verify(mockView).setAdminMenuVisibility(false);
        verifyNoMoreInteractions(mockView);
    }

    @Test
    public void checkUser_adminUserShowsAdminMenu() {
        // Arrange: Usuario con privilegios de administrador
        SocioDB mockAdmin = new SocioDB(2, "Admin User", "Admin Address", "0987654321", "admin@example.com", true);
        when(mockUserRepository.getCurrentUser()).thenReturn(mockAdmin);

        // Act: Llamar a checkUser
        presenter.checkUser();

        // Assert: Verificar mensaje de bienvenida y visibilidad del menú admin
        verify(mockView).showWelcomeMessage("Bienvenido Admin: Admin User");
        verify(mockView).setAdminMenuVisibility(true);
        verifyNoMoreInteractions(mockView);
    }

    @Test
    public void logout_successfulLogoutNavigatesToLogin() {
        // Arrange: Simular un logout exitoso
        when(mockUserRepository.logoutUser()).thenReturn(true);

        // Act: Llamar a logout
        presenter.logout();

        // Assert: Verificar que se navega al Login
        verify(mockView).navigateToLogin();
        verifyNoMoreInteractions(mockView);
    }

    @Test
    public void logout_failedLogoutShowsErrorMessage() {
        // Arrange: Simular un fallo al cerrar sesión
        when(mockUserRepository.logoutUser()).thenReturn(false);

        // Act: Llamar a logout
        presenter.logout();

        // Assert: Verificar mensaje de error
        verify(mockView).showWelcomeMessage("No se pudo cerrar sesión");
        verifyNoMoreInteractions(mockView);
    }
}
