package org.infinispan.objectfilter.impl.hql.predicate;

import java.util.List;

import org.hibernate.hql.ast.spi.predicate.ComparisonPredicate;
import org.hibernate.hql.ast.spi.predicate.InPredicate;
import org.infinispan.objectfilter.impl.syntax.BooleanExpr;
import org.infinispan.objectfilter.impl.syntax.ValueExpr;

/**
 * @author anistor@redhat.com
 * @since 7.0
 */
class FilterInPredicate extends InPredicate<BooleanExpr> {

   private final ValueExpr valueExpr;

   FilterInPredicate(ValueExpr valueExpr, List<Object> values) {
      super(valueExpr.toQueryString(), values);
      this.valueExpr = valueExpr;
   }

   @Override
   public BooleanExpr getQuery() {
      FilterDisjunctionPredicate predicate = new FilterDisjunctionPredicate();

      for (Object element : values) {
         //todo need efficient implementation
         FilterComparisonPredicate eq = new FilterComparisonPredicate(valueExpr, ComparisonPredicate.Type.EQUALS, element);
         predicate.add(eq);
      }

      return predicate.getQuery();
   }
}
