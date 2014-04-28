package pl.com.bottega.ecommerce.sales.acceptancetests;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class TestHelper {
private String customerAId;
private String customerBId;



public String getCustomerBId() {
	return customerBId;
}

public void setCustomerBId(String customerBId) {
	this.customerBId = customerBId;
}

public String getTarget2Id() {
	return target2Id;
}

public void setTarget2Id(String target2Id) {
	this.target2Id = target2Id;
}

public String getTarget3Id() {
	return target3Id;
}

public void setTarget3Id(String target3Id) {
	this.target3Id = target3Id;
}

public String getCustomerAId() {
	return customerAId;
}

public void setCustomerAId(String customerAId) {
	this.customerAId = customerAId;
}
private String target1Id;
private String target2Id;
private String target3Id;
public String getTarget1Id() {
	return target1Id;
}

public void setTarget1Id(String target1Id) {
	this.target1Id = target1Id;
}
}
