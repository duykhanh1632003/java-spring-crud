package com.example.jdk15.Controllers;
import java.util.Optional;

import com.example.jdk15.models.Products;
import com.example.jdk15.models.UserDo;
import com.example.jdk15.services.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductControllers {

    @Autowired
    private ProductResponse repo;

    @GetMapping
    public String AllUser(Model model) {
        List<Products> products = repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("products", products);
        return "products/index";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model) {
        UserDo userDo = new UserDo();
        model.addAttribute("userDo", userDo);
        return "products/create";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute UserDo userDo, Model model) {
        if (userDo.getName() == null || userDo.getName().isEmpty()) {
            model.addAttribute("error", "Name is required");
            return "products/create";
        }
        if (userDo.getAddress() == null || userDo.getAddress().isEmpty()) {
            model.addAttribute("error", "Address is required");
            return "products/create";
        }
        if (userDo.getType() == null || userDo.getType().isEmpty()) {
            model.addAttribute("error", "Type is required");
            return "products/create";
        }
        if (userDo.getPhoneNumber() == null || userDo.getPhoneNumber().isEmpty()) {
            model.addAttribute("error", "Phone number is required");
            return "products/create";
        }

        Products products = new Products();
        products.setName(userDo.getName());
        products.setAddress(userDo.getAddress());
        products.setType(userDo.getType());
        products.setPhoneNumber(userDo.getPhoneNumber());
        repo.save(products);

        return "redirect:/products";
    }

    @GetMapping("/search")
    public String searchByName(@RequestParam String name, Model model) {
        List<Products> products = repo.findByNameContainingIgnoreCase(name);
        model.addAttribute("products", products);
        return "products/search";
    }

    @GetMapping("/edit")
    public String showEditPage(@RequestParam int id, Model model) {
        try {
            // Kiểm tra xem sản phẩm có tồn tại không
            Optional<Products> optionalProduct = repo.findById(id);
            if (optionalProduct.isPresent()) {
                Products products = optionalProduct.get();
                model.addAttribute("product", products);

                // Không cần tạo mới UserDo, sử dụng thông tin của products để thiết lập
                UserDo userDo = new UserDo();
                userDo.setName(products.getName());
                userDo.setAddress(products.getAddress());
                userDo.setType(products.getType());
                userDo.setPhoneNumber(products.getPhoneNumber());

                model.addAttribute("userDo", userDo);
            } else {
                // Nếu không tìm thấy sản phẩm, chuyển hướng về trang danh sách sản phẩm
                return "redirect:/products";
            }
        } catch (Exception ex) {
            // Xử lý ngoại lệ nếu có
            ex.printStackTrace(); // Hoặc log ngoại lệ để xem nguyên nhân
            return "redirect:/products"; // Chuyển hướng về trang danh sách sản phẩm
        }
        return "products/editProduct";
    }

    @PostMapping("/edit") // Remove the semicolon here
    public String updateProduct(@RequestParam int id, @ModelAttribute UserDo userDo,Model model) {
        try {
            // Kiểm tra xem sản phẩm có tồn tại không
            Products products = repo.findById(id).get();
            model.addAttribute("products",products);

            products.setName(userDo.getName());
            products.setAddress(userDo.getAddress());
            products.setType(userDo.getType());
            products.setPhoneNumber(userDo.getPhoneNumber());
            repo.save(products);
        } catch (Exception ex) {
            // Xử lý ngoại lệ nếu có
            ex.printStackTrace(); // Hoặc log ngoại lệ để xem nguyên nhân
            return "redirect:/products"; // Chuyển hướng về trang danh sách sản phẩm
        }
        return "redirect:/products"; // Sau khi cập nhật xong, chuyển hướng về trang danh sách sản phẩm
    }

    @GetMapping("/delete")
    public String deleteProduct(@RequestParam int id) {
        try {
            Products products= repo.findById(id).get();
            repo.delete(products);

        } catch (Exception ex) {
            // Xử lý ngoại lệ nếu có
            ex.printStackTrace(); // Hoặc log ngoại lệ để xem nguyên nhân
        }
        return "redirect:/products"; // Sau khi xóa xong, chuyển hướng về trang danh sách sản phẩm
    }


}
