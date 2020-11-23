package com.spring.boot.project.demo.service.impl;

import com.spring.boot.project.demo.model.Review;
import com.spring.boot.project.demo.service.DbService;
import com.spring.boot.project.demo.service.ProductService;
import com.spring.boot.project.demo.service.ReviewService;
import com.spring.boot.project.demo.service.UserService;
import com.spring.boot.project.demo.unit.FileReader;
import com.spring.boot.project.demo.unit.Parser;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReviewDataServiceImpl implements DbService {
    private final FileReader fileReader;
    private final Parser<Review> parser;
    private final ReviewService reviewService;
    private final ProductService productService;
    private final UserService userService;

    @Override
    public void initializeDb(String dataFilePath) {
        List<String> reviewsStringList = fileReader.readLines(dataFilePath);
        List<Review> reviewsList = parser.parse(reviewsStringList);
        reviewService.saveAll(reviewsList);
    }

    @Override
    public void clearDb() {
        reviewService.deleteAll();
        productService.deleteAll();
        userService.deleteAll();
    }
}
