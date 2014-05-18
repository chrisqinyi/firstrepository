package pl.com.bottega.ecommerce.system;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import org.apache.commons.beanutils.PropertyUtils;

import pl.com.bottega.ecommerce.sharedkernel.Money;

import com.beeInvestment.investment.domain.Investment;
import com.google.common.base.Function;

public class Functions {
	public static <T>Function<Object,T> property(final String key,Class<T> clazz){
		return new Function<Object,T>(){
			public T apply(Object input){
				T property=null;
				try {
					property = (T) PropertyUtils.getProperty(input, key);
				} catch (IllegalAccessException | InvocationTargetException
						| NoSuchMethodException e) {
					e.printStackTrace();
					throw new RuntimeException();
				}
				return property;
			}
		};
	}
}
