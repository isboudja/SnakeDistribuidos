import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Objects;

public class ClienteSnake {
    public static void main(String[] args){
        String fin = "";
        try (Socket socket = new Socket("localhost", 55555);
             DataInputStream r  = new DataInputStream(socket.getInputStream());){

            while (!Objects.equals(fin, "FIN")) {
                fin = r.readLine();

            }

    } catch (IOException e) {
            e.printStackTrace();
        }


    }


}