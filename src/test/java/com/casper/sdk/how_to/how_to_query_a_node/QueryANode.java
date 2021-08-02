package com.casper.sdk.how_to.how_to_query_a_node;

import com.casper.sdk.CasperSdk;
import com.casper.sdk.how_to.common.Methods;

import org.junit.jupiter.api.Test;

public class QueryANode extends Methods {

    final CasperSdk casperSdk = new CasperSdk("http://localhost", 40101);

    private final static String NCTL_HOME = "~/casper-node/utils/nctl";

    @Test
    public void testGetAuctionInfo() throws Throwable {

        final String auctionInfo = casperSdk.getAuctionInfo();

        assert (auctionInfo != null);

    }

    @Test
    public void testGetNodeStatus() throws Throwable {

        final String nodeStatus = casperSdk.getNodeStatus();

        assert (nodeStatus != null);

    }

    @Test
    public void testGetNodePeers() throws Throwable {

        final String nodePeers = casperSdk.getNodePeers();

        assert (nodePeers != null);

    }

    @Test
    public void testGetStateRootHash() throws Throwable {

        final String stateRootHash = casperSdk.getStateRootHash();

        assert (stateRootHash != null);

    }

    @Test
    public void testGetAccountMainPurseURef() throws Throwable {

        final String accountMainPurseURef = casperSdk.getAccountMainPurseURef(super.getPublicKeyAccountHex(
                super.getUserKeyPair(1, NCTL_HOME, casperSdk)));

        assert (accountMainPurseURef != null);

    }

    @Test
    public void testGetAccountHash() throws Throwable {

        final String accountHash = casperSdk.getAccountHash(super.getPublicKeyAccountHex(
                super.getUserKeyPair(1, NCTL_HOME, casperSdk)));

        assert (accountHash != null);

    }

    @Test
    public void testGetAccountBalance() throws Throwable {

        final String accountBalance = casperSdk.getAccountBalance(super.getPublicKeyAccountHex(
                super.getUserKeyPair(1, NCTL_HOME, casperSdk)));

        assert (accountBalance != null);

    }

    @Test
    public void testGetAccountInfo() throws Throwable {

        final String accountInfo = casperSdk.getAccountInfo(super.getPublicKeyAccountHex(
                super.getUserKeyPair(1, NCTL_HOME, casperSdk)));

        assert (accountInfo != null);

    }

}
