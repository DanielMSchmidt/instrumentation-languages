/**
 * generated by Xtext
 */
package de.cau.cs.se.instrumentation.al.ui.labeling;

import com.google.inject.Inject;
import de.cau.cs.se.instrumantation.model.structure.Container;
import de.cau.cs.se.instrumantation.model.structure.NamedElement;
import de.cau.cs.se.instrumentation.al.aspectLang.Aspect;
import de.cau.cs.se.instrumentation.al.aspectLang.Collector;
import de.cau.cs.se.instrumentation.al.aspectLang.ContainerNode;
import de.cau.cs.se.instrumentation.al.aspectLang.InsertionPoint;
import de.cau.cs.se.instrumentation.al.aspectLang.LocationQuery;
import de.cau.cs.se.instrumentation.al.aspectLang.Query;
import de.cau.cs.se.instrumentation.al.aspectLang.SubPathNode;
import de.cau.cs.se.instrumentation.al.aspectLang.WildcardNode;
import de.cau.cs.se.instrumentation.rl.recordLang.RecordType;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.ui.label.DefaultEObjectLabelProvider;

/**
 * Provides labels for a EObjects.
 * 
 * see http://www.eclipse.org/Xtext/documentation.html#labelProvider
 */
@SuppressWarnings("all")
public class AspectLangLabelProvider extends DefaultEObjectLabelProvider {
  @Inject
  public AspectLangLabelProvider(final AdapterFactoryLabelProvider delegate) {
    super(delegate);
  }
  
  public String text(final Aspect e) {
    return "aspect";
  }
  
  public CharSequence text(final ContainerNode e) {
    CharSequence _xifexpression = null;
    Container _container = e.getContainer();
    if ((_container instanceof NamedElement)) {
      Container _container_1 = e.getContainer();
      String _name = ((NamedElement) _container_1).getName();
      _xifexpression = _name;
    } else {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("Container is of type ");
      Container _container_2 = e.getContainer();
      Class<? extends Container> _class = _container_2.getClass();
      String _name_1 = _class.getName();
      _builder.append(_name_1, "");
      _xifexpression = _builder;
    }
    return _xifexpression;
  }
  
  public String text(final WildcardNode e) {
    return "*";
  }
  
  public String text(final SubPathNode e) {
    return "**";
  }
  
  public String text(final Query e) {
    return "query";
  }
  
  public String text(final LocationQuery e) {
    return "path";
  }
  
  public String text(final Collector e) {
    RecordType _type = e.getType();
    String _name = _type.getName();
    String _plus = (_name + " ");
    InsertionPoint _insertionPoint = e.getInsertionPoint();
    String _string = _insertionPoint.toString();
    String _plus_1 = (_plus + _string);
    return _plus_1;
  }
}
