package com.nexossoftware.pruebanexos.jpa.repository;

import com.nexossoftware.pruebanexos.jpa.entity.PersonaEntity;
import com.nexossoftware.pruebanexos.jpa.entity.TipoProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Repositorio para gestionar las operaciones en base de datos de la entidad {@link TipoProductoEntity} */
@Repository
public interface TipoProductoRepository extends JpaRepository<TipoProductoEntity, Long> {
}
