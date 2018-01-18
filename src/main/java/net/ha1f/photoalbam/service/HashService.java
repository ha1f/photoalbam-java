package net.ha1f.photoalbam.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.springframework.stereotype.Service;

@Service
public class HashService {

    private static final String MESSAGE_DIGEST_ALGORITHM_SHA256 = "SHA-256";

    // TODO: 実行時引数とかで渡すようにする
    private static final String SALT_FOR_ALBUM_PASSWORD = "fdR0dP";

    /**
     * SHA-256でハッシュ化した文字列を返す
     *
     * @param text unencrypted password
     *
     * @return hashed string
     */
    private static String hash(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance(MESSAGE_DIGEST_ALGORITHM_SHA256);
            md.update(text.getBytes());
            return Base64.getEncoder().encodeToString(md.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    String calcHashForAlbumPassword(String password) {
        String currentHash = password + SALT_FOR_ALBUM_PASSWORD;
        for (int i = 0; i < 2; i++) {
            currentHash = hash(currentHash);
        }
        return currentHash;
    }

}
