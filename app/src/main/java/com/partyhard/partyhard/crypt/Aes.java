package com.partyhard.partyhard.crypt;


public final class Aes {

    public Cipher cipher;

    public final static String encryptPassword(String password) {
        // decrypt(encrypt(str))==str for all unicode chars
        String enc = null;
        try {
            Cipher.init();
            /*byte[] str = password.getBytes("UTF8");
            enc = Cipher.encrypt(str).toString();*/
            enc = Cipher.encryptString(password, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return enc;
    }

    public final static String decryptKey(String key) {
        // decrypt(encrypt(str))==str for all unicode chars
        String dec = null;
        try {
            Cipher.init();
            dec = Cipher.decryptString(key, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dec;
    }

    /**
     * Cipher getter.
     * @return cipher instance
     */
    public final Cipher getCipher() {
        return cipher;
    }
}
