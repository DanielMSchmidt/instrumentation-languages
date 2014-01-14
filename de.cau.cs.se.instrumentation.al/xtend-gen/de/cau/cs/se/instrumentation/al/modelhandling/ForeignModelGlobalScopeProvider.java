package de.cau.cs.se.instrumentation.al.modelhandling;

import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.inject.Inject;
import de.cau.cs.se.instrumentation.al.applicationLang.ApplicationModel;
import de.cau.cs.se.instrumentation.al.modelhandling.ForeignModelTypeProviderFactory;
import de.cau.cs.se.instrumentation.al.modelhandling.ForeignModelTypeScope;
import de.cau.cs.se.instrumentation.al.modelhandling.IForeignModelTypeProvider;
import java.util.Iterator;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.impl.DefaultGlobalScopeProvider;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;

@SuppressWarnings("all")
public class ForeignModelGlobalScopeProvider extends DefaultGlobalScopeProvider {
  @Inject
  private ForeignModelTypeProviderFactory typeProviderFactory;
  
  @Inject
  private IQualifiedNameConverter qualifiedNameConverter;
  
  public IScope getScope(final Resource resource, final EReference reference, final Predicate<IEObjectDescription> filter) {
    EClass _eReferenceType = reference.getEReferenceType();
    final IScope parentTypeScope = this.getParentTypeScope(resource, reference, filter, _eReferenceType);
    EClass _eReferenceType_1 = reference.getEReferenceType();
    return super.getScope(parentTypeScope, resource, false, _eReferenceType_1, filter);
  }
  
  public IScope getParentTypeScope(final Resource resource, final EReference reference, final Predicate<IEObjectDescription> filter, final EClass referenceType) {
    boolean _isAssignableFrom = EcoreUtil2.isAssignableFrom(EcorePackage.Literals.EOBJECT, referenceType);
    if (_isAssignableFrom) {
      boolean _notEquals = (!Objects.equal(resource, null));
      if (_notEquals) {
        final ResourceSet resourceSet = resource.getResourceSet();
        boolean _notEquals_1 = (!Objects.equal(resourceSet, null));
        if (_notEquals_1) {
          TreeIterator<EObject> _allContents = resource.getAllContents();
          Iterator<ApplicationModel> _filter = Iterators.<ApplicationModel>filter(_allContents, ApplicationModel.class);
          ApplicationModel _head = IteratorExtensions.<ApplicationModel>head(_filter);
          final IForeignModelTypeProvider typeProvider = this.typeProviderFactory.getTypeProvider(resourceSet, _head);
          ForeignModelTypeScope _foreignModelTypeScope = new ForeignModelTypeScope(typeProvider, this.qualifiedNameConverter, filter);
          return _foreignModelTypeScope;
        } else {
          IllegalStateException _illegalStateException = new IllegalStateException("context must be contained in a resource set");
          throw _illegalStateException;
        }
      } else {
        IllegalStateException _illegalStateException_1 = new IllegalStateException("context must be contained in a resource");
        throw _illegalStateException_1;
      }
    } else {
      return IScope.NULLSCOPE;
    }
  }
}