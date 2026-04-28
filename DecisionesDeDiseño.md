# Decisiones de Diseño - BiblioTech

## 1. Arquitectura General

Se implementó una arquitectura en capas separando:

- model: entidades del sistema (Libro, Socio, Prestamo)
- repository: acceso a datos (in-memory)
- service: lógica de negocio
- exception: manejo de errores
- Main: interfaz CLI

Esto permite un sistema desacoplado y mantenible.

---

## 2. Uso de Java Moderno

Se utilizaron:

- Records para entidades inmutables (Libro, Ebook, LibroFisico)
- Optional para evitar null
- Streams para búsquedas

---

## 3. Principios SOLID

- SRP: cada clase tiene una responsabilidad
- OCP: se pueden agregar nuevos tipos sin modificar código existente
- LSP: Ebook y LibroFisico funcionan como Recurso
- ISP: repositorios específicos por entidad
- DIP: servicios dependen de interfaces

---

## 4. Repositorios

Se implementó:

- Repository<T, ID> genérico
- LibroRepository
- SocioRepository
- PrestamoRepository

Implementación en memoria con Map.

---

## 5. Lógica de Negocio

Servicios:

- LibroService
- SocioService
- PrestamoService

Incluyen validaciones como:
- existencia de datos
- límite de préstamos
- cálculo de retraso

---

## 6. Manejo de Errores

Se creó una jerarquía de excepciones:

- BibliotecaException
- LibroNoEncontradoException
- SocioNoEncontradoException
- LimitePrestamosException
- ValidacionException

---

## 7. Polimorfismo

Se implementó la interfaz Recurso:

- Libro
- LibroFisico
- Ebook

---

## 8. Main (CLI)

Permite:

- registrar libros
- registrar socios
- préstamos
- devoluciones
- listados

Sin lógica de negocio, solo orquestación.

---

## 9. Conclusión

El sistema cumple con:

- arquitectura en capas
- uso de Java moderno
- principios SOLID
- desacoplamiento

Esto lo hace mantenible y escalable.