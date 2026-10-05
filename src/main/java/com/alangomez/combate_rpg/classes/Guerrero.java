
package com.alangomez.combate_rpg.classes;

public class Guerrero extends Personaje implements Curable{
    private double escudo;
    private final double SUMA_ESCUDO = 10.0;
    private String tipo;

    // Constructor Parametrizado
    public Guerrero(String nombre, double puntosVidaMax, double puntosAtaque, double puntosDefensa, double escudo) {
        super(nombre, puntosVidaMax, puntosAtaque, puntosDefensa);
        this.escudo = escudo;
        this.tipo = "Gerrero";
    }
    
    // Constructor Predeterminado
    public Guerrero() {
        this("Guerrero Novato", 120.0, 15.0, 8.0, 20.0);
    }
    
    // getters
    public double getEscudo(){
        return this.escudo;
    }
    
     public String getTipo(){
        return this.tipo;
    }
    
    // setter
    public void setEscudo(double escudo){
        this.escudo = escudo;
    }
    
  
    
    // Métodos
    @Override
    public void atacar(Personaje objetivo){
        double dano = calcularDanoBase(objetivo);
        System.out.printf("%s atacó a %s y le causo %.2f de daño %n", this.getNombre(), objetivo.getNombre(), dano);
    }
    
    
    // Habilidad especial: Golpe furioso, esta habilidad hace que el guerrero se lastime asi mismo
    public void habilidadEspecial(Personaje objetivo){
        double dano = this.getPuntosAtaque() * 1.5 - objetivo.getPuntosDefensa();
        
        // Si el daño es menor a 3 el minimo del daño es 3
        if (dano < 3) { dano = 3; }
        
        // Daño a provocar
        objetivo.recibirDano(dano);
        // Daño que se genera asi mismo el guerrero utilizando esta habilidad
        this.setPuntosVida(this.getPuntosVida() - 10);
    }
    
    @Override
    public void recibirDano(double dano){
        double absorcionDano = dano - this.getEscudo();
        super.recibirDano(absorcionDano);
    }
    
    @Override
    public void curar(){
        double vidaAntigua = this.getPuntosVida();
        this.setPuntosVida(this.getPuntosVida() + CURACION_BASE);
        if (this.getPuntosVida() > this.getPuntosVidaMax()) { this.getPuntosVidaMax(); }
        double vidaRecuperada = this.getPuntosVida() - vidaAntigua;
        System.out.printf("%s has recuperado %.2f de vida %n", this.getNombre(), vidaRecuperada);
        System.out.printf("Vida actual: %.2f / %.2f %n", this.getPuntosVida(), this.getPuntosVidaMax());
    }
    
    @Override
    public void subirNivel(){
        super.subirNivel();
        this.setEscudo(this.getEscudo() + this.SUMA_ESCUDO);
        System.out.printf("->  %.2f de escudo %n", this.getEscudo());
    }
    
    @Override
    public void mostrarEstado(){
        super.mostrarEstado();
        System.out.printf("-> Escudo: %.2f %n", this.getEscudo());
    }
}
