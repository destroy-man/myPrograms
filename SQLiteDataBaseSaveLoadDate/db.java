package com.korobeynikov;

import java.sql.SQLException;
public class db {
    public static void main(String[] args) throws ClassNotFoundException,SQLException{
        int numberOperation=0;
        if(!args[0].equals("1") && !args[0].equals("2") && !args[0].equals("3") && !args[0].equals("help")) {
            System.out.println("Указан некорректный номер операции");
            System.exit(0);
        }
        else if(args[0].equals("help"))
            System.out.println("При указании определенной цифры программа выполняет следующие функции:\n" +
                               "1. Создание N кол-ва грузов в ячейке. После цифры указывается кол-во грузов и имя ячейки.\n" +
                               "2. Вывод общей информации о грузах в ячейках. После цифры указываются имена ячеек через пробел.\n" +
                               "3. Экспорт всех данных в xml файл. После цифры указывается имя файла.\n");
        else
            numberOperation=Integer.parseInt(args[0]);
        switch(numberOperation){
            case 1:
                conn.Conn();
                int countLoads=Integer.parseInt(args[1]);
                conn.createLoadsDB(countLoads,args[2]);
                break;
            case 2:
                conn.Conn();
                String[] nameLocs=new String[args.length-1];
                for(int i=1;i<args.length;i++)
                    nameLocs[i-1]=args[i];
                conn.showLoadsDB(nameLocs);
                break;
            case 3:
                conn.Conn();
                conn.exportDataToXML(args[1]);
                break;
        }
    }
}
