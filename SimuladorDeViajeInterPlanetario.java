import java.util.Random;
import java.util.Scanner;

public class SimuladorDeViajeInterPlanetario {

    // Elementos necesarios para todas las acciones
    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();

    static String[] planetas = {"Mercurio", "Venus", "Marte", "Júpiter", "Saturno", "Urano", "Neptuno"};
    static double[] distancias = {91.7, 42.4, 78.3, 628.9, 1284.4, 2721.4, 4345.4}; // en millones de km
    static String[] naves = {"Exploradora", "Carga pesada", "Velocidad máxima"};
    static double[] velocidades = {20_000.0, 15_000.0, 30_000.0}; // en km/h
    static int[] capacidadNaves = {10, 5, 15}; // Capacidad máxima de tripulantes de cada nave
    static int planetaSeleccionado = -1; // Índice del planeta seleccionado
    static int naveSeleccionada = -1; // Índice de la nave seleccionada
    static double combustibleDisponible = 10_000; // en litros
    static double oxigenoDisponible = 500; // en unidades arbitrarias
    static int cantidadPasajeros = 0;

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            opcion = scanner.nextInt();
            switch (opcion) {
                case 1 -> seleccionarPlaneta();
                case 2 -> seleccionarNave();
                case 3 -> gestionarRecursos();
                case 4 -> iniciarSimulacion();
                case 5 -> System.out.println("¡Gracias por usar el simulador! Saliendo del programa...");
                default -> System.out.println("Opción no válida. Intenta de nuevo.");
            }
        } while (opcion != 5);
    }

    // Menú principal
    public static void mostrarMenu() {
        System.out.println("\n--- Menú Principal ---");
        System.out.println("1. Seleccionar un planeta de destino");
        System.out.println("2. Seleccionar una nave espacial");
        System.out.println("3. Gestionar recursos de la nave");
        System.out.println("4. Iniciar la simulación del vuelo");
        System.out.println("5. Salir");
        System.out.print("Por favor, elige una opción: ");
    }

    // Selección de planeta
    public static void seleccionarPlaneta() {
        System.out.println("\n--- Selección de Planeta ---");
        for (int i = 0; i < planetas.length; i++) {
            System.out.println((i + 1) + ". " + planetas[i] + " (Distancia: " + distancias[i] + " millones de km)");
        }
        System.out.print("Selecciona el número del planeta: ");
        int seleccion = scanner.nextInt();
        if (seleccion > 0 && seleccion <= planetas.length) {
            planetaSeleccionado = seleccion - 1;
            System.out.println("Has seleccionado: " + planetas[planetaSeleccionado]);
            mostrarDescripcionPlaneta(planetaSeleccionado);
        } else {
            System.out.println("Selección no válida. Intenta de nuevo.");
        }
    }

    // Mostrar descripción del planeta
    public static void mostrarDescripcionPlaneta(int indice) {
        System.out.println("Descripción del planeta:");
        switch (indice) {
            case 0 -> System.out.println("Mercurio es el planeta más pequeño del sistema solar, su superficie está plagada de cráteres y viaja más rápido a través del espacio.");
            case 1 -> System.out.println("Venus es el objeto más brillante que se puede ver en el cielo desde la Tierra y es el planeta más caliente del sistema solar.");
            case 2 -> System.out.println("Marte, conocido como el planeta rojo, es seco, rocoso, inhóspito y frío.");
            case 3 -> System.out.println("Júpiter tiene el doble de la masa que el resto de los planetas juntos. Su día dura solo 10 horas y tiene 79 lunas.");
            case 4 -> System.out.println("Saturno es famoso por sus siete anillos compuestos de hielo y rocas. Tiene 53 lunas confirmadas y 3 por confirmar.");
            case 5 -> System.out.println("Urano está compuesto por un fluido de agua, amoníaco y metano. Está rodeado por 13 anillos tenues y tiene 27 satélites conocidos.");
            case 6 -> System.out.println("Neptuno no es visible desde la Tierra a simple vista. Tiene 14 lunas conocidas y cinco anillos principales.");
            default -> System.out.println("Descripción no disponible.");
        }
    }

    // Selección de nave
    public static void seleccionarNave() {
        System.out.println("\n--- Selección de Nave ---");
        for (int i = 0; i < naves.length; i++) {
            System.out.println((i + 1) + ". " + naves[i] + " (Velocidad: " + velocidades[i] + " km/h, Capacidad máxima: " + capacidadNaves[i] + " tripulantes)");
        }
        System.out.print("Selecciona el número de la nave: ");
        int seleccion = scanner.nextInt();
        if (seleccion > 0 && seleccion <= naves.length) {
            naveSeleccionada = seleccion - 1;
            System.out.println("Has seleccionado: " + naves[naveSeleccionada]);
            // Solicitar la cantidad de pasajeros
            cantidadPasajeros = seleccionarCantidadPasajeros(capacidadNaves[naveSeleccionada]);
        } else {
            System.out.println("Selección no válida. Intenta de nuevo.");
        }
    }

    // Solicitar la cantidad de pasajeros
    public static int seleccionarCantidadPasajeros(int capacidadMaxima) {
        int pasajeros = 0;
        do {
            System.out.print("¿Cuántos pasajeros llevarás? (Debe ser un número positivo): ");
            pasajeros = scanner.nextInt();
            if (pasajeros <= 0) {
                System.out.println("La cantidad de pasajeros debe ser un número positivo. Intenta de nuevo.");
            }
        } while (pasajeros <= 0); // Solo se permite un número de pasajeros positivo
        return pasajeros;
    }

    // Gestión de recursos
    public static void gestionarRecursos() {
        if (planetaSeleccionado == -1 || naveSeleccionada == -1) {
            System.out.println("Debes seleccionar un planeta y una nave primero.");
            return;
        }
        double distancia = distancias[planetaSeleccionado] * 1_000_000; // Convertir a km
        double combustibleNecesario = calcularCombustible(distancia);
        double oxigenoNecesario = calcularOxigeno(distancia);

        System.out.printf("Combustible necesario: %.2f litros\n", combustibleNecesario);
        System.out.printf("Oxígeno necesario: %.2f unidades\n", oxigenoNecesario);

        // Permitir ajuste de recursos
        while (combustibleDisponible < combustibleNecesario || oxigenoDisponible < oxigenoNecesario) {
            System.out.print("Añadir combustible (litros): ");
            combustibleDisponible += scanner.nextDouble();
            System.out.print("Añadir oxígeno (unidades): ");
            oxigenoDisponible += scanner.nextDouble();
        }
        System.out.println("Recursos ajustados. ¡Listos para despegar!");
    }

    public static double calcularCombustible(double distancia) {
        return distancia * 0.1;
    }

    public static double calcularOxigeno(double distancia) {
        return distancia * 0.05;
    }

    // Iniciar simulación
    public static void iniciarSimulacion() {
        if (planetaSeleccionado == -1 || naveSeleccionada == -1 || cantidadPasajeros <= 0) {
            System.out.println("Faltan configuraciones antes de iniciar el viaje.");
            return;
        }

        System.out.println("\n--- Iniciando simulación del vuelo ---");
        double distancia = distancias[planetaSeleccionado] * 1_000_000; // Convertir a km
        double velocidad = velocidades[naveSeleccionada];
        double tiempoHoras = distancia / velocidad;
        double tiempoDias = tiempoHoras / 24;

        System.out.printf("¡Preparando para el viaje a %s!\n", planetas[planetaSeleccionado]);
        System.out.printf("Distancia total: %.2f km\n", distancia);
        System.out.printf("Velocidad de la nave: %.2f km/h\n", velocidad);
        System.out.printf("El viaje tomará aproximadamente %.2f días.\n", tiempoDias);

        // Simulación de progreso del viaje con eventos en puntos clave
        for (int progreso = 10; progreso <= 100; progreso += 20) {
            System.out.printf("Progreso del viaje: %d%%\n", progreso);
            simularEvento(progreso);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("¡Has llegado a tu destino! Felicitaciones.");
    }

    // Generación de eventos durante el viaje
    public static void simularEvento(int progreso) {
        switch (progreso) {
            case 10 -> {
                System.out.println("¡El despegue ha sido exitoso! Todo parece en orden.");
                break;
            }
            case 30 -> {
                System.out.println("¡Se ha detectado una falla en el sistema!");
                System.out.println("¿Deseas repararla? (1: Reparar, 2: Continuar sin reparar): ");
                int decision = scanner.nextInt();
                if (decision == 1) {
                    System.out.println("¡La reparación fue exitosa! El sistema está funcionando correctamente.");
                } else {
                    System.out.println("Decidiste continuar sin reparar. Puede haber consecuencias más adelante.");
                }
                break;
            }
            case 50 -> {
                System.out.println("¡La nave ha detectado presencia de asteroides!");
                System.out.println("¿Quieres desviar el curso para evitar el peligro? (1: Desviar, 2: Continuar): ");
                int decision = scanner.nextInt();
                if (decision == 1) {
                    System.out.println("¡Desviando el curso para evitar los asteroides!");
                } else {
                    System.out.println("¡Continuando por el camino actual! Riesgo de colisión.");
                }
                break;
            }
            case 70 -> {
                System.out.println("¡La nave ha tomado un desvío para evitar colisionar con asteroides!");
                break;
            }
            case 90 -> {
                System.out.println("¡Estamos acercándonos al planeta destino!");
                break;
            }
        }
    }
}
