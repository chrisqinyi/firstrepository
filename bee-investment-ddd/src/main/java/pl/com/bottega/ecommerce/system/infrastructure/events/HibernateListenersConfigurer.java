package pl.com.bottega.ecommerce.system.infrastructure.events;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;

import org.hibernate.SessionFactory;
import org.hibernate.ejb.HibernateEntityManagerFactory;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.stereotype.Component;
@Component
public class HibernateListenersConfigurer {
    @Resource
    private EntityManagerFactory emf;

    @Resource
    private HibernateEntityDependencyInjector listener;

    @PostConstruct
    public void registerListeners() {
        HibernateEntityManagerFactory hemf = (HibernateEntityManagerFactory) emf;
        SessionFactory sf = hemf.getSessionFactory();
        EventListenerRegistry registry = ((SessionFactoryImpl) sf).getServiceRegistry().getService(
                EventListenerRegistry.class);
        registry.getEventListenerGroup(EventType.POST_LOAD).appendListener(listener);
        registry.getEventListenerGroup(EventType.MERGE).appendListener(listener);
    }
}
