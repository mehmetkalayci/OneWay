package com.yazilimciakli.oneway.Utils;

import android.content.Context;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileHelper {

    private String fileName = "levels.xml";
    private Context context;
    public FileHelper(Context context){
        this.context = context;
    }
    public void write(String data) throws IOException {
        FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
        BufferedOutputStream bos = new BufferedOutputStream( fileOutputStream);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(bos);
        int count=(int) Math.ceil(data.length()/5000);

        int begin;
        int end;

        for(int i=0;i<=count;i++)
        {
            if(i==0)
            {
                //ilk döngüde gereksiz boşluk ve gereksiz karakterleri atlamak için
                begin=3;
            }
            else
            {
                //diğer döngülerde döngü sayısının n sayısının 1 fazlası ile başlaması
                begin=(i*5000)+1;
            }

            if(i==count)
            {
                //Son döngüyü data uzunluğu ile bitirme
                end=data.length();
            }
            else
            {
                //diğer döngülerde döngü sayının bir fazlasının n katı
                end=(i+1)*5000;
            }

            /*
             * begin 3 end 5000
             * begin 5001 end 10000
             * begin 10001 end 15000 olarak devam eder.
             * */

            // String bölümleme işlemi yapılması
            String content=data.substring(begin,end);


        }
        //byte [] b=data.getBytes("UTF-8");
        //Stringin nesnenin sonuna eklenmesi (append işlemi olması gerekir şuan çalışmıyor)
        //objectOutputStream.write(b);
        objectOutputStream.flush();
        objectOutputStream.close();
        fileOutputStream.close();
    }
    /*public String read ()  throws IOException {
        StringBuffer datax = new StringBuffer("");
        FileInputStream fIn = context.openFileInput (fileName) ;
        InputStreamReader isr = new InputStreamReader ( fIn ) ;
        BufferedReader buffreader = new BufferedReader ( isr ) ;
        String readString = buffreader.readLine();
        while ( readString != null ) {
            datax.append(readString);
            readString = buffreader.readLine() ;
        }

        isr.close();
        return datax.toString() ;
    }*/

    public String read() throws IOException {
        FileInputStream fileInputStream = context.openFileInput(fileName);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        String data=objectInputStream.readUTF();

        objectInputStream.close();
        return data.toString();
    }

    public InputStream getInputStream() throws FileNotFoundException {
        return context.openFileInput(fileName);
    }
}