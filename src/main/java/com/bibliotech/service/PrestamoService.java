package com.bibliotech.service;

import com.bibliotech.model.Libro;
import com.bibliotech.model.Prestamo;
import com.bibliotech.model.Socio;
import com.bibliotech.repository.LibroRepository;
import com.bibliotech.repository.PrestamoRepository;
import com.bibliotech.repository.SocioRepository;

import java.time.LocalDate;

public class PrestamoService {

    private final LibroRepository libroRepo;
    private final SocioRepository socioRepo;
    private final PrestamoRepository prestamoRepo;

    public PrestamoService(
            LibroRepository libroRepo,
            SocioRepository socioRepo,
            PrestamoRepository prestamoRepo
    ) {
        this.libroRepo = libroRepo;
        this.socioRepo = socioRepo;
        this.prestamoRepo = prestamoRepo;
    }

    public void realizarPrestamo(String isbn, int socioId) {

        Libro libro = libroRepo.buscarPorId(isbn)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        Socio socio = socioRepo.buscarPorId(socioId)
                .orElseThrow(() -> new RuntimeException("Socio no encontrado"));

        int prestamosActuales = prestamoRepo.buscarPorSocioId(socioId).size();

        if (prestamosActuales >= socio.getLimitePrestamos()) {
            throw new RuntimeException("Límite de préstamos alcanzado");
        }

        Prestamo prestamo = new Prestamo(
                generarId(),
                libro,
                socio,
                LocalDate.now()
        );

        prestamoRepo.guardar(prestamo);
    }

    private int generarId() {
        return prestamoRepo.buscarTodos().size() + 1;
    }
}