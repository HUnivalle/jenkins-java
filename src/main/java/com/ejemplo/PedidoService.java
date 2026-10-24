package com.ejemplo;

public class PedidoService {
    private final InventarioService inventarioService;

    public PedidoService(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    public boolean procesarPedido(Producto producto, int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
        }

        int stockDisponible = inventarioService.obtenerStock(producto.getId());

        if (stockDisponible >= cantidad) {
            inventarioService.reducirStock(producto.getId(), cantidad);
            return true;
        }

        return false;
    }
}
