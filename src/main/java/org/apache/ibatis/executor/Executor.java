/**
 *    Copyright 2009-2015 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.executor;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.transaction.Transaction;

/**
 * 执行器 接口
 *    负责维护一级和二级缓存，提供事务管理的相关操作
 *     cacheingexecutor 装饰器模式
 *     baseexecutor 模版方法模式
 * @author Clinton Begin
 */
public interface Executor {

  /**
   * 空的 resultHandler 对象枚举
   */
  ResultHandler NO_RESULT_HANDLER = null;

  /**
   *  更新 or 插入 or delete 有传入的 MappedStatement 的sql所决定
   * @param ms
   * @param parameter
   * @return
   * @throws SQLException
   */
  int update(MappedStatement ms, Object parameter) throws SQLException;

  /**
   * 查询带 resulthandler + cachekey+boundsql
   * @param ms
   * @param parameter
   * @param rowBounds
   * @param resultHandler
   * @param cacheKey
   * @param boundSql
   * @param <E>
   * @return
   * @throws SQLException
   */
  <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, CacheKey cacheKey, BoundSql boundSql) throws SQLException;

  /**
   *  查询 带resulthandler
   * @param ms
   * @param parameter
   * @param rowBounds
   * @param resultHandler
   * @param <E>
   * @return
   * @throws SQLException
   */
  <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler) throws SQLException;

  /**
   * 查询返回值为cursor
   * @param ms
   * @param parameter
   * @param rowBounds
   * @param <E>
   * @return
   * @throws SQLException
   */
  <E> Cursor<E> queryCursor(MappedStatement ms, Object parameter, RowBounds rowBounds) throws SQLException;

  /**
   * 刷入批处理语句
   * @return
   * @throws SQLException
   */
  List<BatchResult> flushStatements() throws SQLException;

  /**
   * 提交事物
   * @param required
   * @throws SQLException
   */
  void commit(boolean required) throws SQLException;

  /**
   * 回滚事物
   * @param required
   * @throws SQLException
   */
  void rollback(boolean required) throws SQLException;

  /**
   * 创建cacheKey对象
   * @param ms
   * @param parameterObject
   * @param rowBounds
   * @param boundSql
   * @return
   */
  CacheKey createCacheKey(MappedStatement ms, Object parameterObject, RowBounds rowBounds, BoundSql boundSql);

  /**
   * 判断是否缓存
   * @param ms
   * @param key
   * @return
   */
  boolean isCached(MappedStatement ms, CacheKey key);

  /**
   * 清除本地缓存
   */
  void clearLocalCache();

  /**
   * 延迟加载
   * @param ms
   * @param resultObject
   * @param property
   * @param key
   * @param targetType
   */
  void deferLoad(MappedStatement ms, MetaObject resultObject, String property, CacheKey key, Class<?> targetType);

  /**
   * 获得事物
   * @return
   */
  Transaction getTransaction();

  /**
   * 关闭事物
   * @param forceRollback
   */
  void close(boolean forceRollback);

  /**
   * 判断事物是否关闭
   * @return
   */
  boolean isClosed();

  /**
   * 设置包装的 executor
   * @param executor
   */
  void setExecutorWrapper(Executor executor);

}
