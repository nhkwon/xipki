/*
 *
 * Copyright (c) 2013 - 2017 Lijun Liao
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License version 3
 * as published by the Free Software Foundation with the addition of the
 * following permission added to Section 15 as permitted in Section 7(a):
 *
 * FOR ANY PART OF THE COVERED WORK IN WHICH THE COPYRIGHT IS OWNED BY
 * THE AUTHOR LIJUN LIAO. LIJUN LIAO DISCLAIMS THE WARRANTY OF NON INFRINGEMENT
 * OF THIRD PARTY RIGHTS.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * The interactive user interfaces in modified source and object code versions
 * of this program must display Appropriate Legal Notices, as required under
 * Section 5 of the GNU Affero General Public License.
 *
 * You can be released from the requirements of the license by purchasing
 * a commercial license. Buying such a license is mandatory as soon as you
 * develop commercial activities involving the XiPKI software without
 * disclosing the source code of your own applications.
 *
 * For more information, please contact Lijun Liao at this
 * address: lijun.liao@gmail.com
 */

package org.xipki.security.pkcs11.proxy.msg;

import java.io.IOException;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERUTF8String;
import org.xipki.common.util.ParamUtil;
import org.xipki.security.exception.BadAsn1ObjectException;
import org.xipki.security.pkcs11.P11NewKeyControl;
import org.xipki.security.pkcs11.P11SlotIdentifier;

/**
 *
 * <pre>
 * GenSecretKeyParams ::= SEQUENCE {
 *     slotId               P11SlotIdentifier,
 *     label                UTF8STRING,
 *     control              NewKeyControl,
 *     keyType              INTEGER,
 *     keysize              INTEGER }
 * </pre>
 *
 * @author Lijun Liao
 * @since 2.0.0
 */

// CHECKSTYLE:SKIP
public class Asn1GenSecretKeyParams extends ASN1Object {

    private final P11SlotIdentifier slotId;

    private final String label;

    private final P11NewKeyControl control;

    private final long keyType;

    private final int keysize;

    public Asn1GenSecretKeyParams(final P11SlotIdentifier slotId, final String label,
            final P11NewKeyControl control, final long keyType, final int keysize) {
        this.slotId = ParamUtil.requireNonNull("slotId", slotId);
        this.label = ParamUtil.requireNonBlank("label", label);
        this.control = ParamUtil.requireNonNull("control", control);
        this.keyType = keyType;
        this.keysize = ParamUtil.requireMin("keysize", keysize, 1);
    }

    private Asn1GenSecretKeyParams(final ASN1Sequence seq) throws BadAsn1ObjectException {
        Asn1Util.requireRange(seq, 5, 5);
        int idx = 0;
        slotId = Asn1P11SlotIdentifier.getInstance(seq.getObjectAt(idx++)).slotId();
        label = Asn1Util.getUtf8String(seq.getObjectAt(idx++));
        control = Asn1NewKeyControl.getInstance(seq.getObjectAt(idx++)).control();
        keyType = Asn1Util.getInteger(seq.getObjectAt(idx++)).longValue();
        keysize = Asn1Util.getInteger(seq.getObjectAt(idx++)).intValue();
        ParamUtil.requireMin("keysize", keysize, 1);
    }

    public static Asn1GenSecretKeyParams getInstance(final Object obj)
            throws BadAsn1ObjectException {
        if (obj == null || obj instanceof Asn1GenSecretKeyParams) {
            return (Asn1GenSecretKeyParams) obj;
        }

        try {
            if (obj instanceof ASN1Sequence) {
                return new Asn1GenSecretKeyParams((ASN1Sequence) obj);
            } else if (obj instanceof byte[]) {
                return getInstance(ASN1Primitive.fromByteArray((byte[]) obj));
            } else {
                throw new BadAsn1ObjectException("unknown object: " + obj.getClass().getName());
            }
        } catch (IOException | IllegalArgumentException ex) {
            throw new BadAsn1ObjectException("unable to parse encoded object: " + ex.getMessage(),
                    ex);
        }
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector vector = new ASN1EncodableVector();
        vector.add(new Asn1P11SlotIdentifier(slotId));
        vector.add(new DERUTF8String(label));
        vector.add(new ASN1Integer(keyType));
        vector.add(new ASN1Integer(keysize));
        return new DERSequence(vector);
    }

    public P11SlotIdentifier slotId() {
        return slotId;
    }

    public String label() {
        return label;
    }

    public P11NewKeyControl control() {
        return control;
    }

    public long keyType() {
        return keyType;
    }

    public int keysize() {
        return keysize;
    }

}
