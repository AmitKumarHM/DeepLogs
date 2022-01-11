package edu.nitdelhi.deeplogs.repository;

import org.springframework.data.repository.CrudRepository;

import edu.nitdelhi.deeplogs.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {

}
