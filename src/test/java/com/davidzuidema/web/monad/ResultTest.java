package com.davidzuidema.web.monad;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.function.Function;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ResultTest {

	Function<Integer, Integer> addOne = i -> i + 1;
	Function<Integer, Integer> addThree = i -> i + 3;
	Function<Integer, Result<Integer>> addOneM = i -> Result.of(i + 1);
	Function<Result<Integer>, Result<Integer>> id = Function.identity();

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
}
