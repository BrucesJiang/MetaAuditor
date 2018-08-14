package com.bruce.metaauditor.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Properties;

/**
 * @Description
 * @Author Bruce Jiang
 * @Date 2018/8/14 19:37
 * @Version
 */
public class Constants {
    public static Properties configs;

    static {
        configs = new Properties();
        InputStream in = Constants.class.getResourceAsStream("/config.properties");
        InputStreamReader reader = new InputStreamReader(in, Charset.forName("UTF-8"));
        try {
            configs.load(reader);
            in.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    // GAS价格
    public static BigInteger GAS_PRICE = BigInteger.valueOf(20_000_000_000L);
    // GAS上限
    public static BigInteger GAS_LIMIT = BigInteger.valueOf(4_300_000L);

    // 钱包密码
    public static String PASSWORD = configs.getProperty("password");
    // 钱包路径
    public static String PATH = configs.getProperty("path");
    // 钱包目录
    public static String DIRECTORY = configs.getProperty("directory");
    // 合约地址
    public static String Register_Contract_Address = configs.getProperty("RegisterContractAddress");

    public static String HVDAuditor_Contract_Address = configs.getProperty("HVDAuditorContract");

}