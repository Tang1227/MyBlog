package com.cn.blog.handle;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5util {
    public  static String code(String str){
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(str.getBytes());
            byte[] digest = m.digest();
            int i;
            StringBuffer s = new StringBuffer("");
            for(int offset=0;offset<digest.length;offset++){
                i = digest[offset];
                if(i<0){
                    i+=256;
                }
                if(i<16){
                    s.append("0");
                }
                s.append(Integer.toHexString(i));
            }
            return  s.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return  null;
        }
    }

    public static void main(String[] args) {
        System.out.println(MD5util.code("111"));
    }
}
