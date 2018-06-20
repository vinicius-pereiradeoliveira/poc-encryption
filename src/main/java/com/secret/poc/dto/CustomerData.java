package com.secret.poc.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@JsonDeserialize
public class CustomerData {
	private long id;
	private String cardNumber;
	private String password;
	
	public CustomerData() {
	}
	
	public CustomerData(long id, String cardNumber, String password) {
		super();
		this.id = id;
		this.cardNumber = cardNumber;
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
