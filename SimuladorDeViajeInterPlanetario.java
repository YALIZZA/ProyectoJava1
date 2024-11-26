import java.util.Scanner;
import java.util.Random;

public class SimuladorDeViajeInterPlanetario {

    // Elementos necesarios para todas las acciones
    static Scanner scanner = new Scanner(System.in);
    static String[] planetas = {"Marte", "Júpiter", "Saturno"};
    static double[] distancias = {225.0, 778.0, 1429.0}; // en millones de km
    static String[] naves = {"Exploradora", "Carga pesada", "Velocidad máxima"};
    static double[] velocidades = {20_000.0, 15_000.0, 30_000.0}; // en km/h
    static int planetaSeleccionado = -1; // Índice del planeta seleccionado
    static int naveSeleccionada = -1; // Índice de la nave seleccionada

    public static void main(String[] args) throws Exception {
        int opcion;
        do {
            mostrarMenu();
            opcion = scanner.nextInt();
            switch (opcion) {
                case 1 -> seleccionarPlaneta();
                case 2 -> seleccionarNave();
                case 3 -> iniciarSimulacion();
                case 4 -> System.out.println("¡Gracias por usar el simulador! Saliendo del programa...");
                default -> System.out.println("Opción no válida. Intenta de nuevo.");
            }
        } while (opcion != 4);
    }

    // Menú principal
    public static void mostrarMenu() {
        System.out.println("\n--- Menú Principal ---");
        System.out.println("1. Seleccionar un planeta de destino");
        System.out.println("2. Seleccionar una nave espacial");
        System.out.println("3. Iniciar la simulación del vuelo");
        System.out.println("4. Salir");
        System.out.print("Por favor, elige una opción: ");
    }

    // Selección de planeta
    public static void seleccionarPlaneta() {
        System.out.println("\n--- Selección de Planeta ---");
        imprimirPlanetas();
        System.out.print("Selecciona el número del planeta: ");
        int seleccion = scanner.nextInt();
        if (seleccion > 0 && seleccion <= planetas.length) {
            planetaSeleccionado = seleccion - 1;
            System.out.println("Has seleccionado: " + planetas[planetaSeleccionado] +
                               " (Distancia: " + distancias[planetaSeleccionado] + " millones de km)");
        } else {
            System.out.println("Selección no válida. Intenta de nuevo.");
        }
    }

    // Selección de nave
    public static void seleccionarNave() {
        System.out.println("\n--- Selección de Nave ---");
        imprimirNaves();
        System.out.print("Selecciona el número de la nave: ");
        int seleccion = scanner.nextInt();
        if (seleccion > 0 && seleccion <= naves.length) {
            naveSeleccionada = seleccion - 1;
            System.out.println("Has seleccionado: " + naves[naveSeleccionada] +
                               " (Velocidad: " + velocidades[naveSeleccionada] + " km/h)");
        } else {
            System.out.println("Selección no válida. Intenta de nuevo.");
        }
    }

    // Iniciar simulación
    public static void iniciarSimulacion() {
        if (planetaSeleccionado == -1 || naveSeleccionada == -1) {
            System.out.println("Debes seleccionar un planeta y una nave antes de iniciar la simulación.");
            return;
        }

        System.out.println("\n--- Simulación de Vuelo ---");
        double distancia = distancias[planetaSeleccionado] * 1_000_000; // Convertir a km
        double velocidad = velocidades[naveSeleccionada];
        double tiempo = calcularTiempo(distancia, velocidad);
        System.out.printf("Viajando a %s a una velocidad de %.0f km/h...\n", planetas[planetaSeleccionado], velocidad);
        System.out.printf("Distancia total: %.0f km\n", distancia);
        System.out.printf("Tiempo estimado de viaje: %.2f horas\n", tiempo);

        // Simulación del progreso del viaje
        for (int progreso = 0; progreso <= 100; progreso += 20) {
            System.out.println("Progreso del viaje: " + progreso + "% completado.");
            lanzarEvento(); // Simular eventos aleatorios
            try {
                Thread.sleep(1000); // Pausa de 1 segundo
            } catch (InterruptedException e) {
                System.out.println("Simulación interrumpida.");
            }
        }
        System.out.println("¡Has llegado a " + planetas[planetaSeleccionado] + " con éxito!");
    }

    // Métodos auxiliares
    public static void imprimirPlanetas() {
        for (int i = 0; i < planetas.length; i++) {
            System.out.println((i + 1) + ". " + planetas[i] + " (Distancia: " + distancias[i] + " millones de km)");
        }
    }

    public static void imprimirNaves() {
        for (int i = 0; i < naves.length; i++) {
            System.out.println((i + 1) + ". " + naves[i] + " (Velocidad: " + velocidades[i] + " km/h)");
        }
    }

    public static double calcularTiempo(double distancia, double velocidad) {
        return distancia / velocidad;
    }

    public static void lanzarEvento() {
        Random random = new Random();
        int evento = random.nextInt(10); // 10% de probabilidad de evento
        if (evento < 3) { // Evento ocurre si el número está entre 0 y 2
            System.out.println("¡Un evento inesperado ha ocurrido durante el viaje!");
            switch (evento) {
                case 0 -> System.out.println("Falla en el sistema. Reparaciones en curso...");
                case 1 -> System.out.println("Asteroides detectados. Cambiando de rumbo...");
                case 2 -> System.out.println("Reabastecimiento de oxígeno en curso...");
            }
        } else {
            System.out.println("El viaje continúa sin problemas.");
        }
    }
}
