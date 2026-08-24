# SlotMachine

Escuela Colombiana de Ingeniería Julio Garavito Desarrollo Orientado por Objetos [DOPO-POOB] Proyecto Inicial — Ciclo No. 1, 2026-2

Descripción

Este proyecto tiene como propósito simular una máquina tragamonedas, inspirada en el Problem I de la maratón de programación internacional 2025 (Slot Machine). La máquina está compuesta por una o más ruedas, cada una con una secuencia de símbolos identificados mediante colores estándar CSS. El simulador permite crear la máquina, administrar sus ruedas y símbolos, girar las ruedas, consultar su configuración y determinar si se alcanzó un estado ganador (jackpot).

En esta entrega no se resuelve el problema de la maratón; únicamente se construye el simulador, respetando el diseño de la clase principal SlotMachine y reutilizando el paquete shapes para la representación gráfica.

Autores
Carlos Jiménez
Alejandro Ospina

Ejecución
1. Abrir el proyecto en BlueJ desde la carpeta SlotMachine.
2. Compilar todas las clases.
3. Crear un objeto SlotMachine desde el menú contextual de la clase.
4. Invocar los métodos públicos disponibles (addWheel, addSymbol, makeVisible, etc.) desde el banco de objetos de BlueJ.


## Diseño

El diseño de clases y los diagramas de secuencia se encuentran documentados en la herramienta Astah (SlotMachine.asta). La clase principal SlotMachine administra una colección de objetos Wheel, y cada Wheel administra una colección de objetos Symbol. La representación gráfica se apoya en las clases del paquete shapes (Circle, Canvas), sin modificar su comportamiento original. La resolución de colores CSS se centraliza en ColorHelper, lo que permite registrar nuevos colores en tiempo de ejecución sin modificar código existente (requisito de extensibilidad).

## ciclos
Por el momento se realizo el ciclo *1* de los entregables del proyecto, proyecto en desarollo se iran agregando mediante se realizen los demás


### Ciclo-1
    Mini-ciclos planificados para ciclo-1

    Los mini-ciclos se definieron incrementalmente, de manera que cada uno dejara el simulador en un estado ejecutable y verificable antes de avanzar al siguiente.

    Mini-ciclo	Qué se construye	Justificación	Estado
    MC1	SlotMachine(), addWheel, delWheel (sin gráficos)	Es la base: sin ruedas no hay nada que hacer	✅ Completado
    MC2	addSymbol, delSymbol, placeSymbol	Las ruedas ya tienen contenido	✅ Completado
    MC3	spin(wheel), spin()	Ya es posible "jugar", aunque sea sin visual	⏳ Pendiente
    MC4	symbols(), distinctSymbols(), configuration(), isJackpot()	Consultas sobre el estado ya construido	🔶 Parcial — configuration() e isJackpot() construidos como soporte de MC5; symbols() y distinctSymbols() pendientes
    MC5	makeVisible(), makeInvisible(), integración de shapes	Ahora sí entra la parte visual	✅ Completado
    MC6	ok(), manejo de errores, JOptionPane (solo si visible)	Requisito de usabilidad 4	✅ Completado
    MC7	exit(), revisión de extensibilidad	Cierre y refactor final	✅ Completado
    Estado actual del proyecto

Al momento de esta entrega, el simulador permite crear la máquina, administrar ruedas y símbolos, ubicar un símbolo visible por color, hacerse visible o invisible con representación gráfica real, mostrar el estado ganador y manejar errores mediante JOptionPane. Quedan pendientes el giro aleatorio de las ruedas (MC3) y las consultas de symbols() y distinctSymbols() (parte de MC4), que se abordarán en la siguiente iteración.
