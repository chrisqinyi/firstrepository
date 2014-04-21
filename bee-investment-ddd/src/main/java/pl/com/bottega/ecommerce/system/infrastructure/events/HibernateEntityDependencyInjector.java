package pl.com.bottega.ecommerce.system.infrastructure.events;

import java.util.Map;

import javax.inject.Inject;

import org.hibernate.HibernateException;
import org.hibernate.event.spi.MergeEvent;
import org.hibernate.event.spi.MergeEventListener;
import org.hibernate.event.spi.PostLoadEvent;
import org.hibernate.event.spi.PostLoadEventListener;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;
@Component
public class HibernateEntityDependencyInjector
implements PostLoadEventListener, MergeEventListener {
	@Inject
	private AutowireCapableBeanFactory spring;
@Override
public void onPostLoad(PostLoadEvent event) {
this.injectDependency(event.getEntity());
}
@Override
public void onMerge(MergeEvent event) throws HibernateException {
this.injectDependency(event.getEntity());
}
@Override
public void onMerge(MergeEvent event, Map copiedAlready)
    throws HibernateException {
this.injectDependency(event.getEntity());
}
protected void injectDependency(Object obj) {
if (obj != null) {
	spring.autowireBean(obj);
}
}

}
