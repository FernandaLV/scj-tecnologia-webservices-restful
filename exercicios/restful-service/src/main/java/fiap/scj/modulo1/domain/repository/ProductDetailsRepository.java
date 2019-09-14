package fiap.scj.modulo1.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fiap.scj.modulo1.domain.ProductDetails;

@Repository
public interface ProductDetailsRepository extends JpaRepository<ProductDetails, Long> {
	 List<ProductDetails> findByNameOrDescriptionAllIgnoreCase(String name, String description);
}
