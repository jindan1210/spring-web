package com.king.controller;

import com.king.service.XaEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by 金丹 on 2017/12/19.
 */
@RestController
@RequestMapping("xaentity")
public class XaEntityController {
    @Autowired
    private XaEntityService xaEntityService;

    @RequestMapping("download")
    public void download(HttpServletResponse response) {
        response.setContentType("text/xml;charset=GBK");
        response.setCharacterEncoding("GBK");
        xaEntityService.download(response);
    }

}
