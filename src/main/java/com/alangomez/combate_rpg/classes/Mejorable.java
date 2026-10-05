
package com.alangomez.combate_rpg.classes;

public interface Mejorable {
    void subirNivel();
    
    default void mostrarMensajeNivel(int nivel){
        System.out.printf("*** ¡Alcanzó el nivel %d! *** %n", nivel);
    }
}
