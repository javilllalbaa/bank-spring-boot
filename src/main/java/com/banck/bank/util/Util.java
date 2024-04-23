package com.banck.bank.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Util {

    public static String getFechaActual() {
        // Obtener la fecha y hora actual
        LocalDateTime now = LocalDateTime.now();
        // Crear un formateador de fecha y hora con el formato deseado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss:SS");
        // Formatear la fecha y hora actual utilizando el formateador
        return now.format(formatter);
    }
}
