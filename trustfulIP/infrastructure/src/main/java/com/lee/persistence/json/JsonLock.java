package com.lee.persistence.json;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author KitenLee
 * * @date 2026/1/27
 */
public class JsonLock {
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public static void read(Runnable runnable) {
        lock.readLock().lock();
        try {
            runnable.run();
        } finally {
            lock.readLock().unlock();
        }
    }

    public static void write(Runnable runnable) {
        lock.writeLock().lock();
        try {
            runnable.run();
        } finally {
            lock.writeLock().unlock();
        }
    }
}
