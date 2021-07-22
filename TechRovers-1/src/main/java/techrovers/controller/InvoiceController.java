package techrovers.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import techrovers.entity.Invoice;
import techrovers.entity.InvoiceDetails;
import techrovers.entity.Item;
import techrovers.model.CustomError;
import techrovers.service.InvoiceService;

@RestController
@RequestMapping(value = "/invoice")
public class InvoiceController {

	@Autowired
	private InvoiceService invoiceService;
	
	@Autowired
	@Lazy
	private ObjectMapper jacksonObjMapper;
	
	@RequestMapping(value = "/getAllInvoices", method = RequestMethod.GET)
	public ResponseEntity<List<Invoice>> getAllInvoices(HttpServletRequest req){
		return new ResponseEntity<List<Invoice>>(invoiceService.getAllInvoiceDetails(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getAllInvoices/{userId}", method = RequestMethod.GET)
	public ResponseEntity<List<Invoice>> getAllInvoices(HttpServletRequest req, @PathVariable(name = "userId") String userId) throws Exception{
		return new ResponseEntity<List<Invoice>>(invoiceService.getInvoiceDetailsByUser(userId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/saveInvoiceInformation/{userId}", method = RequestMethod.POST)
	public ResponseEntity<CustomError> saveItem(HttpServletRequest req, @PathVariable(name = "userId") String userId, @RequestBody ObjectNode map) throws Exception{
		TypeReference<List<InvoiceDetails>> typeRef = new TypeReference<List<InvoiceDetails>>() {
		};
		List<InvoiceDetails> invoiceDetails = jacksonObjMapper.readValue(map.get("invoiceDetails").toString(), typeRef);
		invoiceService.saveInvoiceInformation(invoiceDetails, userId);
		CustomError ce = new CustomError(00, "Invoice created successfully");
		return new ResponseEntity<CustomError>(ce, HttpStatus.CREATED);
	}
}
