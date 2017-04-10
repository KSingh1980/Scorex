package commons

import examples.commons.SimpleBoxTransaction
import org.scalacheck.Gen
import scorex.core.transaction.box.proposition.PublicKey25519Proposition
import scorex.core.transaction.state.PrivateKey25519
import scorex.testkit.CoreGenerators

trait ExamplesCommonGenerators extends CoreGenerators {
  lazy val privGen: Gen[(PrivateKey25519, Long)] = for {
    prop <- key25519Gen.map(_._1)
    long <- positiveLongGen
  } yield (prop, long)

  lazy val pGen: Gen[(PublicKey25519Proposition, Long)] = for {
    prop <- propositionGen
    long <- positiveLongGen
  } yield (prop, long)

  lazy val simpleBoxTransactionGen: Gen[SimpleBoxTransaction] = for {
    fee <- positiveLongGen
    timestamp <- positiveLongGen
    from: IndexedSeq[(PrivateKey25519, Long)] <- smallInt.flatMap(i => Gen.listOfN(i + 1, privGen).map(_.toIndexedSeq))
    to: IndexedSeq[(PublicKey25519Proposition, Long)] <- smallInt.flatMap(i => Gen.listOfN(i + 1, pGen).map(_.toIndexedSeq))
  } yield SimpleBoxTransaction(from, to, fee, timestamp)


}
