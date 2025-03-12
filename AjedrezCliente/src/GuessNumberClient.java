import java.io.*;
import java.net.Socket;

public class GuessNumberClient {
    private static final String SERVER_HOST = "localhost"; 
    private static final int SERVER_PORT = 5000;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
             BufferedReader serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter serverWriter = new PrintWriter(socket.getOutputStream(), true)) {

            Thread readThread = new Thread(() -> {
                String response;
                try {
                    while ((response = serverReader.readLine()) != null) {
                        System.out.println(response);
                    }
                } catch (IOException e) {
                    System.out.println("Error al leer del servidor: " + e.getMessage());
                }
            });
            readThread.start();

            // Bucle para enviar al servidor lo que el usuario escriba en consola
            String input;
            while ((input = consoleReader.readLine()) != null) {
                serverWriter.println(input);
            }

        } catch (IOException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
    }
}
