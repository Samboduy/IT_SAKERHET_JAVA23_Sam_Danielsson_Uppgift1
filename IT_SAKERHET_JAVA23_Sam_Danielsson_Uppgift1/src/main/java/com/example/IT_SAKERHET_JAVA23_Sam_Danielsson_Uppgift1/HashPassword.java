package com.example.IT_SAKERHET_JAVA23_Sam_Danielsson_Uppgift1;

import java.security.MessageDigest;

public class HashPassword {

    public String generateHash(String password){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            String hashedPassword =  bytesToHex(hash);
            return hashedPassword;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    private   String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for(byte b : bytes){
            String hex = Integer.toHexString(0xff & b);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
