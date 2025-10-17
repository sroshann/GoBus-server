package com.group3.goBus.Repository;

import com.group3.goBus.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository< User, String > {

    User findByUsername( String username );
    User findByEmail( String email );
    User findByPhoneNumber( String phoneNumber );

}
