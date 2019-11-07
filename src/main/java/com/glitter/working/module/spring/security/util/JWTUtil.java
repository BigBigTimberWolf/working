package com.glitter.working.module.spring.security.util;

import com.alibaba.fastjson.JSON;
import com.glitter.working.module.security.encryption.RSACoder;
import com.glitter.working.module.spring.security.modal.JsonWebToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @program:
 * @author: Player
 * @create: 2019-11-07
 **/
@Slf4j
public class JWTUtil {
    @Autowired
    private RSACoder rsaCoder;
    public  JsonWebToken getJsonWebToken(String token){
        try {
            String jwt = rsaCoder.encryptByPrivateKey(token);
            if(StringUtils.isNotEmpty(jwt)){
                JsonWebToken jsonWebToken = JSON.parseObject(jwt, JsonWebToken.class);
                if(jsonWebToken==null){
                    return null;
                }
                return jsonWebToken;
            }
        } catch (BadPaddingException e) {
            log.error("解析token异常:{}",e);
        } catch (IllegalBlockSizeException e) {
            log.error("解析token异常:{}",e);
        }
        return null;
    }

    public String getToken(JsonWebToken jsonWebToken){
        if(!checkJsonWebToken(jsonWebToken)){
            log.warn("生成TOKEN数据无法通过校验:{}",jsonWebToken.toString());
            return null;
        }
        try {
            return rsaCoder.encryptByPrivateKey(JSON.toJSONString(jsonWebToken));
        } catch (BadPaddingException e) {
            log.error("生成TOKEN异常:{}",e);
        } catch (IllegalBlockSizeException e) {
            log.error("生成TOKEN异常:{}",e);
        }
        return null;
    }

    private boolean checkJsonWebToken(JsonWebToken jsonWebToken){
        if (jsonWebToken==null){
            return false;
        }
        if(StringUtils.isEmpty(jsonWebToken.getName())){
            return false;
        }
        if (jsonWebToken.getExpirationTime()<0){
            return false;
        }
        return true;
    }

    public String getTokenName(String name){
        return  "token_"+name;
    }
}
