package de.cau.cs.se.instrumentation.rl.generator.perl

import de.cau.cs.se.instrumentation.rl.recordLang.Type
import de.cau.cs.se.instrumentation.rl.recordLang.RecordType
import de.cau.cs.se.instrumentation.rl.recordLang.Classifier
import de.cau.cs.se.instrumentation.rl.recordLang.Model
import org.eclipse.emf.common.util.EList
import de.cau.cs.se.instrumentation.rl.recordLang.Property
import java.io.File
import de.cau.cs.se.instrumentation.rl.generator.AbstractRecordTypeGenerator
import de.cau.cs.se.instrumentation.rl.validation.PropertyEvaluation

class RecordTypeGenerator extends AbstractRecordTypeGenerator {
		
	/**
	 * Create a perl based record for kieker
	 */
	override createContent(RecordType type, String author, String version) '''
		use strict;
		use warnings;
		
		package «type.recordName»;
		
		=head1 NAME
		
		«type.recordName» 
		
		=head1 SYNOPSIS
		
		 my $record = «type.recordName»->new(«PropertyEvaluation::collectAllProperties(type).createParameterCall»);
		 
		 $writer->write($record->genoutput());
		
		=head1 DESCRIPTION
		
		Auto-generated structures. See the IRL code.
				
		=head1 METHODS
		
		=head2 $record = «type.recordName»->new(«PropertyEvaluation::collectAllProperties(type).createParameterCall»);
		
		Creates a new record with the given parameters.
		
		=cut
		
		sub new {
		  my («PropertyEvaluation::collectAllProperties(type).createParameterCall») = @_;
		  my $this = {
		    «PropertyEvaluation::collectAllProperties(type).map[createProperty].join(',\n')»
		  };
		
		  return bless($this,$type);
		}
		
		=head2 $string = $record->genoutput();
		
		Serializes the record for output. Returns the serialized form of the record.
						
		=head1 COPYRIGHT and LICENCE
		
		Copyright 2013 Kieker Project (http://kieker-monitoring.net)
		
		Licensed under the Apache License, Version 2.0 (the "License"); 
		you may not use this file except in compliance with the License.
		You may obtain a copy of the License at
		
		http://www.apache.org/licenses/LICENSE-2.0
		
		Unless required by applicable law or agreed to in writing, software
		distributed under the License is distributed on an "AS IS" BASIS,
		WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
		See the License for the specific language governing permissions and
		limitations under the License.
		
		=cut
	'''
	
	/**
	 * Create perl type names.
	 * Most likely this routine is never used, as perl sucks in typing.
	 */
	override createTypeName(Classifier classifier) {
		switch (classifier.class_.name) {
			case 'string' : 'String'
			default : classifier.class_.name
		}
	}
	
	/**
	 * Create one property for the type declaration.
	 */
	def createProperty(Property property) '''«property.name» => $«property.name»'''
	
	/**
	 * Compute the absolute Perl package name, which is a FQN name of the record.
	 */
	def recordName(RecordType type) '''«(type.eContainer as Model).name.replace('.','::')»::«type.name»'''
		
	/**
	 * 
	 */
	def createParameterCall(EList<Property> list) {
		list.map['''$«name»'''].join(', ')
	}
	
	/**
	 * Compute the directory name for a record type.
	 */
	override directoryName(Type type) '''perl«File::separator»«(type.eContainer as Model).name.replace('.',File::separator)»'''
		
	/**
	 * Return the extension used for the file type generated by this generator. 
	 */
	override fileName(Type type) '''«type.directoryName»«File::separator»«type.name».pm'''
	
}