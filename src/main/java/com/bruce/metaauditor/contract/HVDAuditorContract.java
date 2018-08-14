package com.bruce.metaauditor.contract;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Uint;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * @Description
 * @Author Bruce Jiang
 * @Date 2018/8/14 16:55
 * @Version
 */
public class HVDAuditorContract extends Contract {
    /**
     *
     * @param contractAddress 合约地址
     * @param web3j　rpc请求
     * @param credentials　钱包地址
     * @param gasPrice　gas价格
     * @param gasLimit　gas限度
     */
    public HVDAuditorContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super("", contractAddress, web3j,credentials, gasPrice, gasLimit);
    }

    /**
     * 添加信息
     * @param username 用户名
     * @param hvd 同态摘要
     * @param salt 盐
     * @return
     * @throws IOException
     */
    public RemoteCall<TransactionReceipt> addHVD(String username, String hvd, String salt) throws IOException {
        //Function function = new Function("addMsg", Arrays.<Type>asList(new Utf8String(username),new Utf8String(ID),new Utf8String(violateRecord)
        //        ,new Uint(lowPoint),new Utf8String(AdministratorName)),Arrays.<TypeReference<?>>asList());
        Function function = new Function("addHVD", Arrays.<Type>asList(new Utf8String(username), new Utf8String(hvd), new Utf8String(salt)),
                Arrays.<TypeReference<?>>asList());
        return executeRemoteCallTransaction(function);
    }


    /**
     * 获取用户的总的HVD数量
     * @return
     * @throws IOException
     */
    public RemoteCall<Uint>  returnTotal() throws IOException {
        Function function = new Function("returnTotal",Arrays.<Type>asList(),Arrays.<TypeReference<?>>asList(new TypeReference<Uint>() {
        }));
        return executeRemoteCallSingleValueReturn(function);
    }

    /**
     * 用户姓名
     */
    public RemoteCall<Utf8String>  getUsername(BigInteger id) throws IOException {
        Function function = new Function("getUsername",Arrays.<Type>asList(new Uint(id)),Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
        }));
        return executeRemoteCallSingleValueReturn(function);
    }


    /**
     * 根据ID获取HVD
     * @param id
     * @return
     * @throws IOException
     */
    public RemoteCall<Utf8String>  getHvd(BigInteger id) throws IOException {
        Function function = new Function("getHvd",Arrays.<Type>asList(new Uint(id)),Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
        }));
        return executeRemoteCallSingleValueReturn(function);
    }

    /**
     *
     * @param id
     * @return
     * @throws IOException
     */
    public RemoteCall<Uint>  getSalt(BigInteger id) throws IOException {
        Function function = new Function("getSalt",Arrays.<Type>asList(new Uint(id)),Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
        }));
        return executeRemoteCallSingleValueReturn(function);
    }
    /**
     * 根据ID获取HVD和sault信息
     */
    public RemoteCall<Utf8String>  getHvdAndSault(BigInteger id) throws IOException {
        Function function = new Function("getHvdAndSalt",Arrays.<Type>asList(new Uint(id)),Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
        }, new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }
}
