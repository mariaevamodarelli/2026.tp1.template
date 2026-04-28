package com.bibliotech.repository;

import com.bibliotech.model.Recurso;

import java.util.List;

public interface LibroRepository extends Repository<Recurso, String> {

    List<Recurso> buscarPorTitulo(String titulo);

    List<Recurso> buscarPorAutor(String autor);

    List<Recurso> buscarPorCategoria(String categoria);
}