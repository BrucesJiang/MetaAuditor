package com.bruce.metaauditor.utils;

import com.bruce.metaauditor.contract.HVDAuditorContract;
import com.bruce.metaauditor.contract.RegisterContract;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.http.HttpService;

/**
 * @Description
 * @Author Bruce Jiang
 * @Date 2018/8/14 16:55
 * @Version
 */
public class Utils {
    public static RegisterContract getRegisterContract(Credentials credentials, String contractAdress){
        Web3j web3j = Web3j.build(new HttpService());
        return new RegisterContract(contractAdress, web3j, credentials, Constants.GAS_PRICE, Constants.GAS_LIMIT);

    }
    public static HVDAuditorContract getHVDAuditorContract(Credentials credentials, String contractAdress){
        Web3j web3j = Web3j.build(new HttpService());
        return new HVDAuditorContract(contractAdress, web3j, credentials, Constants.GAS_PRICE, Constants.GAS_LIMIT);
    }
}
