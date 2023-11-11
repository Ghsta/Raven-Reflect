package zen.relife.util;

import zen.relife.util.asm.B;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class A {
    public String hwidVer = "NULL";

    public A() throws NoSuchAlgorithmException, IOException {

        try {
            System.out.println("???IISIiiIIiIAIVGIAIIIiIIAIIDIAIIAIAIIAIAIAIIA??");

            if (dsbgoLDGBILS("https://gitcode.net/relife/relife1.12.2/raw/master/IiiIIIIiIIiIIIiIIIIiIiiIIIIIIIIiiiIIIii.txt").contains(GfwdsFsfnHUIB()))
                System.out.println("?????");
            else {
                JOptionPane.showMessageDialog(null, "???????IIIIIIIIIIIIIIIIiiiiIIIFIiiiiiIIIIiI???? ", "?IIIIIIiiiIIDIIIDIIIII", JOptionPane.ERROR_MESSAGE);
                Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
                Transferable tText = new StringSelection(GfwdsFsfnHUIB());
                clip.setContents(tText, null);
                new B().FUCKYOU();
                new B().WhatFUCK();
                System.exit(114514);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String dsbgoLDGBILS(String url) throws IOException {
        String inputLine;
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
            response.append("\n");
        }
        in.close();
        return response.toString();
    }

    private static String GfwdsFsfnHUIB() {
        StringBuffer hexString = new StringBuffer();
        try {
            String toEncrypt = System.getenv("COMPUTERNAME") + System.getProperty("user.name") + System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("PROCESSOR_LEVEL");
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(toEncrypt.getBytes());


            byte[] byteData = md.digest();

            for (byte aByteData : byteData) {
                String hex = Integer.toHexString(0xff & aByteData);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }


        } catch (Exception e) {
            e.printStackTrace();
            System.exit(114514);

        }
        return hexString.toString();
    }
}
