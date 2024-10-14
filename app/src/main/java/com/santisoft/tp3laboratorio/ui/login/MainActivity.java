package com.santisoft.tp3laboratorio.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.santisoft.tp3laboratorio.databinding.ActivityMainBinding;
import com.santisoft.tp3laboratorio.ui.registro.RegistroActivity;

public class MainActivity extends AppCompatActivity {
    private MainActivityViewModel vm;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MainActivityViewModel.class);

        // Observa los eventos de navegación
        vm.getNavegarARegistro().observe(this, isEditing -> {
            Intent intent = new Intent(this, RegistroActivity.class);
            intent.putExtra("isEditing", isEditing);  // Pasamos si es modo edición o no
            startActivity(intent);
        });

        // Observa los mensajes de error
        vm.getMensaje().observe(this, this::mostrarMensajeError);

        // Acción del botón Ingresar
        binding.btLogin.setOnClickListener(v -> {
            String correo = binding.etMail.getText().toString();
            String password = binding.etPass.getText().toString();
            vm.ingresar(correo, password);
        });

        // Acción del botón Registrar
        binding.btRegistrar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegistroActivity.class);
            intent.putExtra("isEditing", false);  // Registrar nuevo usuario (campos vacíos)
            startActivity(intent);
        });

    }

    private void mostrarMensajeError(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}