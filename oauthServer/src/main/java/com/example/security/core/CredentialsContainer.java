package com.example.security.core;

/**
 *表示实现对象包含可删除的敏感数据
 * 使用{@code eraseCredentials}方法,实现类在任何内部对象调用此方法，该对象也可以实现此接口
 * 只供内部架构使用。正在编写自己的用户
 *   1.{@code AuthenticationProvider}实现应该创建并返回适当的
 *   2.{@code Authentication}对象，减去任何敏感数据，而不是使用这个接口
 */
public interface CredentialsContainer {
    void eraseCredentials();
}
