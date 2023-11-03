package com.example.eva1_asignotas;

import static android.widget.Toast.makeText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.content.Intent;

import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView txtSupro, txtApr;
    EditText edtAsig, edtN1, edtN2, edtN3;
    Button btnAsig, btnSelec, btnPro, btnGuardar;
    Spinner spAsig;
    ArrayAdapter<String> spinnerAdapter;
    DatabaseReference databaseAsignaturas;

    Asignatura asignaturaSelected;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseAsignaturas = FirebaseDatabase.getInstance().getReference("asignaturas");

        edtAsig = (EditText) findViewById(R.id.edtAsig);
        edtN1 = (EditText) findViewById(R.id.edtN1);
        edtN2 = (EditText) findViewById(R.id.edtN2);
        edtN3 = (EditText) findViewById(R.id.edtN3);
        btnAsig = (Button) findViewById(R.id.btnAsig);
        btnSelec = (Button) findViewById(R.id.btnSelec);
        btnPro = (Button) findViewById(R.id.btnPro);
        spAsig = (Spinner) findViewById(R.id.spAsig);
        txtApr = (TextView) findViewById(R.id.txtApr);
        txtSupro = (TextView) findViewById(R.id.txtSuPro);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);

        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAsig.setAdapter(spinnerAdapter);

        actualizarDatosSpinner();

        spAsig.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Se ejecuta cuando se selecciona un elemento en el Spinner
                String asigSelec = spAsig.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Se ejecuta cuando no hay elementos seleccionados (puedes dejarlo vacío si no necesitas manejar esto)
            }
        });
        btnAsig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addAsig();

            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("asignaturas");
                // Suponiendo que tienes el nombre de la asignatura que deseas buscar
                String nombreAsignaturaBuscada = edtAsig.getText().toString();
                // Realiza la consulta para buscar la asignatura por su nombre
                databaseReference.orderByChild("asigName").equalTo(nombreAsignaturaBuscada).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            // Obtiene el ID de la asignatura encontrada
                            String asignaturaId = snapshot.getKey();
                            String newName = edtAsig.getText().toString();

                            float newNota1 = Float.parseFloat(edtN1.getText().toString().trim());
                            float newNota2 = Float.parseFloat(edtN2.getText().toString().trim());
                            float newNota3 = Float.parseFloat(edtN3.getText().toString().trim());
                            float newPromedio = (newNota1+newNota2 + newNota3)/3;

                            updateAsigName(asignaturaId,newName,newNota1,newNota2,newNota3);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




            }
        });

        btnPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        float newNota1 = Float.parseFloat(edtN1.getText().toString().trim());
                        float newNota2 = Float.parseFloat(edtN2.getText().toString().trim());
                        float newNota3 = Float.parseFloat(edtN3.getText().toString().trim());

                        if (newNota1 == 0.0 || newNota2 == 0.0 || newNota3 == 0.0) {
                            // Mostrar Toast específico si nota1 es igual a 0.0
                            makeText(MainActivity.this, "Debe ingresar todas las notas para calcular el promedio", Toast.LENGTH_LONG).show();
                            return; // Sale del método ya que no se debe continuar con el cálculo
                        }

                        float newPromedio = (newNota1+newNota2 + newNota3)/3;
                        String promedioString = String.format("%.1f", newPromedio);

                        txtSupro.setText(promedioString);
                        if (newPromedio < 3.95){
                            txtApr.setText("REPROBADO");
                            txtApr.setTypeface(null, Typeface.BOLD);
                            txtApr.setTextColor(getResources().getColor(R.color.colorReprobado));


                        }else {
                            txtApr.setText("APROBADO");
                            txtApr.setTypeface(null, Typeface.BOLD);
                            txtApr.setTextColor(getResources().getColor(R.color.colorAprobado));
                        }


    }
        });




        btnSelec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String asigSelec = spAsig.getSelectedItem().toString();

                edtAsig.setText("");

                databaseAsignaturas.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot subjectSnapshot : dataSnapshot.getChildren()) {
                            Asignatura asignatura = subjectSnapshot.getValue(Asignatura.class);
                            if (asignatura != null && asignatura.getAsigName().equals(asigSelec)) {
                                //String asignaturaId = asignatura.getAsigId();
                                edtAsig.setText(asignatura.getAsigName());
                                edtN1.setText(String.valueOf(asignatura.getNota1()));
                                edtN2.setText(String.valueOf(asignatura.getNota2()));
                                edtN3.setText(String.valueOf(asignatura.getNota3()));
                                txtSupro.setText("Su promedio es: ");
                                txtApr.setText("y está ");

                                break;


                            }
                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }
        });
    }


    private void actualizarDatosSpinner() {
        spinnerAdapter.clear();

        databaseAsignaturas.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Asignatura asignatura = snapshot.getValue(Asignatura.class);
                if (asignatura != null) {
                    spinnerAdapter.add(asignatura.getAsigName());
                    spinnerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // Puedes agregar lógica si es necesario
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                // Puedes agregar lógica si es necesario
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // Puedes agregar lógica si es necesario
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejar errores en la base de datos
            }
        });
    }




    private void addAsig(){
        String name = edtAsig.getText().toString().trim();

        if(!TextUtils.isEmpty(name)){

            String id = databaseAsignaturas.push().getKey();
            float nota1 = 0.0f;
            float nota2 = 0.0f;
            float nota3 = 0.0f;




            Asignatura asignaturas = new Asignatura(id,name,nota1,nota2 ,nota3);

            databaseAsignaturas.child(id).setValue(asignaturas);
            edtAsig.setText("");
            edtN1.setText("");
            edtN2.setText("");
            edtN3.setText("");


            makeText(this,"Asignatura añadida",Toast.LENGTH_LONG).show();



        }else {
            makeText(this,"Debe ingresar una asignatura", Toast.LENGTH_LONG).show();
        }
    }



    private void updateAsigName(String asigId, String newName, float newNota1, float newNota2, float newNota3) {
        // Verifica que el ID de la asignatura no esté vacío
        if (!TextUtils.isEmpty(asigId)) {
            // Obtiene la referencia a la base de datos de Firebase
            DatabaseReference databaseAsignaturas = FirebaseDatabase.getInstance().getReference().child("asignaturas");

            // Crea un mapa para actualizar solo el campo específico (nombre)
            Map<String, Object> updateData = new HashMap<>();
            updateData.put("asigName", newName);
            updateData.put("nota1",newNota1);
            updateData.put("nota2",newNota2);
            updateData.put("nota3",newNota3);

            // Actualiza el nombre de la asignatura en la base de datos
            databaseAsignaturas.child(asigId).updateChildren(updateData)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Éxito al actualizar
                            makeText(MainActivity.this, "Notas guardadas exitosamente", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Fallo al actualizar
                            makeText(MainActivity.this, "Error al actualizar el nombre de la asignatura", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            // ID de la asignatura está vacío
            makeText(this, "ID de la asignatura no válido", Toast.LENGTH_SHORT).show();
        }
    }
};


