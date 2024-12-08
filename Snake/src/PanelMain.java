import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.io.*;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class PanelMain extends JFrame {

    private PanelPuntos PP;
    private PanelJuego GP;
    public Juego j;

    private Socket socket;
    private Snake snake;

    private CyclicBarrier barrier;

    private final ConcurrentHashMap<String, Integer> puntuaciones;



    private Flechas direction = Flechas.ABAJO;
    private boolean started = false;
    public boolean gO = false;

    public PanelMain(Snake s, Socket socket, CyclicBarrier barrier,ConcurrentHashMap<String, Integer> puntuaciones) {
        this.snake = s;
        this.socket = socket;
        this.barrier = barrier;
        this.puntuaciones = puntuaciones;
        componentes();
        iniciarGUI();
        empezarJuego();
    }

    public boolean isGameOver() {
        return gO;
    }

    private void empezarJuego() {
        snake = new Snake(GP, PP);
        GP.addKeyListener(new KeyboardHandler());
        GP.setFocusable(true);
        j = new Juego(socket, GP, snake, this,barrier,puntuaciones);
        j.start();
    }

    private void componentes() {
        setLayout(new GridBagLayout());
        addKeyListener(new KeyboardHandler());

        PP = new PanelPuntos();
        add(PP, new GBC(0, 8, 8, 1));

        GP = new PanelJuego();
        add(GP, new GBC(0, 0, 8, 8));
    }

    private void iniciarGUI() {
        pack();
        setTitle("Snake Multijugador");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    public void gameOver() {
        try {
            // Notificar al servidor que el juego ha terminado y enviar la puntuación
            int puntuacion = PP.getPuntos();
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("Puntuacion: " + puntuacion);
            out.println("FIN");

            JOptionPane.showMessageDialog(this, "Fin del Juego\nPuntuación: " + puntuacion, "Game Over", JOptionPane.INFORMATION_MESSAGE);

            // Cerrar la conexión
            socket.close();
            dispose();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPuntos() {
        return PP.getPuntos(); // Accede a la puntuación desde PanelPuntos
    }

    private class KeyboardHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (!started) started = true;

            if (snake != null) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        if (direction != Flechas.ABAJO) {
                            snake.nuevaDireccion(Flechas.ARRIBA);
                            direction = Flechas.ARRIBA;
                        }
                        break;

                    case KeyEvent.VK_DOWN:
                        if (direction != Flechas.ARRIBA) {
                            snake.nuevaDireccion(Flechas.ABAJO);
                            direction = Flechas.ABAJO;
                        }
                        break;

                    case KeyEvent.VK_LEFT:
                        if (direction != Flechas.DERECHA) {
                            snake.nuevaDireccion(Flechas.IZQUIERDA);
                            direction = Flechas.IZQUIERDA;
                        }
                        break;

                    case KeyEvent.VK_RIGHT:
                        if (direction != Flechas.IZQUIERDA) {
                            snake.nuevaDireccion(Flechas.DERECHA);
                            direction = Flechas.DERECHA;
                        }
                        break;
                }
            }
        }
    }
}
