package ru.geekbrains.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.geekbrains.controller.ProductDto;
import ru.geekbrains.persist.model.Category;
import ru.geekbrains.persist.model.Picture;
import ru.geekbrains.persist.model.Product;
import ru.geekbrains.persist.ProductDao;
import service.PictureService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl {

    private ProductDao productDao;

    private final PictureService pictureService;

    @Autowired
    public ProductServiceImpl(ProductDao productDao, PictureService pictureService) {
        this.productDao = productDao;
        this.pictureService = pictureService;
    }

   public List<Product> findAll(){return productDao.findAll();}

   public Optional<Product> findById(Long id){
        return productDao.findById(id);
   }

   @Transactional
   public void save(ProductDto productDto) throws NotFoundException {

       Product product = (productDto.getId() != null) ? productDao.findById(productDto.getId())
               .orElseThrow(() -> new NotFoundException("")) : new Product();

       Category category = new Category(productDto.getCategory().getId(), productDto.getCategory().getName());
       product.setName(productDto.getName());
       product.setCategory(category);
       product.setPrice(productDto.getPrice());

       if (productDto.getNewPictures() != null) {
           for (MultipartFile newPicture : productDto.getNewPictures()) {
               try {
                   product.getPictures().add(new Picture(null,
                           newPicture.getOriginalFilename(),
                           newPicture.getContentType(),
                           pictureService.createPicture(newPicture.getBytes()),
                           product
                   ));
               } catch (IOException ex) {
                   throw new RuntimeException(ex);
               }
           }
       }

       productDao.save(product);
   }

    public List<Product> findByPriceBetween(Integer min, Integer max) {
        return productDao.findByPriceBetween(min, max);
    }

    public List<Product> findByPriceGreaterThanEqual(Integer min) {
        return productDao.findByPriceGreaterThanEqual(min);

    }

    public List<Product> findByPriceLessThanEqual(Integer max) {
        return productDao.findByPriceLessThanEqual(max);
    }

    public void deleteById(Long id){
        productDao.deleteById(id);
    }
}
