package com.horizon.backend.controllers;

import com.horizon.backend.models.DocumentoGuia;
import com.horizon.backend.services.DocumentoGuiaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documentos-guia")
// link http://localhost:8080/api/documentos-guia
public class DocumentoGuiaController {

    private final DocumentoGuiaService documentoGuiaService;

    public DocumentoGuiaController(DocumentoGuiaService documentoGuiaService) {
        this.documentoGuiaService = documentoGuiaService;
    }

    @GetMapping("/guia/{idGuia}")
    // Endpoint para obtener los documentos subidos por un guía.
    public List<DocumentoGuia> obtenerPorGuia(@PathVariable Long idGuia) {
        return documentoGuiaService.obtenerPorGuia(idGuia);
    }

    @PostMapping
    // Endpoint para registrar un documento de guía (INE o certificación).
    public DocumentoGuia crearDocumento(@RequestBody DocumentoGuia documento) {
        return documentoGuiaService.crearDocumento(documento);
    }
}
