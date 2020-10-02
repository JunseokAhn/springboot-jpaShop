package jpabook.springboot_jpashop.controller;

import jpabook.springboot_jpashop.domain.Book;
import jpabook.springboot_jpashop.domain.Item;
import jpabook.springboot_jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String createForm2(BookForm bookForm) {
        Book book = new Book();
        book.setName(bookForm.getName());
        book.setStockQuantity(bookForm.getStockQuantity());
        book.setPrice(bookForm.getPrice());
        book.setAuthor(bookForm.getAuthor());
        book.setIsbn(bookForm.getIsbn());

        itemService.saveItem(book);
        return "redirect:/items/itemList";
    }

    @GetMapping("/items/itemList")
    public String itemList(Model model){
        List<Item> itemList = itemService.findAll();
        model.addAttribute("items", itemList);
        return "items/itemList";
    }
}
