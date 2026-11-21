package com.chenao.ws.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.chenao.ws.dto.PersonaDto;
import com.chenao.ws.service.PersonaService;

@RestController
@RequestMapping("/servicio")
public class PersonaController {
    private final PersonaService personaService;

    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    @GetMapping("/hola")
    public String saludo() {
        return "Este es un saludo desde el proyecto Prueba CRUD";
    }

    // GET con RequestParam: /servicio/personas?id=111
    @GetMapping("/personas")
    public ResponseEntity<?> getPersona(@RequestParam(value = "id", required = false) String documento) {
        if (documento == null || documento.isBlank()) {
            return ResponseEntity.badRequest().body("El parámetro 'id' es obligatorio.");
        }
        PersonaDto persona = personaService.obtenerPersonaPorDocumento(documento);
        if (persona == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Persona no encontrada para el documento: " + documento);
        }
        return ResponseEntity.ok(persona);
    }

    // GET por documento y profesión: /servicio/profesion?id=111&profesion=Ingeniero
    @GetMapping("/profesion")
    public ResponseEntity<?> getPersonaIdProfesion(
            @RequestParam("id") String documento, @RequestParam String profesion) {
        if (documento.isBlank() || profesion.isBlank()) {
            return ResponseEntity.badRequest().body("Los parámetros 'id' y 'profesion' son obligatorios.");
        }
        PersonaDto persona = personaService.consultarPersonaIdProfesion(documento, profesion);
        if (persona == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Persona no encontrada para el documento: " + documento + " y profesión: " + profesion);
        }
        return ResponseEntity.ok(persona);
    }

    // GET con PathVariable: /servicio/personas/111
    @GetMapping("/personas/{id}")
    public ResponseEntity<?> getPersonaId(@PathVariable("id") String documento) {
        PersonaDto persona = personaService.obtenerPersonaPorDocumento(documento);
        if (persona == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Persona no encontrada para el documento: " + documento);
        }
        return ResponseEntity.ok(persona);
    }

    // GET con dos PathVariable: /servicio/personas/222/Estudiante
    @GetMapping("/personas/{id}/{profesion}")
    public ResponseEntity<?> getPersonaIdProfesion2(
            @PathVariable("id") String documento, @PathVariable String profesion) {
        PersonaDto persona = personaService.consultarPersonaIdProfesion(documento, profesion);
        if (persona == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Persona no encontrada para el documento: " + documento + " y profesión: " + profesion);
        }
        return ResponseEntity.ok(persona);
    }

    @GetMapping("/listapersonas")
    public ResponseEntity<?> getPersonas() {
        try {
            List<PersonaDto> personas = personaService.consultarPersonas();
            if (personas.isEmpty()) return ResponseEntity.noContent().build();
            return ResponseEntity.ok(personas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al consultar las personas.");
        }
    }

    @PostMapping("/personas")
    public ResponseEntity<?> registrarPersona(@RequestBody PersonaDto persona) {
        if (persona == null || persona.getDocumento() == null || persona.getDocumento().isBlank()) {
            return ResponseEntity.badRequest().body("El documento es obligatorio.");
        }
        PersonaDto registrada = personaService.registrarPersona(persona);
        if (registrada == null) {
            return ResponseEntity.badRequest().body("Ya existe una persona con ese documento.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(registrada);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody PersonaDto credenciales) {
        if (credenciales == null || credenciales.getDocumento() == null || credenciales.getPassword() == null) {
            return ResponseEntity.badRequest().body("Documento y password son obligatorios.");
        }
        PersonaDto persona = personaService.consultarLogin(credenciales.getDocumento(), credenciales.getPassword());
        if (persona == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Documento o password incorrectos.");
        return ResponseEntity.ok(persona);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<?> actualizarPersona(@RequestBody PersonaDto persona) {
        if (persona == null || persona.getDocumento() == null || persona.getDocumento().isBlank()) {
            return ResponseEntity.badRequest().body("El documento es obligatorio para actualizar.");
        }
        PersonaDto actualizada = personaService.actualizarPersona(persona);
        if (actualizada == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe una persona con documento: " + persona.getDocumento());
        }
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/personas/{id}")
    public ResponseEntity<?> eliminarPersona(@PathVariable String id) {
        if (!personaService.eliminarPersona(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Persona no encontrada para el documento: " + id);
        }
        return ResponseEntity.noContent().build();
    }
}
