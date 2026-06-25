package chess.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChessClient {
    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 9090;
        
        System.out.println("--- Шахматный Сетевой Клиент ---");
        
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Введите координаты (KingX KingY RookX RookY BishopX BishopY) через пробел:");
            System.out.print("> ");
            String inputLine = scanner.nextLine();
            
            System.out.println("Подключение к серверу " + host + ":" + port + "...");
            try (Socket socket = new Socket(host, port);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                
                // Отправляем данные на сервер
                out.println(inputLine);
                System.out.println("[Клиент] Данные отправлены на сервер.");
                
                // Ждем ответ от сервера
                String response = in.readLine();
                System.out.println("[Сервер ответил]: " + response);
                
            } catch (Exception e) {
                System.out.println("Ошибка при подключении к серверу. Убедитесь, что сервер запущен.");
                System.out.println("Детали ошибки: " + e.getMessage());
            }
        }
    }
}
