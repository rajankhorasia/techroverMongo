package techrovers.serviceImpl;


import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import techrovers.entity.Invoice;
import techrovers.entity.InvoiceDetails;
import techrovers.entity.Item;
import techrovers.entity.User;
import techrovers.model.CustomException;
import techrovers.repository.InvoiceDetailRepository;
import techrovers.repository.InvoiceRepository;
import techrovers.service.InvoiceService;
import techrovers.service.ItemService;
import techrovers.service.UserService;
import techrovers.util.Constant;
import techrovers.util.Utility;

@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService{

	@Autowired
	private InvoiceRepository invoiceRepository;
	
	@Autowired
	private InvoiceDetailRepository invoiceDetailsRepo;
	
	@Autowired
	@Lazy
	private UserService userService;
	
	@Autowired
	@Lazy
	private ItemService itemService;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public List<Invoice> getAllInvoiceDetails() {
		// TODO Auto-generated method stub
		return invoiceRepository.findAll();
	}
	
	public List<Invoice> getAllInvoiceDetails(List<String> invoiceIds) {
		// TODO Auto-generated method stub
		return (List<Invoice>) invoiceRepository.findAllById(invoiceIds);
	}

	@Override
	public List<Invoice> getInvoiceDetailsByUser(String userId) throws Exception {
		// TODO Auto-generated method stub
		User user = userService.getUserById(userId);
		if(Objects.isNull(user)) {
			throw new CustomException(Constant.CUSTOM_ERROR_CODE, "No user found");
		}
		return user.getRole().getType() == Constant.ADMIN_ROLE ? getAllInvoiceDetails() : invoiceRepository.findByUserId(userId);
	}

	@Override
	public List<Invoice> getInvoiceDetailsByItem(String itemId) throws Exception {
		Aggregation aggregation = Aggregation.newAggregation(Aggregation.match(Criteria.where("itemId").is(itemId)));
		AggregationResults<InvoiceDetails> output = mongoTemplate.aggregate(aggregation, "invoice_details", InvoiceDetails.class);
		List<InvoiceDetails> invoiceDetails = output.getMappedResults();
		List<Invoice> results = getAllInvoiceDetails(invoiceDetails.stream().map(element -> element.getInvoiceId()).toList());
		return results;
	}
	
	private Double calculateGST(List<InvoiceDetails> invoiceDetails, List<Item> itemDetails) {
		Double gstAmount = 0.00;
		Item item = null;
		for (InvoiceDetails invoiceDeatils : invoiceDetails) {
			item = itemDetails.stream().filter(x -> Objects.equals(x.get_id(), invoiceDeatils.getItemId())).findFirst().orElse(null);
			if(Objects.nonNull(item)) {
				double gstValue = item.getTotalPrice() * (Double.valueOf(item.getGst()) / 100);
				System.out.println(Double.valueOf(item.getGst()) / 100);
				double totalAmount = item.getTotalPrice() * invoiceDeatils.getQuantity();
				gstAmount += (totalAmount + (gstValue * invoiceDeatils.getQuantity()));
			}
		}
		
		return gstAmount;
	}

	@Override
	public void saveInvoiceInformation(List<InvoiceDetails> invoiceDetails, String userId) throws Exception {
		// TODO Auto-generated method stub
		List<Item> itemList = null;
		User user = userService.getUserById(userId);
		if(Objects.nonNull(user) && !invoiceDetails.isEmpty()) {
			Invoice invoice = new Invoice();
			invoice.setInvoiceNumber("invoice_"+Utility.generateRandomLong());
			invoice.setUserId(user);
			itemList = itemService.getItems(invoiceDetails.stream().map(element -> element.getItemId()).distinct().collect(Collectors.toList()));
			invoice.setGstAmount(calculateGST(invoiceDetails, itemList));
			invoiceRepository.save(invoice);
			
			invoiceDetails.forEach(element -> {
				element.setInvoiceId(invoice.get_id());
			});
			invoiceDetailsRepo.saveAll(invoiceDetails);
		}
	}

}
