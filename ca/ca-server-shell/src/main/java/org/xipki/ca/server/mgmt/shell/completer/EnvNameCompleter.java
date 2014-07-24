/*
 * Copyright (c) 2014 Lijun Liao
 *
 * TO-BE-DEFINE
 *
 */

package org.xipki.ca.server.mgmt.shell.completer;

import java.util.Set;

/**
 * @author Lijun Liao
 */

public class EnvNameCompleter extends MgmtNameCompleter
{

    @Override
    protected Set<String> getEnums()
    {
        return caManager.getEnvParameterResolver().getAllParameterNames();
    }

}