package airbnb;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="affiliateproducts", path="affiliateproducts")
public interface AffiliateproductRepository extends PagingAndSortingRepository<Affiliateproduct, Long>{


}
