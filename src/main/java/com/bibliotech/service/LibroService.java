package com.bibliotech.service;

import com.bibliotech.exception.BibliotecaException;
import com.bibliotech.exception.LibroNoEncontradoException;
import com.bibliotech.model.Libro;
import com.bibliotech.repository.LibroRepository;

import java.util.List;

public class LibroService {

    private final LibroRepository libroRepo;

    public LibroService(LibroRepository libroRepo) {
        this.libroRepo = libroRepo;
    }

    public void registrarLibro(String isbn, String titulo, String autor, int anio, String categoria) {
        Libro libro = new Libro(isbn, titulo, autor, anio, categoria);
        libroRepo.guardar(libro);
    }

    public Libro buscarPorIsbn(String isbn) throws BibliotecaException {
        return libroRepo.buscarPorId(isbn)
                .orElseThrow(() -> new LibroNoEncontradoException("Libro no encontrado"));
    }

    public List<Libro> buscarPorTitulo(String titulo) {
        return libroRepo.buscarPorTitulo(titulo);
    }

    public List<Libro> buscarPorAutor(String autor) {
        return libroRepo.buscarPorAutor(autor);
    }

    public List<Libro> buscarPorCategoria(String categoria) {
        return libroRepo.buscarPorCategoria(categoria);
    }

    public List<Libro> listarLibros() {
        return libroRepo.buscarTodos();
    }
}