/**
 * generated by Xtext
 */
package de.cau.cs.se.instrumentation.rl.scoping;

import com.google.common.base.Predicate;
import org.eclipse.xtext.resource.IEObjectDescription;

@SuppressWarnings("all")
public class MyPredicate implements Predicate<IEObjectDescription> {
  public boolean apply(final IEObjectDescription input) {
    final String isNSURI = input.getUserData("nsURI");
    return "true".equals(isNSURI);
  }
}