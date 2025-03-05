package co.edu.uniandes.dse.parcialprueba.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialprueba.entities.HistoriaClinicaEntity;
import co.edu.uniandes.dse.parcialprueba.entities.PacienteEntity;
import co.edu.uniandes.dse.parcialprueba.repositories.HistoriaClinicaRepository;
import co.edu.uniandes.dse.parcialprueba.repositories.PacienteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HistoriaClinicaService {
    @Autowired
    PacienteRepository pacienteRepository;
    @Autowired
    HistoriaClinicaRepository historiaClinicaRepository;

    @Transactional 
    private HistoriaClinicaEntity crearHistoriaClinica(Long idPaciente, String diagnostico, HistoriaClinicaEntity historiaClinicaEntity) throws EntityNotFoundException{
        Optional<PacienteEntity> paciente = pacienteRepository.findById(idPaciente);
        if(paciente.isEmpty()){
            throw new EntityNotFoundException("No se hayo el paciente");
        }
        if(paciente.get().getAcudiente() != null){
            String diag2 = "HistoriaCompartida" + diagnostico;
            historiaClinicaEntity.setDiagnostico(diag2);
            paciente.get().getHistorias().add(historiaClinicaEntity);
            return historiaClinicaRepository.save(historiaClinicaEntity);
        }
        else {
            historiaClinicaEntity.setDiagnostico(diagnostico);
            paciente.get().getHistorias().add(historiaClinicaEntity);
            return historiaClinicaRepository.save(historiaClinicaEntity);
        }

    }
}
