package org.example.vedicvastram.respository;

import org.example.vedicvastram.entity.ProductStatus;
import org.example.vedicvastram.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findBySellerId(Long sellerId);

    List<Product> findByStatus(ProductStatus status);

    @Query("SELECT p FROM Product p WHERE "
            + "(:status IS NULL OR p.status = :status) AND "
            + "(:brand IS NULL OR p.brand = :brand) AND "
            + "(:color IS NULL OR p.color = :color) AND "
            + "(:fabric IS NULL OR p.fabric = :fabric) AND "
            + "(:size IS NULL OR p.availableSizes LIKE %:size%)")
    List<Product> filterProducts(
            @Param("status") ProductStatus status,
            @Param("brand") String brand,
            @Param("color") String color,
            @Param("fabric") String fabric,
            @Param("size") String size
    );
}
