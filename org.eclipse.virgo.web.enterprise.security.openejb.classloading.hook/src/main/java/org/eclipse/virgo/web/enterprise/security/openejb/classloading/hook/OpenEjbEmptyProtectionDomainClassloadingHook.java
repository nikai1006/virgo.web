package org.eclipse.virgo.web.enterprise.security.openejb.classloading.hook;

import java.security.AllPermission;
import java.security.PermissionCollection;
import java.security.ProtectionDomain;
import java.util.ArrayList;

import org.eclipse.osgi.baseadaptor.BaseData;
import org.eclipse.osgi.baseadaptor.HookConfigurator;
import org.eclipse.osgi.baseadaptor.HookRegistry;
import org.eclipse.osgi.baseadaptor.bundlefile.BundleEntry;
import org.eclipse.osgi.baseadaptor.hooks.ClassLoadingHook;
import org.eclipse.osgi.baseadaptor.loader.BaseClassLoader;
import org.eclipse.osgi.baseadaptor.loader.ClasspathEntry;
import org.eclipse.osgi.baseadaptor.loader.ClasspathManager;
import org.eclipse.osgi.framework.adaptor.BundleProtectionDomain;
import org.eclipse.osgi.framework.adaptor.ClassLoaderDelegate;
import org.eclipse.osgi.internal.baseadaptor.DefaultClassLoader;

//Applicable only for org.apache.openejb.core
public class OpenEjbEmptyProtectionDomainClassloadingHook implements ClassLoadingHook, HookConfigurator {
	//Equinox implicitly creates a ProtectionDomain for each bundle with all permissions.
	//Openejb does security checks related to security manager with its own protection domain which is not the app protection domain in OSGi case
	@Override
	public BaseClassLoader createClassLoader(ClassLoader parent,
			ClassLoaderDelegate delegate, BundleProtectionDomain domain,
			BaseData data, String[] classpath) {
		
		ProtectionDomain processedProtectionDomain = domain;
	   
		if (processedProtectionDomain == null && data.getSymbolicName().equals("org.apache.openejb.core")) {		
			PermissionCollection emptyPermissionCollection = (new AllPermission()).newPermissionCollection();
			processedProtectionDomain = new ProtectionDomain(null, emptyPermissionCollection);			
		}
		return new DefaultClassLoader(parent, delegate, processedProtectionDomain, data, classpath);
	}

	@Override
	public boolean addClassPathEntry(ArrayList<ClasspathEntry> arg0,
			String arg1, ClasspathManager arg2, BaseData arg3,
			ProtectionDomain arg4) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String findLibrary(BaseData arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClassLoader getBundleClassLoaderParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initializedClassLoader(BaseClassLoader arg0, BaseData arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] processClass(String arg0, byte[] arg1, ClasspathEntry arg2,
			BundleEntry arg3, ClasspathManager arg4) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addHooks(HookRegistry hookRegistry) {
		hookRegistry.addClassLoadingHook(this);
		
	}
}