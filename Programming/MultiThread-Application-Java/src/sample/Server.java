package sample;

import Classes.Database.QueryProcessing;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Server {
    private Connection connection = null;
    private ServerSocket ServerSocket = null;

    public Server(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_store?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "11111");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        try {
            ServerSocket = new ServerSocket(3003);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(){
        while(true){
            try {
                System.out.println("Waiting...");
                Socket socket = this.ServerSocket.accept();
                System.out.println("Connection - " + socket.getInetAddress().getHostAddress());
                QueryProcessing ch = new QueryProcessing(socket, connection);
                ch.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        Server server = new Server();
        server.start();
    }

}
