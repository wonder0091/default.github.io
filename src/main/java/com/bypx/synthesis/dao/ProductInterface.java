package com.bypx.synthesis.dao;

import com.bypx.synthesis.bean.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductInterface {
    public void upProduct(Product product);
    public void editProduct(Product product);
    public void delProduct(String Sort);
    public Product info(String Sort);
    public List<Product> showImg(Product product);

}
