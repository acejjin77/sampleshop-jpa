package jpa.jpashop.repository.order.queries;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {

    private final EntityManager entityManager;

    public List<OrderSimpleQueryDto> findOrderDtos() {
        return entityManager.createQuery(
                        "select new jpa.jpashop.repository.order.queries.OrderSimpleQueryDto(o.id, m.name, o.orderDate, o.status, d.address) " +
                                "from Order o " +
                                "join o.member m " +
                                "join o.delivery d ", OrderSimpleQueryDto.class)
                .getResultList();
    }
}
