package com.bibliotech.repository;

import com.bibliotech.model.Prestamo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PrestamoRepositoryInMemory implements PrestamoRepository {

    private final Map<Integer, Prestamo> prestamos = new HashMap<>();

    @Override
    public void guardar(Prestamo prestamo) {
        prestamos.put(prestamo.getId(), prestamo);
    }

    @Override
    public Optional<Prestamo> buscarPorId(Integer id) {
        return Optional.ofNullable(prestamos.get(id));
    }

    @Override
    public List<Prestamo> buscarTodos() {
        return new ArrayList<>(prestamos.values());
    }

    @Override
    public List<Prestamo> buscarPorSocioId(int socioId) {
        return prestamos.values().stream()
                .filter(prestamo -> prestamo.getSocio().getId() == socioId)
                .toList();
    }

    @Override
    public List<Prestamo> buscarPorIsbnLibro(String isbn) {
        return prestamos.values().stream()
                .filter(prestamo -> prestamo.getLibro().isbn().equals(isbn))
                .toList();
    }
}