package techrovers.service;

import java.util.List;

import techrovers.entity.Invoice;
import techrovers.entity.InvoiceDetails;

public interface InvoiceService {
	
	List<Invoice> getAllInvoiceDetails();
	
	List<Invoice> getInvoiceDetailsByUser(String userId) throws Exception;
	
	List<Invoice> getInvoiceDetailsByItem(String itemId) throws Exception;
	
	void saveInvoiceInformation(List<InvoiceDetails> invoiceDetails, String userId) throws Exception;
	
}
