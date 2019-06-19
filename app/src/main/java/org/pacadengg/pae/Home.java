package org.pacadengg.pae;

import android.graphics.pdf.PdfDocument;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import android.widget.EditText;
import android.widget.TextView;

public class Home extends AppCompatActivity {
public static String paragraph = "";
private TextView announcementNewsTExt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

    new Content().execute();

    }

    public  void getAnnouncementNews()  {
        Document doc = null;
        try {

            doc = Jsoup.connect("http://www.pacadengg.org/index.html").userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36").get();
            Log.d("Chk","work");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("catchy","cutter");
        }

            System.out.println("Announcement and news"); // Added to provide a combined heading as i cant at the moment separate

            Elements ec = doc.select("div.col-2 p");
            for (Element e : ec) {

                String oneannounnews = e.select("p").text();// Stores each news piece and date piece

                //System.out.println(oneannounnews);
                paragraph = paragraph + oneannounnews + "\n";


            }

        announcementNewsTExt = (TextView) findViewById(R.id.announcementNewsText);
        announcementNewsTExt.setText(paragraph);

        }








    private class Content extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            Document doc = null;
            try {
                doc = Jsoup.connect("https://www.pacadengg.org/index.html").get();

                System.out.println("Announcement and news"); // Added to provide a combined heading as i cant at the moment separate

                Elements ec = doc.select("div.col-2 p");
                for (Element e : ec) {

                    String oneannounnews = e.select("p").text();// Stores each news piece and date piece

                    //System.out.println(oneannounnews);
                    paragraph = paragraph + oneannounnews + "\n";
                }
                    announcementNewsTExt = (TextView) findViewById(R.id.announcementNewsText);
                    announcementNewsTExt.setText(paragraph);
            } catch (IOException e) {

                Log.d("isit" , "is");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


        }
    }

    }


