package com.hackaton.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hackaton.models.TestCategory;

@Repository
public interface TestCategoryRepository extends JpaRepository<TestCategory, Integer> {

}
