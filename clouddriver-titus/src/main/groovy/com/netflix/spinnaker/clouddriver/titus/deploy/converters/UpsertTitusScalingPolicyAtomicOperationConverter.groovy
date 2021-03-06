/*
 * Copyright 2017 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.netflix.spinnaker.clouddriver.titus.deploy.converters

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.netflix.spinnaker.clouddriver.orchestration.AtomicOperations
import com.netflix.spinnaker.clouddriver.security.AbstractAtomicOperationsCredentialsSupport
import com.netflix.spinnaker.clouddriver.titus.TitusOperation
import com.netflix.spinnaker.clouddriver.titus.deploy.description.UpsertTitusScalingPolicyDescription
import com.netflix.spinnaker.clouddriver.titus.deploy.ops.UpsertTitusScalingPolicyAtomicOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component('titusUpsertScalingPolicyDescription')
@TitusOperation(AtomicOperations.UPSERT_SCALING_POLICY)
class UpsertTitusScalingPolicyAtomicOperationConverter extends AbstractAtomicOperationsCredentialsSupport {

  @Autowired
  ObjectMapper objectMapper

  @Override
  UpsertTitusScalingPolicyAtomicOperation convertOperation(Map input) {
    new UpsertTitusScalingPolicyAtomicOperation(convertDescription(input))
  }

  @Override
  UpsertTitusScalingPolicyDescription convertDescription(Map input) {
    UpsertTitusScalingPolicyDescription converted = objectMapper.copy()
      .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
      .convertValue(input, UpsertTitusScalingPolicyDescription)
    converted.credentials = getCredentialsObject(input.credentials as String)
    converted
  }
}
