package org.elastos.meetup;

import org.elastos.meetuplib.base.EthChain;
import org.junit.Test;

import java.math.BigInteger;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        String privateKey="2A6C5BF637CFE4B3EB7745553D5C017CC7316626A654E467407E42455929FD80";

         BigInteger gasPrice= BigInteger.valueOf(3000000000L);
         BigInteger gasLimit= BigInteger.valueOf(2602513L);
        String _name="百度大脑行业创新论坛智慧零售上海专场";
        String _symbol="baidu";
        java.math.BigInteger _supplyLimit= BigInteger.valueOf(100);
        String _contractInfo="地点：上海黄浦区西藏中路555号雅居乐万豪酒店五层大宴会厅 \n" +
                "时间：2018-11-29 13:30 ~ 2018-11-29 17:00\n" +
                "\n" +
                "百度大脑主办的“百度大脑行业创新论坛”将走进全国六大城市，全面分享百度大脑面向多个行业的技术解决方案，并与行业内的企业管理者、专家及产品和方案提供商深度交流，探讨如何用AI技术赋能业务场景，创新产品和服务，解决行业痛点，创造新的业务价值。\n" +
                "诚邀零售、便利店等相关领域的从业者，以及技术服务提供商和投资者等参与本场论坛，共同探讨AI将如何助力零售行业，降低成本、提升效率与服务质量。\n" +
                "活动上，将发布百度大脑面向企业服务领域的技术解决方案，百度多家生态伙伴也将展示基于百度AI技术的企业服务新产品/解决方案，敬请期待！";
        String _owner="0xf5dbb8c80fdac435953680158ffa5200a96f6999";
        try {
            String result= EthChain.deploy(privateKey,gasPrice,gasLimit,_name,_symbol,_supplyLimit,_contractInfo,_owner);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}