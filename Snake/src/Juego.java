
import java.io.*;
import java.net.Socket;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Juego extends Thread {

    private PanelMain PM;
    private PanelJuego PJ;
    private Snake s;
    private Socket socket;
    private Fruta f;
    private boolean jt = false; // Juego terminado
    private String nombre;

    private CyclicBarrier barrier;

    private ConcurrentHashMap<String, Integer> puntuaciones;

    public Juego(Socket s1, PanelJuego pj, Snake snake, PanelMain pm, CyclicBarrier barrier,ConcurrentHashMap<String, Integer> puntuaciones) {
        f = new Fruta(200, 200);
        this.PM = pm;
        this.s = snake;
        this.PJ = pj;
        this.socket = s1;
        this.PJ.setCuerpoSnake(snake.getCuerpo());
        this.PJ.setFruta(f);
        this.s.nuevaDireccion(Flechas.ARRIBA);
        this.barrier = barrier; // Recibimos el CountDownLatch
        this.puntuaciones = puntuaciones;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Writer writer1 = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));) {

            String nombreJugador = reader.readLine();
            this.nombre = nombreJugador; // Guardamos el nombre del jugador

            System.out.println("Jugador conectado: " + nombreJugador);
            barrier.await();

            System.out.println("El juego comienza en 10 segs...");
            Thread.sleep(10000);

            // Ciclo principal del juego
            while (!jt) {
                s.move();
                s.Colisiones();

                // Si hay colisión, el juego termina
                if (s.isGameOver()) {
                    jt = true;
                    PM.gO = true;
                }

                if (!jt) {
                    PJ.repaint();
                    PM.gO = false;
                }

                // Control de velocidad del juego
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            int puntuacion = PM.getPuntos();
            puntuaciones.put(nombreJugador, puntuacion);

            barrier.await();

            for (String nombreJugador2 : puntuaciones.keySet()) {
                puntuacion = puntuaciones.get(nombreJugador2);
                writer1.write(nombreJugador2 + ": " + puntuacion+ "\n");
                writer1.flush();
            }


            writer1.write("FIN\n");
            writer1.flush();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
    }
}
