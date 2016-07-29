package com.mxn.soul.flowingdrawer.data;

/**
 * Created by ZEROYU on 5/24/2016.
 */
public class SecretKey {
    private String Id;
    private String username;
    private String filename;
    private String filekey1;
    private String filekey2;
    private String filekey3;
    private String filekey4;
    private String filekey5;

    public SecretKey(String username,String filename, String filekey1,String filekey2,String filekey3,String filekey4,String filekey5) {
        this.Id = null; //default
        this.username = username;
        this.filename = filename;
        this.filekey1 = filekey1;
        this.filekey2 = filekey2;
        this.filekey3 = filekey3;
        this.filekey4 = filekey4;
        this.filekey5 = filekey5;
    }


    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilekey1() {
        return filekey1;
    }

    public void setFilekey1(String filekey1) {
        this.filekey1 = filekey1;
    }

    public String getFilekey2() {
        return filekey2;
    }

    public void setFilekey2(String filekey2) {
        this.filekey2 = filekey2;
    }

    public String getFilekey3() {
        return filekey3;
    }

    public void setFilekey3(String filekey3) {
        this.filekey3 = filekey3;
    }

    public String getFilekey4() {
        return filekey4;
    }

    public void setFilekey4(String filekey4) {
        this.filekey4 = filekey4;
    }

    public String getFilekey5() {
        return filekey5;
    }

    public void setFilekey5(String filekey5) {
        this.filekey5 = filekey5;
    }

}
