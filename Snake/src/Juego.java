
import java.io.*;
import java.net.Socket;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

public class Juego extends Thread {

    private PanelMain PM;
    private PanelJuego PJ;
    private Snake s;
    private Socket socket;
    private Fruta f;
    private boolean jt = false; // Juego terminado
    private String nombre;

    private CountDownLatch latch;

    private ConcurrentHashMap<String, Integer> puntuaciones;

    public Juego(Socket s1, PanelJuego pj, Snake snake, PanelMain pm, CountDownLatch latch,ConcurrentHashMap<String, Integer> puntuaciones) {
        f = new Fruta(200, 200);
        this.PM = pm;
        this.s = snake;
        this.PJ = pj;
        this.socket = s1;
        this.PJ.setCuerpoSnake(snake.getCuerpo());
        this.PJ.setFruta(f);
        this.s.nuevaDireccion(Flechas.ARRIBA);
        this.latch = latch; // Recibimos el CountDownLatch
        this.puntuaciones = puntuaciones;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintStream out = new PrintStream(socket.getOutputStream())) {

            String nombreJugador = reader.readLine();
            this.nombre = nombreJugador; // Guardamos el nombre del jugador

            System.out.println("Jugador conectado: " + nombreJugador);
            latch.countDown();
            latch.await();

            System.out.println("Esperando 10 segundos para iniciar el juego...");
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

            // Cuando el juego termina, enviar puntuación al servidor
            int puntuacion = PM.getPuntos(); // Método para obtener los puntos del jugador
            if (!socket.isClosed()) {
                out.println("Puntuacion: " + puntuacion);
                out.println("FIN"); // Señal para indicar al servidor que el juego terminó
            }

            System.out.println("Juego terminado. Puntuación enviada: " + puntuacion);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
