/*
  Copyright (c) 2020-present, CompareVerifier Contributors.

  Licensed under the Apache License, Version 2.0 (the "License"); you may not
  use this file except in compliance with the License. You may obtain a copy
  of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
  License for the specific language governing permissions and limitations
  under the License.
 */
package org.artrev.compareverifier;

import org.artrev.compareverifier.implementations.CompareToNull;
import org.artrev.compareverifier.implementations.Correct;
import org.artrev.compareverifier.implementations.EqualToNull;
import org.artrev.compareverifier.implementations.InconsistentWithEquals;
import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import java.math.BigDecimal;

import static org.junit.Assert.assertNotNull;

@RunWith(Enclosed.class)
public class ComparableVerifierTest {
    public static class ForInstance {
        @Test
        public void should_create_non_Null_Verifier_instance() {
            // given:
            final VerificationInstancesCreator<Correct> less =
                    VerificationInstancesCreators.from(new Correct(0));
            final VerificationInstancesCreator<Correct> equal =
                    VerificationInstancesCreators.from(new Correct(42));
            final VerificationInstancesCreator<Correct> greater =
                    VerificationInstancesCreators.from(new Correct(100));

            // when:
            final ComparableVerifier<Correct> verifier =
                    ComparableVerifier.forInstances(less, equal, greater);

            // then:
            assertNotNull(verifier);
        }
    }

    @SuppressWarnings("ConstantConditions")
    public static class ArgumentVerification {
        @Rule
        public ExpectedException expectedException = ExpectedException.none();

        @Test
        public void should_throw_If_Lesser_Creator_is_Null() {
            // given:
            final VerificationInstancesCreator<Correct> lesser = null;
            final VerificationInstancesCreator<Correct> equal =
                    VerificationInstancesCreators.from(new Correct(42));
            final VerificationInstancesCreator<Correct> greater =
                    VerificationInstancesCreators.from(new Correct(100));

            expectedException.expect(IllegalArgumentException.class);
            expectedException.expectMessage("VerificationInstancesCreator (lesser) cannot be null!");

            // when:
            ComparableVerifier
                    .forInstances(lesser, equal, greater)
                    .verify();
        }

        @Test
        public void should_throw_If_Equal_Creator_is_Null() {
            // given:
            final VerificationInstancesCreator<Correct> lesser =
                    VerificationInstancesCreators.from(new Correct(0));
            final VerificationInstancesCreator<Correct> equal = null;
            final VerificationInstancesCreator<Correct> greater =
                    VerificationInstancesCreators.from(new Correct(100));

            expectedException.expect(IllegalArgumentException.class);
            expectedException.expectMessage("VerificationInstancesCreator (equal) cannot be null!");

            // when:
            ComparableVerifier
                    .forInstances(lesser, equal, greater)
                    .verify();
        }

        @Test
        public void should_throw_If_Greater_Creator_is_Null() {
            // given:
            final VerificationInstancesCreator<Correct> lesser =
                    VerificationInstancesCreators.from(new Correct(0));
            final VerificationInstancesCreator<Correct> equal =
                    VerificationInstancesCreators.from(new Correct(42));
            final VerificationInstancesCreator<Correct> greater = null;

            expectedException.expect(IllegalArgumentException.class);
            expectedException.expectMessage("VerificationInstancesCreator (greater) cannot be null!");

            // when:
            ComparableVerifier
                    .forInstances(lesser, equal, greater)
                    .verify();
        }

        @Test
        public void should_throw_If_created_Lesser_instances_is_Null() {
            // given:
            final VerificationInstancesCreator<Correct> lesser =
                    VerificationInstancesCreators.nullInstances();
            final VerificationInstancesCreator<Correct> equal =
                    VerificationInstancesCreators.from(new Correct(42));
            final VerificationInstancesCreator<Correct> greater =
                    VerificationInstancesCreators.from(new Correct(100));

            expectedException.expect(IllegalArgumentException.class);
            expectedException.expectMessage("VerificationInstancesCreator (lesser) cannot return null instances!");

            // when:
            ComparableVerifier
                    .forInstances(lesser, equal, greater)
                    .verify();
        }

        @Test
        public void should_throw_If_created_Equal_instances_is_Null() {
            // given:
            final VerificationInstancesCreator<Correct> lesser =
                    VerificationInstancesCreators.from(new Correct(0));
            final VerificationInstancesCreator<Correct> equal =
                    VerificationInstancesCreators.nullInstances();
            final VerificationInstancesCreator<Correct> greater =
                    VerificationInstancesCreators.from(new Correct(100));

            expectedException.expect(IllegalArgumentException.class);
            expectedException.expectMessage("VerificationInstancesCreator (equal) cannot return null instances!");

            // when:
            ComparableVerifier
                    .forInstances(lesser, equal, greater)
                    .verify();
        }

        @Test
        public void should_throw_If_created_Greater_instances_is_Null() {
            // given:
            final VerificationInstancesCreator<Correct> lesser =
                    VerificationInstancesCreators.from(new Correct(0));
            final VerificationInstancesCreator<Correct> equal =
                    VerificationInstancesCreators.from(new Correct(42));
            final VerificationInstancesCreator<Correct> greater =
                    VerificationInstancesCreators.nullInstances();

            expectedException.expect(IllegalArgumentException.class);
            expectedException.expectMessage("VerificationInstancesCreator (greater) cannot return null instances!");

            // when:
            ComparableVerifier
                    .forInstances(lesser, equal, greater)
                    .verify();
        }

        @Test
        public void should_throw_If_created_Lesser_instances_list_is_Empty() {
            // given:
            final VerificationInstancesCreator<Correct> lesser =
                    VerificationInstancesCreators.emptyInstances();
            final VerificationInstancesCreator<Correct> equal =
                    VerificationInstancesCreators.from(new Correct(42));
            final VerificationInstancesCreator<Correct> greater =
                    VerificationInstancesCreators.from(new Correct(303));

            expectedException.expect(IllegalArgumentException.class);
            expectedException.expectMessage("VerificationInstancesCreator (lesser) cannot return empty list of instances!");

            // when:
            ComparableVerifier
                    .forInstances(lesser, equal, greater)
                    .verify();
        }

        @Test
        public void should_throw_If_created_Equal_instances_list_is_Empty() {
            // given:
            final VerificationInstancesCreator<Correct> lesser =
                    VerificationInstancesCreators.from(new Correct(0));
            final VerificationInstancesCreator<Correct> equal =
                    VerificationInstancesCreators.emptyInstances();
            final VerificationInstancesCreator<Correct> greater =
                    VerificationInstancesCreators.from(new Correct(303));

            expectedException.expect(IllegalArgumentException.class);
            expectedException.expectMessage("VerificationInstancesCreator (equal) cannot return empty list of instances!");

            // when:
            ComparableVerifier
                    .forInstances(lesser, equal, greater)
                    .verify();
        }

        @Test
        public void should_throw_If_created_Greater_instances_list_is_Empty() {
            // given:
            final VerificationInstancesCreator<Correct> lesser =
                    VerificationInstancesCreators.from(new Correct(0));
            final VerificationInstancesCreator<Correct> equal =
                    VerificationInstancesCreators.from(new Correct(42));
            final VerificationInstancesCreator<Correct> greater =
                    VerificationInstancesCreators.emptyInstances();

            expectedException.expect(IllegalArgumentException.class);
            expectedException.expectMessage("VerificationInstancesCreator (greater) cannot return empty list of instances!");

            // when:
            ComparableVerifier
                    .forInstances(lesser, equal, greater)
                    .verify();
        }

        @Test
        public void should_throw_If_created_Lesser_instances_contain_Null() {
            // given:
            final VerificationInstancesCreator<Correct> lesser =
                    VerificationInstancesCreators.nullValue();
            final VerificationInstancesCreator<Correct> equal =
                    VerificationInstancesCreators.from(new Correct(42));
            final VerificationInstancesCreator<Correct> greater =
                    VerificationInstancesCreators.from(new Correct(303));

            expectedException.expect(IllegalArgumentException.class);
            expectedException.expectMessage("VerificationInstancesCreator (lesser) cannot contain null instances!");

            // when:
            ComparableVerifier
                    .forInstances(lesser, equal, greater)
                    .verify();
        }

        @Test
        public void should_throw_If_created_Equal_instances_contain_Null() {
            // given:
            final VerificationInstancesCreator<Correct> lesser =
                    VerificationInstancesCreators.from(new Correct(0));
            final VerificationInstancesCreator<Correct> equal =
                    VerificationInstancesCreators.nullValue();
            final VerificationInstancesCreator<Correct> greater =
                    VerificationInstancesCreators.from(new Correct(303));

            expectedException.expect(IllegalArgumentException.class);
            expectedException.expectMessage("VerificationInstancesCreator (equal) cannot contain null instances!");

            // when:
            ComparableVerifier
                    .forInstances(lesser, equal, greater)
                    .verify();
        }

        @Test
        public void should_throw_If_created_Greater_instances_contain_Null() {
            // given:
            final VerificationInstancesCreator<Correct> lesser =
                    VerificationInstancesCreators.from(new Correct(0));
            final VerificationInstancesCreator<Correct> equal =
                    VerificationInstancesCreators.from(new Correct(42));
            final VerificationInstancesCreator<Correct> greater =
                    VerificationInstancesCreators.nullValue();

            expectedException.expect(IllegalArgumentException.class);
            expectedException.expectMessage("VerificationInstancesCreator (greater) cannot contain null instances!");

            // when:
            ComparableVerifier
                    .forInstances(lesser, equal, greater)
                    .verify();
        }
    }

    public static class SuppressConsistentWithEquals {
        @Rule
        public ExpectedException expectedException = ExpectedException.none();

        @Test
        public void should_allow_inconsistent_instances() {
            // given:
            final VerificationInstancesCreator<Correct> lesser =
                    VerificationInstancesCreators.from(
                            new Correct(0)
                    );
            final VerificationInstancesCreator<Correct> equal =
                    VerificationInstancesCreators.from(
                            new InconsistentWithEquals(42, 0),
                            new InconsistentWithEquals(42, 1)
                    );
            final VerificationInstancesCreator<Correct> greater =
                    VerificationInstancesCreators.from(
                            new Correct(100)
                    );

            // when:
            ComparableVerifier
                    .forInstances(lesser, equal, greater)
                    .suppressConsistentWithEquals(true)
                    .verify();
        }

        @Test
        public void should_NOT_allow_inconsistent_instances() {
            // given:
            final VerificationInstancesCreator<Correct> lesser =
                    VerificationInstancesCreators.from(
                            new Correct(0)
                    );
            final VerificationInstancesCreator<Correct> equal =
                    VerificationInstancesCreators.from(
                            new InconsistentWithEquals(42, 0),
                            new InconsistentWithEquals(42, 1)
                    );
            final VerificationInstancesCreator<Correct> greater =
                    VerificationInstancesCreators.from(
                            new Correct(100)
                    );

            expectedException.expect(AssertionError.class);
            expectedException.expectMessage("CompareTo is not consistent with equals!");

            // when:
            ComparableVerifier
                    .forInstances(lesser, equal, greater)
                    .suppressConsistentWithEquals(false)
                    .verify();
        }

        @Test
        public void should_detect_inconsistency_in_BigDecimal() {
            // because BigDecimal processes differently the precision
            // in case of equals and compareTo

            // given:
            final VerificationInstancesCreator<BigDecimal> lesser =
                    VerificationInstancesCreators.from(
                            new BigDecimal("0.0")
                    );
            final VerificationInstancesCreator<BigDecimal> equal =
                    VerificationInstancesCreators.from(
                            new BigDecimal("42.0"),
                            new BigDecimal("42.00") //different precision although the same
                    );
            final VerificationInstancesCreator<BigDecimal> greater =
                    VerificationInstancesCreators.from(
                            new BigDecimal("100.0")
                    );

            expectedException.expect(AssertionError.class);
            expectedException.expectMessage("CompareTo is not consistent with equals!");

            // when:
            ComparableVerifier
                    .forInstances(lesser, equal, greater)
                    .suppressConsistentWithEquals(false)
                    .verify();
        }
    }

    public static class SuppressEqualsToNullReturnsFalse {
        @Rule
        public ExpectedException expectedException = ExpectedException.none();

        @Test
        public void should_allow_Lesser_instances_equal_to_null() {
            // given:
            final VerificationInstancesCreator<Correct> lesser =
                    VerificationInstancesCreators.from(
                            new EqualToNull(0)
                    );
            final VerificationInstancesCreator<Correct> equal =
                    VerificationInstancesCreators.from(
                            new Correct(42)
                    );
            final VerificationInstancesCreator<Correct> greater =
                    VerificationInstancesCreators.from(
                            new Correct(100)
                    );

            // when:
            ComparableVerifier
                    .forInstances(lesser, equal, greater)
                    .suppressEqualsToNullReturnsFalse(true)
                    .verify();
        }

        @Test
        public void should_allow_Equal_instances_equal_to_null() {
            // given:
            final VerificationInstancesCreator<Correct> lesser =
                    VerificationInstancesCreators.from(
                            new Correct(0)
                    );
            final VerificationInstancesCreator<Correct> equal =
                    VerificationInstancesCreators.from(
                            new EqualToNull(42)
                    );
            final VerificationInstancesCreator<Correct> greater =
                    VerificationInstancesCreators.from(
                            new Correct(100)
                    );

            // when:
            ComparableVerifier
                    .forInstances(lesser, equal, greater)
                    .suppressEqualsToNullReturnsFalse(true)
                    .verify();
        }

        @Test
        public void should_allow_Greater_instances_equal_to_null() {
            // given:
            final VerificationInstancesCreator<Correct> lesser =
                    VerificationInstancesCreators.from(
                            new Correct(0)
                    );
            final VerificationInstancesCreator<Correct> equal =
                    VerificationInstancesCreators.from(
                            new Correct(42)
                    );
            final VerificationInstancesCreator<Correct> greater =
                    VerificationInstancesCreators.from(
                            new EqualToNull(100)
                    );

            // when:
            ComparableVerifier
                    .forInstances(lesser, equal, greater)
                    .suppressEqualsToNullReturnsFalse(true)
                    .verify();
        }

        @Test
        public void should_NOT_allow_Lesser_instances_equal_to_null() {
            // given:
            final VerificationInstancesCreator<Correct> lesser =
                    VerificationInstancesCreators.from(
                            new EqualToNull(0)
                    );
            final VerificationInstancesCreator<Correct> equal =
                    VerificationInstancesCreators.from(
                            new Correct(42)
                    );
            final VerificationInstancesCreator<Correct> greater =
                    VerificationInstancesCreators.from(
                            new Correct(100)
                    );

            expectedException.expect(AssertionError.class);
            expectedException.expectMessage("Instance is equal to null!");

            // when:
            ComparableVerifier
                    .forInstances(lesser, equal, greater)
                    .suppressEqualsToNullReturnsFalse(false)
                    .verify();
        }

        @Test
        public void should_NOT_allow_Equal_instances_equal_to_null() {
            // given:
            final VerificationInstancesCreator<Correct> lesser =
                    VerificationInstancesCreators.from(
                            new Correct(0)
                    );
            final VerificationInstancesCreator<Correct> equal =
                    VerificationInstancesCreators.from(
                            new EqualToNull(42)
                    );
            final VerificationInstancesCreator<Correct> greater =
                    VerificationInstancesCreators.from(
                            new Correct(100)
                    );

            expectedException.expect(AssertionError.class);
            expectedException.expectMessage("Instance is equal to null!");

            // when:
            ComparableVerifier
                    .forInstances(lesser, equal, greater)
                    .suppressEqualsToNullReturnsFalse(false)
                    .verify();
        }

        @Test
        public void should_NOT_allow_Greater_instances_equal_to_null() {
            // given:
            final VerificationInstancesCreator<Correct> lesser =
                    VerificationInstancesCreators.from(
                            new Correct(0)
                    );
            final VerificationInstancesCreator<Correct> equal =
                    VerificationInstancesCreators.from(
                            new Correct(42)
                    );
            final VerificationInstancesCreator<Correct> greater =
                    VerificationInstancesCreators.from(
                            new EqualToNull(100)
                    );

            expectedException.expect(AssertionError.class);
            expectedException.expectMessage("Instance is equal to null!");

            // when:
            ComparableVerifier
                    .forInstances(lesser, equal, greater)
                    .suppressEqualsToNullReturnsFalse(false)
                    .verify();
        }
    }

    public static class SuppressExceptionOnCompareToNull {
        @Rule
        public ExpectedException expectedException = ExpectedException.none();

        @Test
        public void should_allow_Lesser_instances_compareTo_null() {
            // given:
            final VerificationInstancesCreator<Correct> lesser =
                    VerificationInstancesCreators.from(
                            new CompareToNull(0)
                    );
            final VerificationInstancesCreator<Correct> equal =
                    VerificationInstancesCreators.from(
                            new Correct(42)
                    );
            final VerificationInstancesCreator<Correct> greater =
                    VerificationInstancesCreators.from(
                            new Correct(100)
                    );

            // when:
            ComparableVerifier
                    .forInstances(lesser, equal, greater)
                    .suppressExceptionOnCompareToNull(true)
                    .verify();
        }

        @Test
        public void should_allow_Equal_instances_compareTo_null() {
            // given:
            final VerificationInstancesCreator<Correct> lesser =
                    VerificationInstancesCreators.from(
                            new Correct(0)
                    );
            final VerificationInstancesCreator<Correct> equal =
                    VerificationInstancesCreators.from(
                            new CompareToNull(42)
                    );
            final VerificationInstancesCreator<Correct> greater =
                    VerificationInstancesCreators.from(
                            new Correct(100)
                    );

            // when:
            ComparableVerifier
                    .forInstances(lesser, equal, greater)
                    .suppressExceptionOnCompareToNull(true)
                    .verify();
        }

        @Test
        public void should_allow_Greater_instances_compareTo_null() {
            // given:
            final VerificationInstancesCreator<Correct> lesser =
                    VerificationInstancesCreators.from(
                            new Correct(0)
                    );
            final VerificationInstancesCreator<Correct> equal =
                    VerificationInstancesCreators.from(
                            new Correct(42)
                    );
            final VerificationInstancesCreator<Correct> greater =
                    VerificationInstancesCreators.from(
                            new CompareToNull(100)
                    );

            // when:
            ComparableVerifier
                    .forInstances(lesser, equal, greater)
                    .suppressExceptionOnCompareToNull(true)
                    .verify();
        }

        @Test
        public void should_NOT_allow_Lesser_instances_compareTo_null() {
            // given:
            final VerificationInstancesCreator<Correct> lesser =
                    VerificationInstancesCreators.from(
                            new CompareToNull(0)
                    );
            final VerificationInstancesCreator<Correct> equal =
                    VerificationInstancesCreators.from(
                            new Correct(42)
                    );
            final VerificationInstancesCreator<Correct> greater =
                    VerificationInstancesCreators.from(
                            new Correct(100)
                    );

            expectedException.expect(AssertionError.class);
            expectedException.expectMessage("CompareTo null should throw an exception!");

            // when:
            ComparableVerifier
                    .forInstances(lesser, equal, greater)
                    .suppressExceptionOnCompareToNull(false)
                    .verify();
        }

        @Test
        public void should_NOT_allow_Equal_instances_compareTo_null() {
            // given:
            final VerificationInstancesCreator<Correct> lesser =
                    VerificationInstancesCreators.from(
                            new Correct(0)
                    );
            final VerificationInstancesCreator<Correct> equal =
                    VerificationInstancesCreators.from(
                            new CompareToNull(42)
                    );
            final VerificationInstancesCreator<Correct> greater =
                    VerificationInstancesCreators.from(
                            new Correct(100)
                    );

            expectedException.expect(AssertionError.class);
            expectedException.expectMessage("CompareTo null should throw an exception!");

            // when:
            ComparableVerifier
                    .forInstances(lesser, equal, greater)
                    .suppressExceptionOnCompareToNull(false)
                    .verify();
        }

        @Test
        public void should_NOT_allow_Greater_instances_compareTo_null() {
            // given:
            final VerificationInstancesCreator<Correct> lesser =
                    VerificationInstancesCreators.from(
                            new Correct(0)
                    );
            final VerificationInstancesCreator<Correct> equal =
                    VerificationInstancesCreators.from(
                            new Correct(42)
                    );
            final VerificationInstancesCreator<Correct> greater =
                    VerificationInstancesCreators.from(
                            new CompareToNull(100)
                    );

            expectedException.expect(AssertionError.class);
            expectedException.expectMessage("CompareTo null should throw an exception!");

            // when:
            ComparableVerifier
                    .forInstances(lesser, equal, greater)
                    .suppressExceptionOnCompareToNull(false)
                    .verify();
        }
    }

    public static class Verify {
        @Rule
        public ExpectedException expectedException = ExpectedException.none();

        @Test
        public void should_pass_for_all_instances() {
            // given:
            final VerificationInstancesCreator<Correct> lesser =
                    VerificationInstancesCreators.from(
                            new Correct(0),
                            new Correct(1),
                            new Correct(2),
                            new Correct(3)
                    );
            final VerificationInstancesCreator<Correct> equal =
                    VerificationInstancesCreators.from(
                            new Correct(42),
                            new Correct(42),
                            new Correct(42)
                    );
            final VerificationInstancesCreator<Correct> greater =
                    VerificationInstancesCreators.from(
                            new Correct(100),
                            new Correct(101),
                            new Correct(102)
                    );

            // when:
            ComparableVerifier
                    .forInstances(lesser, equal, greater)
                    .verify();
        }

        @Test
        public void should_fail_for_incorrectly_specified_instances() {
            // given:
            final VerificationInstancesCreator<Correct> lesser =
                    VerificationInstancesCreators.from(
                            new Correct(0),
                            new Correct(43)
                    );
            final VerificationInstancesCreator<Correct> equal =
                    VerificationInstancesCreators.from(
                            new Correct(42),
                            new Correct(42)
                    );
            final VerificationInstancesCreator<Correct> greater =
                    VerificationInstancesCreators.from(
                            new Correct(1),
                            new Correct(101),
                            new Correct(102)
                    );

            expectedException.expect(AssertionError.class);
            expectedException.expectMessage(CoreMatchers.containsString("are not transitive!"));

            // when:
            ComparableVerifier
                    .forInstances(lesser, equal, greater)
                    .verify();
        }
    }
}

