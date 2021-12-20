// IConsoleService.aidl
package org.mirai.zhao.dice;
import org.mirai.zhao.dice.ILogCallback;
import org.mirai.zhao.dice.ILoginCallbak;
// Declare any non-default types here with import statements

interface IConsoleService {
    String getLogs();
    void commitVerification(String token);
    byte[] getCaptchaData();
    void loginBot(String qq, String password, String setProtocol,ILoginCallbak callback);
    void registLogCallback(String name,ILogCallback callback);
    void unRegistLogCallback(String name);
    void exitAccount(String qq);
}