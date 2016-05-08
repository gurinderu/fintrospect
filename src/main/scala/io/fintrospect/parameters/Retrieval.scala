package io.fintrospect.parameters

/**
 * Represents the ability to retrieve a parameter value from an enclosing object (request/form etc..)
 */
trait Retrieval[T, -From] {

  /**
   * Extract the parameter from the target object. Throws on failure, but that shouldn't be a problem as the pre-validation
    * stage for declared parameters and bodies handles the failure before user code is entered.
   */
  def <--(from: From): T

  /**
    * Extract the parameter from the target object. Throws on failure, but that shouldn't be a problem as the pre-validation
    * stage for declared parameters and bodies handles the failure before user code is entered.
   */
  def from(from: From): T = <--(from)
}

trait Mandatory[T,From] extends Retrieval[T, From] with Parameter with Validatable[T, From] {
  override val required = true
  override def <--(from: From): T = validate(from).asTry.get.get
}

trait Optional[T, From] extends Retrieval[Option[T], From] with Parameter with Validatable[T, From]  {
  override val required = false
  def <--(from: From) = validate(from).asTry.get
}
