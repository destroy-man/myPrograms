package com.korobeynikov.rssnews;
import android.os.AsyncTask;

import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
public class RssItem {
    private String title;
    private String description;
    private Date pubDate;
    private String link;

    static ArrayList<RssItem> rssItems;
    static boolean hasRss=false;

    public RssItem(String title, String description, Date pubDate, String link) {
        this.title = title;
        this.description = description;
        this.pubDate = pubDate;
        this.link = link;
    }

    public String getTitle()
    {
        return this.title;
    }

    public String getLink()
    {
        return this.link;
    }

    public String getDescription()
    {
        return this.description;
    }

    public Date getPubDate()
    {
        return this.pubDate;
    }

    @Override
    public String toString() {

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd - hh:mm:ss");

        String result = getTitle() + "  ( " + sdf.format(this.getPubDate()) + " )";
        return result;
    }

    static class RssConnection extends AsyncTask< String, Void, String > {

        @Override
        protected String doInBackground(String... args) {
            URL url;
            HttpURLConnection conn=null;
            try {
                //open an URL connection make GET to the server and
                //take xml RSS data
                url = new URL(args[0]);
                conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("GET");
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(5000);
                conn.connect();

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream is = conn.getInputStream();

                    //DocumentBuilderFactory, DocumentBuilder are used for
                    //xml parsing
                    DocumentBuilderFactory dbf = DocumentBuilderFactory
                            .newInstance();
                    DocumentBuilder db = dbf.newDocumentBuilder();

                    //using db (Document Builder) parse xml data and assign
                    //it to Element
                    Document document = db.parse(is);
                    Element element = document.getDocumentElement();

                    //take rss nodes to NodeList
                    NodeList nodeList = element.getElementsByTagName("item");

                    if (nodeList.getLength() > 0) {
                        for (int i = 0; i < nodeList.getLength(); i++) {

                            //take each entry (corresponds to <item></item> tags in
                            //xml data

                            Element entry = (Element) nodeList.item(i);

                            Element _titleE = (Element) entry.getElementsByTagName("title").item(0);
                            Element _descriptionE = (Element) entry.getElementsByTagName("description").item(0);
                            Element _pubDateE = (Element) entry.getElementsByTagName("pubDate").item(0);
                            Element _linkE = (Element) entry.getElementsByTagName("link").item(0);

                            String _title = _titleE.getFirstChild().getNodeValue();
                            String _description = _descriptionE.getFirstChild().getNodeValue();
                            Date _pubDate = new Date(_pubDateE.getFirstChild().getNodeValue());
                            String _link = _linkE.getFirstChild().getNodeValue();

                            //create RssItemObject and add it to the ArrayList
                            RssItem rssItem = new RssItem(_title, _description,
                                                          _pubDate, _link);

                            rssItems.add(rssItem);
                        }
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                if(conn!=null)
                    conn.disconnect();
            }
            hasRss=true;
            return null;
        }
    }

    public static ArrayList<RssItem> getRssItems(String feedUrl) {

        rssItems = new ArrayList<RssItem>();
        try {
            hasRss=false;
            new RssConnection().execute(feedUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        while(!hasRss){

        }
        return rssItems;
    }
}
