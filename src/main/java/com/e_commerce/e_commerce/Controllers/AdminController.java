package com.e_commerce.e_commerce.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.e_commerce.e_commerce.Entity.Category;
import com.e_commerce.e_commerce.Entity.Product;
import com.e_commerce.e_commerce.Entity.ProductCategory;
import com.e_commerce.e_commerce.Service.CategoryService;
import com.e_commerce.e_commerce.Service.ProductCategoryService;
import com.e_commerce.e_commerce.Service.ProductService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/Admin")
public class AdminController<Blob> {

    // Cascade types are useful for managing relationships: They ensure that operations on parent entities are automatically applied to related child entities, maintaining consistency.    // private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;
    // fetching is to retrieve data from database all data or custumized 
    @InitBinder
    public void InitBinder(WebDataBinder databinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        databinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @Autowired
    private final ProductCategoryService productCategoryService;

    @Autowired
    private final ProductService productservice;

    @Autowired
    private final CategoryService categoryService;

    public AdminController(ProductCategoryService productCategoryService, ProductService productservice, CategoryService categoryService) {
        this.productCategoryService = productCategoryService;
        this.productservice = productservice;
        this.categoryService = categoryService;
    }

    // String uploadDir = "uploads/";
    // public static String UPLOAD_DIRECTORY = "/path/to/your/upload/directory";
    @GetMapping("/Categoryform")
    public String goCategoryform(Model model) {
        model.addAttribute("category", new Category());
        return "Admin/Categoryform";
    }

    @PostMapping("/submissionCategory")
    public String setaCategory(@Valid @ModelAttribute("category") Category category, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "Admin/Categoryform";
        }
        category.setCreate_at();
        category.setUpdate_at();
        categoryService.setCategoryData(category);
        return "redirect:/Admin/Categoryform";
    }

    @GetMapping("/Productform")
    public String goProductform(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "Admin/Productform";
    }

    @PostMapping("/submitProduct")
    public String createProduct(@Valid @ModelAttribute("product") Product product,
            BindingResult bindingResult,
            Model model, @RequestParam(required = false) String name) {
        if (name != null && productservice.productNameIsexist(name)) {
            bindingResult.rejectValue("name", "error.product", "Product name already exists.");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("product", product);
            model.addAttribute("categories", categoryService.getAllCategories()); // Add categories to model
            System.out.println("hasErrors");
            return "Admin/Productform"; // Return to the form with validation errors
        }
        product.setCreate_time();
        product.setUpdate_time();
        productservice.saveProduct(product);
        ProductCategory productcategory = new ProductCategory();
        productCategoryService.saveProductCategory(productcategory, product);
        System.out.println("success!!!!");
        return "redirect:/Admin/Productform";
    }

    @GetMapping("/Categories")
    public String goTocategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "Admin/Categories";
    }

    @GetMapping("/ProductsAdmin/category/{categoryId}")
    public String goToProducts(@PathVariable("categoryId") int categoryId, Model model) {
        List<Product> products = productservice.getAllActiveProductsbyCategory(categoryId);
        model.addAttribute("products", products);
        return "Admin/ProductsAdmin";
    }

    @GetMapping("/DeleteProduct/category/{categoryId}")
    public String deleteProduct(@PathVariable("categoryId") int categoryId, @RequestParam(value = "productId", required = true) int productId, Model model) {
        productservice.deleteProductById(productId);
        return "Admin/ProductsAdmin";

    }

}
