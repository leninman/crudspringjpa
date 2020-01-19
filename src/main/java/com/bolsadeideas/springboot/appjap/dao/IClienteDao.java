package com.bolsadeideas.springboot.appjap.dao;



import org.springframework.data.repository.CrudRepository;

import com.bolsadeideas.springboot.appjap.models.entity.Cliente;

public interface IClienteDao extends CrudRepository<Cliente,Long> {
	
	

}
