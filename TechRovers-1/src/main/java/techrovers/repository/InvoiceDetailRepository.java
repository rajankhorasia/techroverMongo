package techrovers.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import techrovers.entity.InvoiceDetails;

@Repository
public interface InvoiceDetailRepository extends MongoRepository<InvoiceDetails, String>{

}
