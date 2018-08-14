package com.bruce.metaauditor;

import com.bruce.metaauditor.contract.HVDAuditorContract;
import com.bruce.metaauditor.contract.RegisterContract;
import com.bruce.metaauditor.utils.Constants;
import org.web3j.abi.datatypes.Bool;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;

/**
 * @Description
 * @Author Bruce Jiang
 * @Date 2018/8/14 19:40
 * @Version
 */
public class AppMainTest {
    public static void main(String[] args) throws Exception {
        Credentials credentials = WalletUtils.loadCredentials(Constants.PASSWORD, Constants.PATH);

        System.out.println(Constants.PASSWORD + Constants.PATH);

        Web3j web3j = Web3j.build(new HttpService());

        RegisterContract registerContract = new RegisterContract(Constants.Register_Contract_Address, web3j, credentials, Constants.GAS_PRICE, Constants.GAS_LIMIT);
        //registerContract.register(Constants.REGISTER_ADDR, "bruce", "123456");

        //RemoteCall<Bool> login = registerContract.login("bruce","123456");
        //System.out.println(login.send().getValue());

        HVDAuditorContract hvdAuditorContract = new HVDAuditorContract(Constants.HVDAuditor_Contract_Address, web3j, credentials, Constants.GAS_PRICE, Constants.GAS_LIMIT);

        BigInteger total = hvdAuditorContract.returnTotal().send().getValue();
        int n = new Integer(total.toString());
        for(int i=0;i<n;i++){
            System.out.println(hvdAuditorContract.getHvd(BigInteger.valueOf(i)).send().getValue());
//            System.out.println(hvdAuditorContract.getHvdAndSault(BigInteger.valueOf(i)).send().toString());
            System.out.println(hvdAuditorContract.getSalt(BigInteger.valueOf(i)).send().getValue());
        }
    }
}
