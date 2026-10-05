package com.alangomez.combate_rpg.classes;

// CLASE ABSTRACTA: no se puede hacer new Personaje(...), solo sirve como base para las clases hijas.
// INTERFACE: implementa Mejorable, por eso debe tener subirNivel() y recibe mostrarMensajeNivel().
public abstract class Personaje implements Mejorable {

    // Atributos de instancia protected: las clases hijas pueden usarlos directamente (HERENCIA)
    protected String nombre;
    protected double puntosVida;
    protected double puntosVidaMax;
    protected double puntosAtaque;
    protected double puntosDefensa;
    protected int nivel;

    // Atributos estáticos: pertenecen a la clase y se comparten entre todos los personajes
    private static int totalPersonajesCreados = 0;
    private static int totalPersonajesSinNombre = 0;

    // Constructor: las clases hijas lo llaman con super(...)
    public Personaje(String nombre, double puntosVidaMax, double puntosAtaque, double puntosDefensa) {

        // Validación del nombre
        if (nombre != null && !nombre.isBlank()) {
            this.nombre = nombre.trim();
        } else {
            totalPersonajesSinNombre++;
            this.nombre = (totalPersonajesSinNombre == 1) ? "Aventurero" : "Aventurero " + totalPersonajesSinNombre;
            System.out.printf("Nombre vacío, se asignará \"%s\"%n", this.nombre);
        }

        // Validación de los datos ingresados
        boolean vidaInvalida = puntosVidaMax <= 0;
        boolean ataqueInvalido = puntosAtaque <= 0;
        boolean defensaInvalida = puntosDefensa <= 0;

        if (vidaInvalida || ataqueInvalido || defensaInvalida) {
            System.out.printf("-> Estimado usuario: %s%n", this.nombre);
            System.out.println("No se aceptan valores menores o iguales a 0");
        }

        // Vida
        if (vidaInvalida) {
            System.out.println("Por defecto se asignarán 100.0 puntos de vida");
            this.puntosVidaMax = 100.0;
        } else {
            this.puntosVidaMax = puntosVidaMax;
        }
        this.puntosVida = this.puntosVidaMax;

        // Ataque
        if (ataqueInvalido) {
            System.out.println("Por defecto se asignarán 15.0 puntos de ataque");
            this.puntosAtaque = 15.0;
        } else {
            this.puntosAtaque = puntosAtaque;
        }

        // Defensa
        if (defensaInvalida) {
            System.out.println("Por defecto se asignarán 5.0 puntos de defensa");
            this.puntosDefensa = 5.0;
        } else {
            this.puntosDefensa = puntosDefensa;
        }

        this.nivel = 1;

        // Suma el nuevo personaje al contador estático
        totalPersonajesCreados++;
    }

    // ===== Getters =====
    public String getNombre() {
        return nombre;
    }

    public int getNivel() {
        return nivel;
    }

    public double getPuntosVida() {
        return puntosVida;
    }

    public double getPuntosVidaMax() {
        return puntosVidaMax;
    }

    public double getPuntosAtaque() {
        return puntosAtaque;
    }

    public double getPuntosDefensa() {
        return puntosDefensa;
    }

    // Método estático: se llama con Personaje.getTotalPersonajesCreados()
    public static int getTotalPersonajesCreados() {
        return totalPersonajesCreados;
    }

    // ===== Métodos ABSTRACTOS: cada clase hija los implementa a su manera (POLIMORFISMO) =====
    public abstract void atacar(Personaje objetivo);

    public abstract void habilidadEspecial(Personaje objetivo);

    public abstract String getTipo();

    // ===== Métodos concretos: las clases hijas los heredan o los sobrescriben =====
    public void recibirDano(double cantidad) {
        this.puntosVida -= cantidad;

        if (this.puntosVida < 0) {
            this.puntosVida = 0.0;
        }
    }

    public boolean estaVivo() {
        return this.puntosVida > 0.0;
    }

    // protected: solo lo usan Personaje y sus clases hijas
    protected double calcularDanoBase(Personaje objetivo) {
        double calculo = this.puntosAtaque - objetivo.puntosDefensa;
        return (calculo <= 0) ? 3.0 : calculo;
    }

    // @Override porque implementa el método declarado en la interfaz Mejorable
    @Override
    public void subirNivel() {
        this.nivel += 1;
        this.puntosVidaMax += 20.0;
        this.puntosAtaque += 5.0;
        this.puntosDefensa += 2.0;
        this.puntosVida = this.puntosVidaMax;

        // Método default de la interfaz Mejorable
        this.mostrarMensajeNivel(this.nivel);

        System.out.printf("¡Felicidades, %s! Ahora tienes:%n", this.nombre);
        System.out.printf("-> Vida: %.2f / %.2f%n", this.puntosVida, this.puntosVidaMax);
        System.out.printf("-> Ataque: %.2f%n", this.puntosAtaque);
        System.out.printf("-> Defensa: %.2f%n", this.puntosDefensa);
    }

    public void mostrarEstado() {
        // getTipo() es abstracto: POLIMORFISMO decide qué tipo se imprime
        System.out.printf("-> Tipo: %s%n", this.getTipo());
        System.out.printf("-> Nombre: %s%n", this.nombre);
        System.out.printf("-> Nivel: %d%n", this.nivel);
        System.out.printf("-> Vida: %.2f / %.2f%n", this.puntosVida, this.puntosVidaMax);
        System.out.printf("-> Ataque: %.2f%n", this.puntosAtaque);
        System.out.printf("-> Defensa: %.2f%n", this.puntosDefensa);
    }
}