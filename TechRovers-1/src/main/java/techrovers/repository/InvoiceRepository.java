package techrovers.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import techrovers.entity.Invoice;

@Repository
public interface InvoiceRepository  extends MongoRepository<Invoice, String>{

	List<Invoice> findByUserId(String userId);
	
	@Query("{ 'itemId' : { $itemId: ?0 } }")
	List<Invoice> getInvoiceFromItem(String itemId);
}
