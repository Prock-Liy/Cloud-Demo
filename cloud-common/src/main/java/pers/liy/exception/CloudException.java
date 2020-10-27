package pers.liy.exception;

/**
 * @Author Prock.Liy
 * @Date 2020/10/11 22:37
 * @Description
 **/
public class CloudException extends Exception{

    private static final long serialVersionUID = -6916154462432027437L;

    public CloudException(String message){
        super(message);
    }
}
