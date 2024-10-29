package com.e_commerce.e_commerce.Entity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int id;

    @NotNull(message = "Name of product is required")
    @Column(name = "name")
    private String name;

    @NotNull(message = "Description is Required")
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "product", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH,
            CascadeType.DETACH })
    private List<OrderItem> orderItems = new ArrayList<>();

    @NotNull(message = "price is required because there is nothing free")
    @Column(name = "price")
    private Double price;

    @NotNull(message = "stock is Required")
    @Column(name = "stock_quantity")
    private int stock;

    @Column(name = "create_time")
    private Timestamp create_time;

    @Column(name = "update_time")
    private Timestamp update_time;

    @Transient
    private MultipartFile image;

    @Column(name = "ImageFileName")
    private String ImageFileName;

    @Column(name = "ImageContentType")
    private String ImageContentType;

    @NotNull(message = "You must upload the image of product")
    @Min(value = 5 * 1024, message = "The image required at least has 10KB")
    @Max(value = 5 * 1024 * 1024, message = "The image does not exceed 5MB")
    @Column(name = "ImageSize")
    private Long ImageSize; // Use Long instead of long to allow null values

    @Column(name = "image_location")
    private String ImageLocation;

    @ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "deleted")
    private boolean deleted = false;

    public String getImageFileName() {
        return this.ImageFileName;
    }

    public void setImageFileName(String ImageFileName) {
        this.ImageFileName = ImageFileName;
    }

    public String getImageContentType() {
        return this.ImageContentType;
    }

    public void setImageContentType(String ImageContentType) {
        this.ImageContentType = ImageContentType;
    }

    public Long getImageSize() {
        return this.ImageSize;
    }

    public void setImageSize(Long ImageSize) {
        this.ImageSize = ImageSize;
    }

    public String getImageLocation() {
        return this.ImageLocation;
    }

    public void setImageLocation(String ImageLocation) {
        this.ImageLocation = ImageLocation;
    }

    public Product() {
    }

    public void setTheStockQuantityAfterPurchasing(int No_ofPieces) {
        stock = stock - No_ofPieces;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getStock() {
        return this.stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Timestamp getCreate_time() {
        return this.create_time;
    }

    public void setCreate_time() {
        this.create_time = new Timestamp(System.currentTimeMillis());
    }

    public Timestamp getUpdate_time() {
        return this.update_time;
    }

    public void setUpdate_time() {
        this.update_time = new Timestamp(System.currentTimeMillis());
    }

    public MultipartFile getImage() {
        return this.image;
    }

    public void setImage(MultipartFile image) {
        try {
            String uploadDir = "uploads"; // Remove the / prefix to make it relative to the project root
            String fileName = image.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);

            // Ensure the upload directory exists
            Files.createDirectories(filePath.getParent());

            // Save the image file
            Files.write(filePath, image.getBytes());

            // Set the product's image properties
            setImageSize(image.getSize());
            setImageLocation(uploadDir);
            setImageContentType(image.getContentType());
            setImageFileName(fileName);

            this.image = image;
        } catch (IOException e) {

            this.image = image;
        }
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Product(String name, String description, Double price, int stock, Timestamp create_time,
            Timestamp update_time, MultipartFile image, String ImageFileName, String ImageContentType, Long ImageSize,
            String ImageLocation, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.image = image;
        this.ImageFileName = ImageFileName;
        this.ImageContentType = ImageContentType;
        this.ImageSize = ImageSize;
        this.ImageLocation = ImageLocation;
        this.category = category;
    }

    /*
     * public ProductCategory getProductCategory() {
     * return this.productCategory;
     * }
     * 
     * public void setProductCategory(ProductCategory productCategory) {
     * this.productCategory = productCategory;
     * }
     */
    public List<OrderItem> getOrderItems() {
        return this.orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public boolean isDeleted() {
        return this.deleted;
    }

    public boolean getDeleted() {
        return this.deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

}
