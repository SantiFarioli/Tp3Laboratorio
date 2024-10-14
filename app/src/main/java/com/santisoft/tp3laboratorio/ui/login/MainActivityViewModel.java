package com.santisoft.tp3laboratorio.ui.login;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.santisoft.tp3laboratorio.model.Usuario;
import com.santisoft.tp3laboratorio.request.ApiClient;

public class MainActivityViewModel extends AndroidViewModel {
    private final Context context;
    private final MutableLiveData<Boolean> navegarARegistro = new MutableLiveData<>();
    private final MutableLiveData<String> mensajeMutableLiveData = new MutableLiveData<>();

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<Boolean> getNavegarARegistro() {
        return navegarARegistro;
    }

    public LiveData<String> getMensaje() {
        return mensajeMutableLiveData;
    }

    public void ingresar(String correo, String password) {
        Usuario usuario = ApiClient.login(context, correo, password);
        if (usuario != null) {
            ApiClient.guardar(context, usuario);  // Guardamos el usuario como "activo"
            navegarARegistro.setValue(true);  // Modo edición
        } else {
            mensajeMutableLiveData.setValue("Correo o contraseña incorrectos");
        }
    }

    public void registrar() {
        navegarARegistro.setValue(false);  // Modo registro (campos vacíos)
    }
}
