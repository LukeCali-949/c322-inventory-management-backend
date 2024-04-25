package edu.iu.habahram.ducksservice.repository;


import edu.iu.habahram.ducksservice.model.Builder;
import edu.iu.habahram.ducksservice.model.Guitar;
import edu.iu.habahram.ducksservice.model.Type;
import edu.iu.habahram.ducksservice.model.Wood;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository
        extends CrudRepository<Guitar, Integer>, QueryByExampleExecutor<Guitar> {
    Optional<Guitar> findBySerialNumber(String serialNumber);

    Optional<Guitar> findByModel(String model);
//    List<Guitar> findByBuilderAndTypeAndBackWoodAndTopWood(
//            Builder builder,
//            Type type,
//            Wood backWood,
//            Wood topWood
//    );
}
