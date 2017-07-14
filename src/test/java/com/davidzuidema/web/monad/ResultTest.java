package com.davidzuidema.web.monad;

import static com.davidzuidema.web.monad.ResultMatchers.failedAndItsError;
import static com.davidzuidema.web.monad.ResultMatchers.succeededAndItsValue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.function.Function;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ResultTest {

	@Mock
	Function<Integer, Result<Integer>> firstM;

	@Mock
	Function<Integer, Result<Integer>> secondM;

	@Mock
	Function<Integer, Integer> second;

	Function<Integer, Integer> addOne = i -> i + 1;
	Function<Integer, Integer> addThree = i -> i + 3;
	Function<Integer, Result<Integer>> addOneM = i -> Result.of(i + 1);
	Function<Integer, Result<Integer>> addThreeM = i -> Result.of(i + 3);
	Function<Result<Integer>, Result<Integer>> id = Function.identity();

	/*
	 * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * Monad and Functor Law Tests
	 * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */

	/**
	 * Functor Identity Law
	 * 
	 * haskell:
	 * fmap id == id
	 */
	@Test
	public void result_shouldSatisfyTheFunctorLaw_identity() throws Exception {
		Result<Integer> expectedResult = Result.of(1);
		Result<Integer> idMappedResult = expectedResult.map(x -> x);
		Result<Integer> idAppliedResult = id.apply(expectedResult);

		assertThat(idMappedResult, is(equalTo(idAppliedResult)));
	}

	/**
	 * Functor Composition Law
	 * 
	 * haskell:
	 * fmap (f . g) == fmap f . fmap g
	 */
	@Test
	public void result_shouldSatisfyTheFunctorLaw_composition() throws Exception {
		Result<Integer> result = Result.of(1);
		Result<Integer> composingMaps = result.map(addOne).map(addThree);
		Result<Integer> mappingComposition = result.map(addOne.compose(addThree));

		assertThat(composingMaps, is(equalTo(mappingComposition)));
	}

	/**
	 * Monad Left Identity Law
	 * 
	 * haskell:
	 * return x >>= f = f x
	 */
	@Test
	public void result_shouldSatisfyTheMonadLaw_leftIdentity() throws Exception {
		Result<Integer> flatMapResult = Result.of(1).flatMap(addOneM);
		Result<Integer> directApplyResult = addOneM.apply(1);

		assertThat(flatMapResult, is(equalTo(directApplyResult)));
	}

	/**
	 * Monad Right Identity Law
	 * 
	 * haskell:
	 * m >>= return = m
	 */
	@Test
	public void result_shouldSatisfyTheMonadLaw_rightIdentity() throws Exception {
		Result<Integer> ofResult = Result.of(1);
		Result<Integer> flatMapOfResult = Result.of(1).flatMap(Result::of);

		assertThat(flatMapOfResult, is(equalTo(ofResult)));
	}

	/**
	 * Monad Associativity Law
	 * 
	 * haskell:
	 * m >>= (\x -> f x >>= g) = (m >>= f) >>= g
	 */
	@Test
	public void result_shouldSatisfyTheMonadLaw_associativity() throws Exception {
		Result<Integer> m = Result.of(1);
		Result<Integer> applyThenChainResult = m.flatMap(x -> addOneM.apply(x).flatMap(addThreeM));
		Result<Integer> chainResult = m.flatMap(addOneM).flatMap(addThreeM);

		assertThat(chainResult, is(equalTo(applyThenChainResult)));
	}

	/*
	 * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * Result API Unit Tests
	 * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */

	@Test
	public void succeeded_withValue_returnsTrue() throws Exception {
		assertThat(Result.of(1).succeeded(), is(true));
	}

	@Test
	public void succeeded_withError_returnsFalse() throws Exception {
		assertThat(Result.error("divide by zero").succeeded(), is(false));
	}

	@Test
	public void failed_withValue_returnsFalse() throws Exception {
		assertThat(Result.of(1).failed(), is(false));
	}

	@Test
	public void failed_withError_returnsTrue() throws Exception {
		assertThat(Result.error("divide by zero").failed(), is(true));
	}

	@Test
	public void extract_withValue_returnsValue() throws Exception {
		assertThat(Result.success(1).extract(), is(1));
	}

	@Test(expected = IllegalStateException.class)
	public void extract_withError_throwsException() throws Exception {
		Result.error("Data Access Violation").extract();
	}

	@Test
	public void error_withError_returnsError() throws Exception {
		assertThat(Result.error("Record Not Found").error(), is("Record Not Found"));
	}

	@Test(expected = IllegalStateException.class)
	public void error_withValue_throwsException() throws Exception {
		Result.success("Data Access Violation").error();
	}

	/*
	 * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * Composition Tests
	 * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */

	@Test
	public void flatMap_chainingSuccessfulOperations_appliesAllFunctions() throws Exception {
		when(firstM.apply(1)).thenReturn(Result.success(2));
		when(secondM.apply(2)).thenReturn(Result.success(3));

		Result<Integer> calculation = Result
				.of(1)
				.flatMap(firstM)
				.flatMap(secondM);

		verify(firstM).apply(1);
		verify(secondM).apply(2);
		assertThat(calculation, succeededAndItsValue(is(3)));
	}

	@Test
	public void flatMap_chainingUpstreamFailingOperations_doesNotApplyDownstreamFunctions() throws Exception {
		when(firstM.apply(1)).thenReturn(Result.error("problem"));

		Result<Integer> calculation = Result
				.of(1)
				.flatMap(firstM)
				.flatMap(secondM);

		verify(firstM).apply(1);
		verifyZeroInteractions(secondM);
		assertThat(calculation, failedAndItsError(is("problem")));
	}

	@Test
	public void map_chainingSuccessfulOperations_appliesAllFunctions() throws Exception {
		when(firstM.apply(1)).thenReturn(Result.success(2));
		when(second.apply(2)).thenReturn(3);

		Result<Integer> calculation = Result
				.of(1)
				.flatMap(firstM)
				.map(second);

		verify(firstM).apply(1);
		verify(second).apply(2);
		assertThat(calculation, succeededAndItsValue(is(3)));
	}

	@Test
	public void map_chainingUpstreamFailingOperations_doesNotApplyDownstreamFunctions() throws Exception {
		when(firstM.apply(1)).thenReturn(Result.error("problem"));

		Result<Integer> calculation = Result
				.of(1)
				.flatMap(firstM)
				.map(second);

		verify(firstM).apply(1);
		verifyZeroInteractions(second);
		assertThat(calculation, failedAndItsError(is("problem")));
	}
}
