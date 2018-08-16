package com.bruce.metaauditor.deploy;

import com.bruce.metaauditor.contract.HVDAuditor;
import com.bruce.metaauditor.contract.Register;
import com.bruce.metaauditor.utils.Constants;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @Description 部署合约
 * @Author Bruce Jiang
 * @Date 2018/8/15 21:46
 * @Version
 */
public class ContractDeploy {
    static String username = "root";
    static String pwd = "root";
    static String dir = "config";
    static String file = "UTC--2018-08-15T14-59-58.562865900Z--2c354b4fa96aea437d60e10f43361fb7df06c0fa";
    public static void main(String[] args){
        final String ROOT_PATH = Credential.class.getResource("/").getPath();
        Web3j web3j = Web3j.build(new HttpService());
        try{
            Credentials credentials = WalletUtils.loadCredentials(pwd, ROOT_PATH + dir + "/" + file);
            //RemoteCall<Register> registerRemoteCall = Register.deploy(web3j, credentials, ManagedTransaction.GAS_PRICE, Constants.GAS_LIMIT);//Contract.GAS_LIMIT);
            //RemoteCall<HVDAuditor> hvdAuditorRemoteCall = HVDAuditor.deploy(web3j, credentials, Constants.GAS_PRICE, Constants.GAS_LIMIT);//Contract.GAS_LIMIT);
            //Register register = registerRemoteCall.send();
            //String registerAddress = register.getContractAddress();
            //System.out.println("registerAddress = " + registerAddress);
            //HVDAuditor hvdAuditor = hvdAuditorRemoteCall.send();
            //String hvdAuditorAddress = hvdAuditor.getContractAddress();
            //System.out.println("hvdAuditorAddress = " + hvdAuditorAddress);

            //register.register(register.getContractAddress(), username, pwd).send();
            //boolean flag = register.login(username, pwd).send();
            //if(flag) {
            //    System.out.println("Hahah");
            //}

            //平均每1秒发送10，20，30，40，50，60，70次交易
            
//            hvdAuditor.addHVD(username, "hahah", "aaa", new BigInteger("10")).send();
//            hvdAuditor.addHVD(username, "oo", "aaa", new BigInteger("10")).send();
//            hvdAuditor.addHVD(username, "bbbb", "aaa", new BigInteger("20")).send();
//            BigInteger num = hvdAuditor.returnTotal().send();
//            System.out.println("NUM = " + num.toString());
//            String str = hvdAuditor.getHvd(num.subtract(new BigInteger("1"))).send();
//            System.out.println("HVD = " + str);

            //String dataPath = "F:\\Source\\maven\\test\\data";
            //scheduleThread(new File(dataPath), hvdAuditor, 20);
            HVDAuditor hvdAuditor = HVDAuditor.load("0x50ae7d17ee9b44ba909ad231d240ebd567c9e85c", web3j, credentials, Constants.GAS_PRICE, Contract.GAS_LIMIT);
            BigInteger num = hvdAuditor.returnTotal().send();
            System.out.println("NUM = " + num.toString());
            String str = hvdAuditor.getHvd(num.subtract(new BigInteger("2"))).send();
            System.out.println(str.getBytes());
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void scheduleThread(File file, HVDAuditor hvdAuditor, int nThreads) {
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
        synchronizedFile(file, executorService, hvdAuditor);
    }

    public static void synchronizedFile(final File file, ExecutorService executorService, HVDAuditor hvdAuditor) {
        if(file.isDirectory()) {
            final File[] files = file.listFiles();
            for(int i = 0; i < files.length; i ++ ) {
                synchronizedFile(files[i], executorService, hvdAuditor);
            }
        }else if(file.isFile()) {
            System.out.println("execute");
            executorService.execute(() -> {
                try{
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
                    Random random = new Random();
                    float y = random.nextFloat();
                    int x = random.nextInt(10) + 1;
                    byte[] buffer = new byte[(int)(x*y*1024*1024)];
                    int len = 0;
                    while((len = bufferedInputStream.read(buffer)) != 0) {
                        System.out.println(buffer);
                        int salt = 2 << (random.nextInt(10));
                        String hvd = getSHA256(new String(buffer) + salt);
                        System.out.println("hvd = " + hvd);
                        System.out.println("salt = " + salt);
                        try{
                            hvdAuditor.addHVD(username, hvd, salt+"", len + "").send();
                        }catch (Exception e) {
                            e.printStackTrace();
                        }


                        y = random.nextFloat();
                        x = random.nextInt(10) + 1;
                        buffer = new byte[(int)(x*y*1024)];
                    }
                    bufferedInputStream.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     *  利用java原生的摘要实现SHA256加密
     * @param str 加密后的报文
     * @return
     */
    public static String getSHA256(String str){
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }

    private static String byte2Hex(byte[] bytes){
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i=0;i<bytes.length;i++){
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length()==1){
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }
}
