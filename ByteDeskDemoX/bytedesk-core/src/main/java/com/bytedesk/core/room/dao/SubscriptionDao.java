package com.bytedesk.core.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.bytedesk.core.room.entity.SubscriptionEntity;

/**
 * @author bytedesk.com on 2019/3/28
 */
@Dao
public interface SubscriptionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSubscription(SubscriptionEntity subscriptionEntity);




}
