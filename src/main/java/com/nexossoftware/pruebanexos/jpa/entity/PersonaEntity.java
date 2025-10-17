package com.nexossoftware.pruebanexos.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "persona")
public class PersonaEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "nombre1")
  private String primerNombre;

  @Column(name = "nombre2")
  private String segundoNombre;

  @Column(name = "apellido1")
  private String primerApellido;

  @Column(name = "apellido2")
  private String segundoApellido;
}
