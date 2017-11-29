package com.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;

public class AddressBean {

    private final String address;

    public AddressBean(String address) {
        String addressLines[] = address.split(",");   // Splitting up the address with a comma
        String theAddress = "";
        for (String theLine : addressLines) {
            if (theLine.length() > 0 && (!theLine.isEmpty()) && (!theLine.equals(" "))) { // Error checking. Making sure the line isn't empty etc.
                theAddress += theLine + ", ";    //Adds the string and then a comma
            }
        }

        if (theAddress.endsWith(", ")) {

            theAddress = theAddress.substring(0, theAddress.length() - 2); // Trims addresses with empty values at the end
        }
        this.address = theAddress;
    }

    @Override
    public String toString() {
        return this.address;
    }
}
