package pl.com.bottega.ecommerce.system;

import java.util.Collection;

import pl.com.bottega.ecommerce.sharedkernel.Money;

import com.google.common.base.Function;

public class ReduceFunctions {
	public static ReduceFunction<Money, Money> sum() {
		return new ReduceFunction<Money, Money>() {
			private Money defaultValue = Money.ZERO;
			public Money getDefaultValue() {
				return defaultValue;
			}
			public Money apply(Money person, Money origin) {
				return person.add(origin == null ? Money.ZERO : origin);
			}
		};
	}
	public static <F,T> ReduceFunction<F, Collection<? super T>> add(final Function<F,T> function) {
		return new ReduceFunction<F, Collection<? super T>>() {
			public Collection<? super T> getDefaultValue() {
				return null;
			}
			public Collection<? super T> apply(F person, Collection<? super T> origin) {
				origin.add(function.apply(person));
				return origin;
			}
		};
	}
}
