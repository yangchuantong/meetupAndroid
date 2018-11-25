package org.elastos.meetup.tools;


import org.elastos.meetuplib.base.DIDChain;
import org.elastos.meetuplib.service.tokenizedAsset.service.AssetService;
import org.elastos.meetuplib.service.tokenizedAsset.service.impl.AssetServiceImpl;

/**
 * Created by xianxian on 2018/11/24.
 */

public class NodeClient {
//   private static String ip = "https://mainnet.infura.io/v2GFVi4kHWGp6Yfxfr47";

   // private static String ip = "https://api.myetherapi.com/eth";
//    private static String ip = "https://api.myetherapi.com/rop";
//    didchain.starrymedia.com
//    elachain.starrymedia.com
//    ethchain.starrymedia.com
//   private static String ip = "https://ropsten.infura.io/v2GFVi4kHWGp6Yfxfr47";
   private static String ip = "http://192.168.1.112:8545";
   private static String didip = "http://192.168.1.112:21604";
   private static String elaip = "http://192.168.1.112:21334";
    private NodeClient(){}

    private volatile static AssetService assetService;
    private volatile static DIDChain didChain;




    public static AssetService getAssetServiceClient(){
        if(assetService==null){
            assetService=new AssetServiceImpl();
        }
        return assetService;
    }

}
