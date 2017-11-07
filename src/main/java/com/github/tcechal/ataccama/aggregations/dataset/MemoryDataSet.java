package com.github.tcechal.ataccama.aggregations.dataset;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.github.tcechal.ataccama.aggregations.AggregateFn;
import com.github.tcechal.ataccama.aggregations.DataSet;
import com.github.tcechal.ataccama.aggregations.TransformFn;


public final class MemoryDataSet<T> implements DataSet<T> {

    private final List<T> data = new ArrayList<>();


    public static <T> DataSet<T> create(Iterable<T> source) {

        return new MemoryDataSet<>(source);
    }

    private MemoryDataSet(Iterable<T> source) {

        source.forEach(data::add);
    }

    private <R> MemoryDataSet(Iterable<R> source, TransformFn<R, T> transformFn) {

        source.forEach(r -> data.add(transformFn.transform(r)));
    }

    @Override
    public <R> DataSet<R> transform(TransformFn<T, R> transformFn) {

        return new MemoryDataSet<>(this, transformFn);
    }

    @Override
    public <R> R aggregate(AggregateFn<T, R> aggregateFn) {

        return aggregateFn.aggregate(this);
    }

    @Override
    public Iterator<T> iterator() {

        return data.iterator();
    }
}