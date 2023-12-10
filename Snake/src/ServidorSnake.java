import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServidorSnake {

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(55555)) {
            System.out.println("Servidor esperando conexiones en el puerto " + 55555 + "...");
            while (true) {
                try{
                    Socket socket = serverSocket.accept();
                System.out.println("Cliente conectado desde " + socket.getInetAddress());
                    PanelJuego pj = new PanelJuego();
                    PanelPuntos pp = new PanelPuntos();
                    Snake snake = new Snake(pj, pp);
                    Fruta f = new Fruta(200,200);

                    Juego j = new Juego(socket,pj,snake,new PanelMain(snake,socket));
                    j.start();
                    System.out.println("Escribio " + socket.getInetAddress());
                } catch (IOException ex) {
                    //throw new RuntimeException(ex);
                    ex.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
