package com.example.prueba.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ComercianteDTO {

    public Integer id;
    private Integer documento;
    private String razon_social;
    private String municipio;
    private String telefono;
    private String email;
    private Date fechaRegistro;
    private Integer  estado;
    private Long cantidadEstablecimientos;
    private Double totalIngresos;
    private Long cantidadEmpleados;

}
