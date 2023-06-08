package com.example.springboot.controllers;

import com.example.springboot.dtos.ProductRecordDto;
import com.example.springboot.models.ProductModel;
import com.example.springboot.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("products")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/save")
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProduct(productRecordDto));
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProductModel>> list(){
        return ResponseEntity.status(HttpStatus.OK).body(productService.list());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Object> findById (@PathVariable UUID id){
        var product = productService.findById(id);
        if(product.isEmpty()){
            return  ResponseEntity.status((HttpStatus.NOT_FOUND)).body("Produto não encontrado.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(product.get());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateById (@PathVariable UUID id, @RequestBody @Valid ProductRecordDto productRecordDto) {
        var product = productService.findById(id);
        if(product.isEmpty()){
            return  ResponseEntity.status((HttpStatus.NOT_FOUND)).body("Produto não encontrado.");
        }
        var productUpdate = productService.updateById(id, productRecordDto);

        return ResponseEntity.status(HttpStatus.OK).body(productUpdate);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteById (@PathVariable UUID id) {
        var product = productService.findById(id);
        if(product.isEmpty()){
            return  ResponseEntity.status((HttpStatus.NOT_FOUND)).body("Produto não encontrado.");
        }
        productService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Produtor deletado");
    }
}
