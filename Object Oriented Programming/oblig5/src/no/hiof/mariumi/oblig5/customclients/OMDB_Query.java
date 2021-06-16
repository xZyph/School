package no.hiof.mariumi.oblig5.customclients;

import no.hiof.mariumi.oblig5.ExecMain;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 *
 */
public class OMDB_Query {
    public static final String OMDB_URL = "http://www.omdbapi.com/?s=TITLE&type=movie&apikey=APIKEY";
    public static final String IMDB_URL = "http://www.omdbapi.com/?i=ID&apikey=APIKEY";
    public static final String API_KEY_OMDB = "e5b2f10e";

    /**
     *
     * @param imdb
     * @return
     */
    public static String fetchMovie(String imdb) {
        String requestURL = IMDB_URL
                .replaceAll("ID", imdb)
                .replaceAll("APIKEY", API_KEY_OMDB);

        return sendGetRequest(requestURL);
    }

    /**
     *
     * @param title
     * @return
     */
    public static String searchMovieByTitle(String title) {
        try {
            title = URLEncoder.encode(title, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            new Error("Wow, a device that doesn't support UTF-8 o.O");
        }

        String requestURL = OMDB_URL
                .replaceAll("TITLE", title)
                .replaceAll("APIKEY", API_KEY_OMDB);

        return sendGetRequest(requestURL);
    }

    /**
     *
     * @param requestURL
     * @return
     */
    public static String sendGetRequest(String requestURL) {
        StringBuffer response = new StringBuffer();

        try {
            URL url = new URL(requestURL);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setRequestProperty("Accept", "*/*");
            c.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            InputStream s = c.getInputStream();
            InputStreamReader r = new InputStreamReader(s);
            BufferedReader b = new BufferedReader(r);

            String line;
            while ((line = b.readLine()) != null) {
                response.append(line);
            }

            b.close();
            c.disconnect();
        }

        catch (IOException ioe) {
            ExecMain.prodSys.showError(ioe.getLocalizedMessage());
        }

        return response.toString();
    }

    public static boolean isOnline() {
        boolean status = false;

        try {
            new URL("http://www.omdbapi.com").openConnection().connect();
            status = true;
        }
        catch (IOException offline) {
            // Means offline. Do nothing.
        }

        return status;
    }
}
