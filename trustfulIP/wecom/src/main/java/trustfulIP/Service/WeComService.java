package trustfulIP.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import trustfulIP.Dto.WeComUser;
import trustfulIP.Dto.entity.Messagecrop;
import trustfulIP.Dto.entity.Messagecropuser;
import trustfulIP.Dto.entity.Messagestore;
import trustfulIP.Dto.vo.CompanyVO;
import trustfulIP.Dto.vo.MessageHistoryVO;
import trustfulIP.Dto.vo.UserVO;
import trustfulIP.mapper.MessagecropMapper;
import trustfulIP.mapper.MessagecropuserMapper;
import trustfulIP.mapper.MessagestoreMapper;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author KitenLee
 * * @date 2025/12/24
 */
@Service
public class WeComService {
    @Value("${wecom.corp-id}")
    private String corpId;

    @Value("${wecom.corp-secret}")
    private String corpSecret;

    @Value("${wecom.agent-id}")
    private String agentId;

    @Autowired
    private  RedisService redisService;

    @Autowired
    private MessagestoreMapper messagestoreMapper;
    @Autowired
    private MessagecropMapper messageCropMapper;
    @Autowired
    private MessagecropuserMapper messageCropUserMapper;

    private final RestTemplate restTemplate = new RestTemplate();

    /** 获取 access_token */
    public String getAccessToken() {
        //查看本地是否存在缓存，如果没有，执行获取操作
        String access_token = redisService.get("wecom:accesstoken");
        if(StringUtils.hasText(access_token)){
            System.out.println("目前本地以及储存access_token: "+access_token);
            return access_token;
        }

        String url = String.format(
                "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=%s&corpsecret=%s",
                corpId, corpSecret
        );

        Map res = restTemplate.getForObject(url, Map.class);
        access_token =  (String) res.get("access_token");

        //缓存结果，至本地
        redisService.setx("wecom:accesstoken",access_token,7150);
        System.out.println("已将accesstoken："+access_token+"存入缓存");
        return access_token;
    }

    /**todo 返回全部的上下游列表*/
    /**
     * 返回值：
     *         {
     *             "chain_id": "chainid1",
     *             "chain_name": "能源供应链"
     *         },
     *         {
     *             "chain_id": "chainid2",
     *             "chain_name": "原材料供应链"
     *         }
     * */
    public JsonNode getChainList() {
        String access_token = getAccessToken();
        String url = String.format(
                "https://qyapi.weixin.qq.com/cgi-bin/corpgroup/corp/get_chain_list?access_token=%s",
                access_token
        );

        Map res = restTemplate.getForObject(url, Map.class);

        System.out.println("目前得到信息是"+ res);
        return (JsonNode) res.get("access_token");
    }

    /**todo 获取企业上下游通讯录分组详情*/
    /**
     * body值：
     * {
     *     "chain_id":"Chxxxxxx",
     *     "groupid":1
     * }
     *
     *
     * 返回值
     *  [
     *         {
     *             "groupid": 2,
     *             "group_name": "一级经销商",
     *             "parentid": 1,
     *             "order": 1
     *         },
     *         {
     *             "groupid": 3,
     *             "group_name": "二级经销商",
     *             "parentid": 2,
     *             "order": 3
     *         }
     *     ]
     * */
    public String getChainGroup() {
        String url = String.format(
                "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=%s&corpsecret=%s",
                corpId, corpSecret
        );

        Map res = restTemplate.getForObject(url, Map.class);

        System.out.println("目前得到toekn"+ res.get("access_token"));
        return (String) res.get("access_token");
    }


    /**todo 企业上下游通讯录分组下的企业详情列表*/

    /**
     * body值：
     * {
     *     "chain_id":"Chxxxxxx",
     *     "groupid":1,
     *     "need_pending":false,
     *     "cursor": "",
     *     "limit": 0
     * }
     * 返回值：
     * [
     *         {
     *             "groupid": 2,
     *             "corpid": "wwxxxx",
     *             "corp_name":"美馨粮油公司",
     *             "custom_id":"custom_id",
     *             "invite_userid":"zhangsan",
     *             "pending_corpid":"wwxxxx",
     *             "is_joined":1
     *         },
     * ]
     *
     * */
    public String getChainCorpinfoList() {
        String url = String.format(
                "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=%s&corpsecret=%s",
                corpId, corpSecret
        );

        Map res = restTemplate.getForObject(url, Map.class);

        System.out.println("目前得到toekn"+ res.get("access_token"));
        return (String) res.get("access_token");
    }

    /**todo 企业上下游通讯录分组下的企业详情列表*/
    public String getPartnerAgent() {
        String url = String.format(
                "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=%s&corpsecret=%s",
                corpId, corpSecret
        );

        Map res = restTemplate.getForObject(url, Map.class);

        System.out.println("目前得到toekn"+ res.get("access_token"));
        return (String) res.get("access_token");
    }



    /**获取应用共享信息*/
    public String getAppShareInfo() {
        String access_token = getAccessToken();
        String url = String.format(
                "https://qyapi.weixin.qq.com/cgi-bin/corpgroup/corp/list_app_share_info?access_token=%s",
                access_token
        );

        Map<String, Object> body = new HashMap<>();
        /** 设置body */
        body.put("agentid", agentId);
        body.put("business_type", "1");


        /** 构造HTTP请求*/
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        /**发送 请求*/
        String result = restTemplate.postForObject(url, entity, String.class);
        System.out.println("目前得到信息是"+ result);


        return result;
    }

    /** 获得已经共享应用的公司表 */
    public JsonNode getSharCropList(){
        String ShareInfoString = getAppShareInfo();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode ShareInfoJson;
        try {
            ShareInfoJson = mapper.readTree(ShareInfoString);
        } catch (Exception e) {
            System.out.println("应用转换失败");
            return null;
        }

        return ShareInfoJson.get("corp_list");
    }


    /** 获取 下游 access_token */
    public String getPartnerAccessToken(String cropid, String agentId) {
        //查看本地是否存在缓存，如果没有，执行获取操作
        String PatternAccess_token = redisService.get("wecom:partner:"+cropid+":accesstoken");
        if(StringUtils.hasText(PatternAccess_token)){
            System.out.println("目前本地以及储存partner_access_token: "+PatternAccess_token);
            return PatternAccess_token;
        }


        String access_token = getAccessToken();
        String url = String.format(
                "https://qyapi.weixin.qq.com/cgi-bin/corpgroup/corp/gettoken?access_token=%s",
                access_token
        );

        Map<String, Object> body = new HashMap<>();
        /** 设置body */
        body.put("corpid", cropid);
        body.put("business_type", "1");
        body.put("agentid", agentId);

        /** 构造HTTP请求*/
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        /**发送 请求*/
        Map result = restTemplate.postForObject(url, entity, Map.class);

        PatternAccess_token =  (String) result.get("access_token");

        //缓存结果，至本地
        redisService.setx("wecom:partner:"+cropid+":accesstoken",PatternAccess_token,7150);
        System.out.println("已将partneraccesstoken："+PatternAccess_token+"存入缓存");

        return PatternAccess_token;
    }

    /** 获取下游一个具体公司的access_token*/
    public String getPartnerAccessToken(JsonNode crop){
        String partnerCorpid = crop.get("corpid").asText();
        String partnerAgentId = crop.get("agentid").asText();
        String PartnerAccessTokenAnswer =  getPartnerAccessToken(
                partnerCorpid ,partnerAgentId
        );
        return PartnerAccessTokenAnswer;

    }

    /** 获取每一个共享公司的获取每一个公司员工加密userId的接口*/
    public JsonNode getEncryptedAccountInfo(String PartnerAccessTokenAnswer,String partnerAgentId){
        String AppShareInfoString = getOneAppShareInfo(PartnerAccessTokenAnswer,partnerAgentId);
        JsonNode AppShareInfoJson;
        ObjectMapper mapper = new ObjectMapper();
        try {
            AppShareInfoJson = mapper.readTree(AppShareInfoString);
        } catch (Exception e) {
            System.out.println("应用转换失败");
            System.out.println(e);
            return null;
        }
        return AppShareInfoJson.get("allow_userinfos").get("user");
    }

    /**上下游通讯录分组*/


    /** 获取指定的应用详情,包含用户信息*/
    public String getOneAppShareInfo(String access_token,String agentId) {
        String url = String.format(
                "https://qyapi.weixin.qq.com/cgi-bin/agent/get?access_token=%s&agentid=%s",
                access_token, agentId
        );

        Map res = restTemplate.getForObject(url, Map.class);
        ObjectMapper objectMapper = new ObjectMapper();
        String resStr = "";
        try {
            resStr = objectMapper.writeValueAsString(res);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


//        System.out.println("目前得到res"+ res);

        return resStr;
    }

    /**获得一个的 上下游中 的组别*/

    /** getUserById*/
    public String getUserById(String access_token,String userId) {
        String url = String.format(
                "https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=%s&userid=%s",
                access_token, userId
        );

        Map res = restTemplate.getForObject(url, Map.class);
        ObjectMapper objectMapper = new ObjectMapper();
        String resStr = "";
        try {
            resStr = objectMapper.writeValueAsString(res);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


//        System.out.println("目前得到res"+ res);

        return resStr;
    }
    /**用 code 换 userid */
    public WeComUser getUserByCode(String code) {
        String token = getAccessToken();

        String url = String.format(
                "https://qyapi.weixin.qq.com/cgi-bin/auth/getuserinfo?access_token=%s&code=%s",
                token, code
        );

        return restTemplate.getForObject(url, WeComUser.class);
    }

    /** 发送消息 */


    /** 获取 企业jsapi_ticket */
    public String getConfigSignature() {
        String token = getAccessToken();
        String url = String.format(
                "https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket?access_token=%s",
                token
        );
        Map res = restTemplate.getForObject(url, Map.class);
        System.out.println("企业结果值为"+res);
        return (String) res.get("ticket");
    }

    /** 获取 应用jsapi_ticket */
    public String getAgentConfigSignature() {
        String token = getAccessToken();
        String url = String.format(
                "https://qyapi.weixin.qq.com/cgi-bin/ticket/get?access_token=%s&type=agent_config",
                token
        );
        Map res = restTemplate.getForObject(url, Map.class);
        System.out.println("应用结果值为: "+res);
        return (String) res.get("ticket");
    }

    public String getCorpId() {
        return corpId;
    }

    /** 一个list+一个message+agentId*/
    public void sendMessage(String touser, String cropId,String agentId,String Message){
        String token = getPartnerAccessToken(cropId,agentId);
        String url = String.format(
                "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=%s",
                token
        );

        Map<String, Object> body = new HashMap<>();
        //获得该公司的agentId和发送密文

        /** 设置body */
        body.put("touser", touser);
        body.put("msgtype", "text");
        body.put("agentid", agentId);
        body.put("enable_id_trans", "1");

        Map<String, Object> text = new HashMap<>();
        text.put("content", Message);
        body.put("text", text);


        /** 构造HTTP请求*/
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        /**发送 请求*/
        Map result = restTemplate.postForObject(url, entity, Map.class);
        System.out.println(result);

    }


    //SHA1算法
    public String sha1(String str) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] bytes = md.digest(str.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }


    public List<MessageHistoryVO> getMessageHistory() {

        List<Messagestore> stores = messagestoreMapper.selectList(null);

        List<MessageHistoryVO> result = new ArrayList<>();

        for (Messagestore store : stores) {

            MessageHistoryVO vo = new MessageHistoryVO();
            vo.setMessageStoreId((store.getId()));
            vo.setMessage(store.getMessage());
            vo.setCreateTime(store.getCreateTime());

            List<Messagecrop> crops =
                    messageCropMapper.selectByStoreId(store.getId());

            List<CompanyVO> companyList = new ArrayList<>();

            for (Messagecrop crop : crops) {

                CompanyVO companyVO = new CompanyVO();
                companyVO.setCropName(crop.getCropName());
                companyVO.setAgentId(crop.getAgentId());

                List<Messagecropuser> users =
                        messageCropUserMapper.selectByStoreId(store.getId());

                System.out.println(users);
                List<UserVO> userList = users.stream()
                        .map(u -> {
                            UserVO userVO = new UserVO();
                            userVO.setUserId(u.getUserId());
                            userVO.setUserName(u.getUserName());
                            return userVO;
                        }).collect(Collectors.toList());

                companyVO.setUserList(userList);
                companyList.add(companyVO);
            }

            vo.setCompanyList(companyList);
            result.add(vo);
        }

        return result;
    }




}
