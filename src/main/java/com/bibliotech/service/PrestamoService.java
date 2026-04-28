package com.bibliotech.service;

import com.bibliotech.exception.BibliotecaException;
import com.bibliotech.exception.LibroNoEncontradoException;
import com.bibliotech.exception.LimitePrestamosException;
import com.bibliotech.exception.SocioNoEncontradoException;
import com.bibliotech.model.Prestamo;
import com.bibliotech.model.Recurso;
import com.bibliotech.model.Socio;
import com.bibliotech.repository.LibroRepository;
import com.bibliotech.repository.PrestamoRepository;
import com.bibliotech.repository.SocioRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import com.bibliotech.exception.LibroNoDisponibleException;

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

        Recurso recurso = libroRepo.buscarPorId(isbn)
                .orElseThrow(() -> new LibroNoEncontradoException("No se encontró ningún libro con ISBN: " + isbn));

        Socio socio = socioRepo.buscarPorId(socioId)
                .orElseThrow(() -> new SocioNoEncontradoException("No se encontró ningún socio con ID: " + socioId));

        // Verificar que el recurso no esté actualmente prestado
        boolean estaDisponible = prestamoRepo.buscarPorIsbnRecurso(isbn).stream()
                .noneMatch(Prestamo::estaActivo);

        if (!estaDisponible) {
            throw new LibroNoDisponibleException("El recurso con ISBN " + isbn + " no está disponible en este momento.");
        }

        // Verificar que el socio no haya alcanzado su límite de préstamos activos
        long prestamosActivos = prestamoRepo.buscarPorSocioId(socioId).stream()
                .filter(Prestamo::estaActivo)
                .count();

        if (prestamosActivos >= socio.getLimitePrestamos()) {
            throw new LimitePrestamosException(
                    "El socio " + socio.getNombre() + " alcanzó su límite de " + socio.getLimitePrestamos() + " préstamos activos."
            );
        }

        Prestamo prestamo = new Prestamo(
                generarId(),
                recurso,
                socio,
                LocalDate.now()
        );

        prestamoRepo.guardar(prestamo);
    }

    public long devolverPrestamo(int prestamoId) throws BibliotecaException {

        Prestamo prestamo = prestamoRepo.buscarPorId(prestamoId)
                .orElseThrow(() -> new BibliotecaException("No se encontró el préstamo con ID: " + prestamoId));

        if (!prestamo.estaActivo()) {
            throw new BibliotecaException("El préstamo con ID " + prestamoId + " ya fue devuelto.");
        }

        LocalDate fechaDevolucion = LocalDate.now();
        prestamo.registrarDevolucion(fechaDevolucion);

        long diasPrestamo = ChronoUnit.DAYS.between(prestamo.getFechaPrestamo(), fechaDevolucion);
        long diasRetraso = Math.max(0, diasPrestamo - 7);

        return diasRetraso;
    }

    public List<Prestamo> listarPrestamos() {
        return prestamoRepo.buscarTodos();
    }

    public List<Prestamo> listarPrestamosPorSocio(int socioId) {
        return prestamoRepo.buscarPorSocioId(socioId);
    }

    private int generarId() {
        return prestamoRepo.buscarTodos().size() + 1;
    }
}