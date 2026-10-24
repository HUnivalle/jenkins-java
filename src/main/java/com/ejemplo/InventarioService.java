package com.ejemplo;

public interface InventarioService {
    int obtenerStock(String productoId);
    void reducirStock(String productoId, int cantidad);
}
