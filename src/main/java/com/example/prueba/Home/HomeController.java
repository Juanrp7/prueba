package com.example.prueba.Home;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.prueba.DTO.ComercianteDTO;

import java.math.BigDecimal;
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
        List<Object[]> results = entityManager.createNativeQuery(
            "SELECT id, documento, razon_social, municipio, telefono, email, fecha_registro, estado, " +
            "cantidad_establecimientos, total_ingresos, cantidad_empleados " +
            "FROM TABLE(obtener_comerciantes())"
        ).getResultList();

        return results.stream()
            .map(obj -> new ComercianteDTO(
                    convertToInteger(obj[0]),   // id como Integer
                    convertToInteger(obj[1]),   // documento como Integer
                    (String) obj[2],            // razon_social como String
                    (String) obj[3],            // municipio como String
                    (String) obj[4],            // telefono como String
                    (String) obj[5],            // email como String
                    (Date) obj[6],              // fecha_registro como Date
                    convertToInteger(obj[7]),   // estado como Integer
                    convertToLong(obj[8]),      // cantidad_establecimientos como Long
                    convertToDouble(obj[9]),    // total_ingresos como Double
                    convertToLong(obj[10])      // cantidad_empleados como Long
            ))
            .collect(Collectors.toList());
    }

    private Integer convertToInteger(Object obj) {
        if (obj instanceof BigDecimal) {
            return ((BigDecimal) obj).intValue();
        } else if (obj instanceof Integer) {
            return (Integer) obj;
        }
        return null;
    }

    private Long convertToLong(Object obj) {
        if (obj instanceof BigDecimal) {
            return ((BigDecimal) obj).longValue();
        } else if (obj instanceof Integer) {
            return ((Integer) obj).longValue();
        }
        return null;
    }
    
    private Double convertToDouble(Object obj) {
        if (obj instanceof BigDecimal) {
            return ((BigDecimal) obj).doubleValue();
        } else if (obj instanceof Double) {
            return (Double) obj;
        }
        return null;
    }
}
