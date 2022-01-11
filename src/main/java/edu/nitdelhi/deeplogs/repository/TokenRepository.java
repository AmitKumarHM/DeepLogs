package edu.nitdelhi.deeplogs.repository;

import org.springframework.data.repository.CrudRepository;

import edu.nitdelhi.deeplogs.entity.Token;

public interface TokenRepository extends CrudRepository<Token, Long> {

}
