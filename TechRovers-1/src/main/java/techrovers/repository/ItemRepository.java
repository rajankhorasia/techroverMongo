package techrovers.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import techrovers.entity.Item;

@Repository
public interface ItemRepository extends MongoRepository<Item, String>{

}
