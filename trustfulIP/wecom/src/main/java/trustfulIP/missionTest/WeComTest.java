package trustfulIP.missionTest;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import trustfulIP.Service.WeComService;

/**
 * @author KitenLee
 * * @date 2025/12/26
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WeComTest {
    @Autowired
    WeComService weComService;

    @Test
    public void  testGetChainList() {
        weComService.getChainList();
    }

    @Test
    public  void testFuncation() {
        ObjectMapper mapper = new ObjectMapper();
        // 根节点
        ObjectNode root = mapper.createObjectNode();
        root.put("message", "大家好，这是一条群发消息");

        // companyList 数组
        ArrayNode companyList = mapper.createArrayNode();

        // company 对象
        ObjectNode company = mapper.createObjectNode();
        company.put("cropName", "鑫鑫绒科技");
        company.put("corpid", "wpy0T3cQAAApCgsl0MR2ojS03S_d0QOw");
        company.put("agentId", "1000002");

        // userList 数组
        ArrayNode userList = mapper.createArrayNode();
        userList.add("woy0T3cQAA7Q3qB6Dq19V6okQQGHgB7g");
        userList.add("woy0T3cQAAx0u3Okq1HX3qEUERFiJdJQ");

        company.set("userList", userList);

        // company 放入 companyList
        companyList.add(company);

        // companyList 放入 root
        root.set("companyList", companyList);

        // 最终 JsonNode
        JsonNode jsonNode = root;

        System.out.println(jsonNode);

        /** todo 安全性需要进一步提高*/
        /**
         * {
         *     "message": "大家好，这是一条群发消息",
         *     "companyList": [
         *         {
         *             "cropName": "鑫鑫绒科技",
         *             "agentId": "1000002",
         *             "cropId": "wpy0T3cQAAApCgsl0MR2ojS03S_d0QOw",
         *             "userList": [
         *                 "woy0T3cQAA7Q3qB6Dq19V6okQQGHgB7g",
         *                 "woy0T3cQAAx0u3Okq1HX3qEUERFiJdJQ"
         *             ]
         *         }
         *     ]
         * }
         * */
        String Message = jsonNode.get("message").asText();
        JsonNode cropList = jsonNode.get("companyList");
        //将userlist转换为字符串
        for (JsonNode crop : cropList){
            JsonNode userlist =  crop.get("userList");
            String userTogether = "";
            for  (JsonNode user : userlist){
                userTogether += "|"+user.asText();
            }
            userTogether.substring(1) ;
            //获得agentId
            String agentId =  crop.get("agentId").asText();
            String cropId =  crop.get("corpid").asText();

//            String PartnerAccessToken =  weComService.getPartnerAccessToken(cropId,agentId);
            weComService.sendMessage(userTogether.substring(1),cropId,agentId,Message);
        }




    }
}
