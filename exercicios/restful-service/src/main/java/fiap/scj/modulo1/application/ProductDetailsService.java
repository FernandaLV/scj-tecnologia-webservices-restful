package fiap.scj.modulo1.application;

import java.io.Serializable;
import java.util.List;

import fiap.scj.modulo1.domain.ProductDetails;
import fiap.scj.modulo1.infrastructure.ProductServiceException;

public interface ProductDetailsService extends Serializable{
	List<ProductDetails> search(String keyword) throws ProductServiceException;

	ProductDetails create(ProductDetails productDetails) throws ProductServiceException;

	ProductDetails retrieve(Long id) throws ProductServiceException;

	ProductDetails update(Long id, ProductDetails productDetails) throws ProductServiceException;

    void delete(Long id) throws ProductServiceException;
}
