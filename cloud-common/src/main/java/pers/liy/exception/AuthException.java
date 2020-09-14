package pers.liy.exception;

/**
 * @Author Prock.Liy
 * @Date 2020/9/14 22:51
 * @Description 自定义异常类
 **/
public class AuthException extends Exception{

    private static final long serialVersionUID = -6916154462432027437L;

    public AuthException(String message){
        super(message);
    }
}
