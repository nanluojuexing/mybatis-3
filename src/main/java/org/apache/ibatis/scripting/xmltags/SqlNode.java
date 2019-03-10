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
package org.apache.ibatis.scripting.xmltags;

/**
 * 这里是组合模式
 *
 *    sqlNode 是 抽象组件的角色
 *      MixSqlNode 树枝节点角色
 *      TextSqlNode 树叶节点角色
 *
 * 解析对应的动态 sql节点 （实现类）
 * @author Clinton Begin
 */
public interface SqlNode {
  /**
   *  根据用户传入的实参，参数解析该 sqlNode所记录的动态SQL节点，并调用 DynamicContext.appendSql() 方法解析后的sql片段追加到
   *    DynamicContext.sqlBuilder 中保存。当所有的节点解析完成后，获取动态sql
   * @param context
   * @return
   */
  boolean apply(DynamicContext context);
}
