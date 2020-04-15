package cn.test;

import cn.aiyou.common.utils.JwtUtils;
import cn.aiyou.common.pojo.UserInfo;
import cn.aiyou.common.utils.RsaUtils;
import org.junit.Before;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

public class JwtTest {

    private static final String pubKeyPath = "F:\\idea\\aiyou\\rsa\\rsa.pub";

    private static final String priKeyPath = "F:\\idea\\aiyou\\rsa\\rsa.pri";

    private PublicKey publicKey;

    private PrivateKey privateKey;

    @Test
    public void testRsa() throws Exception {
        RsaUtils.generateKey(pubKeyPath, priKeyPath, "234");
    }

    @Before
    public void testGetRsa() throws Exception {
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }

    @Test
    public void testGenerateToken() throws Exception {
        // 生成token
        String token = JwtUtils.generateToken(new UserInfo(20L, "jack"), privateKey, 5);
        System.out.println("token = " + token);
    }

    @Test
    public void testParseToken() throws Exception {
       // String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MjAsInVzZXJuYW1lIjoiamFjayIsImV4cCI6MTUzMzI4MjQ3N30.EPo35Vyg1IwZAtXvAx2TCWuOPnRwPclRNAM4ody5CHk8RF55wdfKKJxjeGh4H3zgruRed9mEOQzWy79iF1nGAnvbkraGlD6iM-9zDW8M1G9if4MX579Mv1x57lFewzEo-zKnPdFJgGlAPtNWDPv4iKvbKOk1-U7NUtRmMsF1Wcg";
        String token ="eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJ4dWVxcyIsImV4cCI6MTU4NTAyMTczMH0.hfmk0fOh5-tpeN3O6-DiCqQ88DmdG_OS5OQqWf3FEX6Fu4OJgAM6Ti2mzitVCVcJqv125bz_m5b2vD598kaCFs8LtS0GvqCSLhwy5t04dh1m3YY8zjD4qXWb45G2l2UW5jL1CBBABzMVuesgiN2pQ--4tbLBPogyQqWHiSX8px0";
              //  "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJ4dWVxcyIsImV4cCI6MTU4NTAyMTQ4Mn0.i8qTkQr3hqE4JyjrBUximjdNUlep1YcIe4f_jtN0uPxGkkaqz99KgGMGJJ3AucD88m4e1x_znKwAxqeoaToOGfqUNEiDvvIgU8k6RnKNunCToKt--xmHDJTO72wepIV-_N9q7QGFwKAVz5bdSLV-9obuCmC3SdWhkEEQrioFkZw";

        // 解析token
        UserInfo user = JwtUtils.getInfoFromToken(token, publicKey);
        System.out.println("id: " + user.getId());
        System.out.println("userName: " + user.getUsername());

    }
}