package com.horizon.backend.services;

import com.horizon.backend.models.DocumentoGuia;
import com.horizon.backend.repositories.DocumentoGuiaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DocumentoGuiaService {

    private final DocumentoGuiaRepository documentoGuiaRepository;

    public DocumentoGuiaService(DocumentoGuiaRepository documentoGuiaRepository) {
        this.documentoGuiaRepository = documentoGuiaRepository;
    }

    // Obtiene los documentos subidos por un guía (INE, certificaciones...).
    public List<DocumentoGuia> obtenerPorGuia(Long idGuia) {
        return documentoGuiaRepository.findByIdGuia(idGuia);
    }

    // Registra un documento de guía. La subida real del archivo se maneja fuera del backend (S3);
    // aquí sólo se valida y persiste la URL resultante.
    public DocumentoGuia crearDocumento(DocumentoGuia documento) {
        if (documento.getIdGuia() == null) {
            throw new IllegalArgumentException("El documento debe estar asociado a un guía.");
        }
        if (documento.getTipo() == null || documento.getTipo().isBlank()) {
            throw new IllegalArgumentException("El tipo de documento es obligatorio (INE o CERTIFICACION).");
        }
        if (documento.getUrl() == null || documento.getUrl().isBlank()) {
            throw new IllegalArgumentException("La URL del documento es obligatoria.");
        }
        documento.setFechaSubida(LocalDateTime.now());
        return documentoGuiaRepository.save(documento);
    }
}
