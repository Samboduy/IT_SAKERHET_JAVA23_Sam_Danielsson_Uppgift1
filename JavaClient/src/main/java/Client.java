
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
import java.util.Scanner;

public class Client {
    private static final String MY_URI = "http://localhost:8080";
    public static void main(String[] args) {
       // requestUserUsingEmail();
        //saveUser("sam.danielsson000@gmail.com","password","adress","23456");
        //deleteUser();

        boolean programIsRunning = true;
        Scanner scanner = new Scanner(System.in);
        while (programIsRunning){
            System.out.printf("1.Create a new user\n2.Show a user\n3.Delete a user\n4.Exit\n");
            String input = scanner.nextLine();
            switch (input){
                case "1": System.out.printf("Enter Email:");
                    String email = scanner.nextLine();
                    System.out.println("Enter Password:");
                    String password = scanner.nextLine();
                    System.out.println("Enter adress (We use it to send your packages to your home):");
                    String adress = scanner.nextLine();
                    System.out.println("Enter postNumber (We use it to send your packages to your mailbox):");
                    String postNumber = scanner.nextLine();
                    saveUser(email, password, adress, postNumber);
                break;
                case "2": System.out.printf("Enter Email:");
                String userEmail = scanner.nextLine();
                requestUserUsingEmail(userEmail);
                break;
                case "3": System.out.printf("Enter users id:");
                long userId = Long.parseLong(scanner.nextLine());
                    deleteUser(userId);
                break;
                case "4": programIsRunning = false;
            }
        }

    }
    private static void requestUserUsingEmail(String email){
        String requestUserURL = MY_URI + "/users/user/" + email;
        HttpClient httpClient = HttpClient.newHttpClient();
        try{

            HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(requestUserURL)).GET().build();
            HttpResponse<String> httpResponse = httpClient.send(httpRequest,HttpResponse.BodyHandlers.ofString());
            JSONObject jsonObject = new JSONObject(httpResponse.body());
            System.out.printf("email:%s,id:%d,password:%s,adress:%s,post number:%d"
                    ,jsonObject.getString("email")
                    ,jsonObject.getLong("id"),
                    jsonObject.getString("password"),
                    jsonObject.getString("adress"),
                    jsonObject.getLong("postNumber"));

           // System.out.println("Response: " + httpResponse.statusCode() + " " +  httpResponse.body());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    private static void saveUser(String email, String password, String adress,String postNumber){
        String createUserURL = MY_URI + "/createUser";
        HttpClient httpClient = HttpClient.newHttpClient();

        Map<String, String> formData = new HashMap<>();
        formData.put("email", email);
        formData.put("password", password);
        formData.put("adress", adress);
        formData.put("postNumber", postNumber);

        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(createUserURL)).
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

    private static void deleteUser(long userId){
        try{
            URL url = new URL("http://localhost:8080/users/user/delete/" + userId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("User deleted successfully.");
            } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                System.out.println("User not found.");
            } else {
                System.out.println("Failed to delete user: " + responseCode);
            }
            connection.disconnect();
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