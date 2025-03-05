package co.edu.uniandes.dse.parcialprueba.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialprueba.entities.PacienteEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialprueba.repositories.PacienteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.util.Optional;
@Slf4j
@Service
public class PacienteService {

    @Autowired
    PacienteRepository pacienteRepository;

    @Transactional
    public PacienteEntity crearPaciente(PacienteEntity pacienteEntity)throws IllegalOperationException{
        String telefono = pacienteEntity.getTelefono();
        if(telefono.length()!= 11){
            throw new IllegalOperationException("Numero invalido");
        }
        if(!telefono.startsWith("311" )){
            if( !telefono.startsWith("601")){
                throw new IllegalOperationException("Numero invalido");
            }}
        return pacienteRepository.save(pacienteEntity);
    }

    @Transactional
    public PacienteEntity añadirAcudiente(Long idPaciente, Long idAcudiente)  throws EntityNotFoundException, IllegalOperationException{
        Optional<PacienteEntity> paciente = pacienteRepository.findById(idPaciente);
        if(paciente.isEmpty()){
            throw new EntityNotFoundException("No se hayo el paciente");
        }
        Optional<PacienteEntity> acudiente = pacienteRepository.findById(idAcudiente);
        if(acudiente.isEmpty()){            
            throw new EntityNotFoundException("No se hayo el acudiente");
        }
        if((paciente.get().getAcudiente()) != null){
            throw new IllegalOperationException("Invalido");
        }
        if((acudiente.get().getAcudiente()) != null){
            throw new IllegalOperationException("Invalido");
        }
        if(acudiente.get().getHistorias().getFirst() == null){
            throw new IllegalOperationException("Invalido");
        }
        PacienteEntity acudiente2 = new PacienteEntity();
        acudiente2.setCorreo(acudiente.get().getCorreo());
        pacienteRepository.deleteById(acudiente.get().getId());
        acudiente2.setId(acudiente.get().getId());
        acudiente2.setHistorias(acudiente.get().getHistorias());
        acudiente2.setTelefono(acudiente.get().getTelefono());
        acudiente2.setNombre(acudiente.get().getNombre());
        pacienteRepository.save(acudiente2);
        paciente.get().setAcudiente(acudiente2);
        return paciente.get();
    }
}
