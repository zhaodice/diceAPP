// ILoginCallbak.aidl
package org.mirai.zhao.dice;

// Declare any non-default types here with import statements

interface ILoginCallbak {
    void loginSuccess(long botId);
    void loginFailure(String reason);
}