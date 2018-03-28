package com.king.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by 金丹 on 2017/12/19.
 */
public class ReadFile {
    public static List<String> Readfile() throws IOException {
        File f = new File("F:\\1210.txt");
        List<String> list = FileUtils.readLines(f, "utf-8");
        return list;
    }
}
