package com.irfan.microservices;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {
	
	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversion(
			@PathVariable("from") String from,@PathVariable("to") String to,
			@PathVariable("quantity") BigDecimal quantity) {
		
		Map<String,String> uriVariables=new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		
		ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchnage/from/{from}/to/{to}"
				, CurrencyConversion.class, uriVariables);
		CurrencyConversion response = responseEntity.getBody();
		
		return new CurrencyConversion(response.getId(),from,to,quantity
				,response.getConversionMultiple(),quantity.multiply(response.getConversionMultiple())
				,response.getEnvoirement());
		
	}

}
