package com.mint.common.hibernate3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.Type;

public class Finder {
    private StringBuilder hqlBuilder;
    private List<String> params;
    private List<Object> values;
    private List<Type> types;
    private List<String> paramsList;
    private List<Collection<Object>> valuesList;
    private List<Type> typesList;
    private List<String> paramsArray;
    private List<Object[]> valuesArray;
    private List<Type> typesArray;
    private int firstResult = 0;

    private int maxResults = 0;

    private boolean cacheable = false;
    public static final String ROW_COUNT = "select count(*) ";
    public static final String FROM = "from";
    public static final String DISTINCT = "distinct";
    public static final String HQL_FETCH = "fetch";
    public static final String ORDER_BY = "order";

    protected Finder() {
        this.hqlBuilder = new StringBuilder();
    }

    protected Finder(String hql) {
        this.hqlBuilder = new StringBuilder(hql);
    }

    public static Finder create() {
        return new Finder();
    }

    public static Finder create(String hql) {
        return new Finder(hql);
    }

    public Finder append(String hql) {
        this.hqlBuilder.append(hql);
        return this;
    }

    public String getOrigHql() {
        return this.hqlBuilder.toString();
    }

    public String getRowCountHql() {
        String hql = this.hqlBuilder.toString();

        int fromIndex = hql.toLowerCase().indexOf("from");
        String projectionHql = hql.substring(0, fromIndex);

        hql = hql.substring(fromIndex);
        String rowCountHql = hql.replace("fetch", "");

        int index = rowCountHql.indexOf("order");
        if (index > 0) {
            rowCountHql = rowCountHql.substring(0, index);
        }
        return wrapProjection(projectionHql) + rowCountHql;
    }

    public int getFirstResult() {
        return this.firstResult;
    }

    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }

    public int getMaxResults() {
        return this.maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    public boolean isCacheable() {
        return this.cacheable;
    }

    public void setCacheable(boolean cacheable) {
        this.cacheable = cacheable;
    }

    public Finder setParam(String param, Object value) {
        return setParam(param, value, null);
    }

    public Finder setParam(String param, Object value, Type type) {
        getParams().add(param);
        getValues().add(value);
        getTypes().add(type);
        return this;
    }

    public Finder setParams(Map<String, Object> paramMap) {
        for (Map.Entry entry : paramMap.entrySet()) {
            setParam((String) entry.getKey(), entry.getValue());
        }
        return this;
    }

    public Finder setParamList(String name, Collection<Object> vals, Type type) {
        getParamsList().add(name);
        getValuesList().add(vals);
        getTypesList().add(type);
        return this;
    }

    public Finder setParamList(String name, Collection<Object> vals) {
        return setParamList(name, vals, null);
    }

    public Finder setParamList(String name, Object[] vals, Type type) {
        getParamsArray().add(name);
        getValuesArray().add(vals);
        getTypesArray().add(type);
        return this;
    }

    public Finder setParamList(String name, Object[] vals) {
        return setParamList(name, vals, null);
    }

    public Query setParamsToQuery(Query query) {
        if (this.params != null) {
            for (int i = 0; i < this.params.size(); i++) {
                if (this.types.get(i) == null)
                    query.setParameter((String) this.params.get(i), this.values.get(i));
                else {
                    query.setParameter((String) this.params.get(i), this.values.get(i),
                            (Type) this.types.get(i));
                }
            }
        }
        if (this.paramsList != null) {
            for (int i = 0; i < this.paramsList.size(); i++) {
                if (this.typesList.get(i) == null)
                    query
                            .setParameterList((String) this.paramsList.get(i),
                                    (Collection) this.valuesList.get(i));
                else {
                    query.setParameterList((String) this.paramsList.get(i),
                            (Collection) this.valuesList.get(i), (Type) this.typesList.get(i));
                }
            }
        }
        if (this.paramsArray != null) {
            for (int i = 0; i < this.paramsArray.size(); i++) {
                if (this.typesArray.get(i) == null)
                    query.setParameterList((String) this.paramsArray.get(i),
                            (Object[]) this.valuesArray.get(i));
                else {
                    query.setParameterList((String) this.paramsArray.get(i),
                            (Object[]) this.valuesArray.get(i), (Type) this.typesArray.get(i));
                }
            }
        }
        return query;
    }

    public Query createQuery(Session s) {
        Query query = setParamsToQuery(s.createQuery(getOrigHql()));
        if (getFirstResult() > 0) {
            query.setFirstResult(getFirstResult());
        }
        if (getMaxResults() > 0) {
            query.setMaxResults(getMaxResults());
        }
        if (isCacheable()) {
            query.setCacheable(true);
        }
        return query;
    }

    private String wrapProjection(String projection) {
        if (projection.indexOf("select") == -1) {
            return "select count(*) ";
        }
        return projection.replace("select", "select count(") + ") ";
    }

    private List<String> getParams() {
        if (this.params == null) {
            this.params = new ArrayList();
        }
        return this.params;
    }

    private List<Object> getValues() {
        if (this.values == null) {
            this.values = new ArrayList();
        }
        return this.values;
    }

    private List<Type> getTypes() {
        if (this.types == null) {
            this.types = new ArrayList();
        }
        return this.types;
    }

    private List<String> getParamsList() {
        if (this.paramsList == null) {
            this.paramsList = new ArrayList();
        }
        return this.paramsList;
    }

    private List<Collection<Object>> getValuesList() {
        if (this.valuesList == null) {
            this.valuesList = new ArrayList();
        }
        return this.valuesList;
    }

    private List<Type> getTypesList() {
        if (this.typesList == null) {
            this.typesList = new ArrayList();
        }
        return this.typesList;
    }

    private List<String> getParamsArray() {
        if (this.paramsArray == null) {
            this.paramsArray = new ArrayList();
        }
        return this.paramsArray;
    }

    private List<Object[]> getValuesArray() {
        if (this.valuesArray == null) {
            this.valuesArray = new ArrayList();
        }
        return this.valuesArray;
    }

    private List<Type> getTypesArray() {
        if (this.typesArray == null) {
            this.typesArray = new ArrayList();
        }
        return this.typesArray;
    }

    public static void main(String[] args) {
        Finder find =
                create("select distinct p FROM BookType join fetch p");
        System.out.println(find.getRowCountHql());
        System.out.println(find.getOrigHql());
    }
}

