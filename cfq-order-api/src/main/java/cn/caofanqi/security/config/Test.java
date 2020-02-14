package cn.caofanqi.security.config;

import cn.caofanqi.security.pojo.dto.OrderDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

/**
 * 故意调用错误方法
 * @author caofanqi
 * @date 2020/2/14 17:21
 */
public class Test {


    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1ODE2NzY3OTUsInVzZXJfbmFtZSI6InpoYW5nc2FuIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiJdLCJqdGkiOiIxZjU1ZTlhMy00NTY5LTQxYWEtYWNlNy1hMTdlMWIxZWY0ODUiLCJjbGllbnRfaWQiOiJ3ZWJBcHAiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXX0.j57wdStvUO1y3zHLsMCmjOK1rAdorNmW27Tsh5nTZDotdi5ASsf37VR7H9jHhedOXRsEnYMm33GP3ne23-wa00e2L-v16J9Eh9nwlBdcKuME_R1CP904XfsX3yVIKXv6T97ploJMQuCbUdr3I3lMkH-J8oH_Sxnf2ZeRnlIBKGpyZksDLkKc53cpDGG-b3HqRNOtyA-BpNdtLX345757hae5J6LeNTfcnFArXnBFZK9aGVXtEFSAEd3wJg2ZZkHA2sP9uYy-M8TcSehu6cWyXvvUmdUSG9LPDPiLoBCSOCtfRdmLPjGU2R1MvinDHPQVyvHPinz9ESTG2hjqkPS_Mg");
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setProductId(123L);

        HttpEntity httpEntity = new HttpEntity(orderDTO,headers);

        while (true){
            try {
                restTemplate.exchange("http://127.0.0.1:9080/orders", HttpMethod.POST,httpEntity,String.class);
            }catch (Exception e){}
        }

    }


}
