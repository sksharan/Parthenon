package com.github.sksharan.parthenon.server.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.github.sksharan.parthenon.server.entity.PlayerEntity;

@Repository
public interface PlayerRepository extends CrudRepository<PlayerEntity, Long> {

    public PlayerEntity findByName(String name);
    public void deleteByName(String name);

}
