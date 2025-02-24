package com.example.prueba.Comerciantes;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComercianteRequest {
    String documento;
    String razon_social;
    String municipio;
    String telefono;
    String email;
    Date fecha_registro;
    Boolean estado;
}
