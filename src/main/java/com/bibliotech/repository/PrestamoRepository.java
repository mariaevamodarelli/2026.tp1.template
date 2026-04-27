package com.bibliotech.repository;

import com.bibliotech.model.Prestamo;

import java.util.List;

public interface PrestamoRepository extends Repository<Prestamo, Integer> {

    List<Prestamo> buscarPorSocioId(int socioId);

    List<Prestamo> buscarPorIsbnLibro(String isbn);
}