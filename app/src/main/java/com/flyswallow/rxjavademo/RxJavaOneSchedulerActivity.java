package com.flyswallow.rxjavademo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

public class RxJavaOneSchedulerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_one_scheduler);
        SchedulerTwo();

    }

    private void SchedulerTwo() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d( "rx_newThread" , Thread.currentThread().getName()  );
                rx_Scheduler();
            }
        }).start();
    }

    private void rx_Scheduler() {

        Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        Log.d( "==Log==rx_call" , Thread.currentThread().getName()  );

                        subscriber.onNext( "dd");
                        subscriber.onCompleted();
                    }
                })
                .map(new Func1<String, String >() {
                    @Override
                    public String call(String s) {
                        Log.d( "==Log==rx_map" , Thread.currentThread().getName()  );
                        return s + "88";
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.d( "==Log==rx_subscribe" , Thread.currentThread().getName()  );
                    }
                }) ;
    }

    private void SchedulerOne() {

        Observable.create(new rx.Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

                Log.d("==Log==rx_call",Thread.currentThread().getName());
                subscriber.onNext("dd");
                subscriber.onCompleted();

            }
        })
        .map(new Func1<String, String>() {
            @Override
            public String call(String s) {

                Log.d( "==Log==rx_map" , Thread.currentThread().getName()  );
                return s + "rx_map";
            }
        })
        .subscribe(new Action1<String>() {
            @Override
            public void call(String s) {

                Log.d( "==Log==rx_subscribe" , Thread.currentThread().getName()  );
            }
        });
    }

}
