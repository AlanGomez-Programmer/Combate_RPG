
package com.alangomez.combate_rpg.classes;

public class Mago extends Personaje implements Curable{
    // Atributos propios
    private double mana;
    private double manaMax;
    private String tipo;
    
    // Constructor parametrizado
    public Mago(String nombre, double puntosVidaMax, double puntosAtaque, double puntosDefensa, double manaMax){
        super(nombre, puntosVidaMax, puntosAtaque, puntosDefensa);
        this.manaMax = manaMax;
        this.mana = this.manaMax;
        this.tipo = "Mago";
    }
    
    public Mago(){
        this("Mago Aprendiz", 80.0, 20.0, 2.0, 100.0);
    }
    
    // getters
    public double getMana(){
        return this.mana;
    }
    
    public double getManaMax(){
        return this.manaMax;
    }
    
     public String getTipo(){
        return this.tipo;
    }
     
    // setters
    public void setMana(double mana){
        this.mana = mana;
    }
    
    public void setManaMax(double manaMax){
        this.manaMax = manaMax;
    }
    
    // Métodos
    // Rayo arcano: este ataque ignora la mitad de la defensa del objetivo y consume 5 de mana
    @Override
    public void atacar(Personaje objetivo){
        double rayoArcano = this.getPuntosAtaque() - objetivo.getPuntosDefensa()/2;
        
        if (rayoArcano < 3){rayoArcano = 3;}
        if (this.getMana() <= 0){ rayoArcano = 3;}
        // consumo de mana
        this.setMana(this.getMana() - 5);
        objetivo.recibirDano(rayoArcano);
    }
    
    // Habilidad especial: bola de fuego, el golpe es el doble e ignora la defensa del objetivo
    public void habilidadEspecial(Personaje objetivo){
        double dano = (this.getPuntosAtaque()*2);
        if (this.mana <= 0){
            System.out.printf("%s ya no tienes manas suficiente %n", this.getNombre());
            this.atacar(objetivo);
        }
        objetivo.recibirDano(dano);
    }
    
    @Override
    public void curar(){
        double vidaAntigua = this.getPuntosVida();
        this.setPuntosVida(this.getPuntosVida() + CURACION_BASE + 5.00);
        this.setMana(this.getMana() - 20);
        
        if (this.getPuntosVida() > this.getPuntosVidaMax()){
            this.setPuntosVida(this.getPuntosVidaMax());
        }
        
        double vidaRecuperada = this.getPuntosVida() - vidaAntigua;
        
        System.out.printf("%s has recuperado %.2f de vida %n", this.getNombre(), vidaRecuperada);
        System.out.printf("Vida actual: %.2f / %.2f %n", this.getPuntosVida(), this.getPuntosVidaMax());
        
        if (this.getMana() <= 0) {System.out.println("No hay mana suficiente");}
    }
    
    @Override
    public void subirNivel(){
        super.subirNivel();
        
        this.setManaMax(this.getManaMax() + 20);
        this.setMana(this.getManaMax());
        
         System.out.printf("->  %.2f de mana %n", this.getManaMax());
    }
    
    @Override 
    public void mostrarEstado(){
        super.mostrarEstado();
        System.out.printf("-> Mana: %.2f / %.2f %n", this.getMana(), this.getManaMax());
    }
}
