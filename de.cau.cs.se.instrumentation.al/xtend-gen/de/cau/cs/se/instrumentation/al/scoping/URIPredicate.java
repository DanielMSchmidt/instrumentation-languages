/**
 * generated by Xtext
 */
package de.cau.cs.se.instrumentation.al.scoping;

import com.google.common.base.Predicate;
import org.eclipse.xtext.resource.IEObjectDescription;

@SuppressWarnings("all")
public class URIPredicate implements Predicate<IEObjectDescription> {
  public boolean apply(final IEObjectDescription input) {
    System.out.println(("filter " + input));
    final String isNSURI = input.getUserData("nsURI");
    return "true".equals(isNSURI);
  }
}