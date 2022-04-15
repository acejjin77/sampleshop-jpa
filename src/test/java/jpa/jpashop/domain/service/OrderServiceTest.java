package jpa.jpashop.domain.service;

import jpa.jpashop.domain.Address;
import jpa.jpashop.domain.Member;
import jpa.jpashop.domain.Order;
import jpa.jpashop.domain.OrderStatus;
import jpa.jpashop.domain.exception.NotEnoughStockException;
import jpa.jpashop.domain.item.Book;
import jpa.jpashop.domain.item.Item;
import jpa.jpashop.domain.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class OrderServiceTest {

    @Autowired
    EntityManager entityManager;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;


    @Test
    public void orderProduct() throws Exception {
        Member member = createMember("user1");

        Book book = createBook("JPA", 10000, 10);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        Order result = orderRepository.findOne(orderId);

        assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, result.getStatus());
        assertEquals("주문할 상품 종류 수가 정확해야 한다.", 1, result.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량이다..", 10000 * orderCount, result.getTotalPrice());
        assertEquals("주문한 수량만큼 재고가 감소해야한다.", 8, book.getStockQuantity());

    }

    @Test
    public void cancelOrder() throws Exception {

        Member member = createMember("userA");
        Book book = createBook("jpa", 10000, 10);

        int orderCount = 2;

        Long order = orderService.order(member.getId(), book.getId(), orderCount);

        orderService.cancelOrder(order);

        Order getOrder = orderRepository.findOne(order);

        assertEquals("주문 취소시 상태는 CANCEL", OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals("주문 취소시 재고 수량 복구", 10, book.getStockQuantity());

    }

    @Test(expected = NotEnoughStockException.class)
    public void countExcess() throws Exception {

        Member member = createMember("user1");
        Item book = createBook("JPA", 10000, 10);

        int orderCount = 11;

        orderService.order(member.getId(), book.getId(), orderCount);

        fail("재고 부족이 발생해야함");


    }

    private Book createBook(String name, int price, int stockQuantity) {

        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        entityManager.persist(book);
        return book;
    }

    private Member createMember(String user) {
        Member member = new Member();
        member.setName(user);
        member.setAddress(new Address("seoul", "seocho", "163-124"));
        entityManager.persist(member);
        return member;
    }


}