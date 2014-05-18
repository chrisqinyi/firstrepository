package pl.com.bottega.ecommerce.system;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Objects;

import org.apache.commons.beanutils.PropertyUtils;

import com.google.common.base.Predicate;

public class Predicates {
	public static <T> Predicate<T> invoke(final String method) {
		return new Predicate<T>() {
			@Override
			public boolean apply(T input) {
				try {
					input.getClass().getMethod(method).invoke(input);
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException
						| SecurityException e) {
					e.printStackTrace();
					throw new RuntimeException();
				}
				return true;
			}
		};
	}

	public static <T> Predicate<T> invoke(final String method, final Object arg) {
		return new Predicate<T>() {
			@Override
			public boolean apply(T input) {
				try {
					input.getClass().getMethod(method, arg.getClass())
							.invoke(input, arg);
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException
						| SecurityException e) {
					e.printStackTrace();
					throw new RuntimeException();
				}
				return true;
			}
		};
	}

	public static <T> Predicate<T> propertyEquals(final String method,
			final Object arg) {
		return new Predicate<T>() {
			@Override
			public boolean apply(T input) {
				boolean result = false;
				try {
					Object property = PropertyUtils.getProperty(input,
							method);
					result = Objects
							.equals(property, arg);
				} catch (IllegalAccessException | InvocationTargetException
						| NoSuchMethodException e) {
					e.printStackTrace();
					throw new RuntimeException();
				}
				return result;
			}
		};
	}
	public static <T> Predicate<T> propertyEquals(final String method,
			final BigDecimal arg) {
		return new Predicate<T>() {
			@Override
			public boolean apply(T input) {
				boolean result = false;
				try {
					BigDecimal property = (BigDecimal) PropertyUtils.getProperty(input,
							method);
					result = arg.intValue()==property.intValue();
				} catch (IllegalAccessException | InvocationTargetException
						| NoSuchMethodException e) {
					e.printStackTrace();
					throw new RuntimeException();
				}
				return result;
			}
		};
	}
	public static <T> Predicate<T> propertyNotEquals(final String method,
			final Object arg) {
		return new Predicate<T>() {
			@Override
			public boolean apply(T input) {
				boolean result = false;
				try {
					result = !Objects
							.equals(PropertyUtils.getProperty(input,
									method), arg);
				} catch (IllegalAccessException | InvocationTargetException
						| NoSuchMethodException e) {
					e.printStackTrace();
					throw new RuntimeException();
				}
				return result;
			}
		};
	}
}
