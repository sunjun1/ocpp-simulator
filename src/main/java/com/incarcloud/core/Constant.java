package com.incarcloud.core;

import cn.hutool.core.util.StrUtil;

/**
 * description  常量字段定义
 *
 * @author wangyang
 * @version 1.0
 * @date 2023/11/1 10:15
 */
public interface Constant {

    /**
     * SAtoken缓存用户信息key
     */
    //运营商标记
    String OP_SA_TOKEN_SESSION_KEY = "operatorSAUserInfo";
    String OPERATOR_TOKEN_SESSION_KEY = "OperatorMaintainerSessionInfo";

    String ID_TAG_SPECIAL = "FFFFFFFF";
    String CARD_CR = "CR";

    String CONNECTOR_ID = "#NA";

    /**
     * 超级管理员账号名
     */
    String SUPER_ADMIN = "admin";

    /**
     * 系统超级管理员标记
     */
    Integer ADMIN = 1;
    Integer NOU_ADMIN = 0;

    /**
     * 一级Group名称
     */
    String GROUP_NAME = "Master Group";

    String CPO_GROUP_CONTRACT_PREFIX = "CPO Contract: ";
    String EMSP_GROUP_CONTRACT_PREFIX = "EMSP Contract: ";


    Integer NO_ALL = 1;

    Integer ALL = 0;

    Integer ZERO = 0;
    Integer ONE = 1;

    Integer TWO = 2;

    Integer WIDTH = 300;

    Integer HEIGHT = 300;

    /**
     * 状态码
     */
    interface ApiCode {

        /**
         * 响应码：200-请求成功
         */
        int SUCCESS = 200;

        /**
         * 响应码：400-数据校验异常
         */
        int ERROR_400 = 400;

        /**
         * 响应码：401-权限校验错误
         */
        int ERROR_401 = 401;

        /**
         * 响应码：500-其他异常
         */
        int ERROR_500 = 500;

        /**
         * 响应码：501-请求超限错误
         */
        int ERROR_501 = 501;

        /**
         * 响应码：503-无法解决异常
         */
        int ERROR_503 = 503;

        /**
         * 响应码：505-业务系统异常
         */
        int EXCEPTION = 505;

        /**
         * 响应码：600-FTP异常
         */
        int ERROR_600 = 600;

        /**
         * 响应码：700-Redis异常
         */
        int ERROR_700 = 700;

        /**
         * 响应码：800-登录信息异常，重新登录
         */
        int ERROR_800 = 800;

        /**
         * 响应码：801-异常操作
         */
        int ERROR_801 = 801;

        /**
         * 响应吗：805-Token已被踢下线
         */
        int ERROR_805 = 805;

    }

    interface MongoDbNamespace {
        /**
         * 充电桩上下行数据
         */
        String IOT_DATA = "iot-data";

        /**
         * 充电桩状态数据
         */
        String STATUS_DATA = "status-data";
    }

    /**
     * Redis命名空间
     */
    interface RedisNamespace {
        /**
         * 拼接key
         *
         * @param params 参数
         * @return redis key
         */
        static String appendKey(String... params) {
            return params.length == 0 ? "" : StrUtil.join(":", REDIS_NAMESPACE_PREFIX, (Object) params);
        }


        String REDIS_NAMESPACE_PREFIX = "evcp";

        /**
         * ocpp应答消息类型
         */
        String OCPP_MSG_TYPE = "ocpp_msg_type";

        String OCPP_START_TRANSACTION = "ocpp_start_transaction";

        String OCPI_SESSION_CHARGE_PERIOD = "ocpi_session_charge_period";

        String LATEST = "latest";

        /**
         * OCPI数据
         */
        String OCPI_COMMAND_TYPE = "ocpi_command_type";

        String METER_VALUE_TRANSACTION = "meterValue:transaction";
        /**
         * 订单分时费用
         */
        String ORDER_DETAIL = "order:detail";

        String METER_VALUE_CONNECTOR = "meterValue:connector";


        /**
         * 充电枪状态
         */
        String CONNECTOR_ONLINE = "connector_status";
        /**
         * 充电桩状态
         */
        String PILE_ONLINE = "pile_status";

        /**
         * 密码输入错误次数 -SAAS平台
         */
        String LOGIN_ERROR_NUMBER_ADMIN = "loginErrorNumberAdmin-";
        /**
         * 登录次数
         */
        String LOGIN_ERROR_NUMBER_MERCHANT = "loginErrorNumberMerchant-";

        /**
         * 验证码
         */
        String REDIS_IMG_CODE = "evcp:img_code";

        /**
         * SATOKEN 权限缓存
         */
        String SA_PERMISSION_CACHE = "SA:permission_cache";

        /**
         * SATOKEN 指定用户SAtoken刷新标记
         */
        String SA_PERMISSION_REFRESH = "SA:permission_refresh";

        String CURRENT_CUSTOMER = "SA:Operator_current_customer";

    }

    /**
     * 用户登录操作
     */
    interface UserDealType {

        String USER_LOGIN = "evcp:operator-login:";

        // 禁用
        String DISABLE = "DISABLE";

        // 重置密码
        String RESET_PASSWORD = "RESET_PASSWORD";

        // 修改密码
        String UPDATE_PASSWORD = "UPDATE_PASSWORD";
    }

    /**
     * 状态 ENABLE：启用 DISABLE-禁用
     */
    interface Status {

        Integer ENABLE = 1;

        Integer DISABLE = 0;
    }

    interface Preset {

        Integer PRESET = 1;

        Integer UN_PRESET = 0;
    }

    /**
     * 用户接受邀请的状态：0-Accepted, 1-Invited
     */
    interface Invitation {

        Integer ACCEPTED = 0;

        Integer INVITED = 1;
    }

    /**
     * 语言：0-英文，1-中文简体
     */
    interface Language {

        Integer ENGLISH = 0;

        Integer CHINESE = 1;
    }

    interface CustomerType {

        Integer CPO = 1;

        Integer EMSP = 2;

        Integer CPO_EMSP = 3;
    }

    interface ContractType {

        Integer CPO = 1;

        Integer EMSP = 2;

    }

    /**
     * 文件目录
     */
    interface FilesDirectory {


        /**
         * 网站访问前缀
         */
        String WEBSITE_PREFIX = "/files";

        /**
         * 图片目录
         */
        String PICTURES = "pictures/";

        /**
         * 下载目录
         */
        String DOWNLOAD = "download/";

        /**
         * 自动生成目录
         */
        String AUTOFILE = "autofile/";
    }

    interface EmspTokenType {
        String AD_HOC_USER = "AD_HOC_USER";

        String APP_USER = "APP_USER";

        String OTHER = "OTHER";

        String RFID = "RFID";

    }

    interface EmspTokenWhitelist {
        String ALWAYS = "ALWAYS";

        String ALLOWED = "ALLOWED";

        String ALLOWED_OFFLINE = "ALLOWED_OFFLINE";

        String NEVER = "NEVER";

    }

    interface EmspTokenProfileType {
        String CHEAP = "CHEAP";

        String FAST = "FAST";

        String GREEN = "GREEN";

        String REGULAR = "REGULAR";

    }

}
