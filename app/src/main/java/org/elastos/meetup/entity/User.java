package org.elastos.meetup.entity;

import java.io.Serializable;

public class User implements Serializable {
    private static User user;
    private static Wallet wallet;
    public static User getInstance(){
        if(user ==null){
            user = new User();
        }
        return user;

    }
    public Wallet getWallet(){
        if(wallet==null){
            wallet=new Wallet();
        }
        return wallet;
    }
   String mnemonic;
    private static Did did;
    //    public static Did getDidInstance(){
//        if(did==null){
//            did = new Did();
//        }
//        return  did;
//    }

    private class Did  implements Serializable {
       private String privateKey;
       private String publicKey;
       private String address;

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

       public String getAddress() {
           return address;
       }

       public void setAddress(String address) {
           this.address = address;
       }
   }
  public class Wallet{
        private String privateKey;
        private String publicKey;
        private String address;
        private double balances;

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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public double getBalances() {
            return balances;
        }

        public void setBalances(double balances) {
            this.balances = balances;
        }
    }

    public String getMnemonic() {
        return mnemonic;
    }

    public void setMnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
    }
}