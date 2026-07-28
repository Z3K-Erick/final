package com.horizon.backend.repositories;

import com.horizon.backend.models.DocumentoGuia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentoGuiaRepository extends JpaRepository<DocumentoGuia, Long> {
    List<DocumentoGuia> findByIdGuia(Long idGuia);
}
