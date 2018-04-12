package com.flyswallow.rxjavademo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.reactivestreams.Subscriber;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_toSecActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initview();
        //创建观察者
        Observer<String> observer = new Observer<String>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Log.d("===Return Name", "Item: " + s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        //创建被观察者
        Observable observable = Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {

                emitter.onNext("Hello");
                emitter.onNext("Hi");
                emitter.onNext("Aloha");
                emitter.onComplete();
            }
        });

        observable.subscribe(observer);

    }

    private void initview() {

        btn_toSecActivity = (Button)findViewById(R.id.btn_toSecActivity);
        btn_toSecActivity.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btn_toSecActivity:
                startActivity(new Intent(MainActivity.this,RxjavaOneActivity.class));
                break;
        }
    }
}
