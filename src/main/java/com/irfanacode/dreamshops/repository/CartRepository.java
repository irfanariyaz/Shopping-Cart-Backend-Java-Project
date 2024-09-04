package com.irfanacode.dreamshops.repository;

import com.irfanacode.dreamshops.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long>{

    Cart findByUserId(Long userId);
}
