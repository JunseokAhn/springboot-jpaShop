package jpabook.springboot_jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;

    private int count;

    //생성 메소드
    public static OrderItem createOrderItem (Item item, Order order, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setItem(item);
        orderItem.setCount(count);
        orderItem.setCount(orderPrice);

        item.removeStock(count);
        return orderItem;
    }


    public void cancel() {
        item.addStock(count);
    }

    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
