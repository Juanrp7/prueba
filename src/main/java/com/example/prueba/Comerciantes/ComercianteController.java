package com.example.prueba.Comerciantes;

import com.example.prueba.Comerciantes.dto.CreateComercianteDTO;
import com.example.prueba.Comerciantes.models.Comerciante;
import com.example.prueba.Jwt.JwtService;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ComercianteController {

    @Autowired
    private ComercianteService comercianteService;

    @Autowired
    private ComercianteRepository comercianteRepository;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/createComerciante")
    public ResponseEntity<Comerciante> createComerciante(@RequestBody CreateComercianteDTO dto) {
        Comerciante nuevoComerciante = comercianteService.createComerciante(dto);
        return ResponseEntity.ok(nuevoComerciante);
    }

    @PutMapping("/updateComerciante/{id}")
    public ResponseEntity<?> updateComerciante(@PathVariable Integer id, @RequestBody Comerciante updatedComerciante, @RequestHeader("Authorization") String authHeader) {
        Optional<Comerciante> comercianteOptional = comercianteRepository.findById(id);
        
        if (comercianteOptional.isPresent()) {
            Comerciante comerciante = comercianteOptional.get();
            comerciante.setDocumento(updatedComerciante.getDocumento());
            comerciante.setRazon_social(updatedComerciante.getRazon_social());
            comerciante.setEmail(updatedComerciante.getEmail());
            comerciante.setFechaRegistro(updatedComerciante.getFechaRegistro());
            comerciante.setTelefono(updatedComerciante.getTelefono());
            comerciante.setMunicipio(updatedComerciante.getMunicipio());
            comerciante.setUpdateDate(new Date());

            String token = authHeader.replace("Bearer ", "");
            Integer documento = Integer.parseInt(jwtService.getDocumentoFromToken(token));
            comerciante.setUserUpdate(documento);

            comercianteRepository.save(comerciante);
            return ResponseEntity.ok("Comerciante actualizado correctamente");
        } else {
            return ResponseEntity.status(404).body("Comerciante no encontrado");
        }
    }

    @DeleteMapping("/deleteComerciante/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteComerciante(@PathVariable Integer id) {
        Optional<Comerciante> comerciante = comercianteRepository.findById(id);
        
        if (comerciante.isPresent()) {
            comercianteRepository.deleteById(id);
            return ResponseEntity.ok("Comerciante eliminado exitosamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comerciante no encontrado.");
        }
    }


    @PutMapping("/activeOrInactive/{id}")
    public ResponseEntity<?> activeOrInactive(@PathVariable Integer id, @RequestBody Comerciante updatedComerciante, @RequestHeader("Authorization") String authHeader){

        Optional<Comerciante> comercianteOptional = comercianteRepository.findById(id);
        
        if (comercianteOptional.isPresent()) {
            Comerciante comerciante = comercianteOptional.get();
            
            comerciante.setEstado(updatedComerciante.getEstado());

            String token = authHeader.replace("Bearer ", "");
            Integer documento = Integer.parseInt(jwtService.getDocumentoFromToken(token));
            comerciante.setUserUpdate(documento);

            comercianteRepository.save(comerciante);
            return ResponseEntity.ok("Comerciante actualizado correctamente");
        }else {
            return ResponseEntity.status(404).body("Comerciante no encontrado");
        }
        
    }

}
