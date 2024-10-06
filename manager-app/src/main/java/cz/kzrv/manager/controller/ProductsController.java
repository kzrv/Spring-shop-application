package cz.kzrv.manager.controller;


import cz.kzrv.manager.client.BadRequestException;
import cz.kzrv.manager.client.ProductsRestClient;
import cz.kzrv.manager.controller.payload.NewProductPayload;
import cz.kzrv.manager.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("catalogue/products")
public class ProductsController {

    private final ProductsRestClient productService;

    @GetMapping("list")
    public String getProductsList(Model model, @RequestParam(name="filter",required = false) String filter) {
        model.addAttribute("products", this.productService.findAllProducts(filter));
        model.addAttribute("filter", filter);
        return "catalogue/products/list";
    }

    @GetMapping("create")
    public String getNewProductPage() {
        return "catalogue/products/new_product";
    }

    @PostMapping("create")
    public String createProduct(NewProductPayload payload,
                                Model model) {
        try {
            Product product = this.productService.createProduct(payload.title(), payload.details());
            return "redirect:/catalogue/products/%d".formatted(product.id());
        }catch (BadRequestException e){
            model.addAttribute("payload", payload);
            model.addAttribute("errors", e.getErrors());
            return "catalogue/products/new_product";
        }
    }
}
