package ru.netology.NASA;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;


public class Main {
    public static ObjectMapper mapper = new ObjectMapper();
    public static final String APIKey = "TeGp5gRL1tfghSOv9yenJ0WJn7BzQB8LVf1IFsNO";
    public static final String url = "https://api.nasa.gov/planetary/apod?api_key=" + APIKey;

    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(3000)
                        .setSocketTimeout(50000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();
        HttpGet request = new HttpGet(url);
        CloseableHttpResponse response = httpClient.execute(request);
        InputStream ans = response.getEntity().getContent();
        AnswerNASA answer = mapper.readValue(ans, AnswerNASA.class);
        ans.close();
        String HDUrl = answer.getHdurl();
        System.out.println(HDUrl);
        CloseableHttpResponse respons2 = httpClient.execute(new HttpGet(HDUrl));
        byte[] data = respons2.getEntity().getContent().readAllBytes();
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        BufferedImage img = ImageIO.read(bis);
        ImageIO.write(img, "jpg", new File("pressdracowithspacecraft1_1.jpg"));
    }
}
