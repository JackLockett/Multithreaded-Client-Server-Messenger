package com.company;

import org.json.simple.JSONObject;

import java.io.*;

public class FileManagement {

    public void createFile() {
        File file = new File("console.txt");
    }

    public void writeToFile(JSONObject obj)
    {
        try {
            FileWriter file = new FileWriter("console.txt", true);
            file.append(obj.toJSONString()).append("\n");
            file.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void readFromFile()
    {
        try {
            File file = new File("console.txt");
            if (file.exists()) {
                BufferedReader fileReader = new BufferedReader(new FileReader("console.txt"));
                String line;
                while ((line = fileReader.readLine()) != null) {
                    System.out.println(line);
                }
                fileReader.close();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
