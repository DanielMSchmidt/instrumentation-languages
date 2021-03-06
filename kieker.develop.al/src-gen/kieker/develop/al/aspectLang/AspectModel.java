/**
 */
package kieker.develop.al.aspectLang;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Aspect Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link kieker.develop.al.aspectLang.AspectModel#getName <em>Name</em>}</li>
 *   <li>{@link kieker.develop.al.aspectLang.AspectModel#getImports <em>Imports</em>}</li>
 *   <li>{@link kieker.develop.al.aspectLang.AspectModel#getSources <em>Sources</em>}</li>
 *   <li>{@link kieker.develop.al.aspectLang.AspectModel#getAdvices <em>Advices</em>}</li>
 *   <li>{@link kieker.develop.al.aspectLang.AspectModel#getPointcuts <em>Pointcuts</em>}</li>
 *   <li>{@link kieker.develop.al.aspectLang.AspectModel#getAspects <em>Aspects</em>}</li>
 * </ul>
 *
 * @see kieker.develop.al.aspectLang.AspectLangPackage#getAspectModel()
 * @model
 * @generated
 */
public interface AspectModel extends EObject
{
  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see kieker.develop.al.aspectLang.AspectLangPackage#getAspectModel_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link kieker.develop.al.aspectLang.AspectModel#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Imports</b></em>' containment reference list.
   * The list contents are of type {@link kieker.develop.al.aspectLang.Import}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Imports</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Imports</em>' containment reference list.
   * @see kieker.develop.al.aspectLang.AspectLangPackage#getAspectModel_Imports()
   * @model containment="true"
   * @generated
   */
  EList<Import> getImports();

  /**
   * Returns the value of the '<em><b>Sources</b></em>' containment reference list.
   * The list contents are of type {@link kieker.develop.al.aspectLang.ApplicationModel}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Sources</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Sources</em>' containment reference list.
   * @see kieker.develop.al.aspectLang.AspectLangPackage#getAspectModel_Sources()
   * @model containment="true"
   * @generated
   */
  EList<ApplicationModel> getSources();

  /**
   * Returns the value of the '<em><b>Advices</b></em>' containment reference list.
   * The list contents are of type {@link kieker.develop.al.aspectLang.Advice}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Advices</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Advices</em>' containment reference list.
   * @see kieker.develop.al.aspectLang.AspectLangPackage#getAspectModel_Advices()
   * @model containment="true"
   * @generated
   */
  EList<Advice> getAdvices();

  /**
   * Returns the value of the '<em><b>Pointcuts</b></em>' containment reference list.
   * The list contents are of type {@link kieker.develop.al.aspectLang.Pointcut}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Pointcuts</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Pointcuts</em>' containment reference list.
   * @see kieker.develop.al.aspectLang.AspectLangPackage#getAspectModel_Pointcuts()
   * @model containment="true"
   * @generated
   */
  EList<Pointcut> getPointcuts();

  /**
   * Returns the value of the '<em><b>Aspects</b></em>' containment reference list.
   * The list contents are of type {@link kieker.develop.al.aspectLang.Aspect}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Aspects</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Aspects</em>' containment reference list.
   * @see kieker.develop.al.aspectLang.AspectLangPackage#getAspectModel_Aspects()
   * @model containment="true"
   * @generated
   */
  EList<Aspect> getAspects();

} // AspectModel
