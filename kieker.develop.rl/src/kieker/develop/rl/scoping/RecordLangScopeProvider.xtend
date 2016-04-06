/***************************************************************************
 * Copyright 2013 Kieker Project (http://kieker-monitoring.net)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ***************************************************************************/
package kieker.develop.rl.scoping

import kieker.develop.rl.recordLang.ComplexType
import kieker.develop.rl.recordLang.ForeignKey
import kieker.develop.rl.recordLang.Property
import kieker.develop.rl.validation.PropertyEvaluation
import java.util.Collection
import java.util.List
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.EGenericType
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.scoping.IScope
import org.eclipse.xtext.scoping.Scopes
import org.eclipse.xtext.scoping.impl.AbstractDeclarativeScopeProvider

/**
 * This class contains custom scoping description.
 * 
 * see : http://www.eclipse.org/Xtext/documentation.html#scoping
 * on how and when to use it 
 *
 */
class RecordLangScopeProvider extends AbstractDeclarativeScopeProvider {

	/**
	 * Define scope for foreign key reference.
	 */
	def IScope scope_ForeignKey_propertyRef(ForeignKey key, EReference reference) {
		return Scopes::scopeFor(PropertyEvaluation::collectAllProperties(key.recordType))
	}
	
	/**
	 * Build a scope containing all properties.
	 */
	def IScope scope_Property_referTo(Property property, EReference reference) {
		return Scopes::scopeFor(PropertyEvaluation::collectAllProperties(property.eContainer() as ComplexType))
	}
					
	/**
	 * Internal function to collect all properties of a imported classifier.
	 */
	def Collection<EStructuralFeature> getAllExternalProperties(EClassifier classifier,
	        Class<EStructuralFeature> type) {
		val List<EStructuralFeature> result = EcoreUtil2::getAllContentsOfType(classifier, type)
		if (classifier instanceof EClass) {
			for (EGenericType generic : (classifier as EClass).getEGenericSuperTypes() ) {
				result.addAll(getAllExternalProperties(generic.getEClassifier(), type))
			}
		}
		return result
	}

}