package com.zds.biz.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuthKeyRes {
    private String authKey;
    private LocalDateTime expireTime;
}
