package cz.kzrv.catalogue.controller;


import cz.kzrv.catalogue.controller.payload.NewProductPayload;
import cz.kzrv.catalogue.entity.Product;
import cz.kzrv.catalogue.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("catalogue-api/products")
public class ProductsRestController {
    private final ProductService productService;

    @GetMapping
    public Iterable<Product> findProducts(@RequestParam(name="filter",required = false) String filter){
        return this.productService.findAllProducts(filter);
    }
    @PostMapping()
    public ResponseEntity<?> createProduct(@Valid @RequestBody NewProductPayload payload,
                                                 BindingResult bindingResult,
                                                 UriComponentsBuilder uriComponentsBuilder)
    throws BindException {
        if(bindingResult.hasErrors()){
            if(bindingResult instanceof BindException exception){
                throw exception;
            }
            else {
                throw new BindException(bindingResult);
            }
        }
        else{
            Product product = this.productService.createProduct(payload.title(), payload.details());
            return ResponseEntity.created(uriComponentsBuilder
                    .replacePath("/catalogue-api/products/{productId}")
                    .build(Map.of("productId",product.getId())))
                    .body(product);
        }
    }


}
