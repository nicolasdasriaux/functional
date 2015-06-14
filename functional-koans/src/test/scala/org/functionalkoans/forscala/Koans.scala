package org.functionalkoans.forscala

import org.scalatest._

import support.Master
import koans._

class Koans extends Suite {
  override def nestedSuites = List(
    new AboutVal,
    new AboutExpressions,
    new AboutInstructions,
    new AboutMethods,
    new AboutClasses,
    new AboutHigherOrderFunctions,
    new AboutTuples,
    new AboutImmutableCaseClasses,
    new AboutImmutableIndexedSequences,
    new AboutRanges,
    new AboutImmutableSets,
    new AboutOptions,
    new AboutPatternMatching,
    new AboutPartialFunctions,
    new AboutImmutableMaps
  )

  override def run(testName: Option[String], reporter: Reporter, stopper: Stopper, filter: Filter,
                   configMap: Map[String, Any], distributor: Option[Distributor], tracker: Tracker) {

    super.run(testName, reporter, Master, filter, configMap, distributor, tracker)
  }
}
