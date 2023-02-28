/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.webapp.todo.config;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvBuilder;

/**
 *
 * @author jpmgo
 */

public class Env {

    private static final Dotenv dotenv = new DotenvBuilder().ignoreIfMissing().load();

    public static String get(String key, String defaultValue) {
        String value = dotenv.get(key);

        if (value == null) {
            value = System.getenv(key);
        }

        if (value == null) {
            value = defaultValue;
        }

        return value;
    }
}
