package com.bibliotech;

import com.bibliotech.model.*;
        import com.bibliotech.repository.*;
        import com.bibliotech.service.PrestamoService;

public class Main {

    public static void main(String[] args) {

        // repos
        LibroRepository libroRepo = new LibroRepositoryInMemory();
        SocioRepository socioRepo = new SocioRepositoryInMemory();
        PrestamoRepository prestamoRepo = new PrestamoRepositoryInMemory();

        // datos
        Libro libro = new Libro("1", "Java", "Autor", 2020, "Programacion");
        Socio socio = new Estudiante(1, "Maria", "mail@mail.com", "123");

        libroRepo.guardar(libro);
        socioRepo.guardar(socio);

        // service
        PrestamoService service = new PrestamoService(libroRepo, socioRepo, prestamoRepo);

        // acción
        service.realizarPrestamo("1", 1);

        System.out.println("Préstamo realizado");
    }
}