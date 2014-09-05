package no.clap.assignment1;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import android.util.Log;

// Great source and good help with the XML handle: http://www.tutorialspoint.com/android/android_xml_parsers.htm

public class HandleXML {

    private String temperature = "temperature";
    private String wind = "wind";
    private String urlString = null;
    private XmlPullParserFactory xmlFactoryObject;
    public volatile boolean parsingComplete = true;
    public HandleXML(String url){
        this.urlString = url;
    }
    public String getTemperature(){
        return temperature;
    }
    public String getWind(){
        return wind;
    }

    public void parseXMLAndStoreIt(XmlPullParser myParser) {
        int event;
        int i = 0, j = 0;
        String text = null;
        try {
            event = myParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                String name=myParser.getName();
                switch (event){
                    case XmlPullParser.START_TAG:
                        break;
                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if(name.equals("temperature")){
                            if (i == 0) {
                                temperature = myParser.getAttributeValue(null, "value");
                                i++;
                            }
                        }
                        if (name.equals("windSpeed")) {
                            if (j == 0) {
                                wind = myParser.getAttributeValue(null, "name");
                                j++;
                            }
                        }
                        break;
                }
                event = myParser.next();

            }
            parsingComplete = false;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void fetchXML(){
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection)
                            url.openConnection();
                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream stream = conn.getInputStream();

                    xmlFactoryObject = XmlPullParserFactory.newInstance();
                    XmlPullParser myparser = xmlFactoryObject.newPullParser();

                    myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES
                            , false);
                    myparser.setInput(stream, null);
                    parseXMLAndStoreIt(myparser);
                    stream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();


    }

}