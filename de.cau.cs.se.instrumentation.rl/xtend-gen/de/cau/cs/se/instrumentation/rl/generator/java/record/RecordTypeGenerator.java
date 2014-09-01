package de.cau.cs.se.instrumentation.rl.generator.java.record;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure2;
import org.eclipse.xtext.xbase.lib.StringExtensions;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

import de.cau.cs.se.instrumentation.rl.generator.AbstractRecordTypeGenerator;
import de.cau.cs.se.instrumentation.rl.generator.InternalErrorException;
import de.cau.cs.se.instrumentation.rl.generator.java.RlType2JavaTypeExtensions;
import de.cau.cs.se.instrumentation.rl.recordLang.ArrayLiteral;
import de.cau.cs.se.instrumentation.rl.recordLang.ArraySize;
import de.cau.cs.se.instrumentation.rl.recordLang.BooleanLiteral;
import de.cau.cs.se.instrumentation.rl.recordLang.Classifier;
import de.cau.cs.se.instrumentation.rl.recordLang.Constant;
import de.cau.cs.se.instrumentation.rl.recordLang.ConstantLiteral;
import de.cau.cs.se.instrumentation.rl.recordLang.FloatLiteral;
import de.cau.cs.se.instrumentation.rl.recordLang.IntLiteral;
import de.cau.cs.se.instrumentation.rl.recordLang.Literal;
import de.cau.cs.se.instrumentation.rl.recordLang.Model;
import de.cau.cs.se.instrumentation.rl.recordLang.PartialRecordType;
import de.cau.cs.se.instrumentation.rl.recordLang.Property;
import de.cau.cs.se.instrumentation.rl.recordLang.RecordType;
import de.cau.cs.se.instrumentation.rl.recordLang.StringLiteral;
import de.cau.cs.se.instrumentation.rl.recordLang.Type;
import de.cau.cs.se.instrumentation.rl.validation.PropertyEvaluation;

@SuppressWarnings("all")
public class RecordTypeGenerator extends AbstractRecordTypeGenerator {
	/**
	 * Define language/generation type, which is also used to define the outlet.
	 */
	@Override
	public String outletType() {
		final StringConcatenation _builder = new StringConcatenation();
		_builder.append("java");
		return _builder.toString();
	}

	/**
	 * Compute the directory name for a record type.
	 */
	@Override
	public CharSequence directoryName(final Type type) {
		final StringConcatenation _builder = new StringConcatenation();
		final EObject _eContainer = type.eContainer();
		final String _name = ((Model) _eContainer).getName();
		final String _replace = _name.replace(".", File.separator);
		_builder.append(_replace, "");
		return _builder;
	}

	/**
	 * Compute file name.
	 */
	@Override
	public String fileName(final Type type) {
		final StringConcatenation _builder = new StringConcatenation();
		final CharSequence _directoryName = this.directoryName(type);
		_builder.append(_directoryName, "");
		_builder.append(File.separator, "");
		final String _name = type.getName();
		_builder.append(_name, "");
		_builder.append(".java");
		return _builder.toString();
	}

	/**
	 * Primary code generation template.
	 * 
	 * @params type
	 *         one record type to be used to create monitoring record
	 * @params author
	 *         generic author name for the record
	 * @params version
	 *         generic kieker version for the record
	 */
	@Override
	public CharSequence createContent(final RecordType type, final String author, final String version) {
		CharSequence _xblockexpression = null;
		{
			final long _computeDefaultSUID = ComputeUID.computeDefaultSUID(type);
			final String serialUID = (Long.valueOf(_computeDefaultSUID) + "L");
			final Collection<Property> allDataProperties = PropertyEvaluation.collectAllDataProperties(type);
			final Iterable<Property> allDeclarationProperties = this.collectAllDeclarationProperties(type);
			String _xifexpression = null;
			final String _author = type.getAuthor();
			final boolean _equals = Objects.equal(_author, null);
			if (_equals) {
				_xifexpression = author;
			} else {
				final String _author_1 = type.getAuthor();
				_xifexpression = _author_1;
			}
			final String definedAuthor = _xifexpression;
			String _xifexpression_1 = null;
			final String _since = type.getSince();
			final boolean _equals_1 = Objects.equal(_since, null);
			if (_equals_1) {
				_xifexpression_1 = version;
			} else {
				final String _since_1 = type.getSince();
				_xifexpression_1 = _since_1;
			}
			final String definedVersion = _xifexpression_1;
			final StringConcatenation _builder = new StringConcatenation();
			_builder.append("/***************************************************************************");
			_builder.newLine();
			_builder.append(" ");
			_builder.append("* Copyright 2013 Kieker Project (http://kieker-monitoring.net)");
			_builder.newLine();
			_builder.append(" ");
			_builder.append("*");
			_builder.newLine();
			_builder.append(" ");
			_builder.append("* Licensed under the Apache License, Version 2.0 (the \"License\");");
			_builder.newLine();
			_builder.append(" ");
			_builder.append("* you may not use this file except in compliance with the License.");
			_builder.newLine();
			_builder.append(" ");
			_builder.append("* You may obtain a copy of the License at");
			_builder.newLine();
			_builder.append(" ");
			_builder.append("*");
			_builder.newLine();
			_builder.append(" ");
			_builder.append("*     http://www.apache.org/licenses/LICENSE-2.0");
			_builder.newLine();
			_builder.append(" ");
			_builder.append("*");
			_builder.newLine();
			_builder.append(" ");
			_builder.append("* Unless required by applicable law or agreed to in writing, software");
			_builder.newLine();
			_builder.append(" ");
			_builder.append("* distributed under the License is distributed on an \"AS IS\" BASIS,");
			_builder.newLine();
			_builder.append(" ");
			_builder.append("* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.");
			_builder.newLine();
			_builder.append(" ");
			_builder.append("* See the License for the specific language governing permissions and");
			_builder.newLine();
			_builder.append(" ");
			_builder.append("* limitations under the License.");
			_builder.newLine();
			_builder.append(" ");
			_builder.append("***************************************************************************/");
			_builder.newLine();
			_builder.newLine();
			_builder.append("package ");
			final EObject _eContainer = type.eContainer();
			final String _name = ((Model) _eContainer).getName();
			_builder.append(_name, "");
			_builder.append(";");
			_builder.newLineIfNotEmpty();
			_builder.newLine();
			{
				final boolean _isAbstract = type.isAbstract();
				final boolean _not = (!_isAbstract);
				if (_not) {
					_builder.append("import java.nio.BufferOverflowException;");
					_builder.newLineIfNotEmpty();
				}
			}
			_builder.append("import java.nio.BufferUnderflowException;");
			_builder.newLineIfNotEmpty();
			_builder.append("import java.nio.ByteBuffer;");
			_builder.newLine();
			_builder.newLine();
			{
				final RecordType _parent = type.getParent();
				final boolean _equals_2 = Objects.equal(_parent, null);
				if (_equals_2) {
					_builder.append("import kieker.common.record.AbstractMonitoringRecord;");
					_builder.newLineIfNotEmpty();
					_builder.append("import kieker.common.record.IMonitoringRecord;");
					_builder.newLine();
				}
			}
			_builder.append("import kieker.common.util.registry.IRegistry;");
			_builder.newLineIfNotEmpty();
			_builder.newLine();
			CharSequence _xifexpression_2 = null;
			final RecordType _parent_1 = type.getParent();
			final boolean _notEquals = (!Objects.equal(_parent_1, null));
			if (_notEquals) {
				final CharSequence _createParentImport = this.createParentImport(type);
				_xifexpression_2 = _createParentImport;
			}
			_builder.append(_xifexpression_2, "");
			_builder.newLineIfNotEmpty();
			String _xifexpression_3 = null;
			boolean _and = false;
			final EList<PartialRecordType> _parents = type.getParents();
			final boolean _notEquals_1 = (!Objects.equal(_parents, null));
			if (!_notEquals_1) {
				_and = false;
			} else {
				final EList<PartialRecordType> _parents_1 = type.getParents();
				final int _size = _parents_1.size();
				final boolean _greaterThan = (_size > 0);
				_and = (_notEquals_1 && _greaterThan);
			}
			if (_and) {
				final EList<PartialRecordType> _parents_2 = type.getParents();
				final Function1<PartialRecordType, CharSequence> _function = new Function1<PartialRecordType, CharSequence>() {
					public CharSequence apply(final PartialRecordType i) {
						final CharSequence _createInterfaceImport = RecordTypeGenerator.this.createInterfaceImport(i);
						return _createInterfaceImport;
					}
				};
				final List<CharSequence> _map = ListExtensions.<PartialRecordType, CharSequence> map(_parents_2, _function);
				final String _join = IterableExtensions.join(_map);
				_xifexpression_3 = _join;
			}
			_builder.append(_xifexpression_3, "");
			_builder.newLineIfNotEmpty();
			_builder.newLine();
			_builder.append("/**");
			_builder.newLine();
			_builder.append(" ");
			_builder.append("* @author ");
			_builder.append(definedAuthor, " ");
			_builder.newLineIfNotEmpty();
			_builder.append(" ");
			_builder.append("* ");
			_builder.newLine();
			_builder.append(" ");
			_builder.append("* @since ");
			_builder.append(definedVersion, " ");
			_builder.newLineIfNotEmpty();
			_builder.append(" ");
			_builder.append("*/");
			_builder.newLine();
			_builder.append("public ");
			String _xifexpression_4 = null;
			final boolean _isAbstract_1 = type.isAbstract();
			if (_isAbstract_1) {
				_xifexpression_4 = "abstract ";
			}
			_builder.append(_xifexpression_4, "");
			_builder.append("class ");
			final String _name_1 = type.getName();
			_builder.append(_name_1, "");
			_builder.append(" extends ");
			final CharSequence _createParent = this.createParent(type);
			_builder.append(_createParent, "");
			final CharSequence _createImplements = this.createImplements(type);
			_builder.append(_createImplements, "");
			_builder.append(" {");
			_builder.newLineIfNotEmpty();
			_builder.append("\t");
			{
				final boolean _isAbstract_2 = type.isAbstract();
				final boolean _not_1 = (!_isAbstract_2);
				if (_not_1) {
					_builder.append("/** Descriptive definition of the serialization size of the record. */");
					_builder.newLineIfNotEmpty();
					_builder.append("\t");
					_builder.append("public static final int SIZE = ");
					String _xifexpression_5 = null;
					final int _size_1 = allDataProperties.size();
					final boolean _equals_3 = (_size_1 == 0);
					if (_equals_3) {
						_xifexpression_5 = "0";
					} else {
						final Function1<Property, String> _function_1 = new Function1<Property, String>() {
							public String apply(final Property property) {
								final String _createSizeConstant = RecordTypeGenerator.this.createSizeConstant(property, type);
								return _createSizeConstant;
							}
						};
						final Iterable<String> _map_1 = IterableExtensions.<Property, String> map(allDataProperties, _function_1);
						final String _join_1 = IterableExtensions.join(_map_1, "\n\t\t + ");
						_xifexpression_5 = _join_1;
					}
					_builder.append(_xifexpression_5, "	");
					_builder.newLineIfNotEmpty();
					_builder.append("\t");
					_builder.append(";");
					_builder.newLine();
				}
			}
			_builder.append("\t");
			_builder.append("private static final long serialVersionUID = ");
			_builder.append(serialUID, "	");
			_builder.append(";");
			_builder.newLineIfNotEmpty();
			_builder.append("\t");
			_builder.newLine();
			_builder.append("\t");
			{
				final boolean _isAbstract_3 = type.isAbstract();
				final boolean _not_2 = (!_isAbstract_3);
				if (_not_2) {
					_builder.append("public static final Class<?>[] TYPES = {");
					_builder.newLineIfNotEmpty();
					_builder.append("\t");
					_builder.append("\t");
					final Function1<Property, CharSequence> _function_2 = new Function1<Property, CharSequence>() {
						public CharSequence apply(final Property property) {
							final CharSequence _createPropertyType = RecordTypeGenerator.this.createPropertyType(property, type);
							return _createPropertyType;
						}
					};
					final Iterable<CharSequence> _map_2 = IterableExtensions.<Property, CharSequence> map(allDataProperties, _function_2);
					final String _join_2 = IterableExtensions.join(_map_2);
					_builder.append(_join_2, "		");
					_builder.newLineIfNotEmpty();
					_builder.append("\t");
					_builder.append("};");
				}
			}
			_builder.newLineIfNotEmpty();
			_builder.append("\t");
			_builder.newLine();
			_builder.append("\t");
			final EList<Constant> _constants = type.getConstants();
			final Function1<Constant, CharSequence> _function_3 = new Function1<Constant, CharSequence>() {
				public CharSequence apply(final Constant const_) {
					final CharSequence _createDefaultConstant = RecordTypeGenerator.this.createDefaultConstant(const_);
					return _createDefaultConstant;
				}
			};
			final List<CharSequence> _map_3 = ListExtensions.<Constant, CharSequence> map(_constants, _function_3);
			final String _join_3 = IterableExtensions.join(_map_3);
			_builder.append(_join_3, "	");
			_builder.newLineIfNotEmpty();
			_builder.append("\t");
			final EList<Property> _properties = type.getProperties();
			final Function1<Property, Boolean> _function_4 = new Function1<Property, Boolean>() {
				public Boolean apply(final Property property) {
					final Literal _value = property.getValue();
					final boolean _notEquals = (!Objects.equal(_value, null));
					return Boolean.valueOf(_notEquals);
				}
			};
			final Iterable<Property> _filter = IterableExtensions.<Property> filter(_properties, _function_4);
			final Function1<Property, CharSequence> _function_5 = new Function1<Property, CharSequence>() {
				public CharSequence apply(final Property property) {
					final CharSequence _createDefaultConstant = RecordTypeGenerator.this.createDefaultConstant(property);
					return _createDefaultConstant;
				}
			};
			final Iterable<CharSequence> _map_4 = IterableExtensions.<Property, CharSequence> map(_filter, _function_5);
			final String _join_4 = IterableExtensions.join(_map_4);
			_builder.append(_join_4, "	");
			_builder.newLineIfNotEmpty();
			_builder.append("\t");
			_builder.newLine();
			_builder.append("\t");
			final Function1<Property, CharSequence> _function_6 = new Function1<Property, CharSequence>() {
				public CharSequence apply(final Property property) {
					final CharSequence _createPropertyDeclaration = RecordTypeGenerator.this.createPropertyDeclaration(property);
					return _createPropertyDeclaration;
				}
			};
			final Iterable<CharSequence> _map_5 = IterableExtensions.<Property, CharSequence> map(allDeclarationProperties, _function_6);
			final String _join_5 = IterableExtensions.join(_map_5);
			_builder.append(_join_5, "	");
			_builder.newLineIfNotEmpty();
			_builder.newLine();
			_builder.append("\t");
			_builder.append("/**");
			_builder.newLine();
			_builder.append("\t ");
			_builder.append("* Creates a new instance of this class using the given parameters.");
			_builder.newLine();
			_builder.append("\t ");
			_builder.append("* ");
			_builder.newLine();
			_builder.append("\t ");
			final Function1<Property, CharSequence> _function_7 = new Function1<Property, CharSequence>() {
				public CharSequence apply(final Property property) {
					final CharSequence _createPropertyComment = RecordTypeGenerator.this.createPropertyComment(property);
					return _createPropertyComment;
				}
			};
			final Iterable<CharSequence> _map_6 = IterableExtensions.<Property, CharSequence> map(allDataProperties, _function_7);
			final String _join_6 = IterableExtensions.join(_map_6);
			_builder.append(_join_6, "	 ");
			_builder.newLineIfNotEmpty();
			_builder.append("\t ");
			_builder.append("*/");
			_builder.newLine();
			_builder.append("\t");
			_builder.append("public ");
			final String _name_2 = type.getName();
			_builder.append(_name_2, "	");
			_builder.append("(");
			final Function1<Property, CharSequence> _function_8 = new Function1<Property, CharSequence>() {
				public CharSequence apply(final Property property) {
					final CharSequence _createPropertyParameter = RecordTypeGenerator.this.createPropertyParameter(property);
					return _createPropertyParameter;
				}
			};
			final Iterable<CharSequence> _map_7 = IterableExtensions.<Property, CharSequence> map(allDataProperties, _function_8);
			final String _join_7 = IterableExtensions.join(_map_7, ", ");
			_builder.append(_join_7, "	");
			_builder.append(") {");
			_builder.newLineIfNotEmpty();
			_builder.append("\t\t");
			String _xifexpression_6 = null;
			final RecordType _parent_2 = type.getParent();
			final boolean _notEquals_2 = (!Objects.equal(_parent_2, null));
			if (_notEquals_2) {
				final RecordType _parent_3 = type.getParent();
				final Collection<Property> _collectAllDataProperties = PropertyEvaluation.collectAllDataProperties(_parent_3);
				final Function1<Property, String> _function_9 = new Function1<Property, String>() {
					public String apply(final Property it) {
						final String _name = it.getName();
						return _name;
					}
				};
				final Iterable<String> _map_8 = IterableExtensions.<Property, String> map(_collectAllDataProperties, _function_9);
				final String _join_8 = IterableExtensions.join(_map_8, ", ");
				final String _plus = ("super(" + _join_8);
				final String _plus_1 = (_plus + ");");
				_xifexpression_6 = _plus_1;
			}
			_builder.append(_xifexpression_6, "		");
			_builder.newLineIfNotEmpty();
			_builder.append("\t\t");
			final Function1<Property, CharSequence> _function_10 = new Function1<Property, CharSequence>() {
				public CharSequence apply(final Property property) {
					final CharSequence _createPropertyAssignment = RecordTypeGenerator.this.createPropertyAssignment(property);
					return _createPropertyAssignment;
				}
			};
			final Iterable<CharSequence> _map_9 = IterableExtensions.<Property, CharSequence> map(allDeclarationProperties, _function_10);
			final String _join_9 = IterableExtensions.join(_map_9);
			_builder.append(_join_9, "		");
			_builder.newLineIfNotEmpty();
			_builder.append("\t");
			_builder.append("}");
			_builder.newLine();
			_builder.newLine();
			{
				final boolean _isAbstract_4 = type.isAbstract();
				final boolean _not_3 = (!_isAbstract_4);
				if (_not_3) {
					_builder.append("\t");
					_builder.append("/**");
					_builder.newLine();
					_builder.append("\t");
					_builder.append(" ");
					_builder.append("* This constructor converts the given array into a record.");
					_builder.newLine();
					_builder.append("\t");
					_builder.append(" ");
					_builder.append("* It is recommended to use the array which is the result of a call to {@link #toArray()}.");
					_builder.newLine();
					_builder.append("\t");
					_builder.append(" ");
					_builder.append("* ");
					_builder.newLine();
					_builder.append("\t");
					_builder.append(" ");
					_builder.append("* @param values");
					_builder.newLine();
					_builder.append("\t");
					_builder.append(" ");
					_builder.append("*            The values for the record.");
					_builder.newLine();
					_builder.append("\t");
					_builder.append(" ");
					_builder.append("*/");
					_builder.newLine();
					_builder.append("\t");
					_builder.append("public ");
					final String _name_3 = type.getName();
					_builder.append(_name_3, "	");
					_builder.append("(final Object[] values) { // NOPMD (direct store of values)");
					_builder.newLineIfNotEmpty();
					_builder.append("\t");
					_builder.append("\t");
					{
						final RecordType _parent_4 = type.getParent();
						final boolean _equals_4 = Objects.equal(_parent_4, null);
						if (_equals_4) {
							_builder.append("AbstractMonitoringRecord.checkArray(values, TYPES);");
							_builder.newLineIfNotEmpty();
							_builder.append("\t");
							_builder.append("\t");
						} else {
							_builder.append("super(values, TYPES);");
							_builder.newLineIfNotEmpty();
							_builder.append("\t");
							_builder.append("\t");
						}
					}
					int _xifexpression_7 = 0;
					final RecordType _parent_5 = type.getParent();
					final boolean _notEquals_3 = (!Objects.equal(_parent_5, null));
					if (_notEquals_3) {
						final RecordType _parent_6 = type.getParent();
						final Collection<Property> _collectAllDataProperties_1 = PropertyEvaluation.collectAllDataProperties(_parent_6);
						final int _size_2 = _collectAllDataProperties_1.size();
						_xifexpression_7 = _size_2;
					} else {
						_xifexpression_7 = 0;
					}
					final String _createPropertyGenericAssignments = this.createPropertyGenericAssignments(allDeclarationProperties, _xifexpression_7);
					_builder.append(_createPropertyGenericAssignments, "		");
					_builder.newLineIfNotEmpty();
					_builder.append("\t");
					_builder.append("}");
					_builder.newLine();
				}
			}
			_builder.append("\t");
			_builder.newLine();
			_builder.append("\t");
			_builder.append("/**");
			_builder.newLine();
			_builder.append("\t ");
			_builder.append("* This constructor uses the given array to initialize the fields of this record.");
			_builder.newLine();
			_builder.append("\t ");
			_builder.append("* ");
			_builder.newLine();
			_builder.append("\t ");
			_builder.append("* @param values");
			_builder.newLine();
			_builder.append("\t ");
			_builder.append("*            The values for the record.");
			_builder.newLine();
			_builder.append("\t ");
			_builder.append("* @param valueTypes");
			_builder.newLine();
			_builder.append("\t ");
			_builder.append("*            The types of the elements in the first array.");
			_builder.newLine();
			_builder.append("\t ");
			_builder.append("*/");
			_builder.newLine();
			_builder.append("\t");
			_builder.append("protected ");
			final String _name_4 = type.getName();
			_builder.append(_name_4, "	");
			_builder.append("(final Object[] values, final Class<?>[] valueTypes) { // NOPMD (values stored directly)");
			_builder.newLineIfNotEmpty();
			_builder.append("\t\t");
			{
				final RecordType _parent_7 = type.getParent();
				final boolean _equals_5 = Objects.equal(_parent_7, null);
				if (_equals_5) {
					_builder.append("AbstractMonitoringRecord.checkArray(values, valueTypes);");
					_builder.newLineIfNotEmpty();
					_builder.append("\t\t");
				} else {
					_builder.append("super(values, valueTypes);");
					_builder.newLineIfNotEmpty();
					_builder.append("\t\t");
				}
			}
			int _xifexpression_8 = 0;
			final RecordType _parent_8 = type.getParent();
			final boolean _notEquals_4 = (!Objects.equal(_parent_8, null));
			if (_notEquals_4) {
				final RecordType _parent_9 = type.getParent();
				final Collection<Property> _collectAllDataProperties_2 = PropertyEvaluation.collectAllDataProperties(_parent_9);
				final int _size_3 = _collectAllDataProperties_2.size();
				_xifexpression_8 = _size_3;
			} else {
				_xifexpression_8 = 0;
			}
			final String _createPropertyGenericAssignments_1 = this.createPropertyGenericAssignments(allDeclarationProperties, _xifexpression_8);
			_builder.append(_createPropertyGenericAssignments_1, "		");
			_builder.newLineIfNotEmpty();
			_builder.append("\t");
			_builder.append("}");
			_builder.newLine();
			_builder.newLine();
			_builder.append("\t");
			_builder.append("/**");
			_builder.newLine();
			_builder.append("\t ");
			_builder.append("* This constructor converts the given array into a record.");
			_builder.newLine();
			_builder.append("\t ");
			_builder.append("* ");
			_builder.newLine();
			_builder.append("\t ");
			_builder.append("* @param buffer");
			_builder.newLine();
			_builder.append("\t ");
			_builder.append("*            The bytes for the record.");
			_builder.newLine();
			_builder.append("\t ");
			_builder.append("* ");
			_builder.newLine();
			_builder.append("\t ");
			_builder.append("* @throws BufferUnderflowException");
			_builder.newLine();
			_builder.append("\t ");
			_builder.append("*             if buffer not sufficient");
			_builder.newLine();
			_builder.append("\t ");
			_builder.append("*/");
			_builder.newLine();
			_builder.append("\t");
			_builder.append("public ");
			final String _name_5 = type.getName();
			_builder.append(_name_5, "	");
			_builder.append("(final ByteBuffer buffer, final IRegistry<String> stringRegistry) throws BufferUnderflowException {");
			_builder.newLineIfNotEmpty();
			_builder.append("\t\t");
			{
				final RecordType _parent_10 = type.getParent();
				final boolean _notEquals_5 = (!Objects.equal(_parent_10, null));
				if (_notEquals_5) {
					_builder.append("super(buffer, stringRegistry);");
					_builder.newLineIfNotEmpty();
					_builder.append("\t\t");
				}
			}
			final Function1<Property, CharSequence> _function_11 = new Function1<Property, CharSequence>() {
				public CharSequence apply(final Property property) {
					final CharSequence _createPropertyBinaryDeserialization = RecordTypeGenerator.this.createPropertyBinaryDeserialization(property);
					return _createPropertyBinaryDeserialization;
				}
			};
			final Iterable<CharSequence> _map_10 = IterableExtensions.<Property, CharSequence> map(allDeclarationProperties, _function_11);
			final String _join_10 = IterableExtensions.join(_map_10, "\n");
			_builder.append(_join_10, "		");
			_builder.newLineIfNotEmpty();
			_builder.append("\t");
			_builder.append("}");
			_builder.newLine();
			_builder.newLine();
			{
				final boolean _isAbstract_5 = type.isAbstract();
				final boolean _not_4 = (!_isAbstract_5);
				if (_not_4) {
					_builder.append("\t");
					_builder.append("/**");
					_builder.newLine();
					_builder.append("\t ");
					_builder.append("* {@inheritDoc}");
					_builder.newLine();
					_builder.append("\t ");
					_builder.append("*/");
					_builder.newLine();
					_builder.append("\t");
					_builder.append("@Override");
					_builder.newLine();
					_builder.append("\t");
					_builder.append("public Object[] toArray() {");
					_builder.newLine();
					_builder.append("\t\t");
					_builder.append("return new Object[] {");
					_builder.newLine();
					_builder.append("\t\t\t");
					final Function1<Property, CharSequence> _function_12 = new Function1<Property, CharSequence>() {
						public CharSequence apply(final Property property) {
							final CharSequence _createPropertyArrayEntry = RecordTypeGenerator.this.createPropertyArrayEntry(property);
							return _createPropertyArrayEntry;
						}
					};
					final Iterable<CharSequence> _map_11 = IterableExtensions.<Property, CharSequence> map(allDataProperties, _function_12);
					final String _join_11 = IterableExtensions.join(_map_11, ",\n");
					_builder.append(_join_11, "			");
					_builder.newLineIfNotEmpty();
					_builder.append("\t\t");
					_builder.append("};");
					_builder.newLine();
					_builder.append("\t");
					_builder.append("}");
					_builder.newLine();
					_builder.newLine();
					_builder.append("\t");
					_builder.append("/**");
					_builder.newLine();
					_builder.append("\t ");
					_builder.append("* {@inheritDoc}");
					_builder.newLine();
					_builder.append("\t ");
					_builder.append("*/");
					_builder.newLine();
					_builder.append("\t");
					_builder.append("@Override");
					_builder.newLine();
					_builder.append("\t");
					_builder.append("public void writeBytes(final ByteBuffer buffer, final IRegistry<String> stringRegistry) throws BufferOverflowException {");
					_builder.newLine();
					_builder.append("\t\t");
					final Function1<Property, CharSequence> _function_13 = new Function1<Property, CharSequence>() {
						public CharSequence apply(final Property property) {
							final CharSequence _createPropertyBinarySerialization = RecordTypeGenerator.this.createPropertyBinarySerialization(property);
							return _createPropertyBinarySerialization;
						}
					};
					final Iterable<CharSequence> _map_12 = IterableExtensions.<Property, CharSequence> map(allDataProperties, _function_13);
					final String _join_12 = IterableExtensions.join(_map_12, "\n");
					_builder.append(_join_12, "		");
					_builder.newLineIfNotEmpty();
					_builder.append("\t");
					_builder.append("}");
					_builder.newLine();
					_builder.newLine();
					_builder.append("\t");
					_builder.append("/**");
					_builder.newLine();
					_builder.append("\t ");
					_builder.append("* {@inheritDoc}");
					_builder.newLine();
					_builder.append("\t ");
					_builder.append("*/");
					_builder.newLine();
					_builder.append("\t");
					_builder.append("@Override");
					_builder.newLine();
					_builder.append("\t");
					_builder.append("public Class<?>[] getValueTypes() {");
					_builder.newLine();
					_builder.append("\t\t");
					_builder.append("return TYPES; // NOPMD");
					_builder.newLine();
					_builder.append("\t");
					_builder.append("}");
					_builder.newLine();
					_builder.newLine();
					_builder.append("\t");
					_builder.append("/**");
					_builder.newLine();
					_builder.append("\t ");
					_builder.append("* {@inheritDoc}");
					_builder.newLine();
					_builder.append("\t ");
					_builder.append("*/");
					_builder.newLine();
					_builder.append("\t");
					_builder.append("@Override");
					_builder.newLine();
					_builder.append("\t");
					_builder.append("public int getSize() {");
					_builder.newLine();
					_builder.append("\t\t");
					_builder.append("return SIZE;");
					_builder.newLine();
					_builder.append("\t");
					_builder.append("}");
					_builder.newLine();
				}
			}
			_builder.append("\t");
			_builder.append("/**");
			_builder.newLine();
			_builder.append("\t ");
			_builder.append("* {@inheritDoc}");
			_builder.newLine();
			_builder.append("\t ");
			_builder.append("* ");
			_builder.newLine();
			_builder.append("\t ");
			_builder.append("* @deprecated This record uses the {@link kieker.common.record.IMonitoringRecord.Factory} mechanism. Hence, this method is not implemented.");
			_builder.newLine();
			_builder.append("\t ");
			_builder.append("*/");
			_builder.newLine();
			_builder.append("\t");
			_builder.append("@Override");
			_builder.newLine();
			_builder.append("\t");
			_builder.append("@Deprecated");
			_builder.newLine();
			_builder.append("\t");
			_builder.append("public void initFromArray(final Object[] values) {");
			_builder.newLine();
			_builder.append("\t\t");
			_builder.append("throw new UnsupportedOperationException();");
			_builder.newLine();
			_builder.append("\t");
			_builder.append("}");
			_builder.newLine();
			_builder.newLine();
			_builder.append("\t");
			_builder.append("/**");
			_builder.newLine();
			_builder.append("\t ");
			_builder.append("* {@inheritDoc}");
			_builder.newLine();
			_builder.append("\t ");
			_builder.append("* ");
			_builder.newLine();
			_builder.append("\t ");
			_builder.append("* @deprecated This record uses the {@link kieker.common.record.IMonitoringRecord.BinaryFactory} mechanism. Hence, this method is not implemented.");
			_builder.newLine();
			_builder.append("\t ");
			_builder.append("*/");
			_builder.newLine();
			_builder.append("\t");
			_builder.append("@Override");
			_builder.newLine();
			_builder.append("\t");
			_builder.append("@Deprecated");
			_builder.newLine();
			_builder.append("\t");
			_builder.append("public void initFromBytes(final ByteBuffer buffer, final IRegistry<String> stringRegistry) throws BufferUnderflowException {");
			_builder.newLine();
			_builder.append("\t\t");
			_builder.append("throw new UnsupportedOperationException();");
			_builder.newLine();
			_builder.append("\t");
			_builder.append("}");
			_builder.newLine();
			_builder.newLine();
			_builder.append("\t");
			final Iterable<Property> _collectAllGetterDeclarationProperties = this.collectAllGetterDeclarationProperties(type);
			final Function1<Property, CharSequence> _function_14 = new Function1<Property, CharSequence>() {
				public CharSequence apply(final Property property) {
					final CharSequence _createPropertyGetter = RecordTypeGenerator.this.createPropertyGetter(property);
					return _createPropertyGetter;
				}
			};
			final Iterable<CharSequence> _map_13 = IterableExtensions.<Property, CharSequence> map(_collectAllGetterDeclarationProperties, _function_14);
			final String _join_13 = IterableExtensions.join(_map_13);
			_builder.append(_join_13, "	");
			_builder.newLineIfNotEmpty();
			_builder.append("}");
			_builder.newLine();
			_xblockexpression = (_builder);
		}
		return _xblockexpression;
	}

	/**
	 * Determine the size of one type.
	 * 
	 * @param property
	 *            property which serialization size is determined.
	 * 
	 * @returns
	 *          the serialization size of the property
	 */
	private String createSizeConstant(final Property property, final RecordType type) {
		try {
			String _switchResult = null;
			final Classifier _findType = PropertyEvaluation.findType(property);
			final EClassifier _class_ = _findType.getClass_();
			final String _name = _class_.getName();
			final String _switchValue = _name;
			boolean _matched = false;
			if (!_matched) {
				if (Objects.equal(_switchValue, "string")) {
					_matched = true;
					_switchResult = "TYPE_SIZE_STRING";
				}
			}
			if (!_matched) {
				if (Objects.equal(_switchValue, "byte")) {
					_matched = true;
					_switchResult = "TYPE_SIZE_BYTE";
				}
			}
			if (!_matched) {
				if (Objects.equal(_switchValue, "short")) {
					_matched = true;
					_switchResult = "TYPE_SIZE_SHORT";
				}
			}
			if (!_matched) {
				if (Objects.equal(_switchValue, "int")) {
					_matched = true;
					_switchResult = "TYPE_SIZE_INT";
				}
			}
			if (!_matched) {
				if (Objects.equal(_switchValue, "long")) {
					_matched = true;
					_switchResult = "TYPE_SIZE_LONG";
				}
			}
			if (!_matched) {
				if (Objects.equal(_switchValue, "float")) {
					_matched = true;
					_switchResult = "TYPE_SIZE_FLOAT";
				}
			}
			if (!_matched) {
				if (Objects.equal(_switchValue, "double")) {
					_matched = true;
					_switchResult = "TYPE_SIZE_DOUBLE";
				}
			}
			if (!_matched) {
				if (Objects.equal(_switchValue, "char")) {
					_matched = true;
					_switchResult = "TYPE_SIZE_CHARACTER";
				}
			}
			if (!_matched) {
				if (Objects.equal(_switchValue, "boolean")) {
					_matched = true;
					_switchResult = "TYPE_SIZE_BOOLEAN";
				}
			}
			if (!_matched) {
				final Classifier _findType_1 = PropertyEvaluation.findType(property);
				final EClassifier _class__1 = _findType_1.getClass_();
				final String _name_1 = _class__1.getName();
				final String _plus = (_name_1 + "is not a valid type name");
				final InternalErrorException _internalErrorException = new InternalErrorException(_plus);
				throw _internalErrorException;
			}
			final StringConcatenation _builder = new StringConcatenation();
			_builder.append(" ");
			_builder.append("// ");
			final CharSequence _computeFullQualifiedPropertyName = this.computeFullQualifiedPropertyName(property, type);
			_builder.append(_computeFullQualifiedPropertyName, " ");
			final String _plus_1 = (_switchResult + _builder);
			return _plus_1;
		} catch (final Throwable _e) {
			throw Exceptions.sneakyThrow(_e);
		}
	}

	/**
	 * Determine the name of the parent class.
	 */
	private CharSequence createParent(final RecordType type) {
		String _xifexpression = null;
		final RecordType _parent = type.getParent();
		final boolean _notEquals = (!Objects.equal(_parent, null));
		if (_notEquals) {
			final RecordType _parent_1 = type.getParent();
			final String _name = _parent_1.getName();
			_xifexpression = _name;
		} else {
			_xifexpression = "AbstractMonitoringRecord";
		}
		return _xifexpression;
	}

	/**
	 * Create the sequence of implements of the class and render the implements char sequence.
	 */
	private CharSequence createImplements(final RecordType type) {
		final ArrayList<CharSequence> _arrayList = new ArrayList<CharSequence>();
		final List<CharSequence> interfaces = _arrayList;
		final RecordType _parent = type.getParent();
		final boolean _equals = Objects.equal(_parent, null);
		if (_equals) {
			interfaces.add("IMonitoringRecord.Factory");
			interfaces.add("IMonitoringRecord.BinaryFactory");
		}
		boolean _and = false;
		final EList<PartialRecordType> _parents = type.getParents();
		final boolean _notEquals = (!Objects.equal(_parents, null));
		if (!_notEquals) {
			_and = false;
		} else {
			final EList<PartialRecordType> _parents_1 = type.getParents();
			final int _size = _parents_1.size();
			final boolean _greaterThan = (_size > 0);
			_and = (_notEquals && _greaterThan);
		}
		if (_and) {
			final EList<PartialRecordType> _parents_2 = type.getParents();
			final Function1<PartialRecordType, String> _function = new Function1<PartialRecordType, String>() {
				public String apply(final PartialRecordType iface) {
					final String _name = iface.getName();
					return _name;
				}
			};
			final List<String> _map = ListExtensions.<PartialRecordType, String> map(_parents_2, _function);
			interfaces.addAll(_map);
		}
		final int _size_1 = interfaces.size();
		final boolean _greaterThan_1 = (_size_1 > 0);
		if (_greaterThan_1) {
			final StringConcatenation _builder = new StringConcatenation();
			_builder.append(" ");
			_builder.append("implements ");
			final String _join = IterableExtensions.join(interfaces, ", ");
			_builder.append(_join, " ");
			return _builder;
		} else {
			return " ";
		}
	}

	/**
	 * Create a list of imports for the given type.
	 */
	private CharSequence createInterfaceImport(final PartialRecordType type) {
		final StringConcatenation _builder = new StringConcatenation();
		_builder.append("import ");
		final EObject _eContainer = type.eContainer();
		final String _name = ((Model) _eContainer).getName();
		_builder.append(_name, "");
		_builder.append(".");
		final String _name_1 = type.getName();
		_builder.append(_name_1, "");
		_builder.append(";");
		_builder.newLineIfNotEmpty();
		return _builder;
	}

	/**
	 * Create import for the parent structure.
	 */
	private CharSequence createParentImport(final RecordType type) {
		final StringConcatenation _builder = new StringConcatenation();
		_builder.append("import ");
		final RecordType _parent = type.getParent();
		final EObject _eContainer = _parent.eContainer();
		final String _name = ((Model) _eContainer).getName();
		_builder.append(_name, "");
		_builder.append(".");
		final RecordType _parent_1 = type.getParent();
		final String _name_1 = _parent_1.getName();
		_builder.append(_name_1, "");
		_builder.append(";");
		_builder.newLineIfNotEmpty();
		return _builder;
	}

	/**
	 * Determine the correct deserialization code for a property by type.
	 * 
	 * @param property
	 *            the property to deserialize
	 * 
	 * @returns
	 *          code to deserialize the given property
	 */
	private CharSequence createPropertyBinaryDeserialization(final Property property) {
		CharSequence _xifexpression = null;
		final Classifier _findType = PropertyEvaluation.findType(property);
		final EList<ArraySize> _sizes = _findType.getSizes();
		final int _size = _sizes.size();
		final boolean _greaterThan = (_size > 0);
		if (_greaterThan) {
			CharSequence _xblockexpression = null;
			{
				final Classifier _findType_1 = PropertyEvaluation.findType(property);
				final EList<ArraySize> sizes = _findType_1.getSizes();
				final StringConcatenation _builder = new StringConcatenation();
				_builder.append("// load array sizes");
				_builder.newLine();
				{
					for (final ArraySize size : sizes) {
						{
							final int _size_1 = size.getSize();
							final boolean _equals = (_size_1 == 0);
							if (_equals) {
								_builder.append("int _");
								final String _name = property.getName();
								_builder.append(_name, "");
								_builder.append("_size");
								final int _indexOf = sizes.indexOf(size);
								_builder.append(_indexOf, "");
								_builder.append(" = buffer.getInt();");
								_builder.newLineIfNotEmpty();
							}
						}
					}
				}
				final String _name_1 = property.getName();
				final String _protectKeywords = this.protectKeywords(_name_1);
				_builder.append(_protectKeywords, "");
				_builder.append(" = new ");
				final Classifier _findType_2 = PropertyEvaluation.findType(property);
				final String _name_2 = property.getName();
				final CharSequence _createTypeInstantiationName = this.createTypeInstantiationName(_findType_2, _name_2);
				_builder.append(_createTypeInstantiationName, "");
				_builder.append(";");
				_builder.newLineIfNotEmpty();
				final CharSequence _createForLoopForDeserialization = this.createForLoopForDeserialization(sizes, 0, property);
				_builder.append(_createForLoopForDeserialization, "");
				_builder.newLineIfNotEmpty();
				_xblockexpression = (_builder);
			}
			_xifexpression = _xblockexpression;
		} else {
			final StringConcatenation _builder = new StringConcatenation();
			_builder.append("this.");
			final String _name = property.getName();
			final String _protectKeywords = this.protectKeywords(_name);
			_builder.append(_protectKeywords, "");
			_builder.append(" = ");
			final Classifier _findType_1 = PropertyEvaluation.findType(property);
			final EClassifier _class_ = _findType_1.getClass_();
			final CharSequence _createPropertyPrimitiveTypeDeserialization = this.createPropertyPrimitiveTypeDeserialization(_class_);
			_builder.append(_createPropertyPrimitiveTypeDeserialization, "");
			_builder.append(";");
			_xifexpression = _builder;
		}
		return _xifexpression;
	}

	/**
	 * Determine the name and additional array sizes for an array deserialization.
	 * For example property[2][_property_size1][6] or just property for simple fields
	 */
	private CharSequence createTypeInstantiationName(final Classifier classifier, final String name) {
		String _xifexpression = null;
		final EList<ArraySize> _sizes = classifier.getSizes();
		final int _size = _sizes.size();
		final boolean _greaterThan = (_size > 0);
		if (_greaterThan) {
			final EClassifier _class_ = classifier.getClass_();
			final String _createPrimitiveTypeName = RlType2JavaTypeExtensions.createPrimitiveTypeName(_class_);
			final EList<ArraySize> _sizes_1 = classifier.getSizes();
			final Function1<ArraySize, CharSequence> _function = new Function1<ArraySize, CharSequence>() {
				public CharSequence apply(final ArraySize size) {
					final EList<ArraySize> _sizes = classifier.getSizes();
					final int _indexOf = _sizes.indexOf(size);
					final CharSequence _createArraySize = RecordTypeGenerator.this.createArraySize(size, name, _indexOf);
					return _createArraySize;
				}
			};
			final List<CharSequence> _map = ListExtensions.<ArraySize, CharSequence> map(_sizes_1, _function);
			final String _join = IterableExtensions.join(_map);
			final String _plus = (_createPrimitiveTypeName + _join);
			_xifexpression = _plus;
		} else {
			final EClassifier _class__1 = classifier.getClass_();
			final String _createPrimitiveTypeName_1 = RlType2JavaTypeExtensions.createPrimitiveTypeName(_class__1);
			_xifexpression = _createPrimitiveTypeName_1;
		}
		return _xifexpression;
	}

	/**
	 * Creates code for fixed and runtime array sizes according to the record model.
	 */
	private CharSequence createArraySize(final ArraySize size, final String name, final int index) {
		CharSequence _xifexpression = null;
		final int _size = size.getSize();
		final boolean _greaterThan = (_size > 0);
		if (_greaterThan) {
			final StringConcatenation _builder = new StringConcatenation();
			_builder.append("[");
			final int _size_1 = size.getSize();
			_builder.append(_size_1, "");
			_builder.append("]");
			_xifexpression = _builder;
		} else {
			final StringConcatenation _builder_1 = new StringConcatenation();
			_builder_1.append("[_");
			_builder_1.append(name, "");
			_builder_1.append("_size");
			_builder_1.append(index, "");
			_builder_1.append("]");
			_xifexpression = _builder_1;
		}
		return _xifexpression;
	}

	/**
	 * Create for loops for the deserialization of array data.
	 */
	private CharSequence createForLoopForDeserialization(final EList<ArraySize> sizes, final int depth, final Property property) {
		final StringConcatenation _builder = new StringConcatenation();
		_builder.append("for (int i");
		_builder.append(depth, "");
		_builder.append("=0;i");
		_builder.append(depth, "");
		_builder.append("<");
		Object _xifexpression = null;
		final ArraySize _get = sizes.get(depth);
		final int _size = _get.getSize();
		final boolean _greaterThan = (_size > 0);
		if (_greaterThan) {
			final ArraySize _get_1 = sizes.get(depth);
			final int _size_1 = _get_1.getSize();
			_xifexpression = Integer.valueOf(_size_1);
		} else {
			final String _name = property.getName();
			final String _plus = ("_" + _name);
			final String _plus_1 = (_plus + "_size");
			final String _plus_2 = (_plus_1 + Integer.valueOf(depth));
			_xifexpression = _plus_2;
		}
		_builder.append(_xifexpression, "");
		_builder.append(";i");
		_builder.append(depth, "");
		_builder.append("++)");
		_builder.newLineIfNotEmpty();
		_builder.append("\t");
		CharSequence _xifexpression_1 = null;
		final int _size_2 = sizes.size();
		final int _minus = (_size_2 - 1);
		final boolean _greaterThan_1 = (_minus > depth);
		if (_greaterThan_1) {
			final int _plus_3 = (depth + 1);
			final CharSequence _createForLoopForDeserialization = this.createForLoopForDeserialization(sizes, _plus_3, property);
			_xifexpression_1 = _createForLoopForDeserialization;
		} else {
			final String _createValueAssignmentForDeserialization = this.createValueAssignmentForDeserialization(sizes, property);
			_xifexpression_1 = _createValueAssignmentForDeserialization;
		}
		_builder.append(_xifexpression_1, "	");
		_builder.newLineIfNotEmpty();
		return _builder;
	}

	/**
	 * Assignment for a primitive value
	 */
	private String createValueAssignmentForDeserialization(final EList<ArraySize> sizes, final Property property) {
		final StringConcatenation _builder = new StringConcatenation();
		_builder.append("this.");
		final String _name = property.getName();
		_builder.append(_name, "");
		final CharSequence _determineArrayAccessCode = this.determineArrayAccessCode(sizes);
		_builder.append(_determineArrayAccessCode, "");
		_builder.append(" = ");
		final Classifier _findType = PropertyEvaluation.findType(property);
		final EClassifier _class_ = _findType.getClass_();
		final CharSequence _createPropertyPrimitiveTypeDeserialization = this.createPropertyPrimitiveTypeDeserialization(_class_);
		_builder.append(_createPropertyPrimitiveTypeDeserialization, "");
		_builder.append(";");
		return _builder.toString();
	}

	/**
	 * Used in serialization and deserialization to compose a sequence of [iX] for every
	 * dimension of an array containing the counter variable. If the variable is primitive
	 * the function returns an empty string.
	 */
	private CharSequence determineArrayAccessCode(final EList<ArraySize> sizes) {
		String result = "";
		final int _size = sizes.size();
		final ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
		for (final Integer i : _doubleDotLessThan) {
			final StringConcatenation _builder = new StringConcatenation();
			_builder.append(result, "");
			_builder.append("[i");
			_builder.append(i, "");
			_builder.append("]");
			result = _builder.toString();
		}
		return result;
	}

	/**
	 * Create code to get values from the input buffer.
	 */
	private CharSequence createPropertyPrimitiveTypeDeserialization(final EClassifier classifier) {
		CharSequence _switchResult = null;
		final String _name = classifier.getName();
		final String _switchValue = _name;
		boolean _matched = false;
		if (!_matched) {
			if (Objects.equal(_switchValue, "string")) {
				_matched = true;
				final StringConcatenation _builder = new StringConcatenation();
				_builder.append("stringRegistry.get(buffer.getInt())");
				_switchResult = _builder;
			}
		}
		if (!_matched) {
			if (Objects.equal(_switchValue, "byte")) {
				_matched = true;
				final StringConcatenation _builder_1 = new StringConcatenation();
				_builder_1.append("buffer.get()");
				_switchResult = _builder_1;
			}
		}
		if (!_matched) {
			if (Objects.equal(_switchValue, "short")) {
				_matched = true;
				final StringConcatenation _builder_2 = new StringConcatenation();
				_builder_2.append("buffer.getShort()");
				_switchResult = _builder_2;
			}
		}
		if (!_matched) {
			if (Objects.equal(_switchValue, "int")) {
				_matched = true;
				final StringConcatenation _builder_3 = new StringConcatenation();
				_builder_3.append("buffer.getInt()");
				_switchResult = _builder_3;
			}
		}
		if (!_matched) {
			if (Objects.equal(_switchValue, "long")) {
				_matched = true;
				final StringConcatenation _builder_4 = new StringConcatenation();
				_builder_4.append("buffer.getLong()");
				_switchResult = _builder_4;
			}
		}
		if (!_matched) {
			if (Objects.equal(_switchValue, "float")) {
				_matched = true;
				final StringConcatenation _builder_5 = new StringConcatenation();
				_builder_5.append("buffer.getFloat()");
				_switchResult = _builder_5;
			}
		}
		if (!_matched) {
			if (Objects.equal(_switchValue, "double")) {
				_matched = true;
				final StringConcatenation _builder_6 = new StringConcatenation();
				_builder_6.append("buffer.getDouble()");
				_switchResult = _builder_6;
			}
		}
		if (!_matched) {
			if (Objects.equal(_switchValue, "char")) {
				_matched = true;
				final StringConcatenation _builder_7 = new StringConcatenation();
				_builder_7.append("buffer.getChar()");
				_switchResult = _builder_7;
			}
		}
		if (!_matched) {
			if (Objects.equal(_switchValue, "boolean")) {
				_matched = true;
				final StringConcatenation _builder_8 = new StringConcatenation();
				_builder_8.append("buffer.get()==1?true:false");
				_switchResult = _builder_8;
			}
		}
		return _switchResult;
	}

	/**
	 * Determine the correct serialization for a property by type.
	 * 
	 * @param property
	 *            the property to serialize
	 * 
	 * @returns
	 *          code to serialize the given property
	 */
	private CharSequence createPropertyBinarySerialization(final Property property) {
		CharSequence _xblockexpression = null;
		{
			final Classifier _findType = PropertyEvaluation.findType(property);
			final EList<ArraySize> sizes = _findType.getSizes();
			CharSequence _xifexpression = null;
			final int _size = sizes.size();
			final boolean _greaterThan = (_size > 0);
			if (_greaterThan) {
				final StringConcatenation _builder = new StringConcatenation();
				_builder.append("// store array sizes");
				_builder.newLine();
				{
					for (final ArraySize size : sizes) {
						{
							final int _size_1 = size.getSize();
							final boolean _equals = (_size_1 == 0);
							if (_equals) {
								_builder.append("int _");
								final String _name = property.getName();
								_builder.append(_name, "");
								_builder.append("_size");
								final int _indexOf = sizes.indexOf(size);
								_builder.append(_indexOf, "");
								_builder.append(" = this.");
								final CharSequence _createGetterName = this.createGetterName(property);
								_builder.append(_createGetterName, "");
								_builder.append("()");
								final int _indexOf_1 = sizes.indexOf(size);
								final String _createCodeToDetermineArraySize = this.createCodeToDetermineArraySize(_indexOf_1);
								_builder.append(_createCodeToDetermineArraySize, "");
								_builder.append(".length;");
								_builder.newLineIfNotEmpty();
								_builder.append("buffer.putInt(_");
								final String _name_1 = property.getName();
								_builder.append(_name_1, "");
								_builder.append("_size");
								final int _indexOf_2 = sizes.indexOf(size);
								_builder.append(_indexOf_2, "");
								_builder.append(");");
								_builder.newLineIfNotEmpty();
							}
						}
					}
				}
				final CharSequence _createForLoopForSerialization = this.createForLoopForSerialization(sizes, 0, property);
				_builder.append(_createForLoopForSerialization, "");
				_builder.newLineIfNotEmpty();
				_xifexpression = _builder;
			} else {
				final CharSequence _createValueStoreForSerialization = this.createValueStoreForSerialization(sizes, property);
				_xifexpression = _createValueStoreForSerialization;
			}
			_xblockexpression = (_xifexpression);
		}
		return _xblockexpression;
	}

	/**
	 * This produces a sequence of [0] assuming that arrays follow a matrix layout.
	 */
	private String createCodeToDetermineArraySize(final int count) {
		String result = "";
		int i = 0;

		boolean _while = (i < count);
		while (_while) {
			{
				final String _plus = (result + "[0]");
				result = _plus;
				final int _plus_1 = (i + 1);
				i = _plus_1;
			}
			_while = (i < count);
		}
		return result;
	}

	private CharSequence createForLoopForSerialization(final EList<ArraySize> sizes, final int depth, final Property property) {
		final StringConcatenation _builder = new StringConcatenation();
		_builder.append("for (int i");
		_builder.append(depth, "");
		_builder.append("=0;i");
		_builder.append(depth, "");
		_builder.append("<");
		Object _xifexpression = null;
		final ArraySize _get = sizes.get(depth);
		final int _size = _get.getSize();
		final boolean _greaterThan = (_size > 0);
		if (_greaterThan) {
			final ArraySize _get_1 = sizes.get(depth);
			final int _size_1 = _get_1.getSize();
			_xifexpression = Integer.valueOf(_size_1);
		} else {
			final String _name = property.getName();
			final String _plus = ("_" + _name);
			final String _plus_1 = (_plus + "_size");
			final String _plus_2 = (_plus_1 + Integer.valueOf(depth));
			_xifexpression = _plus_2;
		}
		_builder.append(_xifexpression, "");
		_builder.append(";i");
		_builder.append(depth, "");
		_builder.append("++)");
		_builder.newLineIfNotEmpty();
		_builder.append("\t");
		CharSequence _xifexpression_1 = null;
		final int _size_2 = sizes.size();
		final int _minus = (_size_2 - 1);
		final boolean _greaterThan_1 = (_minus > depth);
		if (_greaterThan_1) {
			final int _plus_3 = (depth + 1);
			final CharSequence _createForLoopForSerialization = this.createForLoopForSerialization(sizes, _plus_3, property);
			_xifexpression_1 = _createForLoopForSerialization;
		} else {
			final CharSequence _createValueStoreForSerialization = this.createValueStoreForSerialization(sizes, property);
			_xifexpression_1 = _createValueStoreForSerialization;
		}
		_builder.append(_xifexpression_1, "	");
		_builder.newLineIfNotEmpty();
		return _builder;
	}

	private CharSequence createValueStoreForSerialization(final EList<ArraySize> sizes, final Property property) {
		CharSequence _switchResult = null;
		final Classifier _findType = PropertyEvaluation.findType(property);
		final EClassifier _class_ = _findType.getClass_();
		final String _name = _class_.getName();
		final String _switchValue = _name;
		boolean _matched = false;
		if (!_matched) {
			if (Objects.equal(_switchValue, "string")) {
				_matched = true;
				final StringConcatenation _builder = new StringConcatenation();
				_builder.append("buffer.putInt(stringRegistry.get(this.get");
				final String _name_1 = property.getName();
				final String _firstUpper = StringExtensions.toFirstUpper(_name_1);
				_builder.append(_firstUpper, "");
				_builder.append("()");
				final CharSequence _determineArrayAccessCode = this.determineArrayAccessCode(sizes);
				_builder.append(_determineArrayAccessCode, "");
				_builder.append("));");
				_switchResult = _builder;
			}
		}
		if (!_matched) {
			if (Objects.equal(_switchValue, "byte")) {
				_matched = true;
				final StringConcatenation _builder_1 = new StringConcatenation();
				_builder_1.append("buffer.put((byte)this.get");
				final String _name_2 = property.getName();
				final String _firstUpper_1 = StringExtensions.toFirstUpper(_name_2);
				_builder_1.append(_firstUpper_1, "");
				_builder_1.append("()");
				final CharSequence _determineArrayAccessCode_1 = this.determineArrayAccessCode(sizes);
				_builder_1.append(_determineArrayAccessCode_1, "");
				_builder_1.append(");");
				_switchResult = _builder_1;
			}
		}
		if (!_matched) {
			if (Objects.equal(_switchValue, "short")) {
				_matched = true;
				final StringConcatenation _builder_2 = new StringConcatenation();
				_builder_2.append("buffer.putShort(this.get");
				final String _name_3 = property.getName();
				final String _firstUpper_2 = StringExtensions.toFirstUpper(_name_3);
				_builder_2.append(_firstUpper_2, "");
				_builder_2.append("()");
				final CharSequence _determineArrayAccessCode_2 = this.determineArrayAccessCode(sizes);
				_builder_2.append(_determineArrayAccessCode_2, "");
				_builder_2.append(");");
				_switchResult = _builder_2;
			}
		}
		if (!_matched) {
			if (Objects.equal(_switchValue, "int")) {
				_matched = true;
				final StringConcatenation _builder_3 = new StringConcatenation();
				_builder_3.append("buffer.putInt(this.get");
				final String _name_4 = property.getName();
				final String _firstUpper_3 = StringExtensions.toFirstUpper(_name_4);
				_builder_3.append(_firstUpper_3, "");
				_builder_3.append("()");
				final CharSequence _determineArrayAccessCode_3 = this.determineArrayAccessCode(sizes);
				_builder_3.append(_determineArrayAccessCode_3, "");
				_builder_3.append(");");
				_switchResult = _builder_3;
			}
		}
		if (!_matched) {
			if (Objects.equal(_switchValue, "long")) {
				_matched = true;
				final StringConcatenation _builder_4 = new StringConcatenation();
				_builder_4.append("buffer.putLong(this.get");
				final String _name_5 = property.getName();
				final String _firstUpper_4 = StringExtensions.toFirstUpper(_name_5);
				_builder_4.append(_firstUpper_4, "");
				_builder_4.append("()");
				final CharSequence _determineArrayAccessCode_4 = this.determineArrayAccessCode(sizes);
				_builder_4.append(_determineArrayAccessCode_4, "");
				_builder_4.append(");");
				_switchResult = _builder_4;
			}
		}
		if (!_matched) {
			if (Objects.equal(_switchValue, "float")) {
				_matched = true;
				final StringConcatenation _builder_5 = new StringConcatenation();
				_builder_5.append("buffer.putFloat(this.get");
				final String _name_6 = property.getName();
				final String _firstUpper_5 = StringExtensions.toFirstUpper(_name_6);
				_builder_5.append(_firstUpper_5, "");
				_builder_5.append("()");
				final CharSequence _determineArrayAccessCode_5 = this.determineArrayAccessCode(sizes);
				_builder_5.append(_determineArrayAccessCode_5, "");
				_builder_5.append(");");
				_switchResult = _builder_5;
			}
		}
		if (!_matched) {
			if (Objects.equal(_switchValue, "double")) {
				_matched = true;
				final StringConcatenation _builder_6 = new StringConcatenation();
				_builder_6.append("buffer.putDouble(this.get");
				final String _name_7 = property.getName();
				final String _firstUpper_6 = StringExtensions.toFirstUpper(_name_7);
				_builder_6.append(_firstUpper_6, "");
				_builder_6.append("()");
				final CharSequence _determineArrayAccessCode_6 = this.determineArrayAccessCode(sizes);
				_builder_6.append(_determineArrayAccessCode_6, "");
				_builder_6.append(");");
				_switchResult = _builder_6;
			}
		}
		if (!_matched) {
			if (Objects.equal(_switchValue, "char")) {
				_matched = true;
				final StringConcatenation _builder_7 = new StringConcatenation();
				_builder_7.append("buffer.putChar(this.get");
				final String _name_8 = property.getName();
				final String _firstUpper_7 = StringExtensions.toFirstUpper(_name_8);
				_builder_7.append(_firstUpper_7, "");
				_builder_7.append("()");
				final CharSequence _determineArrayAccessCode_7 = this.determineArrayAccessCode(sizes);
				_builder_7.append(_determineArrayAccessCode_7, "");
				_builder_7.append(");");
				_switchResult = _builder_7;
			}
		}
		if (!_matched) {
			if (Objects.equal(_switchValue, "boolean")) {
				_matched = true;
				final StringConcatenation _builder_8 = new StringConcatenation();
				_builder_8.append("buffer.put((byte)(this.is");
				final String _name_9 = property.getName();
				final String _firstUpper_8 = StringExtensions.toFirstUpper(_name_9);
				_builder_8.append(_firstUpper_8, "");
				_builder_8.append("()");
				final CharSequence _determineArrayAccessCode_8 = this.determineArrayAccessCode(sizes);
				_builder_8.append(_determineArrayAccessCode_8, "");
				_builder_8.append("?1:0));");
				_switchResult = _builder_8;
			}
		}
		return _switchResult;
	}

	/**
	 * Creates a getter for a given property.
	 * 
	 * @param property
	 *            a property of the record type
	 * 
	 * @returns the resulting getter as a CharSequence
	 */
	private CharSequence createPropertyGetter(final Property property) {
		final StringConcatenation _builder = new StringConcatenation();
		_builder.append("public final ");
		final Classifier _findType = PropertyEvaluation.findType(property);
		final String _createTypeName = this.createTypeName(_findType);
		_builder.append(_createTypeName, "");
		_builder.append(" ");
		final CharSequence _createGetterName = this.createGetterName(property);
		_builder.append(_createGetterName, "");
		_builder.append("() {");
		_builder.newLineIfNotEmpty();
		_builder.append("\t");
		_builder.append("return this.");
		final CharSequence _resolveName = this.resolveName(property);
		_builder.append(_resolveName, "	");
		_builder.append(";");
		_builder.newLineIfNotEmpty();
		_builder.append("}");
		_builder.newLine();
		_builder.newLine();
		return _builder;
	}

	/**
	 * Returns the name of a property for the object array composition
	 * 
	 * @param property
	 *            a property of the record type
	 * 
	 * @returns the resulting array entry
	 */
	private CharSequence createPropertyArrayEntry(final Property property) {
		final StringConcatenation _builder = new StringConcatenation();
		_builder.append("this.");
		final CharSequence _createGetterName = this.createGetterName(property);
		_builder.append(_createGetterName, "");
		_builder.append("()");
		return _builder;
	}

	/**
	 * Returns the correct name for a getter following Java conventions.
	 * 
	 * @param property
	 *            a property of a record type
	 * 
	 * @returns the name of the getter of the property
	 */
	private CharSequence createGetterName(final Property property) {
		CharSequence _xifexpression = null;
		final Classifier _findType = PropertyEvaluation.findType(property);
		final EClassifier _class_ = _findType.getClass_();
		final String _name = _class_.getName();
		final boolean _equals = _name.equals("boolean");
		if (_equals) {
			final StringConcatenation _builder = new StringConcatenation();
			_builder.append("is");
			final String _name_1 = property.getName();
			final String _firstUpper = StringExtensions.toFirstUpper(_name_1);
			_builder.append(_firstUpper, "");
			_xifexpression = _builder;
		} else {
			final StringConcatenation _builder_1 = new StringConcatenation();
			_builder_1.append("get");
			final String _name_2 = property.getName();
			final String _firstUpper_1 = StringExtensions.toFirstUpper(_name_2);
			_builder_1.append(_firstUpper_1, "");
			_xifexpression = _builder_1;
		}
		return _xifexpression;
	}

	/**
	 * Determine the implementation property name of a property by following the alias graph.
	 * 
	 * @param property
	 *            The property to resolve
	 * 
	 * @returns the implementation name of the property
	 */
	private CharSequence resolveName(final Property property) {
		final Property _referTo = property.getReferTo();
		final boolean _notEquals = (!Objects.equal(_referTo, null));
		if (_notEquals) {
			final StringConcatenation _builder = new StringConcatenation();
			final Property _referTo_1 = property.getReferTo();
			final CharSequence _createGetterName = this.createGetterName(_referTo_1);
			_builder.append(_createGetterName, "");
			_builder.append("()");
			return _builder;
		} else {
			final String _name = property.getName();
			return this.protectKeywords(_name);
		}
	}

	/**
	 * Create all assignments for the generic constructor based on property name and an array.
	 * 
	 * @param property
	 *            a property of the record type
	 * @param offset
	 *            the array offset
	 * 
	 * @returns all assignments for the given property list
	 */
	private String createPropertyGenericAssignments(final Iterable<Property> list, final int offset) {
		final BasicEList<CharSequence> _basicEList = new BasicEList<CharSequence>();
		final EList<CharSequence> r = _basicEList;
		final Procedure2<Property, Integer> _function = new Procedure2<Property, Integer>() {
			public void apply(final Property property, final Integer index) {
				final int _plus = ((index).intValue() + offset);
				final String _createPropertyGenericAssignment = RecordTypeGenerator.this.createPropertyGenericAssignment(property, _plus);
				r.add(_createPropertyGenericAssignment);
			}
		};
		IterableExtensions.<Property> forEach(list, _function);
		return IterableExtensions.join(r);
	}

	/**
	 * Create an assignment with a property as assignment target and an array value as source.
	 * Used in the generic constructor.
	 * 
	 * @param property
	 *            a property of the record type
	 * @param index
	 *            the array index
	 * 
	 * @returns one assignment
	 */
	private String createPropertyGenericAssignment(final Property property, final int index) {
		final StringConcatenation _builder = new StringConcatenation();
		_builder.append("this.");
		final String _name = property.getName();
		final String _protectKeywords = this.protectKeywords(_name);
		_builder.append(_protectKeywords, "");
		_builder.append(" = (");
		final Classifier _findType = PropertyEvaluation.findType(property);
		final String _createObjectTypeName = this.createObjectTypeName(_findType);
		_builder.append(_createObjectTypeName, "");
		_builder.append(") values[");
		_builder.append(index, "");
		_builder.append("];");
		_builder.newLineIfNotEmpty();
		return _builder.toString();
	}

	/**
	 * Create an assignment with a property as assignment target and an array value as source.
	 * 
	 * @param property
	 *            a property of the record type
	 * @param index
	 *            the array index
	 * 
	 * @returns one assignment
	 */
	private CharSequence createPropertyAssignment(final Property property) {
		CharSequence _xifexpression = null;
		final Classifier _findType = PropertyEvaluation.findType(property);
		final EClassifier _class_ = _findType.getClass_();
		final String _name = _class_.getName();
		final boolean _equals = "string".equals(_name);
		if (_equals) {
			final StringConcatenation _builder = new StringConcatenation();
			_builder.append("this.");
			final String _name_1 = property.getName();
			final String _protectKeywords = this.protectKeywords(_name_1);
			_builder.append(_protectKeywords, "");
			_builder.append(" = ");
			final String _name_2 = property.getName();
			final String _protectKeywords_1 = this.protectKeywords(_name_2);
			_builder.append(_protectKeywords_1, "");
			_builder.append(" == null?");
			CharSequence _xifexpression_1 = null;
			final Literal _value = property.getValue();
			final boolean _notEquals = (!Objects.equal(_value, null));
			if (_notEquals) {
				final Literal _value_1 = property.getValue();
				final CharSequence _createValue = this.createValue(_value_1);
				_xifexpression_1 = _createValue;
			} else {
				_xifexpression_1 = "\"\"";
			}
			_builder.append(_xifexpression_1, "");
			_builder.append(":");
			final String _name_3 = property.getName();
			final String _protectKeywords_2 = this.protectKeywords(_name_3);
			_builder.append(_protectKeywords_2, "");
			_builder.append(";");
			_builder.newLineIfNotEmpty();
			_xifexpression = _builder;
		} else {
			final StringConcatenation _builder_1 = new StringConcatenation();
			_builder_1.append("this.");
			final String _name_4 = property.getName();
			final String _protectKeywords_3 = this.protectKeywords(_name_4);
			_builder_1.append(_protectKeywords_3, "");
			_builder_1.append(" = ");
			final String _name_5 = property.getName();
			final String _protectKeywords_4 = this.protectKeywords(_name_5);
			_builder_1.append(_protectKeywords_4, "");
			_builder_1.append(";");
			_builder_1.newLineIfNotEmpty();
			_xifexpression = _builder_1;
		}
		return _xifexpression;
	}

	/**
	 * Create one entry for the constructor parameter sequence.
	 * 
	 * @param property
	 *            a property of the record type
	 * 
	 * @returns one parameter for the given property
	 */
	private CharSequence createPropertyParameter(final Property property) {
		final StringConcatenation _builder = new StringConcatenation();
		_builder.append("final ");
		final Classifier _findType = PropertyEvaluation.findType(property);
		final String _createTypeName = this.createTypeName(_findType);
		_builder.append(_createTypeName, "");
		_builder.append(" ");
		final String _name = property.getName();
		final String _protectKeywords = this.protectKeywords(_name);
		_builder.append(_protectKeywords, "");
		return _builder;
	}

	/**
	 * Create an arbitrary comment for a property of a monitoring record.
	 * 
	 * @param property
	 *            a property of the record type
	 * 
	 * @returns one comment
	 */
	private CharSequence createPropertyComment(final Property property) {
		final StringConcatenation _builder = new StringConcatenation();
		_builder.append("* @param ");
		final String _name = property.getName();
		final String _protectKeywords = this.protectKeywords(_name);
		_builder.append(_protectKeywords, "");
		_builder.newLineIfNotEmpty();
		_builder.append("*            ");
		final String _name_1 = property.getName();
		final String _protectKeywords_1 = this.protectKeywords(_name_1);
		_builder.append(_protectKeywords_1, "");
		_builder.newLineIfNotEmpty();
		return _builder;
	}

	/**
	 * Create a property declaration for a monitoring record.
	 * 
	 * @param property
	 *            a property of the record type
	 * 
	 * @returns one property declaration
	 */
	private CharSequence createPropertyDeclaration(final Property property) {
		final StringConcatenation _builder = new StringConcatenation();
		_builder.append("private final ");
		final Classifier _findType = PropertyEvaluation.findType(property);
		final String _createTypeName = this.createTypeName(_findType);
		_builder.append(_createTypeName, "");
		_builder.append(" ");
		final String _name = property.getName();
		final String _protectKeywords = this.protectKeywords(_name);
		_builder.append(_protectKeywords, "");
		_builder.append(";");
		_builder.newLineIfNotEmpty();
		return _builder;
	}

	/**
	 * Check whether a given name is identical to a keyword of the target language and prepends an _.
	 */
	private String protectKeywords(final String name) {
		String _xblockexpression = null;
		{
			final List<String> keywords = Collections.<String> unmodifiableList(Lists.<String> newArrayList("interface", "class", "private", "protected", "public",
					"return", "final", "volatile", "if", "else", "for", "foreach"));
			String _xifexpression = null;
			final Function1<String, Boolean> _function = new Function1<String, Boolean>() {
				public Boolean apply(final String it) {
					final boolean _equals = it.equals(name);
					return Boolean.valueOf(_equals);
				}
			};
			final boolean _exists = IterableExtensions.<String> exists(keywords, _function);
			if (_exists) {
				final String _plus = ("_" + name);
				_xifexpression = _plus;
			} else {
				_xifexpression = name;
			}
			_xblockexpression = (_xifexpression);
		}
		return _xblockexpression;
	}

	/**
	 * Create one entry of the property type array.
	 * 
	 * @param property
	 *            a property of the record type
	 * 
	 * @returns one type entry
	 */
	private CharSequence createPropertyType(final Property property, final RecordType type) {
		final StringConcatenation _builder = new StringConcatenation();
		final Classifier _findType = PropertyEvaluation.findType(property);
		final String _createTypeName = this.createTypeName(_findType);
		_builder.append(_createTypeName, "");
		_builder.append(".class, // ");
		final CharSequence _computeFullQualifiedPropertyName = this.computeFullQualifiedPropertyName(property, type);
		_builder.append(_computeFullQualifiedPropertyName, "");
		_builder.newLineIfNotEmpty();
		return _builder;
	}

	/**
	 * Compute the full qualified name of a property.
	 * 
	 * @param property
	 *            the property itself
	 * @param type
	 *            the present RecordType
	 * 
	 * @returns
	 *          the FQ property name
	 */
	private CharSequence computeFullQualifiedPropertyName(final Property property, final RecordType type) {
		final EList<Property> _properties = type.getProperties();
		final boolean _contains = _properties.contains(property);
		if (_contains) {
			final String _name = type.getName();
			final String _plus = (_name + ".");
			final String _name_1 = property.getName();
			return (_plus + _name_1);
		} else {
			final RecordType _parent = type.getParent();
			final boolean _notEquals = (!Objects.equal(_parent, null));
			if (_notEquals) {
				final RecordType _parent_1 = type.getParent();
				final CharSequence result = this.computeFullQualifiedPropertyName(property, _parent_1);
				final boolean _notEquals_1 = (!Objects.equal(result, null));
				if (_notEquals_1) {
					return result;
				}
			}
			final EList<PartialRecordType> _parents = type.getParents();
			final boolean _notEquals_2 = (!Objects.equal(_parents, null));
			if (_notEquals_2) {
				final EList<PartialRecordType> _parents_1 = type.getParents();
				for (final Type t : _parents_1) {
					{
						final CharSequence result_1 = this.computeFullQualifiedPropertyName(property, t);
						final boolean _notEquals_3 = (!Objects.equal(result_1, null));
						if (_notEquals_3) {
							return result_1;
						}
					}
				}
			}
			return null;
		}
	}

	/**
	 * Compute the full qualified name of a property.
	 * 
	 * @param property
	 *            the property itself
	 * @param type
	 *            the present RecordType
	 * 
	 * @returns
	 *          the FQ property name
	 */
	private CharSequence computeFullQualifiedPropertyName(final Property property, final Type type) {
		final EList<Property> _properties = type.getProperties();
		final boolean _contains = _properties.contains(property);
		if (_contains) {
			final String _name = type.getName();
			final String _plus = (_name + ".");
			final String _name_1 = property.getName();
			return (_plus + _name_1);
		} else {
			final EList<PartialRecordType> _parents = type.getParents();
			final boolean _notEquals = (!Objects.equal(_parents, null));
			if (_notEquals) {
				final EList<PartialRecordType> _parents_1 = type.getParents();
				for (final Type t : _parents_1) {
					{
						final CharSequence result2 = this.computeFullQualifiedPropertyName(property, t);
						final boolean _notEquals_1 = (!Objects.equal(result2, null));
						if (_notEquals_1) {
							return result2;
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * create a constant name from a standard name camel case name.
	 */
	private String createConstantName(final String name) {
		final String _replaceAll = name.replaceAll("([a-z])([A-Z])", "$1_$2");
		return _replaceAll.toUpperCase();
	}

	/**
	 * Create a property constant based on the language constant expression.
	 * 
	 * @param constant
	 *            a default constant object
	 * 
	 * @returns a constant declaration
	 */
	private CharSequence createDefaultConstant(final Constant constant) {
		final StringConcatenation _builder = new StringConcatenation();
		_builder.append("public static final ");
		final Classifier _type = constant.getType();
		final String _createTypeName = this.createTypeName(_type);
		_builder.append(_createTypeName, "");
		_builder.append(" ");
		final String _name = constant.getName();
		final String _protectKeywords = this.protectKeywords(_name);
		_builder.append(_protectKeywords, "");
		_builder.append(" = ");
		final Literal _value = constant.getValue();
		final CharSequence _createValue = this.createValue(_value);
		_builder.append(_createValue, "");
		_builder.append(";");
		_builder.newLineIfNotEmpty();
		return _builder;
	}

	/**
	 * Create a property constant based on the language property expression.
	 * 
	 * @param property
	 *            a default constant object
	 * 
	 * @returns a constant declaration
	 */
	private CharSequence createDefaultConstant(final Property property) {
		final StringConcatenation _builder = new StringConcatenation();
		_builder.append("public static final ");
		final Classifier _type = property.getType();
		final String _createTypeName = this.createTypeName(_type);
		_builder.append(_createTypeName, "");
		_builder.append(" ");
		final String _name = property.getName();
		final String _createConstantName = this.createConstantName(_name);
		final String _protectKeywords = this.protectKeywords(_createConstantName);
		_builder.append(_protectKeywords, "");
		_builder.append(" = ");
		final Literal _value = property.getValue();
		final CharSequence _createValue = this.createValue(_value);
		_builder.append(_createValue, "");
		_builder.append(";");
		_builder.newLineIfNotEmpty();
		return _builder;
	}

	/**
	 * Determine the right Java string for a given system type.
	 * 
	 * @param classifier
	 *            a classifier representing a type
	 * 
	 * @returns a java type name
	 */
	public String createTypeName(final Classifier classifier) {
		String _xifexpression = null;
		final EList<ArraySize> _sizes = classifier.getSizes();
		final int _size = _sizes.size();
		final boolean _greaterThan = (_size > 0);
		if (_greaterThan) {
			final EClassifier _class_ = classifier.getClass_();
			final String _createPrimitiveTypeName = RlType2JavaTypeExtensions.createPrimitiveTypeName(_class_);
			final EList<ArraySize> _sizes_1 = classifier.getSizes();
			final Function1<ArraySize, String> _function = new Function1<ArraySize, String>() {
				public String apply(final ArraySize size) {
					final StringConcatenation _builder = new StringConcatenation();
					_builder.append("[]");
					return _builder.toString();
				}
			};
			final List<String> _map = ListExtensions.<ArraySize, String> map(_sizes_1, _function);
			final String _join = IterableExtensions.join(_map);
			final String _plus = (_createPrimitiveTypeName + _join);
			_xifexpression = _plus;
		} else {
			final EClassifier _class__1 = classifier.getClass_();
			final String _createPrimitiveTypeName_1 = RlType2JavaTypeExtensions.createPrimitiveTypeName(_class__1);
			_xifexpression = _createPrimitiveTypeName_1;
		}
		return _xifexpression;
	}

	/**
	 * Determine the right Java string for a given system type.
	 * 
	 * @param classifier
	 *            a classifier representing a type
	 * 
	 * @returns a java type name
	 */
	private String createObjectTypeName(final Classifier classifier) {
		String _xifexpression = null;
		final EList<ArraySize> _sizes = classifier.getSizes();
		final int _size = _sizes.size();
		final boolean _greaterThan = (_size > 0);
		if (_greaterThan) {
			final EClassifier _class_ = classifier.getClass_();
			final String _createObjectPrimitiveTypeName = this.createObjectPrimitiveTypeName(_class_);
			final EList<ArraySize> _sizes_1 = classifier.getSizes();
			final Function1<ArraySize, String> _function = new Function1<ArraySize, String>() {
				public String apply(final ArraySize size) {
					final StringConcatenation _builder = new StringConcatenation();
					_builder.append("[]");
					return _builder.toString();
				}
			};
			final List<String> _map = ListExtensions.<ArraySize, String> map(_sizes_1, _function);
			final String _join = IterableExtensions.join(_map);
			final String _plus = (_createObjectPrimitiveTypeName + _join);
			_xifexpression = _plus;
		} else {
			final EClassifier _class__1 = classifier.getClass_();
			final String _createObjectPrimitiveTypeName_1 = this.createObjectPrimitiveTypeName(_class__1);
			_xifexpression = _createObjectPrimitiveTypeName_1;
		}
		return _xifexpression;
	}

	/**
	 * Determine the right Java string for a given system type.
	 */
	private String createObjectPrimitiveTypeName(final EClassifier classifier) {
		String _switchResult = null;
		final String _name = classifier.getName();
		final String _switchValue = _name;
		boolean _matched = false;
		if (!_matched) {
			if (Objects.equal(_switchValue, "int")) {
				_matched = true;
				_switchResult = "Integer";
			}
		}
		if (!_matched) {
			if (Objects.equal(_switchValue, "long")) {
				_matched = true;
				_switchResult = "Long";
			}
		}
		if (!_matched) {
			if (Objects.equal(_switchValue, "short")) {
				_matched = true;
				_switchResult = "Short";
			}
		}
		if (!_matched) {
			if (Objects.equal(_switchValue, "double")) {
				_matched = true;
				_switchResult = "Double";
			}
		}
		if (!_matched) {
			if (Objects.equal(_switchValue, "float")) {
				_matched = true;
				_switchResult = "Float";
			}
		}
		if (!_matched) {
			if (Objects.equal(_switchValue, "char")) {
				_matched = true;
				_switchResult = "Character";
			}
		}
		if (!_matched) {
			if (Objects.equal(_switchValue, "byte")) {
				_matched = true;
				_switchResult = "Byte";
			}
		}
		if (!_matched) {
			if (Objects.equal(_switchValue, "string")) {
				_matched = true;
				_switchResult = "String";
			}
		}
		if (!_matched) {
			if (Objects.equal(_switchValue, "boolean")) {
				_matched = true;
				_switchResult = "Boolean";
			}
		}
		if (!_matched) {
			final String _name_1 = classifier.getName();
			_switchResult = _name_1;
		}
		return _switchResult;
	}

	/**
	 * Collect recursively a list of all properties which are defined in an template and
	 * not inherited from predecessors.
	 * 
	 * @param type
	 *            a recordType
	 * 
	 * @returns
	 *          a complete list of all properties which require getters
	 */
	private Iterable<Property> collectAllGetterDeclarationProperties(final RecordType type) {
		final Iterable<Property> result = PropertyEvaluation.collectAllProperties(type);
		final RecordType _parent = type.getParent();
		final boolean _notEquals = (!Objects.equal(_parent, null));
		if (_notEquals) {
			final RecordType _parent_1 = type.getParent();
			return this.removeAlreadyImplementedProperties(result, _parent_1);
		} else {
			return result;
		}
	}

	/**
	 * Collect all properties which must be declared for this type. In total that are:
	 * - properties declared by the type, which are not an alias.
	 * - properties declared by any imported interface, which are not implemented in a parent type
	 * and which are not an alias.
	 * 
	 * @param type
	 *            a recordType
	 * 
	 * @returns
	 *          a complete list of all properties which require declaration
	 */
	private Iterable<Property> collectAllDeclarationProperties(final RecordType type) {
		final ArrayList<Property> _arrayList = new ArrayList<Property>();
		final Collection<Property> properties = _arrayList;
		final EList<Property> _properties = type.getProperties();
		properties.addAll(_properties);
		final Collection<Property> _collectAllTemplateProperties = PropertyEvaluation.collectAllTemplateProperties(type);
		properties.addAll(_collectAllTemplateProperties);
		final Function1<Property, Boolean> _function = new Function1<Property, Boolean>() {
			public Boolean apply(final Property property) {
				final Property _referTo = property.getReferTo();
				final boolean _equals = Objects.equal(_referTo, null);
				return Boolean.valueOf(_equals);
			}
		};
		final Iterable<Property> declarationProperties = IterableExtensions.<Property> filter(properties, _function);
		final RecordType _parent = type.getParent();
		final boolean _notEquals = (!Objects.equal(_parent, null));
		if (_notEquals) {
			final RecordType _parent_1 = type.getParent();
			return this.removeAlreadyImplementedProperties(declarationProperties, _parent_1);
		} else {
			return declarationProperties;
		}
	}

	/**
	 * Find properties implemented in a type and remove them from the list of properties.
	 * 
	 * @param type
	 *            the parent type of the type where the property list belongs to
	 * 
	 * @returns
	 *          the remaining list of properties
	 */
	private Iterable<Property> removeAlreadyImplementedProperties(final Iterable<Property> list, final RecordType parentType) {
		final Collection<Property> allParentProperties = PropertyEvaluation.collectAllProperties(parentType);
		Iterable<Property> result = list;
		for (final Property parentProperty : allParentProperties) {
			final Function1<Property, Boolean> _function = new Function1<Property, Boolean>() {
				public Boolean apply(final Property item) {
					final String _name = item.getName();
					final String _name_1 = parentProperty.getName();
					final boolean _equals = _name.equals(_name_1);
					final boolean _not = (!_equals);
					return Boolean.valueOf(_not);
				}
			};
			final Iterable<Property> _filter = IterableExtensions.<Property> filter(result, _function);
			result = _filter;
		}
		return result;
	}

	/**
	 * Dispatch for literals
	 */
	private CharSequence _createValue(final StringLiteral literal) {
		CharSequence _xifexpression = null;
		final String _requiredType = this.getRequiredType(literal);
		final boolean _equals = _requiredType.equals("string");
		if (_equals) {
			final StringConcatenation _builder = new StringConcatenation();
			_builder.append("\"");
			final String _value = literal.getValue();
			_builder.append(_value, "");
			_builder.append("\"");
			_xifexpression = _builder;
		} else {
			final String _value_1 = literal.getValue();
			final String _plus = ("\'" + _value_1);
			final String _plus_1 = (_plus + "\'");
			_xifexpression = _plus_1;
		}
		return _xifexpression;
	}

	private CharSequence _createValue(final IntLiteral literal) {
		final StringConcatenation _builder = new StringConcatenation();
		final int _value = literal.getValue();
		_builder.append(_value, "");
		String _xifexpression = null;
		final String _requiredType = this.getRequiredType(literal);
		final boolean _equals = _requiredType.equals("long");
		if (_equals) {
			_xifexpression = "L";
		}
		_builder.append(_xifexpression, "");
		return _builder;
	}

	private CharSequence _createValue(final FloatLiteral literal) {
		final StringConcatenation _builder = new StringConcatenation();
		final Float _value = literal.getValue();
		_builder.append(_value, "");
		String _xifexpression = null;
		final String _requiredType = this.getRequiredType(literal);
		final boolean _equals = _requiredType.equals("float");
		if (_equals) {
			_xifexpression = "f";
		}
		_builder.append(_xifexpression, "");
		return _builder;
	}

	private CharSequence _createValue(final BooleanLiteral literal) {
		final StringConcatenation _builder = new StringConcatenation();
		String _xifexpression = null;
		final Boolean _value = literal.getValue();
		if ((_value).booleanValue()) {
			_xifexpression = "true";
		} else {
			_xifexpression = "false";
		}
		_builder.append(_xifexpression, "");
		return _builder;
	}

	private CharSequence _createValue(final ConstantLiteral literal) {
		final StringConcatenation _builder = new StringConcatenation();
		final Constant _value = literal.getValue();
		final Literal _value_1 = _value.getValue();
		final CharSequence _createValue = this.createValue(_value_1);
		_builder.append(_createValue, "");
		return _builder;
	}

	private CharSequence _createValue(final ArrayLiteral literal) {
		final StringConcatenation _builder = new StringConcatenation();
		_builder.append("{ ");
		final EList<Literal> _literals = literal.getLiterals();
		final Function1<Literal, CharSequence> _function = new Function1<Literal, CharSequence>() {
			public CharSequence apply(final Literal element) {
				final CharSequence _createValue = RecordTypeGenerator.this.createValue(element);
				return _createValue;
			}
		};
		final List<CharSequence> _map = ListExtensions.<Literal, CharSequence> map(_literals, _function);
		String _xifexpression = null;
		final EList<Literal> _literals_1 = literal.getLiterals();
		final Literal _get = _literals_1.get(0);
		if ((_get instanceof ArrayLiteral)) {
			_xifexpression = ",\n";
		} else {
			_xifexpression = ", ";
		}
		final String _join = IterableExtensions.join(_map, _xifexpression);
		_builder.append(_join, "");
		_builder.append(" }");
		return _builder;
	}

	private CharSequence _createValue(final Literal literal) {
		final Class<? extends Literal> _class = literal.getClass();
		final String _name = _class.getName();
		final String _plus = ("ERROR " + _name);
		return _plus;
	}

	/**
	 * Resolve the primitive type for the given literal.
	 */
	private String getRequiredType(final Literal literal) {
		String _switchResult = null;
		final EObject _eContainer = literal.eContainer();
		final EObject _switchValue = _eContainer;
		boolean _matched = false;
		if (!_matched) {
			if (_switchValue instanceof Constant) {
				final Constant _constant = (Constant) _switchValue;
				_matched = true;
				final EObject _eContainer_1 = literal.eContainer();
				final Classifier _type = ((Constant) _eContainer_1).getType();
				final EClassifier _class_ = _type.getClass_();
				final String _name = _class_.getName();
				_switchResult = _name;
			}
		}
		if (!_matched) {
			if (_switchValue instanceof Property) {
				final Property _property = (Property) _switchValue;
				_matched = true;
				final EObject _eContainer_1 = literal.eContainer();
				final Classifier _type = ((Property) _eContainer_1).getType();
				final EClassifier _class_ = _type.getClass_();
				final String _name = _class_.getName();
				_switchResult = _name;
			}
		}
		if (!_matched) {
			if (_switchValue instanceof Literal) {
				final Literal _literal = (Literal) _switchValue;
				_matched = true;
				final EObject _eContainer_1 = literal.eContainer();
				final String _requiredType = this.getRequiredType(((Literal) _eContainer_1));
				_switchResult = _requiredType;
			}
		}
		return _switchResult;
	}

	private CharSequence createValue(final Literal literal) {
		if (literal instanceof ArrayLiteral) {
			return this._createValue((ArrayLiteral) literal);
		} else if (literal instanceof BooleanLiteral) {
			return this._createValue((BooleanLiteral) literal);
		} else if (literal instanceof ConstantLiteral) {
			return this._createValue((ConstantLiteral) literal);
		} else if (literal instanceof FloatLiteral) {
			return this._createValue((FloatLiteral) literal);
		} else if (literal instanceof IntLiteral) {
			return this._createValue((IntLiteral) literal);
		} else if (literal instanceof StringLiteral) {
			return this._createValue((StringLiteral) literal);
		} else if (literal != null) {
			return this._createValue(literal);
		} else {
			throw new IllegalArgumentException("Unhandled parameter types: " +
					Arrays.<Object> asList(literal).toString());
		}
	}
}