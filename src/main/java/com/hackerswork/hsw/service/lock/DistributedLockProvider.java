package com.hackerswork.hsw.service.lock;

import com.hackerswork.hsw.service.lock.impl.DistributedLockProviderImpl.DistLock;

public interface DistributedLockProvider {

    DistLock lock(String key, int lockAtLeastFor, int lockAtMostFor);

}
