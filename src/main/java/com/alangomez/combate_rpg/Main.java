package com.alangomez.combate_rpg;

import com.alangomez.combate_rpg.classes.Arquero;
import com.alangomez.combate_rpg.classes.Batalla;
import com.alangomez.combate_rpg.classes.Guerrero;
import com.alangomez.combate_rpg.classes.Mago;
import com.alangomez.combate_rpg.classes.Personaje;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int opcion;

        // POLIMORFISMO POR REFERENCIA: variables de tipo Personaje que pueden
        // guardar un Guerrero, un Mago o un Arquero
        Personaje p1 = null;
        Personaje p2 = null;

        // CLASE ABSTRACTA: si se quita el comentario, NO compila,
        // porque Personaje es abstracta y no se puede instanciar
        // Personaje prueba = new Personaje("Prueba", 100.0, 10.0, 5.0);

        do {
            mostrarMenu();
            opcion = leerEntero(input, "Seleccione una opción: ");

            switch (opcion) {
                case 1: {
                    System.out.println("=== Crear Personaje 1 ===");
                    Personaje creado = crearPersonaje(input);
                    if (creado != null) {
                        p1 = creado;
                    }
                    break;
                }

                case 2: {
                    System.out.println("=== Crear Personaje 2 ===");
                    Personaje creado = crearPersonaje(input);
                    if (creado != null) {
                        p2 = creado;
                    }
                    break;
                }

                case 3:
                    // POLIMORFISMO: sin preguntar el tipo, cada objeto imprime su propia ficha
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

                case 4: {
                    Personaje elegido = seleccionarPersonaje(input, p1, p2,
                            "¿Qué personaje desea subir de nivel? (1 o 2): ");
                    if (elegido != null) {
                        elegido.subirNivel();
                    }
                    break;
                }

                case 5: {
                    Personaje elegido = seleccionarPersonaje(input, p1, p2,
                            "¿Qué personaje desea curar? (1 o 2): ");
                    if (elegido != null) {
                        if (!elegido.estaVivo()) {
                            System.out.printf("%s está derrotado y no puede curarse%n", elegido.getNombre());
                        } else {
                            Batalla.intentarCurar(elegido);
                        }
                    }
                    break;
                }

                case 6:
                    realizarAccion(input, p1, p2, false);
                    break;

                case 7:
                    realizarAccion(input, p1, p2, true);
                    break;

                case 8:
                    if (p1 == null || p2 == null) {
                        System.out.println("Ambos personajes deben estar creados");
                    } else if (!p1.estaVivo() || !p2.estaVivo()) {
                        System.out.println("Ambos personajes deben estar vivos para la batalla");
                    } else {
                        Batalla.iniciarPeleaAutomatica(p1, p2);
                    }
                    break;

                case 9:
                    System.out.printf("Total de personajes creados: %d%n",
                            Personaje.getTotalPersonajesCreados());
                    break;

                case 10:
                    System.out.println("¡Gracias por jugar! Hasta pronto.");
                    break;

                default:
                    System.out.println("Opción no válida, intente de nuevo");
                    break;
            }

            System.out.println();

        } while (opcion != 10);

        input.close();
    }

    private static void mostrarMenu() {
        System.out.println("==========================================");
        System.out.println("        SIMULADOR DE COMBATE RPG");
        System.out.println("==========================================");
        System.out.println("1.  Crear Personaje 1");
        System.out.println("2.  Crear Personaje 2");
        System.out.println("3.  Ver ficha técnica de los personajes");
        System.out.println("4.  Subir de nivel a un personaje");
        System.out.println("5.  Curar a un personaje");
        System.out.println("6.  Realizar un ataque básico");
        System.out.println("7.  Usar habilidad especial");
        System.out.println("8.  Iniciar Batalla Automática (P1 vs P2)");
        System.out.println("9.  Ver total de personajes creados");
        System.out.println("10. Salir");
        System.out.println("==========================================");
    }

    // Pregunta el tipo y el constructor, y devuelve el personaje creado (o null si hubo un error)
    private static Personaje crearPersonaje(Scanner input) {
        System.out.println("Tipo de personaje: 1. Guerrero  2. Mago  3. Arquero");
        int tipo = leerEntero(input, "Seleccione el tipo: ");
        if (tipo < 1 || tipo > 3) {
            System.out.println("Tipo no válido");
            return null;
        }

        System.out.println("Constructor: 1. Predeterminado  2. Parametrizado");
        int constructor = leerEntero(input, "Seleccione el constructor: ");
        if (constructor != 1 && constructor != 2) {
            System.out.println("Opción de constructor no válida");
            return null;
        }

        // POLIMORFISMO POR REFERENCIA: la variable es Personaje, el objeto es de la clase hija
        Personaje nuevo;

        if (constructor == 1) {
            if (tipo == 1) {
                nuevo = new Guerrero();
            } else if (tipo == 2) {
                nuevo = new Mago();
            } else {
                nuevo = new Arquero();
            }
        } else {
            System.out.print("Ingrese el nombre: ");
            String nombre = input.nextLine();
            double vidaMax = leerDouble(input, "Ingrese la vida máxima: ");
            double ataque = leerDouble(input, "Ingrese los puntos de ataque: ");
            double defensa = leerDouble(input, "Ingrese los puntos de defensa: ");

            if (tipo == 1) {
                double escudo = leerDouble(input, "Ingrese los puntos de escudo: ");
                nuevo = new Guerrero(nombre, vidaMax, ataque, defensa, escudo);
            } else if (tipo == 2) {
                double mana = leerDouble(input, "Ingrese los puntos de maná: ");
                nuevo = new Mago(nombre, vidaMax, ataque, defensa, mana);
            } else {
                int precision = leerEntero(input, "Ingrese la precisión (0 a 100): ");
                nuevo = new Arquero(nombre, vidaMax, ataque, defensa, precision);
            }
        }

        System.out.printf("Personaje creado: %s (%s)%n", nuevo.getNombre(), nuevo.getTipo());
        return nuevo;
    }

    // Pregunta 1 o 2 y devuelve el personaje elegido (o null si no es válido o no existe)
    private static Personaje seleccionarPersonaje(Scanner input, Personaje p1, Personaje p2, String mensaje) {
        int seleccion = leerEntero(input, mensaje);
        Personaje elegido;

        if (seleccion == 1) {
            elegido = p1;
        } else if (seleccion == 2) {
            elegido = p2;
        } else {
            System.out.println("Selección no válida");
            return null;
        }

        if (elegido == null) {
            System.out.printf("El Personaje %d no ha sido creado%n", seleccion);
        }
        return elegido;
    }

    // Opciones 6 y 7: ataque básico o habilidad especial
    private static void realizarAccion(Scanner input, Personaje p1, Personaje p2, boolean esHabilidad) {
        if (p1 == null || p2 == null) {
            System.out.println("Ambos personajes deben estar creados");
            return;
        }

        int seleccion = leerEntero(input, "¿Quién ataca? (1 = P1 ataca a P2, 2 = P2 ataca a P1): ");
        Personaje atacante;
        Personaje objetivo;

        if (seleccion == 1) {
            atacante = p1;
            objetivo = p2;
        } else if (seleccion == 2) {
            atacante = p2;
            objetivo = p1;
        } else {
            System.out.println("Selección no válida");
            return;
        }

        if (!atacante.estaVivo()) {
            System.out.printf("%s está derrotado y no puede atacar%n", atacante.getNombre());
            return;
        }
        if (!objetivo.estaVivo()) {
            System.out.printf("%s ya fue derrotado%n", objetivo.getNombre());
            return;
        }

        // POLIMORFISMO: se ejecuta el método de la clase real del atacante
        if (esHabilidad) {
            atacante.habilidadEspecial(objetivo);
        } else {
            atacante.atacar(objetivo);
        }

        if (!objetivo.estaVivo()) {
            System.out.printf("¡%s ha sido derrotado!%n", objetivo.getNombre());
        }
    }

    // Lee un número entero; si el usuario escribe letras, vuelve a preguntar
    private static int leerEntero(Scanner input, String mensaje) {
        System.out.print(mensaje);
        while (!input.hasNextInt()) {
            input.nextLine();
            System.out.print("Debe ingresar un número entero. " + mensaje);
        }
        int valor = input.nextInt();
        input.nextLine();
        return valor;
    }

    // Lee un número decimal; si el usuario escribe letras, vuelve a preguntar
    private static double leerDouble(Scanner input, String mensaje) {
        System.out.print(mensaje);
        while (!input.hasNextDouble()) {
            input.nextLine();
            System.out.print("Debe ingresar un número. " + mensaje);
        }
        double valor = input.nextDouble();
        input.nextLine();
        return valor;
    }
}