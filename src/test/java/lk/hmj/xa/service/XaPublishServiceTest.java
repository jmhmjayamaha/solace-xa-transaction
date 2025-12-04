package lk.hmj.xa.service;

//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InOrder;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.jms.core.JmsTemplate;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.inOrder;
//import static org.mockito.Mockito.verifyNoMoreInteractions;

//@ExtendWith(MockitoExtension.class)
class XaPublishServiceTest {

//    @Mock
//    private JmsTemplate jmsTemplate;
//
//    @Mock
//    private JdbcTemplate jdbcTemplate;
//
//    @InjectMocks
//    private XaPublishService service;
//
//    @Test
//    void publishToTopicAndLog_success_callsDbThenJms() {
//        String topic = "my/topic";
//        String payload = "hello";
//
//        service.publishToTopicAndLog(topic, payload, false);
//
//        InOrder order = inOrder(jdbcTemplate, jmsTemplate);
//        order.verify(jdbcTemplate).update(
//                eq("INSERT INTO TX_LOG (TOPIC, PAYLOAD, CREATED_AT) VALUES (?, ?, CURRENT_TIMESTAMP)"),
//                eq(topic),
//                eq(payload)
//        );
//        order.verify(jmsTemplate).convertAndSend(eq(topic), eq(payload));
//        verifyNoMoreInteractions(jdbcTemplate, jmsTemplate);
//    }
//
//    @Test
//    void publishToTopicAndLog_failureAfterSend_throwsAndCallsBoth() {
//        String topic = "my/topic";
//        String payload = "hello-fail";
//
//        assertThrows(RuntimeException.class, () -> service.publishToTopicAndLog(topic, payload, true));
//
//        InOrder order = inOrder(jdbcTemplate, jmsTemplate);
//        order.verify(jdbcTemplate).update(
//                eq("INSERT INTO TX_LOG (TOPIC, PAYLOAD, CREATED_AT) VALUES (?, ?, CURRENT_TIMESTAMP)"),
//                eq(topic),
//                eq(payload)
//        );
//        order.verify(jmsTemplate).convertAndSend(eq(topic), eq(payload));
//        verifyNoMoreInteractions(jdbcTemplate, jmsTemplate);
//    }
}