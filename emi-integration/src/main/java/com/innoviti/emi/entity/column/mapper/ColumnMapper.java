package com.innoviti.emi.entity.column.mapper;

public interface ColumnMapper<T, S> {
  S mapColumn(T t);
}
