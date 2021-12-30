package com.zyg.auth.service;

import java.io.IOException;
import java.text.ParseException;

/**
 * Created by WF on 2021/12/30 15:58
 */
public interface Auth2Service {
    void getTokenAndUserInfo(String code) throws IOException, ParseException;
}
