package com.example.eva1_asignotas;

public class Asignatura {
    private String asigId;
    private String asigName;
    private float nota1;
    private float nota2;
    private float nota3;


    public Asignatura() {

    }

    public Asignatura(String asigId, String asigName, float nota1, float nota2, float nota3) {
        this.asigId = asigId;
        this.asigName = asigName;
        this.nota1 = nota1;
        this.nota2 = nota2;
        this.nota3 = nota3;

    }

    public void setAsigId(String asigId) {
        this.asigId = asigId;
    }


    public void setAsigName(String asigName) {
        this.asigName = asigName;
    }

    public void setNota1(float nota1) {
        this.nota1 = nota1;
    }

    public void setNota2(float nota2) {
        this.nota2 = nota2;
    }

    public void setNota3(float nota3) {
        this.nota3 = nota3;
    }

    public String getAsigId() {
        return asigId;
    }



    public String getAsigName() {
        return asigName;
    }

    public Float getNota1() {
        return nota1;
    }

    public Float getNota2() {
        return nota2;
    }

    public Float getNota3() {
        return nota3;
    }
}
