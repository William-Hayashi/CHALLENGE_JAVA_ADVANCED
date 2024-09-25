package com.example.testeSpring.controllers;

import com.example.testeSpring.DTOs.ProductRecordDto;
import com.example.testeSpring.model.ProductModel;
import com.example.testeSpring.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    //CRUD

    //POST - CREATE
    @PostMapping("/products")
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto  productRecordDto) { //Ele fala que vai ter um retorno pro ProdcutModel, No RequestBody o metodo recebe como corpo o PRoductdRecordDto, e o Valid serve para a validação
        var productModel = new ProductModel(); //Criacao de um objeto novo do Model que sera inserido no BD
        BeanUtils.copyProperties(productRecordDto, productModel); // Conversao do Dto para o Model, usando o Beans Utils
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));// O retorno precisa ser coerente então reotrnamos que primeiro foi criado o post, e depois enviamos o que foi salvo no Model
    }

    //GET = READ
    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        List<ProductModel> productsList = productRepository.findAll();
        if (!productsList.isEmpty()) {
            for (ProductModel product : productsList) {
                UUID id = product.getId();
                product.add(linkTo(methodOn(ProductController.class).getOneProduct(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(productsList);
    }

    // GET - READ, BUT NOW BY ID
    @GetMapping("products/{id}")
    public ResponseEntity<Object> getOneProduct(@PathVariable (value="id") UUID id) {
        Optional<ProductModel> product0 = productRepository.findById(id);
        if(product0.isEmpty()) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        product0.get().add(linkTo(methodOn(ProductController.class).getAllProducts()).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(product0.get());
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable (value="id") UUID id,
                                                @RequestBody @Valid ProductRecordDto productRecordDto) {
        Optional<ProductModel> product0 = productRepository.findById(id);
        if (product0.isEmpty()) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        var productModel = product0.get();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));

    }

    //DELETE
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable (value="id") UUID id) {
        Optional<ProductModel> product0 = productRepository.findById(id);
        if(product0.isEmpty()) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        productRepository.delete(product0.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully");
    }

}
