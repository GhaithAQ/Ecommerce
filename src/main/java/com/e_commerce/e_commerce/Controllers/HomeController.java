package com.e_commerce.e_commerce.Controllers;

import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.e_commerce.e_commerce.Entity.Address;
import com.e_commerce.e_commerce.Entity.Order;
import com.e_commerce.e_commerce.Entity.OrderItem;
import com.e_commerce.e_commerce.Entity.Product;
import com.e_commerce.e_commerce.Entity.User;
import com.e_commerce.e_commerce.Service.AddressService;
import com.e_commerce.e_commerce.Service.CategoryService;
import com.e_commerce.e_commerce.Service.OrderService;
import com.e_commerce.e_commerce.Service.PayPalService;
import com.e_commerce.e_commerce.Service.ProductService;
import com.e_commerce.e_commerce.Service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/Home")
public class HomeController {

    @InitBinder
    public void InitBinder(WebDataBinder databinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        databinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @Autowired
    private final ProductService productservice;

    @Autowired
    private final CategoryService categoryService;

    @Autowired
    private final UserService userService;

    @Autowired
    private final AddressService addressService;

    @Autowired
    private final OrderService orderService;

    @Autowired
    private final HttpSession session;

    @Autowired
    private PayPalService payPalService;

    private List<OrderItem> orderItems = new ArrayList<>();

    private List<Integer> submittedProductIds;

    public HomeController(HttpSession session, OrderService orderService, ProductService productservice, CategoryService categoryService, UserService userService, AddressService addressService) {
        this.productservice = productservice;
        this.categoryService = categoryService;
        this.userService = userService;
        this.addressService = addressService;
        this.orderService = orderService;
        this.session = session;
    }

    @GetMapping("/user-registration")
    public String registrationPage(Model model, @RequestParam(value = "dynamicInputs", required = false) List<Address> dynamicInputs) {
        User user = new User();
        user.setAddresses(new ArrayList<>(5));
        if (dynamicInputs != null) {
            for (Address address : dynamicInputs) {
                user.addAddress(address);
            }
        }
        model.addAttribute("user", user);

        return "Home/user-registration";
    }

    @PostMapping("/SubmitionUserInfo")
    public String submissionRegistration(@Valid
            @ModelAttribute("user") User user, BindingResult bindingResult,
            Model model
    ) {
        if (user.getPassword() != null && user.getConfirmation_password() != null && !user.getPassword().equals(user.getConfirmation_password())) {
            bindingResult.rejectValue("confirmation_password", "error.user", "Confirmation password is not identical to the password");
        } else if (user.getPassword() == null && user.getConfirmation_password() == null) {
            bindingResult.rejectValue("confirmation_password", "error.user", "password must be written");
        } else if (user.getPassword() != null && user.getConfirmation_password() == null) {
            bindingResult.rejectValue("confirmation_password", "error.user", "confirmation of password must be written");
        } else if (user.getPassword() == null && user.getConfirmation_password() != null) {
            bindingResult.rejectValue("confirmation_password", "error.user", "You typed the confirmation without password");
        }

        if (user.getAddresses() != null) {
            for (int i = 0; i < user.getAddresses().size(); i++) {
                Address address = user.getAddresses().get(i);
                if (address.getAddress_line1() == null || address.getAddress_line1().isEmpty()) {
                    bindingResult.rejectValue("addresses[" + i + "].address_line1", "error.address", "The name of housing must be filled.");
                }
                if (address.getAddress_line2() == null || address.getAddress_line2().isEmpty()) {
                    bindingResult.rejectValue("addresses[" + i + "].address_line2", "error.address", "The name of street must be filled.");
                }
                if (address.getPostal_code() == null || address.getPostal_code().isEmpty()) {
                    bindingResult.rejectValue("addresses[" + i + "].postal_code", "error.address", "The name of postal code must be filled.");
                }
            }
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "Home/user-registration";
        }
        user.setCreate_time();
        user.setUpdate_time();
        userService.saveUser(user);

        if (user.getAddresses() != null) {
            for (Address address : user.getAddresses()) {
                address.setUser(user);  // Associate address with user
                addressService.AddAddressesTouser(address, user);
            }
        }

        return "redirect:/Home/user-registration";
    }

// GET Mapping to load the login form
    @GetMapping("/user-Login")
    public String goLogin(Model model) {
        model.addAttribute("user", new User());

        return "Home/user-Login";
    }

    @PostMapping("/submitUser")
    public String submitUser(@Valid @ModelAttribute User user, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        User existingUser = userService.getUserbyEmail(user.getEmail());
        if (existingUser == null) {
            result.rejectValue("email", "user.error", "User not found with this email.");
            return "Home/user-Login";
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            result.rejectValue("password", "user.error", "Password is required.");
            return "Home/user-Login";
        }

        if (existingUser.getPassword() == null) {
            result.rejectValue("password", "user.error", "User found with null password.");
            return "Home/user-Login";
        }

        if (!user.getPassword().equals(existingUser.getPassword())) {
            result.rejectValue("password", "user.error", "Incorrect password.");
            return "Home/user-Login";
        }
        Integer userId = existingUser.getUser_id();
        redirectAttributes.addAttribute("userId", userId);
        return "redirect:/Home/Categories/user/" + userId;
    }

    @GetMapping("/user-Logout")
    public String logout(Model model, RedirectAttributes redirectAttributes, HttpSession session) {
        if (submittedProductIds != null) {
            submittedProductIds.clear();
        }
        orderItems.clear();
        session.removeAttribute("submittedProductIds");
        // session.removeAttribute("itemAdded");
        redirectAttributes.addFlashAttribute("message", "You have been logged out successfully.");
        return "redirect:/Home/user-Login";
    }

    @GetMapping("/Categories/user/{userId}")
    public String goTocategories(@PathVariable("userId") Integer userId, @ModelAttribute User user, Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "Home/Categories";
    }

    @GetMapping("/Products/category/{categoryId}/user/{userId}")
    public String goToProducts(@PathVariable("categoryId") int categoryId,
            @PathVariable("userId") Integer userId, @ModelAttribute Product product, BindingResult result,
            Model model, RedirectAttributes redirectAttributes, HttpSession session) {
        List<Product> products = productservice.getAllActiveProductsbyCategory(categoryId);
        model.addAttribute("products", products);
        model.addAttribute("orderitem", new OrderItem());
        model.addAttribute("user", userService.getUserbyId(userId));
        for (Product p : products) {
            if (p.getStock() == 0) {
                result.rejectValue("stock", "product.error", "we cannot add it to order because it is not in stock.");
            }

        }
        // model.addAttribute("itemAdded", session.getAttribute("itemAdded"));
        return "Home/Products";
    }

    @PostMapping("/OrderItemSubmission/user/{userId}")
    public String orderitemSubmission(
            @RequestParam("productId") int productId,
            @RequestParam("quantity") int quantity,
            @PathVariable("userId") Integer userId, @Valid
            @ModelAttribute OrderItem orderitem,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {
        // Debugging statement to check userId
        System.out.println("UserId: " + (userId != null ? userId : "null"));
        // Fetch the product details based on productId
        Product product = productservice.getProductbyId(productId);
        model.addAttribute("product", product);

        // Check stock availability
        if (product.getStock() < quantity) {
            result.reject("stockError", "Requested quantity is more than available stock.");
            redirectAttributes.addFlashAttribute("stockError", "Requested quantity is more than available stock.");
            return "Home/Products";
        } else {
            orderitem.setProduct(product);
            if (orderitemExist(product) != null) {
                //int OQ = orderitemExist(product).getQuantity();
                orderitemExist(product).setQuantity(quantity);
            } else {

                orderitem.setPrice(product.getPrice());
                orderItems.add(orderitem);
            }

        }
        if (submittedProductIds == null) {
            submittedProductIds = new ArrayList<>();
        }
        submittedProductIds.add(productId);
        session.setAttribute("submittedProductIds", submittedProductIds);
        return "redirect:/Home/Products/category/" + product.getCategory().getId() + "/user/" + userId;
    }

    @GetMapping("/OrderItems/user/{userId}")
    public String goOrderItems(@PathVariable("userId") Integer userId, Model model) {
        model.addAttribute("Total", orderService.CalculateOrder(orderItems));
        model.addAttribute("orderitems", orderItems);
        return "Home/OrderItems";
    }

    @GetMapping("/submitOrder/user/{userId}")
    public String submitOrder(@PathVariable("userId") Integer userId, Model model) {
        orderService.createOrder(new Order(), orderItems, userService.getUserbyId(userId));
        setOrderItems(new ArrayList<>());
        return "redirect:/Home/OrderItems/user/" + userId;
    }

    public List<OrderItem> getOrderItems() {
        return this.orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public OrderItem orderitemExist(Product product) {
        int left = 0;
        int right = orderItems.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int midOrderItemId = orderItems.get(mid).getProduct().getId();
            int searchOrderItemId = product.getId();

            if (midOrderItemId == searchOrderItemId) {
                return orderItems.get(mid);
            } else if (midOrderItemId < searchOrderItemId) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return null; // or any other value indicating the item was not found
    }

    @PostMapping("/initiatePayment/user/{userId}")
    public String initiatePayment(@PathVariable("userId") Integer userId, Model model) {
        double totalAmount = orderService.CalculateOrder(orderItems);
        try {
            String orderId = payPalService.createPayment(totalAmount);
            model.addAttribute("orderId", orderId);
            return "Home/PaymentConfirmation";
        } catch (IOException e) {
            model.addAttribute("error", "Failed to initiate payment");
            return "Home/OrderItems";
        }
    }

    @PostMapping("/completePayment/user/{userId}")
    public String completePayment(@PathVariable("userId") Integer userId, @RequestParam("orderId") String orderId, Model model) {
        try {
            boolean captured = payPalService.capturePayment(orderId);
            if (captured) {
                Order order = orderService.getOrderById(Integer.parseInt(orderId));
                order.setUser(userService.getUserbyId(userId));
                order.setOrderItems(orderItems);
                order.setTotal_amount(orderService.CalculateOrder(orderItems));
                order.setOrder_date();
                order.setPaymentStatus(Order.PaymentStatus.COMPLETED);
                order.setPaypalTransactionId(orderId);
                orderService.updateOrder(order);
                setOrderItems(new ArrayList<>());
                model.addAttribute("message", "Payment completed successfully");
            } else {
                model.addAttribute("error", "Payment capture failed");
            }
        } catch (IOException e) {
            model.addAttribute("error", "Failed to complete payment: " + e.getMessage());
        }
        return "Home/PaymentResult";
    }
}
