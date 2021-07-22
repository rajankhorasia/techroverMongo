package techrovers.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import techrovers.entity.Item;
import techrovers.repository.ItemRepository;
import techrovers.service.ItemService;

@Service
@Transactional
public class ItemServiceImpl implements ItemService{

	@Autowired
	private ItemRepository itemRepository;
	
	@Override
	public List<Item> getAllItems() {
		// TODO Auto-generated method stub
		return itemRepository.findAll();
	}

	@Override
	public Item getItemById(String id) {
		// TODO Auto-generated method stub
		return itemRepository.findById(id).orElse(null);
	}

	@Override
	public void saveItem(Item item) {
		// TODO Auto-generated method stub
		itemRepository.save(item);
	}

	@Override
	public List<Item> getItems(List<String> itemIds) {
		// TODO Auto-generated method stub
		return (List<Item>) itemRepository.findAllById(itemIds);
	}

}
