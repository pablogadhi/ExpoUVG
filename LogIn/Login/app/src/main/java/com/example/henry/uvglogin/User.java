package com.example.henry.uvglogin;

/**
 * Created by Henry on 18/04/2017.
 */


public class User {
    private String id;
    private String nombre;
    private String edad;
    private String genero;
    private String email;
    private String apellido;


    public User(String id, String nombre,String apellido,String edad,String genero,String email) {
        this.id = id;
        this.nombre = nombre;
        this.apellido  = apellido;
        this.edad = edad;
        this.genero = genero;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEdad() {
        return edad;
    }

    public String getGenero() {
        return genero;
    }

    public String getEmail() {
        return email;
    }

    public String getApellido(){return apellido;}
}
