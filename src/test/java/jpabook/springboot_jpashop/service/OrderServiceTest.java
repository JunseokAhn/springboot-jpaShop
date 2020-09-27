package jpabook.springboot_jpashop.service;

import jpabook.springboot_jpashop.domain.*;
import jpabook.springboot_jpashop.repository.OrderRepository;
import jpabook.springboot_jpashop.service.OrderService;
import org.junit.Assert;
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
        Member member = new Member();
        member.setName("june");
        member.setAddress(new Address("seoul", "gonghang", "123456"));
        em.persist(member);

        Book book = new Book();
        book.setAuthor("june");
        book.setName("june's tegami");
        book.setPrice(10000);
        book.setStockQuantity(10);
        em.persist(book);

        //when
        Long orderId = orderService.order(member.getId(), book.getId(), 2);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
        assertEquals("주문한 상품종류가 1이어야 한다", 1, getOrder.getOrderItems().size());
        assertEquals("주문한 가격은 count * price다", 10000 * 2, getOrder.getTotalPrice());
        assertEquals("주문수량만큼 재고가 빠져야한다", 8, book.getStockQuantity());
    }

    @Test
    public void 상품취소() throws Exception {

        //given

        //when

        //then

    }

    @Test
    public void 재고수량추가() throws Exception {
        //given

        //when

        //then

    }
}
