import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.io.*;
import java.net.Socket;
public class PanelMain extends JFrame {

        private PanelPuntos PP;
        private PanelJuego GP;
        private Juego j;

        private Socket socket;
        private ObjectOutputStream out;
        private ObjectInputStream in;
        private Snake snake;

        private Flechas direction = Flechas.ABAJO;

        private boolean started = false;

        public boolean gO = false;

        public PanelMain(Snake s,Socket socket) {
            componentes();
            this.snake = s;
            this.socket = socket;
            empezarJogo();
            iniciarGUI();
        }
    public boolean isGameOver() {
            return gO;
    }
        private void empezarJogo() {
            snake = new Snake(GP, PP);
            GP.addKeyListener(new KeyboardHandler());
            GP.setFocusable(true);
            Juego j = new Juego(socket,GP,snake, this);
            j.start();
            this.j = j;
        }

    private void componentes() {
        setLayout(new GridBagLayout());
        addKeyListener(new KeyboardHandler());

        PP = new PanelPuntos();
        add(PP, new GBC(0, 8, 8, 1));

        GP = new PanelJuego();
        add(GP, new GBC(0, 0, 8, 8));

    }

    protected void setSnake(Snake s) {
       this.snake = s;

    }

    /* Intento Fallido
    protected void ActGP(Snake s,Fruta f) {
        GP.setCuerpoSnake(s.getParts());
        GP.setFruta(f);
        GP.repaint();
    }
*/
        private void iniciarGUI() {
            pack();
            setTitle("Snake");
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setResizable(false);
            setVisible(true);
        }
        public void nuevaPartida() {
            started = true;
            t.start();
        }
        public void gameOver() {
            int respuestaCli = JOptionPane.showConfirmDialog(this,"Nueva Partida?", "Fin del Juego!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
            switch (respuestaCli) {
                case JOptionPane.OK_OPTION:
                    direction = Flechas.IZQUIERDA;
                    started = false;
                    snake = new Snake(GP, PP);
                    PP.clear();
                    GP.Defecto();
                    PP.repaint();
                    GP.repaint();
                    Juego j = new Juego(socket,GP, snake, this);
                    j.start();
                    break;

                case JOptionPane.CANCEL_OPTION:
                    try (DataOutputStream w = new DataOutputStream(socket.getOutputStream());){
                        w.writeBytes("FIN");
                        w.flush();
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                    }
                    if (socket != null) {
                        socket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                    dispose();
                    break;
            }
        }
    private class KeyboardHandler extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            if (!started) nuevaPartida();

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
