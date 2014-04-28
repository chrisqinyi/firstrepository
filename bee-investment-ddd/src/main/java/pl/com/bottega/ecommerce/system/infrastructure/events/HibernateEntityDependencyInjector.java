package pl.com.bottega.ecommerce.system.infrastructure.events;

import java.util.Map;

import javax.inject.Inject;

import org.hibernate.HibernateException;
import org.hibernate.event.spi.MergeEvent;
import org.hibernate.event.spi.MergeEventListener;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.event.spi.PostLoadEvent;
import org.hibernate.event.spi.PostLoadEventListener;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

import pl.com.bottega.ddd.support.domain.DomainEventPublisher;

import com.beeInvestment.application.AccountSavedEvent;
import com.beeInvestment.transaction.domain.Transaction;

@Component
public class HibernateEntityDependencyInjector implements
		PostLoadEventListener, MergeEventListener,PostInsertEventListener,PostUpdateEventListener {
	@Inject
	private AutowireCapableBeanFactory spring;

	@Override
	public void onPostLoad(PostLoadEvent event) {
		this.injectDependency(event.getEntity());
	}

	@Override
	public void onMerge(MergeEvent event) throws HibernateException {
		Object entity = event.getEntity();
		this.injectDependency(entity);
	}

	@Inject
	protected DomainEventPublisher eventPublisher;

	@Override
	public void onMerge(MergeEvent event, Map copiedAlready)
			throws HibernateException {
		this.onMerge(event);
	}

	protected void injectDependency(Object obj) {
		if (obj != null) {
			spring.autowireBean(obj);
		}
	}

	@Override
	public void onPostUpdate(PostUpdateEvent event) {
		
//		Object entity = event.getEntity();
//		if (entity instanceof Transaction) {
//			System.out.println("onPostUpdate");
//			Transaction transaction = (Transaction) entity;
//			this.eventPublisher.publish(new AccountSavedEvent(
//					transaction.getAccount().getAggregateId().getId()));
//		}	
	}

	@Override
	public void onPostInsert(PostInsertEvent event) {
//		Object entity = event.getEntity();
//		if (entity instanceof Transaction) {
//			System.out.println("onPostUpdate");
//			Transaction transaction = (Transaction) entity;
//			this.eventPublisher.publish(new AccountSavedEvent(
//					transaction.getAccount().getAggregateId().getId()));
//		}	
	}

}
