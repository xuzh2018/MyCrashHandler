package com.test.xzh.mytest.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.test.xzh.mytest.MyApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

/**
 * Created by xzh on 2017/3/14
 */

public class MyDataBase {
    private static String DATA_FILE_NAME = "data.db";
    private static MyDataBase myDataBase;
    File file = new File(MyApplication.getInstance().getFilesDir(), DATA_FILE_NAME);
    Gson gson = new Gson();

    private MyDataBase() {
    }

    public static MyDataBase getInstance() {
        if (myDataBase == null) {
            myDataBase = new MyDataBase();
        }
        return myDataBase;
    }

    public List<ZhuangbiImage> readLists() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Reader reader = new FileReader(file);
            return gson.fromJson(reader, new TypeToken<List<ZhuangbiImage>>(){}.getType());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }

    public void writeLists(List<ZhuangbiImage> list) {
        String json = gson.toJson(list);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
            try {
                Writer writer = new FileWriter(file);
                writer.write(json);
                writer.flush();

            } catch (IOException e) {
                e.printStackTrace();

            }



    }

    public void delete() {
        file.delete();
    }
}
