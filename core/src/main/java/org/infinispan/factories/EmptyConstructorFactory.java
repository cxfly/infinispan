package org.infinispan.factories;

import org.infinispan.commands.RemoteCommandFactory;
import org.infinispan.config.ConfigurationException;
import org.infinispan.factories.annotations.DefaultFactoryFor;
import org.infinispan.factories.scopes.Scope;
import org.infinispan.factories.scopes.Scopes;
import org.infinispan.notifications.cachemanagerlistener.CacheManagerNotifier;
import org.infinispan.remoting.InboundInvocationHandler;
import org.infinispan.transaction.xa.TransactionTable;
import org.infinispan.util.Util;

/**
 * Factory for building global-scope components which have default empty constructors
 *
 * @author Manik Surtani
 * @author <a href="mailto:galder.zamarreno@jboss.com">Galder Zamarreno</a>
 * @since 4.0
 */
@DefaultFactoryFor(classes = {InboundInvocationHandler.class, CacheManagerNotifier.class, RemoteCommandFactory.class, TransactionTable.class})
@Scope(Scopes.GLOBAL)
public class EmptyConstructorFactory extends AbstractComponentFactory implements AutoInstantiableFactory {
   public <T> T construct(Class<T> componentType) {
      try {
         if (componentType.isInterface()) { 
            return componentType.cast(Util.getInstance(componentType.getName() + "Impl"));
         } else {
            return Util.getInstance(componentType);
         }
      }
      catch (Exception e) {
         throw new ConfigurationException("Unable to create component " + componentType, e);
      }

   }
}
