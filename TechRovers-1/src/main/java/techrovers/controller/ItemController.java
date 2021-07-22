package techrovers.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import techrovers.entity.Invoice;
import techrovers.entity.Item;
import techrovers.model.CustomError;
import techrovers.service.InvoiceService;
import techrovers.service.ItemService;

@RestController
@RequestMapping(value = "/item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	@Autowired
	private InvoiceService invoiceService;
	
	@RequestMapping(value = "/getItems", method = RequestMethod.GET)
	public ResponseEntity<List<Item>> getItems(HttpServletRequest req){
		return new ResponseEntity<List<Item>>(itemService.getAllItems(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getInvoices/{itemId}", method = RequestMethod.GET)
	public ResponseEntity<List<Invoice>> getInvoices(HttpServletRequest req, @PathVariable(name = "itemId") String itemId) throws Exception{
		return new ResponseEntity<List<Invoice>>(invoiceService.getInvoiceDetailsByItem(itemId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getItems/{id}", method = RequestMethod.GET)
	public ResponseEntity<Item> getItems(HttpServletRequest req, @PathVariable(name = "id") String id){
		return new ResponseEntity<Item>(itemService.getItemById(id), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/saveItem", method = RequestMethod.POST)
	public ResponseEntity<CustomError> saveItem(HttpServletRequest req, @RequestBody Item item) throws Exception{
		itemService.saveItem(item);
		CustomError ce = new CustomError(00, "Item created successfully");
		return new ResponseEntity<CustomError>(ce, HttpStatus.CREATED);
	}

}
