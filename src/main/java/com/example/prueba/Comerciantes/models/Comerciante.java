package com.example.prueba.Comerciantes.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comerciantes")
public class Comerciante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private Integer documento;

    @Column(nullable = false)
    private String razonSocial;

    @Column(nullable = false)
    private String municipio;

    private String telefono;
    private String email;

    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;

    @Builder.Default
    @Column(nullable = false)
    private Boolean estado = true;

    @Temporal(TemporalType.DATE)
    @Column(nullable = true)
    private Date updateDate;

    @Builder.Default
    @Column(nullable = true)
    private Integer userUpdate = null;

}
