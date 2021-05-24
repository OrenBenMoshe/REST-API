package Exercise.packages.repository;

import org.springframework.stereotype.Repository;
import Exercise.packages.entity.Item;
import org.springframework.data.repository.CrudRepository;


@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {}
