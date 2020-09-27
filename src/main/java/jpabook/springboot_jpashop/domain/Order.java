package jpabook.springboot_jpashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    //반드시 지연로딩을 해야하는 이유 :
    //내가만약 오더만 가지고오고싶다 > select o from Order o 를 했을때, 즉시로딩이 설정되어있으면
    //order를 가져온다음(쿼리1번), order의 갯수만큼 member를 다 가져와버린다. 즉 N+1문제가 발생함.

    //그렇기때문에 기본 지연로딩을 해놓으면 오더만 가져온다. 여기서 멤버까지 같이가져오고싶다면 페치조인을 하면 된다.
    //근데 그렇게했을시 또 N+1문제가 발생, 이것을 막기위해 배치사이즈를 설정해주면 테이블 수만큼 쿼리가 나간다.

    //결과값 하나만 가져올때는 즉시로딩이 더 유리하긴하다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    //order만 persist하면 컬렉션안에 넣어뒀던 orderItems도 같이 저장됨
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;  //[ORDER, CANCEL]

    //연관관계 메소드
    public void addMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void addDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //생성 메소드
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.addMember(member);
        order.addDelivery(delivery);
        for (OrderItem orderItem : orderItems)
            order.addOrderItem(orderItem);
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());

        return order;
    }

    //생성메소드이외에 객체생성 금지
    /*protected Order() {
    }*/

    //비즈니스로직

    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP)
            throw new IllegalStateException("이미 배송된 상품은 취소가 불가능합니다.");
        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    public int getTotalPrice() {
//        int totalPrice = 0;
//        for (OrderItem orderItem : orderItems) {
//            totalPrice += orderItem.getTotalPrice();
//        }
        int totalPrice = orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
        return totalPrice;
    }
}
