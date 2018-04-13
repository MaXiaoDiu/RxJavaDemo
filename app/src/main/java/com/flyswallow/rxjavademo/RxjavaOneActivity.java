package com.flyswallow.rxjavademo;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * RxJava 1.X
 */
public class RxjavaOneActivity extends AppCompatActivity {

    private Observer<String> observer;
    private  Observable observable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava_one);

        //创建观察者
        CreateObserver();
        //创建被观察者
        BaseObservable();
        //just(T...): 将传入的参数依次发送出来。
        JustSend();
        //from(T[])/from(Iterable<? extends T>) :
        //将传入的数组或 Iterable 拆分成具体对象后，依次发送出来。
        FromSend();
        //不完整定义的回调，RxJava 会自动根据定义创建出 Subscriber 。
        Imperfect();

    }

    /**
     * 创建观察者
     */
    private void CreateObserver() {

        observer = new Observer<String>() {
            @Override
            public void onNext(String s) {

                Log.d("===Item", "Item: " + s);
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }
        };
    }

    /**
     * 最基本的定义被监听者
     */
    private void BaseObservable() {

        observable = Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {

                subscriber.onNext("Hello");
                subscriber.onNext("Hi");
                subscriber.onNext("Aloha");
                subscriber.onCompleted();
            }
        });
        observable.subscribe(observer);
    }

    /**
     * just(T...): 将传入的参数依次发送出来。
     */
    private void JustSend() {

        observable = Observable.just("Hello","Hi","Aloha");
        observable.subscribe(observer);
    }

    /**
     * from(T[])/from(Iterable<? extends T>) :
     * 将传入的数组或 Iterable 拆分成具体对象后，依次发送出来。
     */
    private void FromSend() {
        String[] words = {"Hello", "Hi", "Aloha"};
        Observable observable = Observable.from(words);
        observable.subscribe(observer);
    }

    /**
     * 不完整定义的回调，RxJava 会自动根据定义创建出 Subscriber
     */
    private void Imperfect() {

        Action1<String> onNextAction = new Action1<String>() {
            // onNext()
            @Override
            public void call(String s) {
                Log.d("==tag", s);
            }
        };
        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            // onError()
            @Override
            public void call(Throwable throwable) {
                // Error handling
            }
        };
        Action0 onCompletedAction = new Action0() {
            // onCompleted()
            @Override
            public void call() {
                Log.d("==tag", "completed");
            }
        };
        // 自动创建 Subscriber ，并使用 onNextAction 来定义 onNext()
        observable.subscribe(onNextAction);
        // 自动创建 Subscriber ，并使用 onNextAction 和 onErrorAction 来定义 onNext() 和 onError()
        observable.subscribe(onNextAction, onErrorAction);
        // 自动创建 Subscriber ，并使用 onNextAction、 onErrorAction 和 onCompletedAction 来定义 onNext()、 onError() 和 onCompleted()
        observable.subscribe(onNextAction, onErrorAction, onCompletedAction);
    }

    /**
     * 案例1 打印字符串数组
     */
    private void PrintString()
    {
        String[] name = {"cyy","lrf","lh"};
        Observable.from(name)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {

                        Log.d("==tag",s);
                    }
                });
    }



}
