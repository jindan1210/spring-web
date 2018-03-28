package com.king.service.impl;

import com.king.dao.XkEntityDao;
import com.king.entity.XkEntity;
import com.king.service.XaEntityService;
import com.king.util.ReadFile;
import com.king.util.ZipUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 金丹
 * @since 2018/3/28.
 */
@Service
public class XaEntityServiceImpl implements XaEntityService {
    @Autowired
    private XkEntityDao xkEntityDao;
    private static Map<String, String> cookieMap = null;

    @Override
    public void download(HttpServletResponse response) {
        List<XkEntity> list = new ArrayList<>();

        String filePath = System.getProperty("catalina.home") + "/logs/";
        String fileName = filePath + "xk1210";
        File file = new File(fileName + ".csv");
        String zip_name = filePath + "zipfile";
        File zipFile = new File(zip_name + ".zip");
        BufferedWriter bw = null;
        OutputStream out = null;
        try {
            List<String> cards = ReadFile.Readfile();
            cards.forEach(card -> {
                list.addAll(xkEntityDao.queryXkEntityByNo(card));
            });
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "GBK"));
            bw.write("商户名称,卡号, 提卡时间,运营商");
            bw.newLine();
            for (XkEntity item : list) {
                String sb = item.toString();
                bw.write(sb);
                bw.newLine();
            }
            bw.flush();
            out = response.getOutputStream();
            String[] fileList = {file.getPath()};
            ZipUtil.zip(fileList, zip_name + ".zip", false, "");
            long fileLength = zipFile.length();
            response.setContentType("application/zip");
            response.setHeader("Content-Length", String.valueOf(fileLength));
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".zip");
            byte[] temp = FileUtils.readFileToByteArray(zipFile);
            IOUtils.write(temp, out);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(bw);
            IOUtils.closeQuietly(out);
            file.delete();
            zipFile.delete();
        }
    }
}
