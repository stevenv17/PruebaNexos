package com.nexossoftware.pruebanexos.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "tarjeta")
public class TarjetaEntity {
  @Id
  @Column(name = "numero")
  private String numero;


  @Column(name = "producto_id")
  private Long productoId;

  @ManyToOne
  @JoinColumn(name = "producto_id", referencedColumnName = "id", insertable = false, updatable = false)
  private ProductoEntity producto;


  @Column(name = "tipo_tarjeta_id")
  private Long tipoTarjetaId;

  @ManyToOne
  @JoinColumn(name = "tipo_tarjeta_id", referencedColumnName = "id", insertable = false, updatable = false)
  private TipoTarjetaEntity tipoTarjeta;

  @Column(name = "fecha_expedicion")
  private Date fechaExpedicion;

  @Column(name = "fecha_vencimiento")
  private Date fechaVencimiento;

  @Column(name = "moneda")
  private String moneda;

  @Column(name = "saldo")
  private Long saldo;

}
