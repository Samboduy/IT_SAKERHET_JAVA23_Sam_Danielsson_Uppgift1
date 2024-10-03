
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Client {
    private static final String MY_URI = "http://localhost:8080/users/user/delete/";
    public static void main(String[] args) {
       // requestUserUsingEmail();
        //saveUser("sam.danielsson000@gmail.com","password","adress","23456");
        deleteUser();

    }
    private static void requestUserUsingEmail(){
        HttpClient httpClient = HttpClient.newHttpClient();
        try{

            HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(MY_URI)).GET().build();
            HttpResponse<String> httpResponse = httpClient.send(httpRequest,HttpResponse.BodyHandlers.ofString());
            JSONObject jsonObject = new JSONObject(httpResponse.body());
            System.out.println(jsonObject.getString("email"));
            System.out.println("json: " + jsonObject);

            System.out.println("Response: " + httpResponse.statusCode() + " " +  httpResponse.body());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    private static void saveUser(String email, String password, String adress,String postNumber){
        HttpClient httpClient = HttpClient.newHttpClient();

        Map<String, String> formData = new HashMap<>();
        formData.put("email", email);
        formData.put("password", password);
        formData.put("adress", adress);
        formData.put("postNumber", postNumber);

        /*Users user = new Users();
        user.setEmail(email);
        user.setPassword(password);
        if (!adress.trim().isEmpty() && !postNumber.trim().isEmpty()){
            user.setAdress(adress);
            user.setPostNumber(postNumber);
        }
        JSONObject jsonObject = new JSONObject(user);
        System.out.println("json: " + jsonObject);*/

        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(MY_URI)).
                header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(getFormDataAsString(formData)))
                .build();

        try{
            HttpResponse<String> response = httpClient.send(httpRequest,HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
          //  System.out.println(jsonObject.getString("email"));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    private static void deleteUser(){
        long userId = 2; // ID of the user to delete
        // Update
        try{
            URL url = new URL("http://localhost:8080/users/user/delete/" + userId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                System.out.println("User deleted successfully.");
            } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                System.out.println("User not found.");
            } else {
                System.out.println("Failed to delete user: " + responseCode);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    private static String getFormDataAsString(Map<String, String> formData) {
        StringBuilder formBodyBuilder = new StringBuilder();
        for (Map.Entry<String, String> singleEntry : formData.entrySet()) {
            if (!formBodyBuilder.isEmpty()) {
                formBodyBuilder.append("&");
            }
            formBodyBuilder.append(URLEncoder.encode(singleEntry.getKey(), StandardCharsets.UTF_8));
            formBodyBuilder.append("=");
            formBodyBuilder.append(URLEncoder.encode(singleEntry.getValue(), StandardCharsets.UTF_8));
        }
        return formBodyBuilder.toString();
    }
}