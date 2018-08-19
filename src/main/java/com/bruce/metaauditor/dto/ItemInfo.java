package com.bruce.metaauditor.dto;

import java.io.Serializable;

/**
 * @Description
 * @Author Bruce Jiang
 * @Date 2018/8/19 20:47
 * @Version
 */
public class ItemInfo implements Serializable{
    private int index;
    private String username;
    private String hvd;
    private String salt;
    private String size;
    private String belongTo;
    private String remoteAddr;

    public ItemInfo() {

    }

    public ItemInfo(int index, String username, String hvd, String salt, String size) {
        this.index = index;
        this.username = username;
        this.hvd = hvd;
        this.salt = salt;
        this.size = size;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHvd() {
        return hvd;
    }

    public void setHvd(String hvd) {
        this.hvd = hvd;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getBelongTo() {
        return belongTo;
    }

    public void setBelongTo(String belongTo) {
        this.belongTo = belongTo;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ItemInfo{");
        sb.append("index=").append(index);
        sb.append(", username='").append(username).append('\'');
        sb.append(", hvd='").append(hvd).append('\'');
        sb.append(", salt='").append(salt).append('\'');
        sb.append(", size='").append(size).append('\'');
        sb.append(", belongTo='").append(belongTo).append('\'');
        sb.append(", remoteAddr='").append(remoteAddr).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
