package com.ejemplo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductoTest {

    @Test
    @DisplayName("Debe calcular el precio con descuento correctamente")
    void testCalcularPrecioConDescuento() {
        Producto producto = new Producto("P100", "Laptop", 1000.0);
        double resultado = producto.calcularPrecioConDescuento(15);
        assertEquals(850.0, resultado, 0.001);
    }

    @Test
    @DisplayName("Debe lanzar excepción si el precio inicial es negativo")
    void testPrecioNegativoLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Producto("P101", "Mouse", -10.0);
        });
    }

    @Test
    @DisplayName("Debe lanzar excepción con porcentaje de descuento inválido")
    void testDescuentoInvalidoLanzaExcepcion() {
        Producto producto = new Producto("P102", "Teclado", 50.0);
        assertThrows(IllegalArgumentException.class, () -> {
            producto.calcularPrecioConDescuento(100);
        });
    }
}
