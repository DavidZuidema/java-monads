package com.davidzuidema.web.monad;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class ResultMatchers {
	public static <A> Matcher<Result<A>> succeededAndItsValue(Matcher<A> successValueMatcher) {
		return new TypeSafeDiagnosingMatcher<Result<A>>() {

			@Override
			public void describeTo(Description description) {
				description
						.appendText("A Successful Result<> whose value")
						.appendText(" ")
						.appendDescriptionOf(successValueMatcher);
			}

			@Override
			protected boolean matchesSafely(Result<A> item, Description mismatch) {
				if (item.failed()) {
					mismatch
							.appendText("was a Failed Result<> whose error message is \"")
							.appendText(item.error())
							.appendText("\"");
					return false;
				}
				A successValue = item.extract();
				if (!successValueMatcher.matches(successValue)) {
					mismatch
							.appendText("was a Successful Result<> whose value")
							.appendText(" ");
					successValueMatcher.describeMismatch(successValue, mismatch);
					return false;
				}
				return true;
			}

		};
	}

	public static <A> Matcher<Result<A>> failedAndItsError(Matcher<String> errorMessageMatcher) {
		return new TypeSafeDiagnosingMatcher<Result<A>>() {

			@Override
			public void describeTo(Description description) {
				description
						.appendText("A Failed Result<> whose error message")
						.appendText(" ")
						.appendDescriptionOf(errorMessageMatcher);
			}

			@Override
			protected boolean matchesSafely(Result<A> item, Description mismatch) {
				if (item.succeeded()) {
					mismatch
							.appendText("was a Successful Result<> whose value is")
							.appendText(" ")
							.appendValue(item.extract());
					return false;
				}
				String errorMessage = item.error();
				if (!errorMessageMatcher.matches(errorMessage)) {
					mismatch
							.appendText("was a Failed Result<> whose error message")
							.appendText(" ");
					errorMessageMatcher.describeMismatch(errorMessage, mismatch);
					return false;
				}
				return true;
			}

		};
	}
}
