// Katherine Miao
// UID 204970866

import java.util.*;
import java.security.*;
import java.io.*;

public class ComputeSHA {
	public static void main(String args[]){

        if (args.length != 1) {
            System.out.println("ERROR: ComputeSHA takes <filename> argument.");
            return;
        }

        File f;
        InputStream inputstream;
        MessageDigest md;

        try {
            // access input file
            f = new File(args[0]);
            inputstream = new FileInputStream(f);
            md = MessageDigest.getInstance("SHA-1");
        } catch (FileNotFoundException e){
            System.out.println("ERROR: failed finding " + args[0]);
            return;
        } catch (NoSuchAlgorithmException e) {
            System.out.println("ERROR: SHA-1 failed");
            return;
        } 

        // use a buffer to read faster, otherwise default reads one byte at a time
        byte[] buffer = new byte[10000];
        byte[] digested;
        
        try {
            // read in file 10000 bits at a time
            int data = 0;
            while (data != -1) {
                data = inputstream.read(buffer);
                if (data > 0) {
                    md.update(buffer, 0, data);
                }
            }
            inputstream.close();
            digested = md.digest();
        } catch (IOException e) {
            System.out.println("ERROR: failed reading " + args[0]);
            return;
        }

        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < digested.length; i++) {   
            // cast longer with & 0xFF, extend with 0x100 for leading zeros
            int casted = (0xFF & digested[i]) | 0x100;
            // use Integer.toString(int i, int radix) with radix=16 for hexadecimal
            hexString.append(Integer.toString(casted, 16).substring(1));
        }
        System.out.println(hexString.toString());            
	}
}
