package com.bruce.metaauditor.deploy;

import com.bruce.metaauditor.contract.HVDAuditor;
import com.bruce.metaauditor.contract.Register;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;

import java.math.BigInteger;


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
            RemoteCall<Register> registerRemoteCall = Register.deploy(web3j, credentials, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
            RemoteCall<HVDAuditor> hvdAuditorRemoteCall = HVDAuditor.deploy(web3j, credentials, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
            Register register = registerRemoteCall.send();
            HVDAuditor hvdAuditor = hvdAuditorRemoteCall.send();
            register.register(register.getContractAddress(), username, pwd).send();
            boolean flag = register.login(username, pwd).send();
            if(flag) {
                System.out.println("Hahah");
            }

            //平均每1秒发送10，20，30，40，50，60，70次交易
            
            hvdAuditor.addHVD(username, "hahah", "aaa").send();
            hvdAuditor.addHVD(username, "oo", "aaa").send();
            hvdAuditor.addHVD(username, "bbbb", "aaa").send();
            BigInteger num = hvdAuditor.returnTotal().send();
            System.out.println("NUM = " + num.toString());
            String str = hvdAuditor.getHvd(num.subtract(new BigInteger("1"))).send();
            System.out.println("HVD = " + str);
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

}
