/*
 *
 * This file is part of the XiPKI project.
 * Copyright (c) 2014 - 2015 Lijun Liao
 * Author: Lijun Liao
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License version 3
 * as published by the Free Software Foundation with the addition of the
 * following permission added to Section 15 as permitted in Section 7(a):
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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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

package org.xipki.security.speed.p12.cmd;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

import org.apache.karaf.shell.commands.Command;
import org.xipki.common.LoadExecutor;
import org.xipki.security.speed.p12.P12RSASignLoadTest;

/**
 * @author Lijun Liao
 */

@Command(scope = "xipki-tk", name = "bspeed-rsa-sign-p12",
        description="performance test of PKCS#12 RSA signature creation (batch)")
public class BSpeedP12RSASignCmd extends BSpeedP12SignCmd
{

    @Override
    protected List<LoadExecutor> getTesters()
    throws Exception
    {
        List<LoadExecutor> ret = new LinkedList<>();
        int[] keysizes = new int[]{1024, 2048, 3072, 4096};
        for(int keysize : keysizes)
        {
            ret.add(
                    new P12RSASignLoadTest(securityFactory, sigAlgo, keysize, new BigInteger("0x10001")));
        }
        return ret;
    }
}
