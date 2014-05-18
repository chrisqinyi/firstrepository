package pl.com.bottega.ecommerce.system;

import java.util.Iterator;

public class Reduce {
	private Reduce() {
	}

	public static <F, T> T reduce(final Iterable<F> iterable,
			final ReduceFunction<F, T> func) {
		return reduce(iterable, func, func.getDefaultValue());
	}

	public static <F, T> T reduce(final Iterable<F> iterable,
			final ReduceFunction<F, T> func, T origin) {
		for (Iterator iterator = iterable.iterator(); iterator.hasNext();) {
			origin = func.apply((F) (iterator.next()), origin);
		}
		return origin;
	}
	

	
}
