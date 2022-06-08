package client.localization;

import java.util.ListResourceBundle;

public class Data_es extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return contents;
    }

    private final Object[][] contents = new Object[][] {

            {"Connection with server was successful", "La conexión con el servidor se realizó correctamente"},


            // AUTH SCENE
            {"Authorization page", "Página de autorización"},
            {"Type here your login", "Escriba aquí su nombre de usuario"},
            {"Type here your pass", "Escriba aquí su pase"},
            {"Sign in", "Iniciar sesión"},
            {"Login failed", "Error de inicio de sesión"},
            {"User already authorized (multi-session is not supported)", "Usuario ya autorizado (no se admite multisesión)"},
            {"User successfully logged in", "El usuario ha iniciado sesión correctamente"},
            {"Incorrect login or password", "Nombre de usuario o contraseña incorrectos"},
            {"Don't have an account yet?", "¿Aún no tienes una cuenta?"},
            {"Go to the registration page", "Ir a la página de registro"},


            // REG SCENE
            {"Registration page", "Página de registro"},
            {"New user login", "Inicio de sesión de nuevo usuario"},
            {"New user pass", "Pase de nuevo usuario"},
            {"Sign up", "Inscribir"},
            {"User is already registered", "El usuario ya está registrado"},
            {"New user successfully registered", "Nuevo usuario registrado con éxito"},
            {"Already have an account?", "Ya tienes una cuenta?"},
            {"Go to the authorization page", "Ir a la página de autorización"},


            // AUTH-REG SCENE
            {"Login", "Sesión"},
            {"Login must have at least 3 characters", "El inicio de sesión debe tener al menos 3 caracteres"},
            {"Login must have less than 20 characters", "El inicio de sesión debe tener menos de 20 caracteres"},
            {"Login must contains of only alphabetic characters", "El inicio de sesión debe contener solo caracteres alfabéticos"},
            {"Password", "Contraseña"},
            {"Password must have at least 3 characters", "La contraseña debe tener al menos 3 caracteres"},
            {"Password must have less than 20 characters", "La contraseña debe tener menos de 20 caracteres"},


            // MAIN SCENE
            {"Console", "Consola"},
            {"Table", "Tabla"},
            {"Plot", "Parcela"},


            // USER TAB
            {"User", "Usuario"},
            {"Username", "Nombre de usuario"},
            {"Sign out", "Sesión"},
            {"Are you sure to sign out? (all unsaved data will be deleted)", "¿Estás seguro de cerrar sesión? (se eliminarán todos los datos no guardados)"},
            {"Settings", "Configuración"},
            {"Language", "Idioma"},
            {"Are you sure to exit? (all unsaved data will be deleted)", "¿Estás seguro de salir? (se eliminarán todos los datos no guardados)"},
            {"Exit", "Salida"},


            // CONSOLE TAB
            {"Choose the command you want to send", "Elija el comando que desea enviar"},
            {"Send", "Enviar"},
            {"Choose command", "Elegir comando"},
            {"You should choose command to send!", "Usted debe elegir comando para enviar!"},


            // COMMAND
            {"Are you sure?", "¿Están seguros?"},
            {"Are you sure to send command", "¿Está seguro de enviar el comando"},
            {"with given argument?", "con este argumento?"},
            {"with given arguments?", "con argumentos?"},
            {"Enter length", "Introduzca la longitud"},
            {"Enter script filename", "Introduzca el nombre de archivo del script"},
            {"Enter mpaa rating", "Ingrese la calificación de la MPAA"},
            {"Enter key to remove", "Ingrese la clave para eliminar"},
            {"Movie key to insert", "Tecla de película para insertar"},
            {"Movie key to replace", "Tecla de película para reemplazar"},
            {"Movie key to update", "Clave de película para actualizar"},
            {"Return", "Devolver"},
            {"Confirm", "Confirmar"},


            // CHECK INDEX ERRORS
            {"ERROR: Server is not responding, try later or choose another server :(", "ERROR: El servidor no responde, inténtelo más tarde o elija otro servidor :("},
            {"ERROR: User isn't logged in yet (or connection support time is out)", "ERROR: El usuario aún no ha iniciado sesión (o se ha agotado el tiempo de soporte de conexión)"},
            {"ERROR: Your elements count limit exceeded", "ERROR: Se ha excedido el límite de recuento de elementos"},
            {"ERROR: Movie with given key doesn't exist", "ERROR: La película con la clave dada no existe"},
            {"ERROR: You don't have permission to update movie with given key", "ERROR: No tiene permiso para actualizar la película con la clave dada"},
            {"ERROR: You have permission to update movie with given key", "ERROR: Tiene permiso para actualizar la película con la clave dada"},
            {"ERROR: Movie with given key already exists", "ERROR: La película con la clave dada ya existe"},
            {"ERROR: You can't replace movie with given key", "ERROR: No se puede reemplazar la película con una clave determinada"},
            {"ERROR: You can't update movie with given key", "ERROR: No se puede actualizar la película con la clave dada"},
            {"OK", "OK"},


            // ONE PARAM ERRORS
            {"ERROR: method \"setGUIArgs\" is not overridden", "ERROR: el método \"setGUIArgs\" no se anula"},
            {"ERROR: value must be integer", "ERROR: el valor debe ser entero"},
            {"ERROR: current script doesn't exists", "ERROR: el script actual no existe"},
            // ERROR: value must be one of: [G, PG, PG_13, R, NC_17]
            // ERROR: value must be integer


            // MOVIE FIELD ERRORS
            {"ERROR: value must be in range [-200,200]", "ERROR: el valor debe estar en el rango [-200,200]"},
            {"ERROR: value must be float", "ERROR: el valor debe ser flotante"},
            {"ERROR: field can't be null, String can't be empty", "ERROR: el campo no puede ser nulo, la cadena no puede estar vacía"},
            {"ERROR: name must have less than 20 characters", "ERROR: el nombre debe tener menos de 20 caracteres"},
            {"ERROR: value must be more than 0", "ERROR: el valor debe ser mayor que 0"},
            {"ERROR: value must be long", "ERROR: el valor debe ser largo"},
            {"ERROR: value must be more than 60 and less than 300", "ERROR: el valor debe ser mayor que 60 y menor que 300"},
            {"ERROR: value must be integer", "ERROR: el valor debe ser entero"},
            {"ERROR: value must be one of: [G, PG, PG_13, R, NC_17]", "ERROR: el valor debe ser uno de: [G, PG, PG_13, R, NC_17]"},
            {"ERROR: name must have less than 20 characters", "ERROR: el nombre debe tener menos de 20 caracteres"},
            {"ERROR: year must be at least 1900 (vampire-screenwriters is not supported)", "ERROR: el año debe ser al menos 1900 (vampire-screenwriters no es compatible)"},
            {"ERROR: field must have \"DD.MM.YYYY\" format", "ERROR: el campo debe tener el formato \"DD. MM. AAAA\""},



            // TABLE TAB
            {"Filter by", "Filtrar por"},
            {"with value contains", "con valor contiene"},
            {"anything", "nada"},
            {"Do you think here is an old collection?", "¿Crees que aquí hay una colección antigua?"},
            {"Update", "Actualizar"},
            {"or set", "o conjunto"},
            {"Auto-update", "Auto-actualización"},


            // PLOT TAB
            {"Selected movie", "Película seleccionada"},
            {"nothing selected", "nada seleccionado"},


            // TABS
            {"Edit", "Editar"},
            {"Remove", "Quitar"},



            // ERRORS
            {"Error", "Error"},
            {"Something wrong", "Algo anda mal"},
            {"Oops... Seems like evil goblins cut some wires... Try again later", "Ups... Parece que duendes malvados cortaron algunos cables... Inténtalo de nuevo más tarde"},
            {"Wrong request format", "Formato de solicitud incorrecto"},
            {"Server has wrong logic: unexpected", "El servidor tiene una lógica incorrecta: inesperado"},
            {"FATAL", "FATAL"},
            {"FATAL ERROR: don't peek", "ERROR FATAL: no mirar"}
    };
}
