package com.example.pc.guardiannews1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextClock;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * {@link NewsItemAdapter} is an {@link ArrayAdapter} that can provide the layout for each list
 * item based on a data source, which is a list of {@link NewsItem} objects.
 */
public class NewsItemAdapter extends ArrayAdapter<NewsItem> {

    /**
     * The part of the date string from the JSON parsing that separates date from time.
     */
    private static final String DATE_STRING_SEPARATOR = "T";

    /**
     * The end part of the date string from the JSON parsing.
     */
    private static final String DATE_STRING_ENDING = "Z";

    /**
     * Create a new {@link NewsItemAdapter} object.
     *
     * @param context   is the current context (i.e. Activity) that the adapter is being created in.
     * @param newsItems is the list of {@link NewsItem}s to be displayed.
     */
    public NewsItemAdapter(Context context, ArrayList<NewsItem> newsItems) {
        super(context, 0, newsItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list_item, parent, false);
        }

        // Get the {@link NewsItem} object located at this position in the list
        NewsItem currentNewsItem = getItem(position);

        // Find the TextView in the news_list_item.xml layout with the ID section.
        TextView sectionTextView = listItemView.findViewById(R.id.section);

        // Get the section name from the currentNewsItem object and set this text on
        // the section TextView.
        sectionTextView.setText(currentNewsItem.getSection());

        // Find the TextView in the news_list_item.xml layout with the ID title.
        TextView titleTextView = listItemView.findViewById(R.id.title);

        // Get the title from the currentNewsItem object and set this text on
        // the title TextView.
        titleTextView.setText(currentNewsItem.getTitle());

        // Find the TextView in the news_list_item.xml layout with the ID author.
        TextView authorTextView = listItemView.findViewById(R.id.author);

        //Check whether or not there is an author for this NewsItem.
        if (currentNewsItem.hasAuthor()) {
            // Get the author from the currentNewsItem object and set this text on
            // the author TextView.

            authorTextView.setText(currentNewsItem.getAuthor());
        } else {
            // Find the TextView in the news_list_item.xml layout with the ID by.
            TextView byTextView = listItemView.findViewById(R.id.by);

            //In case there is no author, the field is blank
            authorTextView.setText("");
            byTextView.setText("");
        }

        //Call method to display date and time
        //Check whether or not there is an date information for this NewsItem.
        if (currentNewsItem.hasWebPublicationDate()) {
            // Get the original webPublicationDate string from the NewsItem object,
            // which can be in the format of "2018-06-28T15:13:36Z"
            String originalWebPublicationDate = currentNewsItem.getWebPublicationDate();

            /*
             * Split the string into different parts (as an array of Strings)
             * based on the " T " text. We expect an array of 2 Strings, where
             * the first String will be date and the second String will be time
             * "2018-06-27" and  "05:46:02".
             */
            String webPublicationDateMinusZ =
                    originalWebPublicationDate.replaceAll(DATE_STRING_ENDING, "");
            String[] parts = webPublicationDateMinusZ.split(DATE_STRING_SEPARATOR);

            String webPublicationDate;
            String webPublicationTime;
            // First part - date
            webPublicationDate = parts[0];
            // Second part - time
            webPublicationTime = parts[1];

            // Find the TextView with view ID date
            TextView dateView = listItemView.findViewById(R.id.date);

            // Display the date of the current newsItem in that TextView
            dateView.setText(webPublicationDate);


            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
                // Find the TextClock with view ID time
                TextClock textClockView = listItemView.findViewById(R.id.time);
                textClockView.setFormat12Hour(null);
                // Display the time of the current newsItem in that TextClock
                textClockView.setText(webPublicationTime);
            } else {
                // Find the TextView with view ID time
                TextView timeTextView = listItemView.findViewById(R.id.time);

                // Display the time of the current newsItem in that TextView
                timeTextView.setText(webPublicationTime);
            }
        } else {
            //In case there is no date information provided, leave the fields blank

            // Find the TextView with view ID date
            TextView dateView = listItemView.findViewById(R.id.date);
            dateView.setText("");


            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
                // Find the TextClock with view ID time
                TextClock textClockView = listItemView.findViewById(R.id.time);

                // Display the time of the current newsItem in that TextClock
                textClockView.setText("");
            } else {
                // Find the TextView with view ID time
                TextView timeTextView = listItemView.findViewById(R.id.time);

                // Display the time of the current newsItem in that TextView
                timeTextView.setText("");
            }

        }
        // Return the whole list item layout so that it can be shown in
        // the ListView.
        return listItemView;
    }


}