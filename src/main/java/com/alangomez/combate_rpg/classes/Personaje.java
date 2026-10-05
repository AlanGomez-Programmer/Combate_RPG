
package com.alangomez.combate_rpg.classes;

public abstract class Personaje implements Mejorable{
    // Atributos de instancia
    protected String nombre;
    protected double puntosVida;
    protected  double puntosVidaMax;
    protected double puntosAtaque;
    protected double puntosDefensa;
    protected int nivel;
    
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

    public void setPuntosVida(double puntosVida) {
        this.puntosVida = puntosVida;
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

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
    
    
    // Métodos de instancia
    public void atacar(Personaje objetivo){
        double dano = this.getPuntosAtaque() - objetivo.getPuntosDefensa();
        
        if (dano <= 0){
            dano = 3;
        }
        
        objetivo.recibirDano(dano);
        
        System.out.printf("%s atacó a %s y le provocó %.2f de daño %n", this.getNombre(), objetivo.getNombre(), dano);
    };
    
    public void recibirDano(double cantidad){
        
       double cantidadDano = this.getPuntosVida() - cantidad;
       this.setPuntosVida(cantidadDano);
        
        if (this.getPuntosVida() < 0){
            this.setPuntosVida(0.0);
        }   
    };
    
    public void curar(){
        double vidaAntigua = this.getPuntosVida();
        
        this.setPuntosVida(this.getPuntosVida() + 25);
        
        if (this.getPuntosVida() > this.getPuntosVidaMax()){
            this.setPuntosVida(this.getPuntosVidaMax());
        }
        
        double vidaRecuperada = this.getPuntosVida() - vidaAntigua;
        System.out.printf("%s has recuperado %.2f de vida %n", this.getNombre(), vidaRecuperada);
        System.out.printf("Vida actual: %.2f / %.2f %n", this.getPuntosVida(), this.getPuntosVidaMax());
       
    };
    
    public boolean estaVivo(){
        return this.getPuntosVida() > 0.0;
    };
    
    protected double calcularDanoBase(Personaje objetivo){
        double calculo = this.getPuntosAtaque() - objetivo.getPuntosDefensa(); 
        return  (calculo <= 0) ? calculo : 3;
    }  
    
    // Se agrega el @Override ya que este método ya esta en mejorable y se esta sobre escribiendo aqui
    @Override
    public void subirNivel(){
        this.setNivel(this.getNivel() + 1);
        this.setPuntosVidaMax(this.getPuntosVidaMax() + 20);
        this.setPuntosAtaque(this.getPuntosAtaque() + 5 );
        this.setPuntosDefensa(this.getPuntosDefensa() + 2);
        this.setPuntosVida(this.getPuntosVidaMax());
        
        System.out.printf("¡Felicidades, %s! %n", this.getNombre());
        this.mostrarMensajeNivel(this.getNivel());
        System.out.println("has recibido:");
        System.out.printf("-> %.2f de ataque %n", this.getPuntosAtaque());
        System.out.printf("-> %.2f de defensa %n",  this.getPuntosDefensa());
        System.out.printf("-> %.2f / %.2f de vida %n", this.getPuntosVida(), this.getPuntosVidaMax());
        
    };
    
    public void mostrarEstado(){
        System.out.printf("-> Nombre: %s %n", this.getNombre());
        System.out.printf("-> Nivel: %d %n", this.getNivel());
        System.out.printf("-> Vida: %.2f / %.2f %n", this.getPuntosVida(), this.getPuntosVidaMax());
        System.out.printf("-> Ataque: %.2f %n", this.getPuntosAtaque());
        System.out.printf("-> Defensa: %.2f %n", this.getPuntosDefensa());
    };
    
   
    
    
}
