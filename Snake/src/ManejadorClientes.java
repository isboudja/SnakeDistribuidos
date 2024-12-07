import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class ManejadorClientes extends Thread {

    private final Socket jugador1;
    private final Socket jugador2;

    private final ConcurrentHashMap<String, Integer> puntuaciones;
    public ManejadorClientes(Socket jugador1, Socket jugador2) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.puntuaciones = new ConcurrentHashMap<>();
    }

    @Override
    public void run() {
        try (
                BufferedReader reader1 = new BufferedReader(new InputStreamReader(jugador1.getInputStream()));
                Writer writer1 = new BufferedWriter(new OutputStreamWriter(jugador1.getOutputStream()));
                BufferedReader reader2 = new BufferedReader(new InputStreamReader(jugador2.getInputStream()));
                Writer writer2 = new BufferedWriter(new OutputStreamWriter(jugador2.getOutputStream()))
        ) {
            PanelJuego pj1 = new PanelJuego();
            PanelJuego pj2 = new PanelJuego();

            PanelPuntos pp1 = new PanelPuntos();
            PanelPuntos pp2 = new PanelPuntos();

            Snake snake1 = new Snake(pj1, pp1);
            Snake snake2 = new Snake(pj2, pp2);

            Fruta f1 = new Fruta(200, 200);
            Fruta f2 = new Fruta(200, 200);

            CountDownLatch latch = new CountDownLatch(2);
            Juego j1 = new Juego(jugador1, pj1, snake1, new PanelMain(snake1, jugador1, latch,puntuaciones), latch,puntuaciones);
            Juego j2 = new Juego(jugador2, pj2, snake2, new PanelMain(snake2, jugador2, latch,puntuaciones), latch,puntuaciones);

            j1.start();
            j2.start();

            j1.join();
            j2.join();

            // Mostrar los resultados de la partida
            System.out.println("Resultado final:");
            puntuaciones.forEach((nombre, puntos) -> {
                System.out.println("Jugador: " + nombre + " - Puntos: " + puntos);
            });

        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
