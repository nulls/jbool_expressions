package com.bpodgursky.jbool_expressions;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.bpodgursky.jbool_expressions.rules.Rule;
import com.bpodgursky.jbool_expressions.rules.RulesHelper;

public class Not<K> extends Expression<K> {
  public static final String EXPR_TYPE = "not";
  private String cachedStringRepresentation = null;

  private final Expression<K> e;

  private Not(Expression<K> e) {
    this.e = e;
  }

  public Expression<K> getE() {
    return e;
  }

  public String toString() {
    if (cachedStringRepresentation == null) {
      cachedStringRepresentation = "!" + e;
    }
    return cachedStringRepresentation;
  }

  @Override
  public Expression<K> apply(List<Rule<?, K>> rules) {
    Expression<K> e = RulesHelper.applyAll(this.e, rules);

    if(e != this.e){
      return new Not<K>(e);
    }

    return this;
  }

  @Override
  public List<Expression<K>> getChildren() {
    return Collections.singletonList(e);
  }

  @Override
  public Expression<K> sort(Comparator<Expression> comparator) {
    return Not.of(e.sort(comparator));
  }

  public static <K> Not<K> of(Expression<K> e) {
    return new Not<K>(e);
  }

  @Override
  public String getExprType() {
    return EXPR_TYPE;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Not<?> not = (Not<?>)o;
    return Objects.equals(e, not.e);
  }

  @Override
  public int hashCode() {
    return Objects.hash(e);
  }

  public Set<K> getAllK() {
    return e.getAllK();
  }

  @Override
  public void collectK(Set<K> set, int limit) {
    e.collectK(set, limit);
  }

  public Expression<K> replaceVars(Map<K, Expression<K>> m) {
    return of(e.replaceVars(m));
  }
}
