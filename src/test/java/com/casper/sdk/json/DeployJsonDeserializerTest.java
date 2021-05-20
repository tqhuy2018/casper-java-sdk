package com.casper.sdk.json;

import com.casper.sdk.domain.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Tests that a {@link Deploy} can be parsed from JSON
 */
public class DeployJsonDeserializerTest {

    private static final String JSON = """
            {
              "hash": "d7a68bbe656a883d04bba9f26aa340dbe3f8ec99b2adb63b628f2bc920431998",
              "header": {
                "account": "017f747b67bd3fe63c2a736739dfe40156d622347346e70f68f51c178a75ce5537",
                "timestamp": "2021-05-04T14:20:35.104Z",
                "ttl": "30m",
                "gas_price": 2,
                "body_hash": "f2e0782bba4a0a9663cafc7d707fd4a74421bc5bfef4e368b7e8f38dfab87db8",
                "dependencies": [
                  "0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f",
                  "1010101010101010101010101010101010101010101010101010101010101010"
                ],
                "chain_name": "mainnet"
              },
              "payment": {
                "ModuleBytes": {
                  "module_bytes": "",
                  "args": [
                    [
                      "amount",
                      {
                        "cl_type": "U512",
                        "bytes": "0400ca9a3b",
                        "parsed": "1000000000"
                      }
                    ]
                  ]
                }
              },
              "session": {
                "Transfer": {
                  "args": [
                    [
                      "amount",
                      {
                        "cl_type": "U512",
                        "bytes": "05005550b405",
                        "parsed": "24500000000"
                      }
                    ],
                    [
                      "target",
                      {
                        "cl_type": {
                          "ByteArray": 32
                        },
                        "bytes": "0101010101010101010101010101010101010101010101010101010101010101",
                        "parsed": "0101010101010101010101010101010101010101010101010101010101010101"
                      }
                    ],
                    [
                      "id",
                      {
                        "cl_type": "U64",
                        "bytes": "01e703000000000000",
                        "parsed": 999
                      }
                    ],
                    [
                      "additional_info",
                      {
                        "cl_type": "String",
                        "bytes": "1000000074686973206973207472616e73666572",
                        "parsed": "this is transfer"
                      }
                    ]
                  ]
                }
              },
              "approvals": [
                {
                  "signer": "017f747b67bd3fe63c2a736739dfe40156d622347346e70f68f51c178a75ce5537",
                  "signature": "0195a68b1a05731b7014e580b4c67a506e0339a7fffeaded9f24eb2e7f78b96bdd900b9be8ca33e4552a9a619dc4fc5e4e3a9f74a4b0537c14a5a8007d62a5dc06"
                }
              ]
            }""";

    private Deploy deploy;

    @BeforeEach
    void setUp() throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        deploy = mapper.reader().readValue(JSON, Deploy.class);
    }

    @Test
    public void testDeployHashFromJson() {
        assertThat(deploy.getHash(), is(notNullValue(Digest.class)));
        assertThat(deploy.getHash(), is(new Digest("d7a68bbe656a883d04bba9f26aa340dbe3f8ec99b2adb63b628f2bc920431998")));
    }

    @Test
    public void testDeployDeployHeaderFromJson() {
        assertThat(deploy.getHeader(), is(notNullValue(DeployHeader.class)));

        assertThat(deploy.getHeader().getAccount().toHex(), is("017f747b67bd3fe63c2a736739dfe40156d622347346e70f68f51c178a75ce5537"));
        assertThat(deploy.getHeader().getTimestamp(), is("2021-05-04T14:20:35.104Z"));
        assertThat(deploy.getHeader().getBodyHash().getHash(), is("f2e0782bba4a0a9663cafc7d707fd4a74421bc5bfef4e368b7e8f38dfab87db8"));
        assertThat(deploy.getHeader().getGasPrice(), is(2));
        assertThat(deploy.getHeader().getDependencies().size(), is(2));
        assertThat(deploy.getHeader().getDependencies().get(0).getHash(), is("0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f0f"));
        assertThat(deploy.getHeader().getDependencies().get(1).getHash(), is("1010101010101010101010101010101010101010101010101010101010101010"));
        assertThat(deploy.getHeader().getChainName(), is("mainnet"));
    }

    @Test
    void testDeployPaymentFromJson() {
        assertThat(deploy.getPayment(), is(instanceOf(Payment.class)));
        Payment payment = deploy.getPayment();
        assertThat(payment.getModuleBytes(), is(new byte[0]));
    }

    @Test
    void testDeploySessionFromJson() {
        assertThat(deploy.getSession(), is(instanceOf(DeployExecutable.class)));
    }

    @Test
    void testDeployApprovalsFromJson() {
        assertThat(deploy.getApprovals().size(), is(1));
    }
}
