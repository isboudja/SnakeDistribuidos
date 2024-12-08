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
        try{
            PanelJuego pj1 = new PanelJuego();
            PanelJuego pj2 = new PanelJuego();

            PanelPuntos pp1 = new PanelPuntos();
            PanelPuntos pp2 = new PanelPuntos();

            Snake snake1 = new Snake(pj1, pp1);
            Snake snake2 = new Snake(pj2, pp2);

            Fruta f1 = new Fruta(200, 200);
            Fruta f2 = new Fruta(200, 200);

            CyclicBarrier barrier = new CyclicBarrier(2);

            PanelMain panelMain1 = new PanelMain(snake1,jugador1, barrier, puntuaciones);
            PanelMain panelMain2 = new PanelMain(snake1,jugador2, barrier, puntuaciones);

            panelMain1.j.join();
            panelMain2.j.join();




        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }finally {
            try {
                if(jugador1 != null){
                    jugador1.close();
                }
                if(jugador2 != null){
                    jugador2.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
