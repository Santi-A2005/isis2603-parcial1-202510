package co.edu.uniandes.dse.parcialprueba.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import co.edu.uniandes.dse.parcialprueba.entities.HistoriaClinicaEntity;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import co.edu.uniandes.dse.parcialprueba.entities.PacienteEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialprueba.repositories.PacienteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(PacienteService.class)
class PacienteEntityTest {
    
    @Autowired
    PacienteRepository pacienteRepository;

    @Autowired 
    PacienteService pacienteService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();
    private List<PacienteEntity> pacienteList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from PacienteEntity");
        }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
     */
    private void insertData() {

        for (int i = 0; i < 3; i++) {
            PacienteEntity pacienteEntity = factory.manufacturePojo(PacienteEntity.class);
            entityManager.persist(pacienteEntity);
            pacienteList.add(pacienteEntity);
        }}


        @Test
        void crearPacienteTest() throws IllegalOperationException{
            PacienteEntity newEntity = factory.manufacturePojo(PacienteEntity.class);
            newEntity.setTelefono("31145678901");
            PacienteEntity result = pacienteService.crearPaciente(newEntity);
            assertNotNull(result);
            PacienteEntity entity = entityManager.find(PacienteEntity.class, result.getId());
            assertEquals(newEntity.getId(), entity.getId());
            assertEquals(newEntity.getTelefono(), entity.getTelefono());
        }

        @Test
        void crearPacienteFalla(){
            assertThrows(IllegalOperationException.class, () -> {
            PacienteEntity newEntity = factory.manufacturePojo(PacienteEntity.class);
            newEntity.setTelefono("1234");
            PacienteEntity result = pacienteService.crearPaciente(newEntity);   
            });
        }

        @Test
        void añadirAcudienteTest() throws EntityNotFoundException, IllegalOperationException{
            PacienteEntity paciente = factory.manufacturePojo(PacienteEntity.class);
		    paciente.setTelefono("31145678901");
            paciente.setAcudiente(null);
            PacienteEntity acudiente = factory.manufacturePojo(PacienteEntity.class);
		    acudiente.setTelefono("31145678901");
            acudiente.setAcudiente(null);
            HistoriaClinicaEntity historia = factory.manufacturePojo(HistoriaClinicaEntity.class);
            acudiente.getHistorias().add(historia);
            pacienteRepository.save(paciente);
            pacienteRepository.save(acudiente);
            PacienteEntity nuevo = pacienteService.añadirAcudiente(paciente.getId(), acudiente.getId());
            assertEquals(nuevo.getTelefono(), paciente.getTelefono());
        }

        @Test
        void añadirAcudienteFalla(){
            assertThrows(EntityNotFoundException.class, () -> {
            PacienteEntity paciente = factory.manufacturePojo(PacienteEntity.class);
		    paciente.setTelefono("31145678901");
            paciente.setAcudiente(null);
            PacienteEntity acudiente = factory.manufacturePojo(PacienteEntity.class);
		    acudiente.setTelefono("31145678901");
            acudiente.setAcudiente(null);
            HistoriaClinicaEntity historia = factory.manufacturePojo(HistoriaClinicaEntity.class);
            acudiente.getHistorias().add(historia);
            pacienteRepository.save(paciente);
            pacienteRepository.save(acudiente);
            PacienteEntity nuevo = pacienteService.añadirAcudiente(0L, acudiente.getId());
            });
        }

}
