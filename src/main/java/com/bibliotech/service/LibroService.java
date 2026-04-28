package com.bibliotech.service;

import com.bibliotech.exception.BibliotecaException;
import com.bibliotech.exception.LibroNoEncontradoException;
import com.bibliotech.model.Ebook;
import com.bibliotech.model.Libro;
import com.bibliotech.model.LibroFisico;
import com.bibliotech.model.Recurso;
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

    public void registrarLibroFisico(String isbn, String titulo, String autor, int anio, String categoria, String ubicacion) {
        LibroFisico libro = new LibroFisico(isbn, titulo, autor, anio, categoria, ubicacion);
        libroRepo.guardar(libro);
    }

    public void registrarEbook(String isbn, String titulo, String autor, int anio, String categoria, String formato) {
        Ebook ebook = new Ebook(isbn, titulo, autor, anio, categoria, formato);
        libroRepo.guardar(ebook);
    }

    public Recurso buscarPorIsbn(String isbn) throws BibliotecaException {
        return libroRepo.buscarPorId(isbn)
                .orElseThrow(() -> new LibroNoEncontradoException("No se encontró ningún libro con ISBN: " + isbn));
    }

    public List<Recurso> buscarPorTitulo(String titulo) {
        return libroRepo.buscarPorTitulo(titulo);
    }

    public List<Recurso> buscarPorAutor(String autor) {
        return libroRepo.buscarPorAutor(autor);
    }

    public List<Recurso> buscarPorCategoria(String categoria) {
        return libroRepo.buscarPorCategoria(categoria);
    }

    public List<Recurso> listarRecursos() {
        return libroRepo.buscarTodos();
    }
}