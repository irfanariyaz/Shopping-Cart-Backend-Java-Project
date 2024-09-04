package com.irfanacode.dreamshops.repository;

import com.irfanacode.dreamshops.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{
    void deleteAllByCartId(Long id);
}
