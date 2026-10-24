package com.ejemplo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private InventarioService inventarioService;

    private PedidoService pedidoService;
    private Producto producto;

    @BeforeEach
    void setUp() {
        pedidoService = new PedidoService(inventarioService);
        producto = new Producto("P1", "Monitor", 200.0);
    }

    @Test
    @DisplayName("Debe procesar el pedido si hay stock suficiente")
    void testProcesarPedidoExitoso() {
        when(inventarioService.obtenerStock("P1")).thenReturn(10);

        boolean resultado = pedidoService.procesarPedido(producto, 3);

        assertTrue(resultado);
        verify(inventarioService, times(1)).reducirStock("P1", 3);
    }

    @Test
    @DisplayName("Debe rechazar el pedido si no hay suficiente stock")
    void testProcesarPedidoSinStock() {
        when(inventarioService.obtenerStock("P1")).thenReturn(2);

        boolean resultado = pedidoService.procesarPedido(producto, 5);

        assertFalse(resultado);
        verify(inventarioService, never()).reducirStock(anyString(), anyInt());
    }

    @Test
    @DisplayName("Debe lanzar excepción si la cantidad solicitada es menor o igual a cero")
    void testProcesarPedidoCantidadInvalida() {
        assertThrows(IllegalArgumentException.class, () -> {
            pedidoService.procesarPedido(producto, 0);
        });
    }
}
