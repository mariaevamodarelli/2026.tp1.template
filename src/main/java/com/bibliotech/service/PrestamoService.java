package com.bibliotech.service;

import com.bibliotech.exception.BibliotecaException;
import com.bibliotech.exception.LibroNoEncontradoException;
import com.bibliotech.exception.LimitePrestamosException;
import com.bibliotech.exception.SocioNoEncontradoException;
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

    public void realizarPrestamo(String isbn, int socioId) throws BibliotecaException {

        Libro libro = libroRepo.buscarPorId(isbn)
                .orElseThrow(() -> new LibroNoEncontradoException("Libro no encontrado"));

        Socio socio = socioRepo.buscarPorId(socioId)
                .orElseThrow(() -> new SocioNoEncontradoException("Socio no encontrado"));

        int prestamosActuales = prestamoRepo.buscarPorSocioId(socioId).size();

        if (prestamosActuales >= socio.getLimitePrestamos()) {
            throw new LimitePrestamosException("Límite de préstamos alcanzado");
        }

        Prestamo prestamo = new Prestamo(
                generarId(),
                libro,
                socio,
                LocalDate.now()
        );

        prestamoRepo.guardar(prestamo);
    }

    public long devolverPrestamo(int prestamoId) throws BibliotecaException {

        Prestamo prestamo = prestamoRepo.buscarPorId(prestamoId)
                .orElseThrow(() -> new BibliotecaException("Préstamo no encontrado"));

        LocalDate fechaDevolucion = LocalDate.now();
        prestamo.registrarDevolucion(fechaDevolucion);

        // Suponemos 7 días de préstamo permitido
        long diasPrestamo = java.time.temporal.ChronoUnit.DAYS.between(
                prestamo.getFechaPrestamo(),
                fechaDevolucion
        );

        long diasRetraso = Math.max(0, diasPrestamo - 7);

        return diasRetraso;
    }
    private int generarId() {
        return prestamoRepo.buscarTodos().size() + 1;
    }
}