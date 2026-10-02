
package com.alangomez.combate_rpg;

import com.alangomez.combate_rpg.classes.Batalla;
import com.alangomez.combate_rpg.classes.Personaje;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int opcion;

        Personaje p1 = null;
        Personaje p2 = null;
        Batalla batalla = new Batalla();

        do {
            System.out.println("==========================================");
            System.out.println("        SIMULADOR DE COMBATE RPG");
            System.out.println("==========================================");
            System.out.println("1. Crear Personaje 1 (Constructor Predeterminado)");
            System.out.println("2. Crear Personaje 2 (Constructor Parametrizado)");
            System.out.println("3. Ver ficha técnica de los personajes");
            System.out.println("4. Subir de nivel a un personaje");
            System.out.println("5. Curar a un personaje");
            System.out.println("6. Realizar un ataque individual");
            System.out.println("7. Iniciar Batalla Automática (P1 vs P2)");
            System.out.println("8. Ver total de personajes creados en el sistema");
            System.out.println("9. Salir");
            System.out.println("==========================================");
            System.out.print("Seleccione una opción: ");
            opcion = input.nextInt();
            input.nextLine();

            switch (opcion) {
                case 1:
                    p1 = new Personaje();
                    System.out.printf("Personaje 1 creado: %s%n", p1.getNombre());
                    break;

                case 2:
                    System.out.print("Ingrese el nombre: ");
                    String nombre = input.nextLine();
                    System.out.print("Ingrese la vida máxima: ");
                    double vidaMax = input.nextDouble();
                    System.out.print("Ingrese los puntos de ataque: ");
                    double ataque = input.nextDouble();
                    System.out.print("Ingrese los puntos de defensa: ");
                    double defensa = input.nextDouble();
                    input.nextLine();

                    p2 = new Personaje(nombre, vidaMax, ataque, defensa);
                    System.out.printf("Personaje 2 creado: %s%n", p2.getNombre());
                    break;

                case 3:
                    System.out.println("=== Ficha Técnica ===");
                    if (p1 != null) {
                        p1.mostrarEstado();
                    } else {
                        System.out.println("El Personaje 1 no ha sido creado");
                    }
                    System.out.println("---------------------");
                    if (p2 != null) {
                        p2.mostrarEstado();
                    } else {
                        System.out.println("El Personaje 2 no ha sido creado");
                    }
                    break;

                case 4:
                    System.out.print("¿Qué personaje desea subir de nivel? (1 o 2): ");
                    int nivelSel = input.nextInt();
                    input.nextLine();

                    if (nivelSel == 1 && p1 != null) {
                        p1.subirNivel();
                    } else if (nivelSel == 2 && p2 != null) {
                        p2.subirNivel();
                    } else {
                        System.out.println("Selección inválida o el personaje no ha sido creado");
                    }
                    break;

                case 5:
                    System.out.print("¿Qué personaje desea curar? (1 o 2): ");
                    int curarSel = input.nextInt();
                    input.nextLine();

                    if (curarSel == 1 && p1 != null) {
                        p1.curar();
                    } else if (curarSel == 2 && p2 != null) {
                        p2.curar();
                    } else {
                        System.out.println("Selección inválida o el personaje no ha sido creado");
                    }
                    break;

                case 6:
                    if (p1 == null || p2 == null) {
                        System.out.println("Ambos personajes deben estar creados para atacar");
                        break;
                    }

                    System.out.print("¿Quién ataca? (1 = P1 ataca a P2, 2 = P2 ataca a P1): ");
                    int atacanteSel = input.nextInt();
                    input.nextLine();

                    Personaje atacante;
                    Personaje objetivo;

                    if (atacanteSel == 1) {
                        atacante = p1;
                        objetivo = p2;
                    } else if (atacanteSel == 2) {
                        atacante = p2;
                        objetivo = p1;
                    } else {
                        System.out.println("Selección inválida");
                        break;
                    }

                    if (!atacante.estaVivo()) {
                        System.out.printf("%s está derrotado y no puede atacar%n", atacante.getNombre());
                    } else if (!objetivo.estaVivo()) {
                        System.out.printf("%s ya fue derrotado%n", objetivo.getNombre());
                    } else {
                        atacante.atacar(objetivo);
                        if (!objetivo.estaVivo()) {
                            System.out.printf("¡%s ha sido derrotado!%n", objetivo.getNombre());
                        }
                    }
                    break;

                case 7:
                    if (p1 == null || p2 == null) {
                        System.out.println("Ambos personajes deben estar creados");
                    } else if (!p1.estaVivo() || !p2.estaVivo()) {
                        System.out.println("Ambos personajes deben estar vivos para la batalla");
                    } else {
                        batalla.iniciarPeleaAutomatica(p1, p2);
                    }
                    break;

                case 8:
                    System.out.printf("Total de personajes creados: %d%n",
                            Personaje.getTotalPersonajesCreados());
                    break;

                case 9:
                    System.out.println("¡Gracias por jugar! Hasta pronto.");
                    break;

                default:
                    System.out.println("Opción no válida, intente de nuevo");
                    break;
            }

            System.out.println();

        } while (opcion != 9);

        input.close();
    }
}