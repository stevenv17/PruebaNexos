package com.nexossoftware.pruebanexos.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "card_type")
public class CardTypeEntity {

  @Id
  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

}
