package com.bruce.metaauditor.deploy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

import java.io.File;
import java.io.IOException;

/**
 * @Description 创建账户凭证
 * @Author Bruce Jiang
 * @Date 2018/8/15 20:10
 * @Version
 */
public class Credential {
    private static final Logger logger = LoggerFactory.getLogger(Credential.class);


    static String pwd = "12345678";
    static String dir = "config";
    static String file = null;
    public static void main(String[] args) {
        final String ROOT_PATH = Credential.class.getResource("/").getPath();
        file = genrateCredential(pwd, ROOT_PATH + dir);
        try{
            Credentials credentials = WalletUtils.loadCredentials(pwd, ROOT_PATH + dir + "/" + file);
            logger.info(credentials.getAddress());
            logger.info(credentials.getEcKeyPair().getPrivateKey().toString());
            logger.info(credentials.getEcKeyPair().getPublicKey().toString());
        }catch (IOException | CipherException e) {
            e.printStackTrace();
        }

    }

    public static String genrateCredential(String pwd, String dir) {
        File file = new File(dir);
        if(!file.exists()) {
            file.mkdirs();
        }
        String path = null;
        try{
           path  = WalletUtils.generateNewWalletFile(pwd, file, true);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }
}
