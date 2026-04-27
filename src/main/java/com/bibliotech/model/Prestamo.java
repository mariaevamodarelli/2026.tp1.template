package com.bibliotech.model;

import java.time.LocalDate;
import java.util.Optional;

public class Prestamo {

    private final int id;
    private final Libro libro;
    private final Socio socio;
    private final LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;

    public Prestamo(int id, Libro libro, Socio socio, LocalDate fechaPrestamo) {
        this.id = id;
        this.libro = libro;
        this.socio = socio;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = null;
    }

    public int getId() {
        return id;
    }

    public Libro getLibro() {
        return libro;
    }

    public Socio getSocio() {
        return socio;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public Optional<LocalDate> getFechaDevolucion() {
        return Optional.ofNullable(fechaDevolucion);
    }

    public void registrarDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }
}