package mk.learning.repository;

import mk.learning.dao.OAuthClientDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OAuthClientDetailsRepo extends JpaRepository<OAuthClientDetails, String> {


}
