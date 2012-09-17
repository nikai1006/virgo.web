/*******************************************************************************
 * Copyright (c) 2008, 2010 VMware Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   VMware Inc. - initial contribution
 *******************************************************************************/

package org.eclipse.virgo.web.tomcat.support;

import org.osgi.framework.Bundle;

/**
 * A {@link ClassLoader} that delegates load class requests to one or more
 * {@link Bundle Bundles}.
 * <p />
 *
 * <strong>Concurrent Semantics</strong><br />
 *
 * Thread-safe.
 *
 */
final class LoadClassDelegatingClassLoader extends ClassLoader {
    
    private final Bundle[] delegates;
    
    /**
     * @param delegates
     */
    LoadClassDelegatingClassLoader(Bundle[] delegates) {
    	super(null);
        this.delegates = delegates;
    }    

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        for (Bundle delegate : delegates) {
            try {
                Class<?> loadedClass = delegate.loadClass(name);
                if (loadedClass != null) {
                    return loadedClass;
                }
            } catch (ClassNotFoundException _) {
            }
        }
        return null;
    }
    
}
