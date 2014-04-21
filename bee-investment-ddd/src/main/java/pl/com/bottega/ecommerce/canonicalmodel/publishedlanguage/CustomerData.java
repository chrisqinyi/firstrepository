package pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import pl.com.bottega.ddd.annotations.domain.ValueObject;

@ValueObject
@Embeddable
public class CustomerData {
	@Embedded
	@AttributeOverrides({
			  @AttributeOverride(name = "aggregateId", column = @Column(name = "customerId", nullable = false))})
	private AggregateId aggregateId;
	
	private String name;

	@SuppressWarnings("unused")
	private CustomerData(){}
	
	public CustomerData(AggregateId aggregateId, String name) {
		this.aggregateId = aggregateId;
		this.name = name;
	}
	
	public AggregateId getAggregateId() {
		return aggregateId;
	}
	
	public String getName() {
		return name;
	}
}
