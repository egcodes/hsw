package com.hackerupdates.hsw.service.lock;

import com.hackerupdates.hsw.service.lock.impl.DistributedLockProviderImpl.DistLock;

public interface DistributedLockProvider {

    DistLock lock(String key, int lockAtLeastFor, int lockAtMostFor);

}
