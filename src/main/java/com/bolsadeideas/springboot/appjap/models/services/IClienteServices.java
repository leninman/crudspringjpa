package com.bolsadeideas.springboot.appjap.models.services;

import java.util.List;

import com.bolsadeideas.springboot.appjap.models.entity.Cliente;

public interface IClienteServices {

	public List<Cliente> findAll();

	public void save(Cliente cliente);

	public Cliente findOne(Long id);

	public void delete(Long id);
	
	
	
}
