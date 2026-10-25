# SlotMachine

Escuela Colombiana de Ingeniería Julio Garavito
Desarrollo Orientado por Objetos [DOPO-POOB] — Proyecto Inicial 2026-2
Ciclos 1 a 5 (Proyecto Completo)

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

En el ciclo 5 el proyecto pasa de BlueJ a **VS Code con Maven** y se mide su calidad:
**análisis dinámico** con JaCoCo (cubrimiento de pruebas, meta > 75 %) y **análisis estático**
con PMD (meta: 0 violaciones de prioridad 1). Además se completa el seguimiento de la rueda zurda.

## Autores

- Carlos Jiménez
- Alejandro Ospina

## Ejecución

Requisitos: JDK 17 o superior y Maven 3.9 (en VS Code, la extensión *Extension Pack for Java*).

1. Abrir la carpeta raíz del repositorio en VS Code (donde está `pom.xml`).
2. Compilar y correr las pruebas, con el informe de cubrimiento:

   ```bash
   mvn clean test
   ```

   El informe de JaCoCo queda en `target/site/jacoco/index.html`.
3. Generar el informe de análisis estático:

   ```bash
   mvn pmd:pmd
   ```

   El informe de PMD queda en `target/site/pmd.html`.
4. Verificar las dos metas del ciclo 5 de una vez:

   ```bash
   mvn clean verify
   ```

   Falla si el cubrimiento de instrucciones baja de 75 % o si queda alguna violación de PMD de prioridad 1.
5. Para usar el simulador: crear un objeto `SlotMachine` (por ejemplo desde una clase con `main`
   o desde *jshell*) e invocar sus métodos públicos.
6. Para resolver la maratón: invocar `SlotMachineContest.solve(n)` (retorna las acciones,
   máquina invisible) o `SlotMachineContest.simulate(n)` (muestra la solución paso a paso).

> Nota: `SlotMachineVisibleTest` abre la ventana del simulador unos segundos; es normal.
> En un equipo sin pantalla esa prueba se omite.

> Nota: `simulate(n)` anima cada paso con un retardo de 200 ms. Para una demostración
> conviene usar n ≤ 5 o reducir la constante `STEP_DELAY` de `SlotMachine`.

## Estructura del proyecto

| Clase / Interfaz | Responsabilidad |
|---|---|
| `SlotMachine` | Fachada del simulador. Administra la colección de ruedas, reordena y controla el protocolo de errores y visibilidad. |
| `Wheel` | Rueda base concreta (tipo `normal`) con ganchos de comportamiento por defecto. Secuencia de símbolos, selección actual, bloqueo y vecindad. Gira de forma circular y acepta todas las operaciones. |
| `LeftyWheel` | Rueda zurda que copia el símbolo de su vecina izquierda al girar y la sigue cada vez que la vecina cambia; si no tiene vecina, gira como normal. |
| `RebelWheel` | Rueda rebelde que rechaza bloqueo (`lock`), intercambio (`swap`) y eliminación (`delWheel`). |
| `MirrorWheel` | Tipo propuesto (requisito 19); invierte el sentido de rotación respecto a los pasos solicitados. |
| `WheelFactory` / `WheelMaker` | Fábrica extensible de ruedas basada en registro de creadores funcionales en un mapa dinámico. |
| `Symbol` | Símbolo base concreto con ganchos de ciclo de vida. Par (color, figura `Circle`). |
| `EphemeralSymbol` | Símbolo efímero que se encoge progresivamente en cada giro hasta reducirse a un punto visible mínimo. |
| `ShySymbol` | Símbolo tímido que alterna visibilidad cada vez que queda seleccionado, contando siempre para el jackpot. |
| `SymbolFactory` / `SymbolMaker` | Fábrica extensible de símbolos basada en registro de creadores funcionales en un mapa dinámico. |
| `ColorHelper` | Resuelve nombres de color CSS a objetos `Color` y administra la paleta de n colores. |
| `SlotMachineContest` | Resuelve el problema de la maratón mediante el algoritmo de radar diferencial. |
| `Circle`, `Rectangle`, `Canvas` | Paquete *shapes*, reutilizado sin alterar su comportamiento original (en el ciclo 5 solo se ajustaron detalles señalados por PMD). |

Estructura de carpetas (Maven):

```
pom.xml
src/main/java/   clases del simulador
src/test/java/   pruebas de unidad (JUnit 4)
informes/        informes de JaCoCo y PMD del ciclo 5 (inicial y final)
SlotMachine.asta diseño en Astah
```

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
- En `Wheel`: `canLock()`, `canSwap()`, `canDelete()`, `markColor()`, `leftWheel()`, `setLeft()`, `sync()`.
- En `Symbol`: `onSpin()`, `onSelected()`, `isShown()`, `scaled()`.

La instanciación desacoplada se realiza mediante `WheelFactory` y `SymbolFactory`, que utilizan
interfaces funcionales (`WheelMaker`, `SymbolMaker`) para asociar nombres de tipo con sus
respectivos constructores, permitiendo registrar nuevos tipos en caliente sin modificar el código
fuente de las fábricas.

---

## Ciclos

El proyecto se desarrolló en cinco ciclos de manera incremental, asegurando que cada
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
  - `lefty`: si tiene vecina a la izquierda, al girar copia su estado/color; si no tiene vecina o no coincide el color, gira como una normal. Además sigue a su vecina: cuando la vecina cambia de símbolo (`spin`, `placeSymbol`, `delSymbol`, `addSymbol` sobre una rueda vacía) o cuando la zurda queda junto a otra vecina (`swap`, `addWheel`, `delWheel`), se reajusta, y el cambio se propaga por una cadena de zurdas. Un símbolo puesto a mano en la zurda (`placeSymbol`, `spin(String[])`) se respeta hasta que su vecina vuelva a cambiar, y una zurda bloqueada no se mueve.
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

### Ciclo 5 — Calidad: migración, análisis dinámico y estático

| Mini-ciclo | Qué se construye | Justificación | Estado |
|---|---|---|---|
| MC25 | Migración de BlueJ a VS Code con Maven (`pom.xml`, `src/main/java`, `src/test/java`) y medición inicial | Herramientas de análisis automatizadas; el informe pide resultado inicial y final | ✅ Completado |
| MC26 | Eliminación del código muerto (`Figure`, `CircleFigure`, `SquareFigure`, `TriangleFigure`, `Triangle`) | Decisión pendiente del ciclo 4; clases sin uso que bajaban el cubrimiento | ✅ Completado |
| MC27 | Seguimiento completo de `LeftyWheel` (`sync()` y `follow()`) con 8 pruebas | La rueda zurda no reaccionaba cuando su vecina cambiaba | ✅ Completado |
| MC28 | Corrección de las violaciones de PMD de prioridad 1 y 2 | Meta de análisis estático | ✅ Completado |
| MC29 | Pruebas `SlotMachineC1Test`, `SlotMachineC3Test` y `SlotMachineVisibleTest` | Meta de análisis dinámico; faltaban las pruebas de los ciclos 1 y 3 y del modo visible | ✅ Completado |
| MC30 | Medición final, informes, README y retrospectiva | Entregables del ciclo | ✅ Completado |

#### Resultados

| Medida | Inicial | Final | Meta |
|---|---|---|---|
| Pruebas | 61 | 101 | — |
| JaCoCo (instrucciones) | 61.4 % | 92.2 % | > 75 % ✅ |
| PMD prioridad 1 | 6 | 0 | 0 ✅ |
| PMD prioridad 2 | 6 | 0 | — |
| PMD total | 550 | 490 (solo prioridades 3 y 4) | — |

Los informes completos están en `informes/inicial/` e `informes/final/`.

#### Análisis dinámico (JaCoCo)

- **Inicial, 61.4 %.** Las causas: las clases `Figure` y sus tres implementaciones y `Triangle`
  no las usaba nadie (0 %), ninguna prueba usaba el modo visible y faltaban las pruebas de los ciclos 1 y 3.
- **Decisiones:**
  - Se eliminó el código muerto. Solo con esto el cubrimiento subió a 70.4 %.
  - Se completaron las pruebas de los ciclos 1 y 3 y las de la rueda zurda.
  - Se agregó una prueba del modo visible que solo usa operaciones válidas, para que no aparezcan
    ventanas de `JOptionPane`.
- **Final, 92.2 %.** Sin pantalla (la prueba visible se omite) el cubrimiento es 74.1 %.

#### Análisis estático (PMD)

- **Inicial:** 6 violaciones de prioridad 1, que son 4 de `ConstructorCallsOverridableMethod`
  y 2 de `ClassWithOnlyPrivateConstructorsShouldBeFinal`, y 6 de prioridad 2 (`AvoidReassigningParameters`).
- **Decisiones:**
  - `SlotMachine` es `final`: su constructor `SlotMachine(int n)` usa métodos públicos que nadie debe sobrescribir.
  - `Wheel` ya no llama al gancho `markColor()` desde el constructor. El color de la marca se aplica en `showAt(...)`,
    cuando la subclase ya está construida.
  - `Canvas` y su clase interna `CanvasPane` son `final`, porque solo tienen constructores privados.
  - En `Circle` y `Rectangle`, `slowMoveHorizontal` y `slowMoveVertical` ya no reasignan el parámetro `distance`.
  - Las violaciones de prioridad 3 y 4 (`MethodArgumentCouldBeFinal`, `LocalVariableCouldBeFinal`,
    `CommentRequired`, `ShortVariable`, `OnlyOneReturn`, `NoPackage`…) se dejaron documentadas.
    Son de estilo y no hacen parte de la meta.
- **Final:** 0 violaciones de prioridad 1 y 0 de prioridad 2.

#### Seguimiento de la rueda zurda

Cuando una rueda cambia de símbolo o queda junto a otra vecina, `SlotMachine.follow(from)` le pide a las
ruedas de su derecha que se reajusten (`Wheel.sync()`). La cadena se detiene en la primera que no cambia.
Solo `LeftyWheel` redefine `sync()`. Copia el color de su vecina, salvo que esté bloqueada o que ya
muestre ese color (así un `ShySymbol` no cambia de visibilidad sin motivo).

---

## Pruebas

| Clase | Contenido | Pruebas |
|---|---|---|
| `SlotMachineC1Test` | Pruebas de unidad del ciclo 1: creación y eliminación de ruedas y símbolos, posiciones fuera de rango, giros, consultas, jackpot, `ok()` y `exit()`. | 21 |
| `SlotMachineC2Test` | Pruebas de unidad del ciclo 2: `swap`, `lock`/`unlock`, `spin(wheel, steps)`, `spin(String[])` y consultas. | 29 |
| `SlotMachineCC2Test` | Pruebas de unidad compartidas del curso para el ciclo 2 (`accordingJcOm...`). | 4 |
| `SlotMachineC3Test` | Pruebas de unidad del ciclo 3: paleta de colores, registro de colores en tiempo de ejecución, máquina de n ruedas y casos límite del solucionador. | 9 |
| `SlotMachineContestTest` | Pruebas de unidad del ciclo 3: solucionador, sensor visible, jackpot y límite de acciones. | 7 |
| `SlotMachineContestCTest` | Pruebas de unidad compartidas del curso para el ciclo 3 (`accordingJcOm...`). | 2 |
| `SlotMachineC4Test` | Pruebas de unidad del ciclo 4: creación y rechazo de tipos, comportamiento de ruedas zurdas (incluido el seguimiento de su vecina), rebeldes y espejo, encogimiento de efímeros, alternancia de tímidos y fábricas. | 23 |
| `SlotMachineCC4Test` | Pruebas de unidad compartidas del curso para el ciclo 4 (`accordingJcOm...`). | 4 |
| `SlotMachineVisibleTest` | Pruebas del modo visible: juego con todos los tipos y solución de la maratón mostrando cada paso. Solo operaciones válidas; se omiten sin pantalla. | 2 |

Hay 101 pruebas en total y todas pasan. Todas se ejecutan en modo invisible, menos `SlotMachineVisibleTest`.

## Estado actual del proyecto

El simulador está completo y cerrado. Cumple la totalidad de los requisitos funcionales (1 a 19), los requisitos de usabilidad y los criterios de extensibilidad. Los mini-ciclos MC1 a MC30 se encuentran completados y respaldados por pruebas automatizadas, con un cubrimiento de 92.2 % y sin violaciones de PMD de prioridad 1 ni 2.