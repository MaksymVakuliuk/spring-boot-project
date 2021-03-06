package com.spring.boot.project.demo.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.google.common.collect.ImmutableList;
import com.spring.boot.project.demo.model.Product;
import com.spring.boot.project.demo.model.Review;
import com.spring.boot.project.demo.model.User;
import com.spring.boot.project.demo.repository.ProductRepository;
import com.spring.boot.project.demo.repository.ReviewRepository;
import com.spring.boot.project.demo.repository.UserRepository;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ReviewServiceImplTest {
    private static final ImmutableList<String> EXPECTED_MOST_USED_WORDS
            = ImmutableList.of("review", "text");
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private ReviewServiceImpl reviewServiceImpl;
    private List<Review> reviewList;
    private List<String> allText;

    @Before
    public void setup() {
        reviewList = new ArrayList<>();
        Review review1 = new Review();
        review1.setId(1L);
        User user1 = new User();
        user1.setUserId("userId1");
        user1.setProfileName("user1");
        review1.setUser(user1);
        Product product1 = new Product();
        product1.setProductId("productId1");
        review1.setProduct(product1);
        review1.setHelpfulnessNumerator(1);
        review1.setHelpfulnessDenominator(1);
        review1.setScore(5);
        review1.setTime(LocalDateTime.ofInstant(
                Instant.ofEpochMilli(
                        1303862400L), ZoneId.systemDefault()));
        review1.setSummary("Good Quality Dog Food");
        review1.setText("Review 1 text.");
        reviewList.add(review1);
        Review review2 = new Review();
        review2.setId(2L);
        User user2 = new User();
        user2.setUserId("userId2");
        user2.setProfileName("user2");
        review2.setUser(user1);
        Product product2 = new Product();
        product2.setProductId("productId2");
        review2.setProduct(product1);
        review2.setHelpfulnessNumerator(1);
        review2.setHelpfulnessDenominator(1);
        review2.setScore(5);
        review2.setTime(LocalDateTime.ofInstant(
                Instant.ofEpochMilli(
                        1303862770L), ZoneId.systemDefault()));
        review2.setSummary("Good summary");
        review2.setText("Review 2 text.");
        reviewList.add(review2);
        allText = List.of("Review 1 text. First. ",
                "Review 2 text. Second second.",
                "Review 3 text. Third.",
                "Review 4 text. Fourth.",
                "Review 5 text. Fifth.");
        Review review3 = new Review();
        review3.setId(2L);
        User user3 = new User();
        user3.setProfileName("user");
        review3.setUser(user1);
        Product product3 = new Product();
        product3.setProductId("productId2");
        review3.setProduct(product1);
        review3.setHelpfulnessNumerator(1);
        review3.setHelpfulnessDenominator(1);
        review3.setScore(5);
        review3.setTime(LocalDateTime.ofInstant(
                Instant.ofEpochMilli(
                        1303862770L), ZoneId.systemDefault()));
        review3.setSummary("my users");
        review3.setText("Review from logined user text.");
        reviewList.add(review3);
    }

    @Test
    public void saveFromLo() {
        Review expectedReview = reviewList.get(2);
        Mockito.when(reviewRepository.save(expectedReview)).thenReturn(expectedReview);
        Review saveReview = reviewServiceImpl.save(expectedReview);
        assertEquals(expectedReview, saveReview);
    }

    @Test
    public void save() {
        Review expectedReview = reviewList.get(0);
        Mockito.when(reviewRepository.save(expectedReview)).thenReturn(expectedReview);
        Review saveReview = reviewServiceImpl.save(expectedReview);
        assertEquals(expectedReview, saveReview);
    }

    @Test
    public void saveAll() {
        Mockito.when(reviewRepository.saveAll(reviewList)).thenReturn(reviewList);
        List<Review> reviewListInDb = reviewServiceImpl.saveAll(reviewList);
        assertEquals(reviewList, reviewListInDb);
    }

    @Test
    public void findById() {
        Review expectedReview = reviewList.get(0);
        Mockito.when(reviewRepository.findById(1L)).thenReturn(Optional.of(expectedReview));
        Review byId = reviewServiceImpl.findById(1L);
        assertEquals(expectedReview, byId);
    }

    @Test
    public void findAll() {
        Mockito.when(reviewRepository.findAll()).thenReturn(reviewList);
        List<Review> reviewListFromDb = reviewServiceImpl.findAll();
        assertEquals(reviewList, reviewListFromDb);
    }

    @Test
    public void findMostUsedWords() {
        Mockito.when(reviewRepository.getAllText()).thenReturn(allText);
        List<String> mostUsedWords = reviewServiceImpl.findMostUsedWords(2);
        assertEquals(EXPECTED_MOST_USED_WORDS, mostUsedWords);
    }

    @Test
    public void findReviewsSetOfProduct() {
        List<Review> listReviewByUser = reviewList.stream()
                .filter(review -> review.getProduct().getProductId().equals("productId1"))
                .collect(Collectors.toList());
        Mockito.when(reviewRepository.findByProduct_ProductId("productId1"))
                .thenReturn(listReviewByUser);
        Set<Review> setReviewsByUser = reviewServiceImpl
                .findReviewsSetOfProduct("productId1");
        for (Review review : setReviewsByUser) {
            assertNull(review.getUser().getReviews());
            assertNull(review.getProduct());
        }
    }

    @Test
    public void findReviewSetOfUser() {
        List<Review> listReviewByUser = reviewList.stream()
                .filter(review -> review.getUser().getUserId().equals("user1id"))
                .collect(Collectors.toList());
        Mockito.when(reviewRepository.findByUser_UserId("user1id"))
                .thenReturn(listReviewByUser);
        Set<Review> setReviewByUser = reviewServiceImpl
                .findReviewSetOfUser("user1id");
        for (Review review : setReviewByUser) {
            assertNull(review.getUser());
            assertNull(review.getProduct().getReviews());
        }
    }
}
