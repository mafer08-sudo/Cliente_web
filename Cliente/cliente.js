async function obtenerPersona() {
    console.log("Iniciando consulta de persona...");
 
    const documento = document.getElementById("documento").value;
    
    if (!documento) {
        alert("Por favor, ingresa un documento válido.");
        return;
    }

    const url = `http://localhost:8080/servicio/personas?id=${documento}`;
    console.log("URL solicitada:", url);
 
    try {
        const respuesta = await fetch(url);
 
        if (!respuesta.ok) {
            const mensajeError = await respuesta.text();
            throw new Error(`Error ${respuesta.status}: ${mensajeError}`);
        }
 
        const persona = await respuesta.json();
 
        document.getElementById("nombre").value = persona.nombre || "";
        document.getElementById("telefono").value = persona.telefono || "";
        document.getElementById("edad").value = persona.edad || "";
        document.getElementById("profesion").value = persona.profesion || "";
        document.getElementById("password").value = persona.password || "";
        document.getElementById("tipo").value = persona.tipo || "";

        console.log("Persona encontrada:", persona);
    } catch (error) {
        console.error("Error al obtener la persona:", error);
        alert("Hubo un problema al buscar la persona: " + error.message);
    }
}


async function obtenerListaPersonas() {
 console.log("Iniciando consulta de lista de personas...");
 const url = `http://localhost:8080/servicio/listapersonas`;
 console.log("URL solicitada:", url);
 try {
 const respuesta = await fetch(url);
 if (!respuesta.ok) {
 if (respuesta.status === 204) {
 alert("No hay personas disponibles en la lista.");

 console.log("La lista de personas está vacía.");
 return;
 }
 const mensajeError = await respuesta.text();
 throw new Error(`Error ${respuesta.status}: ${mensajeError}`);
 }
 const personas = await respuesta.json();
 console.log("Lista de personas obtenida:");
 // Iterar sobre la lista y mostrar cada persona en la consola
 personas.forEach((persona, index) => {
 console.log(`Persona ${index + 1}:`);
 console.log(`Documento: ${persona.documento}`);
 console.log(`Nombre: ${persona.nombre}`);
 console.log(`Teléfono: ${persona.telefono}`);
 console.log(`Edad: ${persona.edad}`);
 console.log(`Profesión: ${persona.profesion}`);
 console.log(`Tipo: ${persona.tipo}`);
 console.log("-----------------------------");
 });
 } catch (error) {
 console.error("Error al obtener la lista de personas:", error);
 alert("Hubo un problema al obtener la lista de personas: " + error.message);
 }
}

async function registrarPersona() {
 console.log("Iniciando registro de persona...");
 const documento = document.getElementById("documento").value;
 const nombre = document.getElementById("nombre").value;
 const telefono = document.getElementById("telefono").value;
 const edad = document.getElementById("edad").value;
 const profesion = document.getElementById("profesion").value;
 const password = document.getElementById("password").value;
 const tipo = document.getElementById("tipo").value;
 if (!documento || !nombre || !telefono || !edad || !profesion || !password || !tipo) {
 alert("Por favor, completa todos los campos antes de registrar.");
 return;
 }
 const persona = {
 documento: documento,
 nombre: nombre,
 telefono: telefono,
 edad: parseInt(edad, 10),
 profesion: profesion,
 password: password,
 tipo: parseInt(tipo, 10),
 };
 console.log("Persona a registrar:", persona);
 const url = "http://localhost:8080/servicio/personas";
 try {
 const respuesta = await fetch(url, {
 method: "POST",
 headers: {
 "Content-Type": "application/json",
 },
 body: JSON.stringify(persona),
 });
 if (!respuesta.ok) {
 const mensajeError = await respuesta.text();
 throw new Error(`Error ${respuesta.status}: ${mensajeError}`);
 }
 const resultado = await respuesta.json();
 console.log("Persona registrada con éxito:", resultado);
 alert("La persona se registró con éxito.");
 limpiarFormulario();
 } catch (error) {

 console.error("Error al registrar la persona:", error);
 alert("Hubo un problema al registrar la persona: " + error.message);
 }
}
function limpiarFormulario() {
 document.getElementById("documento").value = "";
 document.getElementById("nombre").value = "";
 document.getElementById("telefono").value = "";
 document.getElementById("edad").value = "";
 document.getElementById("profesion").value = "";
 document.getElementById("password").value = "";
 document.getElementById("tipo").value = "";
}

async function actualizarPersona() {
 console.log("Iniciando actualización de persona...");
 const documento = document.getElementById("documento").value;
 const nombre = document.getElementById("nombre").value;
 const telefono = document.getElementById("telefono").value;
 const edad = document.getElementById("edad").value;
 const profesion = document.getElementById("profesion").value;
 const password = document.getElementById("password").value;
 const tipo = document.getElementById("tipo").value;
 if (!documento || !nombre || !telefono || !edad || !profesion || !password || !tipo) {
 alert("Por favor, completa todos los campos antes de actualizar.");
 return;
 }
 const persona = {
 documento: documento,
 nombre: nombre,
 telefono: telefono,
 edad: parseInt(edad, 10),
 profesion: profesion,
 password: password,
 tipo: parseInt(tipo, 10),
 };
 console.log("Persona a actualizar:", persona);
 const url = "http://localhost:8080/servicio/actualizar";
 try {
 const respuesta = await fetch(url, {
 method: "PUT",
 headers: {
 "Content-Type": "application/json",
 },
 body: JSON.stringify(persona),
 });
 if (!respuesta.ok) {
 const mensajeError = await respuesta.text();
 throw new Error(`Error ${respuesta.status}: ${mensajeError}`);
 }
 const resultado = await respuesta.json();
 console.log("Persona actualizada con éxito:", resultado);
 alert("La persona se actualizó con éxito.");
 } catch (error) {
 console.error("Error al actualizar la persona:", error);
 alert("Hubo un problema al actualizar la persona: " + error.message);
 }
}

async function eliminarPersona() {
 console.log("Iniciando proceso para eliminar persona...");
 const documento = document.getElementById("documento").value;
 if (!documento) {
 alert("Por favor, ingresa un documento válido para eliminar.");
 return;
 }
 const url = `http://localhost:8080/servicio/personas/${documento}`;

 console.log("URL solicitada:", url);
 try {
 const respuesta = await fetch(url, {
 method: "DELETE",
 });
 if (respuesta.ok) {
 const mensaje = await respuesta.text();
 console.log("Respuesta del servidor:", mensaje);
 alert("Persona eliminada con éxito.");
 } else if (respuesta.status === 404) {
 const mensaje = await respuesta.text();
 console.warn("Error: Persona no encontrada.");
 alert("Error: " + mensaje);
 } else {
 throw new Error(`Error ${respuesta.status}: ${await respuesta.text()}`);
 }
 } catch (error) {
 console.error("Ocurrió un error al eliminar la persona:", error);
 alert("Hubo un problema al eliminar la persona: " + error.message);
 }
}