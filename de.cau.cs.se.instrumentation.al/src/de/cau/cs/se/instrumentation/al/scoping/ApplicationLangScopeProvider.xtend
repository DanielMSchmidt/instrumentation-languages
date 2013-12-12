/*
 * generated by Xtext
 */
package de.cau.cs.se.instrumentation.al.scoping

import de.cau.cs.se.instrumentation.al.applicationLang.Query
import org.eclipse.xtext.scoping.IScope
import org.eclipse.emf.ecore.EReference
import de.cau.cs.se.instrumentation.al.applicationLang.LocationQuery
import de.cau.cs.se.instrumentation.al.applicationLang.Node
import de.cau.cs.se.instrumentation.al.applicationLang.ContainerNode
import org.eclipse.xtext.scoping.Scopes;

/**
 * This class contains custom scoping description.
 * 
 * see : http://www.eclipse.org/Xtext/documentation.html#scoping
 * on how and when to use it 
 *
 */
class ApplicationLangScopeProvider extends org.eclipse.xtext.scoping.impl.AbstractDeclarativeScopeProvider {

	def IScope scope_Query_method(Query context, EReference reference) {
		System.out.println("scope_Query_method")
		val Node node = context.location.leaveNode
		System.out.println("node " + (node as ContainerNode)?.container.name)
		if (node instanceof ContainerNode) {
			return  Scopes.scopeFor((node as ContainerNode).container.methods)
		} else {
			return IScope.NULLSCOPE
		}
	}
	
	
	def Node leaveNode(LocationQuery query) {
		if (query.specialization != null)
			query.specialization.leaveNode
		else
			query.node
	}
	
}
