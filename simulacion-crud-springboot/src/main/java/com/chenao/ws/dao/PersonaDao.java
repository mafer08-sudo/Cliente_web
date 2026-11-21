package com.chenao.ws.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.chenao.ws.dto.PersonaDto;
import com.chenao.ws.util.PersonasUtilidades;

@Repository
public class PersonaDao {
    public PersonaDao() {
        PersonasUtilidades.iniciarLista();
    }

    public PersonaDto consultarPersonaIndividual(String documento) {
        for (PersonaDto p : PersonasUtilidades.listaPersonas) {
            if (p.getDocumento() != null && p.getDocumento().equals(documento)) {
                return copiar(p);
            }
        }
        return null;
    }

    public PersonaDto consultarPersonaIdProfesion(String documento, String profesion) {
        for (PersonaDto p : PersonasUtilidades.listaPersonas) {
            if (p.getDocumento() != null && p.getDocumento().equals(documento)
                    && p.getProfesion() != null && p.getProfesion().equalsIgnoreCase(profesion)) {
                return copiar(p);
            }
        }
        return null;
    }

    public List<PersonaDto> consultarPersonas() {
        return PersonasUtilidades.listaPersonas.stream().map(this::copiar).toList();
    }

    public PersonaDto registrarPersona(PersonaDto persona) {
        if (persona == null || persona.getDocumento() == null || persona.getDocumento().isBlank()
                || consultarPersonaIndividual(persona.getDocumento()) != null) {
            return null;
        }
        PersonaDto guardada = copiar(persona);
        PersonasUtilidades.listaPersonas.add(guardada);
        return copiar(guardada);
    }

    public PersonaDto consultarLogin(String documento, String pass) {
        for (PersonaDto p : PersonasUtilidades.listaPersonas) {
            if (p.getDocumento() != null && p.getDocumento().equals(documento)
                    && p.getPassword() != null && p.getPassword().equals(pass)) {
                return copiar(p);
            }
        }
        return null;
    }

    public PersonaDto actualizarPersona(PersonaDto persona) {
        if (persona == null || persona.getDocumento() == null) return null;
        for (int i = 0; i < PersonasUtilidades.listaPersonas.size(); i++) {
            PersonaDto actual = PersonasUtilidades.listaPersonas.get(i);
            if (persona.getDocumento().equals(actual.getDocumento())) {
                PersonasUtilidades.listaPersonas.set(i, copiar(persona));
                return copiar(persona);
            }
        }
        return null;
    }

    public boolean eliminarPersona(String documento) {
        return PersonasUtilidades.listaPersonas.removeIf(p ->
                p.getDocumento() != null && p.getDocumento().equals(documento));
    }

    private PersonaDto copiar(PersonaDto p) {
        return new PersonaDto(p.getDocumento(), p.getNombre(), p.getTelefono(), p.getEdad(),
                p.getProfesion(), p.getPassword(), p.getTipo());
    }
}
