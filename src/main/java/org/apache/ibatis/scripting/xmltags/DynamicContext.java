/**
 *    Copyright 2009-2019 the original author or authors.
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
package org.apache.ibatis.scripting.xmltags;

import java.util.HashMap;
import java.util.Map;

import ognl.OgnlContext;
import ognl.OgnlRuntime;
import ognl.PropertyAccessor;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;

/**
 * 记录解析动态sql语句产生的sql片段，记录动态sql语句解析结果的容器
 * @author Clinton Begin
 */
public class DynamicContext {

  /**
   * {@link #bindings} _parameter 的键，参数
   */
  public static final String PARAMETER_OBJECT_KEY = "_parameter";
  /**
   * {@link #bindings} _databaseId 的键，数据库编号
   */
  public static final String DATABASE_ID_KEY = "_databaseId";

  static {
    // 初始化ognl的属性访问器
    OgnlRuntime.setPropertyAccessor(ContextMap.class, new ContextAccessor());
  }

  /**
   * 参数上下文
   */
  private final ContextMap bindings;
  /**
   * 在sqlNode解析动态sql时，会将解析后的sql语句片段添加到该属性中保存，最终拼接完整可执行的sql
   */
  private final StringBuilder sqlBuilder = new StringBuilder();
  private int uniqueNumber = 0;

  /**
   *  初始化 bindings集合
   * @param configuration
   * @param parameterObject  运行时用户传入的参数，后续替换#{}占位符的实参
   */
  public DynamicContext(Configuration configuration, Object parameterObject) {
    if (parameterObject != null && !(parameterObject instanceof Map)) {
      // 对于map类型的参数，会创建对应的metaobject类，并封装成 contextMap对象
      MetaObject metaObject = configuration.newMetaObject(parameterObject);
      bindings = new ContextMap(metaObject);
    } else {
      bindings = new ContextMap(null);
    }
    bindings.put(PARAMETER_OBJECT_KEY, parameterObject);
    bindings.put(DATABASE_ID_KEY, configuration.getDatabaseId());
  }

  public Map<String, Object> getBindings() {
    return bindings;
  }

  public void bind(String name, Object value) {
    bindings.put(name, value);
  }

  /**
   * 追加动态sql片段
   * @param sql
   */
  public void appendSql(String sql) {
    sqlBuilder.append(sql);
    sqlBuilder.append(" ");
  }

  /**
   * 获得解析后完整的sql语句e
   * @return
   */
  public String getSql() {
    return sqlBuilder.toString().trim();
  }

  public int getUniqueNumber() {
    return uniqueNumber++;
  }

  /**
   * 上下文的参数集合
   *
   * 增加支持对 parameterMetaObject 属性的访问
   */
  static class ContextMap extends HashMap<String, Object> {
    private static final long serialVersionUID = 2977601501966151582L;

    /**
     * 将用户传入的参数封装
     */
    private MetaObject parameterMetaObject;

    public ContextMap(MetaObject parameterMetaObject) {
      this.parameterMetaObject = parameterMetaObject;
    }

    @Override
    public Object get(Object key) {
      // 如果有对应的key,直接获得
      String strKey = (String) key;
      if (super.containsKey(strKey)) {
        return super.get(strKey);
      }
      // 从运行参数中茶查找对应的属性
      if (parameterMetaObject != null) {
        // issue #61 do not modify the context when reading
        return parameterMetaObject.getValue(strKey);
      }

      return null;
    }
  }

  /**
   * 上下文接口访问器
   */
  static class ContextAccessor implements PropertyAccessor {

    @Override
    public Object getProperty(Map context, Object target, Object name) {
      Map map = (Map) target;

      Object result = map.get(name);
      if (map.containsKey(name) || result != null) {
        return result;
      }

      Object parameterObject = map.get(PARAMETER_OBJECT_KEY);
      if (parameterObject instanceof Map) {
        return ((Map)parameterObject).get(name);
      }

      return null;
    }

    @Override
    public void setProperty(Map context, Object target, Object name, Object value) {
      Map<Object, Object> map = (Map<Object, Object>) target;
      map.put(name, value);
    }

    @Override
    public String getSourceAccessor(OgnlContext arg0, Object arg1, Object arg2) {
      return null;
    }

    @Override
    public String getSourceSetter(OgnlContext arg0, Object arg1, Object arg2) {
      return null;
    }
  }
}