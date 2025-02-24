package com.example.prueba.Home;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.prueba.DTO.ComercianteDTO;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class HomeController {

    @PersistenceContext
    private  EntityManager entityManager;

    @SuppressWarnings("unchecked")
    @GetMapping(value = "home")
    public List<ComercianteDTO> obtenerComerciantesActivos() { 
        List<Object[]> results = entityManager.createNativeQuery("SELECT id, documento, razon_social, municipio, telefono, email, fecha_registro, estado, cantidad_establecimientos, total_ingresos, cantidad_empleados FROM obtener_comerciantes()").getResultList();

        return results.stream()
            .map(obj -> new ComercianteDTO(
                        (Integer) obj[0],        
                        (String) obj[1],        
                        (String) obj[2],        
                        (String) obj[3],         
                        (String) obj[4],         
                        (String) obj[5],         
                        (Date) obj[6],           
                        (Boolean) obj[7],        
                        ((Number) obj[8]).longValue(), 
                        ((Number) obj[9]).doubleValue(),
                        ((Number) obj[10]).longValue() 
            ))
            .collect(Collectors.toList());
    }
}
