package com.example.clubdiversion.ui.login;

import static org.mockito.Mockito.*;

import com.example.clubdiversion.data.entities.LoginRequest;
import com.example.clubdiversion.data.entities.LoginResponse;
import com.example.clubdiversion.data.network.ApiService;
import com.example.clubdiversion.data.repository.UserRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenterTest {

    @Mock
    private LoginContract.View mockView;

    @Mock
    private ApiService mockApiService;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private Call<LoginResponse> mockCall;

    private LoginPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        presenter = new LoginPresenter(mockView, mockUserRepository, mockApiService);

        // Configuración genérica de mockApiService.login
        when(mockApiService.login(any(LoginRequest.class))).thenReturn(mockCall);
    }

    private void simulateSuccessfulResponse(LoginResponse response) {
        doAnswer(invocation -> {
            Callback<LoginResponse> callback = invocation.getArgument(0);
            callback.onResponse(mockCall, Response.success(response));
            return null;
        }).when(mockCall).enqueue(any());
    }

    private void simulateErrorResponse(int errorCode) {
        doAnswer(invocation -> {
            Callback<LoginResponse> callback = invocation.getArgument(0);
            callback.onResponse(mockCall, Response.error(errorCode,
                    ResponseBody.create("", MediaType.parse("application/json"))));
            return null;
        }).when(mockCall).enqueue(any());
    }

    private void simulateNetworkError(String errorMessage) {
        doAnswer(invocation -> {
            Callback<LoginResponse> callback = invocation.getArgument(0);
            callback.onFailure(mockCall, new Throwable(errorMessage));
            return null;
        }).when(mockCall).enqueue(any());
    }

    @Test
    public void doLogin_successfulLogin() {
        // Arrange
        String username = "testuser";
        String password = "password123";

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken("test_token");

        simulateSuccessfulResponse(loginResponse);

        when(mockUserRepository.saveUser(any(LoginResponse.class))).thenReturn(true);

        // Act
        presenter.doLogin(username, password);

        // Assert
        verify(mockView).showProgress();
        verify(mockView).hideProgress();
        verify(mockView).showLoginSuccess();
        verify(mockView).navigateToHome();
    }

    @Test
    public void doLogin_invalidCredentials() {
        // Arrange
        String username = "wronguser";
        String password = "wrongpassword";

        simulateErrorResponse(401);

        // Act
        presenter.doLogin(username, password);

        // Assert
        verify(mockView).showProgress();
        verify(mockView).hideProgress();
        verify(mockView).showLoginError("Credenciales inválidas");
    }

    @Test
    public void doLogin_networkError() {
        // Arrange
        String username = "testuser";
        String password = "password123";

        simulateNetworkError("Network error");

        // Act
        presenter.doLogin(username, password);

        // Assert
        verify(mockView).showProgress();
        verify(mockView).hideProgress();
        verify(mockView).showLoginError("Error: Network error");
    }

    @Test
    public void doLogin_errorSavingUser() {
        // Arrange
        String username = "testuser";
        String password = "password123";

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken("test_token");

        simulateSuccessfulResponse(loginResponse);

        when(mockUserRepository.saveUser(any(LoginResponse.class))).thenReturn(false);

        // Act
        presenter.doLogin(username, password);

        // Assert
        verify(mockView).showProgress();
        verify(mockView).hideProgress();
        verify(mockView).showLoginError("Error al guardar el usuario en la base de datos");
    }

    @Test
    public void doLogin_emptyInputs() {
        // Act
        presenter.doLogin("", "");

        // Assert
        verify(mockView, never()).showProgress();
        verify(mockView).showLoginError("Completa todos los campos");
    }
}

