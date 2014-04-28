package com.beeInvestment.payment.domain;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import com.google.common.base.Predicate;

public class GuavaSet<T> implements Set<T> {
private Set<T> unfiltered;
private Set<T> filtered;
public GuavaSet(Set<T> unfiltered,Predicate<T> predicate){
	
}
public boolean add(T e) {
	return unfiltered.add(e);
}
public boolean remove(Object o) {
	return unfiltered.remove(o);
}
public boolean addAll(Collection<? extends T> c) {
	return unfiltered.addAll(c);
}
public boolean retainAll(Collection<?> c) {
	return unfiltered.retainAll(c);
}
public boolean removeAll(Collection<?> c) {
	return unfiltered.removeAll(c);
}
public void clear() {
	unfiltered.clear();
}
public int size() {
	return filtered.size();
}
public boolean isEmpty() {
	return filtered.isEmpty();
}
public boolean contains(Object o) {
	return filtered.contains(o);
}
public Iterator<T> iterator() {
	return filtered.iterator();
}
public Object[] toArray() {
	return filtered.toArray();
}
public <T> T[] toArray(T[] a) {
	return filtered.toArray(a);
}
public boolean containsAll(Collection<?> c) {
	return filtered.containsAll(c);
}
public boolean equals(Object o) {
	return filtered.equals(o);
}
public int hashCode() {
	return filtered.hashCode();
}

}
