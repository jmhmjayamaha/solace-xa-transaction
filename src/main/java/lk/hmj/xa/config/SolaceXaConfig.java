package lk.hmj.xa.config;

import com.solacesystems.jms.SolXAConnectionFactory;
import com.solacesystems.jms.SolJmsUtility;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class SolaceXaConfig {

    @Value("${solace.host}")
    private String solaceHost;          // e.g. smf://localhost:55555

    @Value("${solace.vpn}")
    private String solaceVpn;           // message VPN

    @Value("${solace.username}")
    private String solaceUsername;

    @Value("${solace.password}")
    private String solacePassword;

    /**
     * XA-aware Solace ConnectionFactory.
     * Uses Solace XA factory as per Solace docs.
     */
    @Bean
    public SolXAConnectionFactory solaceXaConnectionFactory() throws Exception {
        // programmatic XA factory creation (SolJmsUtility)  [oai_citation:2‡docs.solace.com](https://docs.solace.com/API/Solace-JMS-API/Establishing-Connections.htm?utm_source=chatgpt.com)
        SolXAConnectionFactory xaCf = SolJmsUtility.createXAConnectionFactory();

        xaCf.setHost(solaceHost);
        xaCf.setVPN(solaceVpn);
        xaCf.setUsername(solaceUsername);
        xaCf.setPassword(solacePassword);

        // required for XA, see Solace XA docs  [oai_citation:3‡docs.solace.com](https://docs.solace.com/API/Solace-JMS-API/Creating-XA-Connections.htm?utm_source=chatgpt.com)
        xaCf.setDirectTransport(false); // only guaranteed msg (required for transacted)


        return xaCf;
    }

    /**
     * Narayana starter will wrap this ConnectionFactory in its own proxy
     * so it can enlist the XAResource in the global transaction.  [oai_citation:4‡GitHub](https://github.com/snowdrop/narayana-spring-boot?utm_source=chatgpt.com)
     */
    @Bean
    public ConnectionFactory jmsConnectionFactory(SolXAConnectionFactory solaceXaConnectionFactory) {
        // we can expose the XA factory directly as the main CF
        return solaceXaConnectionFactory;
    }

    /**
     * JmsTemplate configured for Pub/Sub and JTA-managed transactions.
     */
    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory jmsConnectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(jmsConnectionFactory);
        // Pub/Sub domain (topic); set false if you want queues
        jmsTemplate.setPubSubDomain(true);
        // Let JTA manage the transaction; sessions are transacted
        jmsTemplate.setSessionTransacted(true);
        return jmsTemplate;
    }
}