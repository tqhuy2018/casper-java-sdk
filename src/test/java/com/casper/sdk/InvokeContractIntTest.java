package com.casper.sdk;

import com.casper.sdk.service.serialization.cltypes.CLValueBuilder;
import com.casper.sdk.types.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyPair;
import java.time.Instant;

import static com.casper.sdk.IntegrationTestUtils.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

/**
 * Integration tests for invoking a contract
 */
@Disabled
public class InvokeContractIntTest {

    private static final long AMOUNT_TO_TRANSFER = 2000000000L;

    /**
     * Test that gives an example of using a
     */
    @Test
    void invokeContract() throws Throwable {

        // Step 1: Set casper node client.
        final CasperSdk casperSdk = new CasperSdk("http://localhost", 11101);

        // Step 2: Set contract operator key pair.
        final KeyPair userTwoKeyPair = geUserKeyPair(casperSdk, 2);
        final KeyPair nodeOneKeyPair = getNodeKeyPair(casperSdk, 1);

        // Step 3: Query node for global state root hash.
        final String stateRootHash = casperSdk.getStateRootHash();

        // Step 4: Query node for contract hash.
        final ContractHash contractHash = casperSdk.getContractHash(nodeOneKeyPair.getPublic());

        // Make a payment
        final ModuleBytes payment = casperSdk.standardPayment(new BigInteger("10000000000"));

        // Create the transfer
        final Deploy deploy = casperSdk.makeDeploy(
                new DeployParams(
                        nodeOneKeyPair.getPublic(),
                        "casper-net-1",
                        10,
                        Instant.now().toEpochMilli(),
                        DeployParams.DEFAULT_TTL,
                        null),
                new StoredContractByHash(
                        contractHash,
                        "transfer",
                        new DeployNamedArgBuilder()
                                .add("amount", CLValueBuilder.u256(AMOUNT_TO_TRANSFER))
                                .add("recipient", CLValueBuilder.byteArray(userTwoKeyPair.getPublic()))
                                .build()),
                payment
        );

        // Step 5.2: Sign deploy.
        casperSdk.signDeploy(deploy, nodeOneKeyPair);
        final Deploy signedDeploy = casperSdk.signDeploy(deploy, userTwoKeyPair);

        // Assert Approvals
        assertThat(signedDeploy.getApprovals().size(), is(2));
        final DeployApproval approval = signedDeploy.getApprovals().iterator().next();
        assertThat(approval.getSigner(),is(casperSdk.toCLPublicKey(userTwoKeyPair.getPublic())));

        final Digest digest = casperSdk.putDeploy(deploy);

        assertThat(digest, is(notNullValue()));
    }


    private KeyPair geUserKeyPair(final CasperSdk casperSdk, final int userNumber) throws IOException {
        final IntegrationTestUtils.KeyPairStreams streams = geUserKeyPairStreams(userNumber);
        return casperSdk.loadKeyPair(streams.getPublicKeyIn(), streams.getPrivateKeyIn());
    }

    private KeyPair getNodeKeyPair(final CasperSdk casperSdk, final int nodeNumber) throws IOException {
        final IntegrationTestUtils.KeyPairStreams streams = getNodeKeyPairSteams(nodeNumber);
        return casperSdk.loadKeyPair(streams.getPublicKeyIn(), streams.getPrivateKeyIn());
    }
}
