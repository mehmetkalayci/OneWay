package com.yazilimciakli.oneway.Utils;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;

public class FileHelper {

    private String fileName = "levels.xml";
    private Context context;

    public FileHelper(Context context){
        this.context = context;
    }

    public void write(String data) throws IOException {
        FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
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

            //Stringin nesnenin sonuna eklenmesi (append işlemi olması gerekir şuan çalışmıyor)
            objectOutputStream.writeUTF(content);

        }
        objectOutputStream.flush();
        objectOutputStream.close();
        fileOutputStream.close();
    }
    public String read() throws IOException {
        File sdcard = context.getFilesDir();
        File file = new File(sdcard,fileName);
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file), "UTF-8"));
            String line;
                Log.d("adss", "read: ");
            while ((line = br.readLine()) != null) {
                text.append(String.valueOf(line));
            }
            br.close();
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
        }

        /*
        File file=new File(context.getFilesDir(), fileName);
        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        StringBuffer xxx=new StringBuffer();
        int size = (int)file.length();
        int read = 0;
        byte data[] = new byte[size];
        while (read > -1) {
            xxx.append(objectInputStream.readUTF());
            read = (byte) objectInputStream.read(data, read, size-read);
        }

        objectInputStream.close();*/
        return text.toString();
    }

    public InputStream getInputStream() throws FileNotFoundException {
        return context.openFileInput(fileName);
    }
}