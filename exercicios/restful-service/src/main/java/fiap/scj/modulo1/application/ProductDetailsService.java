package fiap.scj.modulo1.application;

import java.io.Serializable;
import java.util.List;

import fiap.scj.modulo1.domain.ProductDetails;
import fiap.scj.modulo1.infrastructure.ProductDetailsServiceException;

public interface ProductDetailsService extends Serializable{
	List<ProductDetails> search(String keyword) throws ProductDetailsServiceException;

	ProductDetails create(ProductDetails productDetails) throws ProductDetailsServiceException;

	ProductDetails retrieve(Long id) throws ProductDetailsServiceException;

	ProductDetails update(Long id, ProductDetails productDetails) throws ProductDetailsServiceException;

    void delete(Long id) throws ProductDetailsServiceException;
}
