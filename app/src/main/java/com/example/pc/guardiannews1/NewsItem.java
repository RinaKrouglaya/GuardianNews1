package com.example.pc.guardiannews1;


/**
 * An {@link  NewsItem} object contains information related to a single  NewsItem.
 */
public class NewsItem {

    /**
     * Constant value that represents no author was provided for this NewsItem
     */
    private static final String NO_AUTHOR_PROVIDED = "Null";

    /**
     * Constant value that represents no date was provided for this NewsItem
     */
    private static final String NO_DATE_PROVIDED = "Null";

    /**
     * Title of the  NewsItem
     */
    private String mTitle;

    /**
     * Section of the  NewsItem
     */
    private String mSection;

    /**
     * Author of the  NewsItem
     */
    private String mAuthor = NO_AUTHOR_PROVIDED;

    /**
     * Date of the  NewsItem
     */
    private String mWebPublicationDate = NO_DATE_PROVIDED;

    /**
     * Website URL of the  NewsItem
     */
    private String mUrl;


    /**
     * Constructs a new {@link  NewsItem} object with all the parameters.
     *
     * @param title              is the title  of the  NewsItem
     * @param section            is the section name of  the  NewsItem
     * @param author             is the author of the  NewsItem
     * @param webPublicationDate is the time  when the NewsItem issued
     * @param url                is the website URL to find more details about the  NewsItem
     */
    public NewsItem(String title, String section, String author, String webPublicationDate,
                    String url) {
        mTitle = title;
        mSection = section;
        mAuthor = author;
        mWebPublicationDate = webPublicationDate;
        mUrl = url;
    }

    /**
     * Constructs a new {@link  NewsItem} object without parameter author or date.
     *
     * @param title        is the title  of the  NewsItem
     * @param section      is the section name of  the  NewsItem
     * @param dateOrAuthor is the date  when the NewsItem issued or the author of the article
     * @param url          is the website URL to find more details about the  NewsItem
     */

    public NewsItem(String title, String section, String dateOrAuthor, String url) {
        mTitle = title;
        mSection = section;
        mUrl = url;

        char firstChar = dateOrAuthor.charAt(0);

        if (Character.isDigit(firstChar)) {
            mWebPublicationDate = dateOrAuthor;
        } else if (Character.isLetter(firstChar)) {
            mAuthor = dateOrAuthor;
        }
    }


    /**
     * Constructs a new {@link  NewsItem} object without parameters author
     * and webPublicationDate.
     *
     * @param title   is the title  of the  NewsItem
     * @param section is the section name of  the  NewsItem
     * @param url     is the website URL to find more details about the  NewsItem
     */
    public NewsItem(String title, String section, String url) {
        mTitle = title;
        mSection = section;
        mUrl = url;
    }

    /**
     * Returns the title of the  NewsItem.
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Returns the section name of the  NewsItem.
     */
    public String getSection() {
        return mSection;
    }

    /**
     * Returns the author name of the  NewsItem.
     */
    public String getAuthor() {
        return mAuthor;
    }


    /**
     * Returns the date of the  NewsItem.
     */
    public String getWebPublicationDate() {
        return mWebPublicationDate;
    }

    /**
     * Returns the website URL to find more information about the  NewsItem.
     */
    public String getUrl() {
        return mUrl;
    }


    /**
     * Returns whether or not there is an author for this NewsItem.
     */

    public boolean hasAuthor() {
        return !mAuthor.equals(NO_AUTHOR_PROVIDED);

    }


    /**
     * Returns whether or not there is an time provided for this NewsItem.
     */

    public boolean hasWebPublicationDate() {

        return !mWebPublicationDate.equals(NO_DATE_PROVIDED);
    }

}
