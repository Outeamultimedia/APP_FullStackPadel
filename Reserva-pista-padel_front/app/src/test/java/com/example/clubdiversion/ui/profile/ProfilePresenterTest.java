package com.example.clubdiversion.ui.profile;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.clubdiversion.data.entities.LoginResponse;
import com.example.clubdiversion.data.entities.ReservationResponse;
import com.example.clubdiversion.data.entities.SocioDB;
import com.example.clubdiversion.data.network.ApiService;
import com.example.clubdiversion.data.repository.UserRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RunWith(MockitoJUnitRunner.class)
public class ProfilePresenterTest {

    @Mock
    private ProfileContract.View view;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ApiService apiService;

    private ProfilePresenter presenter;

    @Before
    public void setUp() {
        // Crear el presenter con los mocks
        presenter = new ProfilePresenter(view, userRepository, apiService);
    }

    @Test
    public void testRefreshUserProfile_UserNotLoggedIn() {
        when(userRepository.getToken()).thenReturn(null);

        presenter.refreshUserProfile();

        InOrder inOrder = inOrder(view);
        inOrder.verify(view).showLoading();
        inOrder.verify(view).showError("El usuario no ha iniciado sesión");
        inOrder.verify(view).hideLoading();
        verifyNoMoreInteractions(view);
    }

    @Test
    public void testRefreshUserProfile_SuccessfulResponse() {
        String token = "valid_token";
        when(userRepository.getToken()).thenReturn(token);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        loginResponse.setName("Juan Pérez");
        loginResponse.setUsername("jperez");
        loginResponse.setTelefono("123456789");
        loginResponse.setDireccion("Calle Falsa 123");

        Call<LoginResponse> call = mock(Call.class);
        when(apiService.getProfile("Bearer " + token)).thenReturn(call);

        doAnswer(invocation -> {
            Callback<LoginResponse> callback = invocation.getArgument(0);
            callback.onResponse(call, Response.success(loginResponse));
            return null;
        }).when(call).enqueue(any(Callback.class));

        when(userRepository.saveUser(loginResponse)).thenReturn(true);

        presenter.refreshUserProfile();

        InOrder inOrder = inOrder(view, userRepository);
        inOrder.verify(view).showLoading();
        inOrder.verify(view).hideLoading();
        inOrder.verify(view).showProfile("Juan Pérez", "jperez", "123456789", "Calle Falsa 123");
        verify(userRepository).saveUser(loginResponse);
    }

    @Test
    public void testRefreshUserProfile_FailedToUpdateLocalProfile() {
        String token = "valid_token";
        when(userRepository.getToken()).thenReturn(token);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        loginResponse.setName("Juan Pérez");
        loginResponse.setUsername("jperez");
        loginResponse.setTelefono("123456789");
        loginResponse.setDireccion("Calle Falsa 123");

        Call<LoginResponse> call = mock(Call.class);
        when(apiService.getProfile("Bearer " + token)).thenReturn(call);

        doAnswer(invocation -> {
            Callback<LoginResponse> callback = invocation.getArgument(0);
            callback.onResponse(call, Response.success(loginResponse));
            return null;
        }).when(call).enqueue(any(Callback.class));

        when(userRepository.saveUser(loginResponse)).thenReturn(false);

        presenter.refreshUserProfile();

        InOrder inOrder = inOrder(view, userRepository);
        inOrder.verify(view).showLoading();
        inOrder.verify(view).hideLoading();
        inOrder.verify(view).showError("No se pudo actualizar el perfil localmente.");
        verify(userRepository).saveUser(loginResponse);
    }

    @Test
    public void testRefreshUserProfile_ErrorResponse() {
        String token = "valid_token";
        when(userRepository.getToken()).thenReturn(token);

        // Mockear la llamada API
        Call<LoginResponse> call = mock(Call.class);
        when(apiService.getProfile("Bearer " + token)).thenReturn(call);

        // Configurar el comportamiento del mock para simular un error de respuesta
        doAnswer(invocation -> {
            Callback<LoginResponse> callback = invocation.getArgument(0);
            Response<LoginResponse> errorResponse = Response.error(400, okhttp3.ResponseBody.create(null, "Error"));
            callback.onResponse(call, errorResponse);
            return null;
        }).when(call).enqueue(any(Callback.class));

        // Ejecutar el método
        presenter.refreshUserProfile();

        // Verificar las interacciones
        InOrder inOrder = inOrder(view);
        inOrder.verify(view).showLoading();
        inOrder.verify(view).hideLoading();
        inOrder.verify(view).showError("Error al obtener el perfil del usuario.");
    }


    @Test
    public void testRefreshUserProfile_NetworkFailure() {
        String token = "valid_token";
        when(userRepository.getToken()).thenReturn(token);

        Call<LoginResponse> call = mock(Call.class);
        when(apiService.getProfile("Bearer " + token)).thenReturn(call);

        doAnswer(invocation -> {
            Callback<LoginResponse> callback = invocation.getArgument(0);
            callback.onFailure(call, new Throwable("Network error"));
            return null;
        }).when(call).enqueue(any(Callback.class));

        presenter.refreshUserProfile();

        InOrder inOrder = inOrder(view);
        inOrder.verify(view).showLoading();
        inOrder.verify(view).hideLoading();
        inOrder.verify(view).showError("Error de red: Network error");
    }

    @Test
    public void testLoadReservations_UserNotLoggedIn() {
        when(userRepository.getToken()).thenReturn(null);

        presenter.loadReservations();

        InOrder inOrder = inOrder(view);
        inOrder.verify(view).showLoading();
        inOrder.verify(view).showError("El usuario no ha iniciado sesión");
        inOrder.verify(view).hideLoading();
    }

    @Test
    public void testLoadReservations_SuccessfulResponse() {
        String token = "valid_token";
        when(userRepository.getToken()).thenReturn(token);

        Call<List<ReservationResponse>> call = mock(Call.class);
        when(apiService.getReservations("Bearer " + token)).thenReturn(call);

        List<ReservationResponse> reservations = new ArrayList<>();
        reservations.add(new ReservationResponse(1, "user1", "2023-10-10", "Instalación 1", true, "2023-10-01", "2023-10-02"));

        doAnswer(invocation -> {
            Callback<List<ReservationResponse>> callback = invocation.getArgument(0);
            callback.onResponse(call, Response.success(reservations));
            return null;
        }).when(call).enqueue(any(Callback.class));

        presenter.loadReservations();

        InOrder inOrder = inOrder(view);
        inOrder.verify(view).showLoading();
        inOrder.verify(view).hideLoading();
        inOrder.verify(view).showReservations(reservations);
    }

    @Test
    public void testLoadProfile_LocalUserExists() {
        SocioDB localUser = new SocioDB(1, "Juan Pérez", "Calle Falsa 123", "123456789", "jperez", false);
        when(userRepository.getCurrentUser()).thenReturn(localUser);

        presenter.loadProfile();

        InOrder inOrder = inOrder(view);
        inOrder.verify(view).showLoading();
        inOrder.verify(view).showProfile("Juan Pérez", "jperez", "123456789", "Calle Falsa 123");
        inOrder.verify(view).hideLoading();
    }

    @Test
    public void testLoadProfile_NoLocalUser_UserNotLoggedIn() {
        when(userRepository.getCurrentUser()).thenReturn(null);
        when(userRepository.getToken()).thenReturn(null);

        presenter.loadProfile();

        InOrder inOrder = inOrder(view);
        inOrder.verify(view).showLoading();
        inOrder.verify(view).showError("El usuario no ha iniciado sesión");
        inOrder.verify(view).hideLoading();
    }

    @Test
    public void testLoadProfile_NoLocalUser_SuccessfulResponse() {
        // Simular que no hay un usuario local
        when(userRepository.getCurrentUser()).thenReturn(null);
        String token = "valid_token";
        when(userRepository.getToken()).thenReturn(token);

        // Mockear la llamada API
        Call<LoginResponse> call = mock(Call.class);
        when(apiService.getProfile("Bearer " + token)).thenReturn(call);

        // Crear una respuesta simulada
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setName("Juan Pérez");
        loginResponse.setEmail("juan@example.com");
        loginResponse.setTelefono("123456789");
        loginResponse.setDireccion("Calle Falsa 123");

        // Configurar el comportamiento del mock para una respuesta exitosa
        doAnswer(invocation -> {
            Callback<LoginResponse> callback = invocation.getArgument(0);
            callback.onResponse(call, Response.success(loginResponse));
            return null;
        }).when(call).enqueue(any(Callback.class));

        // Ejecutar el método
        presenter.loadProfile();

        InOrder inOrder = inOrder(view);
        inOrder.verify(view).showLoading();
        inOrder.verify(view).hideLoading();
        inOrder.verify(view).showProfile("Juan Pérez", "juan@example.com", "123456789", "Calle Falsa 123");

    }

}
