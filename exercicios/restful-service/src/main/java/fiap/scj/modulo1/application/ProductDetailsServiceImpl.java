package fiap.scj.modulo1.application;

import static fiap.scj.modulo1.infrastructure.ProductDetailsServiceException.CREATE_OPERATION_ERROR;
import static fiap.scj.modulo1.infrastructure.ProductDetailsServiceException.DELETE_OPERATION_ERROR;
import static fiap.scj.modulo1.infrastructure.ProductDetailsServiceException.INVALID_PARAMETER_ERROR;
import static fiap.scj.modulo1.infrastructure.ProductDetailsServiceException.PRODUCT_NOT_FOUND_ERROR;
import static fiap.scj.modulo1.infrastructure.ProductDetailsServiceException.RETRIEVE_OPERATION_ERROR;
import static fiap.scj.modulo1.infrastructure.ProductDetailsServiceException.SEARCH_OPERATION_ERROR;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import fiap.scj.modulo1.domain.ProductDetails;
import fiap.scj.modulo1.domain.repository.ProductDetailsRepository;
import fiap.scj.modulo1.infrastructure.ProductDetailsServiceException;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(propagation = Propagation.REQUIRED)
@Slf4j
public class ProductDetailsServiceImpl implements ProductDetailsService {

	private final ProductDetailsRepository repository;
    private final ObjectMapper objectMapper;
	
    @Autowired
    public ProductDetailsServiceImpl(ProductDetailsRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }
    
    @Override
	public List<ProductDetails> search(String keyword) throws ProductDetailsServiceException {
    	log.info("Searching products details for keyword={}", keyword);
        try {
            List<ProductDetails> result = new ArrayList<>();

            if (keyword == null || keyword.isEmpty()) {
                log.debug("No keyword specified, listing all products");
                result.addAll(repository.findAll());
            } else {
                log.debug("Finding products by name or description");
                result.addAll(repository.findByKeyOrDescriptionAllIgnoreCase(keyword, keyword));
            }

            return result;
        } catch (Exception e) {
            log.error("Error searching product", e);
            throw new ProductDetailsServiceException(SEARCH_OPERATION_ERROR, e);
        }
	}

	@Override
	public ProductDetails create(ProductDetails productDetails) throws ProductDetailsServiceException {
		log.info("Creating product details({})", productDetails);
        try {
            if (productDetails == null) {
                log.error("Invalid product");
                throw new ProductDetailsServiceException(INVALID_PARAMETER_ERROR, null);
            }
            ProductDetails result = repository.save(productDetails);
            return result;
        } catch (Exception e) {
            log.error("Error creating product", e);
            throw new ProductDetailsServiceException(CREATE_OPERATION_ERROR, e);
        }
	}

	@Override
	public ProductDetails retrieve(Long id) throws ProductDetailsServiceException {
		log.info("Retrieving product details for id={}", id);
        try {
            if (id == null) {
                log.error("Invalid id");
                throw new ProductDetailsServiceException(INVALID_PARAMETER_ERROR, null);
            }
            ProductDetails result = repository.findById(id).get();
            return result;
        } catch (Exception e) {
            log.error("Error creating product", e);
            throw new ProductDetailsServiceException(RETRIEVE_OPERATION_ERROR, e);
        }
	}

	@Override
	public ProductDetails update(Long id, ProductDetails productDetails) throws ProductDetailsServiceException {
		log.info("Updating product details ({}) for id={}", productDetails, id);
        try {
            if (id == null || productDetails == null) {
                log.error("Invalid id or product");
                throw new ProductDetailsServiceException(INVALID_PARAMETER_ERROR, null);
            }
            if (!repository.existsById(id)) {
                log.debug("Product details not found for id={}", id);
                throw new ProductDetailsServiceException(PRODUCT_NOT_FOUND_ERROR, null);
            }
            ProductDetails result = repository.save(productDetails);
            return result;
        } catch (Exception e) {
            log.error("Error creating product", e);
            throw new ProductDetailsServiceException(RETRIEVE_OPERATION_ERROR, e);
        }
	}

	@Override
	public void delete(Long id) throws ProductDetailsServiceException {
		log.info("Deleting product details for id={}", id);
        try {
            if (id == null) {
                log.error("Invalid id or product");
                throw new ProductDetailsServiceException(INVALID_PARAMETER_ERROR, null);
            }
            if (!repository.existsById(id)) {
                log.debug("Product detail not found for id={}", id);
                throw new ProductDetailsServiceException(PRODUCT_NOT_FOUND_ERROR, null);
            }
            repository.deleteById(id);
        } catch (Exception e) {
            log.error("Error creating product detail", e);
            throw new ProductDetailsServiceException(DELETE_OPERATION_ERROR, e);
        }
    }

}
