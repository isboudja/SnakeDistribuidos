import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ServidorSnake {

    public static void main(String[] args) {

        final int PORT = 55555;
        ExecutorService pool = Executors.newCachedThreadPool();
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor esperando conexiones en el puerto " + PORT + "...");


            while (true) {
                try {
                    List<Socket> jugadores = new ArrayList<>();

                    while (jugadores.size() < 2) {
                        Socket socket = serverSocket.accept();
                        jugadores.add(socket);
                        System.out.println("Cliente conectado desde " + socket.getInetAddress());

                    }

                    System.out.println("Dos jugadores conectados. Iniciando el juego...");

                    Socket jugador1 = jugadores.get(0);
                    Socket jugador2 = jugadores.get(1);

                    ManejadorClientes mc = new ManejadorClientes(jugador1, jugador2);
                    pool.execute(mc);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(pool !=null){
                pool.shutdown();
            }
        }
    }
}

