/**
 */
package kieker.develop.rl.recordLang;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Array Literal</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link kieker.develop.rl.recordLang.ArrayLiteral#getLiterals <em>Literals</em>}</li>
 * </ul>
 *
 * @see kieker.develop.rl.recordLang.RecordLangPackage#getArrayLiteral()
 * @model
 * @generated
 */
public interface ArrayLiteral extends Literal
{
  /**
   * Returns the value of the '<em><b>Literals</b></em>' containment reference list.
   * The list contents are of type {@link kieker.develop.rl.recordLang.Literal}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Literals</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Literals</em>' containment reference list.
   * @see kieker.develop.rl.recordLang.RecordLangPackage#getArrayLiteral_Literals()
   * @model containment="true"
   * @generated
   */
  EList<Literal> getLiterals();

} // ArrayLiteral
