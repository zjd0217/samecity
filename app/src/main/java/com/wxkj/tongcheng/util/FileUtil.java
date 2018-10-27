package com.wxkj.tongcheng.util;

import android.text.TextUtils;

import java.io.File;

public class FileUtil {

    public static boolean deleteFolderFile(String filePath) {
        if (!TextUtils.isEmpty(filePath)) {
            File file = new File(filePath);
            return deleteFolderFile(file);
        }
        return false;
    }


    public static boolean deleteFolderFile(File file) {
        if (file == null) {
            return false;
        }
        try {
            // 处理目录
            if (file.isDirectory()) {
                File files[] = file.listFiles();
                if (files != null) {
                    for (File file1 : files) {
                        if (file1 != null) {
                            deleteFolderFile(file1.getAbsolutePath());
                        }
                    }
                }
            }
            // 如果是文件，删除
            if (!file.isDirectory()) {
                file.delete();
            } else {
                // 目录
                File[] files = file.listFiles();
                if (files != null) {
                    if (files.length == 0) {
                        // 目录下没有文件或者目录，删除
                        file.delete();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


}
