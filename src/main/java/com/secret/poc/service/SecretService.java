package com.secret.poc.service;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.secret.poc.dto.CustomerData;
import com.secret.poc.repository.SecretRepository;
import com.secret.poc.security.Wallet;
import com.secret.poc.util.EncryptionUtil;

@Service
public class SecretService {

	@Autowired
	private SecretRepository secretRepository;
	@Autowired
	private EncryptionUtil encryptionUtil;
	
	private static PrivateKey secretPrivateKeyA;
	private static PublicKey publicKeyA;
	private static PrivateKey secretPrivateKeyB;
	private static PublicKey publicKeyB;
	private static byte[] signature;
	
	public String addNewSecret(CustomerData customer) throws Exception {
		Wallet walletA = new Wallet();
		Wallet walletB = new Wallet();
		Map<PrivateKey, PublicKey> keysA = walletA.generateKeyPair();
		keysA.forEach((key, value) -> {
			secretPrivateKeyA = key;
			publicKeyA = value;
		});
		
		Map<PrivateKey, PublicKey> keysB = walletB.generateKeyPair();
		keysB.forEach((key, value) -> {
			secretPrivateKeyB = key;
			publicKeyB = value;
		});
		
		generateSignature(secretPrivateKeyA, publicKeyA, publicKeyB, customer.getCardNumber());
		if(verifiySignature(publicKeyA, publicKeyB, customer.getCardNumber())) {
			System.out.println(new String(signature.toString()));
		}
		//secretRepository.saveSecret(customer.getId(), secretPrivateKey);
		return publicKeyA.toString();
	}

	public String getSecret(long id) throws Exception {
		return secretRepository.getSecret(id);
	}
	
	public String changeSecret(CustomerData customerData) throws Exception {
		secretRepository.updateSecret(customerData.getId(), "");
		return null;
	}
	
	//Signs all the data we dont wish to be tampered with.
	private void generateSignature(PrivateKey privateKey, PublicKey pubKeyA, PublicKey pubKeyB, String inp) {
		System.out.println("===>>> ===>>> Metodo de Assinatura");
		System.out.println("===>>> VALOR: "+inp);
		System.out.println("===>>> REMETENTE: "+pubKeyA);
		System.out.println("===>>> DESTINATARIO: "+pubKeyB);
		String data = EncryptionUtil.getStringFromKey(pubKeyA)+EncryptionUtil.getStringFromKey(pubKeyB)+inp;
		signature = encryptionUtil.applyECDSASig(privateKey, data);		
	}
	//Verifies the data we signed hasnt been tampered with
	private boolean verifiySignature(PublicKey pubKeyA, PublicKey pubKeyB, String input) {
		System.out.println("===>>> ===>>> Metodo de Verificacao");
		System.out.println("===>>> VALOR: "+input);
		System.out.println("===>>> REMETENTE: "+pubKeyA);
		System.out.println("===>>> DESTINATARIO: "+pubKeyB);
		String data = EncryptionUtil.getStringFromKey(pubKeyA)+EncryptionUtil.getStringFromKey(pubKeyB)+input;
		return encryptionUtil.verifyECDSASig(pubKeyA, data, signature);
	}
	
}
