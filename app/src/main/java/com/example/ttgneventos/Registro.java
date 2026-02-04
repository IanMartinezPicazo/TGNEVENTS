package com.example.ttgneventos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Registro extends AppCompatActivity implements View.OnClickListener {
    private EditText Nombre_usuario;
    private EditText Correo_nuevo;
    private EditText Password_nueva;
    private Button Registrarse;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Nombre_usuario = findViewById(R.id.Nombre);
        Correo_nuevo = findViewById(R.id.Correo_nuevo);
        Password_nueva = findViewById(R.id.Password_nueva);
        Registrarse = findViewById(R.id.Registrarse);
        Registrarse.setOnClickListener(this);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void onClick(View v) {
        String nombre = Nombre_usuario.getText().toString().trim();
        String correo = Correo_nuevo.getText().toString().trim();String password = Password_nueva.getText().toString().trim();

        if (v.getId() == R.id.Registrarse) {

            // 1. Validar campos vacíos
            if (nombre.isEmpty()) {
                Nombre_usuario.setError("El nombre es obligatorio");
                return;
            }
            if (correo.isEmpty()) {
                Correo_nuevo.setError("El correo es obligatorio");
                return;
            }
            if (password.isEmpty()) {
                Password_nueva.setError("La contraseña es obligatoria");
                return;
            }

            // 2. Validar formato de correo
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                Correo_nuevo.setError("Formato de correo no válido");
                return;
            }

            // 3. Validar seguridad de contraseña
            if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
                Password_nueva.setError("Contraseña demasiado débil (mínimo 8 caracteres, Mayús, Minús, Número y Especial)");
                return;
            }

                // AQUÍ es donde realmente añadirías el usuario a tu lista persistente
                mAuth.createUserWithEmailAndPassword(correo, password).addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                //1. Recibe el ID asignado por BD
                                String uid = mAuth.getCurrentUser().getUid();

                                //2. Crea el usuario en la base de datos
                                Usuario nuevoUsuario = new Usuario(nombre, correo, password);
                                nuevoUsuario.setAdmin(false);

                                db = FirebaseFirestore.getInstance();
                                //3. Guarda el usuario en la base de datos
                                db.collection("Usuarios").document(uid).set(nuevoUsuario)
                                        .addOnSuccessListener(aVoid -> {
                                            // El usuario se ha registrado correctamente
                                            Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                                            mAuth.signOut();
                                            Intent intent = new Intent(this, Login.class);
                                            startActivity(intent);
                                            finish();

                                        }
                                    ).addOnFailureListener(e -> {
                                            // Error al guardar el usuario en la base de datos
                                            Toast.makeText(this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show();
                                        });


                            }else if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(this, "Error: Este correo ya está registrado", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                });

        }
    }
}