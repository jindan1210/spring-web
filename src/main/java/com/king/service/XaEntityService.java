package com.king.service;

import javax.servlet.http.HttpServletResponse;

/**
 * @author 金丹
 * @since 2018/3/28.
 */
public interface XaEntityService {
    /**
     * 下载
     *
     * @param response
     */
    void download(HttpServletResponse response);
}
