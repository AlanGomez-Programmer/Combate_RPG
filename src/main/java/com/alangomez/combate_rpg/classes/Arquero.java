
package com.alangomez.combate_rpg.classes;

public class Arquero extends Personaje{
    // Atributo propio
    private int presicion;
    private String tipo;
    
    // Constructor parametrizado
    public Arquero(String nombre, double puntosVidaMax, double puntosAtaque, double puntosDefensa, int presicion){
        super(nombre, puntosVidaMax, puntosAtaque, puntosDefensa);
        
        boolean presicionValida = presicion < 0 && presicion > 100;
        
        if (presicionValida){
            System.out.println("Por defecto se asignarán 40 puntos de presición");
            this.presicion = 40;
        } else {
            this.presicion = presicion;
        }
        
        this.tipo = "Arquero";
    }
    
    // Constructor predeterminado
    public Arquero(){
        this( "Arquero Novato", 90.0, 18.0, 4.0, 40);
    }
    
    // getter
    public int getPresicion(){
        return this.presicion;
    }
    
    public String getTipo(){
        return this.tipo;
    }
    
    // setter
    public void setPresicion(int presicion){
        this.presicion = presicion;
    }
    
    // Métodos
    @Override
    public void atacar(Personaje objetivo){
        double dano = Math.random() * 100;
        
        if (dano < this.getPresicion()){
            //  Ejecución de ataque critico
            
        } else {
            objetivo.recibirDano(dano);
        }
    }
    
    // Habilidad especial: Lluvia de flechas, se lanzan 3 flechas
    public void habilidadEspecial(Personaje objetivo){
        double cantidadImpactos = 3;
        
        for (int i = 0; i < cantidadImpactos; i++){
            System.out.printf("%s has lanzado una flecha a %s %n", this.getNombre(), objetivo.getNombre());
            double dano = ((this.getPuntosAtaque() * 0.6)-objetivo.getPuntosDefensa());
            if (dano < 3) { dano = 3; }
            objetivo.recibirDano(dano);
            System.out.printf("%s has recibido una flecha de %s %n", objetivo.getNombre(),this.getNombre());
        }   
    }
    
    @Override
    public void subirNivel(){
        super.subirNivel();
        this.setPresicion(this.getPresicion() + 3);
        
        if (this.getPresicion() > 95){ this.setPresicion(95); }
        
        System.out.printf("-> %d de presición %n", this.getPresicion() );
    }
    
    @Override
    public void mostrarEstado(){
        super.mostrarEstado();
        System.out.printf("-> Nivel: %d %n", this.getPresicion());
    }
}
