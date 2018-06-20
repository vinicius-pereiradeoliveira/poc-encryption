package com.secret.poc.util;

import java.security.Key;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

import org.springframework.stereotype.Component;

import com.google.gson.GsonBuilder;
import com.secret.poc.dto.CustomerData;

@Component
public class EncryptionUtil {
	
	//Applies Sha256 to a string and returns the result. 
		public static String applySha256(String input){
			
			try {
				MessageDigest digest = MessageDigest.getInstance("SHA-256");
		        
				//Applies sha256 to our input, 
				byte[] hash = digest.digest(input.getBytes("UTF-8"));
		        
				StringBuffer hexString = new StringBuffer(); // This will contain hash as hexidecimal
				for (int i = 0; i < hash.length; i++) {
					String hex = Integer.toHexString(0xff & hash[i]);
					if(hex.length() == 1) hexString.append('0');
					hexString.append(hex);
				}
				return hexString.toString();
			}
			catch(Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		//Applies ECDSA Signature and returns the result ( as bytes ).
		public static byte[] applyECDSASig(PrivateKey privateKey, String input) {
			Signature dsa;
			byte[] output = new byte[0];
			try {
				dsa = Signature.getInstance("ECDSA", "BC");
				dsa.initSign(privateKey);
				byte[] strInfoByte =input.getBytes();
				dsa.update(strInfoByte);
				byte[] realSig = dsa.sign();
				output = realSig;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			return output;
		}
		
		//Verifies a String signature 
		public static boolean verifyECDSASig(PublicKey publicKey, String data, byte[] signature) {
			try {
				Signature ecdsaVerify = Signature.getInstance("ECDSA", "BC");
				ecdsaVerify.initVerify(publicKey);
				ecdsaVerify.update(data.getBytes());
				return ecdsaVerify.verify(signature);
			}catch(Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		public static String getStringFromKey(Key key) {
			String encoder = Base64.getEncoder().encodeToString(key.getEncoded());
			System.out.println("=== Converte a Chave para String ===> "+encoder);
			return encoder;
		}
		
		//Short hand helper to turn Object into a json string
		public static String getJson(Object o) {
			return new GsonBuilder().setPrettyPrinting().create().toJson(o);
		}
}
