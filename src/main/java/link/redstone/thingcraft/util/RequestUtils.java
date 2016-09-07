package link.redstone.thingcraft.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;

/**
 * Created by Erioifpud on 16/9/5.
 */
public class RequestUtils {

    public static String post(URL url, String content) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection(Proxy.NO_PROXY);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Host", "api.thingspeak.com");
        connection.setRequestProperty("Content-Length", "" + content.getBytes().length);
        connection.setRequestProperty("Connection", "close");
        connection.setRequestProperty("User-Agent", "Paw/2.3.1 (Macintosh; OS X/10.11.4) GCDHTTPRequest");
        connection.setUseCaches(false);
        connection.setDoInput(true);//guan jian
        connection.setDoOutput(true);
        DataOutputStream output = new DataOutputStream(connection.getOutputStream());
        output.writeBytes(content);
        output.flush();
        output.close();
        BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuffer buffer = new StringBuffer();
        for (String line; (line = input.readLine()) != null; ) {
            buffer.append(line);
            buffer.append("\n");
        }
        input.close();
        return buffer.toString();
    }

    public static String get(String url, String content) throws IOException {
        String urlNameString = url + "?" + content;
        URL newUrl = new URL(urlNameString);
        HttpURLConnection connection = (HttpURLConnection) newUrl.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Host", "api.thingspeak.com");
        connection.setRequestProperty("Content-Length", "" + content.getBytes().length);
        connection.setRequestProperty("Connection", "close");
        connection.setRequestProperty("User-Agent", "Paw/2.3.1 (Macintosh; OS X/10.11.4) GCDHTTPRequest");
        BufferedReader input =
                new BufferedReader(new InputStreamReader(
                        connection.getInputStream()));
        StringBuilder buffer = new StringBuilder();
        for (String line; (line = input.readLine()) != null; ) {
            buffer.append(line);
            buffer.append("\n");
        }
        input.close();
        return buffer.toString();
    }
}
