package com.messio.lineage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/site")
public class MustacheController {

    @GetMapping("/index")
    public String getProducts(final Model model){
        List<Product> productList = IntStream
                .range(0, 7)
                .mapToObj(Product::newInstance)
                .collect(Collectors.toList());
        model.addAttribute("productList", productList);
        return "index";
    }
}
