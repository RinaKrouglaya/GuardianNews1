package com.example.pc.guardiannews1;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods related to requesting and receiving newsItem data from The Guardian Open Platform.
 */
public final class QueryUtils {

    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Query the Guardian Open Platform and return a list of {@link NewsItem} objects.
     */
    public static List<NewsItem> fetchNewsItemData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link NewsItem}s
        List<NewsItem> newsItems = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link NewsItem}s
        return newsItems;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the newsItem JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader =
                    new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link NewsItem} objects that has been built up from
     * parsing the given JSON response.
     */
    private static List<NewsItem> extractFeatureFromJson(String newsItemJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(newsItemJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding newsItems to
        List<NewsItem> newsItems = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the root of the JSON response string
            JSONObject rootObject = new JSONObject(newsItemJSON);

            // Create a JSONObject from JSON response from the value of the key "response" of the API
            JSONObject responseObject = rootObject.getJSONObject("response");

            //In the variable resultsArray gets the JSONArray results stored
            JSONArray resultsArray = responseObject.getJSONArray("results");


            // Creates a for loop that goes through the arraylist of news
            for (int i = 0; i < resultsArray.length(); i++) {

                // Get a single newsItem at position index within the list of news
                JSONObject newsObject = resultsArray.getJSONObject(i);

                // Extract the value for the key called "sectionName"
                String section = newsObject.getString("sectionName");

                // Extract the value for the key called "webTitle"
                String title = newsObject.getString("webTitle");


                // Extract the value for the key called "webPublicationDate"
                String webPublicationDate = newsObject.getString("webPublicationDate");

                // Extract the value for the key called "webUrl"
                String url = newsObject.getString("webUrl");

                //In the variable tagArray gets the JSONArray "tags" stored
                JSONArray tagArray = newsObject.getJSONArray("tags");


                //creates an if statement that an author is shown when the tagArray is >=1
                if (tagArray.length() >= 1) {
                    // this creates a for loop that goes through the arrayList of tags
                    for (int j = 0; j < 1; j++) {

                        // Get a single tagsObject in the  tagArray
                        JSONObject tagsObject = tagArray.getJSONObject(j);
                        // Extract the value for the key called "webTitle"
                        String author = tagsObject.getString("webTitle");


                        // Create a new {@link NewsItem} object with the section, title,
                        // webPublicationDate, and url and author from the JSON response.
                        NewsItem newsItemObject =
                                new NewsItem(title, section, author, webPublicationDate, url);
                        newsItems.add(newsItemObject);

                    }
                }
            }


        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the newsItem JSON results", e);
        }

        // Return the list of newsItems
        return newsItems;
    }

}
