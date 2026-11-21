package com.chenao.ws.util;

import java.util.ArrayList;
import java.util.List;
import com.chenao.ws.dto.PersonaDto;

public final class PersonasUtilidades {
    public static final int TIPO_ADMIN = 1;
    public static final int TIPO_EMPLEADO = 2;
    public static final List<PersonaDto> listaPersonas = new ArrayList<>();
    private static boolean inicializada = false;

    private PersonasUtilidades() {}

    public static synchronized void iniciarLista() {
        if (!inicializada) {
            listaPersonas.add(new PersonaDto("admin", "Administrador", "NA", 0, "NA", "admin", TIPO_ADMIN));
            listaPersonas.add(new PersonaDto("111", "Cristian David Henao", "345345", 33, "Ingeniero", "111", TIPO_ADMIN));
            listaPersonas.add(new PersonaDto("222", "Juan Martin Orozco", "2342342", 21, "Estudiante", "222", TIPO_EMPLEADO));
            inicializada = true;
        }
    }
}
