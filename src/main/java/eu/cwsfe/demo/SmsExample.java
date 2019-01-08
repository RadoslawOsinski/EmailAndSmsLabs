package eu.cwsfe.demo;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.time.Duration;
import java.util.Base64;

/**
 * Sending sms via SmsLabs
 *
 * @see <a href="https://smslabs.pl/">SmsLabs</a>
 * <p>
 * Created by Radosław Osiński
 */
public class SmsExample {

    public static void main(String[] args) throws IOException, InterruptedException {
        sendTestSms();
    }

    private static void sendTestSms() throws IOException, InterruptedException {
        String appKey = "f244...."; //app key z strony
        String secretKey = "4d1....";   //secret key z strony
        String receiverAddress = "+48111222333";    //numer telefonu  z strony
        String senderAddress = "SMS TEST";  //nagłówek tekstowy z strony
        String msg = "Dzień doberek od Raduś. ĄĘĆŹŃŁÓ 123.";    //wysyłana wiadomość

        String forEncoding = appKey + ":" + secretKey;
        String authToBase64 = Base64.getEncoder().encodeToString(forEncoding.getBytes());

        HttpClient client = HttpClient.newBuilder().build();

        Charset utf8 = Charset.forName("UTF-8");
        String query =
            "expiration=0" +
            "&phone_number=" + URLEncoder.encode(receiverAddress, utf8) +
            "&message=" + URLEncoder.encode(msg, utf8) +
            "&sender_id=" + URLEncoder.encode(senderAddress, utf8) +
            "&flash=0";

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.smslabs.net.pl/apiSms/sendSms/"))
            .PUT(HttpRequest.BodyPublishers.ofString(query))
            .timeout(Duration.ofSeconds(10))
            .header("Authorization", "Basic " + authToBase64)
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("STATUS CODE: " + response.statusCode());
        System.out.println("BODY: " + response.body());
    }

}
