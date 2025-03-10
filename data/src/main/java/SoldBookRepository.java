package com.hd.vbookstore.data;

import com.hd.vbookstore.domain.SoldBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SoldBookRepository extends JpaRepository<SoldBook, Long> {

}
