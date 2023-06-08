package com.example.springboot.services;


import com.example.springboot.controllers.ProductController;
import com.example.springboot.dtos.ProductRecordDto;
import com.example.springboot.models.ProductModel;
import com.example.springboot.repositories.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;


    public ProductModel saveProduct(ProductRecordDto productRecordDto){
        var productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return productRepository.save(productModel);
    }

    public List<ProductModel> list(){
        List<ProductModel> listProducts = productRepository.findAll();
        if(!listProducts.isEmpty()){
            for(ProductModel product : listProducts){
                UUID id = product.getId();
                product.add(linkTo(methodOn(ProductController.class).findById(id)).withSelfRel());
            }
        }
        return listProducts;
    }

    public Optional<ProductModel> findById (UUID id){
        var product = productRepository.findById(id);
        if(product.isPresent()){
            product.get().add(linkTo(methodOn(ProductController.class).list()).withSelfRel());
        }
        return product;
    }

    public ProductModel updateById(UUID id, ProductRecordDto productRecordDto) {
        var product = productRepository.findById(id);
        BeanUtils.copyProperties(productRecordDto, product.get());
        var a = product;
        return productRepository.save(product.get());
    }

    public void deleteById(UUID id){
        productRepository.deleteById(id);
    }
}
