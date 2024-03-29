package com.github.sksharan.parthenon.server.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.github.sksharan.parthenon.server.entity.ItemStackEntity;

@Repository
public interface ItemStackRepository extends CrudRepository<ItemStackEntity, Long> {

    public List<ItemStackEntity> findByNameAndAmountAndType(String name, int amount, String type);

}
