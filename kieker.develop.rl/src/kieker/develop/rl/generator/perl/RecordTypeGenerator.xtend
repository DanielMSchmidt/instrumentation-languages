package kieker.develop.rl.generator.perl

import kieker.develop.rl.recordLang.Type
import kieker.develop.rl.recordLang.RecordType
import kieker.develop.rl.recordLang.Classifier
import kieker.develop.rl.recordLang.Model
import kieker.develop.rl.recordLang.Property
import java.io.File
import kieker.develop.rl.generator.AbstractRecordTypeGenerator
import kieker.develop.rl.validation.PropertyEvaluation
import java.util.Collection
import java.util.Calendar

class RecordTypeGenerator extends AbstractRecordTypeGenerator {

	/**
	 * Return the unique id.
	 */
	override getId() '''perl'''
	
	/**
	 * Return the preferences activation description.
	 */
	override getDescription() '''Perl record generator'''
	
	/**
	 * No perl structures for abstract record types.
	 */
	override boolean supportsAbstractRecordType()  { false }

	/**
	 * Compute the directory name for a record type.
	 */
	override getDirectoryName(Type type) '''«(type.eContainer as Model).name.replace('.',File::separator)»'''
		
	/**
	 * Return the extension used for the file type generated by this generator. 
	 */
	override getFileName(Type type) '''«type.getDirectoryName»«File::separator»«type.name».pm'''
		
	override getOutletType() '''perl'''
	
	/**
	 * Create a perl based record for kieker
	 */
	override createContent(RecordType type, String author, String version, String headerComment) {
		'''
		«IF (!headerComment.equals(""))»«headerComment.replace("THIS-YEAR", Calendar.getInstance().get(Calendar.YEAR).toString)»
		«ENDIF»use strict;
		use warnings;
		
		package «type.recordName»;
		
		=head1 NAME
		
		«type.recordName» 
		
		=head1 SYNOPSIS
		
		 my $record = «type.recordName»->new(«PropertyEvaluation::collectAllDataProperties(type).createParameterCall»);
		 
		 $writer->write($record->genoutput());
		
		=head1 DESCRIPTION
		
		Auto-generated structures. See the IRL code.
				
		=head1 METHODS
		
		=head2 $record = «type.recordName»->new(«PropertyEvaluation::collectAllDataProperties(type).createParameterCall»);
		
		Creates a new record with the given parameters.
		
		=cut
		
		sub new {
		  my («PropertyEvaluation::collectAllDataProperties(type).createParameterCall») = @_;
		  my $this = {
		    «PropertyEvaluation::collectAllDataProperties(type).map[createProperty].join(',\n')»
		  };
		
		  return bless($this,$type);
		}
		
		=head2 $string = $record->genoutput();
		
		Serializes the record for output. Returns the serialized form of the record.
						
		=head1 COPYRIGHT and LICENCE
		
		Copyright «Calendar.getInstance().get(Calendar.YEAR)» Kieker Project (http://kieker-monitoring.net)
		
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
	}
	
	/**
	 * Create perl type names.
	 * Most likely this routine is never used, as perl sucks in typing.
	 */
	def createTypeName(Classifier classifier) {
		switch (classifier.type.name) {
			case 'string' : 'String'
			default : classifier.type.name
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
	def createParameterCall(Collection<Property> list) {
		list.map['''$«name»'''].join(', ')
	}
	
	
}