import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class RestClient {

    public static String readUrl(String urlString) throws IOException {
        BufferedReader reader=null;
        try {
            URL url= new URL(urlString);
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            int read;
            StringBuffer buffer = new StringBuffer();
            char [] chars = new char[1024];
            while ((read =reader.read(chars)) != -1) {
                buffer.append(chars,0,read);
            }
            return buffer.toString();
        }
        finally {
            if(reader!=null) {
                reader.close();
            }
        }
    }
}
