/*
 * generated by Xtext
 */
package kieker.develop.rl.ui;

import org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory;
import org.osgi.framework.Bundle;

import com.google.inject.Injector;

import kieker.develop.rl.ui.internal.RecordLangActivator;

/**
 * This class was generated. Customizations should only happen in a newly
 * introduced subclass. 
 */
public class RecordLangExecutableExtensionFactory extends AbstractGuiceAwareExecutableExtensionFactory {

	@Override
	protected Bundle getBundle() {
		return RecordLangActivator.getInstance().getBundle();
	}
	
	@Override
	protected Injector getInjector() {
		return RecordLangActivator.getInstance().getInjector(RecordLangActivator.KIEKER_DEVELOP_RL_RECORDLANG);
	}
	
}
