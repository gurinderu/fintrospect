package io.fintrospect.formats

import argonaut.Argonaut.{casecodec1, casecodec3}
import argonaut.CodecJson
import com.twitter.finagle.http.{Request, Status}
import io.fintrospect.formats.Argonaut.JsonFormat.{compact, decode, encode, parse, _}
import io.fintrospect.formats.Argonaut._
import io.fintrospect.formats.JsonFormat.InvalidJsonForDecoding
import io.fintrospect.parameters.{Body, BodySpec, Query}

import scala.language.reflectiveCalls

class ArgonautFiltersTest extends AutoFiltersSpec(Argonaut.Filters) {

  implicit def StreetAddressCodec: CodecJson[StreetAddress] = casecodec1(StreetAddress.apply, StreetAddress.unapply)("address")

  implicit def LetterCodec: CodecJson[Letter] = casecodec3(Letter.apply, Letter.unapply)("to", "from", "message")

  override def toString(l: Letter): String = compact(encode(l))

  override def fromString(s: String): Letter = decode[Letter](parse(s))

  override def bodySpec: BodySpec[Letter] = Argonaut.bodySpec[Letter]()

  override def toOut() = Argonaut.Filters.tToToOut[Letter]
}

class ArgonautJsonResponseBuilderTest extends JsonResponseBuilderSpec(Argonaut)

class ArgonautJsonFormatTest extends JsonFormatSpec(Argonaut) {

  implicit def StreetAddressCodec: CodecJson[StreetAddress] = casecodec1(StreetAddress.apply, StreetAddress.unapply)("address")

  implicit def LetterCodec: CodecJson[Letter] = casecodec3(Letter.apply, Letter.unapply)("to", "from", "message")

  describe("Argonaut.JsonFormat") {
    val aLetter = Letter(StreetAddress("my house"), StreetAddress("your house"), "hi there")
    it("roundtrips to JSON and back") {
      val encoded = encode(aLetter)
      decode[Letter](encoded) shouldBe aLetter
    }

    it("invalid extracted JSON throws up") {
      intercept[InvalidJsonForDecoding](decode[Letter](obj()))
    }

    it("body spec decodes content") {
      Body(bodySpec[Letter]()) <-- Argonaut.ResponseBuilder.Ok(encode(aLetter)).build() shouldBe aLetter
    }

    it("param spec decodes content") {
      val param = Query.required(parameterSpec[Letter]("name"))
      (param <-- Request("?name=" + encode(aLetter))) shouldBe aLetter
    }

    it("response spec has correct code") {
      responseSpec[Letter](Status.Ok -> "ok", aLetter).status shouldBe Status.Ok
    }

  }

  override val expectedJson: String = """{"string":"hello","null":null,"bigInt":12344,"object":{"field1":"aString"},"decimal":1.2,"array":["world",true],"long":2,"bool":true,"int":1}"""
}
