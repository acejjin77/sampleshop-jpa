package jpa.jpashop.domain.repository;

import jpa.jpashop.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ordersearch {

    private String memberName; // 회원 이름
    private OrderStatus orderStatus; // 주문 상태
}
