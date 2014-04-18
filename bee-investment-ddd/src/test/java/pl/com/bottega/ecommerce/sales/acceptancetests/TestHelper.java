package pl.com.bottega.ecommerce.sales.acceptancetests;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class TestHelper {
private String customerId;

public String getCustomerId() {
	return customerId;
}

public void setCustomerId(String customerId) {
	this.customerId = customerId;
}
private String targetId;

public String getTargetId() {
	return targetId;
}

public void setTargetId(String targetId) {
	this.targetId = targetId;
}
}
