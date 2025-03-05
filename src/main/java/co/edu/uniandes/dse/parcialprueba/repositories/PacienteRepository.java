package co.edu.uniandes.dse.parcialprueba.repositories;
import co.edu.uniandes.dse.parcialprueba.entities.PacienteEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<PacienteEntity, Long> {
    
}
