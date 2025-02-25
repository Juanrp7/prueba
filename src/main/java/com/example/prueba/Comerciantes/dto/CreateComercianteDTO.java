package com.example.prueba.Comerciantes.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CreateComercianteDTO {

    private Long documento;
    private String razon_social;
    private String municipio;
    private String telefono;
    private String email;
    private Date fechaRegistro;
    private Date updateDate;
    private Integer userUpdate;
    private Integer estado = 1;
}
