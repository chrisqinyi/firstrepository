package pl.com.bottega.ecommerce.system;

public interface ReduceFunction<F,T> {
	T getDefaultValue();
    T apply(F currentElement, T origin);
}
