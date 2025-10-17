package com.nexossoftware.pruebanexos.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "product_type")
public class ProductTypeEntity {

  @Id
  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

}
