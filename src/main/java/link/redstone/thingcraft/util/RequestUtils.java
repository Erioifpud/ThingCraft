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

    public static HttpURLConnection getHttpConnection(String url, String method) {
        HttpURLConnection conn = null;
        try {
            URL uri = new URL(url);
            conn = (HttpURLConnection) uri.openConnection();
            conn.setRequestMethod(method);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setRequestProperty("Host", "api.thingspeak.com");
            conn.setRequestProperty("Connection", "close");
            conn.setRequestProperty("User-Agent", "Paw/2.3.1 (Macintosh; OS X/10.11.4) GCDHTTPRequest");
        } catch (Exception ex) {
            ChatUtils.error(ex.toString());
            ex.printStackTrace();
        }
        return conn;
    }

    public static String http(String url, String method, String body) throws IOException {
        HttpURLConnection conn = getHttpConnection(url, method);
        if (body != null) {
            conn.setDoInput(true);
            conn.setDoOutput(true);
            DataOutputStream output = new DataOutputStream(conn.getOutputStream());
            output.writeBytes(body);
            output.flush();
            output.close();
        }
        conn.connect();
        BufferedReader input = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuffer buffer = new StringBuffer();
        for (String line; (line = input.readLine()) != null; ) {
            buffer.append(line).append("\n");
        }
        input.close();
        return buffer.toString();
    }
}
