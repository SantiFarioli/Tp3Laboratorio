package com.santisoft.tp3laboratorio.request;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import com.santisoft.tp3laboratorio.model.Usuario;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ApiClient {
    private static File file;

    private static File conectar(Context context) {
        if (file == null) {
            file = new File(context.getFilesDir(), "usuario.obj");
        }
        return file;
    }

    public static boolean guardar(Context context, Usuario usuario) {
        File file = conectar(context);
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(file)))) {
            oos.writeObject(usuario);
            oos.flush();
            return true;
        } catch (IOException e) {
            Toast.makeText(context, "Error guardando usuario: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return false;
        }
    }

    public static Usuario leer(Context context) {
        File file = conectar(context);
        if (!file.exists()) return null;

        try (ObjectInputStream ois = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream(file)))) {
            Usuario usuario = (Usuario) ois.readObject();

            // Convertimos imagenUri a Uri si no es null
            if (usuario.getImagenUri() != null) {
                usuario.setImagen(Uri.parse(usuario.getImagenUri()));
            }

            return usuario;
        } catch (IOException | ClassNotFoundException e) {
            Toast.makeText(context, "Error al leer usuario: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return null;
        }
    }


    // Realizar login
    public static Usuario login(Context context, String mail, String password) {
        Usuario usuario = leer(context);
        if (usuario != null && usuario.getMail().equals(mail) && usuario.getPassword().equals(password)) {
            return usuario;
        }
        return null;
    }
}
