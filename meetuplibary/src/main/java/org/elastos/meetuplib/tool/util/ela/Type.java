package org.elastos.meetuplib.tool.util.ela;

public   enum Type {
        PrivateKeyLower("privateKey"),
        PrivateKeyUpper("PrivateKey"),
        AddressLower("address"),
        ChangeAddress("ChangeAddress"),
        AmountLower("amount"),
        MUpper("M"),
        Host("Host"),
        Fee("Fee"),
        Confirmation("Confirmation"),
        TxidLower("txid"),
        IndexLower("index"),
        PasswordLower("password"),
        RecordTypeLower("recordType"),
        RecordDataLower("recordData"),
        BlockHashUpper("BlockHash");

        private String type;

        private Type(String t) {
            this.type = t;
        }

        public String getValue() {
            return this.type;
        }
    }