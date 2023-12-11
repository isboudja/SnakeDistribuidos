
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;

public class Juego extends Thread {

    //private ObjectOutputStream out; intento de hacerlo con Object
    private PanelMain PM;
    private PanelJuego PJ;
    private Snake s;
    //private ObjectInputStream in;
    private Socket socket;
    private Fruta f;

    private boolean jt =false;


    public Juego(Socket s1,PanelJuego pj, Snake snake, PanelMain pm) {
        f = new Fruta(200, 200);
        this.PM = pm;
        this.s = snake;
        this.PJ = pj;
        this.socket = s1;
        this.PJ.setCuerpoSnake(snake.getCuerpo());
        this.PJ.setFruta(f);
        this.s.nuevaDireccion(Flechas.ARRIBA);
    }
    public void run() {
        while (!jt) {
            s.move();
            s.Colisiones();
            if (s.isGameOver()) {
                jt = true;
                PM.gO = true;
            }
            if (!jt) {
                PJ.repaint();
                PM.gO = false;
            }
            try {
                Juego.sleep(100);//se puede alterar la velocidad del juego cambiendo el tiempo a menor tiempo mas rapido
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if (PM.isGameOver()) {
            PM.gameOver();
        }

    }

    //Intento fallido
   /* public void run() {
        try (ObjectOutputStream Out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            while (!socket.isClosed() && socket.isConnected()) {
                try {
                    if (in.available() > 0) {
                        Flechas dir = (Flechas) in.readObject();
                        s.changeDirection(dir);
                    }
                } catch (IOException | ClassNotFoundException e) {
                    // Maneja las excepciones relacionadas con la lectura del objeto
                    e.printStackTrace();
                    break; // Sale del bucle si hay un error en la lectura
                }

                s.move();
                s.check();

                try {
                    if (socket.isConnected() && !socket.isClosed()) {

                        JuegoData juegoData = new JuegoData(s, f);
                        out.writeObject(juegoData);
                        out.flush();

                    }
                } catch (IOException e) {
                    // Maneja las excepciones relacionadas con la escritura en el objeto
                    e.printStackTrace();
                    break; // Sale del bucle si hay un error en la escritura
                }

                if (s.isGameOver()) {
                    Thread.currentThread().interrupt();
                }

                if (!Thread.currentThread().isInterrupted()) {
                    PJ.repaint();
                }

                Thread.sleep(100);
            }
        } catch (InterruptedException | IOException ex) {
            // Maneja las excepciones relacionadas con la interrupción y cierre del socket
            ex.printStackTrace();
            PM.gameOver();
        }
    }*/
}
