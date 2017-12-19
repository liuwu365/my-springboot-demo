package com.liuwu.Helper;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;

/**
 * @Description: 文件监控
 * @Date: 2017-12-18 17:56
 */
public class ZJPFileMonitor {
    FileAlterationMonitor monitor = null;

    public ZJPFileMonitor(long interval) throws Exception {
        monitor = new FileAlterationMonitor(interval);
    }

    public void monitor(String path, FileAlterationListener listener) {
        FileAlterationObserver observer = new FileAlterationObserver(new File(path));
        monitor.addObserver(observer);
        observer.addListener(listener);
    }

    public void stop() throws Exception {
        monitor.stop();
    }

    public void start() throws Exception {
        monitor.start();
    }

    public static void main(String[] args) throws Exception {
        ZJPFileMonitor m = new ZJPFileMonitor(10 * 1000);
        m.monitor("E:\\filepath", new ZJPFileListener());
        m.start();
    }


}
