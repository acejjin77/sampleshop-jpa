package jpa.jpashop.domain.repository;

import jpa.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager entityManager;

    public void save(Item item) {
        if (item.getId() == null) {
            entityManager.persist(item);
        } else {
            entityManager.merge(item);
        }
    }

    public Item findOne(Long itemId) {
        return entityManager.find(Item.class, itemId);
    }

    public List<Item> findAll() {
        return entityManager.createQuery("select i fron Item i", Item.class)
                .getResultList();
    }

}
