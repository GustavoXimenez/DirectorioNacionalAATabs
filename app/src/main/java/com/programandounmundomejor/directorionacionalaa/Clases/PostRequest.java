package com.programandounmundomejor.directorionacionalaa.Clases;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import static com.programandounmundomejor.directorionacionalaa.Clases.Global.system_WServices;

public class PostRequest {
    public String enviarPost(String params, String servicio){
        HttpURLConnection connection = null;
        String response = "";
        try {
            URL url = new URL(system_WServices + servicio);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Length",""+Integer.toString(params.getBytes("UTF-8").length));
            connection.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(params);
            wr.close();

            Scanner inStream = new Scanner(connection.getInputStream());

            while (inStream.hasNextLine()){
                response += inStream.nextLine();
            }
        } catch (Exception e){
            e.getMessage();
        }
        return response;
    }
}
