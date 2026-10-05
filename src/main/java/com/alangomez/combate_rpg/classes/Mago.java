package com.alangomez.combate_rpg.classes;

// HERENCIA: Mago extiende Personaje
// INTERFACE: implementa Curable, por lo que debe tener curar()
public class Mago extends Personaje implements Curable {

    private static final double COSTO_RAYO = 5.0;
    private static final double COSTO_BOLA_FUEGO = 30.0;
    private static final double COSTO_CURACION = 20.0;
    private static final double MANA_POR_NIVEL = 20.0;

    // Atributos propios
    private double mana;
    private double manaMax;

    // Constructor parametrizado: usa super(...)
    public Mago(String nombre, double puntosVidaMax, double puntosAtaque,
                double puntosDefensa, double mana) {
        super(nombre, puntosVidaMax, puntosAtaque, puntosDefensa);

        if (mana <= 0) {
            System.out.println("Maná inválido, se asignarán 100.0 puntos por defecto");
            this.manaMax = 100.0;
        } else {
            this.manaMax = mana;
        }
        this.mana = this.manaMax;
    }

    // Constructor predeterminado: usa this(...)
    public Mago() {
        this("Mago Aprendiz", 80.0, 20.0, 2.0, 100.0);
    }

    public double getMana() {
        return mana;
    }

    public double getManaMax() {
        return manaMax;
    }

    // POLIMORFISMO: implementación propia de los métodos abstractos
    @Override
    public String getTipo() {
        return "Mago";
    }

    // Ataque básico: Rayo Arcano, ignora la mitad de la defensa y cuesta 5 de maná
    @Override
    public void atacar(Personaje objetivo) {
        double dano;

        if (this.mana >= COSTO_RAYO) {
            this.mana -= COSTO_RAYO;
            dano = this.puntosAtaque - objetivo.getPuntosDefensa() / 2;
            if (dano < 3.0) {
                dano = 3.0;
            }
        } else {
            dano = 3.0;
            System.out.printf("%s no tiene maná suficiente, el rayo se debilita%n", this.nombre);
        }

        System.out.printf("%s lanza Rayo Arcano contra %s y causa %.2f de daño%n",
                this.nombre, objetivo.getNombre(), dano);
        objetivo.recibirDano(dano);
    }

    // Habilidad especial: Bola de Fuego, daño doble que ignora la defensa y cuesta 30 de maná
    @Override
    public void habilidadEspecial(Personaje objetivo) {
        if (this.mana < COSTO_BOLA_FUEGO) {
            System.out.printf("%s no tiene maná suficiente para la Bola de Fuego%n", this.nombre);
            this.atacar(objetivo);
        } else {
            this.mana -= COSTO_BOLA_FUEGO;
            double dano = this.puntosAtaque * 2;
            System.out.printf("%s lanza una Bola de Fuego contra %s y causa %.2f de daño%n",
                    this.nombre, objetivo.getNombre(), dano);
            objetivo.recibirDano(dano);
        }
    }

    // Implementación de la interfaz Curable: cuesta 20 de maná
    @Override
    public void curar() {
        if (this.puntosVida >= this.puntosVidaMax) {
            System.out.printf("%s ya tiene la vida completa, no gasta maná%n", this.nombre);
            return;
        }
        if (this.mana < COSTO_CURACION) {
            System.out.printf("%s no tiene maná suficiente para curarse%n", this.nombre);
            return;
        }

        this.mana -= COSTO_CURACION;
        double vidaAntigua = this.puntosVida;

        this.puntosVida += CURACION_BASE + 5.0;
        if (this.puntosVida > this.puntosVidaMax) {
            this.puntosVida = this.puntosVidaMax;
        }

        System.out.printf("%s recuperó %.2f de vida. Vida actual: %.2f / %.2f%n",
                this.nombre, this.puntosVida - vidaAntigua, this.puntosVida, this.puntosVidaMax);
        System.out.printf("Maná restante: %.2f / %.2f%n", this.mana, this.manaMax);
    }

    @Override
    public void subirNivel() {
        super.subirNivel();
        this.manaMax += MANA_POR_NIVEL;
        this.mana = this.manaMax;
        System.out.printf("-> Maná: %.2f / %.2f%n", this.mana, this.manaMax);
    }

    @Override
    public void mostrarEstado() {
        super.mostrarEstado();
        System.out.printf("-> Maná: %.2f / %.2f%n", this.mana, this.manaMax);
    }
}