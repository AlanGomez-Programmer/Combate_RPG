package com.alangomez.combate_rpg.classes;

// Clase de utilidad: todos sus métodos son estáticos.
// POLIMORFISMO: todos los métodos reciben referencias de tipo Personaje,
// por eso funcionan con Guerrero, Mago o Arquero sin preguntar el tipo.
public class Batalla {

    // Constructor privado: no tiene sentido crear objetos Batalla
    private Batalla() {
    }

    public static void ejecutarAtaqueCritico(Personaje atacante, Personaje objetivo, double multiplicador) {
        // Casting explícito: primero se multiplica y luego se convierte a int (redondea hacia abajo)
        int dano = (int) (atacante.getPuntosAtaque() * multiplicador);

        System.out.printf("¡Golpe Crítico! %s golpea a %s y causa %d de daño%n",
                atacante.getNombre(), objetivo.getNombre(), dano);
        objetivo.recibirDano(dano);
    }

    public static void intentarCurar(Personaje p) {
        // instanceof: única excepción permitida, consulta si el objeto tiene la capacidad Curable
        if (p instanceof Curable) {
            Curable curable = (Curable) p;   // casting a la interfaz para poder llamar a curar()
            curable.curar();
        } else {
            System.out.printf("%s es un %s y no puede curarse%n", p.getNombre(), p.getTipo());
        }
    }

    public static void iniciarPeleaAutomatica(Personaje p1, Personaje p2) {
        int turno = 1;
        System.out.println("--- BATALLA AUTOMÁTICA ---");

        while (p1.estaVivo() && p2.estaVivo()) {
            System.out.printf("Turno %d%n", turno);

            // POLIMORFISMO: Java ejecuta el atacar() o habilidadEspecial() de la clase real
            if (turno % 3 == 0) {
                p1.habilidadEspecial(p2);
            } else {
                p1.atacar(p2);
            }

            if (p2.estaVivo()) {
                if (turno % 3 == 0) {
                    p2.habilidadEspecial(p1);
                } else {
                    p2.atacar(p1);
                }
            }

            turno++;
        }

        Personaje ganador = p1.estaVivo() ? p1 : p2;
        System.out.printf(">>> El ganador es: %s (%s)%n", ganador.getNombre(), ganador.getTipo());
    }
}