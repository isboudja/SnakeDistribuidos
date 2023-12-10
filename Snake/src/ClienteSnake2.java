import java.io.IOException;
import java.net.Socket;

public class ClienteSnake2 {
    public static void main(String[] args){
        try (Socket socket = new Socket("localhost", 55555);){




        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
