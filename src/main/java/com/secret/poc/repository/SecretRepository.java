package com.secret.poc.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SecretRepository {

	@Autowired
    JdbcTemplate jdbcTemplate;
	
	public void saveSecret(long id, String secret) {
		jdbcTemplate.update("INSERT INTO public.customer(id, secret) VALUES (?, ?)", id, secret);
	}
	
	public String getSecret(long id) {
		return jdbcTemplate.queryForObject(
	            "SELECT secret FROM public.customer WHERE ID = ?", new Object[] { id }, String.class);
	}
	
	public void updateSecret(long id, String newSecret) {
		jdbcTemplate.update("UPDATE public.customer SET SECRET = ? WHERE ID = ?", id, newSecret);
	}
	
}
