/**
 * Copyright (C) 2017 - 2020 https://github.com/joewee
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bifrost.demo.comm;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据模型
 *
 * @author bifrost
 * @version 1.0.0
 * @Date 2017/10/13 13:27
 */
public class ResultModel extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public ResultModel() {
        put("code", HttpServletResponse.SC_OK);
    }

    @Override
    public ResultModel put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public static ResultModel error() {
        return error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
    }

    public static ResultModel error(String msg) {
        return error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, msg);
    }

    public static ResultModel error(int code, String msg) {
        ResultModel r = new ResultModel();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static ResultModel ok(String msg) {
        ResultModel r = new ResultModel();
        r.put("msg", msg);
        return r;
    }

    public static ResultModel ok(Map<String, Object> map) {
        ResultModel r = new ResultModel();
        r.putAll(map);
        return r;
    }

    public static ResultModel ok() {
        return new ResultModel();
    }
}
