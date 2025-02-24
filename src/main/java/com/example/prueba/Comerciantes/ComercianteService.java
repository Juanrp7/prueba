package com.example.prueba.Comerciantes;

import com.example.prueba.Comerciantes.dto.CreateComercianteDTO;
import com.example.prueba.Comerciantes.models.Comerciante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComercianteService {

    @Autowired
    private ComercianteRepository comercianteRepository;

    public Comerciante createComerciante(CreateComercianteDTO dto) {
        if (comercianteRepository.existsByDocumento(dto.getDocumento())) {
            throw new RuntimeException("El comerciante con este documento ya existe.");
        }

        Comerciante comerciante = new Comerciante();
        comerciante.setDocumento(dto.getDocumento());
        comerciante.setRazonSocial(dto.getRazonSocial());
        comerciante.setMunicipio(dto.getMunicipio());
        comerciante.setTelefono(dto.getTelefono());
        comerciante.setEmail(dto.getEmail());
        comerciante.setFechaRegistro(dto.getFechaRegistro());
        comerciante.setEstado(true);
        
        return comercianteRepository.save(comerciante);
    }

}
