package com.example.api.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api.domain.Address;
import com.example.api.domain.Customer;
import com.example.api.repository.AddressRepository;
import com.example.api.web.util.Util;
import com.google.gson.Gson;

@Service
public class AdressService {
	
	@Autowired
	private AddressRepository addressRespository;
	
	@Autowired
	private CustomerService customerService;
	
	 private static String webService = "http://viacep.com.br/ws/";
	 private static int codigoSucesso = 200;
	 
	 public  Address findCep(String cep) throws Exception{
		 
		 String urlToCall = webService + cep + "/json";
		 
		 try {
			 
			 URL url = new URL(urlToCall);
			 HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
			 
			 if (conexion.getResponseCode() != codigoSucesso)
	                throw new RuntimeException("HTTP error code : " + conexion.getResponseCode());

	            BufferedReader awser = new BufferedReader(new InputStreamReader((conexion.getInputStream())));
	            String jsonEmString = Util.converterJsonToString(awser);

	            Gson gson = new Gson();
	            Address address = gson.fromJson(jsonEmString, Address.class);

	            return address;
			 
		 } catch (Exception e) {
	            throw new Exception("ERRO: " + e);
	        }
		 
	 }
	 
	 @Transactional
	 public Address save(Address address) {
		 Long idCustomer = address.getCustomer().getId();
		 Customer customer = customerService.findOrFail(idCustomer);
		 
		 address.setCustomer(customer);
		 
		 return addressRespository.save(address);
		 
	 }

	
}
