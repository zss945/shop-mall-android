package com.zss.mall;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.zss.kit.ToastUtils;
import com.zss.mall.room.dao.CartDao;
import com.zss.mall.room.database.AppDatabase;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * 全局应用
 * @author zm
 */
public class BaseApplication extends Application {

    private static BaseApplication instance;

    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        instance = this;

        Log.i("debug", "--------- Thread Name1 = " + Thread.currentThread().getName());
//        Observable.range(1, 10).subscribe(new Observer<Integer>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//            }
//
//            @Override
//            public void onNext(Integer value) {
//                Log.i("debug", "--------- onNext = " + value);
//            }
//            @Override
//            public void onError(Throwable e) {
//            }
//            @Override
//            public void onComplete() {
//                Log.i("debug", "--------- Thread Name2 = " + Thread.currentThread().getName());
//            }
//        });

//        Observable.interval(2, TimeUnit.SECONDS).subscribe(new Observer<Long>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(Long value) {
//                Log.i("debug", "--------- onNext = " + value);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//                Log.i("debug", "--------- Thread Name2 = " + Thread.currentThread().getName());
//            }
//        });

//        Observable.timer(2, TimeUnit.SECONDS).subscribe(new Observer<Long>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//            @Override
//            public void onNext(Long value) {
//                Log.i("debug", "--------- onNext = " + value);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//            @Override
//            public void onComplete() {
//                Log.i("debug", "--------- Thread Name2 = " + Thread.currentThread().getName());
//            }
//        });

//        Observable.empty()
//                .doOnComplete(new Action() {
//                    @Override
//                    public void run() throws Exception {
//                        Log.i("debug", "--------- Thread Name2 = " + Thread.currentThread().getName());
//                        throw new RuntimeException("error");
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnComplete(new Action() {
//                    @Override
//                    public void run() throws Exception {
//                        Log.i("debug", "--------- Thread Name3 = " + Thread.currentThread().getName());
//                    }
//                })
//                .subscribe();

//        Observable.empty()
//                 .doOnSubscribe(new Consumer<Disposable>() {
//                     @Override
//                     public void accept(Disposable disposable) throws Exception {
//                         Log.i("debug", "--------- Thread Name2 = " + Thread.currentThread().getName());
//                         disposable.dispose();
//                     }
//                 })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doFinally(new Action() {
//                    @Override
//                    public void run() throws Exception {
//                        Log.i("debug", "--------- Thread Name3 = " + Thread.currentThread().getName());
//                    }
//                })
//                .subscribe();

//        Observable.create(
//                new ObservableOnSubscribe<List<File>>() {
//                    @Override
//                    public void subscribe(ObservableEmitter<List<File>> emitter) throws Exception {
//                        Log.i("debug", "--------- Thread Name2 = " + Thread.currentThread().getName());
//                        List<File> fileList = new ArrayList<>();
//                        fileList.add(new File("file1.txt"));
//                        fileList.add(new File("file2.txt"));
//                        emitter.onNext(fileList);
//                    }
//                }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<List<File>>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(List<File> fileList) {
//                        Log.i("debug", "--------- Thread File Size = " + fileList.size());
//                        Log.i("debug", "--------- Thread Name3 = " + Thread.currentThread().getName());
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtils.init(this);
    }

    public CartDao getCartDao() {
        return AppDatabase.getInstance(this).getCartDao();
    }

    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                return new ClassicsHeader(context);
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }
}
