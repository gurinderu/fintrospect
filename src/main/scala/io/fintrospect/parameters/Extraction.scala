package io.fintrospect.parameters

import scala.util.{Failure, Success, Try}

class InvalidParameters(invalid: Seq[Parameter]) extends Exception(
  invalid.flatMap(_.description).mkString(", ")
)

sealed trait Extraction[T] {
  def asTry: Try[Option[T]]

  val invalid: Seq[Parameter]
}

case class Missing[T]() extends Extraction[T] {
  override def asTry: Try[Option[T]] = Success(None)

  override val invalid: Seq[Parameter] = Nil
}

case class Extracted[T](value: T) extends Extraction[T] {
  override def asTry: Try[Option[T]] = Success(Some(value))

  override val invalid: Seq[Parameter] = Nil
}

case class MissingOrInvalid[T](missingOrInvalid: Seq[Parameter]) extends Extraction[T] {
  override def asTry: Try[Option[T]] = Failure(new InvalidParameters(missingOrInvalid))

  override val invalid: Seq[Parameter] = missingOrInvalid
}
