package server;

/**
 *
 * @author Коробейников Константин
 */
import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    //static int port=3443; // порт, который будет прослушивать наш сервер
    static int port; // порт, который будет прослушивать наш сервер
    private ArrayList<ClientHandler> clients=new ArrayList<ClientHandler>(); // список клиентов, которые будут подключаться к серверу
    public Server(){
        Socket clientSocket=null; //сокет клиента, это некий поток, который будет подключаться к серверу по адресу и порту
        ServerSocket serverSocket=null; // серверный сокет
        
        try(InputStream input=new FileInputStream("chat.properties")){
            Properties prop=new Properties();
            prop.load(input);
            port=Integer.parseInt(prop.getProperty("server_port"));
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        
        try{
            // создаём серверный сокет на определенном порту
            serverSocket=new ServerSocket(port);
            System.out.println("Сервер запущен!");
            
//            BufferedReader reader = new BufferedReader(new FileReader("ClientsList.txt"));
//            String line;
//            ArrayList<String> lines = new ArrayList<String>();
//            while ((line = reader.readLine()) != null) {
//                lines.add(line);
//            }
            
            // запускаем бесконечный цикл
            while(true){
                // таким образом ждём подключений от сервера
                clientSocket=serverSocket.accept();
                // создаём обработчик клиента, который подключился к серверу
                // this - это наш сервер
                
                ClientHandler client=new ClientHandler(clientSocket,this);
                if(client.nameInList){
                    clients.add(client);
                    // каждое подключение клиента обрабатываем в новом потоке
                    new Thread(client).start();
                }
                else client.close();
            }
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        finally{
            try{
                // закрываем подключение
                clientSocket.close();
                System.out.println("Сервер остановлен");
                serverSocket.close();
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }
    // отправляем сообщение всем клиентам
    public void sendMessageToAllClients(String msg){
        for(ClientHandler o:clients){
            o.sendMsg(msg);
        }
    }
    // удаляем клиента из коллекции при выходе из чата
    public void removeClient(ClientHandler client){
        clients.remove(client);
    }
}
