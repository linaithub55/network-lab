package chess.server;

import chess.shared.ChessMath;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ChessServer {
    public static void main(String[] args) {
        int port = 9090;
        System.out.println("Запуск шахматного сервера на порту " + port + "...");

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер успешно запущен. Ожидание подключений...");
            
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                    
                    System.out.println("Подключился новый клиент: " + clientSocket.getInetAddress());
                    
                    String inputLine = in.readLine();
                    if (inputLine != null) {
                        System.out.println("Получены данные от клиента: " + inputLine);
                        
                        String[] parts = inputLine.split(" ");
                        if (parts.length == 6) {
                            int kingX = Integer.parseInt(parts[0]);
                            int kingY = Integer.parseInt(parts[1]);
                            int rookX = Integer.parseInt(parts[2]);
                            int rookY = Integer.parseInt(parts[3]);
                            int bishopX = Integer.parseInt(parts[4]);
                            int bishopY = Integer.parseInt(parts[5]);
                            
                            int threatCode = ChessMath.checkThreats(kingX, kingY, rookX, rookY, bishopX, bishopY);
                            String response;
                            switch (threatCode) {
                                case 3: response = "Угроза от обеих фигур"; break;
                                case 1: response = "Угроза от ладьи"; break;
                                case 2: response = "Угроза от слона"; break;
                                default: response = "Король в безопасности"; break;
                            }
                            
                            System.out.println("Отправляем ответ: " + response);
                            out.println(response);
                        } else {
                            out.println("ОШИБКА: Ожидается 6 координат (KingX KingY RookX RookY BishopX BishopY)");
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Ошибка при обработке клиента: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("Ошибка при запуске сервера: " + e.getMessage());
        }
    }
}
