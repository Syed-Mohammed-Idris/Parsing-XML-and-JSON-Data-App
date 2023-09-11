package com.example.prog6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class ViewActivity extends AppCompatActivity {

    int mode;

    TextView xmlPlaceHolder,jsonPlaceHolder,xmlHeading,jsonHeading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        mode = getIntent().getIntExtra("mode",0);

        xmlPlaceHolder = findViewById(R.id.xmlPlaceHolder);
        jsonPlaceHolder = findViewById(R.id.jsonPlaceHolder);
        xmlHeading = findViewById(R.id.xmlHeading);
        jsonHeading = findViewById(R.id.jsonHeading);



        if(mode==1){
            parseXML();
        }



        if(mode==2){
            parseJSON();
        }

    }

    // read the file --> inputStream

    // array = inputStream

    // String(array)


    public void parseJSON(){

        xmlPlaceHolder.setText("");
        xmlHeading.setText("");

        String stringData=null;

        try {

            InputStream inputStream = getAssets().open("input.json");

            int size = inputStream.available();

            byte buffer[] = new byte[size];

            inputStream.read(buffer);

            stringData = new String(buffer);

            Log.e("data", "parseJSON: "+stringData );

            JSONObject jsonObject = new JSONObject(stringData);

            Log.e("data", "parseJSON: "+(jsonObject));
            Log.e("data", "parseJSON: "+jsonObject.getClass().getName());

            JSONObject cityObject = jsonObject.getJSONObject("City");

            String cityName = cityObject.getString("City-Name");
            String logintude = cityObject.getString("Longitude");
            String latitude = cityObject.getString("Latitude");
            String temperature = cityObject.getString("Temperature");
            String humidity = cityObject.getString("Humidity");

            Log.e("data", "parseJSON: "+ cityName );

            jsonPlaceHolder.setText("City-Name - "+cityName+"\n\n");

            jsonPlaceHolder.append("Longitude - "+logintude+"\n\n");
            jsonPlaceHolder.append("Latitude - "+latitude+"\n\n");
            jsonPlaceHolder.append("Temperature - " +temperature+"\n\n");
            jsonPlaceHolder.append("Humidity - "+humidity+"\n\n");


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void parseXML(){

        xmlPlaceHolder.setText(" ");

        jsonPlaceHolder.setText(" ");

        jsonHeading.setText(" ");




        try {
            InputStream inputStream = getAssets().open("input.xml");

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document document = documentBuilder.parse(inputStream);

            NodeList cityList = document.getElementsByTagName("City");

            for(int i=0; i<cityList.getLength();i++){

                Node c = cityList.item(i);

                if(c.getNodeType() == Node.ELEMENT_NODE){
                    Element city = (Element) c;

                   // String id = city.getAttribute("id");
                   // Log.e("data", "parseXML: "+id );

                    NodeList cityDetailList = city.getChildNodes();

                    for(int j=0;j<cityDetailList.getLength();j++) {
                        Node n = cityDetailList.item(j);

                        if (n.getNodeType() == Node.ELEMENT_NODE) {
                            Element cityDetail = (Element) n;


                            String tagValue = cityDetail.getTagName();
                            String value = cityDetail.getTextContent();


                            Log.e("data", "parseXML: " + value);
                            xmlPlaceHolder.append(tagValue +" - "+value + "\n");
                            xmlPlaceHolder.append("\n");

                        }
                    }
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }


    }

}