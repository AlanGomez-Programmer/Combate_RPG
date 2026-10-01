
package com.alangomez.combate_rpg.classes;

public class Personaje {
    // Atributos de instancia
    private String nombre;
    private double puntosVida;
    private double puntosVidaMax;
    private double puntosAtaque;
    private double puntosDefensa;
    private int nivel;
    
    // Atributo estatico
    private static int totalPersonajesCreados = 0;
    private static int totalPersonajesPredeterminados = 0;
    
    
    // Constructores
       // Constructor Predeterminado
    public Personaje(){
        
        ++totalPersonajesPredeterminados;
        String numeroPersonaje = (totalPersonajesPredeterminados == 1) ? "" : String.valueOf(totalPersonajesPredeterminados); 
        this.nombre = "Guerrero Novato " + numeroPersonaje;
        this.puntosVidaMax = 100.0;
        this.puntosVida = 100.0;
        this.puntosAtaque = 15.0;
        this.puntosDefensa = 5.0;
        this.nivel = 1;
        
        totalPersonajesCreados++;
    }
    
    // Constructor parametrizado
    public Personaje(String nombre, double puntosVidaMax, double puntosAtaque, double puntosDefensa) {

        // Validación del nombre
        if (nombre != null && !nombre.isBlank()) {
            this.nombre = nombre;
        } else {
            ++totalPersonajesPredeterminados;
            String numeroPersonaje = (totalPersonajesPredeterminados == 1) ? "" : String.valueOf(totalPersonajesPredeterminados);
            this.nombre = "Guerrero Novato " + numeroPersonaje;
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

        // Suma el nuevo personaje ingresado
        totalPersonajesCreados++;
    }
    // Métodos get y set
    
        // getter
    public String getNombre(){
        return nombre;
    }

    public int getNivel() {
        return nivel;
    }
    
    public double getPuntosVidaMax(){
        return puntosVidaMax;
    }
    
    public double getPuntosVida(){
        return puntosVida;
    }
    
    public double getPuntosAtaque(){
        return puntosAtaque;
    }
    
    public double getPuntosDefensa(){
        return puntosDefensa;
    }
    
     // Método estático
    public static int getTotalPersonajesCreados(){
      return totalPersonajesCreados;  
    };
    
        // Setter
    public void setNombre(String nombre){
        if (nombre != null && !nombre.trim().isEmpty()){
           this.nombre = nombre;
        }
    }

    public void setPuntosVidaMax(double puntosVidaMax){
        if (puntosVidaMax <= 0){
            System.out.println("No se aceptan valores menores o iguales a 0 en el máximo de puntos de vida");
        } else {
            this.puntosVidaMax = puntosVidaMax;
        }
    }
    
    public void setPuntosAtaque(double puntosAtaque){
        if (puntosAtaque <= 0){
            System.out.println("No se aceptan valores menores o iguales a 0 en los puntos de ataque");
        } else {
            this.puntosAtaque = puntosAtaque;
        } 
    }
    
    public void setPuntosDefensa(double puntosDefensa){
        if (puntosDefensa <= 0){
            System.out.println("No se aceptan valores menores o iguales a 0 en los puntos de defensa");
        } else {
            this.puntosDefensa = puntosDefensa;
        }
    }
    
    
    // Métodos de instancia
    public void atacar(Personaje objetivo){
        double dano = this.puntosAtaque - objetivo.puntosDefensa;
        
        if (dano <= 0){
            dano = 3;
        }
        
        objetivo.recibirDano(dano);
        
        System.out.printf("%s atacó a %s y le provocó %.2f de daño %n", this.getNombre(), objetivo.getNombre(), dano);
    };
    
    public void recibirDano(double cantidad){
       
        this.puntosVida -= cantidad;
        
        if (this.puntosVida < 0){
            this.puntosVida = 0.0;
        }   
    };
    
    public void curar(){
        double vidaAntigua = this.puntosVida;
        
        this.puntosVida += 25;
        
        if (this.puntosVida > this.puntosVidaMax){
            this.puntosVida = this.puntosVidaMax;
        }
        
        double vidaRecuperada = this.puntosVida - vidaAntigua;
        System.out.printf("%s has recuperado %.2f de vida %n", this.getNombre(), vidaRecuperada);
        System.out.printf("Vida actual: %.2f / %.2f %n", this.getPuntosVida(), this.getPuntosVidaMax());
       
    };
    
    public boolean estaVivo(){
        return this.puntosVida > 0;
    };
    
    public void subirNivel(){
        this.nivel += 1;
        this.puntosVidaMax += 20;
        this.puntosAtaque += 5;
        this.puntosDefensa += 2;
        this.puntosVida = this.puntosVidaMax;
        
        System.out.printf("¡Felicidades, %s!, has subido de nivel %n", this.getNombre());
        System.out.printf("has recibido %.2f / %.2f de vida | %.2f de ataque | %.2f de defensa %n", 
                this.getPuntosVida(),
                this.getPuntosVidaMax(), 
                this.getPuntosAtaque(), this.getPuntosDefensa());
    };
    
    public void mostrarEstado(){
        System.out.printf("-> Nombre: %s %n", this.getNombre());
        System.out.printf("-> Nivel: %d %n", this.getNivel());
        System.out.printf("-> Vida: %.2f / %.2f %n", this.getPuntosVida(), this.getPuntosVidaMax());
        System.out.printf("-> Ataque: %.2f %n", this.getPuntosAtaque());
        System.out.printf("-> Defensa: %.2f %n", this.getPuntosDefensa());
    };
    
   
    
    
}
