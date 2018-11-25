package org.elastos.meetuplib.base.entity;

import org.elastos.meetuplib.tool.util.DatatypeConverter;
import org.elastos.meetuplib.tool.util.ela.ECKey;
import org.elastos.meetuplib.tool.util.ela.Ela;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.utils.Numeric;

import java.io.Serializable;

/**
 * @Author: yangchuantong
 * @Description:did用户对象
 * @Date:Created in  2018/11/24 15:13
 */
public class DIDAccount implements Serializable {


    private String address;  //钱包地址
    private String privateKey;//私钥
    private String publicKey;//公钥
    private String did;//did
    private String ethAddress;

    public DIDAccount() {
        ECKey ec = new ECKey();
        this.privateKey = DatatypeConverter.printHexBinary(ec.getPrivateKeyBytes());
        this.publicKey = DatatypeConverter.printHexBinary(ec.getPubBytes());
        this.address = ec.toAddress();
        this.did = ec.toIdentityID();


        Credentials credentials = Credentials.create(privateKey);
        ECKeyPair ecKeyPair = credentials.getEcKeyPair();
        this.ethAddress = Numeric.prependHexPrefix(Keys.getAddress(ecKeyPair));
    }


    public DIDAccount(String privateKey) {
        this.privateKey = privateKey;
        this.publicKey = Ela.getPublicFromPrivate(privateKey);
        this.address = Ela.getAddressFromPrivate(privateKey);
        this.did = Ela.getIdentityIDFromPrivate(privateKey);
        Credentials credentials = Credentials.create(privateKey);
        ECKeyPair ecKeyPair = credentials.getEcKeyPair();
        this.ethAddress = Numeric.prependHexPrefix(Keys.getAddress(ecKeyPair));
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getEthAddress() {
        return ethAddress;
    }

    public void setEthAddress(String ethAddress) {
        this.ethAddress = ethAddress;
    }


    @Override
    public String toString() {
        return "DIDAccount{" +
                "address='" + address + '\'' +
                ", privateKey='" + privateKey + '\'' +
                ", publicKey='" + publicKey + '\'' +
                ", did='" + did + '\'' +
                ", ethAddress='" + ethAddress + '\'' +
                '}';
    }
}
