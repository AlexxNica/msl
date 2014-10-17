package com.netflix.msl.client.assertable;

import com.netflix.msl.MslConstants;
import com.netflix.msl.msg.ErrorHeader;
import com.netflix.msl.msg.MessageInputStream;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

/**
 * User: skommidi
 * Date: 7/25/14
 */
public class ErrorHdrAssertable {

    private MessageInputStream msg;
    private ErrorHeader err_hdr;
    private boolean booleanExpectation;


    public ErrorHdrAssertable(MessageInputStream msg) {
        this.msg = msg;
    }

    public ErrorHdrAssertable shouldBe() {
        this.booleanExpectation = true;
        return this;
    }

    public ErrorHdrAssertable shouldHave() {
        this.booleanExpectation = true;
        return this;
    }

    public ErrorHdrAssertable shouldNotBe() {
        this.booleanExpectation = false;
        return this;
    }

    public ErrorHdrAssertable validateHdr() {
        try {
            err_hdr = msg.getErrorHeader();
            assertNull(msg.getMessageHeader());
            assertNotNull(err_hdr);
            assertNull(msg.getKeyExchangeCryptoContext());
            assertNull(msg.getPayloadCryptoContext());
            assertTrue(err_hdr.getMessageId() >= 0);

            assertNotNull(err_hdr.getErrorMessage());
            assertNotNull(err_hdr.getInternalCode());
        } catch(AssertionError e) {
            if(this.booleanExpectation) {
                throw e;
            }
        }
        return this;
    }

    public ErrorHdrAssertable validateErrCode(MslConstants.ResponseCode respCode) {
        err_hdr = msg.getErrorHeader();
        assertEquals(err_hdr.getErrorCode(), respCode);
        return this;
    }
}
