package com.upc.gessi.automation.domain.respositories;

import com.upc.gessi.automation.domain.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {

    User findByUsername(String name);

    Boolean existsByUsername(String username);
}
