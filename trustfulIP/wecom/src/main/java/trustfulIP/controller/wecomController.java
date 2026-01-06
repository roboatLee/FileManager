package trustfulIP.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trustfulIP.Dto.WeComUser;

import trustfulIP.Dto.entity.Messagecrop;
import trustfulIP.Dto.entity.Messagecropuser;
import trustfulIP.Dto.entity.Messagestore;
import trustfulIP.Dto.vo.MessageHistoryVO;
import trustfulIP.Service.WeComService;
import trustfulIP.mapper.MessagecropMapper;
import trustfulIP.mapper.MessagecropuserMapper;
import trustfulIP.mapper.MessagestoreMapper;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author KitenLee
 * * @date 2025/12/24
 */
@RestController
@RequestMapping("/api/wecom")
public class wecomController {


    @Autowired
    private WeComService weComService;
    @Autowired
    private MessagestoreMapper messagestoreMapper;

    @Autowired
    private MessagecropMapper messagecropMapper;

    @Autowired
    private MessagecropuserMapper messagecropuserMapper;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body,
                                   HttpSession session) {

        String code = body.get("code");

        //获得User信息
        WeComUser user = weComService.getUserByCode(code);

        if (user.getErrcode() != null && user.getErrcode() != 0) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(user);
        }

        // 建立登录态（推荐）
        session.setAttribute("WE_COM_USER", user.getUserid());

        return ResponseEntity.ok(user);
    }

    /** 企业JS-SDK 签名接口 */
    @GetMapping("/getconfigsignature")
    public Map<String, Object> getSignature(@RequestParam String url) throws Exception {
        String ticket = weComService.getConfigSignature();
        String nonceStr = UUID.randomUUID().toString().replace("-", "");
        long timestamp = System.currentTimeMillis() / 1000;

        String plain = "jsapi_ticket=" + ticket +
                "&noncestr=" + nonceStr +
                "&timestamp=" + timestamp +
                "&url=" + url;

        String signature =weComService.sha1(plain);

        Map<String, Object> result = new HashMap<>();
        result.put("corpId", weComService.getCorpId());
        result.put("nonceStr", nonceStr);
        result.put("timestamp", timestamp);
        result.put("signature", signature);

        System.out.println(result);
        return result;
    }

    /** 应用JS-SDK 签名接口 */
    @GetMapping("/getAgentConfigSignature")
    public Map<String, Object> getAgentConfigSignature(@RequestParam String url) throws Exception {
        String ticket = weComService.getAgentConfigSignature();
        String nonceStr = UUID.randomUUID().toString().replace("-", "");
        long timestamp = System.currentTimeMillis() / 1000;

        String plain = "jsapi_ticket=" + ticket +
                "&noncestr=" + nonceStr +
                "&timestamp=" + timestamp +
                "&url=" + url;

        String signature =weComService.sha1(plain);

        Map<String, Object> result = new HashMap<>();
        result.put("corpId", weComService.getCorpId());
        result.put("nonceStr", nonceStr);
        result.put("timestamp", timestamp);
        result.put("signature", signature);

        System.out.println(result);
        return result;
    }

    @PostMapping("/sendPartnerMessage")
    public  void senMessage(@RequestBody JsonNode request){


        Messagestore messagestore = new Messagestore();


        /** 将request拆分*/
        System.out.println(request);
        String Message =  request.get("message").asText();

        //设置数据库Message
        messagestore.setMessage(Message);
        messagestoreMapper.insert(messagestore);


        JsonNode cropList = request.get("companyList");

        for (JsonNode crop : cropList){
            Messagecrop messagecrop = new Messagecrop();

            JsonNode userlist =  crop.get("userList");

            String userTogether = "";

            //获得agentId
            String agentId =  crop.get("agentId").asText();
            String cropId =  crop.get("corpid").asText();
            String cropName =  crop.get("cropName").asText();

            messagecrop.setMessageStoreId(messagestore.getId());
            messagecrop.setCropName(cropName);
            messagecrop.setCropId(cropId);
            messagecrop.setAgentId(agentId);
            messagecropMapper.insert(messagecrop);

            for  (JsonNode user : userlist){
                Messagecropuser messagecropuser = new Messagecropuser();
                userTogether += "|"+user.asText();
                messagecropuser.setMessageStoreId(messagestore.getId());
                messagecropuser.setMessageCropId(cropId);
                messagecropuser.setUserId(user.asText());
                String userinfoString =  weComService.getUserById(weComService.getPartnerAccessToken(cropId,agentId),user.asText());
                ObjectMapper mapper = new ObjectMapper();

                JsonNode userinfo = null;
                try{
                    userinfo = mapper.readTree(userinfoString);
                }catch (Exception e){
                    System.out.println(e);
                }
                messagecropuser.setUserName(userinfo.get("name").asText());
                messagecropuserMapper.insert(messagecropuser);
            }
            userTogether.substring(1) ;


//            String PartnerAccessToken =  weComService.getPartnerAccessToken(cropId,agentId);
            weComService.sendMessage(userTogether.substring(1),cropId,agentId,Message);
        }


        /** 发送消息*/


    }

    @GetMapping("/getPartner")
    public ArrayNode getPartner(){
        System.out.println("正在获取应用共享信息");

        ObjectMapper mapper = new ObjectMapper();

        /** 获得已经共享应用的公司表 */
        JsonNode ShareCropInfoJson = weComService.getSharCropList();

        System.out.println("目前获得的公司表是");
        System.out.println(ShareCropInfoJson);
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

        ArrayNode FinalAnswer = mapper.createArrayNode();
        /** 获取下游access_token表*/
        for (JsonNode crop: ShareCropInfoJson) {
            /** 获取每一个共享公司的access_token*/
            String PartnerAccessTokenAnswer = weComService.getPartnerAccessToken(crop);
            //获得当前crop的agentid
            String partnerAgentId = crop.get("agentid").asText();
            String partnerCropName = crop.get("corp_name").asText();
            String partnerCropId = crop.get("corpid").asText();
//            System.out.println("这是我要的"+crop);
            //创建cropAnswer
            ObjectNode cropAnswer = mapper.createObjectNode();
            cropAnswer.put("cropName",partnerCropName);
            /** TODO 安全性待提高*/
            cropAnswer.put("agentId",partnerAgentId);

            cropAnswer.put("corpid",partnerCropId);

            /** 获取每一个共享公司的获取每一个公司员工加密userId的接口*/
            JsonNode UserListId =  weComService.getEncryptedAccountInfo(PartnerAccessTokenAnswer,partnerAgentId);


            /** 提取userid*/
            System.out.println("在"+partnerCropName+"的公司成员列表如下：");
            System.out.println(UserListId);
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();


            ArrayNode users = mapper.createArrayNode();
            for(JsonNode userNode : UserListId ){
                String userid= userNode.get("userid").asText();
                JsonNode UserJsonNode = null;
                /** 执行用户解密工作*/
                try {
                    UserJsonNode = mapper.readTree(weComService.getUserById(PartnerAccessTokenAnswer,userid));
                }catch (Exception e){
                    System.out.println(e);
                }
                System.out.println(UserJsonNode);
                String UserName =  UserJsonNode.get("name").asText();
                String UserAvatarPath =  UserJsonNode.get("avatar").asText();

                ObjectNode UserAnswer = mapper.createObjectNode();
                /** TODO 安全性待提高*/
                UserAnswer.put("UserId",userid);
                UserAnswer.put("UserName",UserName);
                UserAnswer.put("UserAvatarPath",UserAvatarPath);
                System.out.println("前端的用户信息： "+UserAnswer);
                users.add(UserAnswer);
            }
            cropAnswer.set("cropWorker",users);

            /**result值
             * 公司表
             *  公司名称
             *  公司的cropId
             *  公司所共享的agentId
             *  公司员工
             *      公司员工名称
             *      员工id
             *      公司员工头像链接
             *
             * */
            FinalAnswer.add(cropAnswer);

            /** 提取user信息里面的name和avater地址*/

        }
        System.out.println("前端信息： "+FinalAnswer);
        return FinalAnswer;
    }

    @GetMapping("/message/history")
    public List<MessageHistoryVO> history() {
        return weComService.getMessageHistory();
    }


}
