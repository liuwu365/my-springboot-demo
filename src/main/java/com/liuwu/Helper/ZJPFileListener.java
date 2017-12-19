package com.liuwu.Helper;

import com.google.gson.Gson;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @Description: 文件监听
 * @Date: 2017-12-18 17:52
 */
public class ZJPFileListener implements FileAlterationListener {
    private static final Gson gson = new Gson();
    private static final Logger logger = LoggerFactory.getLogger(ZJPFileListener.class);

    @Override
    public void onStart(FileAlterationObserver fileAlterationObserver) {
        //logger.info("onStart");
    }

    @Override
    public void onDirectoryCreate(File file) {
        logger.info("onDirectoryCreate fileName:{}", file.getName());
    }

    @Override
    public void onDirectoryChange(File file) {
        logger.info("onDirectoryChange fileName:{}", file.getName());
    }

    @Override
    public void onDirectoryDelete(File file) {
        logger.info("onDirectoryDelete fileName:{}", file.getName());
    }

    @Override
    public void onFileCreate(File file) {
        logger.info("onFileCreate fileName:{}", file.getName());
    }

    @Override
    public void onFileChange(File file) {
        logger.info("onFileChange fileName:{}", file.getName());
    }

    @Override
    public void onFileDelete(File file) {
        logger.info("onFileDelete fileName:{}", file.getName());
    }

    @Override
    public void onStop(FileAlterationObserver fileAlterationObserver) {
        //logger.info("onStop");
    }



}
