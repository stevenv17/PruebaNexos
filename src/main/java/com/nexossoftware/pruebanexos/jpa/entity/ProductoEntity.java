package com.nexossoftware.pruebanexos.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "producto")
public class ProductoEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;


  @Column(name = "titular_id")
  private Long titularId;

  @ManyToOne
  @JoinColumn(name = "titular_id", referencedColumnName = "id", insertable = false, updatable = false)
  private PersonaEntity titular;


  @Column(name = "tipo_producto_id")
  private Long tipoProductoId;

  @ManyToOne
  @JoinColumn(name = "tipo_producto_id", referencedColumnName = "id", insertable = false, updatable = false)
  private TipoProductoEntity tipoProducto;

}
