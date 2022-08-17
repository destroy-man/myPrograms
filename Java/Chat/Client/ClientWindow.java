package client;

/**
 *
 * @author Коробейников Константин
 */
import javax.accessibility.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class ClientWindow extends JFrame{
//    private static final String SERVER_HOST="localhost"; // адрес сервера
//    private static final int SERVER_PORT=3443; // порт
    private static String server_host; // адрес сервера
    private static int server_port; // порт
    private Socket clientSocket; // клиентский сокет
    private Scanner inMessage; // входящее сообщение
    private PrintWriter outMessage; // исходящее сообщение
    // следующие поля отвечают за элементы формы
    private JTextField jtfMessage;
    private JTextField jtfName;
    private JTextArea jtaTextAreaMessage;
    private String clientName=""; // имя клиента
    // получаем имя клиента
    public String getClientName(){
        return this.clientName;
    }
    // конструктор
    public ClientWindow(){
        try(InputStream input=new FileInputStream("chat.properties")){
            Properties prop=new Properties();
            prop.load(input);
            server_host=prop.getProperty("server_host");
            server_port=Integer.parseInt(prop.getProperty("server_port"));
            clientName=prop.getProperty("client_name");
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        
        try{
            // подключаемся к серверу
            clientSocket=new Socket(server_host,server_port);
            inMessage=new Scanner(clientSocket.getInputStream());
            outMessage=new PrintWriter(clientSocket.getOutputStream());
            //
            outMessage.println(clientName);
            outMessage.flush();
            //
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        // Задаём настройки элементов на форме
        setBounds(600,300,600,500);
        setTitle("Клиент");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jtaTextAreaMessage=new JTextArea();
        jtaTextAreaMessage.setEditable(false);
        jtaTextAreaMessage.setLineWrap(true);
        JScrollPane jsp=new JScrollPane(jtaTextAreaMessage);
        add(jsp,BorderLayout.CENTER);
        // label, который будет отражать количество клиентов в чате
        JLabel jlNumberOfClients=new JLabel("Количество клиентов в чате: ");
        add(jlNumberOfClients,BorderLayout.NORTH);
        JPanel bottomPanel=new JPanel(new BorderLayout());
        add(bottomPanel,BorderLayout.SOUTH);
        JButton jbSendMessage=new JButton("Отправить");
        bottomPanel.add(jbSendMessage,BorderLayout.EAST);
        jtfMessage=new JTextField("Введите ваше сообщение: ");
        bottomPanel.add(jtfMessage,BorderLayout.CENTER);
        //jtfName=new JTextField("Введите ваше имя: ");
        jtfName=new JTextField(clientName);
        bottomPanel.add(jtfName,BorderLayout.WEST);
        // обработчик события нажатия кнопки отправки сообщения
        jbSendMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // если имя клиента, и сообщение непустые, то отправляем сообщение
                if(!jtfMessage.getText().trim().isEmpty() && !jtfName.getText().trim().isEmpty()){
                //    clientName=jtfName.getText();
                    sendMsg();
                    // фокус на текстовое поле с сообщением
                    jtfMessage.grabFocus();
                }
            }
        });
        // при фокусе поле сообщения очищается
        jtfMessage.addFocusListener(new FocusAdapter(){
            public void focusGained(FocusEvent e){
                jtfMessage.setText("");
            }
        });
        // при фокусе поле имя очищается
//        jtfName.addFocusListener(new FocusAdapter(){
//            public void focusGained(FocusEvent e){
//                jtfName.setText("");
//            }
//        });
        // в отдельном потоке начинаем работу с сервером
        new Thread(new Runnable(){
            public void run(){
                try{
                    // бесконечный цикл
                    while(true){
                        // если есть входящее сообщение
                        if(inMessage.hasNext()){
                            // считываем его
                            String inMes=inMessage.nextLine();
                            String clientsInChat="Клиентов в чате = ";
                            if(inMes.indexOf(clientsInChat)==0)
                                jlNumberOfClients.setText(inMes);
                            else{
                                // выводим сообщение
                                jtaTextAreaMessage.append(inMes);
                                jtaTextAreaMessage.append("\n");
                            }
                        }
                    }
                }
                catch(Exception e){}
            }
        }).start();
        // добавляем обработчик события закрытия окна клиентского приложения
        addWindowListener(new WindowAdapter(){
            public void WindowClosing(WindowEvent e){
                super.windowClosing(e);
                try{
                    // здесь проверяем, что имя клиента непустое и не равно значению по умолчанию
                    if(!clientName.isEmpty() && clientName!="Введите ваше имя: ")
                        outMessage.println(clientName+" вышел из чата!");
                    else
                        outMessage.println("Участник вышел из чата, так и не представившись!");
                    // отправляем служебное сообщение, которое является признаком того, что клиент вышел из чата
                    outMessage.println("##session##end##");
                    outMessage.flush();
                    outMessage.close();
                    inMessage.close();
                    clientSocket.close();
                }
                catch(IOException ex){}
            }
        });
        // отображаем форму
        setVisible(true);
    }
    // отправка сообщения
    public void sendMsg(){
        // формируем сообщение для отправки на сервер
        String messageStr=jtfName.getText()+": "+jtfMessage.getText();
        // отправляем сообщение
        outMessage.println(messageStr);
        outMessage.flush();
        jtfMessage.setText("");
    }
}
