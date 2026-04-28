package com.bibliotech.repository;

import com.bibliotech.model.Prestamo;

import java.util.List;

public interface PrestamoRepository extends Repository<Prestamo, Integer> {

    List<Prestamo> buscarPorSocioId(int socioId);

    // Busca por el isbn del recurso (LibroFisico, Ebook, o Libro)
    List<Prestamo> buscarPorIsbnRecurso(String isbn);
}