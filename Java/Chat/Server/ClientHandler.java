package server;

/**
 *
 * @author Коробейников Константин
 */
import client.ClientWindow;
import java.io.*;
import java.net.*;
import java.util.*;

// реализуем интерфейс Runnable, который позволяет работать с потоками
public class ClientHandler implements Runnable{
    private Server server; // экземпляр нашего сервера
    private PrintWriter outMessage; // исходящее сообщение
    private Scanner inMessage; // входящее собщение
    //private static String host="localhost";
    //private static int port=3443;
    private static String host;
    private static int port;
    private Socket clientSocket=null; // клиентский сокет
    private static int clients_count=0; // количество клиентов в чате
   
    private String clientName;
    boolean nameInList;
    // конструктор, который принимает клиентский сокет и сервер
    public ClientHandler(Socket socket,Server server){
        try{
            clients_count++;
            this.server=server;
            this.clientSocket=socket;
            this.outMessage=new PrintWriter(socket.getOutputStream());
            this.inMessage=new Scanner(socket.getInputStream());
            //
            BufferedReader reader = new BufferedReader(new FileReader("ClientsList.txt"));
            String line;
            ArrayList<String> lines = new ArrayList<String>();
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            if(inMessage.hasNext()){
                nameInList=false;
                clientName=inMessage.nextLine();
                for(String name:lines)
                    if(name.equals(clientName)){
                        nameInList=true;
                        break;
                    }
            }
            //
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }
    // Переопределяем метод run(), который вызывается когда
    // мы вызываем new Thread(client).start();
    public void run(){
        try{
            while(true){
                // сервер отправляет сообщение
                server.sendMessageToAllClients("Новый участник вошёл в чат!\n"+clientName);
                server.sendMessageToAllClients("Клиентов в чате = "+clients_count);
                break;
            }
            while(true){
                // Если от клиента пришло сообщение
                if(inMessage.hasNext()){
                    String clientMessage=inMessage.nextLine();
                    // если клиент отправляет данное сообщение, то цикл прерывается и 
                    // клиент выходит из чата
                    if(clientMessage.equalsIgnoreCase("##session##end##"))
                        break;
                    // выводим в консоль сообщение (для теста)
                    System.out.println(clientMessage);
                    // отправляем данное сообщение всем клиентам
                    server.sendMessageToAllClients(clientMessage);
                }
                // останавливаем выполнение потока на 100 мс
                Thread.sleep(100);
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        finally{
            this.close();
        }
    }
    // отправляем сообщение
    public void sendMsg(String msg){
        try{
            outMessage.println(msg);
            outMessage.flush();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    // клиент выходит из чата
    public void close(){
        // удаляем клиента из списка
        server.removeClient(this);
        clients_count--;
        server.sendMessageToAllClients("Клиентов в чате = "+clients_count);
    }
}
