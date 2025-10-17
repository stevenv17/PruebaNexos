package com.nexossoftware.pruebanexos.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "product")
public class ProductEntity {
  @Id
  @Column(name = "id")
  private String id;


  @Column(name = "person_id")
  private Long personId;

  @ManyToOne
  @JoinColumn(name = "person_id", referencedColumnName = "id", insertable = false, updatable = false)
  private PersonEntity person;


  @Column(name = "product_type")
  private String productTypeId;

  @ManyToOne
  @JoinColumn(name = "product_type", referencedColumnName = "name", insertable = false, updatable = false)
  private ProductTypeEntity productType;

}
