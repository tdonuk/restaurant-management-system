package com.tahadonuk.restaurantmanagementsystem.service;

import com.tahadonuk.restaurantmanagementsystem.data.ItemType;
import com.tahadonuk.restaurantmanagementsystem.data.entity.Item;
import com.tahadonuk.restaurantmanagementsystem.data.entity.Product;
import com.tahadonuk.restaurantmanagementsystem.data.repository.ItemRepository;
import com.tahadonuk.restaurantmanagementsystem.data.repository.ProductRepository;
import com.tahadonuk.restaurantmanagementsystem.dto.ItemDTO;
import com.tahadonuk.restaurantmanagementsystem.dto.stat.ItemStats;
import com.tahadonuk.restaurantmanagementsystem.dto.stat.Stats;
import com.tahadonuk.restaurantmanagementsystem.exception.ItemConflictException;
import com.tahadonuk.restaurantmanagementsystem.exception.ItemNotFoundException;
import com.tahadonuk.restaurantmanagementsystem.exception.ItemOutOfStockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ItemRepository itemRepository;
    @PersistenceContext
    EntityManager em;

    public void saveProduct(Product product) throws ItemConflictException {
        if (isExists(product.getName())) {
            throw new ItemConflictException("An item with given info for '" + product.getName() + "' is already exists");
        } else {
            productRepository.save(product);
        }
    }


    public void handleStocks(Set<Item> items) throws ItemOutOfStockException{
        for(Item item : items) {
            if (item.getProduct().isStockRequired()) {
                if(item.getQuantity() > item.getProduct().getStock()) {
                    throw new ItemOutOfStockException(item.getProduct().getName() + " is out of stocks.");
                }
                else {
                    productRepository.updateStock(item.getProduct().getProductId(), -1 * item.getQuantity());
                }
            }
        }
    }

    public Product saveItemFromData(ItemDTO item) {
        Product productEntity = new Product();

        productEntity.setName(item.getName());
        productEntity.setDescription(item.getDescription());
        productEntity.setPrice(item.getPrice());
        productEntity.setItemType(ItemType.valueOf(item.getType().toUpperCase()));
        productEntity.setStockRequired(item.isStockRequired());
        productEntity.setStock(item.getStock());

        saveProduct(productEntity);

        return productEntity;
    }

    public Product updateItemStockValue(long itemId, int amount) throws ItemNotFoundException{
        if(isExists(itemId)) {
            productRepository.updateStock(itemId, amount);
            return productRepository.findById(itemId).get();
        }
        else {
            throw new ItemNotFoundException("No item found with given ID: "+itemId);
        }
    }

    public void updatePrice(long id, double price) {
        if(isExists(id)) {
            productRepository.updatePrice(id, price);
        }
        else throw new ItemNotFoundException("No such item");
    }

    public void updateDescription(long id, String description) {
        if(isExists(id)) {
            productRepository.updateDescription(id, description);
        }
        else throw new ItemNotFoundException("No such item");
    }

    public List<Product> getAll() {
        List items = productRepository.findAll();
        items.sort(Comparator.comparing(Product::getProductId));
        return items;
    }

    public Product getItemById(long id) throws ItemNotFoundException{
        if(isExists(id)) {
            return productRepository.findById(id).get();
        }
        else throw new ItemNotFoundException("No such item with id '" + id + "'");
    }

    public List<Product> getItemsByName(String name) {
        return productRepository.findAllByName(name);
    }

    @Transactional
    public void deleteItem(long id) {
        if(isExists(id)) {
            List<Item> items = itemRepository.findAllByProduct_ProductId(id);
            for(Item item : items) {
                item.setProduct(null);
                em.merge(item);
            }
            productRepository.deleteById(id);
        } else {
            throw new ItemNotFoundException("No item found with given ID: "+id);
        }
    }

    public List<Product> getByType(ItemType type) {
        List<Product> items = productRepository.findByItemType(type);
        items.sort(Comparator.comparing(Product::getProductId));
        return items;
    }
    
    public int countByType(ItemType type) {
        return productRepository.countByItemType(type);
    }
    
    public long countAll() {
        return productRepository.count();
    }
    
    public int getTotalStockByType(ItemType type) {
        List<Product> items = getByType(type);
        int totalStocks = 0;
        
        for (Product product : items) {
            totalStocks = totalStocks + product.getStock();
        }
        
        return totalStocks;
    }
    
    public Stats getItemStatistics() {
        ItemStats itemStats = new ItemStats();

        itemStats.setTotalCount(countAll());

        itemStats.setBeverageCount(countByType(ItemType.BEVERAGE));
        itemStats.setTotalBeverageStocks(getTotalStockByType(ItemType.BEVERAGE));

        itemStats.setMealCount(countByType(ItemType.MEAL));
        itemStats.setTotalMealStocks(getTotalStockByType(ItemType.MEAL));

        itemStats.setDessertCount(countByType(ItemType.DESSERT));
        itemStats.setTotalDessertStocks(getTotalStockByType(ItemType.DESSERT));

        return itemStats;
    }


    //id
    public boolean isExists(long id) {
        return productRepository.existsById(id);
    }
    //name
    public boolean isExists(String name) {
        return productRepository.existsByName(name);
    }

    //type
    public boolean isExists(ItemType type) {
        return productRepository.existsByItemType(type);
    }
}
