package com.bolsadeideas.springboot.appjap.Controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideas.springboot.appjap.models.entity.Cliente;
import com.bolsadeideas.springboot.appjap.models.services.IClienteServices;

@Controller

public class ClienteController {
	
	String mensajeFlash;
	
	@Autowired
	IClienteServices clientServices;
	@RequestMapping("/listar")
	public String ListarClientes(Model model) {
		model.addAttribute("titulo", "Listado de clientes");
		model.addAttribute("listadoclientes",clientServices.findAll());
		return"Clientes";
	}
	
	
	@RequestMapping({"/inicio",""})
	public String Inicio(Model model) {
		model.addAttribute("titulo", "Pagina de inicio");
		return"Inicio";
	}
	
	@GetMapping(value="/form")
	public String Crear(Model model) {
		Cliente cliente = new Cliente();
		model.addAttribute("cliente",cliente);
		model.addAttribute("titulo","Formulario de inclusión de clientes");
		return "form";
	}
	//METODO PARA GUARDAR
	@RequestMapping(value="/form",method=RequestMethod.POST)
	public String Guardar(@Valid Cliente cliente,BindingResult result,Model model, RedirectAttributes flash) {
		if(result.hasErrors()) {
			model.addAttribute("titulo","Formulario de inclusión de clientes");
			return "form";
		}
		
		clientServices.save(cliente);
		
		String mensajeflash=(cliente.getId()!=null)? "Cliente editado con éxito":"Cliente creado con éxito"; 
		
		if(cliente.getId()!=null) {
			mensajeFlash="Cliente creado con éxito";
		}else {
			mensajeFlash="Cliente actualizado con éxito";
		}
		
		flash.addFlashAttribute("success", mensajeflash);
		return "redirect:/listar";
	}
	//METODO PARA EDITAR
	@RequestMapping(value="/form/{id}")
	public String Editar(@PathVariable(value="id") Long id,Model model, RedirectAttributes flash) {
		Cliente cliente;
		
		if(id>0) {
			cliente=this.clientServices.findOne(id);
			if(cliente==null) {
				flash.addFlashAttribute("error", "El id del cliente no existe en la base de datos");
				return "redirect:/listar";
			}
		}else {
			flash.addFlashAttribute("error", "El id del cliente no puede ser cero");
			return "redirect:/listar";
		}
		model.addAttribute("titulo", "Editar cliente");
		model.addAttribute("cliente", cliente);
		return "form";
	}
	
	
	@RequestMapping(value="/eliminar/{id}")
	public String Eliminar(@PathVariable(value="id") Long id, RedirectAttributes flash) {
		
		if(id>0) {
			this.clientServices.delete(id);
			flash.addFlashAttribute("success", "Cliente eliminado con éxito");
		}
		
		return"redirect:/listar";
	}
	
}
