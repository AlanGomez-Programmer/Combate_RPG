package com.alangomez.combate_rpg.classes;

// HERENCIA: Arquero extiende Personaje
// NO implementa Curable: el Arquero no puede curarse
public class Arquero extends Personaje {

    private static final int PRECISION_MAXIMA_NIVEL = 95;

    // Atributo propio (entre 0 y 100)
    private int precision;

    // Constructor parametrizado: usa super(...)
    public Arquero(String nombre, double puntosVidaMax, double puntosAtaque,
                   double puntosDefensa, int precision) {
        super(nombre, puntosVidaMax, puntosAtaque, puntosDefensa);

        if (precision < 0 || precision > 100) {
            System.out.println("Precisión inválida (debe estar entre 0 y 100), se asignará 40 por defecto");
            this.precision = 40;
        } else {
            this.precision = precision;
        }
    }

    // Constructor predeterminado: usa this(...)
    public Arquero() {
        this("Arquero Novato", 90.0, 18.0, 4.0, 40);
    }

    public int getPrecision() {
        return precision;
    }

    // POLIMORFISMO: implementación propia de los métodos abstractos
    @Override
    public String getTipo() {
        return "Arquero";
    }

    // Ataque básico: puede ser crítico según la precisión
    @Override
    public void atacar(Personaje objetivo) {
        double probabilidad = Math.random() * 100;

        if (probabilidad < this.precision) {
            Batalla.ejecutarAtaqueCritico(this, objetivo, 1.5);
        } else {
            double dano = calcularDanoBase(objetivo);
            System.out.printf("%s dispara una flecha a %s y causa %.2f de daño%n",
                    this.nombre, objetivo.getNombre(), dano);
            objetivo.recibirDano(dano);
        }
    }

    // Habilidad especial: Lluvia de Flechas, 3 impactos
    @Override
    public void habilidadEspecial(Personaje objetivo) {
        System.out.printf("%s usa Lluvia de Flechas contra %s%n", this.nombre, objetivo.getNombre());

        for (int i = 1; i <= 3; i++) {
            double dano = this.puntosAtaque * 0.6 - objetivo.getPuntosDefensa();
            if (dano < 3.0) {
                dano = 3.0;
            }
            System.out.printf("  Impacto %d: %.2f de daño%n", i, dano);
            objetivo.recibirDano(dano);
        }
    }

    @Override
    public void subirNivel() {
        super.subirNivel();

        if (this.precision < PRECISION_MAXIMA_NIVEL) {
            this.precision = Math.min(this.precision + 3, PRECISION_MAXIMA_NIVEL);
        }
        System.out.printf("-> Precisión: %d%n", this.precision);
    }

    @Override
    public void mostrarEstado() {
        super.mostrarEstado();
        System.out.printf("-> Precisión: %d%n", this.precision);
    }
}