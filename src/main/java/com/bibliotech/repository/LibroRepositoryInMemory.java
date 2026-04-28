package com.bibliotech.repository;

import com.bibliotech.model.Ebook;
import com.bibliotech.model.Libro;
import com.bibliotech.model.LibroFisico;
import com.bibliotech.model.Recurso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class LibroRepositoryInMemory implements LibroRepository {

    private final Map<String, Recurso> recursos = new HashMap<>();

    @Override
    public void guardar(Recurso recurso) {
        recursos.put(recurso.isbn(), recurso);
    }

    @Override
    public Optional<Recurso> buscarPorId(String isbn) {
        return Optional.ofNullable(recursos.get(isbn));
    }

    @Override
    public List<Recurso> buscarTodos() {
        return new ArrayList<>(recursos.values());
    }

    // Helpers para extraer campos comunes según el tipo concreto
    private String getTitulo(Recurso r) {
        return r.titulo();
    }

    private String getAutor(Recurso r) {
        if (r instanceof Libro l)         return l.autor();
        if (r instanceof LibroFisico lf)  return lf.autor();
        if (r instanceof Ebook e)         return e.autor();
        return "";
    }

    private String getCategoria(Recurso r) {
        if (r instanceof Libro l)         return l.categoria();
        if (r instanceof LibroFisico lf)  return lf.categoria();
        if (r instanceof Ebook e)         return e.categoria();
        return "";
    }

    @Override
    public List<Recurso> buscarPorTitulo(String titulo) {
        return recursos.values().stream()
                .filter(r -> getTitulo(r).equalsIgnoreCase(titulo))
                .toList();
    }

    @Override
    public List<Recurso> buscarPorAutor(String autor) {
        return recursos.values().stream()
                .filter(r -> getAutor(r).equalsIgnoreCase(autor))
                .toList();
    }

    @Override
    public List<Recurso> buscarPorCategoria(String categoria) {
        return recursos.values().stream()
                .filter(r -> getCategoria(r).equalsIgnoreCase(categoria))
                .toList();
    }
}