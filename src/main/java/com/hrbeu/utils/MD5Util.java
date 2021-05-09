package com.hrbeu.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    public static String md5(String password){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            int i;
            StringBuffer buffer = new StringBuffer("");
            for(int offset=0;offset<bytes.length;offset++){
                i = bytes[offset];
                if(i<0){
                    i+=256;
                }
                if(i<16){
                    buffer.append("0");
                }
                buffer.append(Integer.toHexString(i));
            }
            return buffer.toString();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            return null;
        }
    }

}
