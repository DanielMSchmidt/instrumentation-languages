/*
* generated by Xtext
*/
package de.cau.cs.se.instrumentation.language;

import org.eclipse.xtext.junit4.IInjectorProvider;

import com.google.inject.Injector;

public class InstrumentationLanguageUiInjectorProvider implements IInjectorProvider {
	
	public Injector getInjector() {
		return de.cau.cs.se.instrumentation.language.ui.internal.InstrumentationLanguageActivator.getInstance().getInjector("de.cau.cs.se.instrumentation.language.InstrumentationLanguage");
	}
	
}