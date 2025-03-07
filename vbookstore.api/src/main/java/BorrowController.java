package com.hd.vbookstore.api;


import com.hd.vbookstore.commons.BorrowDto;
import com.hd.vbookstore.commons.BorrowedBookResponseDto;
import com.hd.vbookstore.commons.UpdateBorrowDto;
import com.hd.vbookstore.core.services.BorrowedBookService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/borrow", consumes = "application/json")
public class BorrowController {

    BorrowedBookService borrowedBookService;


    public BorrowController(
            BorrowedBookService borrowedBookService
    ) {
        this.borrowedBookService = borrowedBookService;
    }

    @PostMapping(path = "/set")
    public Optional<BorrowedBookResponseDto> setBorrow(
            @RequestBody BorrowDto borrowDto
           ) {
        return Optional.ofNullable(borrowedBookService.setBorrow(borrowDto.getBook_id(), borrowDto.getStart_date(), borrowDto.getEnd_date()));
        }

    @PostMapping("/update")
    public BorrowedBookResponseDto updateBorrow(@RequestBody UpdateBorrowDto updateBorrowDto) {
        return borrowedBookService.updateBorrow(updateBorrowDto.getBorrow_id(), updateBorrowDto.getStatus());
    }
}
