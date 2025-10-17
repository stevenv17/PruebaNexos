package com.nexossoftware.pruebanexos.jpa.repository;

import com.nexossoftware.pruebanexos.jpa.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/** Repositorio para gestionar las operaciones en base de datos de la entidad {@link TransactionEntity} */
@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {


  @Query(value = """
      SELECT t FROM TransactionEntity t
      WHERE t.status = 'APPROVED'
      AND t.id = :id AND t.cardNumber = :card
      """)
  Optional<TransactionEntity> findByIdAndCardNumberApproved(@Param("id") Long transactionId, @Param("card") String cardNumber);

}
