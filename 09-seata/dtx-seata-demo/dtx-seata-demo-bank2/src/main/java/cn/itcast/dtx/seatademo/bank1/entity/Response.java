package cn.itcast.dtx.seatademo.bank1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response<T>
{
    private Integer code;
    private String  message;
    private T       data;

    public Response(Integer code, String message)
    {
        this(code,message,null);
    }
}
