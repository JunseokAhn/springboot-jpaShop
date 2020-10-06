package jpabook.springboot_jpashop.controller;

import jpabook.springboot_jpashop.domain.Item;
import jpabook.springboot_jpashop.domain.Member;
import jpabook.springboot_jpashop.domain.Order;
import jpabook.springboot_jpashop.repository.OrderSearch;
import jpabook.springboot_jpashop.service.ItemService;
import jpabook.springboot_jpashop.service.MemberService;
import jpabook.springboot_jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String createOrder(Model model) {
        List<Member> memberList = memberService.findAll();
        List<Item> itemList = itemService.findAll();

        model.addAttribute("members", memberList);
        model.addAttribute("items", itemList);
        return "order/orderForm";
    }

    @PostMapping("/order")
    public String createOrder(Long memberId, Long itemId, int count) {
        orderService.createOrder(memberId, itemId, count);
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {

        List<Order> orderList = orderService.findOrder(orderSearch);
        model.addAttribute("orders", orderList);
        return "order/orderList";
    }
}
