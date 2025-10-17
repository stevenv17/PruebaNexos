package com.nexossoftware.pruebanexos.jpa.repository;

import com.nexossoftware.pruebanexos.jpa.entity.CardTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Repositorio para gestionar las operaciones en base de datos de la entidad {@link CardTypeEntity} */
@Repository
public interface CardTypeRepository extends JpaRepository<CardTypeEntity, String> {
}
