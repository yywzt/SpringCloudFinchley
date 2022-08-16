package com.yw.user.sdk.decoder;

import cn.hutool.core.lang.ParameterizedTypeImpl;
import com.yyw.api.model.ResponseData;
import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/16 15:12
 */
public class ResultDecoder implements Decoder {

    private final Decoder decoder;

    public ResultDecoder(Decoder decoder) {
        this.decoder = decoder;
    }

    @Override
    public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
        Method method = response.request().requestTemplate().methodMetadata().method();
        boolean isResponseData = method.getReturnType() != ResponseData.class;
        if (isResponseData) {
            ParameterizedTypeImpl resultType = new ParameterizedTypeImpl(new Type[]{type}, null, ResponseData.class);
            ResponseData<?> result = (ResponseData<?>) this.decoder.decode(response, resultType);
            return result.getData();
        }
        return this.decoder.decode(response, type);
    }
}
