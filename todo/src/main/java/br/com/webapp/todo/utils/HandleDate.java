/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.webapp.todo.utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 *
 * @author jpmgo
 */
public class HandleDate {

    public static String formartStringUTCDate(String utcTime) {
        ZonedDateTime zdt = ZonedDateTime.parse(utcTime);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        return formatter.format(zdt.withZoneSameInstant(ZoneId.from(zdt).of("America/Sao_Paulo")));
    }
}
