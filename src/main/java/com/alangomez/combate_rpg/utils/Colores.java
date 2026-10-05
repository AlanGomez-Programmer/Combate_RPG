package com.alangomez.combate_rpg.utils;

// Clase de utilidad para mostrar texto de colores en la consola usando códigos ANSI.
// Todos los métodos son estáticos: se usan sin crear objetos, por ejemplo Colores.verde("Hola").
public class Colores {

    // Códigos ANSI: le indican a la consola qué color usar.
    // Son públicos para poder guardar el color de cada personaje (Colores.AZUL, Colores.AMARILLO).
    public static final String RESET = "\u001B[0m";
    public static final String ROJO = "\u001B[31m";
    public static final String VERDE = "\u001B[32m";
    public static final String AMARILLO = "\u001B[33m";
    public static final String AZUL = "\u001B[34m";

    // Constructor privado: no tiene sentido crear objetos de esta clase
    private Colores() {
    }

    // Método general: colorea un texto con el color que se le pase
    public static String colorear(String texto, String color) {
        return color + texto + RESET;
    }

    // ===== Devuelven el texto coloreado (para usarlo dentro de printf o concatenaciones) =====
    public static String rojo(String texto) {
        return colorear(texto, ROJO);
    }

    public static String verde(String texto) {
        return colorear(texto, VERDE);
    }

    public static String amarillo(String texto) {
        return colorear(texto, AMARILLO);
    }

    public static String azul(String texto) {
        return colorear(texto, AZUL);
    }

    // ===== Imprimen directamente una línea de color =====
    public static void imprimirRojo(String texto) {
        System.out.println(rojo(texto));
    }

    public static void imprimirVerde(String texto) {
        System.out.println(verde(texto));
    }

    public static void imprimirAmarillo(String texto) {
        System.out.println(amarillo(texto));
    }

    public static void imprimirAzul(String texto) {
        System.out.println(azul(texto));
    }
}