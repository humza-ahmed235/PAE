package org.pacadengg.pae;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class Home extends AppCompatActivity {
private static String paragraph = "";
private TextView announcementNewsText;
public static Document doc = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar my_toolbar = findViewById(R.id.toolbarHome);
        setSupportActionBar(my_toolbar);


        new headlines().execute();


    }

    public  void getAnnouncementNews()  {
        Document doc = null;
        try {

            doc = Jsoup.connect("http://www.pacadengg.org/index.html").userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements ec = doc.select("div.col-2 p");
            for (Element e : ec) {

                String oneannounnews = e.select("p").text();// Stores each news piece and date piece
                paragraph = paragraph + oneannounnews + "\n";
            }

        announcementNewsText = (TextView) findViewById(R.id.announcementNewsText);
        announcementNewsText.setText(paragraph);

        }


        private class headlines extends AsyncTask<Void,Void,Void>{

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... voids) {




                Document doc = null;
                try {

                    doc = Jsoup.connect("https://www.pacadengg.org/index.html").get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Elements ec = doc.select("div.col-2 p");
                for (Element e : ec) {

                    String oneannounnews = e.select("p").text();// Stores each news piece and date piece
                    paragraph = paragraph + oneannounnews + "\n";

                }
                System.out.println(paragraph);


                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                announcementNewsText = (TextView) findViewById(R.id.announcementNewsText);
                announcementNewsText.setMovementMethod(new ScrollingMovementMethod());
                announcementNewsText.setText(paragraph);
            }
        }








    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       int theID = item.getItemId();

       if(theID == R.id.home)
       { Intent intent = new Intent(this,Home.class);
           startActivity(intent);
           return true;
       }
       else if(theID == R.id.symposiums)
        { Intent intent = new Intent(this,Symposiums.class);
            startActivity(intent);
            return true;
        }

       else if(theID == R.id.fellows)
        { Intent intent = new Intent(this,Fellows.class);
            startActivity(intent);
            return true;
        }

       else if(theID == R.id.contactUs)
       { Intent intent = new Intent(this,ContactUs.class);
           startActivity(intent);
           return true;
       }


        else {return super.onOptionsItemSelected(item);}
    }
}


