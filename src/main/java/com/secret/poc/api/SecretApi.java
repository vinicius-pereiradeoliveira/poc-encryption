package com.secret.poc.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.secret.poc.dto.CustomerData;
import com.secret.poc.service.SecretService;

@RestController
@RequestMapping("/api/secret")
public class SecretApi {
	
	@Autowired
	private SecretService secretService;
	
	@PostMapping("/add")
	@ResponseBody
	public ResponseEntity<?> addSecret(@RequestBody CustomerData customer) throws Exception{
		return ResponseEntity.ok(secretService.addNewSecret(customer));
	}
	
	@GetMapping
	public String getSecret(@RequestParam long id) throws Exception {
		return secretService.getSecret(id);
	}
	
	@PutMapping
	public String putSecret(@RequestParam long id, @RequestParam String secret) throws Exception {
		return secretService.getSecret(id);
	}
	
}
