package com.nexossoftware.pruebanexos.jpa.repository;

import com.nexossoftware.pruebanexos.jpa.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Repositorio para gestionar las operaciones en base de datos de la entidad {@link CardEntity} */
@Repository
public interface CardRepository extends JpaRepository<CardEntity, String> {
}
