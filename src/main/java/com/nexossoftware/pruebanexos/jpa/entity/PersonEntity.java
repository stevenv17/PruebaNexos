package com.nexossoftware.pruebanexos.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "person")
public class PersonEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "firstname")
  private String firstName;

  @Column(name = "middlename")
  private String middleName;

  @Column(name = "lastname1")
  private String lastName;

  @Column(name = "lastname2")
  private String secondLastName;
}
