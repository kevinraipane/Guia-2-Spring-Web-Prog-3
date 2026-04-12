package org.eduardomango.practicaspringweb.model.services;

import org.eduardomango.practicaspringweb.model.entities.product.entity.ProductEntity;
import org.eduardomango.practicaspringweb.model.exceptions.ProductNotFoundException;
import org.eduardomango.practicaspringweb.model.repositories.IRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final IRepository<ProductEntity> productRepository;

    public ProductService(IRepository<ProductEntity> productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductEntity> findAll() {
        return productRepository.findAll();
    }
    public ProductEntity findById(long id) {
        return productRepository.findAll()
                .stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(ProductNotFoundException::new);
    }

    public ProductEntity findByName(String name){
        return productRepository.findAll()
                .stream()
                .filter(user -> user.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(ProductNotFoundException::new);
    }

    public List<ProductEntity> findMoreExpensiveThan(Double price){
        return productRepository.findAll()
                .stream()
                .filter(product -> product.getPrice() > price)
                .toList();
    }

    public void save(ProductEntity p) {
        productRepository.save(p);
    }

    public void delete(ProductEntity p) {
        productRepository.delete(p);
    }

    public void update(ProductEntity p) {
        productRepository.update(p);
    }
}
