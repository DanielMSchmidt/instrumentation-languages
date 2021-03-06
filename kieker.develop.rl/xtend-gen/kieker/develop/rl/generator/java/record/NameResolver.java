package kieker.develop.rl.generator.java.record;

import java.util.Collections;
import java.util.List;
import kieker.develop.rl.recordLang.BaseType;
import kieker.develop.rl.recordLang.Classifier;
import kieker.develop.rl.recordLang.Property;
import kieker.develop.rl.validation.PropertyEvaluation;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class NameResolver {
  /**
   * Returns the correct name for a getter following Java conventions.
   * 
   * @param property
   * 		a property of a record type
   * 
   * @returns the name of the getter of the property
   */
  public static CharSequence createGetterName(final Property property) {
    CharSequence _xifexpression = null;
    Classifier _findType = PropertyEvaluation.findType(property);
    BaseType _type = _findType.getType();
    String _name = _type.getName();
    boolean _equals = _name.equals("boolean");
    if (_equals) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("is");
      String _name_1 = property.getName();
      String _firstUpper = StringExtensions.toFirstUpper(_name_1);
      _builder.append(_firstUpper, "");
      _xifexpression = _builder;
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("get");
      String _name_2 = property.getName();
      String _firstUpper_1 = StringExtensions.toFirstUpper(_name_2);
      _builder_1.append(_firstUpper_1, "");
      _xifexpression = _builder_1;
    }
    return _xifexpression;
  }
  
  /**
   * create a constant name from a standard name camel case name.
   */
  public static String createConstantName(final String name) {
    String _replaceAll = name.replaceAll("([a-z])([A-Z])", "$1_$2");
    return _replaceAll.toUpperCase();
  }
  
  /**
   * Check whether a given name is identical to a keyword of the target language and prepends an _.
   */
  public static String protectKeywords(final String name) {
    String _xblockexpression = null;
    {
      final List<String> keywords = Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("interface", "class", "private", "protected", "public", "return", "final", "volatile", "if", "else", "for", "foreach"));
      String _xifexpression = null;
      final Function1<String, Boolean> _function = (String it) -> {
        return Boolean.valueOf(it.equals(name));
      };
      boolean _exists = IterableExtensions.<String>exists(keywords, _function);
      if (_exists) {
        _xifexpression = ("_" + name);
      } else {
        _xifexpression = name;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
}
