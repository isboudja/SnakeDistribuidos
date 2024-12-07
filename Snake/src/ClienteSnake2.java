import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.Scanner;


import java.io.*;
import java.net.*;
import java.util.*;

public class ClienteSnake2 {
    public static void main(String[] args) {
        String mensaje = "";
        Scanner scanner = new Scanner(System.in);

        try (Socket socket = new Socket("localhost", 55555);
             BufferedReader r = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintStream ps = new PrintStream(socket.getOutputStream())) {

            System.out.println("Conectado al servidor. Esperando a otro jugador...");

            System.out.print("Ingresa tu nombre: ");
            String nombre = scanner.nextLine();
            ps.println(nombre);

            System.out.println(r.readLine());

            while ((mensaje = r.readLine()) != null) {
                if (Objects.equals(mensaje, "FIN")) {
                    break;
                }

                System.out.println(mensaje);
            }
            System.out.println("El juego ha terminado. ¡Gracias por jugar!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


