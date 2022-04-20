package jpa.jpashop.controller.api;

import jpa.jpashop.domain.Address;
import jpa.jpashop.domain.Order;
import jpa.jpashop.domain.OrderStatus;
import jpa.jpashop.repository.OrderRepository;
import jpa.jpashop.repository.OrderSearch;
import jpa.jpashop.repository.order.queries.OrderSimpleQueryDto;
import jpa.jpashop.repository.order.queries.OrderSimpleQueryRepository;
import jpa.jpashop.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    // N + 1 문제 발생
    @GetMapping("/api/v2/simple-orders")
    public Result ordersV2() {
        List<Order> orders = orderService.findOrders(new OrderSearch());
        List<OrderSimpleDto> collection = orders.stream().map(o -> new OrderSimpleDto(o)).collect(Collectors.toList());

        return new Result(collection);
    }


    // 엔티티를 DTO 로 변환하고 Fetch join 으로 연결된 테이블 전부 select하여 N + 1 해결
    @GetMapping("/api/v3/simple-orders")
    public Result ordersV3() {
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        List<OrderSimpleDto> collection = orders.stream().map(o -> new OrderSimpleDto(o)).collect(Collectors.toList());

        return new Result(collection);
    }

    // DTO로 바로 조회하여 직접 쿼리문 작성 후 원하는 데이터만 가져와서 N + 1 해결
    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {
        return orderSimpleQueryRepository.findOrderDtos();
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }


    @Data
    @AllArgsConstructor
    static class OrderSimpleDto {

        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public OrderSimpleDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
        }
    }
}
