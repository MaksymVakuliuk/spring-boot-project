package com.spring.boot.project.demo.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private AmazonUser amazonUser;
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
    @Column(name = "helpfulness_numerator", unique = true, nullable = false)
    private int helpfulnessNumerator;
    @Column(name = "helpfulness_nenominator", unique = true, nullable = false)
    private int helpfulnessDenominator;
    @Column(name = "score", unique = true, nullable = false)
    private int score;
    @Column(name = "time", nullable = false)
    private LocalDateTime time;
    @Column(name = "tummary", nullable = false)
    private String summary;
    @Column(name = "text", nullable = false)
    private String text;
}