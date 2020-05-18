package com.demo.springboot.serviceImpl;

import com.demo.springboot.constant.Constant;
import com.demo.springboot.exception.ErrorEnum;
import com.demo.springboot.exception.ResultReturnException;
import com.demo.springboot.service.RedisService;
import com.demo.springboot.service.TokenService;
import com.demo.springboot.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author baiyu
 * @data 2020-05-18 15:26
 */
@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private RedisService redisService;


    /**
     * 创建token
     *
     * @return
     */
    @Override
    public String createToken() {
        String uuid = UuidUtil.getUUID();
        // String str = RandomUtil.randomUUID();
        // StrBuilder token = new StrBuilder();
        StringBuilder token = new StringBuilder();
        try {
            // token.append(Constant.Redis.TOKEN_PREFIX).append(str);
            token.append(Constant.Redis.TOKEN_PREFIX).append(uuid);
            redisService.setEx(token.toString(), token.toString(),10000L);
            boolean notEmpty = !StringUtils.isEmpty(token.toString());
            if (notEmpty) {
                return token.toString();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }


    /**
     * 检验token
     *
     * @param request
     * @return
     */
    @Override
    public boolean checkToken(HttpServletRequest request) throws Exception {

        String token = request.getHeader(Constant.TOKEN_NAME);
        if (StringUtils.isEmpty(token)) {// header中不存在token
            token = request.getParameter(Constant.TOKEN_NAME);
            if (StringUtils.isEmpty(token)) {// parameter中也不存在token
                throw new ResultReturnException(ErrorEnum.COMMON_PARAMS_ERR);
                // throw new ServiceException(Constant.ResponseCode.ILLEGAL_ARGUMENT, 100);
            }
        }

        if (!redisService.exists(token)) {
            throw new ResultReturnException(ErrorEnum.COMMON_REPETITIVE_OPERATION);
            // throw new ServiceException(Constant.ResponseCode.REPETITIVE_OPERATION, 200);
        }

        boolean remove = redisService.remove(token);
        if (!remove) {
            throw new ResultReturnException(ErrorEnum.COMMON_REPETITIVE_OPERATION);
            // throw new ServiceException(Constant.ResponseCode.REPETITIVE_OPERATION, 200);
        }
        return true;
    }
}
