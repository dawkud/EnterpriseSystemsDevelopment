package com.services;

import com.models.AddressBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class AddressAPI {

    private final String key = "5URYd92xYkuFcF1EMka0yg11150";             // Our API key
    private final String api = "https://api.getAddress.io/find/";         //the API address. Gotta add the postcode to the end.
    private String pCode;

    public AddressAPI(String pCode) {
        this.pCode = pCode;
    }

    public ArrayList<AddressBean> findAddress() {
        ArrayList<AddressBean> addresses = new ArrayList<>();

        try {
            pCode = pCode.replace(" ", ""); // takes care of any whitespace
            URL theURL = new URL(api + URLEncoder.encode(pCode, "UTF-8")); // Creates new URL using the postcode and the API address
            HttpURLConnection httpCON = (HttpURLConnection) theURL.openConnection();

            httpCON.setRequestMethod("GET");
            httpCON.setRequestProperty("API-KEY", key); // required for authentication

            if (httpCON.getResponseCode() == 200) {    // 200 is a successful response. A code over 400 tends to be errors
                StringBuffer resp;
                try ( 
                        BufferedReader input = new BufferedReader(new InputStreamReader(httpCON.getInputStream()))) {
                    String currentLine;
                    resp = new StringBuffer();
                    while ((currentLine = input.readLine()) != null) {
                        resp.append(currentLine);
                    }
                }

                System.out.println("Unformatted data from the API " + resp.toString());

                JSONObject jObject = (JSONObject) (new JSONParser()).parse(resp.toString());
                JSONArray jAddresses = (JSONArray) jObject.get("addresses");

                for (Object temp : jAddresses) {
                    String address = temp + ", " + pCode;
                    addresses.add(new AddressBean(address));
                }
            } else {
                System.out.println("API error, Could not find any addresses");
            }
        } catch (IOException | ParseException e) {
            System.out.println("Could not find any addresses");

        }
        return addresses;
    }
    public static void main(String[] args) {
        AddressAPI x = new AddressAPI("BS57SS/158 Speedwell Road");
        for (AddressBean findAddress : x.findAddress()) {
            System.out.println(findAddress.toString());
        }
    }
    
}