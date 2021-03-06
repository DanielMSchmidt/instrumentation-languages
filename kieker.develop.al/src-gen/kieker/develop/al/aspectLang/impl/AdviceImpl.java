/**
 */
package kieker.develop.al.aspectLang.impl;

import java.util.Collection;

import kieker.develop.al.aspectLang.Advice;
import kieker.develop.al.aspectLang.AdviceParameterDeclaration;
import kieker.develop.al.aspectLang.AspectLangPackage;
import kieker.develop.al.aspectLang.Collector;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Advice</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link kieker.develop.al.aspectLang.impl.AdviceImpl#getName <em>Name</em>}</li>
 *   <li>{@link kieker.develop.al.aspectLang.impl.AdviceImpl#getParameterDeclarations <em>Parameter Declarations</em>}</li>
 *   <li>{@link kieker.develop.al.aspectLang.impl.AdviceImpl#getCollectors <em>Collectors</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AdviceImpl extends MinimalEObjectImpl.Container implements Advice
{
  /**
   * The default value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected static final String NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected String name = NAME_EDEFAULT;

  /**
   * The cached value of the '{@link #getParameterDeclarations() <em>Parameter Declarations</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getParameterDeclarations()
   * @generated
   * @ordered
   */
  protected EList<AdviceParameterDeclaration> parameterDeclarations;

  /**
   * The cached value of the '{@link #getCollectors() <em>Collectors</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCollectors()
   * @generated
   * @ordered
   */
  protected EList<Collector> collectors;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected AdviceImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return AspectLangPackage.Literals.ADVICE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getName()
  {
    return name;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setName(String newName)
  {
    String oldName = name;
    name = newName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, AspectLangPackage.ADVICE__NAME, oldName, name));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<AdviceParameterDeclaration> getParameterDeclarations()
  {
    if (parameterDeclarations == null)
    {
      parameterDeclarations = new EObjectContainmentEList<AdviceParameterDeclaration>(AdviceParameterDeclaration.class, this, AspectLangPackage.ADVICE__PARAMETER_DECLARATIONS);
    }
    return parameterDeclarations;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Collector> getCollectors()
  {
    if (collectors == null)
    {
      collectors = new EObjectContainmentEList<Collector>(Collector.class, this, AspectLangPackage.ADVICE__COLLECTORS);
    }
    return collectors;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case AspectLangPackage.ADVICE__PARAMETER_DECLARATIONS:
        return ((InternalEList<?>)getParameterDeclarations()).basicRemove(otherEnd, msgs);
      case AspectLangPackage.ADVICE__COLLECTORS:
        return ((InternalEList<?>)getCollectors()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case AspectLangPackage.ADVICE__NAME:
        return getName();
      case AspectLangPackage.ADVICE__PARAMETER_DECLARATIONS:
        return getParameterDeclarations();
      case AspectLangPackage.ADVICE__COLLECTORS:
        return getCollectors();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case AspectLangPackage.ADVICE__NAME:
        setName((String)newValue);
        return;
      case AspectLangPackage.ADVICE__PARAMETER_DECLARATIONS:
        getParameterDeclarations().clear();
        getParameterDeclarations().addAll((Collection<? extends AdviceParameterDeclaration>)newValue);
        return;
      case AspectLangPackage.ADVICE__COLLECTORS:
        getCollectors().clear();
        getCollectors().addAll((Collection<? extends Collector>)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case AspectLangPackage.ADVICE__NAME:
        setName(NAME_EDEFAULT);
        return;
      case AspectLangPackage.ADVICE__PARAMETER_DECLARATIONS:
        getParameterDeclarations().clear();
        return;
      case AspectLangPackage.ADVICE__COLLECTORS:
        getCollectors().clear();
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case AspectLangPackage.ADVICE__NAME:
        return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
      case AspectLangPackage.ADVICE__PARAMETER_DECLARATIONS:
        return parameterDeclarations != null && !parameterDeclarations.isEmpty();
      case AspectLangPackage.ADVICE__COLLECTORS:
        return collectors != null && !collectors.isEmpty();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (name: ");
    result.append(name);
    result.append(')');
    return result.toString();
  }

} //AdviceImpl
