package com.yazilimciakli.oneway.Utils;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeUTF(data);
        objectOutputStream.flush();
        objectOutputStream.close();
        fileOutputStream.close();
    }

    public String read() throws IOException {
        FileInputStream fileInputStream = context.openFileInput(fileName);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        String data = objectInputStream.readUTF();
        objectInputStream.close();
        return data;
    }

    public FileInputStream getInputStream() throws FileNotFoundException {
        return context.openFileInput(fileName);
    }
}