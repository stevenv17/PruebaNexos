package com.nexossoftware.pruebanexos.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "card")
public class CardEntity {
  @Id
  @Column(name = "number")
  private String number;


  @Column(name = "product_id")
  private String productId;

  @ManyToOne
  @JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
  private ProductEntity product;


  @Column(name = "card_type")
  private String cardTypeId;

  @ManyToOne
  @JoinColumn(name = "card_type", referencedColumnName = "name", insertable = false, updatable = false)
  private CardTypeEntity cardType;

  @Column(name = "issue_date")
  private Date issueDate;

  @Column(name = "expiration_date")
  private Date expirationDate;

  @Column(name = "currency")
  private String currency;

  @Column(name = "balance")
  private Long balance;

  @Column(name = "status")
  private String status;

}
