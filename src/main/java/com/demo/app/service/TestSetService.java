package com.demo.app.service;

import com.demo.app.dto.testset.TestSetDetailResponse;
import com.demo.app.dto.testset.TestSetRequest;
import com.demo.app.dto.testset.TestSetResponse;
import jakarta.transaction.Transactional;

import java.util.List;

public interface TestSetService {
    @Transactional
    void createTestSetFromTest(int testId, TestSetRequest request) throws InterruptedException;

    List<TestSetResponse> getAllTestSet();

    TestSetDetailResponse getTestSetDetail(int testSetId);
}
