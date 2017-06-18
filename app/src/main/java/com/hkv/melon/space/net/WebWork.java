package com.hkv.melon.space.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebWork {
    static public String connect(String name, int searchType) {
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        String resultLink = "https://restcountries.eu/rest/v2/";

        try {
            if (searchType == 0) {
                resultLink += "region/" + name;
                connection = (HttpURLConnection) (new URL(resultLink).openConnection());
            }
            if (searchType == 1){
                resultLink += "capital/" + name;
                connection = (HttpURLConnection) (new URL(resultLink).openConnection());
            }

            if (searchType == 2){
                resultLink += "name/" + name;
                connection = (HttpURLConnection) (new URL(resultLink).openConnection());
            }

            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);
            connection.setRequestMethod("GET");
            connection.connect();

            StringBuffer stringBuffer = new StringBuffer();
            inputStream = connection.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line + "\r\n");
            }

            inputStream.close();
            connection.disconnect();

            return stringBuffer.toString();
        } catch (IOException e) {
            return null;
        }
    }
}
