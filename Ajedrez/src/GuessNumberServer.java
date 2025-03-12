import java.io.*;
import java.net.*;
import java.util.Random;

public class GuessNumberServer {
    private static final int PORT = 5000;
    private static int targetNumber;
    private static boolean gameOver = false;

    public static void main(String[] args) {
        targetNumber = new Random().nextInt(100) + 1;
        System.out.println("Servidor iniciado en el puerto " + PORT);
        System.out.println("Número a adivinar (para pruebas): " + targetNumber);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            // Espera y acepta solo un cliente
            Socket socket = serverSocket.accept();
            System.out.println("Cliente conectado: " + socket);

          
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {


                writer.println("Bienvenido al juego Adivina el Número. Ingresa tu nombre:");
                String playerName = reader.readLine();
                writer.println("Hola " + playerName + "! Adivina un número entre 1 y 100:");

 
                while (!gameOver) {
                    String input = reader.readLine();

                    if (input == null) {
                        System.out.println("El cliente se ha desconectado.");
                        break;
                    }

                    int guess;
                    try {
                        guess = Integer.parseInt(input.trim());
                    } catch (NumberFormatException e) {
                        writer.println("Por favor, ingresa un número válido.");
                        continue;
                    }

                    if (guess < targetNumber) {
                        writer.println("El número es mayor.");
                    } else if (guess > targetNumber) {
                        writer.println("El número es menor.");
                    } else {
                        writer.println("¡Correcto! Has adivinado el número.");
                        System.out.println("El jugador " + playerName + " ha ganado adivinando el número " + guess + "!");
                        gameOver = true;
                    }
                }
            } catch (IOException e) {
                System.out.println("Error al manejar la conexión del cliente: " + e.getMessage());
            }

        } catch (IOException e) {
            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }
}
