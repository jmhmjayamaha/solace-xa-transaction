package lk.hmj.xa.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class XaPublishService {

    private final JmsTemplate jmsTemplate;
    private final JdbcTemplate jdbcTemplate;

    public XaPublishService(JmsTemplate jmsTemplate, JdbcTemplate jdbcTemplate) {
        this.jmsTemplate = jmsTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * One global XA transaction:
     *  - Insert into H2 (TX_LOG)
     *  - Send JMS message to Solace topic
     * If failAfterSend == true -> runtime exception -> XA rollback for BOTH.
     */
    @Transactional
    public void publishToTopicAndLog(String topicName, String payload, boolean failAfterSend) {

        // 1) INSERT into H2 (XA enlisted H2 DataSource)
        jdbcTemplate.update(
                "INSERT INTO TX_LOG (TOPIC, PAYLOAD, CREATED_AT) VALUES (?, ?, CURRENT_TIMESTAMP)",
                topicName,
                payload
        );

        // 2) SEND Solace JMS message (XA enlisted Solace XAConnectionFactory)
        jmsTemplate.convertAndSend(topicName, payload);

        // 3) Simulate a failure -> whole XA transaction should roll back
        if (failAfterSend) {
            throw new RuntimeException("Simulated failure - XA should roll back DB + JMS");
        }
    }
}