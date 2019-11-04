package com.zss.mall.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.zss.mall.room.entity.Cart;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addOrReplaceList(List<Cart> cartList);

    @Delete
    void deleteList(List<Cart> cart);

    @Query("delete from tb_cart")
    void deleteAll();

    @Query("select * from tb_cart")
    Flowable<List<Cart>> queryAll();

    @Query("select count(*) as count from tb_cart")
    Single<Integer> getBadgeCount();

    @Query("select cart_key from tb_cart limit 1")
    Single<String> getCartKey();

}
