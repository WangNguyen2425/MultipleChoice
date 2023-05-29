package com.demo.app.service;

import com.demo.app.dto.test.TestRequest;
import com.demo.app.dto.test.TestDetailResponse;
import com.demo.app.dto.test.TestResponse;
import jakarta.transaction.Transactional;

import java.util.List;

public interface TestService {
    TestDetailResponse createTestFirstStep(TestRequest request);

    @Transactional
    void createTestSecondStep(TestDetailResponse response);

    List<TestResponse> getAllTests();
}
