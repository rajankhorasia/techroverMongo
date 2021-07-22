package techrovers.service;

import java.util.List;

import techrovers.entity.Item;

public interface ItemService {
	
	List<Item> getAllItems();
	
	Item getItemById(String id);
	
	void saveItem(Item item);
	
	List<Item> getItems(List<String> itemIds);
	
}
