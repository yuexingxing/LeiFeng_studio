package com.tajiang.leifeng.common.mock;

import com.tajiang.leifeng.common.retrofit.RetrofitService;



import java.io.IOException;
import java.util.concurrent.TimeUnit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;
import rx.observers.TestSubscriber;

/**
 * Created by Admins on 2017/2/9.
 */

public class MyServiceTest {
    private final NetworkBehavior behavior = NetworkBehavior.create();
    private final rx.observers.TestSubscriber<String> testSubscriber = TestSubscriber.create();
    private RetrofitService mockService;


    public void setUp() throws Exception {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("http://example.com").build();

        MockRetrofit mockRetrofit = new MockRetrofit.Builder(retrofit)
                .networkBehavior(behavior).build();

        final BehaviorDelegate<RetrofitService> delegate = mockRetrofit.create(RetrofitService.class);

//        mockService = new MyServiceMock(delegate);
    }


    public void testSuccessResponse() throws Exception {
        givenNetworkFailurePercentIs(0);

//        mockService.name().subscribe(testSubscriber);

        testSubscriber.assertValue("test");
        testSubscriber.assertCompleted();
    }


    public void testFailureResponse() throws Exception {
        givenNetworkFailurePercentIs(100);

//        mockService.name().subscribe(testSubscriber);

        testSubscriber.assertNoValues();
        testSubscriber.assertError(IOException.class);
    }

    private void givenNetworkFailurePercentIs(int failurePercent) {
        behavior.setDelay(0, TimeUnit.MILLISECONDS);
        behavior.setVariancePercent(0);
        behavior.setFailurePercent(failurePercent);
    }
}