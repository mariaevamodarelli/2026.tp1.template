package com.bibliotech;

import com.bibliotech.exception.BibliotecaException;
import com.bibliotech.model.Prestamo;
import com.bibliotech.model.Recurso;
import com.bibliotech.repository.LibroRepositoryInMemory;
import com.bibliotech.repository.PrestamoRepositoryInMemory;
import com.bibliotech.repository.SocioRepositoryInMemory;
import com.bibliotech.service.LibroService;
import com.bibliotech.service.PrestamoService;
import com.bibliotech.service.SocioService;

import java.util.List;
import java.util.Scanner;

public class Main {

    // Instanciamos los repositorios
    private static final LibroRepositoryInMemory libroRepo     = new LibroRepositoryInMemory();
    private static final SocioRepositoryInMemory socioRepo     = new SocioRepositoryInMemory();
    private static final PrestamoRepositoryInMemory prestamoRepo = new PrestamoRepositoryInMemory();

    // Inyectamos los repositorios en los servicios (Dependency Inversion)
    private static final LibroService    libroService    = new LibroService(libroRepo);
    private static final SocioService    socioService    = new SocioService(socioRepo);
    private static final PrestamoService prestamoService = new PrestamoService(libroRepo, socioRepo, prestamoRepo);

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("   Bienvenido a BiblioTech");
        System.out.println("===========================================");

        boolean ejecutando = true;
        while (ejecutando) {
            mostrarMenuPrincipal();
            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1" -> menuLibros();
                case "2" -> menuSocios();
                case "3" -> menuPrestamos();
                case "0" -> {
                    System.out.println("\nHasta luego!");
                    ejecutando = false;
                }
                default -> System.out.println("[!] Opción inválida. Intente de nuevo.");
            }
        }
    }



    private static void mostrarMenuPrincipal() {
        System.out.println("\n--- MENÚ PRINCIPAL ---");
        System.out.println("1. Gestión de Libros/Recursos");
        System.out.println("2. Gestión de Socios");
        System.out.println("3. Gestión de Préstamos");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void menuLibros() {
        System.out.println("\n--- GESTIÓN DE RECURSOS ---");
        System.out.println("1. Registrar Libro");
        System.out.println("2. Registrar Libro Físico (con ubicación)");
        System.out.println("3. Registrar Ebook");
        System.out.println("4. Buscar por ISBN");
        System.out.println("5. Buscar por título");
        System.out.println("6. Buscar por autor");
        System.out.println("7. Buscar por categoría");
        System.out.println("8. Listar todos los recursos");
        System.out.println("0. Volver");
        System.out.print("Seleccione una opción: ");

        switch (scanner.nextLine().trim()) {
            case "1" -> registrarLibro();
            case "2" -> registrarLibroFisico();
            case "3" -> registrarEbook();
            case "4" -> buscarPorIsbn();
            case "5" -> buscarLibrosPorTitulo();
            case "6" -> buscarLibrosPorAutor();
            case "7" -> buscarLibrosPorCategoria();
            case "8" -> listarRecursos();
            case "0" -> {}
            default  -> System.out.println("[!] Opción inválida.");
        }
    }

    private static void menuSocios() {
        System.out.println("\n--- GESTIÓN DE SOCIOS ---");
        System.out.println("1. Registrar Estudiante");
        System.out.println("2. Registrar Docente");
        System.out.println("3. Listar todos los socios");
        System.out.println("0. Volver");
        System.out.print("Seleccione una opción: ");

        switch (scanner.nextLine().trim()) {
            case "1" -> registrarEstudiante();
            case "2" -> registrarDocente();
            case "3" -> listarSocios();
            case "0" -> {}
            default  -> System.out.println("[!] Opción inválida.");
        }
    }

    private static void menuPrestamos() {
        System.out.println("\n--- GESTIÓN DE PRÉSTAMOS ---");
        System.out.println("1. Realizar préstamo");
        System.out.println("2. Registrar devolución");
        System.out.println("3. Listar todos los préstamos");
        System.out.println("0. Volver");
        System.out.print("Seleccione una opción: ");

        switch (scanner.nextLine().trim()) {
            case "1" -> realizarPrestamo();
            case "2" -> devolverPrestamo();
            case "3" -> listarPrestamos();
            case "0" -> {}
            default  -> System.out.println("[!] Opción inválida.");
        }
    }

    private static void registrarLibro() {
        System.out.println("\n-- Registrar Libro --");
        try {
            System.out.print("ISBN: ");
            String isbn = scanner.nextLine().trim();
            System.out.print("Título: ");
            String titulo = scanner.nextLine().trim();
            System.out.print("Autor: ");
            String autor = scanner.nextLine().trim();
            System.out.print("Año: ");
            int anio = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Categoría: ");
            String categoria = scanner.nextLine().trim();

            libroService.registrarLibro(isbn, titulo, autor, anio, categoria);
            System.out.println("[OK] Libro registrado correctamente.");
        } catch (NumberFormatException e) {
            System.out.println("[!] El año debe ser un número entero.");
        }
    }

    private static void registrarLibroFisico() {
        System.out.println("\n-- Registrar Libro Físico --");
        try {
            System.out.print("ISBN: ");
            String isbn = scanner.nextLine().trim();
            System.out.print("Título: ");
            String titulo = scanner.nextLine().trim();
            System.out.print("Autor: ");
            String autor = scanner.nextLine().trim();
            System.out.print("Año: ");
            int anio = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Categoría: ");
            String categoria = scanner.nextLine().trim();
            System.out.print("Ubicación en biblioteca (ej: Estante A-3): ");
            String ubicacion = scanner.nextLine().trim();

            libroService.registrarLibroFisico(isbn, titulo, autor, anio, categoria, ubicacion);
            System.out.println("[OK] Libro físico registrado correctamente.");
        } catch (NumberFormatException e) {
            System.out.println("[!] El año debe ser un número entero.");
        }
    }

    private static void registrarEbook() {
        System.out.println("\n-- Registrar Ebook --");
        try {
            System.out.print("ISBN: ");
            String isbn = scanner.nextLine().trim();
            System.out.print("Título: ");
            String titulo = scanner.nextLine().trim();
            System.out.print("Autor: ");
            String autor = scanner.nextLine().trim();
            System.out.print("Año: ");
            int anio = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Categoría: ");
            String categoria = scanner.nextLine().trim();
            System.out.print("Formato (ej: PDF, EPUB): ");
            String formato = scanner.nextLine().trim();

            libroService.registrarEbook(isbn, titulo, autor, anio, categoria, formato);
            System.out.println("[OK] Ebook registrado correctamente.");
        } catch (NumberFormatException e) {
            System.out.println("[!] El año debe ser un número entero.");
        }
    }

    private static void buscarPorIsbn() {
        System.out.print("\nIngrese el ISBN a buscar: ");
        String isbn = scanner.nextLine().trim();
        try {
            Recurso r = libroService.buscarPorIsbn(isbn);
            System.out.println("\nRecurso encontrado:");
            imprimirRecurso(r);
        } catch (BibliotecaException e) {
            System.out.println("[!] " + e.getMessage());
        }
    }

    private static void buscarLibrosPorTitulo() {
        System.out.print("\nIngrese el título a buscar: ");
        String titulo = scanner.nextLine().trim();
        List<Recurso> resultados = libroService.buscarPorTitulo(titulo);
        imprimirListaRecursos(resultados);
    }

    private static void buscarLibrosPorAutor() {
        System.out.print("\nIngrese el autor a buscar: ");
        String autor = scanner.nextLine().trim();
        List<Recurso> resultados = libroService.buscarPorAutor(autor);
        imprimirListaRecursos(resultados);
    }

    private static void buscarLibrosPorCategoria() {
        System.out.print("\nIngrese la categoría a buscar: ");
        String categoria = scanner.nextLine().trim();
        List<Recurso> resultados = libroService.buscarPorCategoria(categoria);
        imprimirListaRecursos(resultados);
    }

    private static void listarRecursos() {
        List<Recurso> recursos = libroService.listarRecursos();
        if (recursos.isEmpty()) {
            System.out.println("\nNo hay recursos registrados.");
            return;
        }
        System.out.println("\n-- Recursos registrados --");
        recursos.forEach(Main::imprimirRecurso);
    }



    private static void registrarEstudiante() {
        System.out.println("\n-- Registrar Estudiante --");
        try {
            System.out.print("ID (número entero): ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Nombre completo: ");
            String nombre = scanner.nextLine().trim();
            System.out.print("Email: ");
            String email = scanner.nextLine().trim();
            System.out.print("DNI: ");
            String dni = scanner.nextLine().trim();

            socioService.registrarEstudiante(id, nombre, email, dni);
            System.out.println("[OK] Estudiante registrado correctamente. Límite de préstamos: 3.");
        } catch (NumberFormatException e) {
            System.out.println("[!] El ID debe ser un número entero.");
        } catch (BibliotecaException e) {
            System.out.println("[!] " + e.getMessage());
        }
    }

    private static void registrarDocente() {
        System.out.println("\n-- Registrar Docente --");
        try {
            System.out.print("ID (número entero): ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Nombre completo: ");
            String nombre = scanner.nextLine().trim();
            System.out.print("Email: ");
            String email = scanner.nextLine().trim();
            System.out.print("DNI: ");
            String dni = scanner.nextLine().trim();

            socioService.registrarDocente(id, nombre, email, dni);
            System.out.println("[OK] Docente registrado correctamente. Límite de préstamos: 5.");
        } catch (NumberFormatException e) {
            System.out.println("[!] El ID debe ser un número entero.");
        } catch (BibliotecaException e) {
            System.out.println("[!] " + e.getMessage());
        }
    }

    private static void listarSocios() {
        var socios = socioRepo.buscarTodos();
        if (socios.isEmpty()) {
            System.out.println("\nNo hay socios registrados.");
            return;
        }
        System.out.println("\n-- Socios registrados --");
        socios.forEach(s ->
                System.out.printf("  ID: %-4d | %-20s | DNI: %-10s | Email: %-25s | Tipo: %s%n",
                        s.getId(), s.getNombre(), s.getDni(), s.getEmail(),
                        s.getClass().getSimpleName())
        );
    }



    private static void realizarPrestamo() {
        System.out.println("\n-- Realizar Préstamo --");
        try {
            System.out.print("ISBN del libro/recurso: ");
            String isbn = scanner.nextLine().trim();
            System.out.print("ID del socio: ");
            int socioId = Integer.parseInt(scanner.nextLine().trim());

            prestamoService.realizarPrestamo(isbn, socioId);
            System.out.println("[OK] Préstamo registrado correctamente.");
        } catch (NumberFormatException e) {
            System.out.println("[!] El ID del socio debe ser un número entero.");
        } catch (BibliotecaException e) {
            System.out.println("[!] " + e.getMessage());
        }
    }

    private static void devolverPrestamo() {
        System.out.println("\n-- Registrar Devolución --");
        try {
            System.out.print("ID del préstamo: ");
            int prestamoId = Integer.parseInt(scanner.nextLine().trim());

            long diasRetraso = prestamoService.devolverPrestamo(prestamoId);

            if (diasRetraso == 0) {
                System.out.println("[OK] Devolución registrada. El recurso fue devuelto en término.");
            } else {
                System.out.println("[OK] Devolución registrada.");
                System.out.println("[!] ATENCIÓN: El socio devolvió el recurso con " + diasRetraso + " día(s) de retraso.");
            }
        } catch (NumberFormatException e) {
            System.out.println("[!] El ID del préstamo debe ser un número entero.");
        } catch (BibliotecaException e) {
            System.out.println("[!] " + e.getMessage());
        }
    }

    private static void listarPrestamos() {
        List<Prestamo> prestamos = prestamoService.listarPrestamos();
        if (prestamos.isEmpty()) {
            System.out.println("\nNo hay préstamos registrados.");
            return;
        }
        System.out.println("\n-- Préstamos registrados --");
        for (Prestamo p : prestamos) {
            String estado = p.estaActivo() ? "ACTIVO" : "DEVUELTO (" + p.getFechaDevolucion().orElse(null) + ")";
            System.out.printf("  ID: %-4d | Recurso: %-30s | Socio: %-20s | Desde: %s | Estado: %s%n",
                    p.getId(),
                    p.getRecurso().titulo(),
                    p.getSocio().getNombre(),
                    p.getFechaPrestamo(),
                    estado);
        }
    }



    private static void imprimirRecurso(Recurso r) {
        System.out.printf("  [%s] ISBN: %-15s | Título: %s%n",
                r.getClass().getSimpleName(), r.isbn(), r.titulo());
    }

    private static void imprimirListaRecursos(List<Recurso> lista) {
        if (lista.isEmpty()) {
            System.out.println("No se encontraron resultados.");
        } else {
            lista.forEach(Main::imprimirRecurso);
        }
    }
}
