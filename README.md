# SlotMachine

Escuela Colombiana de Ingeniería Julio Garavito
Desarrollo Orientado por Objetos [DOPO-POOB] — Proyecto Inicial 2026-2
Ciclos 1, 2 y 3 

## Descripción

Este proyecto simula una máquina tragamonedas inspirada en el Problem I de la maratón
de programación internacional 2025 (Slot Machine). La máquina está compuesta por una o
más ruedas, cada una con una secuencia de símbolos identificados mediante colores
estándar CSS.

El simulador permite crear la máquina, administrar sus ruedas y símbolos, intercambiar
y bloquear ruedas, girarlas de forma aleatoria o por un número exacto de pasos, dejarlas
en una configuración dada, consultar su estado y determinar si se alcanzó el jackpot.

A partir del ciclo 3 el proyecto también **resuelve** el problema de la maratón: la clase
`SlotMachineContest` encuentra la secuencia de giros que lleva al jackpot en una máquina
de n ruedas y n símbolos inicializada aleatoriamente, usando como única información el
número de símbolos distintos que la máquina está mostrando.

## Autores

- Carlos Jiménez
- Alejandro Ospina

## Ejecución

1. Abrir el proyecto en BlueJ desde la carpeta `SlotMachine`.
2. Compilar todas las clases.
3. Para usar el simulador: crear un objeto `SlotMachine` desde el menú contextual de la
   clase e invocar sus métodos públicos desde el banco de objetos.
4. Para resolver la maratón: invocar `SlotMachineContest.solve(n)` (retorna las acciones,
   máquina invisible) o `SlotMachineContest.simulate(n)` (muestra la solución paso a paso).

> Nota: `simulate(n)` anima cada paso con un retardo de 200 ms. Para una demostración
> conviene usar n ≤ 5 o reducir la constante `STEP_DELAY` de `SlotMachine`.

## Estructura del proyecto

| Clase | Responsabilidad |
|---|---|
| `SlotMachine` | Fachada del simulador. Administra la colección de ruedas y todo el protocolo de errores. |
| `Wheel` | Secuencia de símbolos de una rueda, cuál está visible y si la rueda está bloqueada. |
| `Symbol` | Par (color, figura). Adapta `Circle` para poder identificar un símbolo por su color. |
| `ColorHelper` | Resuelve nombres de color CSS a objetos `Color` y reparte la paleta de n colores. |
| `SlotMachineContest` | Resuelve el problema de la maratón. `solve(n)` y `simulate(n)`. |
| `Circle`, `Rectangle`, `Canvas` | Paquete *shapes*, reutilizado sin alterar su comportamiento original. |

## Diseño

El diseño de clases y los diagramas de secuencia están documentados en Astah
(`SlotMachine.asta`).

`SlotMachine`