package com.chenao.ws.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.chenao.ws.dao.PersonaDao;
import com.chenao.ws.dto.PersonaDto;

@Service
public class PersonaService {
    private final PersonaDao personaDao;

    public PersonaService(PersonaDao personaDao) {
        this.personaDao = personaDao;
    }

    public PersonaDto obtenerPersonaPorDocumento(String documento) {
        return personaDao.consultarPersonaIndividual(documento);
    }

    public PersonaDto consultarPersonaIdProfesion(String documento, String profesion) {
        return personaDao.consultarPersonaIdProfesion(documento, profesion);
    }

    public List<PersonaDto> consultarPersonas() {
        return personaDao.consultarPersonas();
    }

    public PersonaDto registrarPersona(PersonaDto persona) {
        return personaDao.registrarPersona(persona);
    }

    public PersonaDto consultarLogin(String documento, String pass) {
        return personaDao.consultarLogin(documento, pass);
    }

    public PersonaDto actualizarPersona(PersonaDto persona) {
        return personaDao.actualizarPersona(persona);
    }

    public boolean eliminarPersona(String documento) {
        return personaDao.eliminarPersona(documento);
    }
}
