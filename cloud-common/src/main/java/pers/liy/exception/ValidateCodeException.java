package pers.liy.exception;

/**
 * @Author Prock.Liy
 * @Date 2020/10/11 21:22
 * @Description
 **/
public class ValidateCodeException extends Exception {

    private static final long serialVersionUID = 7514854456967620043L;

    public ValidateCodeException(String message) {
        super(message);
    }
}
