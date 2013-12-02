/**
 * generated by Xtext
 */
package de.cau.cs.se.instrumentation.rl.validation;

import com.google.common.base.Objects;
import de.cau.cs.se.instrumentation.rl.recordLang.PartialRecordType;
import de.cau.cs.se.instrumentation.rl.recordLang.Property;
import de.cau.cs.se.instrumentation.rl.recordLang.RecordLangPackage.Literals;
import de.cau.cs.se.instrumentation.rl.recordLang.RecordType;
import de.cau.cs.se.instrumentation.rl.recordLang.Type;
import de.cau.cs.se.instrumentation.rl.validation.AbstractRecordLangValidator;
import de.cau.cs.se.instrumentation.rl.validation.PropertyEvaluation;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pair;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

/**
 * Custom validation rules.
 * 
 * see http://www.eclipse.org/Xtext/documentation.html#validation
 */
@SuppressWarnings("all")
public class RecordLangValidator extends AbstractRecordLangValidator {
  public final static String INVALID_NAME = "invalidName";
  
  @Check
  public void checkPropertyDeclaration(final Property property) {
    EObject _eContainer = property.eContainer();
    if ((_eContainer instanceof RecordType)) {
      EObject _eContainer_1 = property.eContainer();
      EList<Property> _collectAllProperties = PropertyEvaluation.collectAllProperties(((RecordType) _eContainer_1));
      final Function1<Property,Boolean> _function = new Function1<Property,Boolean>() {
        public Boolean apply(final Property p) {
          boolean _and = false;
          String _name = p.getName();
          String _name_1 = property.getName();
          boolean _equals = _name.equals(_name_1);
          if (!_equals) {
            _and = false;
          } else {
            boolean _notEquals = (!Objects.equal(p, property));
            _and = (_equals && _notEquals);
          }
          return Boolean.valueOf(_and);
        }
      };
      boolean _exists = IterableExtensions.<Property>exists(_collectAllProperties, _function);
      if (_exists) {
        EObject _eContainer_2 = property.eContainer();
        EList<Property> _collectAllProperties_1 = PropertyEvaluation.collectAllProperties(((RecordType) _eContainer_2));
        final Function1<Property,Boolean> _function_1 = new Function1<Property,Boolean>() {
          public Boolean apply(final Property p) {
            boolean _and = false;
            String _name = p.getName();
            String _name_1 = property.getName();
            boolean _equals = _name.equals(_name_1);
            if (!_equals) {
              _and = false;
            } else {
              boolean _notEquals = (!Objects.equal(p, property));
              _and = (_equals && _notEquals);
            }
            return Boolean.valueOf(_and);
          }
        };
        Property _findFirst = IterableExtensions.<Property>findFirst(_collectAllProperties_1, _function_1);
        EObject _eContainer_3 = _findFirst.eContainer();
        final Type type = ((Type) _eContainer_3);
        String _name = type.getName();
        String _plus = ("Property has been defined in " + _name);
        String _plus_1 = (_plus + ". Cannot be declared again.");
        this.error(_plus_1, 
          Literals.PROPERTY__NAME, 
          RecordLangValidator.INVALID_NAME);
      }
    } else {
      EObject _eContainer_4 = property.eContainer();
      if ((_eContainer_4 instanceof PartialRecordType)) {
        EObject _eContainer_5 = property.eContainer();
        EList<Property> _collectAllProperties_2 = PropertyEvaluation.collectAllProperties(((PartialRecordType) _eContainer_5));
        final Function1<Property,Boolean> _function_2 = new Function1<Property,Boolean>() {
          public Boolean apply(final Property p) {
            boolean _and = false;
            String _name = p.getName();
            String _name_1 = property.getName();
            boolean _equals = _name.equals(_name_1);
            if (!_equals) {
              _and = false;
            } else {
              boolean _notEquals = (!Objects.equal(p, property));
              _and = (_equals && _notEquals);
            }
            return Boolean.valueOf(_and);
          }
        };
        boolean _exists_1 = IterableExtensions.<Property>exists(_collectAllProperties_2, _function_2);
        if (_exists_1) {
          EObject _eContainer_6 = property.eContainer();
          EList<Property> _collectAllProperties_3 = PropertyEvaluation.collectAllProperties(((PartialRecordType) _eContainer_6));
          final Function1<Property,Boolean> _function_3 = new Function1<Property,Boolean>() {
            public Boolean apply(final Property p) {
              boolean _and = false;
              String _name = p.getName();
              String _name_1 = property.getName();
              boolean _equals = _name.equals(_name_1);
              if (!_equals) {
                _and = false;
              } else {
                boolean _notEquals = (!Objects.equal(p, property));
                _and = (_equals && _notEquals);
              }
              return Boolean.valueOf(_and);
            }
          };
          Property _findFirst_1 = IterableExtensions.<Property>findFirst(_collectAllProperties_3, _function_3);
          EObject _eContainer_7 = _findFirst_1.eContainer();
          final Type type_1 = ((Type) _eContainer_7);
          String _name_1 = type_1.getName();
          String _plus_2 = ("Property has been defined in " + _name_1);
          String _plus_3 = (_plus_2 + ". Cannot be declared again.");
          this.error(_plus_3, 
            Literals.PROPERTY__NAME, 
            RecordLangValidator.INVALID_NAME);
        }
      }
    }
  }
  
  @Check
  public void checkRecordTypeComposition(final RecordType type) {
    final EList<Property> properties = PropertyEvaluation.collectAllProperties(type);
    final Function1<Property,Boolean> _function = new Function1<Property,Boolean>() {
      public Boolean apply(final Property p) {
        final Function1<Property,Boolean> _function = new Function1<Property,Boolean>() {
          public Boolean apply(final Property pInner) {
            boolean _and = false;
            String _name = p.getName();
            String _name_1 = pInner.getName();
            boolean _equals = _name.equals(_name_1);
            if (!_equals) {
              _and = false;
            } else {
              boolean _notEquals = (!Objects.equal(p, pInner));
              _and = (_equals && _notEquals);
            }
            return Boolean.valueOf(_and);
          }
        };
        boolean _exists = IterableExtensions.<Property>exists(properties, _function);
        return Boolean.valueOf(_exists);
      }
    };
    boolean _exists = IterableExtensions.<Property>exists(properties, _function);
    if (_exists) {
      BasicEList<Pair<Property,Property>> _basicEList = new BasicEList<Pair<Property, Property>>();
      final EList<Pair<Property,Property>> duplicates = _basicEList;
      final Procedure1<Property> _function_1 = new Procedure1<Property>() {
        public void apply(final Property p) {
          Pair<Property,Property> _findDuplicate = RecordLangValidator.this.findDuplicate(p, properties);
          duplicates.add(_findDuplicate);
        }
      };
      IterableExtensions.<Property>forEach(properties, _function_1);
      final Procedure1<Pair<Property,Property>> _function_2 = new Procedure1<Pair<Property,Property>>() {
        public void apply(final Pair<Property,Property> entry) {
          Property _key = entry.getKey();
          String _name = _key.getName();
          String _plus = ("Multiple property inheritance of " + _name);
          String _plus_1 = (_plus + 
            " from ");
          Property _key_1 = entry.getKey();
          EObject _eContainer = _key_1.eContainer();
          String _name_1 = ((Type) _eContainer).getName();
          String _plus_2 = (_plus_1 + _name_1);
          String _plus_3 = (_plus_2 + " and ");
          Property _value = entry.getValue();
          EObject _eContainer_1 = _value.eContainer();
          String _name_2 = ((Type) _eContainer_1).getName();
          String _plus_4 = (_plus_3 + _name_2);
          RecordLangValidator.this.error(_plus_4, 
            Literals.PARTIAL_RECORD_TYPE__PARENTS, 
            RecordLangValidator.INVALID_NAME);
        }
      };
      IterableExtensions.<Pair<Property,Property>>forEach(duplicates, _function_2);
    }
  }
  
  public Pair<Property,Property> findDuplicate(final Property property, final EList<Property> properties) {
    final Function1<Property,Boolean> _function = new Function1<Property,Boolean>() {
      public Boolean apply(final Property p) {
        boolean _and = false;
        String _name = property.getName();
        String _name_1 = p.getName();
        boolean _equals = _name.equals(_name_1);
        if (!_equals) {
          _and = false;
        } else {
          boolean _notEquals = (!Objects.equal(p, property));
          _and = (_equals && _notEquals);
        }
        return Boolean.valueOf(_and);
      }
    };
    final Property second = IterableExtensions.<Property>findFirst(properties, _function);
    Pair<Property,Property> _pair = new Pair<Property, Property>(property, second);
    return _pair;
  }
  
  @Check
  public void checkPartialTypeComposition(final PartialRecordType type) {
    final EList<Property> properties = PropertyEvaluation.collectAllProperties(type);
    final Function1<Property,Boolean> _function = new Function1<Property,Boolean>() {
      public Boolean apply(final Property p) {
        final Function1<Property,Boolean> _function = new Function1<Property,Boolean>() {
          public Boolean apply(final Property pInner) {
            boolean _and = false;
            String _name = p.getName();
            String _name_1 = pInner.getName();
            boolean _equals = _name.equals(_name_1);
            if (!_equals) {
              _and = false;
            } else {
              boolean _notEquals = (!Objects.equal(p, pInner));
              _and = (_equals && _notEquals);
            }
            return Boolean.valueOf(_and);
          }
        };
        boolean _exists = IterableExtensions.<Property>exists(properties, _function);
        return Boolean.valueOf(_exists);
      }
    };
    boolean _exists = IterableExtensions.<Property>exists(properties, _function);
    if (_exists) {
      BasicEList<Pair<Property,Property>> _basicEList = new BasicEList<Pair<Property, Property>>();
      final EList<Pair<Property,Property>> duplicates = _basicEList;
      final Procedure1<Property> _function_1 = new Procedure1<Property>() {
        public void apply(final Property p) {
          Pair<Property,Property> _findDuplicate = RecordLangValidator.this.findDuplicate(p, properties);
          duplicates.add(_findDuplicate);
        }
      };
      IterableExtensions.<Property>forEach(properties, _function_1);
      final Procedure1<Pair<Property,Property>> _function_2 = new Procedure1<Pair<Property,Property>>() {
        public void apply(final Pair<Property,Property> entry) {
          Property _key = entry.getKey();
          String _name = _key.getName();
          String _plus = ("Multiple property inheritance of " + _name);
          String _plus_1 = (_plus + 
            " from ");
          Property _key_1 = entry.getKey();
          EObject _eContainer = _key_1.eContainer();
          String _name_1 = ((Type) _eContainer).getName();
          String _plus_2 = (_plus_1 + _name_1);
          String _plus_3 = (_plus_2 + " and ");
          Property _value = entry.getValue();
          EObject _eContainer_1 = _value.eContainer();
          String _name_2 = ((Type) _eContainer_1).getName();
          String _plus_4 = (_plus_3 + _name_2);
          RecordLangValidator.this.error(_plus_4, 
            Literals.PARTIAL_RECORD_TYPE__PARENTS, 
            RecordLangValidator.INVALID_NAME);
        }
      };
      IterableExtensions.<Pair<Property,Property>>forEach(duplicates, _function_2);
    }
  }
}