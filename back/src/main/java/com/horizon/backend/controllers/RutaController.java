package com.horizon.backend.controllers;

import com.horizon.backend.models.Ruta;
import com.horizon.backend.models.ZonaEstadistica;
import com.horizon.backend.services.ZonaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rutas")
// link http://localhost:8080/api/rutas
public class RutaController {

    private final ZonaService zonaService;

    public RutaController(ZonaService zonaService) {
        this.zonaService = zonaService;
    }

    @GetMapping
    // Endpoint para obtener todas las rutas.
    public List<Ruta> obtenerRutas(){
        return zonaService.obtenerTodas();
    }

    @GetMapping("/estadisticas")
    // Endpoint para obtener estadísticas de las zonas.
    public List<ZonaEstadistica> obtenerEstadisticasZonas() {
        return zonaService.obtenerEstadisticas();
    }

    @GetMapping("/{id}")
    // Endpoint para obtener una ruta por su ID.
    public Ruta obtenerRutaPorId(@PathVariable Long id) {
        return zonaService.obtenerPorId(id);
    }

    @PostMapping
    // Endpoint para crear una nueva ruta.
    public Ruta crearRuta(@RequestBody Ruta ruta) {
        return zonaService.guardar(ruta);
    }

    @PutMapping("/{id}")
    // Endpoint para actualizar una ruta existente.
    public Ruta actualizarRuta(@PathVariable Long id, @RequestBody Ruta ruta) {
        return zonaService.actualizar(id, ruta);
    }

    @DeleteMapping("/{id}")
    // Endpoint para eliminar una ruta.
    // Requiere el idGuia para comprobar que quien borra es el dueño.
    public void eliminarRuta(@PathVariable Long id, @RequestParam Long idGuia) {
        zonaService.eliminar(id, idGuia);
    }
}