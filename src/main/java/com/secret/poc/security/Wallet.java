package com.secret.poc.security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.ECGenParameterSpec;
import java.util.HashMap;
import java.util.Map;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Component;

@Component
public class Wallet {
	
	public PrivateKey privateKey;
	public PublicKey publicKey;
	public Map<PrivateKey, PublicKey> keys = new HashMap<>();
	
	public Wallet(){	
	}
		
	public Map<PrivateKey, PublicKey> generateKeyPair() {
		try {
			Security.addProvider(new BouncyCastleProvider());
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA","BC");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
			// Initialize the key generator and generate a KeyPair
			keyGen.initialize(ecSpec, random);   //256 bytes provides an acceptable security level
	        KeyPair keyPair = keyGen.generateKeyPair();
	        // Set the public and private keys from the keyPair
	        privateKey = keyPair.getPrivate();
	        publicKey = keyPair.getPublic();
	        keys.put(privateKey, publicKey);
	        return keys;
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
