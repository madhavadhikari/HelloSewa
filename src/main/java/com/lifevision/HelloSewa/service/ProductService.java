package com.lifevision.HelloSewa.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.lifevision.HelloSewa.exception.NotFoundException;
import com.lifevision.HelloSewa.model.Category;
import com.lifevision.HelloSewa.model.Product;
import com.lifevision.HelloSewa.model.ProductCategory;
import com.lifevision.HelloSewa.model.ProductImages;
import com.lifevision.HelloSewa.repository.CategoryRepository;
import com.lifevision.HelloSewa.repository.ProductCategoryRepository;
import com.lifevision.HelloSewa.repository.ProductImagesRepository;
import com.lifevision.HelloSewa.repository.ProductRepository;
import com.lifevision.HelloSewa.request.ProductCreationRequest;
import com.lifevision.HelloSewa.request.ProductEditRequest;
import com.lifevision.HelloSewa.response.ProductImagesResponse;
import com.lifevision.HelloSewa.response.ProductResponse;
import com.lifevision.HelloSewa.utils.ProductStatus;

@Service
public class ProductService {
	private static final Logger LOG = LoggerFactory.getLogger(ProductService.class);
	
	@Autowired
	private Environment environment;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	ProductImagesRepository productImagesRepository;
	
	@Autowired 
	ProductCategoryRepository productCategoryRepository;
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	/**
	 * for adding product in the system.
	 * @param loginId
	 * @param token
	 * @param productCreationRequest
	 */
	public Long addProduct(Long loginId, String token, ProductCreationRequest productCreationRequest) {
		
		commonService.isValidToken(loginId, token);
		
		//saving product detail
		Product product = new Product();
		
		product.setAddedBy(loginId);
		product.setStatus(ProductStatus.ACTIVE);
		product.setName(productCreationRequest.getName());
		product.setStock(productCreationRequest.getStock());
		product.setBrandId(productCreationRequest.getBrandId());
		product.setPrice(productCreationRequest.getPrice());
		product.setSellingPrice(commonService.getPriceAfterDiscount(productCreationRequest.getPrice(), productCreationRequest.getDiscount()));
		product.setDescription(productCreationRequest.getDescription());
		product.setDiscount(productCreationRequest.getDiscount());
		product.setCreatedDate(new Date());
		
		if(productCreationRequest.getThumbnail() != null)
			product.setThumbnail(commonService.uploadImage(productCreationRequest.getThumbnail(),
					commonService.generateImageName(),
					environment.getProperty("image.directory.thumbnail")));
		
		productRepository.save(product);
		
		//saving product images
		if(!productCreationRequest.getImages().isEmpty() || productCreationRequest.getImages() == null) {
			LOG.info("----------->No. of images: "+productCreationRequest.getImages().size());
			List<ProductImages> productImageList = new ArrayList<>();
			for(String imgCode:productCreationRequest.getImages()) {
				ProductImages productImage = new ProductImages();
				productImage.setCreatedDate(new Date());
				productImage.setLink(commonService.uploadImage(imgCode,
						commonService.generateImageName(),
						environment.getProperty("image.directory.product")));
				productImage.setProduct(product);
				
				productImageList.add(productImage);
			}
			productImagesRepository.saveAll(productImageList);
			
			
		}
		
		//saving the value of product Id and Category Id in Product Category table.
		ProductCategory  productCategory = new ProductCategory();
			
		productCategory.setProductId(product.getId());
		productCategory.setCategoryId(productCreationRequest.getCategoryId());
		productCategoryRepository.save(productCategory);
		
		
		setRelatedCatergoryIds(product, productCreationRequest.getCategoryId());
		return product.getId();
			
	}
	
	private void setRelatedCatergoryIds(Product product, int id) {
		
		ProductCategory  productCategory = new ProductCategory();
		
		Category category = categoryRepository.getOne(id);
		
		if(category.getParentCategoryId() != 0) {	
		productCategory.setCategoryId(category.getParentCategoryId());
		
		productCategory.setProductId(product.getId());
		productCategoryRepository.save(productCategory);
		
		setRelatedCatrgoryIdss(product, category.getParentCategoryId());
	
		}
	}

	private void setRelatedCatrgoryIdss(Product product, int id) {

		ProductCategory  productCategory = new ProductCategory();
		
		Category category = categoryRepository.getOne(id);
		if(category.getParentCategoryId() != 0) {
			
		productCategory.setCategoryId(category.getParentCategoryId());
		
		productCategory.setProductId(product.getId());
		productCategoryRepository.save(productCategory);
		setRelatedCatrgoryIdsss(product, category.getParentCategoryId());
		}
		
	}

	private void setRelatedCatrgoryIdsss(Product product, int parentCategoryId) {
		
		ProductCategory  productCategory = new ProductCategory();
		Category category = categoryRepository.getOne(parentCategoryId);
		if(category.getParentCategoryId() != 0) {
		productCategory.setCategoryId(category.getParentCategoryId());
		
		productCategory.setProductId(product.getId());
		productCategoryRepository.save(productCategory);		
	}
	}

	/**
	 * for getting particular product with associated images.
	 * @param id
	 * @param loginId
	 * @param token
	 * @return ProductResponse
	 */
	public ProductResponse getProduct(Long id) {
		
		ProductCategory productCategory = productCategoryRepository.getOne(id);
		
		Product product = productRepository.getOne(id);
		if(product == null) {
			throw new NotFoundException("Product Not found.");
		}
		LOG.info("Product: "+ product.getImages());
		
		ProductResponse productResponse = new ProductResponse();
		
		//setting overall Response
		productResponse.setAddedBy(product.getAddedBy());
		productResponse.setId(product.getId());
		productResponse.setCreatedDate(product.getCreatedDate());
		productResponse.setName(product.getName());
		productResponse.setCategoryId(productCategory.getCategoryId());
		productResponse.setStock(product.getStock());
		productResponse.setBrandId(product.getBrandId());
		productResponse.setSavedAmmount(product.getPrice() - product.getSellingPrice());
		productResponse.setDescription(product.getDescription());
		productResponse.setPrice(product.getPrice());
		productResponse.setSellingPrice(product.getSellingPrice());
		productResponse.setDiscount(product.getDiscount());
		productResponse.setThumbnail(product.getThumbnail());

		List<ProductImages> productImages =  productImagesRepository.findByProductId(id);
		
		LOG.info("----------->No. of images: "+productImages.size());
		
		List<ProductImagesResponse> listofimagesresponse = new ArrayList<>();
		for (ProductImages i : productImages ) {
			
			ProductImagesResponse imageresponse = new ProductImagesResponse();
			
			//setting image links response
			imageresponse.setId(i.getId());
			imageresponse.setLink(i.getLink());
			
			listofimagesresponse.add(imageresponse);	
		}
		productResponse.setImages(listofimagesresponse);
		return productResponse;
	}
	
	/**
	 * for getting all product in the system.
	 * @param loginId
	 * @param token
	 * @return List<ProductResponse>
	 */
	public List<ProductResponse> getAllProducts() {

		List<ProductResponse> allProducts = new ArrayList<>();
		
		List<Product> products = productRepository.findAll();
		
		products.forEach(p -> {
			
			ProductResponse productResponse = new ProductResponse();
			
			//setting Response
			productResponse.setAddedBy(p.getAddedBy());
			productResponse.setId(p.getId());
			productResponse.setCreatedDate(p.getCreatedDate());
			productResponse.setName(p.getName());
			productResponse.setBrandId(p.getBrandId());
			productResponse.setDescription(p.getDescription());
			productResponse.setPrice(p.getPrice());
			productResponse.setStock(p.getStock());
			productResponse.setSellingPrice(p.getSellingPrice());
			productResponse.setDiscount(p.getDiscount());
			productResponse.setThumbnail(p.getThumbnail());
			
			allProducts.add(productResponse);
		});
		return allProducts;
	}
	
	/**
	 * for editing product in the database.
	 * @param loginId
	 * @param token
	 * @param productEditRequest
	 * @return Product
	 */
	public void editProduct(Long loginId, String token, ProductEditRequest productEditRequest) {
		
		commonService.isValidToken(loginId, token);
		
		Product product = productRepository.getOne(productEditRequest.getId());
		if(product == null) {
			throw new NotFoundException("No product found associated to this Id");
		}
				
		ProductCategory productCategory = productCategoryRepository.getOne(productEditRequest.getId());
		
		product.setName(productEditRequest.getName());
		productCategory.setCategoryId(productEditRequest.getCategoryId());
		product.setStock(productEditRequest.getStock());
		product.setStatus(productEditRequest.getStatus());
		product.setBrandId(productEditRequest.getBrandId());
		product.setPrice(productEditRequest.getPrice());
		product.setSellingPrice(productEditRequest.getSellingPrice());
		product.setDescription(productEditRequest.getDescription());
		product.setDiscount(productEditRequest.getDiscount());
		product.setModifiedDate(new Date());
		
		productRepository.save(product);
		
	}

	/**
	 * for getting product according to brand.
	 * @param brandId
	 * @return List<ProductResponse>
	 */
	public List<ProductResponse> getProductsByBrand(int brandId) {
		
		List<ProductResponse> allProducts = new ArrayList<>();
		
		List<Product> products = productRepository.findByBrandId(brandId);
		
		products.forEach(p -> {
			ProductResponse productResponse = new ProductResponse();
			
			//setting Response
			productResponse.setAddedBy(p.getAddedBy());
			productResponse.setId(p.getId());
			productResponse.setCreatedDate(p.getCreatedDate());
			productResponse.setName(p.getName());
			productResponse.setBrandId(p.getBrandId());
			productResponse.setDescription(p.getDescription());
			productResponse.setPrice(p.getPrice());
			productResponse.setSellingPrice(p.getSellingPrice());
			productResponse.setDiscount(p.getDiscount());
			productResponse.setThumbnail(p.getThumbnail());
			
			allProducts.add(productResponse);
		});
		
		return allProducts;
	}

	/** 
	 * get products by category.
	 * @param categoryId
	 * @return List<ProductResponse>
	 */
	public List<ProductResponse> getProductsByCategory(int categoryId) {
		
		List<ProductResponse> finalResponse = new ArrayList<>();
		
		List<ProductCategory> products = productCategoryRepository.findByCategoryId(categoryId);
		
		products.forEach(p -> {
			
			ProductResponse productResponse = new ProductResponse();
			
			Product product = productRepository.getOne(p.getProductId());
			
			//setting Product response.
			productResponse.setAddedBy(product.getAddedBy());
			productResponse.setId(product.getId());
			productResponse.setCreatedDate(product.getCreatedDate());
			productResponse.setName(product.getName());
			productResponse.setBrandId(product.getBrandId());
			productResponse.setDescription(product.getDescription());
			productResponse.setPrice(product.getPrice());
			productResponse.setSellingPrice(product.getSellingPrice());
			productResponse.setDiscount(product.getDiscount());
			productResponse.setThumbnail(product.getThumbnail());
			
			finalResponse.add(productResponse);
		});
		
		return finalResponse;
	}
}
