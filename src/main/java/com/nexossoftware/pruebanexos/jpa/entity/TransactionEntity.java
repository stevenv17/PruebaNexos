package com.nexossoftware.pruebanexos.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "transaction")
public class TransactionEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;


  @Column(name = "card_number")
  private String cardNumber;

  @ManyToOne
  @JoinColumn(name = "card_number", referencedColumnName = "number", insertable = false, updatable = false)
  private CardEntity card;

  @Column(name = "price")
  private Long price;

  @Column(name = "transaction_date")
  private Date transactionDate;

  @Column(name = "status")
  private String status;

}
