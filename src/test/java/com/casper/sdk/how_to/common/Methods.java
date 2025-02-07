package com.casper.sdk.how_to.common;

import com.casper.sdk.CasperSdk;
import com.casper.sdk.types.Algorithm;
import com.casper.sdk.types.CLPublicKey;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyPair;

/**
 * Shared methods for the How-Tos
 */
public abstract class Methods {

    protected String getPublicKeyAccountHex(final KeyPair keyPair) {
        final CLPublicKey publicKey = new CLPublicKey(keyPair.getPublic().getEncoded(), Algorithm.ED25519);
        return publicKey.toAccountHex();
    }

    protected KeyPair getUserKeyPair(int userNumber, final String defaultNctlHome, final CasperSdk casperSdk) throws IOException {

        final String userNPath = String.format("%s/assets/net-1/users/user-%d", getNctlHome(defaultNctlHome), userNumber);
        final FileInputStream publicKeyIn = new FileInputStream(new File(userNPath, "public_key.pem"));
        final FileInputStream privateKeyIn = new FileInputStream(new File(userNPath, "secret_key.pem"));

        return casperSdk.loadKeyPair(publicKeyIn, privateKeyIn);
    }

    protected String getNctlHome(final String defaultNctlHome) {
        String nctlHome = System.getProperty("nctl.home");
        if (nctlHome == null) {
            nctlHome = defaultNctlHome;
        }
        // Replace user home '~' tilda with full path to user home directory
        nctlHome = nctlHome.replaceFirst("^~", System.getProperty("user.home"));
        return nctlHome;
    }

    protected KeyPair getNodeKeyPair(final int nodeNumber, final String defaultNctlHome, final CasperSdk casperSdk) throws IOException {

        final String userNPath = String.format("%s/assets/net-1/nodes/node-%d/keys", getNctlHome(defaultNctlHome), nodeNumber);
        final FileInputStream publicKeyIn = new FileInputStream(new File(userNPath, "public_key.pem"));
        final FileInputStream privateKeyIn = new FileInputStream(new File(userNPath, "secret_key.pem"));

        return casperSdk.loadKeyPair(publicKeyIn, privateKeyIn);
    }

}
