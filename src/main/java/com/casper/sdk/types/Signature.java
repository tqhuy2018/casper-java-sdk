package com.casper.sdk.types;

import com.casper.sdk.service.json.serialize.PublicKeyJsonSerializer;
import com.casper.sdk.service.json.deserialize.SignatureJsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Signature type used in deployment approvals.
 */
@JsonDeserialize(using = SignatureJsonDeserializer.class)
@JsonSerialize(using = PublicKeyJsonSerializer.class)
public class Signature extends CLPublicKey {

    public Signature(final byte[] bytes, final SignatureAlgorithm keyAlgorithm) {
        super(bytes, keyAlgorithm);
    }

    public Signature(final String signature) {
        super(signature);
    }
}
