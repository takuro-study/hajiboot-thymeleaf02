package com.example.web;

import java.net.URI;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.domain.Customer;
import com.example.service.CustomerService;

@Controller
@RequestMapping("customers")
public class CustomerRestController {

	@Autowired
	CustomerService customerService;
	
	@ModelAttribute
	CustomerForm setUpForm() {
		// @@RequestMappingでマッピングされたメソッド（listやcreate）が行われる前にmodel.addAttribute(new CustomerForm())相当の処理が行われる
		return new CustomerForm();
	}

	@GetMapping
	String list(Model model) {
		List<Customer> customers = customerService.findAll();
		model.addAttribute("customers", customers);
		return "customers/list";
	}
	
	@PostMapping(path = "create")
	String create(@Validated CustomerForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return list(model);
		}
		Customer customer = new Customer();
		BeanUtils.copyProperties(form, customer);
		customerService.create(customer);
		return "redirect:/customers";
	}

	@GetMapping(path = "edit", params = "form")
	String editForm(@RequestParam Integer id, CustomerForm form) {
		Customer customer = customerService.findOne(id);
		BeanUtils.copyProperties(customer, form);
		return "customers/edit";
	}
	
	@PostMapping(path = "edit")
	String editForm(@RequestParam Integer id, @Validated CustomerForm form, BindingResult result) {
		if (result.hasErrors()) {
			return editForm(id, form);
		}
		Customer customer = new Customer();
		BeanUtils.copyProperties(form, customer);
		customer.setId(id);
		customerService.update(customer);
		return "redirect:/customers";
	}
	
	@PostMapping(path = "edit", params = "goToTop")
	String goToTop() {
		return "redirect:/customers";
	}
	
	@PostMapping(path = "delete")
	String delete(@RequestParam Integer id) {
		customerService.delete(id);
		return "redirect:/customers";
	}
}

