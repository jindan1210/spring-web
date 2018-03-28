package com.king.util.httpclient;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by jindan
 * 2017/8/2.
 */
public interface HttpResponseCallBack {
    void processResponse(InputStream responseBody) throws IOException;
}
