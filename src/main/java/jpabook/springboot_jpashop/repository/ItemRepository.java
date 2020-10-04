package jpabook.springboot_jpashop.repository;

import jpabook.springboot_jpashop.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {

        if (item.getId() == null) {
            em.persist(item);
        } else {
            //merge실행시 item.id를 db에서 find해와서, 필드를 전부 item의 변수로 변경해버린 새로운 객체를 반환한다.
            //이 경우, dirty checking이 작동해서 변경된사항을 업데이트하는데,
            // merge하는 item은 new로 생성한 새 객체이기때문에 모든 필드를 업데이트한다. 즉 입력하지않은 필드는 null이될 위험성이 존재한다.
            //변경감지기능(updateItem)을 사용하면 영속성컨텍스트가 관리하는 헌 객체이기때문에 원하는 값만 선택해서 업데이트가 가능.
            Item mergedItem = em.merge(item);
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }
}
