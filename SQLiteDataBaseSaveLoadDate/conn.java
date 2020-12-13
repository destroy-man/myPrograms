package com.korobeynikov;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class conn {

    private static Connection conn;
    private static Statement statmt;
    private static ResultSet resSet;

    public static void Conn() throws ClassNotFoundException, SQLException {
        conn=null;
        Class.forName("org.sqlite.JDBC");
        conn=DriverManager.getConnection("jdbc:sqlite:test.s3db");
        System.out.println("База Подключена!");
    }

    private static void createTables() throws SQLException{
        statmt = conn.createStatement();
        statmt.execute("CREATE TABLE if not exists 'Location' ('id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 'name' VARCHAR(32) UNIQUE NOT NULL)");
        statmt.execute("CREATE TABLE if not exists 'Loads' ('id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 'name' VARCHAR(32) UNIQUE NOT NULL, 'Loc_id' INTEGER NOT NULL)");
        System.out.println("Созданы таблицы базы данных!");
    }

    public static void createLoadsDB(int countLoads,String nameLoc) throws SQLException{
        try {
            int locId = 0;
            statmt = conn.createStatement();
            resSet = statmt.executeQuery("SELECT id FROM Location WHERE name='" + nameLoc + "'");
            if (!resSet.next()) {
                statmt.execute("INSERT INTO 'Location' ('name') VALUES ('" + nameLoc + "'); ");
                resSet = statmt.executeQuery("SELECT id FROM Location WHERE name='" + nameLoc + "'");
                while (resSet.next())
                    locId = resSet.getInt("id");
            } else
                locId = resSet.getInt("id");


            int currentCountLoads = 0;
            resSet = statmt.executeQuery("SELECT COUNT(id) FROM Loads");
            while (resSet.next())
                currentCountLoads = resSet.getInt("COUNT(id)");

            for (int i = 1; i <= countLoads; i++) {
                StringBuilder nameLoad = new StringBuilder("LD");
                if (currentCountLoads + i < 10) nameLoad.append("000" + (currentCountLoads + i));
                else if (currentCountLoads + i < 100) nameLoad.append("00" + (currentCountLoads + i));
                else if (currentCountLoads + i < 1000) nameLoad.append("0" + (currentCountLoads + i));
                else nameLoad.append("" + (currentCountLoads + i));
                statmt.execute("INSERT INTO 'Loads' ('name', 'Loc_id') VALUES ('" + nameLoad + "','" + locId + "'); ");
            }
            System.out.println("Данные успешно добавлены!");
            conn.close();
            statmt.close();
            resSet.close();
        }
        catch(SQLException e){
            createTables();
            createLoadsDB(countLoads,nameLoc);
        }
    }

    public static void showLoadsDB(String[] nameLocs) throws SQLException {
        try {
            statmt = conn.createStatement();
            for (String nameLoc : nameLocs) {
                int locId = 0;
                resSet = statmt.executeQuery("SELECT id FROM Location WHERE name='" + nameLoc + "'");
                if (!resSet.next())
                    System.out.println("Ячейка " + nameLoc + " в базе данных не обнаружена");
                else {
                    locId = resSet.getInt("id");
                    resSet = statmt.executeQuery("SELECT COUNT(id) FROM Loads WHERE Loc_id='" + locId + "'");
                    System.out.println(nameLoc + " - " + resSet.getInt("COUNT(id)"));
                }
            }
            conn.close();
            statmt.close();
            resSet.close();
        }
        catch(SQLException e){
            createTables();
            showLoadsDB(nameLocs);
        }
    }

    public static void exportDataToXML(String nameFile) throws SQLException {
        try {
            StringBuilder xmlText = new StringBuilder("<dbinfo>");
            statmt = conn.createStatement();
            resSet = statmt.executeQuery("SELECT * FROM Location");
            while (resSet.next()) {
                int locId = resSet.getInt("id");
                String locName = resSet.getString("name");
                boolean hasLoads = false;
                Statement loadStatmt = conn.createStatement();
                ResultSet loadsResSet = loadStatmt.executeQuery("SELECT * FROM Loads WHERE Loc_id='" + locId + "'");
                while (loadsResSet.next()) {
                    if (!hasLoads) {
                        xmlText.append("\n   <location name=\"" + locName + "\" id=\"" + locId + "\">");
                        hasLoads = true;
                    }
                    int loadId = loadsResSet.getInt("id");
                    String nameLoad = loadsResSet.getString("name");
                    xmlText.append("\n      <load name=\"" + nameLoad + "\" id=\"" + loadId + "\"/>");
                }
                if (hasLoads)
                    xmlText.append("\n   </location>");
            }
            xmlText.append("\n</dbinfo>");
            File xmlFile = new File(nameFile);
            try {
                FileOutputStream fStream = new FileOutputStream(xmlFile);
                OutputStreamWriter oWriter = new OutputStreamWriter(fStream, "UTF-8");
                BufferedWriter writer = new BufferedWriter(oWriter);
                writer.write(xmlText.toString());
                writer.close();
                oWriter.close();
                fStream.close();
                System.out.println("Данные записаны в xml файл!");
            } catch (FileNotFoundException e) {
                System.out.println("Не удалось найти указанный файл");
            } catch (IOException e) {
                System.out.println("Не удалось записать данные в указанный файл");
            } finally {
                conn.close();
                statmt.close();
                resSet.close();
            }
        }
        catch(SQLException e){
            createTables();
            exportDataToXML(nameFile);
        }
    }
}
