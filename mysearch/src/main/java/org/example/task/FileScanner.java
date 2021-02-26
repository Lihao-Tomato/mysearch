package org.example.task;

import org.example.model.FileMeta;
import org.example.service.FileService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: LiHao
 * Date: 2021-01-29
 * Time: 14:33
 */
class ScanJob implements Runnable {
    private final File root;
    private final ExecutorService threadPool;
    private final FileService fileService;
    private final CountDownLatch countDownLatch;
    private final AtomicInteger jobCount;

    public ScanJob(File root, ExecutorService threadPool, FileService fileService, AtomicInteger jobCount, CountDownLatch countDownLatch) {
        this.root = root;
        this.threadPool = threadPool;
        this.fileService = fileService;
        this.jobCount = jobCount;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        File[] children = root.listFiles();
        if (children != null) {
            List<FileMeta> scanResultList = new ArrayList<>();
            for (File child : children) {
                if (child.isDirectory()) {
                    jobCount.incrementAndGet();
                    ScanJob job = new ScanJob(child, threadPool, fileService, jobCount, countDownLatch);
                    threadPool.execute(job);
                } else if (child.isFile()) {
                    scanResultList.add(new FileMeta(child));
                }
            }
            fileService.service(root.getAbsolutePath(), scanResultList);
            if (jobCount.decrementAndGet() == 0) {
                countDownLatch.countDown();
            }
        }
    }
}

public class FileScanner {
    private final ExecutorService threadPool = new ThreadPoolExecutor(
            4, 4, 0, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(10)
    );
    private final CountDownLatch countDownLatch = new CountDownLatch(1);
    private final AtomicInteger jobCount = new AtomicInteger();

    private final FileService fileService = new FileService();

    public void scan(File root) throws InterruptedException {
        ScanJob job = new ScanJob(root, threadPool, fileService, jobCount, countDownLatch);
        jobCount.incrementAndGet();
        threadPool.execute(job);
        // 如果不加限制，提交任务之后。会运行到这里
        // 但扫描工作还没有完成
        // 如何在这里等待，直到扫描工作完成
        countDownLatch.await();
    }

//    public void scan(File root) {
//        scanDir(root);
//    }
//
//    private void scanDir(File root) {
//        if (!root.isDirectory()) {
//            return;
//        }
//
//        File[] children = root.listFiles();
//        if (children == null) {
//            return;
//        }
//
//        List<FileMeta> scanResultList = new ArrayList<>();
//        for (File child : children) {
//            scanDir(child);
//            if (child.isFile()) {
//                scanResultList.add(new FileMeta(child));
//            }
//        }
//
//        fileService.service(root.getAbsolutePath(), scanResultList);
//    }
}
