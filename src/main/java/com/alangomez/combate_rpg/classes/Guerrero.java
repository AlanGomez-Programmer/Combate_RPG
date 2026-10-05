package com.alangomez.combate_rpg.classes;

// HERENCIA: Guerrero extiende Personaje
// INTERFACE: implementa Curable, por lo que debe tener curar()
public class Guerrero extends Personaje implements Curable {

    private static final double ESCUDO_POR_NIVEL = 10.0;

    // Atributo propio
    private double escudo;

    // Constructor parametrizado: usa super(...) para inicializar lo heredado de Personaje
    public Guerrero(String nombre, double puntosVidaMax, double puntosAtaque,
                    double puntosDefensa, double escudo) {
        super(nombre, puntosVidaMax, puntosAtaque, puntosDefensa);

        if (escudo < 0) {
            System.out.println("Escudo inválido, se asignarán 20.0 puntos por defecto");
            this.escudo = 20.0;
        } else {
            this.escudo = escudo;
        }
    }

    // Constructor predeterminado: usa this(...) para reutilizar el constructor parametrizado
    public Guerrero() {
        this("Guerrero Novato", 120.0, 15.0, 8.0, 20.0);
    }

    public double getEscudo() {
        return escudo;
    }

    // POLIMORFISMO: implementación propia de los métodos abstractos
    @Override
    public String getTipo() {
        return "Guerrero";
    }

    @Override
    public void atacar(Personaje objetivo) {
        double dano = calcularDanoBase(objetivo);
        System.out.printf("%s ataca a %s y causa %.2f de daño%n",
                this.nombre, objetivo.getNombre(), dano);
        objetivo.recibirDano(dano);
    }

    // Habilidad especial: Golpe Furioso
    @Override
    public void habilidadEspecial(Personaje objetivo) {
        double dano = this.puntosAtaque * 1.5 - objetivo.getPuntosDefensa();
        if (dano < 3.0) {
            dano = 3.0;
        }

        System.out.printf("%s usa Golpe Furioso contra %s y causa %.2f de daño%n",
                this.nombre, objetivo.getNombre(), dano);
        objetivo.recibirDano(dano);

        // El guerrero se lastima a sí mismo, sin bajar de 1.0
        if (this.puntosVida > 1.0) {
            this.puntosVida = Math.max(this.puntosVida - 10.0, 1.0);
        }
        System.out.printf("%s se lastima con el esfuerzo. Vida actual: %.2f / %.2f%n",
                this.nombre, this.puntosVida, this.puntosVidaMax);
    }

    // SOBRESCRITURA: el escudo absorbe el daño primero
    @Override
    public void recibirDano(double cantidad) {
        if (cantidad <= this.escudo) {
            this.escudo -= cantidad;
            System.out.printf("El escudo de %s absorbe todo el daño (escudo restante: %.2f)%n",
                    this.nombre, this.escudo);
        } else {
            double restante = cantidad - this.escudo;
            if (this.escudo > 0) {
                System.out.printf("El escudo de %s absorbe %.2f de daño y se rompe%n",
                        this.nombre, this.escudo);
            }
            this.escudo = 0.0;
            // super.método(): Personaje se encarga de restar la vida
            super.recibirDano(restante);
        }
    }

    // Implementación de la interfaz Curable
    @Override
    public void curar() {
        double vidaAntigua = this.puntosVida;

        this.puntosVida += CURACION_BASE;
        if (this.puntosVida > this.puntosVidaMax) {
            this.puntosVida = this.puntosVidaMax;
        }

        System.out.printf("%s recuperó %.2f de vida. Vida actual: %.2f / %.2f%n",
                this.nombre, this.puntosVida - vidaAntigua, this.puntosVida, this.puntosVidaMax);
    }

    @Override
    public void subirNivel() {
        super.subirNivel();
        this.escudo += ESCUDO_POR_NIVEL;
        System.out.printf("-> Escudo: %.2f%n", this.escudo);
    }

    @Override
    public void mostrarEstado() {
        super.mostrarEstado();
        System.out.printf("-> Escudo: %.2f%n", this.escudo);
    }
}