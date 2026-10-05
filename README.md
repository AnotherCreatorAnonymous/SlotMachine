# SlotMachine

Escuela Colombiana de Ingeniería Julio Garavito
Desarrollo Orientado por Objetos [DOPO-POOB] — Proyecto Inicial 2026-2
Ciclos 1, 2, 3 y 4 (Proyecto Completo)

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

En el ciclo 4 se introduce el soporte completo para **tipos especializados de ruedas y símbolos**,
permitiendo comportamientos heterogéneos y dinámicos (ruedas zurdas, rebeldes y espejo;
símbolos efímeros y tímidos), estructurados mediante ganchos y fábricas extensibles.

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

| Clase / Interfaz | Responsabilidad |
|---|---|
| `SlotMachine` | Fachada del simulador. Administra la colección de ruedas, reordena y controla el protocolo de errores y visibilidad. |
| `Wheel` | Rueda base concreta con ganchos de comportamiento por defecto. Secuencia de símbolos, selección actual, bloqueo y vecindad. |
| `NormalWheel` | Rueda estándar que gira de forma circular y acepta todas las operaciones. |
| `LeftyWheel` | Rueda zurda que copia el símbolo de su vecina izquierda al girar; si no tiene vecina, gira como normal. |
| `RebelWheel` | Rueda rebelde que rechaza bloqueo (`lock`), intercambio (`swap`) y eliminación (`delWheel`). |
| `MirrorWheel` | Tipo propuesto (requisito 19); invierte el sentido de rotación respecto a los pasos solicitados. |
| `WheelFactory` / `WheelMaker` | Fábrica extensible de ruedas basada en registro de creadores funcionales en un mapa dinámico. |
| `Symbol` | Símbolo base concreto con ganchos de ciclo de vida. Par (color, figura `Circle`). |
| `EphemeralSymbol` | Símbolo efímero que se encoge progresivamente en cada giro hasta reducirse a un punto visible mínimo. |
| `ShySymbol` | Símbolo tímido que alterna visibilidad cada vez que queda seleccionado, contando siempre para el jackpot. |
| `SymbolFactory` / `SymbolMaker` | Fábrica extensible de símbolos basada en registro de creadores funcionales en un mapa dinámico. |
| `ColorHelper` | Resuelve nombres de color CSS a objetos `Color` y administra la paleta de n colores. |
| `SlotMachineContest` | Resuelve el problema de la maratón mediante el algoritmo de radar diferencial. |
| `Circle`, `Rectangle`, `Canvas` | Paquete *shapes*, reutilizado sin alterar su comportamiento original. |

## Diseño

El diseño de clases y los diagramas de secuencia están documentados en Astah
(`SlotMachine.asta`).

`SlotMachine` administra una colección de objetos `Wheel`, y cada `Wheel` administra una
colección de objetos `Symbol`. La representación gráfica se apoya en las clases del
paquete *shapes*. La resolución de colores CSS se centraliza en `ColorHelper`, lo que
permite registrar colores nuevos en tiempo de ejecución sin modificar código existente.

Las clases base `Wheel` y `Symbol` son concretas y definen el comportamiento estándar del
sistema. Para soportar nuevos tipos se implementó el patrón de método plantilla (*template method*)
a través de ganchos (*hooks*):
- En `Wheel`: `canLock()`, `canSwap()`, `canDelete()`, `markColor()`, `leftWheel()`, `setLeft()`.
- En `Symbol`: `onSpin()`, `onSelected()`, `isShown()`, `scaled()`.

La instanciación desacoplada se realiza mediante `WheelFactory` y `SymbolFactory`, que utilizan
interfaces funcionales (`WheelMaker`, `SymbolMaker`) para asociar nombres de tipo con sus
respectivos constructores, permitiendo registrar nuevos tipos en caliente sin modificar el código
fuente de las fábricas.

---

## Ciclos

El proyecto se desarrolló en cuatro ciclos de manera incremental, asegurando que cada
mini-ciclo dejara el simulador ejecutable y probado.

### Ciclo 1 — Construcción del simulador

| Mini-ciclo | Qué se construye | Justificación | Estado |
|---|---|---|---|
| MC1 | `SlotMachine()`, `addWheel`, `delWheel` (sin gráficos) | Es la base: sin ruedas no hay nada que hacer | ✅ Completado |
| MC2 | `addSymbol`, `delSymbol`, `placeSymbol` | Las ruedas ya tienen contenido | ✅ Completado |
| MC3 | `spin(wheel)`, `spin()` | Ya es posible jugar, aunque sea sin visual | ✅ Completado (MC8) |
| MC4 | `symbols()`, `distinctSymbols()`, `configuration()`, `isJackpot()` | Consultas sobre el estado ya construido | ✅ Completado (MC8) |
| MC5 | `makeVisible()`, `makeInvisible()`, integración de *shapes* | Entra la parte visual | ✅ Completado |
| MC6 | `ok()`, manejo de errores, `JOptionPane` solo si es visible | Requisito de usabilidad 4 | ✅ Completado |
| MC7 | `exit()`, revisión de extensibilidad | Cierre y refactor final | ✅ Completado |

### Ciclo 2 — Refactoring y extensión

| Mini-ciclo | Qué se construye | Justificación | Estado |
|---|---|---|---|
| MC8 | Cierre de MC3 y MC4: `spin(wheel)`, `spin()`, `symbols()`, `distinctSymbols()` | Saldar deuda del ciclo 1 antes de extender | ✅ Completado |
| MC9 | `swap(wheel1, wheel2)` | Reordenamiento de ruedas en la máquina | ✅ Completado |
| MC10 | `lock(wheel)`, `unlock(wheel)` y la bandera `locked` en `Wheel` | Ruedas fijas que condicionan operaciones | ✅ Completado |
| MC11 | `spin(wheel, steps)` con rotación circular y animación paso a paso | Giro determinista, base del solucionador | ✅ Completado |
| MC12 | `spin(String[] setSymbols)` | Configuración atómica de la máquina | ✅ Completado |

### Ciclo 3 — Solución del problema de la maratón

| Mini-ciclo | Qué se construye | Justificación | Estado |
|---|---|---|---|
| MC13 | `ColorHelper.palette(n)` y `paletteSize()` | Paleta de n colores para máquinas de n símbolos | ✅ Completado |
| MC14 | `SlotMachine(int n)` | Requisito funcional 13 | ✅ Completado |
| MC15 | `distinctSymbols()` pasa a contar los símbolos **visibles** | Sensor fundamental del solucionador | ✅ Completado |
| MC16 | `radar()`: la antena que devuelve el mapa de ocupación | Medición sin costo de movimiento | ✅ Completado |
| MC17 | `collect()`: ubicar una rueda por diferencia de mapas y recogerla | Requisito funcional 14 | ✅ Completado |
| MC18 | `solve(n)`, `simulate(n)`, pruebas de unidad y retrospectiva | Requisito funcional 15 y entregables | ✅ Completado |

### Ciclo 4 — Tipos de ruedas y de símbolos

| Mini-ciclo | Qué se construye | Justificación | Estado |
|---|---|---|---|
| MC19 | Ganchos en `Symbol` y tipos concretos `EphemeralSymbol` y `ShySymbol` | Comportamientos dinámicos de símbolos (requisito 18) | ✅ Completado |
| MC20 | Ganchos en `Wheel` y tipos concretos `LeftyWheel` y `RebelWheel` | Comportamientos especializados y permisos de ruedas (requisito 17) | ✅ Completado |
| MC21 | Fábricas `WheelFactory` y `SymbolFactory` con interfaces `WheelMaker` y `SymbolMaker` | Creación polimórfica y extensibilidad sin modificar código existente | ✅ Completado |
| MC22 | Refactorización de `toIndex` y métodos sobrecargados en `SlotMachine` (`addWheel`, `addSymbol`) | Integración con las fábricas y simplificación de índices | ✅ Completado |
| MC23 | Validación de permisos (`canLock`, `canSwap`, `canDelete`) y enlace de vecindad `relink()` | Respeto a las reglas de cada tipo y soporte para ruedas zurdas | ✅ Completado |
| MC24 | `MirrorWheel` (tipo propuesto), marcas visuales, pruebas `SlotMachineC4Test`/`SlotMachineCC4Test` y documentación | Requisito 19, verificación exhaustiva y cierre de ciclo | ✅ Completado |

#### Tipos disponibles

- **Ruedas:**
  - `normal`: gira de forma circular y acepta todas las operaciones.
  - `lefty`: si tiene vecina a la izquierda, al girar copia su estado/color; si no tiene vecina o no coincide el color, gira como una normal.
  - `rebel`: no permite ser bloqueada (`lock`), intercambiada (`swap`), ni eliminada (`delWheel`).
  - `mirror`: tipo propuesto (requisito 19); invierte el sentido de giro solicitado (los pasos positivos giran en reversa).
- **Símbolos:**
  - `normal`: símbolo estándar, no reacciona a los giros ni a la selección.
  - `ephemeral`: en cada giro de su rueda se desgasta (`onSpin`), encogiéndose hasta alcanzar un tamaño mínimo de punto visible (10% de vida / 4 px).
  - `shy`: alterna entre visible y oculto cada vez que es seleccionado (`onSelected`), manteniéndose en la rueda y contando siempre para el cálculo del jackpot.

#### Cómo se resolvió

- **Clases base concretas y ganchos:** `Wheel` y `Symbol` permanecen como clases concretas con comportamiento por defecto neutro, facilitando la creación directa de elementos estándar sin requerir subclases artificiales (`NormalWheel`/`NormalSymbol`). Las subclases sobreescriben exclusivamente los ganchos relevantes.
- **Fábricas desacopladas:** `WheelFactory` y `SymbolFactory` emplean mapas dinámicos indexados por nombre y basados en interfaces funcionales (`WheelMaker`, `SymbolMaker`), garantizando el principio Abierto/Cerrado (OCP) al permitir registrar tipos nuevos en caliente sin tocar las fábricas.
- **Coordinación en la fachada:** `SlotMachine` mantiene la vecindad entre ruedas mediante `relink()` ante inserciones, intercambios o eliminaciones, unifica la conversión de índices con `toIndex`, y valida los permisos de cada rueda antes de ejecutar operaciones mutables.

#### Corrección del ciclo 3

En `SlotMachineContest.collect`, la guarda contra bucles infinitos `if (here < 0) return before;` se encontraba erróneamente ubicada dentro del bucle `for`, interrumpiendo prematuramente la exploración en el primer paso sin señal. Se reubicó inmediatamente después del cierre del bucle, garantizando que se agoten los `n` sondeos necesarios. El algoritmo ahora alcanza el jackpot con 0 fallos en todas las ejecuciones.

---

## Pruebas

| Clase | Contenido | Pruebas |
|---|---|---|
| `SlotMachineC2Test` | Pruebas de unidad del ciclo 2: `swap`, `lock`/`unlock`, `spin(wheel, steps)`, `spin(String[])` y consultas. | 29 |
| `SlotMachineCC2Test` | Pruebas de unidad compartidas del curso para el ciclo 2 (`accordingJcOm...`). | 4 |
| `SlotMachineContestTest` | Pruebas de unidad del ciclo 3: solucionador, sensor visible, jackpot y límite de acciones. | 7 |
| `SlotMachineContestCTest` | Pruebas de unidad compartidas del curso para el ciclo 3 (`accordingJcOm...`). | 2 |
| `SlotMachineC4Test` | Pruebas de unidad del ciclo 4: creación y rechazo de tipos, comportamiento de ruedas zurdas, rebeldes y espejo, encogimiento de efímeros, alternancia de tímidos y fábricas. | 15 |
| `SlotMachineCC4Test` | Pruebas de unidad compartidas del curso para el ciclo 4 (`accordingJcOm...`). | 4 |

Todas las pruebas se ejecutan con la máquina en modo invisible (61 pruebas en total, 100% pasando).

## Estado actual del proyecto

El simulador está completo y cerrado. Cumple la totalidad de los requisitos funcionales (1 a 19), los requisitos de usabilidad y los criterios de extensibilidad. Los mini-ciclos MC1 a MC24 se encuentran completados y respaldados por pruebas automatizadas.