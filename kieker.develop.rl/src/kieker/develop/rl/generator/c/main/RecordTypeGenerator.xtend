package kieker.develop.rl.generator.c.main

import kieker.develop.rl.recordLang.Type
import kieker.develop.rl.recordLang.RecordType
import kieker.develop.rl.recordLang.Property
import kieker.develop.rl.recordLang.Classifier
import kieker.develop.rl.recordLang.Model
import java.io.File
import kieker.develop.rl.generator.AbstractRecordTypeGenerator
import kieker.develop.rl.validation.PropertyEvaluation

import static extension kieker.develop.rl.generator.c.CommonCFunctionsExtension.*
import java.util.Calendar
import kieker.develop.rl.typing.BaseTypes
import kieker.develop.rl.generator.InternalErrorException

class RecordTypeGenerator extends AbstractRecordTypeGenerator {
	
	/**
	 * Return the unique id.
	 */
	override getId() '''c.main'''
	
	/**
	 * Return the preferences activation description.
	 */
	override getDescription() '''C code generator'''
	
	/**
	 * No serialization for abstract record types.
	 */
	override boolean supportsAbstractRecordType()  { false }
	
	/**
	 * Compute the directory name for a record type.
	 */
	override getDirectoryName(Type type) '''«(type.eContainer as Model).name.replace('.',File::separator)»'''

	/**
	 * compute the filename of a c file. 
 	*/
	override getFileName(Type type) '''«type.getDirectoryName»«File::separator»«type.name.cstyleName».c'''	
	
	/**
	 * Return the language type name.
	 */
	override getOutletType() '''c'''
			
	/**
	 * Primary code generation template.
	 * 
	 * @param type
	 * 		one record type to be used to create monitoring record
	 * @param author
	 * 		generic author name for the record
	 * @param version
	 * 		generic kieker version for the record
	 * @param headerComment
	 *      comment placed as header of the file
	 */
	override createContent(RecordType type, String author, String version, String headerComment) {
		'''
		«IF (!headerComment.equals(""))»«headerComment.replace("THIS-YEAR", Calendar.getInstance().get(Calendar.YEAR).toString)»
		«ENDIF»#include <stdlib.h>
		#include <kieker.h>
		#include "«type.getDirectoryName»/«type.packageName»_«type.name.cstyleName».h"

		/**
		 * Author: «author»
		 * Version: «version»
		 */
		«type.createSerializer»
		'''
	}
	
	/**
	 * Generate the serializer for the given record type.
	 */
	private def createSerializer(RecordType type) '''
		/*
		 * Serialize an «type.name» and return the size of the written structure.
		 *
		 * buffer = the buffer to send the data
		 * id = id to identify the record type
		 * offset = store data to buffer at offset
		 * value = the value to be stored
		 *
		 * returns size of written structure
		 */
		int «type.packageName»_«type.name.cstyleName»_serialize(char *buffer, const int id, const int offset, const «type.packageName»_«type.name.cstyleName» value) {
			int length = 0;
			«PropertyEvaluation::collectAllDataProperties(type).map[createValueSerializer].join»
			return length;
		}
	'''
	
	/**
	 * 
	 */
	private def createValueSerializer(Property property) '''
		length += kieker_serialize_«PropertyEvaluation::findType(property).serializerSuffix»(buffer,offset,«property.name»);
	'''
		
	/**
	 * 
	 */
	private def serializerSuffix(Classifier classifier) throws InternalErrorException {
		switch (BaseTypes.getTypeEnum(classifier.type)) {
			case STRING : 'string'
			case BYTE : 'int8'
			case SHORT : 'int16'
			case INT : 'int32'
			case LONG : 'int64'
			case FLOAT : 'float'
			case DOUBLE : 'double'
			case CHAR : 'int16'
			case BOOLEAN : 'boolean'
		}
	}
		
}