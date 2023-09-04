package com.cc.sp3;

import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.ClassLayout;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Time;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.*;

@SpringBootTest
class ApplicationTest {

    private static class Padding {
        public volatile Long p1, p2, p3, p4, p5, p6, p7;
    }

    private static class T extends Padding {
        volatile public Long x;
    }

    public static T[] ts = new T[2];

    static {
        ts[1] = new T();
        ts[0] = new T();
    }




    class T1 {
        public synchronized void m1() {
            System.out.println("parent m1");
        }


    }

    class TT1 extends T1 {
        @Override
        public synchronized void m1() {
            System.out.println("m1 start");
            super.m1();
            System.out.println("m1 end");

        }
    }

    @Test
    void test01() throws InterruptedException {



        Thread t1 = new Thread(() -> {
            for (long i = 0; i < 10000000; i++) {
                ts[0].x = i;
            }
        });
        Thread t2 = new Thread(() -> {
            for (long i = 0; i < 10000000; i++) {
                ts[1].x = i;
            }
        });

        long s = System.nanoTime();
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println((System.nanoTime() - s) / 1_000_000);


        ThreadLocal<Integer> l = new ThreadLocal<>();
        l.set(1);

        ReentrantLock reentrantLock = new ReentrantLock();
        reentrantLock.lock();

    }


    @Test
    void test02() {
        TT1 t1 = new TT1();
        t1.m1();
        t1.m1();
    }

    @Test
    void test03() {
        new Thread(() -> {
            long s1 = System.nanoTime();
            for (int i = 0; i < 100000; i++) {
//				System.out.println(i);
//				if (i % 10 == 0) {
                Thread.yield();
//				}
            }
            long s2 = System.nanoTime();
            System.out.println((s2 - s1) / 1000000);
        }).start();

    }

    static 	AtomicLong count1 = new AtomicLong(0L);
    static long count2 = 0;
    static LongAdder count3 = new LongAdder();

    @Test
    void test04() throws InterruptedException {

        final int I = 10;
        final int J = 10000;
        {

            Thread[] threads = new Thread[I];
            for (int i = 0; i < I; i++) {
                Thread thread = new Thread(() -> {
                    for (int j = 0; j < J; j++) {
                        count1.incrementAndGet();
                    }
                });

                threads[i] = thread;
            }
            long s = System.currentTimeMillis();
            for (Thread thread : threads) {
                thread.start();
            }
            for (Thread thread : threads) {
                thread.join();
            }
            System.out.println(System.currentTimeMillis() - s);
        }




        {
            Object lock = new Object();
            Thread[] threads = new Thread[I];
            for (int i = 0; i < I; i++) {
//				int finalI = i;
                Thread thread = new Thread(() -> {
//					if (finalI == 999)
//						System.out.println(ClassLayout.parseInstance(lock).toPrintable());

                    for (int j = 0; j < J; j++) {
                        synchronized (lock) {
                            count2++;
                        }
                    }

                });

                threads[i] = thread;
            }

            long s = System.currentTimeMillis();
            for (Thread thread : threads) {
                thread.start();
            }
            for (Thread thread : threads) {
                thread.join();
            }
            System.out.println(System.currentTimeMillis() - s);
        }



        {
            Thread[] threads = new Thread[I];
            for (int i = 0; i < I; i++) {
                Thread thread = new Thread(() -> {
                    for (int j = 0; j < J; j++) {
                        count3.add(1L);
                    }
                });

                threads[i] = thread;
            }

            long s = System.currentTimeMillis();
            for (Thread thread : threads) {
                thread.start();
            }
            for (Thread thread : threads) {
                thread.join();
            }
            System.out.println(System.currentTimeMillis() - s);
        }
    }

    @Test
    void test05() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock(true);
//		Condition condition = lock.newCondition();

//		lock.lockInterruptibly();

        Thread t = new Thread(() -> {
            int i = 10;
            for (int i1 = 0; i1 < i; i1++) {
                try {
//					Thread.yield();
                    lock.lock();

                    System.out.println(i1);
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
            }
        });
        t.start();

        Thread.sleep(1000);
        System.out.println("--------");
        Thread t2 = new Thread(() -> {
            try {
                System.out.println("+++++");

                lock.lock();
                System.out.println("ooo");

            } finally {
                lock.unlock();
            }
        });

        t2.start();
        t2.join();
        t.join();
    }


    @Test
    void test06() throws InterruptedException {
        CountDownLatch lock = new CountDownLatch(100);
        Thread[] threads = new Thread[100];

        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(() -> {
                int result = 0;
                for (int j = 0; j < 100000; j++) {
                    result += 1;
                }
                System.out.println(result);
                lock.countDown();
            });

            threads[i] = thread;
        }

        for (Thread thread : threads) {
            thread.start();
        }

//		for (Thread thread : threads) {
//			thread.join();
//		}

        lock.await();
        System.out.println("ok");


    }


    @Test
    void test07() throws InterruptedException {


        Thread thread = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                if (i == 5) {
                    LockSupport.park();
                }

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                System.out.println(i);
            }
        });

        thread.start();


//		Thread.sleep(8000);
        System.out.println("unpark...");
        LockSupport.unpark(thread);
        thread.join();
    }


    @Test
    void test08() throws InterruptedException {
        List<Integer> lists = Collections.synchronizedList(new ArrayList<Integer>(10));

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                lists.add(1);
                System.out.println(i);
            }
        });

        t1.start();
        new Thread(() -> {
            for(;;) {
                if (lists.size() == 5) {
                    System.out.println("ok size is 5");
                    break;
                }
            }
        }).start();
        t1.join();
    }


    @Test
    void test09() throws InterruptedException {
        final Object lock = new Object();
        List<Integer> lists = new ArrayList<>(10);

        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                lock.notify();

                System.out.println("ok");
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                for (int i = 0; i < 10; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    lists.add(i);
                    System.out.println("i = " + i);

                    if (lists.size() == 5) {
                        lock.notify();

                        // 手动释放锁
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });

        t1.start();
        Thread.sleep(1000);
        t2.start();

        t1.join();
        t2.join();
    }


    @Test
    void test10() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (i == 5) {
                    latch.countDown();
                }
                System.out.println(i);

            }
        });

        Thread t2 = new Thread(() -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("ok");
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

    }

    LinkedList<Object> list_test11 = new LinkedList<>();
    final int MAX_test11 = 10;

    Integer count_test11 = 0;


    synchronized void consume_test11() throws InterruptedException {
        while (list_test11.size() == 0) {
            this.wait();
        }
        Object o = list_test11.removeFirst();
        System.out.println("o = " + o + "; count = " + --count_test11);
        this.notifyAll();
    }

    synchronized void produce_test11() throws InterruptedException {
        for (;;) {
            while (list_test11.size() >= MAX_test11) {
                this.wait();
            }

            list_test11.add(count_test11++);
            System.out.println("produce_test11 " + count_test11);
            this.notifyAll();
            System.out.println("produce_test11 notifyAll");
        }
    }

    @Test
    void test11() throws InterruptedException {

        Thread[] t = new Thread[2];

        for (int i = 0; i < 2; i++) {
            Thread producer = new Thread(() -> {
                try {
                    this.produce_test11();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            t[i] = producer;
        }

        for (Thread thread : t) {
            thread.start();
        }

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (;;) {
                    try {
                        Thread.sleep(100);
                        this.consume_test11();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }
            }).start();
        }

        Thread.sleep(1000 * 60);
    }


    @Test
    void test12() throws InterruptedException {
        final ReentrantReadWriteLock  lock = new ReentrantReadWriteLock();


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.writeLock().lock();
                System.out.println("Thread real execute");
                lock.writeLock().unlock();
            }
        });

        lock.writeLock().lock();
        lock.writeLock().lock();
        t.start();
        Thread.sleep(200);

        System.out.println("release one once");
        lock.writeLock().unlock();
//		lock.writeLock().unlock();
    }




    LinkedList<Object> list_test13 = new LinkedList<>();
    final int MAX_test13 = 10;

    Integer count_test13 = 0;
    ReentrantLock lock_test13 = new ReentrantLock();
    Condition producer_test13 = lock_test13.newCondition();
    Condition consumer_test13 = lock_test13.newCondition();



    void consume_test13() throws InterruptedException {
        try {
            lock_test13.lock();
            while (list_test13.size() == 0) {
                consumer_test13.await();
            }
            Object o = list_test13.removeFirst();
            System.out.println("o = " + o + "; count = " + --count_test13);

            producer_test13.signalAll();
        } finally {
            lock_test13.unlock();
        }
    }

    void produce_test13() throws InterruptedException {
        for (;;) {
            try {
                lock_test13.lock();
                while (list_test13.size() >= MAX_test13) {
                    producer_test13.await();
                }

                list_test13.add(count_test13++);
                System.out.println("produce_test13 " + list_test13);
                consumer_test13.signalAll();

            } finally {
                lock_test13.unlock();
            }
        }
    }


    @Test
    void test13() throws InterruptedException {


        Thread[] t = new Thread[2];

        for (int i = 0; i < 2; i++) {
            Thread thread = new Thread(() -> {
                try {
                    this.produce_test13();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            t[i] = thread;
        }

        for (Thread thread : t) {
            thread.start();
        }

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (;;) {
                    try {
                        Thread.sleep(100);
                        this.consume_test13();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }
            }).start();
        }

        Thread.sleep(1000 * 60);

    }



    // A -> B -> C
    Lock lock_test20 = new ReentrantLock();
    Condition condition1_test20 = lock_test20.newCondition();
    Condition condition2_test20 = lock_test20.newCondition();
    Condition condition3_test20 = lock_test20.newCondition();
    int flag_test20 = 1;
    @Test
    void test20() throws InterruptedException {

        Thread thread1 = new Thread(() -> {
            for (;;) {
                lock_test20.lock();

                while (flag_test20 != 1) {
                    try {
                        condition1_test20.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                try {
                    Thread.sleep(1000);
                    System.out.println("A");
                    flag_test20 = 2;
                    condition2_test20.signal();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock_test20.unlock();
                }

            }
        });

        Thread thread2 = new Thread(() -> {
            for (;;) {
                lock_test20.lock();

                while (flag_test20 != 2) {
                    try {
                        condition2_test20.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                try {
                    Thread.sleep(1000);
                    System.out.println("B");
                    flag_test20 = 3;
                    condition3_test20.signal();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock_test20.unlock();
                }

            }
        });

        Thread thread3 = new Thread(() -> {
            for (;;) {
                lock_test20.lock();

                while (flag_test20 != 3) {
                    try {
                        condition3_test20.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                try {
                    Thread.sleep(1000);
                    System.out.println("C");
                    flag_test20 = 1;
                    condition1_test20.signal();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock_test20.unlock();
                }

            }
        });


        thread1.start();
        thread2.start();
        thread3.start();
        thread1.join();
        thread2.join();
        thread3.join();


    }





    @Test
    void test21() throws InterruptedException {
        Lock lock = new ReentrantLock();
        lock.lock();


        Thread.sleep(1000);

        lock.unlock();

    }




    @Test
    void contextLoads() throws InterruptedException {
        ConcurrentHashMap<Object, Object> map = new ConcurrentHashMap<>();

        new HashMap<>().put("key1", "value");

        map.put("key1", "value");
        //-XX:BiasedLockingStartupDelay=0 偏向锁将延迟改为0，
		Thread.sleep(10000);

        Object o = new Object();

        System.out.println(ClassLayout.parseInstance(o).toPrintable());
//
//		o.hashCode();
//
//		System.out.println(ClassLayout.parseInstance(o).toPrintable());


        synchronized (o) {
            System.out.println(ClassLayout.parseInstance(o).toPrintable());


            // sync中调用hashcode 升级为重量级锁
//            o.hashCode();
//
//            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }


//        o.hashCode();

        System.out.println("done");
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
    }





}