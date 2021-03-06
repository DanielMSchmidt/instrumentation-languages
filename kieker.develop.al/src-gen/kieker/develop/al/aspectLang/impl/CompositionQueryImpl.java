/**
 */
package kieker.develop.al.aspectLang.impl;

import java.util.Collection;

import kieker.develop.al.aspectLang.AspectLangPackage;
import kieker.develop.al.aspectLang.CompositionQuery;
import kieker.develop.al.aspectLang.LocationQuery;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Composition Query</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link kieker.develop.al.aspectLang.impl.CompositionQueryImpl#getSubQueries <em>Sub Queries</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CompositionQueryImpl extends QueryImpl implements CompositionQuery
{
  /**
   * The cached value of the '{@link #getSubQueries() <em>Sub Queries</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSubQueries()
   * @generated
   * @ordered
   */
  protected EList<LocationQuery> subQueries;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected CompositionQueryImpl()
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
    return AspectLangPackage.Literals.COMPOSITION_QUERY;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<LocationQuery> getSubQueries()
  {
    if (subQueries == null)
    {
      subQueries = new EObjectContainmentEList<LocationQuery>(LocationQuery.class, this, AspectLangPackage.COMPOSITION_QUERY__SUB_QUERIES);
    }
    return subQueries;
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
      case AspectLangPackage.COMPOSITION_QUERY__SUB_QUERIES:
        return ((InternalEList<?>)getSubQueries()).basicRemove(otherEnd, msgs);
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
      case AspectLangPackage.COMPOSITION_QUERY__SUB_QUERIES:
        return getSubQueries();
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
      case AspectLangPackage.COMPOSITION_QUERY__SUB_QUERIES:
        getSubQueries().clear();
        getSubQueries().addAll((Collection<? extends LocationQuery>)newValue);
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
      case AspectLangPackage.COMPOSITION_QUERY__SUB_QUERIES:
        getSubQueries().clear();
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
      case AspectLangPackage.COMPOSITION_QUERY__SUB_QUERIES:
        return subQueries != null && !subQueries.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //CompositionQueryImpl
