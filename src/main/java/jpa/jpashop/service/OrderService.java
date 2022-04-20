package jpa.jpashop.service;

import jpa.jpashop.domain.Delivery;
import jpa.jpashop.domain.Member;
import jpa.jpashop.domain.Order;
import jpa.jpashop.domain.OrderItem;
import jpa.jpashop.domain.item.Item;
import jpa.jpashop.repository.ItemRepository;
import jpa.jpashop.repository.MemberRepository;
import jpa.jpashop.repository.OrderRepository;
import jpa.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;


    // 주문 생성
    @Transactional(readOnly = false)
    public Long order(Long memberId, Long itemId, int count) {
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        Order order = Order.createOrder(member, delivery, orderItem);
        orderRepository.save(order);
        return order.getId();
    }

    // 주문 취소
    @Transactional(readOnly = false)
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }

    // 검색
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllByString(orderSearch);
    }
}
