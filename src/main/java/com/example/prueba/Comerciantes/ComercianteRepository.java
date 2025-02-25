package com.example.prueba.Comerciantes;

import com.example.prueba.Comerciantes.models.Comerciante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComercianteRepository extends JpaRepository<Comerciante, Integer> {
    boolean existsByDocumento(Integer documento);
    //Optional<Comerciante> findById(Integer id);
}