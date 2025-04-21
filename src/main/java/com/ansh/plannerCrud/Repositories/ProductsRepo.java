package com.ansh.plannerCrud.Repositories;

import com.ansh.plannerCrud.Modules.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepo  extends JpaRepository<Products, Integer> {
}
