package jpabook.springboot_jpashop.service;

import jpabook.springboot_jpashop.domain.*;
import jpabook.springboot_jpashop.exception.NotEnoughStockException;
import jpabook.springboot_jpashop.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {

        //given
        Member member = createMember();

        Book book = createBook(10000, 10);

        //when
        Long orderId = orderService.createOrder(member.getId(), book.getId(), 2);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
        assertEquals("주문한 상품종류가 1이어야 한다", 1, getOrder.getOrderItems().size());
        assertEquals("주문한 가격은 count * price다", 10000 * 2, getOrder.getTotalPrice());
        assertEquals("주문수량만큼 재고가 빠져야한다", 8, book.getStockQuantity());
    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception {

        //given
        Member member = createMember();
        Item item = createBook(10000, 10);

        //when
        orderService.createOrder(member.getId(), item.getId(), 11);

        //then
        fail("재고수량부족 예외가 발생해야한다");
    }

    @Test
    public void 상품취소() throws Exception {

        //given
        Member member = createMember();
        Item item = createBook(10000, 10);

        //when
        Long orderId = orderService.createOrder(member.getId(), item.getId(), 2);
        Order getOrder = orderRepository.findOne(orderId);
        getOrder.cancel();

        //then
        assertEquals("주문 취소시 상태는 CANCEL", OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals("주문 취소된 상품의 재고= 10", 10, item.getStockQuantity());
    }

    @Test
    public void 재고수량추가() throws Exception {

        //given
        Item item = createBook(10000, 10);

        //when
        item.addStock(3);

        //then
        assertEquals("추가된 재고의 숫자는 13", 13, item.getStockQuantity());
    }

    private Book createBook(int price, int stockQuantity) {
        Book book = new Book();
        book.setAuthor("june");
        book.setName("june's tegami");
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("june");
        member.setAddress(new Address("seoul", "gonghang", "123456"));
        em.persist(member);
        return member;
    }
}
