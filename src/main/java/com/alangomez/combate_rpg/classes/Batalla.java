
package com.alangomez.combate_rpg.classes;

public class Batalla {
       
    // Métodos estaticos
    public void ejecutarAtaqueCritico(Personaje atacante, Personaje objetivo, double multiplicador){
        int ataquePotenciado = (int) atacante.getPuntosAtaque() * (int) multiplicador;
        
        objetivo.recibirDano(ataquePotenciado);
        
        System.out.printf("%s has dado un golpe critico a %s %n", atacante.getNombre(), objetivo.getNombre());
    }
    
    public void iniciarPeleaAutomatica(Personaje p1, Personaje p2){
        int round = 1;
        
        while (p1.estaVivo() && p2.estaVivo()) {
           
               System.out.printf("Round %d %n", round);
           
               p1.atacar(p2);
               
               if (p2.estaVivo()){
                   p2.atacar(p1);
               }
          
           round++;
        }
        
        String ganador = p1.estaVivo() ? p1.getNombre() : p2.getNombre();
        System.out.printf("El ganador es : %s %n",ganador);
    }
}
