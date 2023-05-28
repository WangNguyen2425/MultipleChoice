package com.demo.app.service;

import com.demo.app.dto.test.TestRequest;
import com.demo.app.dto.test.TestResponse;

public interface TestService {
    TestResponse createTestFirstStep(TestRequest request);
}
