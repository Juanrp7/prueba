package com.example.prueba.Municipios;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class MunicipioController {

    @GetMapping("/getMunicipio")
    public List<MunicipioDTO> getMunicipios() {
        return Arrays.asList(
            new MunicipioDTO("Bogotá", "Cundinamarca"),
            new MunicipioDTO("Medellín", "Antioquia"),
            new MunicipioDTO("Cali", "Valle del Cauca"),
            new MunicipioDTO("Barranquilla", "Atlántico"),
            new MunicipioDTO("Cartagena", "Bolívar"),
            new MunicipioDTO("Bucaramanga", "Santander"),
            new MunicipioDTO("Santa Marta", "Magdalena"),
            new MunicipioDTO("Cúcuta", "Norte de Santander"),
            new MunicipioDTO("Manizales", "Caldas"),
            new MunicipioDTO("Pereira", "Risaralda"),
            new MunicipioDTO("Ibagué", "Tolima"),
            new MunicipioDTO("Pasto", "Nariño")
        );
    }

}
