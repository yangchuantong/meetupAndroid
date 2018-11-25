package org.elastos.meetuplib.storage.conf;

public class MethodConstant {
//    private static final String API_URL = "http://192.168.43.18:9999/";
    private static final String API_URL = "http://61.164.243.108:9999/";


    public static class Contract {
        private static final String MODULE = API_URL + "contract/";
        public static final String HOME_PAGE = MODULE + "/homePage";
        public static final String FIND_BY_CONTRACT_ADDRESS = MODULE + "findByContractAddress";
        public static final String CREATE_CONTRACT = MODULE + "createContract";
        public static final String FIND_OWNER_BY_CONTRACT_ADDRESS = MODULE + "findOwnerByContractAddress";
        public static final String FIND_OWNER_CONTRACT_LIST_BY_WALLET_ADDRESS = MODULE + "findOwnerContractListByWalletAddress";
    }

    public static class File {
        private static final String MODULE = API_URL + "file/";
        public static final String UPLOAD_URL = MODULE + "upload";
        public static final String FILE = MODULE + "";
    }

    public static class Apply {
        private static final String MODULE = API_URL + "apply/";
        public static final String APPLY_DETAIL = MODULE + "applyDetail";
        public static final String CREATE = MODULE + "create";
        public static final String OWNER_APPLY_DETAIL_LIST = MODULE + "ownerApplyDetailList";
        public static final String AUDIT = MODULE + "audit";
        public static final String BURN = MODULE + "burn";
    }

}
