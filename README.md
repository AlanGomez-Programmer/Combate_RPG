# ⚔️ Simulador de Combate RPG en Java

Simulador de combate por turnos que se ejecuta en consola. El usuario crea dos personajes, consulta sus estadísticas, los cura, los sube de nivel y los enfrenta en ataques individuales o en una batalla automática, todo desde un menú interactivo.

El proyecto fue desarrollado como taller práctico de **Programación Orientada a Objetos**, aplicando clases, objetos, atributos, métodos, constructores y miembros estáticos, sin utilizar arreglos, listas, herencia ni polimorfismo.

---

## 📁 Estructura del proyecto

```
com.alangomez.combate_rpg
├── Main.java                 → Punto de entrada con el menú interactivo
└── classes
    ├── Personaje.java        → Representa a un personaje y sus acciones
    └── Batalla.java          → Métodos estáticos de utilidad para el combate
```

Las clases del modelo se agruparon en el package `classes` para separar la lógica del juego del programa principal.

---

## 🧩 Descripción de las clases

### `Personaje.java`

Representa a un combatiente con sus estadísticas y las acciones que puede realizar.

**Atributos de instancia:** `nombre`, `puntosVida`, `puntosVidaMax`, `puntosAtaque`, `puntosDefensa` y `nivel`.

**Atributos estáticos:**

| Atributo | Función |
|---|---|
| `totalPersonajesCreados` | Cuenta todos los personajes creados en el sistema. |
| `totalPersonajesPredeterminados` | Numera los personajes sin nombre ("Guerrero Novato", "Guerrero Novato 2", ...). |

**Constructores:**

| Constructor | Comportamiento |
|---|---|
| `Personaje()` | Crea un "Guerrero Novato" con 100 de vida, 15 de ataque, 5 de defensa y nivel 1. |
| `Personaje(nombre, vidaMax, ataque, defensa)` | Crea un personaje con los valores del usuario, validando cada uno. |

**Métodos:**

| Método | Descripción |
|---|---|
| `atacar(Personaje objetivo)` | Calcula `ataque − defensa del objetivo`. Si el resultado es 0 o menor, aplica un daño mínimo de 3.0. |
| `recibirDano(double cantidad)` | Resta vida; si baja de 0, la ajusta a 0.0. |
| `curar()` | Recupera 25 puntos de vida sin superar la vida máxima e informa cuánto se recuperó realmente. |
| `estaVivo()` | Devuelve `true` si la vida es mayor que 0. |
| `subirNivel()` | Sube 1 nivel, +20 vida máxima, +5 ataque, +2 defensa y restaura la vida completa. |
| `mostrarEstado()` | Imprime la ficha técnica del personaje. |
| `getTotalPersonajesCreados()` | Método estático que devuelve el total de personajes creados. |

### `Batalla.java`

Clase de utilidad con métodos **estáticos**, por lo que se usa sin crear objetos (`Batalla.iniciarPeleaAutomatica(p1, p2)`).

| Método | Descripción |
|---|---|
| `ejecutarAtaqueCritico(atacante, objetivo, multiplicador)` | Multiplica el ataque y usa casting `(int)` para redondear el daño hacia abajo. Ejemplo: 15 × 1.5 = 22.5 → 22. |
| `iniciarPeleaAutomatica(p1, p2)` | Ciclo `while` que alterna ataques por rondas mientras ambos estén vivos y al final anuncia al ganador. |

### `Main.java`

Contiene el menú interactivo dentro de un ciclo `do-while`, controlado con `Scanner` y un `switch`. Los personajes se guardan en dos variables de referencia (`p1` y `p2`) inicializadas en `null`.

---

## 🎮 Menú del programa

```
==========================================
        SIMULADOR DE COMBATE RPG
==========================================
1. Crear Personaje 1 (Constructor Predeterminado)
2. Crear Personaje 2 (Constructor Parametrizado)
3. Ver ficha técnica de los personajes
4. Subir de nivel a un personaje
5. Curar a un personaje
6. Realizar un ataque individual
7. Iniciar Batalla Automática (P1 vs P2)
8. Ver total de personajes creados en el sistema
9. Salir
==========================================
Seleccione una opción:
```

---

## 🛠️ Conceptos aplicados

**Clases y objetos.** `Personaje` funciona como plantilla y cada `new Personaje()` crea un objeto independiente con sus propios valores.

**Encapsulamiento.** Todos los atributos son `private` y se accede a ellos mediante *getters* y *setters*. Los setters validan que no se asignen valores menores o iguales a 0.

**Constructores.** Se implementaron dos: uno predeterminado y uno parametrizado, cada uno con su propia forma de inicializar el objeto.

**La palabra `this`.** Se usa para diferenciar los atributos del objeto de los parámetros con el mismo nombre (`this.puntosAtaque = puntosAtaque`).

**Objetos como parámetros.** `atacar(Personaje objetivo)` recibe el objeto completo, lo que permite leer su defensa y modificar su vida.

**Miembros estáticos.** Los contadores pertenecen a la clase y no a cada objeto, por eso se comparten entre todos los personajes. `Batalla` usa métodos estáticos porque no necesita guardar estado propio.

**Casting explícito.** `(int) (ataque * multiplicador)` convierte el resultado a entero después de multiplicar.

**Operador ternario.** Se usa para decisiones cortas, como elegir el ganador o generar el número del nombre por defecto.

**Estructuras de control.** `do-while` para el menú, `while` para la batalla automática, `switch` para las opciones e `if/else` para las validaciones.

---

## ✅ Validaciones implementadas

**Nombre vacío o nulo.** Si el usuario no escribe un nombre (o solo escribe espacios), se asigna automáticamente "Guerrero Novato" con un número consecutivo.

**Valores inválidos.** Si la vida, el ataque o la defensa son menores o iguales a 0, se muestra un aviso y se asigna el valor por defecto (100.0, 15.0 y 5.0). Cada valor se valida de forma independiente.

**Límites de vida.** La vida nunca baja de 0 al recibir daño ni supera el máximo al curarse.

**Personajes no creados.** Antes de usar `p1` o `p2`, el menú verifica que no sean `null` para evitar errores (`NullPointerException`).

**Personajes derrotados.** Un personaje sin vida no puede atacar ni ser atacado, y la batalla automática solo inicia si ambos están vivos.

---

## 🖨️ Formato de las salidas

Las salidas en consola se formatearon con `System.out.printf` para que la información sea clara y el flujo del juego se entienda fácilmente.

| Especificador | Uso |
|---|---|
| `%s` | Textos, como el nombre del personaje. |
| `%d` | Enteros, como el nivel, la ronda o el total de personajes. |
| `%.2f` | Decimales con 2 cifras, como vida, ataque, defensa y daño. |
| `%n` | Salto de línea, para que cada mensaje aparezca en su propia línea. |

Además, se aplicaron estas decisiones para mejorar la lectura:

- Separadores visuales (`=====`, `-----`) para distinguir el menú, las fichas y cada sección.
- La vida se muestra como `actual / máxima` para ver de un vistazo el estado del personaje.
- Cada acción describe quién la realizó, sobre quién y con qué resultado.
- La batalla automática se divide en rondas numeradas.
- Se deja una línea en blanco después de cada opción antes de volver a mostrar el menú.

**Ejemplo de ficha técnica:**

```
-> Nombre: Alan
-> Nivel: 1
-> Vida: 100.00 / 100.00
-> Ataque: 20.00
-> Defensa: 15.00
```

**Ejemplo de batalla automática:**

```
Round 1
Guerrero Novato atacó a Alan y le provocó 3.00 de daño
Alan atacó a Guerrero Novato y le provocó 15.00 de daño
Round 2
...
El ganador es: Alan
```

> **Nota:** según la configuración regional del equipo, los decimales pueden mostrarse con coma (`100,00`) en lugar de punto.

---

## ▶️ Cómo ejecutar

1. Clonar el repositorio.
2. Abrir el proyecto en NetBeans (o cualquier IDE para Java).
3. Ejecutar la clase `Main.java`.
4. Seguir las opciones del menú en la consola.

**Requisito:** Java 11 o superior (se usa el método `isBlank()`).

---

## Actualizaciones

--- 

## 👨 AUTOR

Programador Full-Stack Jr. Alan Gomez

GitHub: [AlanGomez-Programmer](https://github.com/AlanGomez-Programmer)

LinkedIn: alan-gomez-763163320