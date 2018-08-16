pragma solidity ^0.4.18;

contract Register {

    struct User {
        address ownerAddr;
        string ownerName;
        bytes32 password;
    }

    address public owner;
    address[] addrList;
    mapping(string=>bool) registerPool;
    mapping(string=>User) userPool;

    constructor() public {
        owner = msg.sender;
    }

    //注册
    function register(address addr, string username, string password) public {
        require(!(msg.sender != owner));

        require(!checkRegister(addr, username));

        userPool[username] = User(addr, username, keccak256(abi.encodePacked(password)));
        addrList.push(addr);
        registerPool[username] = true;
    }

    //注册检测
    function checkRegister(address addr, string username) constant public returns (bool) {
        for(uint i = 0; i < addrList.length; ++i) {
            if(addrList[i] == addr || registerPool[username] == true) {
                return true;
            }
        }
        return false;
    }

    //登录
    function login(string username, string password) constant public returns (bool) {
        return userPool[username].password == keccak256(abi.encodePacked(password));
    }

    //更新密码
    function updatePassword(string username, bytes newPassword) public {
        require(msg.sender != owner);

        //keccak256加密
        userPool[username].password = keccak256(abi.encodePacked(newPassword));
    }

    function addHVD(string username, string hvd, string salt, string size) public{
        require(msg.sender != owner);

        HVDAuditor a = new HVDAuditor();
        a.addHVD(username, hvd, salt, size);

    }
}

contract HVDAuditor {
    struct HVD {
        string username;
        string hvd;
        string salt;
        string size;
    }

    HVD[] hvds;
    constructor() public{}
    function addHVD(string username, string hvd, string salt, string size) public {
        hvds.push(HVD(username, hvd, salt, size));
    }

    function returnTotal() constant public returns (uint) {
        return hvds.length;
    }


    function getUsername(uint id) constant public returns (string) {
        return hvds[id].username;
    }

    function getHvd(uint id) constant public returns (string) {
        return hvds[id].hvd;
    }

    function getSalt(uint id) constant public returns (string) {
        return hvds[id].salt;
    }

    function getSize(uint id) constant public returns (string) {
        return hvds[id].size;
    }

    function getHvdAndSalt(uint id) constant public returns (string hvd, string salt, string size) {
        hvd = hvds[id].hvd;
        salt = hvds[id].salt;
        size = hvds[id].size;
    }
}

